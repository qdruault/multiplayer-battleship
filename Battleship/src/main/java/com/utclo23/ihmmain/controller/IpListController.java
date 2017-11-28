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
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Loïc
 */
public class IpListController extends AbstractController{
    @FXML
    private TextField ipAdressField;
    
    @FXML
    private TableView<ObservableIp> ipList;
    
    @FXML
    public void start(){
        if(ipList.getColumns().isEmpty()){
            TableColumn ipColumn = new TableColumn("IP");
            ipColumn.setCellValueFactory(new PropertyValueFactory<ObservableIp, String>("ipAdress"));
            ipColumn.getStyleClass().add("cell-center");
            ipColumn.getStyleClass().add("label");

            ipList.getColumns().addAll(ipColumn);

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
            discoveryNodes.add(data.get(i).getIpAdress());
        }

        try {
            // save the ip address
            facade.iDataIHMMain.setIPDiscovery(discoveryNodes);
        } catch (DataException ex) {
            Logger.getLogger(IpListController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ipAdressField.setText("");
            ihmmain.toMenu();
        }
    }
    
    /**
     * This function is call when the player click on the button "ADD/REMOVE ADDRESS".
     * If the address is in the list, the address is removed.
     * If the address is NOT in the list, the address is added.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void addRemoveIpAdress(ActionEvent event) throws IOException{
        String ipAdress = ipAdressField.getText();
        
        // test : ip adress is correct ?
        try{
            Inet4Address inet4address =  (Inet4Address) Inet4Address.getByName(ipAdress);
            
            ObservableList<ObservableIp> data = ipList.getItems();
            int index = -1;

            // we search the ip adress in the actual list. If the ip adress doesn't exist, index = -1; else, index = range of the ip adress in the list
            for(int i = 0; i<data.size(); i++){
                if(data.get(i).getIpAdress().equals(ipAdress)){
                    index = i;
                }
            }

            if(index == -1){
                // add an ip adress
                data.add(new ObservableIp(ipAdress));
            }else{
                // remove an ip adress
                data.remove(index);
            }
            
            ipAdressField.setText("");

            // Update the list in the GUI
            ipList.setItems(data);

        } catch (UnknownHostException e){
            // TODO : open a pop up with the message
        }
    }
    
    /**
     * This function allows to update the list in the GUI with the saved ip address
     */
    public void getKnownIp(){
        // Call data method in order to collect know ip
        if(facade != null){
            ArrayList<ObservableIp> knownIp = new ArrayList<ObservableIp>();
            List<String> ipDiscovery = facade.iDataIHMMain.getIPDiscovery();
            
            for(int i = 0; i<ipDiscovery.size(); i++){
                knownIp.add(new ObservableIp((ipDiscovery.get(i))));
            }
            
            ObservableList<ObservableIp> data = FXCollections.observableArrayList(knownIp);

            // Update the list in the GUI
            ipList.setItems(data);
        } else {
            Logger.getLogger(IpListController.class.getName()).log(Level.SEVERE, null, "facade is not instanciated");
        }
    }
    
    /**
     * This class is mandatory for the tableview.
     */
    public class ObservableIp {
        private String ipAdress;
        
        public ObservableIp(String ipAdress){
            this.ipAdress = ipAdress;
        };
        
        public String getIpAdress(){
            return ipAdress;
        }
    }
    
}
