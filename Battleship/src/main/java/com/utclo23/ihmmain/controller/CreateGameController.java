/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;
import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;

/**
 * Controll of the createGame page.
 * 
 * @author Audrick
 */
public class CreateGameController extends AbstractController{
    ToggleGroup mode;
    ToggleGroup enemy;
    
    @FXML
    RadioButton radioButtonClassical;
    
    @FXML
    RadioButton  radioButtonBelge;
    
    @FXML
    RadioButton  radioButtonComputer;
    
    @FXML
    RadioButton  radioButtonPlayer;
    
    @FXML 
    CheckBox radioButtonAudience;
    
    @FXML 
    CheckBox radioButtonChat;
    
    @FXML
    TextField gameNameField;
    
    @FXML
    private ImageView avatarImageView;
    
   @FXML 
   private Label playerUsernameLabel;
    
    @FXML
    
    /**
     * This function is called at the oppening of the page.
     * It affects default values to Mode and Enemy and create the toggle groups
     */
    public void initialize(){
        // Toggle Group
        mode = new ToggleGroup();
        enemy = new ToggleGroup();
        
        radioButtonClassical.setToggleGroup(mode);
        radioButtonBelge.setToggleGroup(mode);
        radioButtonComputer.setToggleGroup(enemy);
        radioButtonPlayer.setToggleGroup(enemy);
        
        // Default values
        radioButtonClassical.setSelected(true);
        radioButtonComputer.setSelected(true);        
    }
    
    public void start(){   
        avatarImageView.setImage(super.retrievePlayerAvatar());
        playerUsernameLabel.setText(super.retrievePlayerUsername());
    }
    
    /**
     * Go to the previous page.
     * Called when the return button is clicked.
     * 
     * @param event
     * @throws IOException 
     */
    @FXML
    private void back(ActionEvent event) throws IOException{
        getIhmmain().toGameList();
    }
    /**
     * Creation of a new game.
     * Called when the Create button is clicked
     * 
     * @param event 
     */
    @FXML
    private void validateCreateGame(ActionEvent event){
        String names = gameNameField.getText();
        gameNameField.setStyle("");
        JOptionPane msg = new JOptionPane();
        if (!names.isEmpty()){
            GameType modes = (((RadioButton) mode.getSelectedToggle()).getText()).equals("CLASSIC") ? GameType.CLASSIC : GameType.BELGIAN;
            boolean enemys = (((RadioButton) enemy.getSelectedToggle()).getText()).equals("Computer");
            boolean chats = radioButtonChat.isSelected();
            boolean audiences = radioButtonAudience.isSelected();
            try{
                Game newGame = getFacade().iDataIHMMain.createGame(names, enemys, audiences, chats, modes);
                //msg.showMessageDialog(null, "Game created", "Information", JOptionPane.INFORMATION_MESSAGE);
                getFacade().iIHMTableToIHMMain.createInGameGUI(getIhmmain().primaryStage);

            }catch (DataException e){
                showErrorPopup("Error","Game not created",
                    "Try again !");
            }
        }
        else
            gameNameField.getStyleClass().add("borderError");
    }
}