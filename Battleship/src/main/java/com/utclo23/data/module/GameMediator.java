package com.utclo23.data.module;

import com.utclo23.com.ComFacade;
import com.utclo23.data.configuration.Configuration;
import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.structure.ComputerPlayer;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Event;
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
    private DataFacade dataFacade;
    private Map<String, StatGame> gamesMap;
    private GameFactory gameFactory;

    private Game currentGame;

    /**
     * Constructor
     */
    public GameMediator(DataFacade dataFacade) {

        this.dataFacade = dataFacade;
        this.gamesMap = new HashMap<>();
        this.currentGame = null;

        this.gameFactory = new GameFactory();

    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Data | ");
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
        } else {
            System.out.println("no notify");
        }

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
            if (this.currentGame.getTemplateShips().size() == player.getShips().size()) {
                if (this.dataFacade.getComfacade() != null) {
                    this.dataFacade.getComfacade().sendShipsToEnnemy(player.getShips(), this.currentGame.getRecipients(player.getLightPublicUser().getPlayerName()));
                    checkPlayersReady();
                }
            }

        } else {
            throw new DataException("Data : error as no current game");
        }

    }

    /**
     *
     */
    public void setComputerShips() {
        Player cPlayer = this.currentGame.getComputerPlayer();
        List<Ship> listShips = this.currentGame.getTemplateShips();
        for (int s = 0; s < listShips.size(); s++) {
            craftCoordinates(listShips, listShips.get(s));
        }
    }

    /**
     * Gives a random position to a ship.
     *
     * @param previousShips
     * @param ship
     */
    private void craftCoordinates(List<Ship> previousShips, Ship ship) {
        List<List<Coordinate>> allCoords = this.createAvailableCoordinates(previousShips, ship);
        Random r = new Random();
        int position = r.nextInt(allCoords.size());
        ship.setListCoord(allCoords.get(position));
    }

    private List<List<Coordinate>> createAvailableCoordinates(List<Ship> previousShips, Ship ship) {
        int size = ship.getSize();
        List<List<Coordinate>> returnList = new ArrayList();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                Coordinate c = new Coordinate(x, y);
                if (c.isAllowed(previousShips)) {

                    //Crafting the West -> East coordinates.
                    List<Coordinate> coordsWE = new ArrayList();
                    coordsWE.add(new Coordinate(x, y));
                    boolean allowed = true;
                    for (int s = 0; s < previousShips.size(); s++) {
                        Coordinate cSuite = new Coordinate(x + s, y);
                        if (!cSuite.isAllowed(previousShips)) {
                            allowed = false;
                            break;
                        } else {
                            coordsWE.add(cSuite);
                        }
                    }
                    if (allowed) {
                        returnList.add(coordsWE);
                    }

                    //Crafting the South->North coordinates.
                    List<Coordinate> coordsSN = new ArrayList();
                    coordsSN.add(new Coordinate(x, y));
                    allowed = true;
                    for (int s = 0; s < previousShips.size(); s++) {
                        Coordinate cSuite = new Coordinate(x, y + s);
                        if (!cSuite.isAllowed(previousShips)) {
                            allowed = false;
                            break;
                        } else {
                            coordsSN.add(cSuite);
                        }
                    }
                    if (allowed) {
                        returnList.add(coordsSN);
                    }
                }
            }

        }
        return returnList;
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
            System.out.println("ATTACK CURRENT PLAYER " + player.getLightPublicUser().getPlayerName());

            Pair<Integer, Ship> pairReturn;
            if (player == null) {
                throw new DataException("Data : player not found for set player ship");
            }

            //return the result of the attack
            //if isTrueAttack=1, then add mine to player ; otherwise, that is just a test, no stat of mine
            if (isTrueAttack == true) {
                System.out.println("--------------------------------------------------");
                System.out.println("Data | True attack -------------------------------");
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

                pairReturn = this.currentGame.attack(player, coordinate, isTrueAttack);

                // Forward to other players.
                dataFacade.getComfacade().notifyNewCoordinates(new Mine(player, coordinate), currentGame.getRecipients(player.getLightPublicUser().getPlayerName()));

                //save with caretaker
                if (!this.currentGame.isSave()) {
                    this.currentGame.getCaretaker().add(this.currentGame.saveStateToMemento());
                }

                //if creator of the game
                if (this.currentGame.getStatGame().getCreator().getId().equals(this.dataFacade.getUserMediator().getMyPublicUserProfile().getId())) {
                    //if computer mode ?
                    if (this.currentGame.isComputerGame()) {
                        //attack

                        Mine m = this.currentGame.getComputerPlayer().randomMine(player.getShips(), this.currentGame);
                        this.forwardCoordinates(m);

                        boolean check = false;
                        for (Ship ship : this.currentGame.getCurrentPlayer().getShips()) {
                            if (this.currentGame.isShipTouched(ship, m)) {

                                this.currentGame.getComputerPlayer().setFocus(m.getCoord());
                                check = true;

                                if (this.currentGame.isShipDestroyed(ship, this.currentGame.getComputerPlayer().getMines())) {
                                    this.currentGame.getComputerPlayer().loseFocus(ship);
                                    System.out.println("DATA  TOTAL FOCUS LOST");

                                } else {
                                    System.out.println("DATA  FOCUS ON " + m.getCoord().getX() + "," + m.getCoord().getY());
                                }
                            }

                        }

                        if (!check) {

                            this.currentGame.getComputerPlayer().setFocus(null);
                            System.out.println("DATA  FOCUS  LOST  ");
                            if (this.currentGame.getComputerPlayer().getFocus() != null) {
                                System.out.println("NEW FOCUS  " + this.currentGame.getComputerPlayer().getFocus().getX() + "," + this.currentGame.getComputerPlayer().getFocus().getY());
                            }
                        }

                    }
                }

            } else {
                // In the case of a test, that's possible that the current player is not
                // the right player to test the mine (that means the enemy of the player 
                // is the right person to test the mine)
                pairReturn = this.currentGame.attack(player, coordinate, isTrueAttack);
                if (pairReturn.getKey() == 0 && pairReturn.getValue() == null) {
                    //pairReturn = this.currentGame.attack(this.currentGame.ennemyOf(player), coordinate, isTrueAttack);
                }

            }

            //this.currentGame.nextTurn();
            if (isTrueAttack && !this.currentGame.isComputerGame()) {
                //Test if this game is finished
                //If this game is finished, leave the game
                if (this.currentGame.getStatGame().getWinner() != null) {

                    this.defWin();

                }
            }

            System.out.println("--------------------------------------------------");

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
     */
    public void updateGameList(LightPublicUser user, String id, String role) throws DataException {
        System.out.print("liste players");

        for (Player p : this.currentGame.getPlayers()) {
            System.out.println(p.getLightPublicUser().getId() + " " + p.getLightPublicUser().getPlayerName());
        }

        System.out.println("-----------");

        System.out.println("id " + id);
        if (user == null) {
            System.out.println("user is null");
        }

        if (this.currentGame == null) {
            System.out.println("current game is null");
        }

        if (this.currentGame.getId().equals(id)) {

            System.out.println("add Urole " + role);
            if (true) {
                this.getCurrentGame().addUser(user, role);

                if (this.dataFacade.getComfacade() != null) {

                    System.out.println("data join game resp");
                    this.dataFacade.getComfacade().joinGameResponse(true, user.getId(), this.currentGame.getStatGame());

                }
            } else {
                this.dataFacade.getComfacade().joinGameResponse(false, id, null);
            }
        } else {

            this.dataFacade.getComfacade().joinGameResponse(false, id, null);
        }

        System.out.println("nombre de joueurs " + this.currentGame.getPlayers().size());
    }

    public void gameConnectionRequestGame(String id, String role) {

        role = role.toLowerCase();

        if (this.dataFacade.getComfacade() != null) {
            StatGame game = null;
            if (this.gamesMap.containsKey(id)) {
                game = this.gamesMap.get(id);
                //send game

                // 
                System.out.println(" ROLE : " + role);

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

        Message msg = new Message(sender, text, this.currentGame.getRecipients(sender.getPlayerName()));
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
        if (status == "player") {
            if (this.currentGame.getStatGame().getWinner() == null) {
                this.giveUp();
            }
        }
        // this.currentGame = null;
    }

    public void receptionGame(Game game) {
        System.out.println("reception game ... ");
        Player player = null;

        for (Player p : game.getPlayers()) {
            if (p.getLightPublicUser().getId().equals(this.dataFacade.getUserMediator().getMyLightPublicUserProfile().getId())) {
                player = p;
            }
        }

        game.setCurrentPlayer(player);

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
        System.out.println("FORWARD COORDINATES "+mine.getOwner().getLightPublicUser().getPlayerName()+" "+mine.getCoord().getX()+","+mine.getCoord().getY());
        List<Ship> ships = this.currentGame.ennemyOf(mine.getOwner()).getShips();//this.currentGame.getCurrentPlayer().getShips();
        Ship shipDestroyed = null;
        boolean touched = false;
        for (Ship s : ships) {
            if (this.currentGame.isShipTouched(s, mine)) {
                
                System.out.println("data "+mine.getOwner().getLightPublicUser().getPlayerName()+" touched "+mine.getCoord().getX()+","+mine.getCoord().getY());
                
                touched = true;
                if (this.currentGame.isShipDestroyed(s, mine.getOwner().getMines())) {
                    shipDestroyed = s;
                    
                    System.out.println("data "+mine.getOwner().getLightPublicUser().getPlayerName()+" destroyed ");
                
                    
                }
                
            }
        }
        
        if(touched)
        {
             System.out.println("attack manqué ");
            
        }

        //Add mine to local game
        if (!this.currentGame.isComputerGame()) {
            this.currentGame.ennemyOf(this.currentGame.getCurrentPlayer()).getMines().add(mine);
        }

        //save
        if (!this.currentGame.isSave()) {
            this.currentGame.getCaretaker().add(this.currentGame.saveStateToMemento());
        }

        if (this.dataFacade.getIhmTablefacade() != null) {
            this.dataFacade.getIhmTablefacade().feedBack(mine.getCoord(), touched, shipDestroyed);

        }

        if (this.currentGame.isGameFinishedByEnnemy()) {
            //Sauvegarde à ajouter, que l'owner soit joueur ou pas.
            String status = this.getOwnerStatus();
            if (status == "player") {
                if (this.currentGame.getStatGame().getWinner() == null) {
                    this.giveUp();
                }
                this.dataFacade.getUserMediator().addPlayedGame(this.currentGame.getStatGame());
            }
            this.dataFacade.getIhmTablefacade().finishGame(this.currentGame.getStatGame());
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
            status = "player";
        } else if (this.currentGame.isSpectator(user)) {
            status = "spectator";
        }
        return status;

    }

    public void setEnnemyShips(List<Ship> ships) {
        // Check game is instanciated
        if (this.currentGame != null) {
            if (!ships.isEmpty()) {
                // retrieve the id of the player that put the ships
                String player_id = ships.get(0).getOwner().getLightPublicUser().getId();

                // Retrieve the player that put the ships
                Player p = this.currentGame.getPlayer(player_id);

                // Set the ships
                p.setShips(ships);

                checkPlayersReady();
            }
        }
    }

    /**
     * Check if the two players are ready.
     */
    private void checkPlayersReady() {
        // If the 2 players are ready, notify IHM Table.

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
            System.out.println("Data | IA game");
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
                System.out.println("Data | IA turn set ships");
                this.currentGame.getComputerPlayer().setShips(this.currentGame.getTemplateShips());
                this.dataFacade.getIhmTablefacade().notifyGameReady();
            }
        }
    }

    public void next() {
        if (this.currentGame != null) {
            if (this.currentGame.isSave()) {
                Event event = this.currentGame.getCaretaker().getMemento().getLastEvent();
                if (event instanceof Mine) {
                    Mine mine = (Mine) event;
                    if (this.dataFacade.getMyPublicUserProfile().getId().equals(mine.getOwner().getLightPublicUser().getId())) {
                        //equivalent of attack

                    } else {
                        //equivalent of forward

                    }
                }
                this.currentGame.getCaretaker().next();
            }
        }
    }

}
