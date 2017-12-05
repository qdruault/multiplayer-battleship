/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.utclo23.ihmtable.controller;

import com.utclo23.ihmtable.IHMTableFacade;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.ShipType;
import com.utclo23.ihmtable.structure.InGameStats;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
*
* @author CHEN Tong
*/
public class InGameGUIController {

    Map<ShipType, String> shipsPictures = new HashMap<ShipType, String>();

    /**1
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

    /**
    * List of all the panes of the grid.
    */
    private List<Pane> playerPanes;

    /**
    * List of all the panes of the grid.
    */
    private List<Pane> opponentPanes;

    @FXML
    private Label chronoLabel;

    @FXML
    private Button chronoButtonMenu;

    /*
    countdown value for chrono
    */
    private Integer countdown;

    /*
    String that show in javafx
    */
    String labelTime = "";


    @FXML
    private Text labelDeadShipCounter1;

    @FXML
    private Text labelDeadShipCounter2;

    @FXML
    private Text labelImpactCounter1;

    @FXML
    private Text labelImpactCounter2;

    @FXML
    private Text labelMineTotalCounter1;

    @FXML
    private Text labelMineTotalCounter2;

    /**
    * The cell chosen to attack;
    */
    private Coordinate cellToAttack;

    // Box in bottom right corner
    @FXML
    private HBox actionPanel;

    
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
    * True if the two players are ready to play.
    */
    private boolean gameStarted;

    /**
    * Map of the btn ship
    */
    private Map<ShipType,Button> mapBtnType;

    /*
    Map reccording the number of ships
    */
    private Map<ShipType,Integer> mapShipCount;

    /**
    * True if the player is ready to fire.
    */
    private boolean readyToAttack;

    /**
    * Number of turns where the player didn't play.
    */
    private int nbPassedTurns;

    /**
    * Current player's stats
    */
    private InGameStats currentPlayerStats;

    /**
<<<<<<< HEAD
    * Opponent's stats
    */
    private InGameStats opponentStats;

    /**
     * The current player.
     */
    private Player currentPlayer;

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

    /**
     * Disable all the useless items.
     */
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
        // Map of the ship pictures links.
        shipsPictures.put(ShipType.BATTLESHIP, "images/ship1.png");
        shipsPictures.put(ShipType.CARRIER,    "images/ship2.png");
        shipsPictures.put(ShipType.CRUISER,    "images/ship3.png");
        shipsPictures.put(ShipType.DESTROYER,  "images/ship4.png");
        shipsPictures.put(ShipType.SUBMARINE,  "images/ship5.png");

        // Player not able to fire
        readyToAttack = false;
        // Fill in the opponent grid.
        opponentPanes = new ArrayList<>();
        for (int col = 0; col < opponentGrid.getColumnConstraints().size(); col++) {
            for (int row = 0; row < opponentGrid.getRowConstraints().size(); row++) {
                // Create an empty pane.
                Pane pane = new Pane();

                // Add it to the list to store it.
                opponentPanes.add(pane);
                // Add a onClick event on it.
                pane.setOnMouseClicked(new AttackEvent(row, col));
                opponentGrid.add(pane, col, row);
            }
        }

        // Fill in the player grid.
        playerPanes = new ArrayList<>();
        for (int col = 0; col < playerGrid.getColumnConstraints().size(); col++) {
            for (int row = 0; row < playerGrid.getRowConstraints().size(); row++) {
                // Create an empty pane.
                Pane pane = new Pane();

                // Add it to the list to store it.
                playerPanes.add(pane);
                // Add a CSS class to handle the hover effect.
                pane.getStyleClass().add("inGameGUI_hover_cell");
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
        ships.add(new Ship(ShipType.BATTLESHIP, 4));
        ships.add(new Ship(ShipType.BATTLESHIP, 4));
        ships.add(new Ship(ShipType.CARRIER, 4));
        ships.add(new Ship(ShipType.CRUISER, 3));
        ships.add(new Ship(ShipType.DESTROYER, 2));
        ships.add(new Ship(ShipType.SUBMARINE, 2));

        ships.add(new Ship(ShipType.BATTLESHIP, 4));
        ships.add(new Ship(ShipType.BATTLESHIP, 4));
        ships.add(new Ship(ShipType.CARRIER, 4));
        ships.add(new Ship(ShipType.CRUISER, 3));
        ships.add(new Ship(ShipType.DESTROYER, 2));
        ships.add(new Ship(ShipType.SUBMARINE, 2));

        //Add manually the button to set them up
        mapBtnType = new HashMap<>();
        mapBtnType.put(ShipType.BATTLESHIP, buttonImage1);
        mapBtnType.put(ShipType.CARRIER, buttonImage2);
        mapBtnType.put(ShipType.CRUISER, buttonImage3);
        mapBtnType.put(ShipType.DESTROYER, buttonImage4);
        mapBtnType.put(ShipType.SUBMARINE, buttonImage5);

        //Map to count the number of ship / type
        mapShipCount = new HashMap<>();
        mapShipCount.put(ShipType.CARRIER,0);
        mapShipCount.put(ShipType.BATTLESHIP,0);
        mapShipCount.put(ShipType.CRUISER,0);
        mapShipCount.put(ShipType.DESTROYER,0);
        mapShipCount.put(ShipType.SUBMARINE,0);

        //Fill the mapShipCount from the list of ships obtained from Data
        for(int i = 0; i<ships.size();i++)
        {
            mapShipCount.put(ships.get(i).getType(),mapShipCount.get(ships.get(i).getType())+1);
        }

        //Set the number of available ship in the label of the buttons
        mapBtnType.get(ShipType.CARRIER).setText(mapShipCount.get(ShipType.CARRIER).toString());
        System.out.println("CARRIER : " + mapBtnType.get(ShipType.CARRIER).getText());
        mapBtnType.get(ShipType.BATTLESHIP).setText(mapShipCount.get(ShipType.BATTLESHIP).toString());
        System.out.println("BATTLE : " + mapBtnType.get(ShipType.BATTLESHIP).getText());
        mapBtnType.get(ShipType.CRUISER).setText(mapShipCount.get(ShipType.CRUISER).toString());
        System.out.println("CRUISER : " + mapBtnType.get(ShipType.CRUISER).getText());
        mapBtnType.get(ShipType.DESTROYER).setText(mapShipCount.get(ShipType.DESTROYER).toString());
        System.out.println("DESTROYER : " + mapBtnType.get(ShipType.DESTROYER).getText());
        mapBtnType.get(ShipType.SUBMARINE).setText(mapShipCount.get(ShipType.SUBMARINE).toString());
        System.out.println("SUBMARINE : " + mapBtnType.get(ShipType.SUBMARINE).getText());

        // Example ships.
        buttonImage1.setOnMouseClicked(new SelectShipEvent(ShipType.BATTLESHIP));
        buttonImage2.setOnMouseClicked(new SelectShipEvent(ShipType.CARRIER));
        buttonImage3.setOnMouseClicked(new SelectShipEvent(ShipType.CRUISER));
        buttonImage4.setOnMouseClicked(new SelectShipEvent(ShipType.DESTROYER));
        buttonImage5.setOnMouseClicked(new SelectShipEvent(ShipType.SUBMARINE));

        // Start chrono.
        chronoTimeInit();

        // Init the number of turns passed.
        nbPassedTurns = 0;

        // Init current player's stats of the match
        currentPlayerStats = new InGameStats();
        // Init opponent's stats of the match
        opponentStats = new InGameStats();
        // Init pannel with values
        updateStatsPannel();
        // Get the current player.
        currentPlayer = facade.getFacadeData().getGame().getCurrentPlayer();

    }

    /**
    * Method called when notifyGameReady() is called.
    */
    public void startGame() {
        // To know that the game is started.
        gameStarted = true;

        // To know whose turn it is.
        if (facade.getFacadeData().getGame().getCurrentPlayer().getLightPublicUser() == facade.getFacadeData().getMyPublicUserProfile().getLightPublicUser()) {
            // I'm the first player to play.
            readyToAttack = true;
        } else {
            // I'm not the first player to play.
            readyToAttack = false;
        }

        // We can no longer hover the player panes.
        for (Pane playerPane : playerPanes) {
            playerPane.getStyleClass().removeAll("inGameGUI_hover_cell");
        }

        // We can now hover the opponent panes.
        for (Pane opponentPane : opponentPanes) {
            opponentPane.getStyleClass().add("inGameGUI_hover_cell");
        }

        // Start the timer if it is my turn.
        if(readyToAttack) {
            restartChronoTime();
        }
    }

    /**
    * Function for switch different pane in starting turn for the current player
    */
    public void switchOpponnentPane() {
        readyToAttack = !readyToAttack;
        if (readyToAttack) {
            // We can now hover the opponent panes.
            for (Pane opponentPane : opponentPanes) {
                opponentPane.getStyleClass().add("inGameGUI_hover_cell");
            }
        } else {
            for (Pane opponentPane : opponentPanes) {
                opponentPane.getStyleClass().removeAll("inGameGUI_hover_cell");
            }
        }
    }

    /**
    * Update the label on the ship button by adding the specified value
    * Param ShipType
    */
    void updateShipButton(ShipType type, int value)
    {
        try {
            mapShipCount.replace(type, mapShipCount.get(type) + value);
            mapBtnType.get(type).setText(mapShipCount.get(type).toString());
        } catch (Exception e) {
            //Pour une raison obscure, les types apparaissent par moments en francais quand un merge est fait avec DATA
            System.out.println("The shipType key isn't found in the map");
        }
        buttonImage1.setOnMouseClicked(new SelectShipEvent(ShipType.CARRIER));
        buttonImage2.setOnMouseClicked(new SelectShipEvent(ShipType.CRUISER));
    }

    /**
    * Update stats of the current player and the opponent in the bottom left pannel
    */
    void updateStatsPannel() {
        labelDeadShipCounter1.setText(currentPlayerStats.getDeadShipCounter());
        labelImpactCounter1.setText(currentPlayerStats.getImpactCounter());
        labelMineTotalCounter1.setText(currentPlayerStats.getMineTotalCounter());
        labelDeadShipCounter2.setText(opponentStats.getDeadShipCounter());
        labelImpactCounter2.setText(opponentStats.getImpactCounter());
        labelMineTotalCounter2.setText(opponentStats.getMineTotalCounter());
    }

    /**
    * Click on the "Fire" button.
    * @param event
    */
    @FXML
    void onClickFire(MouseEvent event) {
        // Prevent to click if the game is not started and/or turn of the current player
        if (gameStarted && readyToAttack) {
            // Only if a cell has been aimed.
            if (cellToAttack != null) {
                // Remove the highlight on the cell.
                clickedPane.getStyleClass().removeAll("inGameGUI_selected_cell");
                // Attack!
                try {
                    //Send the attack signal to data, fetch the mine, place it on the grid
                    // (the better would be for data to have the boolean information
                    // in a Mine returned with attack)

                    facade.getFacadeData().attack(cellToAttack, true);
                    placeMine(cellToAttack,currentPlayer);

                    // Reinitialize chrono for the next turn
                    chronoTimeInit();
                    switchOpponnentPane();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
            System.out.println("onclickfire " + cellToAttack.getX() + " " + cellToAttack.getY());
        }
    }



    /**
    * Function for set the attack after feedback call by Data
    */
    public void timeToAttack() {
        switchOpponnentPane();
        restartChronoTime();
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

    /**
     * Get the Node in the grid according to its positions
     * @param row
     * @param column
     * @param gridPane
     * @return
     */
    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane)
    {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(node.hasProperties())
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    /**
     * Generic method for placing a mine on the grid just with the coordinates
     * @param c
     * @param p
     */
    private void placeMine(Coordinate c, Player p)
    {
        // Select the right grid which depends on the player (TODO spectateur?)
        GridPane grid;
        if(p.getLightPublicUser().getId().equals(facade.getFacadeData().getMyPublicUserProfile().getId())) {
            grid = playerGrid;
        } else {
            grid = opponentGrid;
        }


        Node n = getNodeByRowColumnIndex(c.getX(), c.getY(), grid);
        n.setDisable(true);


        Pair<Integer, Ship> attack_result = facade.getFacadeData().attack(c, false);
        //TODO: Voir si il faut demander à data une méthode "attack" neutralisée,
        // on a besoin de pouvoir tester si une mine placée à un endroit provoque
        // une explosion sans aucun autre effet (ou dire à data de tester si le joueur
        // en local est le currentPlayer)
        if (attack_result.getKey() == 1) {
            // Ship Touched!
            n.getStyleClass().add("inGameGUI_touched_cell");
            // TODO: check if the ship is destroyed.
            if(attack_result.getValue() != null) //ship destroyed here
            {
                Ship destroyedShip = attack_result.getValue();
            }

            // Reset the number of turns passed.
            nbPassedTurns = 0;
        } else {
            // Ship missed!
            n.getStyleClass().add("inGameGUI_missed_cell");
        }
    }

    /**
     * Generic method for placing a mine on the grid
     * Data gives us the mine object and the player
     */
    private void placeMine(Mine m, Player p)
    {
        placeMine(m.getCoord(), p);
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
            if (gameStarted && readyToAttack) {
                // Remove the higlight on the previous cell.
                if (clickedPane != null) {
                    clickedPane.getStyleClass().removeAll("inGameGUI_selected_cell");
                }

                // Save the cell to attack.
                cellToAttack = new Coordinate(column, row);
                // Highlight the cell.
                clickedPane = (Pane)event.getSource();
                clickedPane.getStyleClass().add("inGameGUI_selected_cell");

                // Save the cell to attack.
                cellToAttack = new Coordinate(column, row);
                // Highlight the cell.
                clickedPane = (Pane)event.getSource();
                clickedPane.getStyleClass().add("inGameGUI_selected_cell");
            }
        }
    }

    /**
    * Function for initialize chrono
    */
    private void chronoTimeInit() {
        if (nbPassedTurns == 0) {
            countdown = 30;
            chronoLabel.setText("00:30");
            chronoLabel.setTextFill(Color.web("#FFFFFF"));
        } else if (nbPassedTurns == 1) {
            countdown = 15;
            chronoLabel.setText("00:15");
            chronoLabel.setTextFill(Color.web("#FFFFFF"));
        } else {
            countdown = 5;
            chronoLabel.setText("00:05");
            chronoLabel.setTextFill(Color.web("#c0392b"));
        }
    }

    /**
    * Function for initialize chrono
    */
    private void restartChronoTime() {
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
                // To prevent the timer when it'snot my turn.
                if (readyToAttack) {
                    countdown--;
                    // Time's up!
                    if (countdown <= 0) {
                        time.stop();
                        chronoLabel.setText("00:00");

                        // Leave the game if te player has not played for the 3rd time.
                        if (nbPassedTurns == 2) {
                            facade.getFacadeData().leaveGame();
                        } else {
                            // Fake an attack.
                            placeMine(new Coordinate(-1, -1), currentPlayer);

                            // Increase the number of turns passed.
                            nbPassedTurns++;

                            // Reinitialize chrono for the next turn
                            chronoTimeInit();
                            switchOpponnentPane();

                            // TODO: Remove this line!
                            timeToAttack();
                        }
                    }
                    else {
                        if (countdown < 10) {
                            chronoLabel.setTextFill(Color.web("#c0392b"));
                            labelTime = "00:0" + countdown.toString();
                        } else {
                            labelTime = "00:" + countdown.toString();
                        }
                        chronoLabel.setText(labelTime);
                    }
                }
            }

        });
        time.getKeyFrames().add(frame);
        time.playFromStart();
    }
    
    /**
     * Generic private method for setting anchor pane in right place
     * @param n anchorPane selected
     * @param c button in the anchor selected
     */
    private void setAnchorEach(AnchorPane anchor, Button butt) {
        anchor.setTopAnchor(butt, 0.0);
        anchor.setLeftAnchor(butt, 0.0);
        anchor.setRightAnchor(butt, 0.0);
        anchor.setBottomAnchor(butt, 0.0);
        anchor.getChildren().add(butt);
    }
    
    /**
     * Method for clearing the corner and put the right buttons (forward...)
     */
    private void prepareReviewGame() {
        /**
         * Create anchorPane for layout 
         */
        AnchorPane backWardPane = new AnchorPane();
        AnchorPane playPane = new AnchorPane();
        AnchorPane pausePane = new AnchorPane();
        AnchorPane forwardPane = new AnchorPane();
        
        /**
         * Prepare image in each button 
         */
        final ImageView backwardImage = new ImageView("images/fleche_backward.png");
        final ImageView playImage = new ImageView("images/play_button.png");
        final ImageView pauseImage = new ImageView("images/pause_button.png");
        final ImageView forwardImage = new ImageView("images/fleche_forward.png");
        
        /**
         * Style on each button
         */
        String styleNoHover = ("-fx-background-color: none;" +
            "    -fx-cursor: pointer;" +
            "    -fx-background-repeat: stretch;" +
            "    -fx-background-position: center center;");
        
        /**
         * Backward button preparation : set image + style + binding + onAction
         */
        Button backWardButton = new Button();
        backWardButton.setGraphic(backwardImage);
        backWardButton.getStyleClass().add("reviews");
        backWardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                facade.getFacadeData().getPreviousBoard();
            }
        });
        
        Button playButton = new Button();
        playButton.setGraphic(playImage);
        playButton.getStyleClass().add("reviews");
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                facade.getFacadeData().getNextBoard();
            }
        });
        
        Button pauseButton = new Button();
        pauseButton.setGraphic(pauseImage);
        pauseButton.getStyleClass().add("reviews");
        backWardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                /*
                TODO: Pause
                */
            }
        });
        
        Button forwardButton = new Button();
        forwardButton.setGraphic(forwardImage);
        forwardButton.getStyleClass().add("reviews");
        backWardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                facade.getFacadeData().getNextBoard();
            }
        });
        
        /**
         * Create the right pane with their button for each
         */
        setAnchorEach(backWardPane, backWardButton);
        setAnchorEach(playPane, playButton);
        setAnchorEach(pausePane, pauseButton);
        setAnchorEach(forwardPane, forwardButton);
        
        /**
         * Create the right general pane, clearing "playing" option
         */
        actionPanel.getChildren().clear();
        actionPanel.getChildren().add(backWardPane);
        actionPanel.setHgrow(backWardPane, Priority.ALWAYS);
        actionPanel.getChildren().add(playPane);
        actionPanel.setHgrow(playPane, Priority.ALWAYS);
        actionPanel.getChildren().add(pausePane);
        actionPanel.setHgrow(pausePane, Priority.ALWAYS);
        actionPanel.getChildren().add(forwardPane);
        actionPanel.setHgrow(forwardPane, Priority.ALWAYS);
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
            if (!gameStarted) {
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
            if (!gameStarted && shipToPlace != null) {
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
                                // No exception : Place the ship on the board.
                                // Load the image.
                                ImageView shipOnTheGrid = new ImageView(shipsPictures.get(ship.getType()));
                                if (ship.getListCoord().get(0).getY() == ship.getListCoord().get(1).getY()) {
                                    // Horizontal.
                                    // Set the size.
                                    shipOnTheGrid.setFitWidth(playerGrid.getWidth()/10.0 * ship.getSize());
                                    shipOnTheGrid.setFitHeight(playerGrid.getHeight()/10.0);
                                    // Place on the grid.
                                    playerGrid.add(shipOnTheGrid, Math.min(ship.getListCoord().get(0).getX(), ship.getListCoord().get(1).getX()), ship.getListCoord().get(0).getY(), ship.getSize(), 1);
                                } else {
                                    // Vertical
                                    // Set the size.
                                    shipOnTheGrid.setFitHeight(playerGrid.getWidth()/10.0);
                                    shipOnTheGrid.setFitWidth(playerGrid.getHeight()/10.0 * ship.getSize());
                                    // Rotate the image.
                                    shipOnTheGrid.setRotate(90);
                                    // Place on the grid.
                                    playerGrid.add(shipOnTheGrid, ship.getListCoord().get(0).getX(), Math.min(ship.getListCoord().get(0).getY(), ship.getListCoord().get(1).getY()), 1, ship.getSize());
                                }

                                // ATTENTION! Grid size is out of control!
                                // setShip didn't return any exception so the ship is correctly placed -> Update the label on the left panel
                                updateShipButton(shipToPlace, -1);
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


    /**
    * Method for loading a game
    * @param game Game object (from saved game or for a second player / spectator)
    */
    public void loadGame(Game game)
    {
        // Place ships.
        for (Player player : game.getPlayers()) {
            for (Ship ship : player.getShips()) {
                //placeShip(ship, player);
            }
        }

        // Place mines.
        for (Player player : game.getPlayers()) {
            for (Mine mine : player.getMines()) {
                placeMine(mine, player);
            }
        }
    }
}
