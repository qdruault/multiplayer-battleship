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
import java.util.Enumeration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

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
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (isValid(iface)){
                    comboBox.getItems().add(iface);
                }
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
                getFacade().iDataIHMMain.setNetworkInterface(ifAddr);
                System.out.println(usedIf.getHostAddress());
                getIhmmain().toLogin();
            } catch (Exception e){}
        }   
    }

    
    private boolean isValid(NetworkInterface networkInt){
        Inet4Address usedIf;
        Boolean isValid = false;
        for(InterfaceAddress ifAddr : networkInt.getInterfaceAddresses()){
            try{
                usedIf = (Inet4Address) ifAddr.getAddress();
                getFacade().iDataIHMMain.setNetworkInterface(ifAddr);
                System.out.println(usedIf.getHostAddress());
                isValid = true;
            } catch (Exception e){}
        }
        return isValid;
    } 
}
