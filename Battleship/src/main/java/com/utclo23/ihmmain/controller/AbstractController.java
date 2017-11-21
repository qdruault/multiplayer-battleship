/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.ihmmain.IHMMain;
import com.utclo23.ihmmain.facade.IHMMainFacade;
import java.io.IOException;
/*import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;*/

/**
 * upper class of all ihm-main controller class, contain IHMMain class
 * @author Linxuhao
 */
public class AbstractController {
    /**
     * the reference of ihmmain, to jump between scenes
     */
    public IHMMain ihmmain;
    
    public IHMMainFacade facade;
    
    private boolean isRunning;

    public IHMMainFacade getFacade() {
        return facade;
    }

    public void setFacade(IHMMainFacade facade) {
        this.facade = facade;
    }

    public IHMMain getIhmmain() {
        return ihmmain;
    }

    public void setIhmmain(IHMMain ihmmain) {
        this.ihmmain = ihmmain;
    }

    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Override this to init your controller when you arrived on it
     */
    public void start(){
        
    }
    
    public void run(){
        this.isRunning = true;
    }
    
    public void stop(){
        this.isRunning = false;
    }
    
    /**
     * Override this method to refresh the page when isRunning is true
     * @throws IOException 
     */
    public void refresh() throws IOException {
    }

    /**
     * Displays an error popup.
     *
     * @param title
     * @param header
     * @param message
     */
    public void showErrorPopup(String title, String header, String message){
        /*Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.setResizable(false);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/ihmmain.css").toExternalForm());
        alert.showAndWait();*/
    }
}
