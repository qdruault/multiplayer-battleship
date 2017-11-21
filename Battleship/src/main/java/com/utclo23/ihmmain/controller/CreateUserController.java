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
 * @author Louis Groisne
 * Controller class for the user creation view. 
 * This class allows a user to create a new user json file to log into the application.
 * This class get the values from the form's fields with FXML properties.
 * The associated view is defined in the FXML file CreateUser.fxml.
 * No field can be empty for the controller to authorize a new user creation.
 *
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
    
    /**
     * 
     * This method is the handler for the Create button.
     * It is called when a user click on the create button.
     * This method checks the fields by calling @ref isAnyFieldEmpty()
     * If every field is filled, the method calls @createUser()
     * @param event the event parameter used by Java for buttons' handler.
     * @throws IOException throws a default Java exception in case if any failure.
     */
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
    /**
     * This method is the handler for the Return button.
     * It is called when a user click on the return button.
     * Returns to the login screen by calling @ref back().
     * @param event the event parameter used by Java for buttons' handler.
     * @throws IOException throws a default Java exception in case if any failure.
     */
    @FXML
    private void handleButtonReturn(ActionEvent event) throws IOException{
        back();
    }
    /**
     * This method is the handler for the ChooseFile button.
     * It is called when a user click on the choose file button.
     * This opens a windows file chooser to choose a file.
     * A File object is created with the path associated to the selected file.
     * If the File is successfully created, the avatarPath property is set.
     * @param event the event parameter used by Java for buttons' handler.
     * @throws IOException throws a default Java exception in case if any failure.
     */
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
    /**
     * Returns to the login view.
     * Method called by @ref handleButtonReturn.
     * @throws IOException throws a default Java exception in case if any failure.
     */
    private void back() throws IOException{
        ihmmain.toLogin();
    }
    /**
     * This method calls Data's createUser() method to create a new user's JSON file.
     * @param userName: user's name
     * @param password: user's password
     * @param firstName user's first name
     * @param lastName user's last name
     * @param birthDate user's birth date
     * @param avatarPath user's avatar path
     * @throws Exception throws a default Java exception in case if any failure.
     */
    private void createUser(String userName, String password, String firstName, String lastName, Date birthDate, String avatarPath) throws Exception{
        System.out.println("createUser method called");
        facade.iDataIHMMain.createUser(userName, password, firstName, lastName, birthDate, avatarPath);
    }     
        
    // UTILITY methods
    /**
     * This method is used internally to this class.
     * It checks if any field is empty, either by calling @ref isFieldEmpty or by checking against a null value.
     * @return true if any field is empty, false otherwise.
     */
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
    /**
     * This method is used internally to this class.
     * It checks if the given value is null or an empty string.
     * @param textField the TextField to check.
     * @return true if the given field is empty, false otherwise.
     */
    private boolean isFieldEmpty(TextField textField){
        if (textField.getText() == null || textField.getText().trim().isEmpty() ){
            return true;
        } else {
            return false;
        }
    }
}
