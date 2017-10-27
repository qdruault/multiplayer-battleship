/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

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
    
    @FXML 
    private void handleButtonCreate(ActionEvent event){
        System.out.println("Action Create requested");
        
    }
    
    @FXML
    private void handleButtonReturn(ActionEvent event) throws IOException{
        System.out.println("Action Return requested");
        back();
    } 
    
    private void back() throws IOException{
        System.out.println("Return method called");
        ihmmain.toMenu();
    }
    

    
    private boolean createUser(String userName, String password, String firstName, String lastName, int age, String avatarPath){
        System.out.println("toPlayerList method called");
        
        return true;
    }     
}
