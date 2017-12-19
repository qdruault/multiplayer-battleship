/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.ihmmain.IHMMain;
import com.utclo23.ihmmain.facade.IHMMainFacade;
import java.io.ByteArrayInputStream;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;

/**
 * Upper class of all IHMMain controllers, contains IHMMain class.
 * @author Linxuhao
 */
public class AbstractController {
    /**
     * The reference of IHMMain used to jump between scenes.
     */
    private IHMMain ihmmain;
    private IHMMainFacade facade;
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

    public void setRunning(Boolean state) {
        isRunning = state;
    }
        
    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Override this to initialize your controller.
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
     * Override this method to refresh the page when isRunning is true.
     */
    public void refresh(){
    }

    /**
     * Displays an error popup.
     * Calls the generic showPopup method with the parameter AlertType.ERROR
     * @param title
     * @param header
     * @param message
     */
    public void showErrorPopup(String title, String header, String message){
        showPopup(title, header, message, Alert.AlertType.ERROR);
    }
    
    /**
     * Displays a success popup.
     * Calls the generic showPopup method with the parameter AlertType.INFORMATION
     * @param title
     * @param header
     * @param message
     */
    public void showSuccessPopup(String title, String header, String message){
        showPopup(title, header, message, Alert.AlertType.INFORMATION);
    }
    
    /**
     * Displays a popup of different types depending on the type parameter.
     * 
     * @param title
     * @param header
     * @param message
     * @param type
     */
    public void showPopup(String title, String header, String message, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.setResizable(false);
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("/styles/ihmmain.css").toExternalForm());
        alert.showAndWait();
    }
    
    /**
     * Call data's method to get the proper avatar image to display.
     * 
     * @return the currently connected user's avatar image.
     */
    protected Image retrievePlayerAvatar(){
        byte[] thumbnail = getFacade().iDataIHMMain.getMyPublicUserProfile().getLightPublicUser().getAvatarThumbnail();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(thumbnail);
        return new Image(inputStream);
    }
    
    /**
     * Call data's method to get the proper username to display.
     * 
     * @return the currently connected user's username.
     */
    protected String retrievePlayerUsername(){
        return getFacade().iDataIHMMain.getMyPublicUserProfile().getPlayerName();
    }
}
