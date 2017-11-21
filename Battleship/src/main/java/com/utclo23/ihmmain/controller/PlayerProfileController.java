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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
/**
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
    private Button backButton;
    @FXML
    private Button playerList;
    @FXML
    private Button editDesc;
    
    private PublicUser me;
    String testUserID="Player1";
    @FXML
    @Override
    public void start(){
        try{
            me = facade.iDataIHMMain.getMyPublicUserProfile();
            userID.setText(me.getLightPublicUser().getPlayerName());
            firstName.setText(me.getFirstName());
            lastName.setText(me.getLastName());
            birthday.setText(me.getBirthDate().toString());
        }
        catch(NullPointerException e){
            System.out.println("[PlayerProfile] - getMyPublicUserProfile() not supported yet");
        }
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
    private void editID(ActionEvent event) throws IOException{
        popup();
    }
    
    private void popup() throws IOException{
        final Stage primaryStage = ihmmain.primaryStage;
        String path = "/fxml/ihmmain/popup.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent sceneLoader = loader.load();
        Scene newScene;
        newScene = new Scene(sceneLoader);
        Stage popup = new Stage();
        popup.initOwner(primaryStage);
        popup.setScene(newScene);
        popup.show();
    }         
}
