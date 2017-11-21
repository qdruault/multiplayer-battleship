/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.LightPublicUser;
import java.io.IOException;
import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
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
        
    }
    
    @FXML
    private void lastPage(ActionEvent event) throws IOException{
        
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
    public void refresh(){
        // Service allows the GUI to create a task
        final ScheduledService<Void> refreshService = new ScheduledService<Void>(){
        
            // Task is the task itself : it is the algorithm which run in background
            @Override
            protected Task<Void> createTask() {
                
                return new Task<Void>() {
                
                    @Override
                    protected Void call() throws Exception {

                        getConnectedUsers();

                        return null;
                    };
                };
            }
        };
        
        // Refresh the list of players every 3 seconds
        refreshService.setPeriod(Duration.seconds(3));
        
        // Launching the service which refresh the list of players
        refreshService.start();
        
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
