/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable;

import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.StatGame;
import com.utclo23.data.facade.IDataIHMTable;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.Message;
import com.utclo23.ihmmain.facade.IHMMainToIhmTable;
import com.utclo23.ihmtable.controller.InGameGUIController;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
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
    private final String FXML_PATH = "/fxml/ihmtable/InGame.fxml";

    /**
     * The Data facade.
     */
    private IDataIHMTable facadeData;

    /**
     * @return the facadeData
     */
    public IDataIHMTable getFacadeData() {
        return facadeData;
    }

    /**
     * @return the facadeIHMMain
     */
    public IHMMainToIhmTable getFacadeIHMMain() {
        return facadeIHMMain;
    }

    /**
     * The facade of IHM Main.
     */
    private IHMMainToIhmTable facadeIHMMain;

    /**
     * True if the 2 players are ready.
     * /!\ Reset to false when the game ends.
     */
    private boolean gameReady;
    
    /**
     * The controller of the main scene.
     */
    private InGameGUIController controller;

    /**
     * Constructor
     * @param iDataIHMtable Interface Data IHMTable
     */
    public IHMTableFacade(IDataIHMTable iDataIHMtable) {
        facadeData = iDataIHMtable;
        gameReady = false;
    }

    /**
     * Set a reference to the facade of IHM Main.
     * @param iHMMainTOIhmTable : interface of the facade of ihm main for ihm table.
     */
    public void setIhmMainLink(IHMMainToIhmTable iHMMainTOIhmTable) {
        facadeIHMMain = iHMMainTOIhmTable;
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
        controller.startReviewingGame(id);
    }

    /**
     * Launch a new game.
     * @param primaryStage Primary stage from IHMMain
     */
    @Override
    public void createInGameGUI(Stage primaryStage) {
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource(FXML_PATH));
        Parent pane;
        try {
            controller = new InGameGUIController();
            controller.setFacade(this);
            paneLoader.setController(controller);
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
     */
    @Override
    public void showGame() {
        System.out.println("TABLE: SHOWGAME START");
        //Créer la fenêtre
        FXMLLoader paneLoader = new FXMLLoader(getClass().getResource(FXML_PATH));
        Parent pane;
        Stage primaryStage = facadeIHMMain.getPrimaryStage();
        try {
            controller = new InGameGUIController();
            controller.setFacade(this);
            paneLoader.setController(controller);
            pane = paneLoader.load();
            Scene scene = new Scene(pane);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Game");
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(IHMTableFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       //Gérer les cas spectateur/utilisateur standard
        boolean startSpectateur = true;
        Game game = facadeData.getGame();
        LightPublicUser user = facadeData.getMyPublicUserProfile().getLightPublicUser();
        for(int i=0;i<game.getPlayers().size() && !startSpectateur;++i)
            if(game.getPlayers().get(i).getLightPublicUser().getId() == user.getId())
                startSpectateur = false;
        System.out.println(startSpectateur);
        if(startSpectateur && controller != null)
        {
           System.out.println("TABLE: SPECTATEUR ARRIVE");
           controller.refreshBoardForSpectator();
           controller.loadGame(game);
        }
        

            
    }

    /**
     * Notify that the two players are ready to play.
     */
    @Override
    public void notifyGameReady() {
        System.out.println("notifygameready");
        gameReady = true;
        // Notify the controller the game has started.
        controller.startGame();
    }

    /**
     * Display a message in the chat.
     * @param message : the message to display.
     */
    @Override
    public void printMessage(Message message) {
        controller.printMessageInChat(message.getSender().getPlayerName(), message.getContent()); 
    }

    /**
     * Show on the board if the shot has hit or not a ship.
     * @param coord : the coordinates of the hit.
     * @param touched : true if a ship is hit.
     * @param destroyedShip : destroyed ship or null.
     */
    @Override
    public void feedBack(final Coordinate coord, boolean touched, Ship destroyedShip) {
        
        final Coordinate coordFinal = coord;
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                System.out.println("TABLE: ON RECOIT UNE DEMANDE DE PLACEMENT DE MINE");
                 controller.placeMine(coordFinal,IHMTableFacade.this.getFacadeData().getGame().getRecentMine(coord).getOwner());
                //controller.displayOpponentAttack(coord, touched, destroyedShip);
                controller.timeToAttack();
                
            }
       
        });
    }

    /**
     * Display the new stats of the player.
     * @param stGame : the stats
     */
    @Override
    public void finishGame(StatGame stGame) {
        String sMessage;
        // Game lost.
        String winner = stGame.getWinner().getPlayerName();
        if(!winner.equals(facadeData.getMyPublicUserProfile().getPlayerName()))
        {
            sMessage = "Defeat! You should train against AI! Hahahah!";
        } else {
            // Check if the player is a spectator
            if(controller.isSpectator)
            {
                sMessage = winner.concat("won the game!");
            }else{
                // Game won.
                sMessage = "Victory! I'm proud of you General!";
            }
        }
        // Display popup.
        controller.displayFinishPopup(sMessage);
    }

    /**
     * The opponent has quit the game.
     */
    @Override
    public void opponentHasLeftGame() {
        if(controller.isSpectator)
            return;
        // Display popup.
        controller.displayFinishPopup("Your opponent has left this game!");
    }

    @Override
    public void spectatorLeaveGame()
    {
        try {
            this.getFacadeIHMMain().toMenu();
        } catch (IOException ex) {
            Logger.getLogger(IHMTableFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Connection has been lost with your opponent.
     */
    @Override
    public void connectionLostWithOpponent() {
        if(controller.isSpectator)
            return;
        controller.displayFinishPopup("Connection has been lost with your opponent");
    }

    /**
     * @return the gameReady
     */
    public boolean isGameReady() {
        return gameReady;
    }

    /**
     * @param pGameReady the gameReady to set
     */
    public void setGameReady(boolean pGameReady) {
        gameReady = pGameReady;
    }

}
