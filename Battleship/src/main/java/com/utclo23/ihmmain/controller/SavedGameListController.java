/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.StatGame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * The GUI that displays the list of saved local games
 * @author calvezlo
 */
public class SavedGameListController extends AbstractController{
    
    
    @FXML
    private Button returnButton;
    @FXML
    private Button watchButton;
    @FXML
    private ScrollPane gameListPane;
    
    private StatGame selectedGame;
    
    private TableView<StatGame> gameList;
    
    /**
     * This function is called at the beginning of the application.
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

    private void addOnMoussClickEventOnGameList() {
        //add onMoussClicked event on table view
        gameList.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                Node node = ((Node) event.getTarget()).getParent();
                TableRow row;
                if(node instanceof TableView){
                }else{
                    if (node instanceof TableRow) {
                    row = (TableRow) node;
                } else {
                    // clicking on text part, parent is cell or row, cell's parent is the row
                    //so check here if parent is cell or row
                    if(node.getParent() instanceof TableRow){
                        row = (TableRow) node.getParent();
                    }else{
                        row = (TableRow) node.getParent().getParent();
                    }
                }
                StatGame selected = (StatGame)row.getItem();
                selectedGame = selected;
                }

            }
        });
    }

    private void createGameListTableView() {
        //add columns
        TableColumn nameColumn = new TableColumn("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.getStyleClass().add("cell-left");
        nameColumn.getStyleClass().add("label");
        
        TableColumn creatorColumn = new TableColumn("CREATOR");
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
        //setting the cell factory for the creator.playerName column  
        creatorColumn.getStyleClass().add("label");
        creatorColumn.setCellFactory(new Callback<TableColumn<StatGame, LightPublicUser>, TableCell<StatGame, LightPublicUser>>(){

            @Override
            public TableCell<StatGame, LightPublicUser> call(TableColumn<StatGame, LightPublicUser> param) {

                TableCell<StatGame, LightPublicUser> cell = new TableCell<StatGame, LightPublicUser>(){

                    @Override
                    protected void updateItem(LightPublicUser item, boolean empty) {
                        if (item != null) {
                            Label label = new Label(item.getPlayerName());
                            setGraphic(label);
                        }
                    }
                };
                return cell;                
            }
        });
        
        TableColumn modeColumn = new TableColumn("MODE");
        modeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        modeColumn.getStyleClass().add("label");
        
        TableColumn chatColumn = new TableColumn("CHAT");
        chatColumn.setCellValueFactory(new PropertyValueFactory<>("spectatorChat"));
        chatColumn.getStyleClass().add("label");
        
        TableColumn playerNumberColumn = new TableColumn("NUMBER PLAYER");
        playerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("LightPublicUser"));
        // ======== setting the cell factory for the creator.playerName column  
        playerNumberColumn.getStyleClass().add("cell-right");
        playerNumberColumn.getStyleClass().add("label");
        playerNumberColumn.setCellFactory(new Callback<TableColumn<StatGame, List<Player>>, TableCell<StatGame, List<Player>>>(){

            @Override
            public TableCell<StatGame, List<Player>> call(TableColumn<StatGame, List<Player>> param) {

                TableCell<StatGame, List<Player>> cell = new TableCell<StatGame, List<Player>>(){

                    @Override
                    protected void updateItem(List<Player> item, boolean empty) {
                        if (item != null) {
                            Label label = new Label(String.valueOf(item.size()));
                            setGraphic(label);
                        }
                    }
                };
                return cell;                
            }
        });
        
        gameList = new TableView<>();
        
        gameList.getColumns().addAll(nameColumn, creatorColumn, modeColumn, chatColumn, playerNumberColumn);
        gameList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * This function update the list of online games, it refreshes the display each time is called when this controller is running and is not loading
     */
    @Override
    public void refresh(){
        if(isRunning()){
            List<StatGame> newGameList = null;
            try{
                newGameList = getFacade().iDataIHMMain
            }catch(Exception e){
                e.printStackTrace();
            }
            ObservableList<StatGame> data = FXCollections.observableArrayList(newGameList);
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
    private void watchSelectedGame(ActionEvent event) {
        getFacade().iIHMTableToIHMMain.showSavedGameWithId(Integer.valueOf(selectedGame.getId()));
    }

    private void disableAllButtonsExceptReturn() {
        watchButton.setDisable(true);
    }
    
    private void enableAllButtons() {
        watchButton.setDisable(false);
        returnButton.setDisable(false);
    }

    /**
     * in order to not be infected the last time's result
     */
    private void resetValues() {
        selectedGame = null;
    }

}
