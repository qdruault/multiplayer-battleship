package com.utclo23.data.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.utclo23.com.ComFacade;
import com.utclo23.data.module.DataException;
import com.utclo23.data.configuration.Configuration;
import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.Ship;

import com.utclo23.data.structure.StatGame;
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
        if (comFacade != null) {
            comFacade.notifyNewGame(game.getStatGame());
        }

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
                if(positionMap.containsKey(""+c.getX()+"-"+c.getY())){
                     throw new DataException("Data : position already taken");
                }
            }
            
            //ship is ok
            ship.setOwner(player);
            player.getShips().add(ship);
            
            if(this.currentGame.getTemplateShips().size() == player.getShips().size())
            {
                if(this.dataFacade.getComfacade()!=null)
                {
                    //TODO notify network that the player is ready (all ships initialized
                }
            }

        } else {
            throw new DataException("Data : error as no current game");
        }

    }
    
    
    public void attack(Coordinate coordinate) throws DataException
    {
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
    /**
     * 
     * Update current game's list as a new user has joined it.
     * 
     * @param user the new user who has joined
     * @param id id of the stat game 
     * @param role role of the new user
     */
    public void updateGameList(LightPublicUser user, String id, String role) throws DataException {
        if(this.currentGame.getId().compareTo(id) == 0) {
            this.getCurrentGame().addUser(user, role);
        } else {
            throw new DataException("The game whose list of players you want to"
                    + " update is not the current game.");
        }
    }
    
    /**
     * Set the winner of the current game.
     * 
     * @param winner 
     */
    public void setWinner(LightPublicUser winner) {
        this.currentGame.setWinner(winner);
    }
    
    /**
     * Exit current game.
     */
    public void leaveGame() {
        //Sauvegarde à ajouter, que l'owner soit joueur ou pas.
        String status = this.getOwnerStatus();
        if(status == "player") {
            if(this.currentGame.getStatGame().getWinner() == null) {
                this.giveUp();
            }
            this.dataFacade.getUserMediator().addPlayedGame(this.currentGame.getStatGame());
        }
        this.currentGame = null;
    }
    
    /**
     * Set the opponent as the winner of the current game.
     */
    private void giveUp() {
        String ownerID = this.dataFacade.getUserMediator().getMyPublicUserProfile().getId();
        Player opponent = this.currentGame.ennemyOf(this.currentGame.getPlayer(ownerID));
        this.currentGame.getStatGame().setWinner(opponent.getLightPublicUser());
    }
    
    /**
     * Win if the game has no winner yet.
     */
    public void defWin() throws DataException {
        if(this.currentGame == null) {
            throw new DataException("Pas de partie en cours.");
        }
        if(this.getCurrentGame().getWinner() == null) {
            this.win();
        }
    }
    /**
     * Check if there is no current game or there is one but it already has a winner.
     * 
     * @return 
     */
    public boolean isFinishedGame() {
        boolean finished = true;
        if((this.currentGame != null) && (this.currentGame.getWinner() == null)) {
            finished = false;
        }
        return finished;
    }
    
    /**
     * Set the owner as the winner of the current game.
     */
    private void win() {
        this.currentGame.getStatGame().setWinner(this.dataFacade.getUserMediator().getMyLightPublicUserProfile());
    }
    
    /**
     * Return the status of the owner in the current game (player, spectator or null).
     * 
     * @return 
     */
    public String getOwnerStatus() {
        LightPublicUser owner = this.dataFacade.getUserMediator().getMyLightPublicUserProfile();
        return getUserStatus(owner);
    }
    
    /**
     * Return the status of user in the current game (player, spectator or null).
     * 
     * @param user
     * @return 
     */
    public String getUserStatus(LightPublicUser user) {
        String status = null;
        if(this.currentGame.isPlayer(user)) {
            status = "player";
        } else if (this.currentGame.isSpectator(user)) {
            status = "spectator";
        }
        return status;
    }
}
