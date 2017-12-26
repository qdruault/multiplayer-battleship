/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.utclo23.ihmtable.controller;

import com.utclo23.data.module.DataException;
import com.utclo23.ihmtable.IHMTableFacade;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.Message;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.ShipType;
import com.utclo23.ihmtable.structure.CoordinatesGenerator;
import com.utclo23.ihmtable.structure.InGameStats;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.geometry.NodeOrientation;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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

    //association ship <=> image
    Map<Ship, Button> listOfShipsOnTheGrid = new HashMap<Ship, Button>();

    //Memorize destroyed ships so that spectators don't count their destruction multiple times while replaying an ongoing game
    ArrayList<Ship> listOfDestroyedShips = new ArrayList<Ship>();

    //Associative Map (not HashMap because hashcode different with Coordinate) foreach grid
    ArrayList<Coordinate> minesCoordinatesGrid1 = new ArrayList<Coordinate>();
    ArrayList<AnchorPane> minesAnchorPaneGrid1 = new ArrayList<AnchorPane>();
    ArrayList<Coordinate> minesCoordinatesGrid2 = new ArrayList<Coordinate>();
    ArrayList<AnchorPane> minesAnchorPaneGrid2 = new ArrayList<AnchorPane>();


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
    private ListView<HBox> listView;
    @FXML
    private TextField sendcontent;
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
    private int nbTotalPassedTurns;

    /**
    * Current player's stats
    */
    private InGameStats currentPlayerStats;

    /**
    * Opponent's stats
    */
    private InGameStats opponentStats;

    /**
     * The current player.
     */
    private Player currentPlayer;

     /**
     * Me.
     */
    private Player myPlayer;

    /**
     * Me if I'm a spectator.
     */
    private PublicUser mySpectator;


    /**
     * True if playing review game
     */
    private boolean reviewGame = false;

    /**
     * Frame for reloading a game
     */
    private Timeline reloadTimeline = null;

    /**
     * Timer
     */
    private Timeline timer;

    /**
    * The pane of chat.
    */
    @FXML
    private Pane paneChat;

    public boolean isSpectator = false;

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
        //sendButton.setDisable(false);
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

        // Get the current player.
        currentPlayer = facade.getFacadeData().getGame().getCurrentPlayer();
        // Get my player.
        myPlayer = facade.getFacadeData().getGame().getPlayer(facade.getFacadeData().getMyPublicUserProfile().getId());

        
        for(Player p : facade.getFacadeData().getGame().getPlayers())
        {
            System.out.println(p);
        }
        // Spectator.
        if (myPlayer == null) {
            
            System.out.println("je suis spectator");
           
            isSpectator = true;
            mySpectator = facade.getFacadeData().getMyPublicUserProfile();
        }
        else
        {
             System.out.println("je suis player");
        }

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
                if (!isSpectator) {
                    pane.setOnMouseClicked(new AttackEvent(row, col));
                }

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
                if (!isSpectator) {
                    // Add a CSS class to handle the hover effect.
                    pane.getStyleClass().add("inGameGUI_hover_cell");
                    // Add the click event on it.
                    pane.setOnMouseClicked(new ChooseCellEvent(row, col));
                }

                playerGrid.add(pane, col, row);
            }
        }

        // Initialize the position of the ship to place.
        shipToPlace = null;
        startPosition = null;
        endPosition = null;

        if (!isSpectator) {
            try {
                // Get the ships.
                ships = facade.getFacadeData().getTemplateShips();
            } catch (DataException ex) {
                Logger.getLogger(InGameGUIController.class.getName()).log(Level.SEVERE, null, ex);
            }

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
            initTimer();
            chronoTimeInit();

            // Init the number of turns passed.
            nbPassedTurns = 0;
            nbTotalPassedTurns = 0;

        }


        // Init current player's stats of the match
        currentPlayerStats = new InGameStats();
        // Init opponent's stats of the match
        opponentStats = new InGameStats();
        // Init pannel with values
        updateStatsPannel();

        /**
        * Binding of key "enter" for sending message in tchat
        */
       sendcontent.setOnKeyPressed(new EventHandler<KeyEvent>() {
           public void handle(KeyEvent ke) {
               if (ke.getCode() == KeyCode.ENTER) {
                    retrieveInformationAndSendMessage();
               }
           }
        });

       // If the chat is enabled for this game
       // isSpectatorChat() is true if the chat is enabled in the game settings
       if(!facade.getFacadeData().getGame().getStatGame().isSpectatorChat()) {
            paneChat.setOpacity(0);
            paneChat.setDisable(true);
        }

        printMessageInChat("System", "Welcome to the game room !");
    }

    /**
     * Method called when player want reviewing game
     * Start right function for board
     * @param idGame
     */
    public void startReviewingGame(int idGame) {
        // Get list of ships
        /**
         * TODO: Right method for reviewing
         */
        facade.getFacadeData().getInitialBoardFromGameId(Integer.toString(idGame));
        prepareReviewGame();
    }

    /**
    * Method called when notifyGameReady() is called.
    */
    public void startGame() {
        // To know that the game is started.
        gameStarted = true;

        if (isSpectator) {
            // Display all the ships on the board.
            // Left board.
            for (Ship leftShip : facade.getFacadeData().getGame().getPlayers().get(0).getShips()) {
                putShipOnBoard(leftShip, playerGrid);
            }

            // Right board.
            for (Ship rightShip : facade.getFacadeData().getGame().getPlayers().get(1).getShips()) {
                putShipOnBoard(rightShip, opponentGrid);
            }
        } else {
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
    }

    /**
    * Function for switch different pane in starting turn for the current player
    */
    public void switchOpponnentPane() {
        if (readyToAttack == true) {
            System.out.println("A lui de jouer");
            readyToAttack = false;
        }
        else {
            System.out.println("A moi de jouer");
            readyToAttack = true;
        }
        System.out.println("switchOpponnentPane : rdyToAttack : "  + readyToAttack);
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
     * Function to place a ship on the grid with its corresponding image
     * @param ship : the ship to place
     * @param grid : the grid to place the ship on
     * @return : the imageview of the ship placed
     */
    private Button putShipOnBoard(Ship ship, GridPane grid)
    {
        // We assume the ship has the right coordinates.
        // Moreover, this function won't update the shipboard!
        try {

            // Load the image.
            //ImageView shipOnTheGrid2 = new ImageView(shipsPictures.get(ship.getType()));
            Button shipOnTheGrid = new Button(); //Bouton pour ne pas imposer la taille de imageview à la grid
            shipOnTheGrid.setStyle("-fx-background-color: none;"
                    + "-fx-background-repeat: stretch;"
                    + "-fx-background-position: center center;"
                    + "-fx-background-size: 100% 100%;");

            //Anchorpane en wrapper pour resize automatiquement le button
            AnchorPane wrapper = new AnchorPane();
            AnchorPane.setTopAnchor(shipOnTheGrid, 0.0);
            AnchorPane.setBottomAnchor(shipOnTheGrid, 0.0);
            AnchorPane.setLeftAnchor(shipOnTheGrid, 0.0);
            AnchorPane.setRightAnchor(shipOnTheGrid, 0.0);
            wrapper.toBack();
            wrapper.getChildren().addAll(shipOnTheGrid);
            double cellSizeW = (grid.getWidth()-11)/10;
            double cellSizeH = (grid.getWidth()-11)/10;


            // For 1-cell ship.
            if (ship.getSize() == 1) {
                // Set the size.
                // shipOnTheGrid.setFitWidth(cellSizeW);
                // shipOnTheGrid.setFitHeight(cellSizeH);


                shipOnTheGrid.setStyle(shipOnTheGrid.getStyle() + "-fx-background-image: url('" + shipsPictures.get(ship.getType()) + "');");

                // Place on the grid.
                grid.add(
                    wrapper,
                    ship.getListCoord().get(0).getX(),
                    ship.getListCoord().get(0).getY(),
                    ship.getSize(),
                    1
                );
            } else {
                if (ship.getListCoord().get(0).getY() == ship.getListCoord().get(1).getY()) {
                    // Horizontal.
                    // Set the size.
                    // shipOnTheGrid.setFitWidth(cellSizeW * ship.getSize());
                    // shipOnTheGrid.setFitHeight(cellSizeH);

                    shipOnTheGrid.setStyle(shipOnTheGrid.getStyle() + "-fx-background-image: url('" + shipsPictures.get(ship.getType()) + "');");

                    // Place on the grid.
                    grid.add(
                        wrapper,
                        Math.min(
                            ship.getListCoord().get(0).getX(),
                            ship.getListCoord().get(ship.getListCoord().size() - 1).getX()
                        ),
                        ship.getListCoord().get(0).getY(),
                        ship.getSize(),
                        1
                    );

                } else {
                    // Vertical
                    // Set the size.
                    // shipOnTheGrid.setFitHeight(cellSizeW);
                    // shipOnTheGrid.setFitWidth(cellSizeH * ship.getSize());
                    // Rotate the image.
                    //shipOnTheGrid.setRotate(90);

                    //Image revert car pas de rotation de background en CSS
                    String shipImgPath = shipsPictures.get(ship.getType());
                    int len = shipImgPath.length();
                    shipImgPath = shipImgPath.substring(0,len-4) + "rev" + shipImgPath.substring(len-4,len);
                    shipOnTheGrid.setStyle(shipOnTheGrid.getStyle() + "-fx-background-image: url('" + shipImgPath + "');");


                    // Place on the grid.
                    grid.add(
                        wrapper,
                        ship.getListCoord().get(0).getX(),
                        Math.min(
                            ship.getListCoord().get(0).getY(),
                            ship.getListCoord().get(ship.getListCoord().size() - 1).getY()
                        ),
                        1,
                        ship.getSize()
                    );

                }
            }

            listOfShipsOnTheGrid.put(ship, shipOnTheGrid);
            return shipOnTheGrid;

        } catch (Exception ex) {
            Logger.getLogger(InGameGUIController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    /**
     * Update the label on the ship button by adding the specified value
     * @param type : the ship type
     * @param value : the number of ships
     */
    private void updateShipButton(ShipType type, int value)
    {
        try {
            mapShipCount.replace(type, mapShipCount.get(type) + value);
            mapBtnType.get(type).setText(mapShipCount.get(type).toString());
        } catch (Exception e) {
            //Pour une raison obscure, les types apparaissent par moments en francais quand un merge est fait avec DATA
            System.out.println("The shipType key isn't found in the map");
        }
    }

    /**
     * Update stats of the current player and the opponent in the bottom left pannel.
     */
    private void updateStatsPannel() {
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
                System.out.println("onClickFire : rdyToAttack : "  + readyToAttack);

                // Remove the highlight on the cell.
                clickedPane.getStyleClass().removeAll("inGameGUI_selected_cell");
                // Attack!
                try {
                    //Send the attack signal to data, fetch the mine, place it on the grid
                    // (the better would be for data to have the boolean information
                    // in a Mine returned with attack)

                    Pair<Integer, Ship> fireResult = facade.getFacadeData().attack(cellToAttack, true, this.myPlayer);
                    placeMine(cellToAttack, myPlayer);


                    // Reinitialize chrono for the next turn.
                    chronoTimeInit();
                    cellToAttack = null;
                    // End of my turn.
                    switchOpponnentPane();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }

    /**
    * Function for set the attack after feedback call by Data.
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
            if(node.hasProperties()) {
                if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                    result = node;
                    break;
                }
            }
        }

        return result;
    }

     /**
     * Get the Node in the grid according to its positions
     * @param row
     * @param column
     * @param gridPane
     * @param cssClass
     * @return
     */
    private Node getNodeByRowColumnIndexAndCSSClass(final int row, final int column, GridPane gridPane, String cssClass)
    {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(node.hasProperties()) {
                if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column && node.getStyleClass().contains(cssClass)) {
                    result = node;
                    break;
                }
            }
        }

        return result;
    }



    /**
     * Place a mine graphically
     * @param x
     * @param y
     * @param class : css class associated
     */
    private void placeMineGraphics(GridPane grid, int x, int y, String classcss)
    {
        //Setup the arrayList for the selected grid for mine record
        ArrayList<Coordinate> minesCoordinates = new ArrayList<Coordinate>();
        ArrayList<AnchorPane> minesAnchorPane = new ArrayList<AnchorPane>();
        if(grid == opponentGrid)
        {
            minesCoordinates = minesCoordinatesGrid1;
            minesAnchorPane = minesAnchorPaneGrid1;
        }
        else
        {
            minesCoordinates = minesCoordinatesGrid2;
            minesAnchorPane = minesAnchorPaneGrid2;
        }

        //Si une mine est déjà présente (cas spectateur rejoint en cours)
        Coordinate coord = new Coordinate(x,y);
        if(minesCoordinates.contains(coord))
        {
            int index = minesCoordinates.indexOf(coord);
            AnchorPane a = minesAnchorPane.get(index);
            Button b = (Button)a.getChildren().get(a.getChildren().size()-1);
            if(b.getStyleClass().contains("inGameGUI_destroyed_cell")) //if ship destroyed, do nothing
                return;
            else if(b.getStyleClass().contains("inGameGUI_touched_cell") && classcss.equals("inGameGUI_touched_cell")) //if mine already placed, do nothing
                return;
            else if(b.getStyleClass().contains("inGameGUI_touched_cell") && classcss.equals("inGameGUI_destroyed_cell"))   //Si bateau non détruit, on la supprime
            {
                grid.getChildren().remove(a);
                minesCoordinates.remove(index);
                minesAnchorPane.remove(index);
            }

        }



        //Ajout d'une mine (image d'une mine dans un bouton)
        Button b = new Button();

        b.setStyle("-fx-background-color: none;"
                   + "-fx-background-repeat: stretch;"
                   + "-fx-background-position: center center;"
                   + "-fx-background-size: 100% 100%;");

        //Anchorpane en wrapper pour resize automatiquement le button
        AnchorPane wrapper = new AnchorPane();
        AnchorPane.setTopAnchor(b, 0.0);
        AnchorPane.setBottomAnchor(b, 0.0);
        AnchorPane.setLeftAnchor(b, 0.0);
        AnchorPane.setRightAnchor(b, 0.0);
        wrapper.toFront();

        wrapper.getChildren().addAll(b);
        b.getStyleClass().add(classcss);
        b.toFront();
        grid.add(wrapper,x, y);
        minesCoordinates.add(coord);
        minesAnchorPane.add(wrapper);
    }


    /**
     * Generic method for placing a mine on the grid just with the coordinates
     * @param coord : where to place the mine
     * @param player : who places the mine
     */
    public void placeMine(Coordinate coord, Player player)
    {
        //System.out.println("TABLE: ON PLACE UNE MINE EN " + coord.getX() + " " + coord.getY());
        // Select the right grid which depends on the player
        GridPane grid;

        //Cas non spectateur (J1 ou J2)
        if(!isSpectator)
        {
            System.out.println("Je suis pas un spectateur");
            if(player.getLightPublicUser().getId().equals(facade.getFacadeData().getMyPublicUserProfile().getId())) {
                grid = opponentGrid;
                // Reset the number of turns passed for J1.
                nbPassedTurns = 0;
            } else {
                grid = playerGrid;
            }
        }
        //Cas spectateur
        else
        {

             System.out.println("Je suis  un spectateur");
            if(player.equals(facade.getFacadeData().getGame().getPlayers().get(0))) //Current Player is J1 (cf Slack)
            {
                grid = playerGrid; //J1 on the left Grid for spectator
            }
            else
            {
                grid = opponentGrid; //J2 on the right grid
            }
        }

        // Select the node in the grid and disable it when we lanch the attack
        Node hitCell = getNodeByRowColumnIndex(coord.getY(), coord.getX(), grid);
        hitCell.setDisable(true);

        //Launch the attack
        Pair<Integer, Ship> attack_result = facade.getFacadeData().attack(coord, false, player);
        if(attack_result == null) //Can't handle null
        {
            throw new UnsupportedOperationException("ERROR: attack returned null ");
        }

        //data from attack
        boolean touched = attack_result.getKey() == 1;
        boolean newShipDestroyed = attack_result.getValue() != null && !listOfDestroyedShips.contains(attack_result.getValue());

        //If a mine did touch
        if (touched) {
            // Ship Touched!
            placeMineGraphics(grid, coord.getX(), coord.getY(), "inGameGUI_touched_cell");

            // Check if the ship is destroyed.
            Ship destroyedShip = attack_result.getValue();
            if(destroyedShip != null) {
                if ((grid == opponentGrid || isSpectator) && !listOfShipsOnTheGrid.containsKey(destroyedShip)) {
                    // Add ship picture on the opponent grid.
                    putShipOnBoard(destroyedShip, grid);
                }

                // Change the CSS class of the cells.
                destroyShip(destroyedShip, grid);
            }

        } else {
            placeMineGraphics(grid,coord.getX(), coord.getY(), "inGameGUI_missed_cell");
        }
        hitCell.toFront();


        // Update stats pannel
        InGameStats statsToUpdate = null;
        if(grid == opponentGrid){
            statsToUpdate = opponentStats;}
        else{
            statsToUpdate = this.currentPlayerStats;
        }
        statsToUpdate.turnPlayed(touched, newShipDestroyed);
        updateStatsPannel();
    }

    /**
     * Generic method for placing a mine on the grid
     * Data gives us the mine object and the player
     * @param m
     * @param p
     */
    private void placeMine(Mine m, Player p)
    {
        placeMine(m.getCoord(), p);
    }
    /**
     * Method to display popup asking the player to save the game.
     * @param sMessage
     */
    public void displayFinishPopup(String sMessage) {

        final String message = sMessage;

        // Create a runLater Runnable so that the following code will be executed
        // on the JavaFX thread and not in a random background thread
        // To avoid crash
        Platform.runLater(new Runnable() {
            @Override public void run() {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("End of the Game");
                alert.setHeaderText(message);
                alert.setContentText("Do you want to save this game?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    // ... user chose OK
                    //facade.getFacadeData().save();
                }

                try {
                    // Go back to the menu.
                    facade.getFacadeIHMMain().toMenu();
                } catch (IOException ex) {
                    Logger.getLogger(InGameGUIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    /**
     * Display a popup when there is a problem.
     * @param message : the message to display
     */
    private void displayWarningPopup(String message) {
        // Create the popup.
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        // Display it.
        alert.showAndWait();
    }

    /**
     * Display a popup when there is an unknown error.
     * @param ex : the exception that occured
     */
    private void displayErrorPopup(Exception ex) {
        // Create the popup.
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An error occurred");
        alert.setContentText(ex.getMessage());

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    /*
    * Function called when you click on the send button of the chat
    * It will send the message written in the textfield of the chat
    * fx:controller="com.utclo23.ihmtable.controller.InGameGUIController" ==> all the windows
    */
    @FXML
    public void onClickSendButton(MouseEvent event) throws IOException {
        retrieveInformationAndSendMessage();
    }

    /**
     * Retrieve information from the textfield of the chat, clear the textfield
     * and send the message to data
     */
    private void retrieveInformationAndSendMessage() {
        String userName;
        if (isSpectator) {
            userName = mySpectator.getPlayerName();
        } else {
            userName = myPlayer.getLightPublicUser().getPlayerName();;
        }

        String text = retrieveAndClearMessage();
        if (text != null) {
            printMessageInChat(userName, text);
            facade.getFacadeData().sendMessage(text);
        }
    }

    /**
     * Retrieve the message from the textfield of the chat and clear it
     * @return Text contained in the textfield of the chat
     */
    private String retrieveAndClearMessage() {
        String text = null;
        if(!sendcontent.getText().isEmpty()) {
            text = sendcontent.getText();
            sendcontent.clear();
        }
        return text;
    }

    /**
     * Create a message from the username and the message content, and display it
     * in the chat
     * @param userName Name of the player that sends this message
     * @param msgContent Content of the message that has to be displayed
     */
    public void printMessageInChat(String userName, String msgContent) {

        HBox chatBox = new HBox();
        Text chatText = new Text(userName + " :: " + msgContent);
        chatBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
        chatText.setWrappingWidth(400);
        chatBox.getChildren().add(chatText);
        listView.getItems().add(chatBox);
        sendcontent.clear();
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
            }
        }
    }

    /**
    * Function for initialize chrono
    */
    private void chronoTimeInit() {
        switch (nbPassedTurns) {
            case 0:
                countdown = 30;
                chronoLabel.setText("00:30");
                chronoLabel.setTextFill(Color.web("#FFFFFF"));
                break;
            case 1:
                countdown = 15;
                chronoLabel.setText("00:15");
                chronoLabel.setTextFill(Color.web("#FFFFFF"));
                break;
            default:
                countdown = 5;
                chronoLabel.setText("00:05");
                chronoLabel.setTextFill(Color.web("#c0392b"));
                break;
        }
    }

    /**
    * Function for initialize chrono
    */
    private void restartChronoTime() {
        System.out.println("restartcronotime");
        chronoTimeInit();
        if(timer!=null){
            timer.playFromStart();
        }

    }

    /**
    * Function for simulate chrono, using Timeline and KeyFrame import and set the label
    */
    private void initTimer() {
        timer = new Timeline();
        timer.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(1.1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // To prevent the timer when it'snot my turn.
                if (readyToAttack) {
                    countdown--;
                    // Time's up!
                    if (countdown <= 0) {
                        timer.stop();
                        chronoLabel.setText("00:00");

                        // Leave the game if te player has not played for the 3rd time.
                        if (nbPassedTurns == 2) {
                            facade.getFacadeData().leaveGame();
                        } else {
                            // Increase the number of turns passed.
                            nbPassedTurns++;
                            nbTotalPassedTurns--;

                            // Fake an attack.
                            facade.getFacadeData().attack(new Coordinate(nbTotalPassedTurns, nbTotalPassedTurns), true, null);

                            // Reinitialize chrono for the next turn
                            chronoTimeInit();
                            switchOpponnentPane();
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
        timer.getKeyFrames().add(frame);
    }

    /**
     * Generic private method for setting anchor pane in right place
     * @param anchor anchorPane selected
     * @param butt button in the anchor selected
     */
    private void setAnchorEach(AnchorPane anchor, Button butt) {
        AnchorPane.setTopAnchor(butt, 0.0);
        AnchorPane.setLeftAnchor(butt, 0.0);
        AnchorPane.setRightAnchor(butt, 0.0);
        AnchorPane.setBottomAnchor(butt, 0.0);
        anchor.getChildren().add(butt);
    }


    /**
     * Reload a game from a list of event
     * @param eventList : list of events (mines and messages)
     */
    private void reloadGame(List<com.utclo23.data.structure.Event> eventList)
    {
        //Foreach event
        for(com.utclo23.data.structure.Event dataEvent : eventList)
        {
            //If it's a mine, place it
            if(dataEvent instanceof Mine)
            {
                Mine m = (Mine)dataEvent;
                placeMine(m, m.getOwner());
            }
            //If it's a message, print it
            else
            {
                Message m = (Message)dataEvent;
                // facade.printMessage(m);
            }
        }
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

        /*
         * Backward button preparation : set image + style + binding + onAction
         */
        Button backWardButton = new Button();
        backWardButton.setGraphic(backwardImage);
        backWardButton.getStyleClass().add("reviews");
        backWardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                reloadGame(facade.getFacadeData().getPreviousBoard());
            }
        });

        Button playButton = new Button();
        playButton.setGraphic(playImage);
        playButton.getStyleClass().add("reviews");
        playButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //if the timeline doesn't exist, create it
                if(reloadTimeline == null)
                {
                    //Create the review timeline
                    reloadTimeline = new Timeline();
                    reloadTimeline.setCycleCount(Timeline.INDEFINITE);
                    KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            List<com.utclo23.data.structure.Event> l = facade.getFacadeData().getNextBoard();
                            if(l == null){
                                reloadTimeline.pause();
                            }
                            else{
                                reloadGame(l);
                            }
                        }
                    });
                    reloadTimeline.getKeyFrames().add(frame);
                    reloadTimeline.playFromStart();
                }
                //if the timeline already exist, play it
                else{
                    reloadTimeline.play();
                }
            }
        });

        Button pauseButton = new Button();
        pauseButton.setGraphic(pauseImage);
        pauseButton.getStyleClass().add("reviews");
        backWardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                //stop the review timeline
                reloadTimeline.pause();
            }
        });

        Button forwardButton = new Button();
        forwardButton.setGraphic(forwardImage);
        forwardButton.getStyleClass().add("reviews");
        backWardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                reloadGame(facade.getFacadeData().getNextBoard());
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
        HBox.setHgrow(backWardPane, Priority.ALWAYS);
        actionPanel.getChildren().add(playPane);
        HBox.setHgrow(playPane, Priority.ALWAYS);
        actionPanel.getChildren().add(pausePane);
        HBox.setHgrow(pausePane, Priority.ALWAYS);
        actionPanel.getChildren().add(forwardPane);
        HBox.setHgrow(forwardPane, Priority.ALWAYS);
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

                    // Prevent diagonal placement.
                    if (startPosition.getX() != endPosition.getX() &&
                        startPosition.getY() != endPosition.getY()
                    ) {
                        displayWarningPopup("Ship cannot be placed diagonally.");
                    } else {
                        // Update the ship.
                        for (Ship ship : ships) {
                            // Search for the first ship of the right type not placed.
                            if (ship.getType() == shipToPlace && ship.getListCoord().isEmpty()) {
                                try {
                                    // Ship founded.
                                    suitableShip = true;
                                    // Add the coordinates.
                                    CoordinatesGenerator.generate(startPosition, endPosition, ship.getListCoord());

                                    // Send the ship.
                                    facade.getFacadeData().setShip(ship);

                                    // No exception : Place the ship on the board.
                                    // Load the image.
                                    //associate ship with its image on the grid

                                    // Put the image on the board
                                    putShipOnBoard(ship, playerGrid);


                                    // ATTENTION! Grid size is out of control!
                                    // setShip didn't return any exception so the ship is correctly placed -> Update the label on the left panel
                                    updateShipButton(shipToPlace, -1);

                                } catch (DataException ex) {
                                    Logger.getLogger(InGameGUIController.class.getName()).log(Level.SEVERE, null, ex);
                                    // Display the error.
                                    displayWarningPopup("You cannot place your ship here.\n"
                                            + "Do not forget this ship needs "
                                            + ship.getSize() + " cells."
                                    );
                                    // Wrong coordinates -> reset.
                                    ship.getListCoord().clear();

                                } catch (Exception e) {
                                    Logger.getLogger(InGameGUIController.class.getName()).log(Level.SEVERE, null, e);
                                    // Display the error.
                                    displayErrorPopup(e);
                                    // Wrong coordinates -> reset.
                                    ship.getListCoord().clear();
                                } finally {
                                    // The ship was found, no need to continue the loop.
                                    break;
                                }
                            }
                        }

                        // If no ship suits.
                        if (!suitableShip) {
                            System.out.println("No suitable ship for this type!");
                            // Display the error.
                            displayWarningPopup("You have placed all of your "
                                + shipToPlace.name() + " ships."
                            );
                        }
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
    * @param game Game object (for spectator)
    */
    public void loadGame(Game game)
    {
        System.out.println("TABLE: ON LIT UNE GAME PAR UN SPECTATEUR");

        // Place ships (disabled to avoid cheating)
        /*for (Player player : game.getPlayers()) {
            for (Ship ship : player.getShips()) {
                //placeShip(ship, player); use putShipOnBoard
            }
        }*/

        // Place mines.
        for (Player player : game.getPlayers()) {
            for (Mine mine : player.getMines()) {
                placeMine(mine, player);
            }
        }
    }

    /**
     * Display the opponent attack on the player grid.
     * @param coord : attacked cell
     * @param touched : true if touched
     * @param destroyedShip : the destroyed ship or null.
     */
    public void displayOpponentAttack(Coordinate coord, boolean touched, Ship destroyedShip) {
        System.out.println("displayOpponentAttack : rdyToAttack : "  + readyToAttack);

        // Return in case of fake attack
        if (coord.getX() < 0 || coord.getY() < 0) {
            return;
        }

        // Get the cell.
        Node cell = getNodeByRowColumnIndex(coord.getY(), coord.getX(), playerGrid);
        // The opponent has touched my ship.
        if (touched) {
            // Add the CSS class.
            cell.getStyleClass().add("inGameGUI_touched_cell");
        } else {
            // The opponent has missed.
            cell.getStyleClass().add("inGameGUI_missed_cell");
        }
        cell.toFront();

        // Ship destroyed.
        if (destroyedShip != null) {
            // Change the CSS class of the cells.
            destroyShip(destroyedShip, playerGrid);
        }

    }

    /**
     * Update the CSS of the cell where the destroyed ship is.
     * @param ship : the destroyed ship
     * @param grid : the player or opponent grid
     */
    private void destroyShip(Ship ship, GridPane grid)
    {
        if(!listOfDestroyedShips.contains(ship))
        {
                // Change the opacity.
            listOfShipsOnTheGrid.get(ship).setOpacity(0.5);
            listOfShipsOnTheGrid.get(ship).toBack();
            listOfDestroyedShips.add(ship);

            // Change the CSS class of the cells.
            for (Coordinate coord : ship.getListCoord()) {
                placeMineGraphics(grid,coord.getX(), coord.getY(), "inGameGUI_destroyed_cell");


            }
        }

    }
}
