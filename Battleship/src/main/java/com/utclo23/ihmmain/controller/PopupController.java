package com.utclo23.ihmmain.controller;

import javafx.fxml.FXML;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author lipeining
 */
public class PopupController extends AbstractController{  
    
    PlayerProfileController profile;
    @FXML
    private TextArea field;
    @FXML
    private void close(ActionEvent event) throws IOException{
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
    @FXML
    private void updateID(ActionEvent event) throws IOException{
        String text;
        text = field.getText();
        //facade.iDataIHMMain.updateUser(text, text, text, birthDate, text);
        //System.out.println(profile.testUserID);
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}