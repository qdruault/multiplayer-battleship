/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.facade.DataFacade;
import com.utclo23.ihmmain.IHMMain;
import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author Linxuhao
 */

public class CreateUserController extends AbstractController{
    
    private String avatarPath = null;

    @FXML
    private Label fileSelected;
    
    // FXML fields
    @FXML
    private TextField userNameField;
    
    @FXML
    private TextField passwordField;
    
    @FXML
    private TextField firstNameField;
    
    @FXML 
    private TextField lastNameField;
    
    @FXML 
    private DatePicker birthDateField;
    
    // BUTTON HANDLERS
    
    @FXML 
    private void handleButtonCreate(ActionEvent event) throws Exception{
        
        if (!isAnyFieldEmpty()){
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            Date birthDate = Date.from(birthDateField.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            createUser(userName, password, firstName, lastName, birthDate, avatarPath);
        }   
    }
    
    @FXML
    private void handleButtonReturn(ActionEvent event) throws IOException{
        back();
    }
    
    @FXML
    private void handleButtonChooseFile(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open avatar file");
        File selectedFile = fileChooser.showOpenDialog(ihmmain.primaryStage);
        if (selectedFile != null){
            avatarPath = selectedFile.getPath();
            fileSelected.setText("file selected: " + avatarPath);
            System.out.println("The chosen file is : " + avatarPath);
        }        
    }
        
    private void back() throws IOException{
        ihmmain.toMenu();
    }
    
    private void createUser(String userName, String password, String firstName, String lastName, Date birthDate, String avatarPath) throws Exception{
        System.out.println("createUser method called");
        facade.iDataIHMMain.createUser(userName, password, firstName, lastName, birthDate, avatarPath);
    }     
        
    // UTILITY methods
    
    private boolean isAnyFieldEmpty(){
        
        boolean fieldEmpty = false;
        
        if (isFieldEmpty(userNameField)){
            fieldEmpty = true;
        }
        if (isFieldEmpty(passwordField)){
            fieldEmpty = true;
        }
        if (isFieldEmpty(firstNameField)){
            fieldEmpty = true;
        }
        if (isFieldEmpty(lastNameField)){
            fieldEmpty = true;
        }
        if (birthDateField.getValue() == null){
            fieldEmpty = true;
        }
        if (avatarPath == null){
            fieldEmpty = true;
        }
        
        return fieldEmpty;
    }
    
    private boolean isFieldEmpty(TextField textField){
        if (textField.getText() == null || textField.getText().trim().isEmpty() ){
            return true;
        } else {
            return false;
        }
    }
}
