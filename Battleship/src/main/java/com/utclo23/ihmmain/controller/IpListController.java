/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private Button validateButton;
    
    @FXML
    private Button addRemoveButton;
    
    @FXML
    private TextField ipAdressField;
    
    @FXML
    private TableView<ObservableIp> ipList;
    
    @FXML
    public void initialize(){
        TableColumn ipColumn = new TableColumn("IP");
        ipColumn.setCellValueFactory(new PropertyValueFactory<ObservableIp, String>("ipAdress"));
        
        ipList.getColumns().addAll(ipColumn);
               
        ipList.setEditable(true);
        
        // Columns take all the width of the window
        ipList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        getKnownIp();
    }
    
    @FXML
    private void validateList(ActionEvent event) throws IOException{
        // TODO : save the list with data
        
        ihmmain.toMenu();
    }
    
    @FXML
    private void addRemoveIpAdress(ActionEvent event) throws IOException{
        String ipAdress = ipAdressField.getText();
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
        
        // Update the list in the GUI
        ipList.setItems(data);
    }
    
    private void getKnownIp(){
        // Call data method in order to collect know ip
        // TODO call data method public getIPDiscovery() and remove the next line.
        ArrayList<ObservableIp> knownIp = new ArrayList<ObservableIp>();         
        ObservableList<ObservableIp> data = FXCollections.observableArrayList(knownIp);

        // Update the list in the GUI
        ipList.setItems(data);

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
