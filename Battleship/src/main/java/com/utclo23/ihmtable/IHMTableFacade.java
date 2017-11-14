/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable;

import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.StatGame;
import com.utclo23.data.facade.IDataIHMTable;
import com.utclo23.ihmmain.controller.AbstractController;
import com.utclo23.ihmmain.facade.IHMMainToIhmTable;
import java.io.IOException;

import java.rmi.server.UID;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author pjeannot
 */
public class IHMTableFacade implements IIHMTableToIHMMain, IIHMTableToData {
    
    /**
     * The path of the inGameGUI.fxml.
     */
    private final String FXML_PATH = "/fxml/ihmtable/inGameGUI.fxml";
    
    /**
     * The Data facade.
     */
    private IDataIHMTable facadeData;
    
    /**
     * The facade of IHM Main.
     */
    private IHMMainToIhmTable facadeIHMMain;
    
    /**
     * Constructor 
     */
    public IHMTableFacade(IDataIHMTable iDataIHMtable) {
        this.facadeData = iDataIHMtable;
    }
    
    /**
     * Set a reference to the facade of IHM Main.
     * @param iHMMainTOIhmTable : interface of the facade of ihm main for ihm table.
     */
    public void setIhmMainLink(IHMMainToIhmTable iHMMainTOIhmTable) {
        this.facadeIHMMain = iHMMainTOIhmTable;
    }

    /**
     * The message of the exception thrown by unimplemented methods.
     */
    public static final String EXCEPTION_MESSAGE = "Not supported yet";
    
    /**
     * Replay a saved game.
     * @param id : id of the game.
     */
    @Override
    public void showSavedGameWithId(int id) {
        throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
    }

    /**
     * Launch a new game.
     */
    @Override
    public void createInGameGUI(Stage primaryStage) {
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource(FXML_PATH));
        Parent pane;
        try {
            pane = paneLoader.load();
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Game");
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(IHMTableFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Pause a playing saved game.
     */
    @Override
    public void stopTimer() {
        throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
    }

    /**
     * Join a current game.
     * @param guid : id of the game.
     */
    @Override
    public void showGame(UID guid) {
        throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
    }

    /**
     * Notify that the two players are ready to play.
     */
    @Override
    public void notifyGameReady() {
        throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
    }

    /**
     * Display a message in the chat.
     * @param message : the message to display.
     */
    @Override
    public void printMessage(String message) {
        throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
    }

    /**
     * Show on the board if the shot has hit or not a ship.
     * @param coord : the coordinates of the hit.
     * @param bool : true if a ship is hit.
     */
    @Override
    public void feedBack(Coordinate coord, boolean bool) {
        throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
    }

    /**
     * Display the new stats of the player.
     * @param stGame : ths stats
     */
    @Override
    public void finishGame(StatGame stGame) {
        throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
    }

    /**
     * The opponent has quit the game.
     */
    @Override
    public void opponentHasLeftGame() {
        throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
    }

    /**
     * Connection has been lost with your opponent.
     */
    @Override
    public void connectionLostWithOpponent() {
        throw new UnsupportedOperationException(EXCEPTION_MESSAGE);
    }
    
}
