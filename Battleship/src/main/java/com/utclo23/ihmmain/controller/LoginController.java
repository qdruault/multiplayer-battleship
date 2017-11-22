package com.utclo23.ihmmain.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controller of the login page.
 * 
 * @author Camille Quenin
 * @author Linxuhao
 */
public class LoginController extends AbstractController{
    
    @FXML
    private TextField usernameField;  
    
    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginAction(ActionEvent event) throws IOException{
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if(fieldsAreNotEmpty(username, password)){
            try{
                facade.iDataIHMMain.signin(username, password);
                ihmmain.toMenu();
            }catch (Exception e){
                e.printStackTrace();
                //TODO : show pop up
            }
        }
    }
    
    @FXML
    private void createUserAction(ActionEvent event) throws IOException{
        ihmmain.toCreateUser();
    }
    
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
