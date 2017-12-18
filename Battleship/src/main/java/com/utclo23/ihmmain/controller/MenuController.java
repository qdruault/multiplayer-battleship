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
import javafx.scene.shape.Line;

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
    private Line lineDesign;
    @FXML
    private Button savedGameButton;

    @FXML
    private void play(ActionEvent event) throws IOException{
        getIhmmain().toGameList();
    }

    @FXML
    private void showProfile(ActionEvent event) throws IOException{
        getIhmmain().toPlayerProfile();
    }

    @FXML
    private void showPlayerList(ActionEvent event) throws IOException{
        getIhmmain().toPlayerList();
    }
    
    @FXML
    private void showIpList(ActionEvent event) throws IOException{
        getIhmmain().toIpList();
    }
    
    private void showCreateGame(ActionEvent event) throws IOException{
        getIhmmain().toCreateGame();
    }

    @FXML
    private void exit(ActionEvent event){
        getIhmmain().exit();
    }

    @FXML
    private void disconnect(ActionEvent event)  throws IOException{
        try{
            getFacade().iDataIHMMain.signOut();
            getIhmmain().toLogin();
        }catch(Exception e){
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    private void toSavedGames(ActionEvent event) throws IOException{
        getIhmmain().toSavedGameList();
    }
}
