package com.utclo23.data.module;

import com.utclo23.com.ComFacade;
import com.utclo23.data.configuration.Configuration;
import com.utclo23.data.facade.DataFacade;
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
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    private final DataFacade dataFacade;
    private final Map<String, StatGame> gamesMap;
    private final GameFactory gameFactory;

    private Game currentGame;

    /**
     * Constructor
     *
     * @param dataFacade
     */
    public GameMediator(DataFacade dataFacade) {

        this.dataFacade = dataFacade;
        this.gamesMap = new HashMap<>();
        this.currentGame = null;

        this.gameFactory = new GameFactory();

    }

    /**
     *
     * @return
     */
    public Game getCurrentGame() {
        return currentGame;
    }

    /**
     *
     * @param currentGame
     */
    public void setCurrentGame(Game currentGame) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Data | ");
        this.currentGame = currentGame;
    }

    /**
     * Create a game
     *
     * @param name
     * @param computerMode
     * @param spectator
     * @param spectatorChat
     * @param type
     * @return
     * @throws com.utclo23.data.module.DataException
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
        this.addNewGame(game.getStatGame());

        //to Com : notify a new game
        ComFacade comFacade = this.dataFacade.getComfacade();
        if (comFacade != null && game != null) {
            comFacade.notifyNewGame(game.getStatGame());
        }

        //set current game
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création d'un Game");
        this.currentGame = game;

        return game;
    }

    /**
     * add a new game
     *
     * @param statgame
     */
    public void addNewGame(StatGame statgame) {
        if (!this.gamesMap.containsKey(statgame.getId())) {
            this.gamesMap.put(statgame.getId(), statgame);
        }
    }

    /**
     * Get a game in gamesMap.
     *
     * @param id
     * @return StatGame representing the targeted game
     */
    public StatGame getGame(String id) {
        return this.gamesMap.get(id);

    }

    /* get list of games
     *
     * @return list of games
     */
    /**
     *
     * @return
     */
    public List<StatGame> getGamesList() {
        return new ArrayList<>(this.gamesMap.values());

    }

    /**
     *
     * @param ship
     * @throws DataException
     */
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
            if (ship.getSize() != ship.getListCoord().size()) {
                throw new DataException("Data : ship has more coordinates than its size");
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
                    throw new DataException("Data : position already taken " + c.getX() + "-" + c.getY());
                }
            }

            //ship is ok
            ship.setOwner(player);
            player.getShips().add(ship);

            //last ship
            if (this.currentGame.getTemplateShips().size() == player.getShips().size() && this.dataFacade.getComfacade() != null) {
                this.dataFacade.getComfacade().sendShipsToEnnemy(player.getShips(), this.currentGame.getRecipients(player.getLightPublicUser().getPlayerName()));
                checkPlayersReady();
            }

        } else {
            throw new DataException("Data : error as no current game");
        }

    }

    /**
     *
     */
    /**
     *
     * @param coordinate
     * @param isTrueAttack
     * @param playerWhoPutTheMine
     * @return
     * @throws DataException
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public Pair<Integer, Ship> attack(Coordinate coordinate, boolean isTrueAttack, Player playerWhoPutTheMine) throws DataException, IOException, ClassNotFoundException {
        if (this.currentGame != null) {

            Player player = playerWhoPutTheMine;

            Pair<Integer, Ship> pairReturn;
            if (player == null) {
                throw new DataException("Data : player not found for set player ship");
            }

            //return the result of the attack
            //if isTrueAttack=1, then add mine to player ; otherwise, that is just a test, no stat of mine
            if (isTrueAttack) {
                //check if mine already used at current location
                List<Mine> mines = player.getMines();
                if (!mines.isEmpty()) {
                    for (int i = 0; i < mines.size(); i++) {
                        Mine mine = mines.get(i);
                        if (mine.getCoord().getX() == coordinate.getX() && mine.getCoord().getY() == coordinate.getY()) {
                            Logger.getLogger(GameMediator.class.getName()).log(Level.WARNING, "Data : Mine places in the place where already has a mine");
                            return null;
                        }
                    }
                }

                pairReturn = this.currentGame.attack(player, coordinate, isTrueAttack);

                // Forward to other players.
                dataFacade.getComfacade().notifyNewCoordinates(new Mine(player, coordinate), currentGame.getRecipients(player.getLightPublicUser().getPlayerName()));

                // If the current game is against comptuer and is the current player has finished : the game is finished!
                if (this.currentGame.isComputerGame() && this.currentGame.isGameFinishedByCurrentPlayer()) {
                    this.dataFacade.getUserMediator().addPlayedGame(this.currentGame.getStatGame());
                    this.dataFacade.getIhmTablefacade().finishGame(this.currentGame.getStatGame());

                    //if creator => delete game
                    if (this.currentGame.getStatGame().getCreator().getId().equals(this.dataFacade.getMyPublicUserProfile().getId())) {
                        Logger.getLogger(GameMediator.class.getName()).info("delete game by creator after leave game");
                        this.dataFacade.getComfacade().removeGame(this.currentGame.getId());
                        this.removeGame(this.getCurrentGame().getId());

                    }

                    return null;
                }

                //if creator of the game
                if (this.currentGame.getStatGame().getCreator().getId().equals(this.dataFacade.getUserMediator().getMyPublicUserProfile().getId()) && this.currentGame.isComputerGame()) {
                    //attack

                    Mine m = this.currentGame.getComputerPlayer().randomMine(player.getShips(), this.currentGame);
                    this.forwardCoordinates(m);

                    // Forward to other players.
                    dataFacade.getComfacade().notifyNewCoordinates(m, currentGame.getRecipients(player.getLightPublicUser().getPlayerName()));

                    boolean check = false;
                    for (Ship ship : this.currentGame.getCurrentPlayer().getShips()) {
                        if (this.currentGame.isShipTouched(ship, m)) {

                            this.currentGame.getComputerPlayer().setFocus(m.getCoord());
                            check = true;

                            if (this.currentGame.isShipDestroyed(ship, this.currentGame.getComputerPlayer().getMines())) {
                                this.currentGame.getComputerPlayer().loseFocus(ship);

                            }
                        }

                    }

                    if (!check) {

                        this.currentGame.getComputerPlayer().setFocus(null);
                        if (this.currentGame.getComputerPlayer().getFocus() != null) {
                        }
                    }
                    
                    Random r = new Random();
                    if(r.nextInt(5)==1){
                        this.sendMessageIA();
                    }
                }

            } else {
                // In the case of a test, that's possible that the current player is not
                // the right player to test the mine (that means the enemy of the player 
                // is the right person to test the mine)
                if (playerWhoPutTheMine != null) {
                    pairReturn = this.currentGame.attack(playerWhoPutTheMine, coordinate, isTrueAttack);
                } else {
                    pairReturn = this.currentGame.attack(player, coordinate, isTrueAttack);
                }

            }

            if (isTrueAttack && !this.currentGame.isComputerGame()) {
                //Test if this game is finished
                //If this game is finished, leave the game
                if (this.currentGame.getStatGame().getWinner() != null) {

                    this.defWin();

                }
            }

            return pairReturn;
        } else {
            throw new DataException("Data : player dosn't existe");
        }
    }

    /**
     *
     * Update current game's list as a new user has joined it.
     *
     * @param user the new user who has joined
     * @param id id of the stat game
     * @param role role of the new user
     * @throws com.utclo23.data.module.DataException
     */
    public void updateGameList(LightPublicUser user, String id, String role) throws DataException {

        if (user == null) {
            throw new DataException("error in Data");
        }

        if (this.currentGame.getId().equals(id)) {

            if (role.equals("spectator") || (role.equals("player") && this.currentGame.getPlayers().size() < 2)) {

                Logger.getLogger(this.getClass().toString()).info(user.getPlayerName() + " role " + role);
                this.getCurrentGame().addUser(user, role);

                if (this.dataFacade.getComfacade() != null) {
                    this.dataFacade.getComfacade().joinGameResponse(true, user.getId(), this.currentGame.getStatGame());

                }

            } else {
                this.dataFacade.getComfacade().joinGameResponse(false, id, null);
            }

        } else {

            this.dataFacade.getComfacade().joinGameResponse(false, id, null);
        }

    }

    /**
     *
     * @param id
     * @param role
     */
    public void gameConnectionRequestGame(String id, String role) {

        role = role.toLowerCase();

        if (this.dataFacade.getComfacade() != null) {
            StatGame game = null;
            if (this.gamesMap.containsKey(id)) {
                game = this.gamesMap.get(id);
                //send game
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
        if (this.currentGame.getSpectators().contains(sender) || this.currentGame.getRecipients("").contains(sender) ) {
            if (!this.currentGame.getStatGame().isSpectatorChat()) {
                return;
            }
        }

        Message msg = new Message(sender, text, this.currentGame.getRecipients(sender.getPlayerName()));
        ComFacade comFacade = this.dataFacade.getComfacade();
        if (comFacade != null) {
            comFacade.notifyNewMessage(msg);
        }
    }

    public void sendMessageIA() {
        System.out.println("IA send message");
        List<String> punchlines = new ArrayList<String>();
        punchlines.add("Tu vas bientôt perdre !");
        punchlines.add("Tu as autant de chance de gagner que d'avoir A en LO23");
        punchlines.add("zZz zZz Je m'ennuie avec toi. Tu joues mal.");
        punchlines.add("Tu crois pouvoir battre une IA ?");
        punchlines.add("LOL");
        
        Random r = new Random();
        int i = r.nextInt(punchlines.size());
        String text = punchlines.get(i);
        
        //get information of sender
        LightPublicUser sender = this.currentGame.getComputerPlayer().getLightPublicUser();

        //check if sender is spectator and if chat is allowed for spectators
        
            if (!this.currentGame.getStatGame().isSpectatorChat()) {
                return;
            }
        

        Message msg = new Message(sender, text, this.currentGame.getRecipients(this.dataFacade.getMyPublicUserProfile().getPlayerName()));
        ComFacade comFacade = this.dataFacade.getComfacade();
        if (comFacade != null) {
            comFacade.notifyNewMessage(msg);
        }
        
        this.forwardMessage(msg);
        System.out.println("IA send message end");
    }

    
    /**
     * Forward a message
     *
     * @param msg message to forward
     */
    public void forwardMessage(Message msg) {
        IIHMTableToData ihmTablefacade = this.dataFacade.getIhmTablefacade();
        if (ihmTablefacade != null) {
            ihmTablefacade.printMessage(msg);
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
        if (status.equals(Configuration.PLAYER)) {
            //Sauvegarde à ajouter.
            this.dataFacade.getUserMediator().addPlayedGame(this.currentGame.getStatGame());
            if (this.currentGame.getStatGame().getWinner() == null) {
                this.giveUp();
            }

            //if creator => delete game
            if (this.currentGame.getStatGame().getCreator().getId().equals(this.dataFacade.getMyPublicUserProfile().getId())) {
                Logger.getLogger(GameMediator.class.getName()).info("delete game by creator after leave game");
                this.dataFacade.getComfacade().removeGame(this.currentGame.getId());

                this.removeGame(this.getCurrentGame().getId());

            }

        }

    }

    /**
     *
     * @param game
     */
    public void receptionGame(Game game) {
        System.out.println("refresh game");
        Player player = null;

        for (Player p : game.getPlayers()) {
            if (p.getLightPublicUser().getId().equals(this.dataFacade.getUserMediator().getMyLightPublicUserProfile().getId())) {
                player = p;
                break;
            }
        }

        game.setCurrentPlayer(player);

        //do one time
        if ((this.currentGame != null && !this.currentGame.getId().equals(game.getId()) || this.currentGame == null) && this.dataFacade.getIhmMainFacade() != null) {
            this.currentGame = game;
            this.dataFacade.getIhmMainFacade().receptionGame(game);
        } else {
            this.currentGame = game;
        }

    }

    /**
     *
     */
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
        System.out.println("forward coordinate " + mine.getOwner().getLightPublicUser().getPlayerName() + " mine " + mine.getCoord().getX() + " " + mine.getCoord().getY());
        List<Ship> ships = this.currentGame.ennemyOf(mine.getOwner()).getShips();
        Ship shipDestroyed = null;
        boolean touched = false;

        //Add mine to local player
        this.currentGame.getPlayer(mine.getOwner().getLightPublicUser().getId()).getMines().add(mine);

        for (Ship s : ships) {
            if (this.currentGame.isShipTouched(s, mine)) {

                touched = true;
                if (this.currentGame.isShipDestroyed(s, mine.getOwner().getMines())) {
                    shipDestroyed = s;
                    // Destroyed ship found.
                    break;
                }
            }
        }

        if (this.dataFacade.getIhmTablefacade() != null) {
            this.dataFacade.getIhmTablefacade().feedBack(mine.getCoord(), touched, shipDestroyed);

        }

        if (this.currentGame.isGameFinishedByEnnemy(mine.getOwner())) {

            String status = this.getOwnerStatus();
            if (status.equals(Configuration.PLAYER)) {
                if (this.currentGame.getStatGame().getWinner() == null) {
                    this.giveUp();
                }
                this.dataFacade.getUserMediator().addPlayedGame(this.currentGame.getStatGame());
            } else {
                this.currentGame.getStatGame().setWinner(mine.getOwner().getLightPublicUser());
            }
            this.dataFacade.getIhmTablefacade().finishGame(this.currentGame.getStatGame());

            //if creator => delete game
            if (this.currentGame.getStatGame().getCreator().getId().equals(this.dataFacade.getMyPublicUserProfile().getId())) {
                Logger.getLogger(GameMediator.class.getName()).info("delete game by creator after leave game");
                this.dataFacade.getComfacade().removeGame(this.currentGame.getId());
                this.removeGame(this.getCurrentGame().getId());

            }

        }
    }

    /**
     * Set the opponent as the winner of the current game.
     */
    private void giveUp() {
        String ownerID = this.dataFacade.getUserMediator().getMyPublicUserProfile().getId();
        Player opponent = this.currentGame.ennemyOf(this.currentGame.getPlayer(ownerID));

        if (opponent != null) {
            this.currentGame.getStatGame().setWinner(opponent.getLightPublicUser());
        }
    }

    public void removeGame(String id) {
        if (this.gamesMap.containsKey(id)) {
            this.gamesMap.remove(id);
            Logger.getLogger("GameMediator").info("delete " + id);
        }
    }

    /**
     * Win if the game has no winner yet.
     *
     * @throws com.utclo23.data.module.DataException
     */
    public void defWin() throws DataException {
        if (this.currentGame == null) {
            throw new DataException("Pas de partie en cours.");
        }
        this.dataFacade.getUserMediator().addPlayedGame(this.currentGame.getStatGame());
        if (this.getCurrentGame().getWinner() == null) {
            this.win();
        }

        this.dataFacade.getIhmTablefacade().finishGame(this.currentGame.getStatGame());
    }

    /**
     * Check if there is no current game or there is one but it already has a
     * winner.
     *
     * @return
     */
    public boolean isFinishedGame() {
        boolean finished = true;
        if ((this.currentGame != null) && (this.currentGame.getWinner() == null)) {
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
     * Return the status of the owner in the current game (player, spectator or
     * null).
     *
     * @return
     */
    public String getOwnerStatus() {
        LightPublicUser owner = this.dataFacade.getUserMediator().getMyLightPublicUserProfile();
        return getUserStatus(owner);
    }

    /**
     * Return the status of user in the current game (player, spectator or
     * null).
     *
     * @param user
     * @return
     */
    public String getUserStatus(LightPublicUser user) {
        String status = null;
        if (this.currentGame.isPlayer(user)) {
            status = Configuration.PLAYER;
        } else if (this.currentGame.isSpectator(user)) {
            status = Configuration.SPECTATOR;
        }
        return status;

    }

    /**
     *
     * @param ships
     */
    public void setEnnemyShips(List<Ship> ships) {
        System.out.println("ENNEMY SHIPS " + ships.get(0).getOwner().getLightPublicUser().getPlayerName());

        // Check game is instanciated
        if (this.currentGame != null) {
            if (!ships.isEmpty()) {
                // retrieve the id of the player that put the ships
                String playerid = ships.get(0).getOwner().getLightPublicUser().getId();

                // Retrieve the player that put the ships
                Player p = this.currentGame.getPlayer(playerid);

                // Set the ships
                p.setShips(ships);

                System.out.println("ADD TO " + p.getLightPublicUser().getPlayerName());

                if (!this.getOwnerStatus().equals("spectator")) {
                    checkPlayersReady();
                }

            }
        }
    }

    /**
     * Check if the two players are ready.
     */
    private void checkPlayersReady() {
        // If the 2 players are ready, notify IHM Table.
        if (this.currentGame.getPlayers().size() == 2) {
            if (!this.currentGame.isComputerGame()) {
                boolean ready = true;
                for (Player player : this.currentGame.getPlayers()) {
                    // If their ships are placed.
                    if (this.currentGame.getTemplateShips().size() != player.getShips().size()) {
                        ready = false;
                        break;
                    }
                }

                if (ready) {
                    this.dataFacade.getIhmTablefacade().notifyGameReady();
                }
            } else {
                boolean ready = false;
                for (Player player : this.currentGame.getPlayers()) {
                    // If their ships are placed.
                    if (this.currentGame.getTemplateShips().size() == player.getShips().size()) {
                        ready = true;
                        break;
                    }
                }

                if (ready) //notify IA to place ships
                {
                    Player ennemy = this.currentGame.ennemyOf(this.currentGame.getComputerPlayer());
                    this.currentGame.getComputerPlayer().setIAShips(this.currentGame.getTemplateShips());
                    this.dataFacade.getComfacade().sendShipsToEnnemy(this.currentGame.getComputerPlayer().getShips(), this.currentGame.getRecipients(ennemy.getLightPublicUser().getPlayerName()));

                    this.dataFacade.getIhmTablefacade().notifyGameReady();
                }
            }
        }
    }

    /**
     *
     */
}
