/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Louis
 */
public class NetworkInterfaceChoiceController extends AbstractController {
    
    @FXML
    ComboBox comboBox;
        
    @Override
    public void start(){
        lookForInterface();
    }
    
    private void lookForInterface(){
        String ip;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                //RadioButton rb = new RadioButton(iface.getDisplayName());
                comboBox.getItems().add(iface);
                //System.out.println(iface.getDisplayName());

            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    
    @FXML 
    private void handleButtonValidate(ActionEvent event) throws Exception{

        Inet4Address usedIf;
        NetworkInterface chosenIf = (NetworkInterface)comboBox.getValue(); 
        for(InterfaceAddress ifAddr : chosenIf.getInterfaceAddresses()){
            try{
                usedIf = (Inet4Address) ifAddr.getAddress();
                facade.iDataIHMMain.setNetworkInterface(ifAddr);
                System.out.println(usedIf.getHostAddress());
                ihmmain.toLogin();
            } catch (Exception e){}
        }   
    }
    
    
}
