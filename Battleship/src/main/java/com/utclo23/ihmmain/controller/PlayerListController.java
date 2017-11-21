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
import javafx.util.Duration;

/**
 * The GUI that displays the list of connected users
 * @author calvezlo
 */
public class PlayerListController extends AbstractController{
    
    @FXML
    private Button lastButton;
    
    @FXML
    private Button nextButton;
    
    @FXML
    private Button returnButton;
    
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
    
    /**
     * This function is called at the beginning of the application.
     * It loads the connected users and print them into the tableview.
     */
    @FXML
    @Override
    public void start(){
        TableColumn idColumn = new TableColumn("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<LightPublicUser, String>("id"));
        idColumn.getStyleClass().add("cell-right");
        
        TableColumn nameColumn = new TableColumn("NAME");
        nameColumn.setCellValueFactory(new PropertyValueFactory<LightPublicUser, String>("playerName"));
        nameColumn.getStyleClass().add("cell-left");
        
        /* TODO Add this lines when data add avatar in LightPublicUser. Add avatarColum in listPlayers.getColumns().addAll(...);
        TableColumn avatarColumn = new TableColumn("AVATAR");
        avatarColumn.setCellValueFactory(new PropertyValueFactory<LightPublicUser, String>("avatarThumbnal"));*/
        
        listPlayers.getColumns().addAll(idColumn, nameColumn);
        
        // Columns take all the width of the window
        listPlayers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        getConnectedUsers();
        
        refresh();
    }
    
    /**
     * This function update the list of connected users.
     * It is launched at the beginning of the application and work in background
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
    
}
