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
public class LoginController extends AbstractController{
    @FXML
    private Label label;
    @FXML
    private Button loginButton;
    @FXML
    private Button createUserButton;

    @FXML
    private void login(ActionEvent event) throws IOException{
        ihmmain.toMenu();
    }

    @FXML
    private void createUser(ActionEvent event) throws IOException{
        ihmmain.toCreateUser();
    }
}
