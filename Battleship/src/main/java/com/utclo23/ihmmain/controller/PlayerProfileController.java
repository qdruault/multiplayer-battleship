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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
/**
 *
 * @author Linxuhao
 */
public class PlayerProfileController extends AbstractController{
    @FXML
    private Label userID;
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
    private Button othersButton;
    @FXML
    private Button editDesc;
    
    private PublicUser me; 
    
    @FXML
    private void initialisation() throws IOException{
        try{
            me = facade.iDataIHMMain.getMyPublicUserProfile();
        }
        catch(UnsupportedOperationException e){
            System.out.println("[PlayerProfile] - getMyPublicUserProfile() not supported yet");
        }
       
       userID.setText(me.getId());
       firstName.setText(me.getFirstName());
       lastName.setText(me.getLastName());
       birthday.setText(me.getBirthDate().toString());
    } 
    @FXML
    private void back(ActionEvent event) throws IOException{
        ihmmain.toMenu();
    }
    
    @FXML
    private void editDesc(ActionEvent event) throws IOException{
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
