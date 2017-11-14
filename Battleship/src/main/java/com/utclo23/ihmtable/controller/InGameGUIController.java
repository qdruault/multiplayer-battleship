/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable.controller;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author CHEN Tong
 */
public class InGameGUIController {
    
    @FXML
    private Button buttonImage1;
    @FXML
    private Button buttonImage2;
    @FXML
    private Button buttonImage3;
    @FXML
    private Button buttonImage4;
    @FXML
    private Button buttonImage5;
    
    @FXML
    private Button fireButton;
    @FXML
    private Button menuButton;

    @FXML
    public void buttonAction(ActionEvent event) throws IOException {
       System.out.println("test for the button Image !");
       
    }
    
    @FXML
    public void fireAction(ActionEvent event) throws IOException {
        
    }
    
    @FXML
    public void menuAction(ActionEvent event) throws IOException {
        /*
          cette fonction permet de modifier l'interface vers MenuInterface
        */
    }
  
    
}
