/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * @author Audrick
 */
public class CreateGameController extends AbstractController{
    ToggleGroup mode;
    ToggleGroup enemy;
    
    @FXML
    RadioButton radioButtonClassical;
    
    @FXML
    RadioButton  radioButtonBelge;
    
    @FXML
    RadioButton  radioButtonComputer;
    
    @FXML
    RadioButton  radioButtonPlayer;
    
    @FXML 
    CheckBox radioButtonAudience;
    
    @FXML 
    CheckBox radioButtonChat;
    
    @FXML
    TextField gameNameField;
    
    @FXML
    public void initialize(){
        //
        mode = new ToggleGroup();
        enemy = new ToggleGroup();
        
        //
        radioButtonClassical.setToggleGroup(mode);
        radioButtonBelge.setToggleGroup(mode);
        radioButtonComputer.setToggleGroup(enemy);
        radioButtonPlayer.setToggleGroup(enemy);
        
        // Affectation des valeurs par défaut
        radioButtonClassical.setSelected(true);
        radioButtonComputer.setSelected(true);
                
    }
    
    @FXML
    private void back(ActionEvent event) throws IOException{
        ihmmain.toMenu();
    }
    
    @FXML
    private void validateCreateGame(ActionEvent event){
        String names = gameNameField.getText();
        gameNameField.setStyle("");
        if (!names.isEmpty()){
            String modes = ((RadioButton) mode.getSelectedToggle()).getText();
            String enemys = ((RadioButton) enemy.getSelectedToggle()).getText();
            boolean chats = radioButtonChat.isSelected();
            boolean audiences = radioButtonAudience.isSelected();
            facade.iDataIHMMain.createGame(names, audiences, chats, modes);
            System.out.println(modes);
            System.out.println(enemys);
            System.out.println(names);
            System.out.println(chats);
            System.out.println(audiences);
            // comparer deux strings : string1.equal(string2)
        }
        else
            gameNameField.setStyle("-fx-border-color: red;"); 
    }
}