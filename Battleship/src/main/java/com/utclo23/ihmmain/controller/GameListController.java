/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Player;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * The controller of the GUI that displays the list of online games.
 *
 * @author Linxuhao
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
    @FXML
    private ImageView avatarImageView;
    @FXML
    private Label playerUsernameLabel;
    
    private StatGame selectedGame;
    private TableView<StatGame> gameList;
    private boolean isLoading;
    private Game receivedGame; //game received with asynchronous load
    private boolean isGameSelected;
    
    /**
     * Method called at the beginning of the application.
     * It loads the connected users and print them into the tableview.
     */
    @Override
    public void start(){
        resetValues();
        createGameListTableView();
        addOnMoussClickEventOnGameList();
        enableAllButtons();
        selectedGame = null;
        gameListPane.setFitToWidth(true);
        gameListPane.setFitToHeight(true);
        avatarImageView.setImage(super.retrievePlayerAvatar());
        playerUsernameLabel.setText(super.retrievePlayerUsername());
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
                TableRow row = null;
                if(!(node instanceof TableView)){
                    if (node instanceof TableRow){
                        row = (TableRow) node;
                    } else{
                        //clicking on text part, parent is cell or row, cell's parent is the row
                        //so check here if parent is cell or row
                        if(node.getParent() instanceof TableRow){
                            row = (TableRow) node.getParent();
                        }else{
                            if(node.getParent().getParent() instanceof TableRow){
                                row = (TableRow) node.getParent().getParent();
                            } 
                        }
                    }
                    if(row != null){
                        StatGame selected = (StatGame)row.getItem();
                        selectedGame = selected;
                    }
                }
            }
        });
    }
    
    /**
     * Create the table of games.
     */
    private void createGameListTableView() {
        String labelClass = "label";
        //add columns
        TableColumn nameColumn = new TableColumn("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.getStyleClass().add("cell-left");
        nameColumn.getStyleClass().add(labelClass);
        
        TableColumn creatorColumn = new TableColumn("CREATOR");
        creatorColumn.setCellValueFactory(new PropertyValueFactory<>("creator"));
        //setting the cell factory for the creator.playerName column  
        creatorColumn.getStyleClass().add(labelClass);
        creatorColumn.setCellFactory(new Callback<TableColumn<StatGame, LightPublicUser>, TableCell<StatGame, LightPublicUser>>(){

            @Override
            public TableCell<StatGame, LightPublicUser> call(TableColumn<StatGame, LightPublicUser> param){
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
        modeColumn.getStyleClass().add(labelClass);
        
        TableColumn aiColumn = new TableColumn("AI");
        aiColumn.setCellValueFactory(new PropertyValueFactory<>("computerMode"));
        aiColumn.getStyleClass().add(labelClass);
        
        TableColumn chatColumn = new TableColumn("CHAT");
        chatColumn.setCellValueFactory(new PropertyValueFactory<>("spectatorChat"));
        chatColumn.getStyleClass().add(labelClass);
        
        TableColumn playerNumberColumn = new TableColumn("NUMBER PLAYER");
        playerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("LightPublicUser"));

        //setting the cell factory for the creator.playerName column  
        playerNumberColumn.getStyleClass().add("cell-right");
        playerNumberColumn.getStyleClass().add(labelClass);
        playerNumberColumn.setCellFactory(new Callback<TableColumn<StatGame, List<Player>>, TableCell<StatGame, List<Player>>>(){

            @Override
            public TableCell<StatGame, List<Player>> call(TableColumn<StatGame, List<Player>> param){
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
        gameList.getColumns().addAll(nameColumn, creatorColumn, modeColumn, aiColumn, chatColumn, playerNumberColumn);
        gameList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    /**
     * Updates the list of online games.
     * It refreshes the display each time this controller is running and is not loading.
     */
    @Override
    public void refresh(){
        if(isRunning() && !isLoading){
            List<StatGame> newGameList = new ArrayList<>();
            try{
                newGameList = getFacade().iDataIHMMain.getGameList();
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

    /**
     * Join the selected game in the table.
     * @param event
     */
    @FXML
    private void joinSelectedGame(ActionEvent event){
        if(selectedGame != null){
            if(!selectedGame.isComputerMode()){
                getFacade().iDataIHMMain.gameConnectionRequestGame(selectedGame.getId(), "player");
                loadingScreen();
            }else{
                showSuccessPopup("You can't join a party VS AI","Click on watch if you want to watch it","Create a game by yourself if you want to play VS AI");
            }
        }else{
            showSuccessPopup("Please select a game","Please select a game to join","Create a game or Find a friend and add his ip if you don't have any game in list");
        }
    }

    @FXML
    private void createNewGame(ActionEvent event) throws IOException{
        getIhmmain().toCreateGame();
    }

    @FXML
    private void watchSelectedGame(ActionEvent event){
        if(selectedGame != null){
            getFacade().iDataIHMMain.gameConnectionRequestGame(selectedGame.getId(), "spectator");
            loadingScreen();
        }else{
            showSuccessPopup("Please select a game","Please select a game to watch","Find a friend and add his ip if you don't have any game in list");
        }
    }
    
    /**
     * Displays the loading screen.
     * A loop is checking if loading is finished.
     * If so, it loads the receivedGame.
     */
    private void loadingScreen(){
        isLoading = true;
        disableAllButtonsExceptReturn();
        ProgressIndicator pin = new ProgressIndicator();
        pin.setProgress(-1);
        gameListPane.setContent(pin);

        //create a wait task, check every 0.5s if the loading is finished
        Task<Void> wait = new Task<Void>(){
            @Override
            protected Void call() throws Exception{
                try {
                    while(isLoading){
                        Thread.sleep(500);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
        //if loading succeeded, calls goIntoGame()
        wait.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle(WorkerStateEvent event){
                goIntoGame();
            }
        });
        //if loading failed
        wait.setOnFailed(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle(WorkerStateEvent t){
                //this function handle fail case too, so calling it when fails will give a fail message
                goIntoGame();
            }
    });
        new Thread(wait).start();
    }

    /**
     * Disables all buttons of the view, except the return one.
     * It is called when the screen is loading.
     */
    private void disableAllButtonsExceptReturn(){
        joinButton.setDisable(true);
        createButton.setDisable(true); 
        watchButton.setDisable(true);
    }
    
    /**
     * Enables all buttons of the view, except the return one.
     * It is called when start() and goIntoGame().
     */
    private void enableAllButtons(){
        joinButton.setDisable(false);
        createButton.setDisable(false); 
        watchButton.setDisable(false);
        returnButton.setDisable(false);
    }

    /**
     * Goes into game or displays an error message if the connection is impossible.
     */
    private void goIntoGame(){
        if(isRunning()){
            if(receivedGame != null){
                getFacade().iIHMTableToIHMMain.showGame();
            }else{
                showErrorPopup("Connection Impossible","","Your connection request failed.");
                refresh();
                enableAllButtons();
            }
        }
    }
    
    /**
     * Loads the game aynchronousely.
     * @param game 
     */
    public void receptionGame(Game game){
        if(isRunning()){
            receivedGame = game;
            isLoading = false;
        }
    }

    /**
     * Called by data when can't connect to a game.
     */
    public void connectionImpossible(){
        if(isRunning()){
            receivedGame = null;
            isLoading = false;
        }
    }

    /**
     * Resets the boolean values of the controller.
     * Used in order to not be affected by the previous result.
     */
    private void resetValues() {
        isLoading = false;
        selectedGame = null;
        receivedGame = null;
    }
    
    /**
     * Sets isLoading to false when leaving this controller.
     */
    @Override
    public void stop(){
        setRunning(false);
        isLoading = false;
    }
}
