/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.PublicUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * The GUI that displays the list of connected users.
 * @author calvezlo
 */
public class PlayerListController extends AbstractController{

    @FXML
    private TableView<LightPublicUser> listPlayers;
    
    @FXML
    private ImageView avatarImageView;
    
    @FXML
    private Label playerUsernameLabel;
    
    private boolean isLoading;
    
    private PublicUser other;

    @FXML
    private void returnMenu(ActionEvent event) throws IOException{
        getIhmmain().toMenu();
    }
    
    /** 
     * This method is called at the beginning of the application.
     * It loads the connected users and print them into the tableview.
     */
    @FXML
    @Override
    public void start(){
        isLoading = false;
        other = null;
        // The first display : we create the tableview
        if(listPlayers.getColumns().isEmpty()){
            TableColumn idColumn = new TableColumn("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<LightPublicUser, String>("id"));
            idColumn.getStyleClass().add("cell-left");
            idColumn.getStyleClass().add("label");

            TableColumn nameColumn = new TableColumn("NAME");
            nameColumn.setCellValueFactory(new PropertyValueFactory<LightPublicUser, String>("playerName"));
            nameColumn.getStyleClass().add("cell-right");
            nameColumn.getStyleClass().add("label");

            listPlayers.getColumns().addAll(idColumn, nameColumn);

            // Columns take all the width of the window
            listPlayers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            
            avatarImageView.setImage(super.retrievePlayerAvatar());
            playerUsernameLabel.setText(super.retrievePlayerUsername());
        }
        
        // Load the connected users
        getConnectedUsers();
    }
    
    /**
     * This method is called by data module when a new player is connected.
     */
    @Override
    public void refresh(){
        if(isRunning()){
            getConnectedUsers();
        }
    }
    
    /**
     * This function calls the method of data to update the list of players.
     */
    private void getConnectedUsers(){

        if(getFacade() != null){
            // Call data method in order to collect connected users
            ArrayList<LightPublicUser> connectedUsers = new ArrayList<>(getFacade().iDataIHMMain.getConnectedUsers());
            ObservableList<LightPublicUser> data = FXCollections.observableArrayList(connectedUsers);
        
            // Update the list in the GUI
            listPlayers.setItems(data);
        }
    }
    
    /**
     * This function is called when the user click on a line in the tableview.
     * @param event 
     */
    @FXML
    public void clickItem(MouseEvent event){
        if(event.getClickCount() == 2){
            // Verify the list is not empty. If the list is empty, it's impossible to select an item
            if(listPlayers.getSelectionModel().getSelectedItem() != null){
                String id = listPlayers.getSelectionModel().getSelectedItem().getId();
                try {
                    getFacade().iDataIHMMain.askPublicUserProfile(id);
                    loading();
                } catch (IOException ex) {
                    Logger.getLogger(PlayerListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }    
    
    /**
     * This method is for receiving the profile of other player asked by user.
     * @param player: profile sent by Data for us to display
     * @throws IOException 
     */
    public void recievePublicUser(PublicUser player) throws IOException{
        if(isRunning()){
            isLoading = false;
            other = player;
        }
    }
    /**
     * This method is for waiting the profile.
     * As soon as receive the profile sent by Data, skip the loading and refresh the page.
     * @throws IOException 
     */
    public void loading() throws IOException{
        isLoading = true;
        if (isLoading){
            //change the cursor
            getIhmmain().primaryStage.getScene().setCursor(Cursor.WAIT);

            //create a wait task, check every 0.5s if the loading is finished, finish the waiting
            Task<Void> wait;
            wait = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    try {
                        while(isLoading){
                            Logger.getLogger(
                                    PlayerProfileController.class.getName()).log(
                                            Level.INFO, "Waiting."
                                    );
                            Thread.sleep(500);
                            PublicUser me = getFacade().iDataIHMMain.getMyPublicUserProfile();
                            recievePublicUser(me);
                        }
                    } catch (InterruptedException e) {
                    }
                    return null;
                }
            };
            //if loading succed, call the refresh();
            wait.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent event) {
                    try {
                        if(other != null){
                            getIhmmain().toOthersPlayerProfile(other);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(
                                PlayerProfileController.class.getName()).log(
                                        Level.SEVERE, null, ex);
                    }
                }
            });
            //if loading failed
            wait.setOnFailed(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent t){
                    getIhmmain().primaryStage.getScene().setCursor(Cursor.DEFAULT);
                    Logger.getLogger(
                            PlayerProfileController.class.getName()).log(
                                    Level.INFO,
                                    "Loading task failed : {0}", t.toString()
                            );
                }
            });
            new Thread(wait).start();
        }
    }
    
    @Override
    public void stop(){
        setRunning(false);
        isLoading = false;
    }
}
