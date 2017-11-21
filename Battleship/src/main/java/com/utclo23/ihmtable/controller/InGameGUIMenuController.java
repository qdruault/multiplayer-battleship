/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable.controller;

import com.utclo23.ihmtable.IHMTableFacade;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author theohordequin
 */
public class InGameGUIMenuController {
    
    /**
     * IHMTable façade
     */
    IHMTableFacade facade;
    
    
    @FXML
    private Button inGameGUIContinue;
    
    @FXML
    private Button inGameGUIReview;

    @FXML
    private Button inGameGUIReturn;
    
    
    /**
     * setFacade for getting all interface functions
     * @param facade 
     */
    public void setFacade(IHMTableFacade facade) {
        this.facade = facade;
    }
    
    
    /**
     * Function for continuing the game, after a pause, internal function
     * @param event 
     */
    @FXML
    void onContinueGame(ActionEvent event) {
        System.out.print("Continue the game");
        Stage stage = (Stage) inGameGUIContinue.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Function for saving the game, call data interface function
     * @param event
     * @throws IOException 
     */
    @FXML
    private void onClickSave(MouseEvent event) throws IOException {
        System.out.print("Fonction save dans data (TODO)");
        
    }
    
    /**
     * Function for leaving the game, call IHM Main interface function
     * @param event
     * @throws IOException 
     */
    @FXML
    private void returnToMenu(MouseEvent event) throws IOException {
        System.out.print("Retour vers IHM Main");
        facade.getFacadeData().leaveGame();
        facade.getFacadeIHMMain().toMenu();
        
    }
}
