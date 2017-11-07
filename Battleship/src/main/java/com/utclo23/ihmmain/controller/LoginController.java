package com.utclo23.ihmmain.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
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
            //TODO : add IDataIHMMain.signin(username, password) within a try/catch block
            ihmmain.toMenu();
        }
    }
    
    @FXML
    private void createUserAction(ActionEvent event) throws IOException{
        ihmmain.toCreateUser();
    }
    
    @FXML
    private void exitAction(ActionEvent event) throws IOException{
        System.exit(0);
    }
    
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
