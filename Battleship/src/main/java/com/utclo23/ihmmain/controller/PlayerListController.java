/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.LightPublicUser;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * The GUI that displays the list of connected users
 * @author calvezlo
 */
public class PlayerListController extends AbstractController{

    @FXML
    private TableView<LightPublicUser> listPlayers;

    @FXML
    private void returnMenu(ActionEvent event) throws IOException{
        ihmmain.toMenu();
    }
    
    @FXML
    private void nextPage(ActionEvent event) throws IOException{
        //TODO: implement it        
    }
    
    @FXML
    private void lastPage(ActionEvent event) throws IOException{
        //TODO: implement it        
    }
    
     /* This function is called at the beginning of the application.
     * It loads the connected users and print them into the tableview.
     */
    @FXML
    @Override
    public void start(){
        // The first display : we create the tableview
        if(listPlayers.getColumns().isEmpty()){
            TableColumn idColumn = new TableColumn("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<LightPublicUser, String>("id"));
            idColumn.getStyleClass().add("cell-left");

            TableColumn nameColumn = new TableColumn("NAME");
            nameColumn.setCellValueFactory(new PropertyValueFactory<LightPublicUser, String>("playerName"));
            nameColumn.getStyleClass().add("cell-right");

            /* TODO Add this lines when data add avatar in LightPublicUser. Add avatarColum in listPlayers.getColumns().addAll(...);
            TableColumn avatarColumn = new TableColumn("AVATAR");
            avatarColumn.setCellValueFactory(new PropertyValueFactory<LightPublicUser, String>("avatarThumbnal"));*/

            listPlayers.getColumns().addAll(idColumn, nameColumn);

            // Columns take all the width of the window
            listPlayers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        
        // Load the connected users
        getConnectedUsers();
    }
    
    /**
     * This method is call by data module when a new player is connected
     */
    @Override
    public void refresh(){
        if(isRunning()){
            getConnectedUsers();
        }
    }
    
    /**
     * This function call the method of data to update the list of players
     */
    private void getConnectedUsers(){

        if(facade != null){
            // Call data method in order to collect connected users
            ArrayList<LightPublicUser> connectedUsers = new ArrayList<LightPublicUser>(facade.iDataIHMMain.getConnectedUsers());
            ObservableList<LightPublicUser> data = FXCollections.observableArrayList(connectedUsers);
        
            // Update the list in the GUI
            listPlayers.setItems(data);
        }

    }
    
    /**
     * This function is call when the user click on a line in the tableview
     * @param event 
     */
    @FXML
    public void clickItem(MouseEvent event){
        // TODO Call PlayerProfile to show the profile of the user.
        String id = listPlayers.getSelectionModel().getSelectedItem().getId();
    }
    
}
