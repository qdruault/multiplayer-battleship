/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.facade.DataFacade;
import com.utclo23.ihmmain.IHMMain;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author Linxuhao
 */

public class CreateUserController extends AbstractController{
    
    @FXML
    private TextField userNameField;
    
    @FXML
    private TextField passwordField;
    
    @FXML
    private TextField firstNameField;
    
    @FXML 
    private TextField lastNameField;
    
    @FXML 
    private TextField ageField;
      
    private IHMMain ihmMain;
    
    
    // BUTTON HANDLERS
    
    @FXML 
    private void handleButtonCreate(ActionEvent event){
        
        if (!isAnyFieldEmpty()){
            String userName = userNameField.getText();
            String password = passwordField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            int age = Integer.parseInt(ageField.getText());
            // TODO add a field for the avatar path in the fxml view
            String avatarPath = ""; 
            createUser(userName, password, firstName, lastName, age, avatarPath);
        }   
    }
    
    @FXML
    private void handleButtonReturn(ActionEvent event) throws IOException{
        back();
    } 
        
    private void back() throws IOException{
        ihmmain.toMenu();
    }
    
    private boolean createUser(String userName, String password, String firstName, String lastName, int age, String avatarPath){
        System.out.println("createUser method called");
        // TODO implement the call to dataFacade to create a user
        //dataFacade.createUser(userName, password, firstName, lastName, age, avatarPath);
        throw new UnsupportedOperationException("The call to createUser method from the dataFacade class is not implemented yet");
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
        if (isFieldEmpty(ageField)){
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
