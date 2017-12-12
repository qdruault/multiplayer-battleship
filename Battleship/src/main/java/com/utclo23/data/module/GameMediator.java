package com.utclo23.data.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.utclo23.com.ComFacade;
import com.utclo23.data.module.DataException;
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
import java.io.IOException;
import java.rmi.server.UID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Pair;

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

        this.gameFactory = new GameFactory();

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
            System.out.println("notify");
            comFacade.notifyNewGame(game.getStatGame());
        }
        else   System.out.println("no notify");
               

        //set current game
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création d'un Game");
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
             * Check if the ship has the right amount of coordinates
             */
            for (Ship s : player.getShips()) {
                if(s.getSize() != s.getListCoord().size()){
                    throw new DataException("Data : ship has more coordinates than its size");
                }
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

            //test the ship isn't placed on coordinates that are already taken
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

    /**
     *
     * @param coordinate
     * @param isTrueAttack
     * @return
     * @throws DataException
     */
    public Pair<Integer, Ship> attack(Coordinate coordinate, boolean isTrueAttack) throws DataException, IOException, ClassNotFoundException {
        
        if (this.currentGame != null) {
            Player player = this.currentGame.getCurrentPlayer();
            Pair<Integer, Ship> pairReturn;
            if (player == null) {
                throw new DataException("Data : player not found for set player ship");
            }

          if (isTrueAttack == true) {
            //check if mine already used at current location
            List<Mine> mines = player.getMines();
            if (mines.size() > 0) {
                for (int i = 0; i < mines.size(); i++) {
                    Mine mine = mines.get(i);
                    if (mine.getCoord().getX() == coordinate.getX() && mine.getCoord().getY() == coordinate.getY()) {
                        Logger.getLogger(GameMediator.class.getName()).log(Level.WARNING, "Data : Mine places in the place where already has a mine");
                        return null;
                    }
                }
            }

            //return the result of the attack
            //if isTrueAttack=1, then add mine to player ; otherwise, that is just a test, no stat of mine
           
                pairReturn = this.currentGame.attack(player, coordinate, isTrueAttack);

                //save with caretaker
                this.currentGame.getCaretaker().add(this.currentGame.saveStateToMemento());
                return pairReturn;
            } else {
                // In the case of a test, that's possible that the current player is not
                // the right player to test the mine (that means the enemy of the player 
                // is the right person to test the mine)
                pairReturn = this.currentGame.attack(player, coordinate, isTrueAttack);
                if (pairReturn.getKey() == 0 && pairReturn.getValue() == null) {
                    pairReturn = this.currentGame.attack(this.currentGame.ennemyOf(player), coordinate, isTrueAttack);
                }
                return pairReturn;
            }
        } else {
            throw new DataException("Data : player dosn't existe");
        }
    }

    public void attackIA() throws DataException {

        //check if current game
        if (this.currentGame != null) {

            ComputerPlayer computerPlayer = (ComputerPlayer) this.currentGame.getCurrentPlayer();
            Coordinate coord = null; //location of shoot

            //random mode, no focus on location
            if (true) {

                //make a new shot not already chosen
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
            }

            this.currentGame.nextTurn();
            //save with caretaker
            //this.currentGame.getCaretaker().add(this.currentGame.saveStateToMemento());
        }
    }

    public Coordinate generateRandomPosition() {
        //reduce possible locations
        int x = (int) (Math.random() * (Configuration.WIDTH));
        int y = (int) (Math.random() * (Configuration.HEIGHT));

        Coordinate coordinate = new Coordinate(x, y);
        return coordinate;
    }
    

    /**
     *
     * Update current game's list as a new user has joined it.
     * @param user the new user who has joined
     * @param id id of the stat game
     * @param role role of the new user
     */
    public void updateGameList(LightPublicUser user, String id, String role) throws DataException {
       if(user == null)
            {
                System.out.println("user is null");
            }
            
            if(this.currentGame == null)
            {
                System.out.println("current game is null");
            }
        
        if (this.currentGame.getId().compareTo(id) == 0) {
            
            
            
             System.out.println("add Urole "+role);
            this.getCurrentGame().addUser(user, role);

            if (this.dataFacade.getComfacade() != null) {
                this.dataFacade.getComfacade().joinGameResponse(true, user.getId(), this.currentGame);

            }
        } else {

            this.dataFacade.getComfacade().joinGameResponse(false, id, null);
        }
    }

    public void gameConnectionRequestGame(String id, String role) {

        role = role.toLowerCase();
        
        if (this.dataFacade.getComfacade() != null) {
            StatGame game = null;
            if (this.gamesMap.containsKey(id)) {
                game = this.gamesMap.get(id);
                //send game
                
                // 
                System.out.println(" ROLE : "+role);
                
                this.dataFacade.getComfacade().connectionToGame(game, role);
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
        //Sauvegarde à ajouter.
        this.dataFacade.getUserMediator().addPlayedGame(this.currentGame.getStatGame());
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



    public void receptionGame(Game game) {
        System.out.println("reception game ... ");
        this.currentGame = game;
        if (this.dataFacade.getIhmMainFacade() != null) {
            
            System.out.println("give to ihm ... ");
            this.dataFacade.getIhmMainFacade().receptionGame(game);
        }

    }

    public void connectionImpossible() {
        if (this.dataFacade.getIhmMainFacade() != null) {
            this.dataFacade.getIhmMainFacade().connectionImpossible();

        }
    }

    /**
     * foorwardCoordinates
     *
     * @param mine the mine placed
     */
    public void forwardCoordinates(Mine mine) {
        List<Ship> ships = this.currentGame.getCurrentPlayer().getShips();
        Ship shipDestroyed = null;
        boolean touched = false;
        for (Ship s : ships) {
            if (this.currentGame.isShipTouched(s, mine)) {
                touched = true;
                if (this.currentGame.isShipDestroyed(s, this.currentGame.ennemyOf(this.currentGame.getCurrentPlayer()).getMines())) {
                    shipDestroyed = s;
                }
            }
        }

        if (this.dataFacade.getIhmTablefacade() != null) {
            //this.dataFacade.getIhmTablefacade().feedback(mine.getCoord(),touched,shipDestroyed) ;
        }

        if (this.currentGame.isGameFinishedByEnnemy()) {
            // a faire
             //Sauvegarde à ajouter, que l'owner soit joueur ou pas.
            String status = this.getOwnerStatus();
            if(status == "player") {
                if(this.currentGame.getStatGame().getWinner() == null) {
                    this.giveUp();
                }
                this.dataFacade.getUserMediator().addPlayedGame(this.currentGame.getStatGame());
                
                 this.currentGame = null;
            }
        }


       
       
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
