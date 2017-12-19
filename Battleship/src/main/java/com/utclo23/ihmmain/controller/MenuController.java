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

/**
 * Show the list of buttons which allow to navigate in the application
 * @author Linxuhao a calvezlo
 */
public class MenuController extends AbstractController{

    /**
     * Go to the GUI which show the list of game and offer the possibility to create a game
     * @param event
     * @throws IOException 
     */
    @FXML
    private void play(ActionEvent event) throws IOException{
        getIhmmain().toGameList();
    }

    /**
     * Go to the GUI which show the profile
     * @param event
     * @throws IOException 
     */
    @FXML
    private void showProfile(ActionEvent event) throws IOException{
        getIhmmain().toPlayerProfile();
    }

    /**
     * Go to the GUI which show the player list
     * @param event
     * @throws IOException 
     */
    @FXML
    private void showPlayerList(ActionEvent event) throws IOException{
        getIhmmain().toPlayerList();
    }
    
    /**
     * Go to the GUI which show the list of saved games
     * @param event
     * @throws IOException 
     */
    @FXML
    private void toSavedGames(ActionEvent event) throws IOException{
        getIhmmain().toSavedGameList();
    }
    
    /**
     * Go to the GUI which show the list of IP
     * @param event
     * @throws IOException 
     */
    @FXML
    private void showIpList(ActionEvent event) throws IOException{
        getIhmmain().toIpList();
    }
    
    /**
     * Disconnect the current user and go to the view of login
     * @param event
     * @throws IOException 
     */
    @FXML
    private void disconnect(ActionEvent event)  throws IOException{
        try{
            getFacade().iDataIHMMain.signOut();
            getIhmmain().toLogin();
        }catch(Exception e){
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Exit the application (close the window)
     * @param event 
     */
    @FXML
    private void exit(ActionEvent event){
        System.exit(0);
    }
    
}
