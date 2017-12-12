/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.structure.LightPublicUser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * The GUI that displays the list of connected users.
 * @author calvezlo
 */
public class PlayerListController extends AbstractController{

    @FXML
    private TableView<LightPublicUser> listPlayers;

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
                    getIhmmain().toOthersPlayerProfile(id);
                } catch (IOException ex) {
                    Logger.getLogger(PlayerListController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }    
}
