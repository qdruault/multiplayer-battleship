/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.StatGame;
import com.utclo23.ihmmain.beans.StatGameBean;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * The controller of the GUI that displays the list of saved local games.
 * @author calvezlo
 */
public class SavedGameListController extends AbstractController{
    
    @FXML
    private Button returnButton;
    @FXML
    private Button watchButton;
    @FXML
    private ScrollPane gameListPane;
    private StatGameBean selectedGame;
    private TableView<StatGameBean> gameList;
    
    /**
     * Called at the beginning of the application.
     * It loads the connected users and print them into the tableview.
     */
    @Override
    public void start(){
        resetValues();
        createGameListTableView();
        addOnMoussClickEventOnGameList();
        enableAllButtons();
        gameListPane.setFitToWidth(true);
        gameListPane.setFitToHeight(true);
        refresh();
    }

    /**
     * Adds an onMouseClicked event on the table view.
     */
    private void addOnMoussClickEventOnGameList(){
        gameList.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Node node = ((Node) event.getTarget()).getParent();
                TableRow row;
                if(!(node instanceof TableView)){
                    if (node instanceof TableRow) {
                    row = (TableRow) node;
                } else {
                    //clicking on text part, parent is cell or row, cell's parent is the row
                    //so check here if parent is cell or row
                    if(node.getParent() instanceof TableRow){
                        row = (TableRow) node.getParent();
                    }else{
                        row = (TableRow) node.getParent().getParent();
                    }
                }
                StatGameBean selected = (StatGameBean)row.getItem();
                selectedGame = selected;
                }
            }
        });
    }

    /**
     * Create the table that displays the saved games.
     */
    private void createGameListTableView(){
        //add columns
        TableColumn nameColumn = new TableColumn("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("gameName"));
        nameColumn.getStyleClass().add("cell-left");
        nameColumn.getStyleClass().add("label");

        TableColumn winnerColumn = new TableColumn("WINNER");
        winnerColumn.setCellValueFactory(new PropertyValueFactory<>("winner"));

        //setting the cell factory for the creator.playerName column  
        winnerColumn.getStyleClass().add("label");
        
        TableColumn losserColumn = new TableColumn("PLAYER");
        losserColumn.setCellValueFactory(new PropertyValueFactory<>("losser"));

        //setting the cell factory for the creator.playerName column  
        losserColumn.getStyleClass().add("label");
        
        gameList = new TableView<>();
        gameList.getColumns().addAll(nameColumn, winnerColumn, losserColumn);
        gameList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * Updates the list of online games.
     * It refreshes the display each time this controller is running and is not loading.
     */
    @Override
    public void refresh(){
        if(isRunning()){
            List<StatGameBean> newGameList = new ArrayList<>();
            try{
                List<Game> savedGameList = getFacade().iDataIHMMain.getMyOwnerProfile().getSavedGamesList();
                for(Game game : savedGameList){
                    StatGame stat = game.getStatGame(); 
                    newGameList.add(new StatGameBean(stat));
                }
                if(savedGameList.isEmpty()){
                    PublicUser me = getFacade().iDataIHMMain.getMyPublicUserProfile();
                    StatGame fake = new StatGame();
                    fake.setWinner(me.getLightPublicUser());
                    fake.setName("Fake");
                    fake.setId("111111");
                    newGameList.add(new StatGameBean(fake));
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            ObservableList<StatGameBean> data = FXCollections.observableArrayList(newGameList);
            // Update the list in the GUI
            gameList.setItems(data);
            //create a new VBox to save table view
            gameListPane.setContent(gameList);
        }
    }
    
    @FXML
    private void returnMenu(ActionEvent event) throws IOException{
        getIhmmain().toMenu();
    }

    @FXML
    private void watchSelectedGame(ActionEvent event){
        try{
            getFacade().iIHMTableToIHMMain.showSavedGameWithId(Integer.valueOf(selectedGame.getGame().getId()));
        }catch(NullPointerException e){
            showErrorPopup("Error","Your request failed",e.getMessage());
            e.printStackTrace();
        }
    }

    private void disableAllButtonsExceptReturn(){
        watchButton.setDisable(true);
    }
    
    private void enableAllButtons(){
        watchButton.setDisable(false);
        returnButton.setDisable(false);
    }

    /**
     * Resets booleans of this controller.
     * Used in order not to be affected by the previous result.
     */
    private void resetValues(){
        selectedGame = null;
    }
}
