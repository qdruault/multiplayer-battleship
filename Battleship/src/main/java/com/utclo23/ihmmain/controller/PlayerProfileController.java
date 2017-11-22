/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.PublicUser;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
/**
 *
 * @author Lipeining
 */

//recievePublicUserProfile(Player);
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

    private PublicUser me;
    private PublicUser other;
    private boolean isLoading = true; 
    private boolean isOther = false; 
    private String attribut;
    
    @FXML
    @Override
    public void start(){
       refresh();
    } 
    @FXML
    private void back(ActionEvent event) throws IOException{
        ihmmain.toMenu();
    }
    @FXML
    private void toPlayerList(ActionEvent event) throws IOException{
        ihmmain.toPlayerList();
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
    private void popup(String attribut) throws IOException{
        final Stage primaryStage = ihmmain.primaryStage;
        String path = "/fxml/ihmmain/popup.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent sceneLoader = loader.load();
        PopupController controller=loader.getController();
        controller.setFacade(facade);
        controller.setIhmmain(ihmmain);
        controller.setAttribut(attribut);
        Scene newScene;
        newScene = new Scene(sceneLoader);
        Stage popup = new Stage();
        popup.initOwner(primaryStage);
        popup.setScene(newScene);
        popup.show();
    }  
    public void recievePublicUser(PublicUser player) throws IOException{
        isLoading = false;
        other = player;
    }
    public void loading() throws IOException{
        if (isLoading==true){
            //todo 
        }
        else{
            isOther = true;
            ihmmain.toPlayerProfile();
        }
    }
    @Override
    public void refresh(){
        if (isOther==false){
            try{
                me = facade.iDataIHMMain.getMyPublicUserProfile();
                userID.setText(me.getLightPublicUser().getPlayerName());
                firstName.setText(me.getFirstName());
                lastName.setText(me.getLastName());
                birthday.setText(me.getBirthDate().toString());
            }
            catch(NullPointerException e){
                System.out.println("[PlayerProfile] error - my profile is null");
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
                System.out.println("[PlayerProfile] - error - other profile is null");
            }
        }
    }
}
