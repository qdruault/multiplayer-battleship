/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.PublicUser;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
/**
 * Object: display all info of player profile.
 * Display user's own profile (writable)
 * Display others profiles (read-only)
 * 
 * @author Lipeining
 */

public class PlayerProfileController extends AbstractController{
    @FXML
    public  Label userID;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label birthday;    
    @FXML
    private TextField description;
    @FXML
    private ImageView image;
   
    private PublicUser me;
    private PublicUser other;
    private boolean isLoading = false; 
    private boolean isOther = false; 
    private String attribut;
    private String imagePath;
    
    @FXML
    @Override
    public void start(){
       refresh();
    } 
  
    @FXML
    private void back(ActionEvent event) throws IOException{
        getIhmmain().toMenu();
    }
    @FXML
    private void toPlayerList(ActionEvent event) throws IOException{
        getIhmmain().toPlayerList();
    }
    
    @FXML
    private void editDescription(ActionEvent event) throws IOException{
        String text;
        description.setEditable(true);
        text = description.getText();
        description.setText(text);
    }
    @FXML
    private void closeEdit(KeyEvent event) throws IOException{
        KeyCode code = event.getCode();
        if (code == KeyCode.ENTER){
            description.setEditable(false);
        }
    }
    @FXML
    private void editPlayerName(ActionEvent event) throws IOException{
        attribut="PlayerName";
        popup(attribut);
    }
    @FXML
    private void editFirstName(ActionEvent event) throws IOException{
        attribut="FirstName";
        popup(attribut);
    }
    @FXML
    private void editLastName(ActionEvent event) throws IOException{
        attribut="LastName";
        popup(attribut);
    }
    @FXML
    private void editBirthday(ActionEvent event) throws IOException{
        attribut="Birthday";
        popup(attribut);
    }
    @FXML
    private void editPassword(ActionEvent event) throws IOException{
        attribut="Password";
        popup(attribut);
    }
    /**
     * Generate a pop-up
     * @param attribut:name of info that user would like to modify
     * @throws IOException 
     */
    private void popup(String attribut) throws IOException{
        final Stage primaryStage = getIhmmain().primaryStage;
        String path = "/fxml/ihmmain/popup.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent sceneLoader = loader.load();
        PopupController controller=loader.getController();
        controller.setFacade(getFacade());
        controller.setIhmmain(getIhmmain());
        controller.setAttribut(attribut);
        Scene newScene;
        newScene = new Scene(sceneLoader);
        Stage popup = new Stage();
        popup.initOwner(primaryStage);
        popup.setScene(newScene);
        popup.show();
    } 
    /**
     * This function is for receiving the profile of other player asked by user
     * @param player: profile sent by Data for us to display
     * @throws IOException 
     */
    public void recievePublicUser(PublicUser player) throws IOException{
        if(isRunning()){
            isLoading = false;
            other = player;
        }
    }
    /**
     * This function is for waiting the profile
     * As soon as receive the profile sent by Data, skip the loading and refresh the page.
     * @throws IOException 
     */
    public void loading() throws IOException{
        isLoading = true;
        if (isLoading){
            //change the cursor
            getIhmmain().primaryStage.getScene().setCursor(Cursor.WAIT);
            
            //create a wait task, check every 0.5s if the loading is finished, finish the waiting
            Task<Void> wait;
            wait = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        while(isLoading){
                            Logger.getLogger(
                                    PlayerProfileController.class.getName()).log(
                                            Level.INFO, "Waiting."
                                    );
                            Thread.sleep(500);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            //if loading succed, call the refresh();
            wait.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    isOther = true;
                    try {
                        getIhmmain().primaryStage.getScene().setCursor(Cursor.DEFAULT);
                        getIhmmain().toPlayerProfile();
                    } catch (IOException ex) {
                        Logger.getLogger(
                                PlayerProfileController.class.getName()).log(
                                        Level.SEVERE, null, ex);
                    }
                }
            });
            //if loading failed
            wait.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t){
                    Logger.getLogger(
                            PlayerProfileController.class.getName()).log(
                                    Level.INFO,
                                    "Loading task failed : {0}", t.toString()
                            );
                }
            });
            new Thread(wait).start();
        }
    }
    @Override
    /**
     * Initialize all the info of profile
     */
    public void refresh(){
        if (!isOther){
            try{
                //imagePath = me.getLightPublicUser().
                image.setImage(new Image (imagePath));
                firstName.setText(me.getFirstName());
                lastName.setText(me.getLastName());
                birthday.setText(me.getBirthDate().toString());
            }
            catch(NullPointerException e){
                Logger.getLogger(
                        PlayerProfileController.class.getName()).log(
                                Level.INFO,
                                "[PlayerProfile] error - my profile is null."
                        );
            }
        }
        else{
            try{
                userID.setText(other.getLightPublicUser().getPlayerName());
                firstName.setText(other.getFirstName());
                lastName.setText(other.getLastName());
                birthday.setText(other.getBirthDate().toString());
            }
            catch(NullPointerException e){
                Logger.getLogger(
                        PlayerProfileController.class.getName()).log(Level.INFO,
                        "[PlayerProfile] - error - other profile is null."
                        );
            }
        }
    }
}
