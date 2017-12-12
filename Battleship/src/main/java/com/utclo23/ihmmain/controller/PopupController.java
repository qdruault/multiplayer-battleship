package com.utclo23.ihmmain.controller;

import com.utclo23.data.module.DataException;
import com.utclo23.ihmmain.constants.SceneName;
import javafx.fxml.FXML;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

/**
 * Generates a pop-up to enter and to send new info to update player profile.
 * According to the label transformed by interface player profile,
 * this controller will call the update function corresponding created by Data.
 * 
 * @author lipeining
 */
public class PopupController extends AbstractController{  
    
    public String label;
    @FXML
    private TextArea field;
    @FXML
    private void close(ActionEvent event) throws IOException{
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    private void update(ActionEvent event) throws IOException, DataException{
        String text;
        text = field.getText();
        switch(label){
            case "PlayerName":
                //facade.iDataIHMMain.updatePlayername(text);
                break;
            case "FirstName":
                //facade.iDataIHMMain.updateFirstname(text);
                break;
            case "LastName":
                //facade.iDataIHMMain.updateLastname(text);
                break;
            /* To-do: change popup*/
            case "Birthday":
                //facade.iDataIHMMain.updateLastname(text);
                break;
            case "Password":
                //facade.iDataIHMMain.updatePassword(text);
                break;
            default:
                Logger.getLogger(
                        PopupController.class.getName()).log(
                                Level.INFO,
                                "[PlayerProfile] - error update profile, attribut not found."
                        );
        }
        getIhmmain().controllerMap.get(SceneName.PLAYER_PROFILE.toString()).refresh();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    /**
     * 
     * @param attribut: tells which info the user would like to modify
     */
    public void setAttribut(String attribut){
        label = attribut; 
    }
}
