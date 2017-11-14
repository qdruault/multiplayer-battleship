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

/**
 *
 * @author Linxuhao
 */
public class MenuController extends AbstractController{
    private Label label;
    @FXML
    private Button playButton;
    @FXML
    private Label title;
    @FXML
    private Button profileButton;
    @FXML
    private Button PlayerListButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button ipListButton;
    @FXML
    private Button disconnectButton;

    @FXML
    private void play(ActionEvent event) {
        facade.iIHMTableToIHMMain.createInGameGUI(ihmmain.primaryStage);
    }

    @FXML
    private void showProfile(ActionEvent event) throws IOException{
        ihmmain.toPlayerProfile();
    }

    @FXML
    private void showPlayerList(ActionEvent event) throws IOException{
        ihmmain.toPlayerList();
    }
    
    @FXML
    private void showIpList(ActionEvent event) throws IOException{
        ihmmain.toIpList();
    }

    @FXML
    private void exit(ActionEvent event) throws IOException{
        ihmmain.toLogin();
    }

    @FXML
    private void disconnect(ActionEvent event) {
        try{
            facade.iDataIHMMain.signOut();
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}
