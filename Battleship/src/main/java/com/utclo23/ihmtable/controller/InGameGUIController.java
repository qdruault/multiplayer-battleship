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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
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
    private Button menuButton;

    @FXML
    private GridPane opponentGrid;
    @FXML
    private Button btnFire;

    /**
     * The cell chosen to attack;
     */
    private Coordinate cellToAttack;

    /**
     * The pane of the previous attack.
     */
    private Pane clickedPane;

    @FXML
    private GridPane opponentGrid;

    @FXML
    public void buttonAction(ActionEvent event) throws IOException {
       System.out.println("test for the button Image !");

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
        // Prevent to click if the game is not started.
        if (facade.isGameReady()) {
            // Only if a cell has been aimed.
            if (cellToAttack != null) {
                // Remove the highlight on the cell.
                clickedPane.getStyleClass().removeAll("InGameGUI_selected_cell");
                // Attack!
                try {
                    if (facade.getFacadeData().attack(cellToAttack)) {
                        // TODO: OK.
                    } else {
                        // TODO: NOT OK.
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

            } else {
                System.err.println("No cell is selected!");
            }
        }
    }


    /**
     * Set the IHM Table facade.
     * @param facade : IHM Table facade.
     */
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
            // Prevent to click if the game is not started.
            if (facade.isGameReady()) {
                // Remove the higlight on the previous cell.
                if (clickedPane != null) {
                    clickedPane.getStyleClass().removeAll("InGameGUI_selected_cell");
                }

                // Save the cell to attack.
                cellToAttack = new Coordinate(column, row);
                // Highlight the cell.
                clickedPane = (Pane)event.getSource();
                clickedPane.getStyleClass().add("InGameGUI_selected_cell");
            }
        }
    }

    /**
     * Function for displaying new window with menu option (Save and leave)
     * @param event
     * @throws IOException
     */
    @FXML
    public void onClickMenuButton(MouseEvent event) throws IOException {
       System.out.println("Clic sur le bouton menu ");
       FXMLLoader menuLoader = new FXMLLoader();
       menuLoader.setLocation(getClass().getResource("/fxml/ihmtable/inGameGUIMenu.fxml"));
       try {
            Scene scene = new Scene((Parent) menuLoader.load(), 220, 300);
            Stage stage = new Stage();
            stage.setTitle("Pause");
            stage.setScene(scene);
            InGameGUIMenuController controller = menuLoader.<InGameGUIMenuController>getController();
            controller.setFacade(facade);
            stage.show();
       } catch (IOException ex) {
            Logger.getLogger(IHMTableFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
