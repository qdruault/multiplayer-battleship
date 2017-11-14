/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable.controller;

import com.utclo23.ihmtable.IHMTableFacade;
import com.utclo23.data.structure.Coordinate;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author CHEN Tong
 */
public class InGameGUIController {
    
    /**
     * IHMTable façade
     */
    IHMTableFacade facade;
    
    @FXML
    private Button buttonImage1;
    @FXML
    private Button buttonImage2;
    @FXML
    private Button buttonImage3;
    @FXML
    private Button buttonImage4;
    @FXML
    private Button buttonImage5;
    
    @FXML
    private Button fireButton;
    @FXML
    private Button menuButton;

    @FXML
    private GridPane opponentGrid;
    @FXML
    private Button btnFire;
    
    /**
     * The cell chosen to attack;
     */
    private Coordinate cellToAttack;
    
    @FXML
    public void buttonAction(ActionEvent event) throws IOException {
       System.out.println("test for the button Image !");
       
    }
    
    @FXML
    public void fireAction(ActionEvent event) throws IOException {
        
    }
    
    @FXML
    public void menuAction(ActionEvent event) throws IOException {
        /*
          cette fonction permet de modifier l'interface vers MenuInterface
        */
    }
    
    /**
     * First method called.
     */
    @FXML
    public void initialize() {
        // Fill in the grid.
        for (int col = 0; col < opponentGrid.getColumnConstraints().size(); col++) {
            for (int row = 0; row < opponentGrid.getRowConstraints().size(); row++) {
                // Create an empty pane.
                Pane pane = new Pane();
                
                // Add a onClick event on it.
                pane.setOnMouseClicked(new AttackEvent(row, col));
                opponentGrid.add(pane, col, row);
            }
        }
    }
    
    /**
     * Click on the "Fire" button.
     * @param event 
     */
    @FXML
    void onClickFire(MouseEvent event) {
        // Only if a cell has been aimed.
        if (cellToAttack != null) {
            System.out.println("Row: " + cellToAttack.getY() + " Col: " + cellToAttack.getX());
        } else {
            System.err.println("No cell is selected!");
        }
    }
   
    public void setFacade(IHMTableFacade facade) {
        this.facade = facade;
    }

    private class AttackEvent implements EventHandler {

        /**
         * The row to attack.
         */
        private int row;
        
        /**
         * The column to attack.
         */
        private int column;
        
        /**
         * Constructor
         * @param pRow: the row
         * @param pColumn: the column
         */
        public AttackEvent(int pRow, int pColumn) {
            row = pRow;
            column = pColumn;
        }
        
        @Override
        public void handle(Event event) {
            // Save the cell to attack.
            cellToAttack = new Coordinate(column, row);
        }
        
    }

}

