/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *
 * @author Linxuhao
 */
public class MenuController extends AbstractController{
    @FXML
    private Button playButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button playerListButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button ipListButton;
    @FXML
    private Button disconnectButton;

    @FXML
    private void play(ActionEvent event) throws IOException{
        ihmmain.toGameList();
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
    private void showCreateGame(ActionEvent event) throws IOException{
        ihmmain.toCreateGame();
    }

    @FXML
    private void exit(ActionEvent event){
        System.exit(0);
    }

    @FXML
    private void disconnect(ActionEvent event)  throws IOException{
        try{
            facade.iDataIHMMain.signOut();
            ihmmain.toLogin();
        }catch(Exception e){
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
