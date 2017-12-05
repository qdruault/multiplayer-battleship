/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.data.module.DataException;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Controller of the IP address list.
 * @author Loïc
 */
public class IpListController extends AbstractController{
    @FXML
    private TextField ipAddressField;
    
    @FXML
    private TableView<ObservableIp> ipList;
    
    @FXML
    @Override
    public void start(){
        if(ipList.getColumns().isEmpty()){
            TableColumn ipColumn = new TableColumn("IP");
            ipColumn.setCellValueFactory(new PropertyValueFactory<ObservableIp, String>("ipAddress"));
            ipColumn.getStyleClass().add("cell-left");
            ipColumn.getStyleClass().add("label");
            
            TableColumn deleteColumn = new TableColumn("REMOVE");
            deleteColumn.setCellValueFactory(new PropertyValueFactory<ObservableIp, Button>("remove"));
            deleteColumn.getStyleClass().add("cell-right");
            deleteColumn.getStyleClass().add("center");

            ipList.getColumns().addAll(ipColumn, deleteColumn);

            ipList.setEditable(true);

            // Columns take all the width of the window
            ipList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }
        getKnownIp();
    }
    
    /**
     * This function is call when the player click on the button "VALIDATE AND RETURN".
     * It allows to save the list of Ip address et return home.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void validateList(ActionEvent event) throws IOException{
        // ip address in the tableview in the GUI
        ObservableList<ObservableIp> data = ipList.getItems();
        
        List<String> discoveryNodes = new ArrayList<>();
        
        // we convert our ObservableIp in String for data
        for (int i = 0; i<data.size(); i++){
            discoveryNodes.add(data.get(i).getIpAddress());
        }
        try {
            // save the ip address
            getFacade().iDataIHMMain.setIPDiscovery(discoveryNodes);
        } catch (DataException ex) {
            Logger.getLogger(IpListController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ipAddressField.setText("");
            getIhmmain().toMenu();
        }
    }
    
    /**
     * This function is call when the player click on the button "ADD ADDRESS".
     * If the address is NOT in the list, the address is added.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void addIpAddress(ActionEvent event) throws IOException{
        String ipAddress = ipAddressField.getText();
        
        // Test : ip address is correct ?
        try{
            Inet4Address inet4address =  (Inet4Address) Inet4Address.getByName(ipAddress);
            
            ObservableList<ObservableIp> data = ipList.getItems();
            int index = -1;

            // We search the ip address in the actual list. If the ip address doesn't exist, index = -1; else, index = range of the ip address in the list
            for(int i = 0; i<data.size(); i++){
                if(data.get(i).getIpAddress().equals(ipAddress)){
                    index = i;
                }
            }
            if(index == -1){
                // add an ip address
                data.add(new ObservableIp(ipAddress));
                
                // Clean the textfield
                ipAddressField.setText("");
                
                // Update the list in the GUI
                ipList.setItems(data);
            }else{
                // TODO : open a pop up with the message : "This IP address already exists"
            }

        } catch (UnknownHostException e){
            // TODO : open a pop up with the message : "This IP address have a wrong format"
        }
    }
    
    /**
     * This function is called when the players clic on the button "REMOVE".
     * The ip address is removed from the list of ip
     * @param ipAddress the ip address which is deleted
     */
    private void removeIpAddress(String ipAddress){
        ObservableList<ObservableIp> data = ipList.getItems();
        
        int index = -1;

        // we search the ip address in the actual list.
        for(int i = 0; i<data.size(); i++){
            if(data.get(i).getIpAddress().equals(ipAddress)){
                index = i;
            }
        }
        
        data.remove(index);
        
        // Update the list in the GUI
        ipList.setItems(data);
    }
    
    /**
     * This function allows to update the list in the GUI with the saved ip address.
     */
    public void getKnownIp(){
        // Call data method in order to collect know ip
        if(getFacade() != null){
            ArrayList<ObservableIp> knownIp = new ArrayList<ObservableIp>();
            List<String> ipDiscovery = getFacade().iDataIHMMain.getIPDiscovery();
            
            for(int i = 0; i<ipDiscovery.size(); i++){
                knownIp.add(new ObservableIp((ipDiscovery.get(i))));
            }
            
            ObservableList<ObservableIp> data = FXCollections.observableArrayList(knownIp);

            // Update the list in the GUI
            ipList.setItems(data);
        }
    }
    
    /**
     * This class is mandatory for the tableview.
     */
    public class ObservableIp {
        private String ipAddress;
        private Button remove;
        
        public ObservableIp(String ipAddress){
            this.ipAddress = ipAddress;
            this.remove = new Button("REMOVE");
            
            this.remove.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    removeIpAddress(getIpAddress());
                }
            });
        }
        
        public String getIpAddress(){
            return ipAddress;
        }
        
        public Button getRemove(){
            return remove;
        }
    }
    
}
