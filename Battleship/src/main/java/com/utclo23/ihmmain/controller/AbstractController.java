/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.ihmmain.IHMMain;
import com.utclo23.ihmmain.facade.IHMMainFacade;
import java.io.ByteArrayInputStream;
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
    private Image playerAvatar;
    private String playerUsername;

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
     *
     * @param title
     * @param header
     * @param message
     */
    public void showErrorPopup(String title, String header, String message){
        showPopup(title, header, message, Alert.AlertType.ERROR);
    }
    
    public void showSuccessPopup(String title, String header, String message){
        showPopup(title, header, message, Alert.AlertType.INFORMATION);
    }
    
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

    protected Image retrievePlayerAvatar(){
        if (playerAvatar == null){
            byte[] thumbnail = getFacade().iDataIHMMain.getMyPublicUserProfile().getLightPublicUser().getAvatarThumbnail();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(thumbnail);
            playerAvatar = new Image(inputStream);
        }
        return playerAvatar;
    }
    
    protected String retrievePlayerUsername(){
        if (playerUsername == null){
            playerUsername = getFacade().iDataIHMMain.getMyPublicUserProfile().getPlayerName();
        } 
        return playerUsername;
    }
    
    protected void cleanPlayerAvatar(){
        playerAvatar = null;
        playerUsername = null;
    }
}
