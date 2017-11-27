/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable.controller;

import com.utclo23.ihmtable.IHMTableFacade;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.ShipType;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;

/**
 *
 * @author CHEN Tong
 */
public class InGameGUIController {

    /**
     * IHMTable façade
     */
    IHMTableFacade facade;

    // Ships.
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
    private Button sendButton;
    @FXML
    private Button fireButton;
    @FXML
    private Button menuButton;

    // Boards.
    @FXML
    private GridPane opponentGrid;
    @FXML
    private GridPane playerGrid;

    @FXML
    private Label chronoLabel;

    @FXML
    private Button chronoButtonMenu;

    /*
        Starting value for chrono (like a constant)
    */
    private final int timePassed = 30;

    /*
        countdown value for chrono
    */
    private Integer countdown = timePassed;

    /*
        String that show in javafx
    */
    String labelTime = "";


    /**
     * The cell chosen to attack;
     */
    private Coordinate cellToAttack;

    /**
     * The pane of the previous attack.
     */
    private Pane clickedPane;

    /**
     * The button of the ship to be placed.
     */
    private Button clickedShip;

    /**
     * The selected ship to be placed.
     */
    private ShipType shipToPlace;

    /**
     * First bound of the ship to place.
     */
    private Coordinate startPosition;

    /**
     * The pane of the start position.
     */
    private Pane startPositionPane;

    /**
     * Second bound of the ship to place.
     */
    private Coordinate endPosition;

    /**
     * The pane of the end position.
     */
    private Pane endPositionPane;

    /**
     * The player ships.
     */
    private List<Ship> ships;

    /**
     * Set the IHM Table facade.
     * @param facade : IHM Table facade.
     */
    public void setFacade(IHMTableFacade facade) {
        this.facade = facade;
    }

    @FXML
    public void buttonAction(ActionEvent event) throws IOException {
       System.out.println("test for the button Image !");

    }

    public void refreshBoardForSpectator()
    {
        buttonImage1.setDisable(true);
        buttonImage2.setDisable(true);
        buttonImage3.setDisable(true);
        buttonImage4.setDisable(true);
        buttonImage5.setDisable(true);
        sendButton.setDisable(false);
        fireButton.setDisable(true);
        menuButton.setDisable(false);
        playerGrid.setDisable(false);
        opponentGrid.setDisable(false);
        
    }
    
    /**
     * First method called.
     */
    @FXML
    public void initialize() {
        // Fill in the opponent grid.
        for (int col = 0; col < opponentGrid.getColumnConstraints().size(); col++) {
            for (int row = 0; row < opponentGrid.getRowConstraints().size(); row++) {
                // Create an empty pane.
                Pane pane = new Pane();

                // Add a onClick event on it.
                pane.setOnMouseClicked(new AttackEvent(row, col));
                opponentGrid.add(pane, col, row);
            }
        }

        // Fill in the player grid.
        for (int col = 0; col < playerGrid.getColumnConstraints().size(); col++) {
            for (int row = 0; row < playerGrid.getRowConstraints().size(); row++) {
                // Create an empty pane.
                Pane pane = new Pane();

                // Add a CSS class to handle the hover effect.
                pane.getStyleClass().add("inGameGUI_player_cell");
                // Add the click event on it.
                pane.setOnMouseClicked(new ChooseCellEvent(row, col));
                playerGrid.add(pane, col, row);
            }
        }

        // Initialize the position of the ship to place.
        shipToPlace = null;
        startPosition = null;
        endPosition = null;

        // Get the ships.
        // TODO: ships = facade.getFacadeData().getShips();
        ships = new ArrayList<>();
        ships.add(new Ship(ShipType.BATTLESHIP, 2));
        ships.add(new Ship(ShipType.BATTLESHIP, 2));
        ships.add(new Ship(ShipType.CARRIER, 3));

        // Example ships.
        buttonImage1.setOnMouseClicked(new SelectShipEvent(ShipType.CARRIER));
        buttonImage2.setOnMouseClicked(new SelectShipEvent(ShipType.CRUISER));

        // Start chrono.
        chronoTimeInit();
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
                clickedPane.getStyleClass().removeAll("inGameGUI_selected_cell");
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
                    clickedPane.getStyleClass().removeAll("inGameGUI_selected_cell");
                }

                // Save the cell to attack.
                cellToAttack = new Coordinate(column, row);
                // Highlight the cell.
                clickedPane = (Pane)event.getSource();
                clickedPane.getStyleClass().add("inGameGUI_selected_cell");
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


    /**
     * Temporary function for starting chrono at 5:00 to 0:00
     * @param event
     */
    public void onClickChronoButton(MouseEvent event) {
        restartChronoTime();
    }

    /**
     * Function for initialize chrono
     */
    public void chronoTimeInit() {
        chronoLabel.setText("00:30");
        chronoLabel.setTextFill(Color.web("#FFFFFF"));
    }

    /**
     * Function for initialize chrono
     */
    public void restartChronoTime() {
        chronoTimeInit();
        timePass();
    }

    /**
     * Function for simulate chrono, using Timeline and KeyFrame import and set the label
     */
    private void timePass() {
        final Timeline time = new Timeline();
        time.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(1.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                countdown--;
                /*
                TODO: Expiration of timer, add function of ending turn
                */
                if (countdown <= 1) {
                    time.stop();
                    chronoLabel.setTextFill(Color.web("#c0392b"));
                }
                else {
                    if (countdown < 10) {
                        labelTime = "00:0" + countdown.toString();
                    } else {
                        labelTime = "00:" + countdown.toString();
                    }
                    chronoLabel.setText(labelTime);
                }
            }

        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }

    private class SelectShipEvent implements EventHandler {

        /**
         * The ship selected.
         */
        private ShipType shiptype;

        /**
         * Constructor
         * @param pShipType : the ship clicked.
         */
        public SelectShipEvent(ShipType pShipType) {
            shiptype = pShipType;
        }

        @Override
        public void handle(Event event) {
            // Prevent to click if the game is already started.
            if (!facade.isGameReady()) {
                // Remove the higlight on the previous cell.
                if (clickedShip != null) {
                    clickedShip.getStyleClass().removeAll("inGameGUI_selected_ship");
                }

                // Save the ship to move.
                shipToPlace = shiptype;
                // Highlight the ship.
                clickedShip = (Button)event.getSource();
                clickedShip.getStyleClass().add("inGameGUI_selected_ship");
            }
        }
    }

    private class ChooseCellEvent implements EventHandler {

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
        public ChooseCellEvent(int pRow, int pColumn) {
            row = pRow;
            column = pColumn;
        }

        @Override
        public void handle(Event event) {
            // Prevent to click if the game is not started and no ship is selected.
            if (!facade.isGameReady() && shipToPlace != null) {
                // First click.
                if (startPosition == null) {
                    startPosition = new Coordinate(column, row);
                    // Highlight the cell.
                    startPositionPane = (Pane)event.getSource();
                    startPositionPane.getStyleClass().add("inGameGUI_selected_cell");
                } else {
                    boolean suitableShip = false;
                    // Last click.
                    endPosition = new Coordinate(column, row);
                    // Highlight the cell.
                    endPositionPane = (Pane)event.getSource();
                    endPositionPane.getStyleClass().add("inGameGUI_selected_cell");
                    // Update the ship.
                    for (Ship ship : ships) {
                        // Search for the first ship of the right type not placed.
                        if (ship.getType() == shipToPlace && ship.getListCoord().isEmpty()) {
                            try {
                                // Ship founded.
                                suitableShip = true;
                                // Add the coordinates.
                                ship.getListCoord().add(startPosition);
                                ship.getListCoord().add(endPosition);

                                // Send the ship.
                                facade.getFacadeData().setShip(ship);
                                break;
                            } catch (Exception ex) {
                                Logger.getLogger(InGameGUIController.class.getName()).log(Level.SEVERE, null, ex);
                                // Wrong coordinates -> reset.
                                ship.getListCoord().clear();
                            }
                        }
                    }

                    // If no ship suits.
                    if (!suitableShip) {
                        System.out.println("No suitable ship for this type!");
                    }

                    // Clear the previous positions.
                    startPosition = null;
                    endPosition = null;
                    // Remove the highlight.
                    startPositionPane.getStyleClass().removeAll("inGameGUI_selected_cell");
                    endPositionPane.getStyleClass().removeAll("inGameGUI_selected_cell");
                }
            }
        }
    }

}
