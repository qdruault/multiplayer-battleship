package com.utclo23.ihmmain.controller;

import com.utclo23.data.module.DataException;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller of the login page.
 * 
 * @author Camille Quenin
 */
public class LoginController extends AbstractController{
    
    @FXML
    private TextField usernameField;  
    
    @FXML
    private PasswordField passwordField;

    /**
     * Attempts to log in with the given username and password.
     * Called when the login button is clicked or when the ENTER key is pressed.
     *
     * @param event
     */
    @FXML
    private void loginAction(ActionEvent event) throws IOException{
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if(fieldsAreNotEmpty(username, password)){
            try{
                facade.iDataIHMMain.signin(username, password);
                ihmmain.toMenu();
            }catch (DataException e){
                showErrorPopup(
                        "This user doesn't exist.",
                        "Please, check that the username and the password are correct.",
                        "(" + e.getMessage() + ")"
                );
            }
        }
    }
    
    /**
     * Goes to the 'Create User' page.
     * Called when the create button is clicked.
     *
     * @param event
     */
    @FXML
    private void createUserAction(ActionEvent event) throws IOException{
        ihmmain.toCreateUser();
    }
    
    /**
     * Exits the application.
     * Called when the exit button is clicked.
     *
     * @param event
     */
    @FXML
    private void exitAction(ActionEvent event){
        System.exit(0);
    }
    
    /**
     * Checks if the usernameField and the passwordField are not empty
     * and changes the color of their border accordingly (red or grey).
     *
     * @param username
     * @param password
     * @return true if they are not empty, false otherwise
     */
    private boolean fieldsAreNotEmpty(String username, String password){
        usernameField.setStyle("");
        passwordField.setStyle("");
        boolean notEmpty = true;
        
        if(username.length() == 0){
            usernameField.setStyle("-fx-border-color: red;"); 
            notEmpty = false;
        }        
        if(password.length() == 0){
            passwordField.setStyle("-fx-border-color: red;");
            notEmpty = false;
        }
        return notEmpty;
    }
}
