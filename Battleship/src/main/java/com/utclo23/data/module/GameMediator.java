package com.utclo23.data.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.utclo23.com.ComFacade;
import com.utclo23.data.configuration.Configuration;
import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.structure.ComputerPlayer;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.Message;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.StatGame;
import com.utclo23.ihmtable.IIHMTableToData;
import java.io.File;
import java.rmi.server.UID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Game Mediator related to games features
 *
 * @author tboulair
 */
public class GameMediator {

    /**
     * reference to the data facade
     */
    private DataFacade dataFacade;
    private Map<String, StatGame> gamesMap;
    private GameFactory gameFactory;

    private Game currentGame;

    /**
     * Constructor
     */
    public GameMediator(DataFacade dataFacade) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création du Game Mediator");

        this.dataFacade = dataFacade;
        this.gamesMap = new HashMap<>();
        this.currentGame = null;

    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    /**
     * Create a game
     *
     * @param name
     * @param spectator
     * @param spectatorChat
     * @param type
     */
    public Game createGame(String name, boolean computerMode, boolean spectator, boolean spectatorChat, GameType type) throws DataException {
        //empty game name
        if (name.isEmpty()) {
            throw new DataException("Data: error due to empty game name ");
        }

        //uppercase game name
        name = name.toUpperCase();

        //get information of creator
        LightPublicUser creator = this.dataFacade.getMyPublicUserProfile().getLightPublicUser();

        //creat Game for realGame
        Game game = this.gameFactory.createGame(name, creator, computerMode, spectator, spectatorChat, type);

        //to Com : notify a new game
        ComFacade comFacade = this.dataFacade.getComfacade();
        if (comFacade != null && game != null) {
            comFacade.notifyNewGame(game.getStatGame());
        }

        //set current game
        System.out.println("Game created");
        this.currentGame = game;

        return game;
    }

    /**
     * add a new game
     *
     * @param game
     */
    public void addNewGame(StatGame statgame) {
        if (!this.gamesMap.containsKey(statgame.getId())) {
            this.gamesMap.put(statgame.getId(), statgame);
        } else {
            throw new RuntimeException("Game " + statgame.getName() + " was already in the list of game.");
        }
    }

    /**
     * Get a game in gamesMap.
     *
     * @param ID UID of the targeted game
     * @return StatGame representing the targeted game
     */
    public StatGame getGame(String ID) {
        StatGame game = this.gamesMap.get(ID);
        return game;
    }

    /* get list of games
     *
     * @return list of games
     */
    public List<StatGame> getGamesList() {
        List<StatGame> listGame = new ArrayList<>(this.gamesMap.values());
        return listGame;
    }

    public void setPlayerShip(Ship ship) throws DataException {
        if (this.currentGame != null) {
            String id = this.dataFacade.getMyPublicUserProfile().getId();
            Player player = this.currentGame.getPlayer(id);
            if (player == null) {
                throw new DataException("Data : player not found for set player ship");
            }

            /**
             * determine all positions taken *
             */
            Map<String, Coordinate> positionMap = new HashMap<>();
            for (Ship s : player.getShips()) {
                for (Coordinate c : s.getListCoord()) {
                    positionMap.put("" + c.getX() + "-" + c.getY(), c);
                }
            }

            //test
            for (Coordinate c : ship.getListCoord()) {
                if (positionMap.containsKey("" + c.getX() + "-" + c.getY())) {
                    throw new DataException("Data : position already taken");
                }
            }

            //ship is ok
            ship.setOwner(player);
            player.getShips().add(ship);

            //last ship
            if (this.currentGame.getTemplateShips().size() == player.getShips().size()) {
                if (this.dataFacade.getComfacade() != null) {

                    this.dataFacade.getComfacade().sendShipsToEnnemy(player.getShips(), this.currentGame.getRecipients());
                }
            }

        } else {
            throw new DataException("Data : error as no current game");
        }

    }

    public void attack(Coordinate coordinate) throws DataException {
        if (this.currentGame != null) {
            String id = this.dataFacade.getMyPublicUserProfile().getId();
            Player player = this.currentGame.getPlayer(id);
            if (player == null) {
                throw new DataException("Data : player not found for set player ship");
            }

            //TODO check if mine already used at current location
            //add mines
            this.currentGame.attack(player, coordinate);

            //save with caretaker
            this.currentGame.getCaretaker().add(this.currentGame.saveStateToMemento());
        }
    }

    public void attackIA() throws DataException {
        if (this.currentGame != null) {
            String id = this.dataFacade.getMyPublicUserProfile().getId();
            Player localPlayer = this.currentGame.getPlayer(id);
            if (localPlayer == null) {
                throw new DataException("Data : player not found");
            }

            
            int dx = 0;
            int dy = 0;
            
            ComputerPlayer computerPlayer = (ComputerPlayer) this.currentGame.ennemyOf(localPlayer);
            Coordinate coord = null;

            //random mode, no focus on location
            if (computerPlayer.getFocus() == null) {
                //make a new shot
                coord = this.generateRandomPosition();
                boolean alreadyDone = false;
                do {
                    for (Mine mine : computerPlayer.getMines()) {
                        if (mine.getCoord().getX() == coord.getX() && mine.getCoord().getY() == coord.getY()) {
                            alreadyDone = true;
                            break;
                        }

                    }

                    coord = this.generateRandomPosition();

                } while (alreadyDone);

            } else {

                int x = computerPlayer.getFocus().getX();
                int y = computerPlayer.getFocus().getY();

                dx = -1;
                dy = -1;

                boolean check = false;

                while (dx <= 1 && !check) {
                    if (dx + x < 0 || dx + x > Configuration.HEIGHT) {
                        while (dy <= 1 && !check) {
                            if (dy + y < 0 || dy + y > Configuration.WIDTH) {
                                
                                
                                //possible shot x y
                                //need to check if already done or not
                                
                                coord = new Coordinate(dx+x, dy+y);
                                boolean control = true;
                                for(Mine mine : computerPlayer.getMines())
                                {
                                    if(mine.getCoord().getX() == coord.getX() && mine.getCoord().getY() == coord.getY())
                                    {
                                        control = false;
                                    }
                                }
                                
                                if(control)
                                {
                                    check = true;
                                    
                                }
                            }
                            dy++;
                        }
                    }
                    dx++;
                }

            }

            dx--;
            dy--;
            
            Mine mine = new Mine(computerPlayer, coord);
            computerPlayer.getMines().add(mine);

            //check if shot
            for (Ship ship : localPlayer.getShips()) {
                for (Coordinate c : ship.getListCoord()) {

                    computerPlayer.setFocus(c);
                }
            }

            this.currentGame.nextTurn();
            //save with caretaker
            //this.currentGame.getCaretaker().add(this.currentGame.saveStateToMemento());
        }
    }

    public Coordinate generateRandomPosition() {
        //reduce possible locations
        int x = (int) (Math.random() * (Configuration.WIDTH / 2));
        int y = (int) (Math.random() * (Configuration.HEIGHT / 2));

        Coordinate coordinate = new Coordinate(x * 2, y * 2);
        return coordinate;

    }

    /**
     *
     * Update current game's list as a new user has joined it.
     *
     * @param user the new user who has joined
     * @param id id of the stat game
     * @param role role of the new user
     */
    public void updateGameList(LightPublicUser user, String id, String role) throws DataException {
        if (this.currentGame.getId().compareTo(id) == 0) {
            this.getCurrentGame().addUser(user, role);

            if (this.dataFacade.getComfacade() != null) {
                this.dataFacade.getComfacade().joinGameResponse(true, id, this.currentGame);
            }

        } else {

            this.dataFacade.getComfacade().joinGameResponse(false, id, null);
        }
    }

    public void gameConnectionRequestGame(String id, String role) {

        if (this.dataFacade.getComfacade() != null) {
            StatGame game = null;
            if (this.gamesMap.containsKey(id)) {
                game = this.gamesMap.get(id);
                //send game
                //TODO set spectator or player
                this.dataFacade.getComfacade().connectionToGame(game);
            }

        }
    }

    /**
     * send a chat message
     *
     * @param text the text message to send
     */
    public void sendMessage(String text) {
        //get information of sender
        LightPublicUser sender = this.dataFacade.getMyPublicUserProfile().getLightPublicUser();

        //check if sender is spectator and if chat is allowed for spectators
        if (this.currentGame.getSpectators().contains(sender)) {
            if (!this.currentGame.getStatGame().isSpectatorChat()) {
                return;
            }
        }

        Message msg = new Message(sender, text, this.currentGame.getRecipients());
        ComFacade comFacade = this.dataFacade.getComfacade();
        if (comFacade != null) {
            comFacade.notifyNewMessage(msg);
        }
    }

    /**
     * Forward a message
     *
     * @param msg message to forward
     */
    public void forwardMessage(Message msg) {
        IIHMTableToData ihmTablefacade = this.dataFacade.getIhmTablefacade();
        if (ihmTablefacade != null) {
            ihmTablefacade.printMessage(msg.getContent());
        }
    }

    /**
     * Exit current game.
     */
    public void leaveGame() {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    public void receptionGame(Game game) {

        if (this.dataFacade.getIhmMainFacade() != null) {

            this.currentGame = game;
            this.dataFacade.getIhmMainFacade().receptionGame(game);
        }

    }

    public void connectionImpossible() {

        if (this.dataFacade.getIhmMainFacade() != null) {
            this.dataFacade.getIhmMainFacade().connectionImpossible();
        }

    }
}
