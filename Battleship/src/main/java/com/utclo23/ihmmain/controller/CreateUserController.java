/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * Controller class for the user creation view. 
 * This class allows a user to create a new user json file to log into the application.
 * This class get the values from the form's fields with FXML properties.
 * The associated view is defined in the FXML file CreateUser.fxml.
 * No field can be empty for the controller to authorize a new user creation.
 *
 * @author Louis Groisne
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
    
    private String emptyFields;
    // BUTTON HANDLERS
    
    /**
     * This method is the handler for the Create button.
     * It is called when a user click on the create button.
     * This method checks the fields by calling @ref isAnyFieldEmpty().
     * If every field is filled, the method calls @createUser().
     *
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
        } else {
            // Show popup error
            showErrorPopup("Error", "Some required fields aren't filled", "Empty fields : " + emptyFields);
        }
    }
    /**
     * This method is the handler for the Return button.
     * It is called when a user click on the return button.
     * Returns to the login screen by calling @ref back().
     *
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
     *
     * @param event the event parameter used by Java for buttons' handler.
     * @throws IOException throws a default Java exception in case if any failure.
     */
    @FXML
    private void handleButtonChooseFile(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open avatar file");
        File selectedFile = fileChooser.showOpenDialog(getIhmmain().primaryStage);
        if (selectedFile != null){
            avatarPath = selectedFile.getPath();
            fileSelected.setText("file selected: " + avatarPath);
            Logger.getLogger(CreateUserController.class.getName()).log(
                    Level.INFO, "The chosen file is : {0}", avatarPath);
        }        
    }
    /**
     * Returns to the login view.
     * Method called by @ref handleButtonReturn.
     *
     * @throws IOException throws a default Java exception in case if any failure.
     */
    private void back() throws IOException{
        getIhmmain().toLogin();
    }
    /**
     * This method calls Data's createUser() method to create a new user's JSON file.
     *
     * @param userName: user's name
     * @param password: user's password
     * @param firstName user's first name
     * @param lastName user's last name
     * @param birthDate user's birth date
     * @param avatarPath user's avatar path
     * @throws Exception throws a default Java exception if any failure occurs.
     */
    private void createUser(String userName, String password, String firstName, String lastName, Date birthDate, String avatarPath) throws Exception{
        Logger.getLogger(CreateUserController.class.getName()).log(
                Level.INFO,  "createUser method called.");
        try{
            getFacade().iDataIHMMain.createUser(userName, password, firstName, lastName, birthDate, avatarPath);
            showErrorPopup("Success", "Your account was successfully created", "Back to the login screen.");
            back();
        } catch(Exception e){
            showErrorPopup("Error", "We're sorry, but an error occured", e.getMessage());
        }
    }     
        
    // UTILITY methods
    /**
     * This method is used internally to this class.
     * It checks if any field is empty, either by calling @ref isFieldEmpty or by checking against a null value.
     *
     * @return true if any field is empty, false otherwise.
     */
    private boolean isAnyFieldEmpty(){
        
        emptyFields = "";
        boolean fieldEmpty = false;
        
        if (isFieldEmpty(userNameField)){
            fieldEmpty = true;
            emptyFields += "user name, ";
        }
        if (isFieldEmpty(passwordField)){
            fieldEmpty = true;
            emptyFields += "password, ";
        }
        if (isFieldEmpty(firstNameField)){
            fieldEmpty = true;
            emptyFields += "first name, ";
        }
        if (isFieldEmpty(lastNameField)){
            fieldEmpty = true;
            emptyFields += "last name, ";
        }
        if (birthDateField.getValue() == null){
            fieldEmpty = true;
            emptyFields += "date of birth, ";
        }
        if (avatarPath == null){
            fieldEmpty = true;
            emptyFields += "avatar image";
        }
        
        return fieldEmpty;
    }
    /**
     * This method is used internally to this class.
     * It checks if the given value is null or an empty string.
     *
     * @param textField the TextField to check.
     * @return true if the given field is empty, false otherwise.
     */
    private boolean isFieldEmpty(TextField textField){
        return (textField.getText() == null || textField.getText().trim().isEmpty());
    }
}