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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
/**
 *
 * @author Linxuhao
 */
public class PlayerProfileController extends AbstractController{
    @FXML
    private Label label;
    @FXML
    private TextField description;

    @FXML
    private Button backButton;
    @FXML
    private Button othersButton;
    @FXML
    private Button editDesc;

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
}
