/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author Linxuhao
 */
public class PlayerProfileController extends AbstractController{
    @FXML
    private Label label;
    @FXML
    private Button backButton;

    @FXML
    private void back(ActionEvent event) throws IOException{
        ihmmain.toMenu();
    }
    
    
}
