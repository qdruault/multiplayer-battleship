/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.StatGame;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

/**
 * The GUI that displays the list of connected users
 * @author calvezlo
 */
public class GameListController extends AbstractController{
    
    
    @FXML
    private Button returnButton;
    @FXML
    private Button joinButton;
    @FXML
    private Button createButton;
    @FXML
    private Button watchButton;
    @FXML
    private ScrollPane gameListPane;
    
    private StatGame selectedGame;
    
    private TableView<StatGame> gameList;

    @FXML
    private void returnMenu(ActionEvent event) throws IOException{
        ihmmain.toMenu();
    }

    @FXML
    private void joinSelectedGame(ActionEvent event) {
        //TODO
        System.out.println("Not supported yet");
    }

    @FXML
    private void createNewGame(ActionEvent event) throws IOException{
        ihmmain.toCreateGame();
    }

    @FXML
    private void watchSelectedGame(ActionEvent event) {
        //TODO
         System.out.println("Not supported yet");
    }
    
    
    /**
     * This function is called at the beginning of the application.
     * It loads the connected users and print them into the tableview.
     */
    @Override
    public void start(){
        //add columns
        TableColumn nameColumn = new TableColumn("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<StatGame, String>("name"));
        //nameColumn.getStyleClass().add("cell-left");
        
        TableColumn creatorColumn = new TableColumn("CREATOR");
        creatorColumn.setCellValueFactory(new PropertyValueFactory<StatGame, String>("creator"));
        //setting the cell factory for the creator.playerName column  
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
        modeColumn.setCellValueFactory(new PropertyValueFactory<StatGame, String>("type"));
        
        TableColumn chatColumn = new TableColumn("CHAT");
        chatColumn.setCellValueFactory(new PropertyValueFactory<StatGame, String>("spectatorChat"));
        
        TableColumn playerNumberColumn = new TableColumn("NUMBER PLAYER");
        playerNumberColumn.setCellValueFactory(new PropertyValueFactory<StatGame, List<Player>>("LightPublicUser"));
        // ======== setting the cell factory for the creator.playerName column  
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
        
        gameList = new TableView<StatGame>();
        
        gameList.getColumns().addAll(nameColumn, creatorColumn, modeColumn, chatColumn, playerNumberColumn);
        gameList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        gameListPane.setFitToWidth(true);
        gameListPane.setFitToHeight(true);
        
        refresh();
    }
    
    /**
     * This function update the list of online games, it refreshes the display each time is called
     */
    @Override
    public void refresh(){
        if(isRunning()){
            List<StatGame> newGameList = null;
            try{
                newGameList = facade.iDataIHMMain.getGameList();
            }catch(Exception e){
                e.printStackTrace();
            }
            if(gameList == null || newGameList.size() == 0){//if getGameList() is not implemented or not working as excepted
                newGameList = new ArrayList<StatGame>();
            }
            ObservableList<StatGame> data = FXCollections.observableArrayList(newGameList);
            // Update the list in the GUI
            gameList.setItems(data);
            //create a new VBox to save table view
            gameListPane.setContent(gameList);

        }
        
    }

}
