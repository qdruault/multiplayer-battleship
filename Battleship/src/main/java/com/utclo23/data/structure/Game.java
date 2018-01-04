/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.utclo23.data.module.DataException;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import javafx.util.Pair;

import java.util.Iterator;

/**
 *
 * @author Davy
 */
public abstract class Game extends SerializableEntity {

    private StatGame statGame;
    private List<Player> players;
    private List<LightPublicUser> spectators;
    private List<Message> messages;
    private boolean save;
    
    private Player currentPlayer;

    /**
     *
     * @param players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     *
     * @param spectators
     */
    public void setSpectators(List<LightPublicUser> spectators) {
        this.spectators = spectators;
    }

    /**
     *
     * @param messages
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     *
     * @param statGame
     * @param players
     * @param spectators
     * @param messages
     */
    public Game(StatGame statGame, List<Player> players, List<LightPublicUser> spectators, List<Message> messages) {
        this.statGame = statGame;
       
        this.save = false;

        this.players = players;
        this.spectators = spectators;

        this.messages = messages;
        
        if(!this.players.isEmpty()){
        this.currentPlayer = players.get(0);
        }
          
         this.statGame.setRealGame(this);

    }

    /**
     *
     */
    public Game() {
        this.players = new ArrayList<>();
        
    }

    /**
     *
     * @return
     */
    public boolean isSave() {
        return save;
    }

    /**
     *
     * @param save
     */
    public void setSave(boolean save) {
        this.save = save;
    }

    /**
     * Adding a user to the game, as spectator or player.
     *
     * @param user
     * @param role
     * @throws DataException
     */
    public void addUser(LightPublicUser user, String role) throws DataException {
 
        role = role.toLowerCase();

        if (role.equals("player")) {

            if (this.players.size() <= 1) {
                Player player = new Player(user);
                this.players.add(player);
                
                System.out.println("ADD AS USER "+user.getPlayerName());

            }
            
        } else if (this.getStatGame().isSpectator() && role.equals("spectator")) {
            System.out.println("ADD AS SPECTATOR "+user.getPlayerName());
            this.spectators.add(user);
           

        } else {
            throw new DataException("Data : given role is not known.");
        }

    }

    /**
     *
     * @return
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     *
     * @return
     */
    public List<LightPublicUser> getSpectators() {
        return spectators;
    }

    /**
     *
     * @return
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     *
     * @return
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public boolean isReady() {
        return this.players.size() == 2;
    }

    /**
     *
     */
    public void nextTurn() {

    }

    /**
     *
     * @param player
     * @return
     */
    public Player ennemyOf(Player player) {
        Player ennemy = null;

        if (this.players.size() >= 2) {
            if (player.getLightPublicUser().getId().equals(this.players.get(0).getLightPublicUser().getId())) {
                ennemy = this.players.get(1);
            } else {
                ennemy = this.players.get(0);
            }
        }

        return ennemy;
    }

    /**
     *
     * @param id
     * @return
     */
    public Player getPlayer(String id) {
        Player player = null;
        for (Player p : this.players) {
            if (p.getLightPublicUser().getId().equals(id)) {
                player = p;
            }
        }

        return player;
    }

    /**
     *
     * @param idCurrentPlayer
     * @return
     */
    public List<LightPublicUser> getRecipients(String idCurrentPlayer) {
        List<LightPublicUser> listRecipients = new ArrayList<>();

        for (int i = 0; i < this.getPlayers().size(); ++i) {
            if (!(this.getPlayers().get(i).isComputer()) && !this.getPlayers().get(i).getLightPublicUser().getPlayerName().equals(idCurrentPlayer)) {
                listRecipients.add(this.getPlayers().get(i).getLightPublicUser());
            } 
        }

       listRecipients.addAll(this.getSpectators());

       return listRecipients;
    }

    /**
     *
     * @return
     */
    public StatGame getStatGame() {
        return statGame;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public String getId() {
        return this.statGame.getId();
    }

    /**
     *
     * @param statGame
     */
    public void setStatGame(StatGame statGame) {
        this.statGame = statGame;
    }

    /**
     * get templates of ships for a given game
     *
     * @return
     */
    public abstract List<Ship> getTemplateShips();

    /**
     *
     * @param <T>
     * @param src
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @JsonIgnore
    // This is for deep copy of a List
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
     
        return new ArrayList<>(src);
    }

    /**
     *
     * @param player
     * @param coordinate
     * @param isTrueAttack
     * @return
     * @throws DataException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @JsonIgnore
    public Pair<Integer, Ship> attack(Player player, Coordinate coordinate, boolean isTrueAttack) throws DataException, IOException, ClassNotFoundException {
        //Create mine
        Mine mine = new Mine(player, coordinate);

        //For see if the mine is in the place of a ship(succeed at attack)
        // 0=fail . 1=succeed
        int succeedAttack = 0;
        // The ship if the mine is in the place of it
        Ship shipTouch = null;
        Ship shipReturn = null;

        //Get player opponent
        Player playerOpponent = ennemyOf(player);
        //Get opponent's ships
        if (playerOpponent == null) {
            throw new DataException("Data : player opponent doesn't exist");
        }
        List<Ship> shipsOpponent = playerOpponent.getShips();
        if (shipsOpponent.isEmpty()) {
            throw new DataException("Data : player opponent didn't set any ship");
        }

        for (Ship shipA : shipsOpponent) {

            for (Coordinate shipCoord : shipA.getListCoord()) {
                if (shipCoord.getX() == coordinate.getX() && shipCoord.getY() == coordinate.getY()) {
                    succeedAttack = 1;
                    shipTouch = shipA;

                }
            }
            if (succeedAttack == 1) {
               

                break;
            }
        }

        if (isTrueAttack) {//When this is a real attack

            //Change the state of MineResult according to value of succeedAttack
            if (succeedAttack == 1) {
             
                mine.setResult(MineResult.SUCCESS);
                //Add mine to player
                player.getMines().add(mine);
                //If is not in right place, shipReturn = null
                if (isShipDestroyed(shipTouch, player.getMines())) {
                 
                    shipReturn = shipTouch;
                }
            } else {
                mine.setResult(MineResult.FAILURE);
               
                //Add mine to player
                player.getMines().add(mine);
            }
            //Test if this game is finished
            boolean gameFinished = this.isGameFinishedByCurrentPlayer();
            if (gameFinished) {
                this.setWinner(player.getLightPublicUser());
            }

        } else {
            //We creat a list of mines to test if one ship is destroyed 
            //by adding the mine of test
            List<Mine> minesAdd = deepCopy(player.getMines());
            if (succeedAttack == 1) {
                minesAdd.add(mine);
                if (isShipDestroyed(shipTouch, minesAdd)) {
                    shipReturn = shipTouch;
                }
            }
        }
        Pair attackShip;
        attackShip = new Pair(succeedAttack, shipReturn);
        return attackShip;
    }



    /**
     *
     * @param coordinate
     * @return
     */
    @JsonIgnore
    /* to get the mine added in attack */
    public Mine getRecentMine(Coordinate coordinate) {
        List<Player> playersList = this.players;
        for (Player play : playersList) {
            Mine minePlayer = play.getMines().get(play.getMines().size() - 1);
            Coordinate coorMine = minePlayer.getCoord();
            if (coorMine.getX() == coordinate.getX() && coorMine.getY() == coordinate.getY()) {
                return minePlayer;
            }
        }
        return null;
    }


    /**
     *
     * @param player
     */

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    /**
     * clear mines and messages (all moves are stored now in caretaker) it
     * enables player to have a blank game at the beginning of the next review
     */
     @JsonIgnore
    public void prepareToBeSaved() {
        this.messages.clear();
        for (Player p : players) {
            p.getMines().clear();
        }
    }

    /**
     * test if a ship is destroyed
     *
     * @param ship the ship to test
     * @param mines list of the mines to compare with the coord of the ship
     * @return boolean true if ship destroyed false otherwise
     */
     @JsonIgnore
    public boolean isShipDestroyed(Ship ship, List<Mine> mines) {
        boolean shipDestroyed = true;

        for (Coordinate coordinateShip : ship.getListCoord()) {
            boolean touched = false;
            for (Mine mine : mines) {
                Coordinate coordinateMine = mine.getCoord();

                if (coordinateMine.getX() == coordinateShip.getX() && coordinateMine.getY() == coordinateShip.getY()) {
                    touched = true;
                }
            }

            if (!touched) {
                shipDestroyed = false;
                break;
            }
        }

        return shipDestroyed;
    }

    /**
     * test if a ship is touched
     *
     * @param ship the ship to test
     * @param mine the mine to test
     * @return boolean true if ship touched false otherwise
     */
     @JsonIgnore
    public boolean isShipTouched(Ship ship, Mine mine) {
        boolean touched = false;
        Coordinate coordinateMine = mine.getCoord();
        for (Coordinate coordinate : ship.getListCoord()) {
            if (coordinate.getX() == coordinateMine.getX() && coordinate.getY() == coordinateMine.getY()) {
                touched = true;
                break;
            }
        }

        return touched;
    }

    /**
     * test if the game is finished
     *
     * @return boolean true if game finished false otherwise
     */
     @JsonIgnore
    public boolean isGameFinishedByCurrentPlayer() {
        List<Mine> mines = this.currentPlayer.getMines();
        List<Ship> ships = this.ennemyOf(this.currentPlayer).getShips();
        boolean gameFinished = true;
        for (Ship s : ships) {
            if (!isShipDestroyed(s, mines)) {
                gameFinished = false;
            }
        }

       return gameFinished;
    }

    /**
     * test if the game is finished
     *
     * @param ennemy
     * @return boolean true if game finished false otherwise
     */
     @JsonIgnore
    public boolean isGameFinishedByEnnemy(Player ennemy) {
        List<Mine> mines = this.ennemyOf(this.ennemyOf(ennemy)).getMines();
        List<Ship> ships = this.ennemyOf(ennemy).getShips();
        boolean gameFinished = true;
        for (Ship s : ships) {
            if (!isShipDestroyed(s, mines)) {
                gameFinished = false;
            }
        }
      return gameFinished;
    }

    /**
     * Get the winner of this game's StatGame.
     *
     * @return
     */
     @JsonIgnore
    public LightPublicUser getWinner() {
        return this.statGame.getWinner();
    }

    /**
     * Set the winner of this game's StatGame.
     *
     * @param winner
     */
     @JsonIgnore
    public void setWinner(LightPublicUser winner) {
        this.statGame.setWinner(winner);
    }

    /**
     * Check if user is a player of the game.
     *
     * @param user
     * @return
     */
    @JsonIgnore
    public boolean isPlayer(LightPublicUser user) {
        String userID = user.getId();
        Iterator<Player> i = this.players.iterator();
        boolean isPlayer = false;
        while (i.hasNext() && !isPlayer) {
            String playerID = i.next().getLightPublicUser().getId();
            if (playerID.equals(userID)) {
                isPlayer = true;
            }
        }
        return isPlayer;
    }

    /**
     * Check if user is a spectator of the game.
     *
     * @param user
     * @return
     */
     @JsonIgnore
    public boolean isSpectator(LightPublicUser user) {
        String userID = user.getId();
        Iterator<LightPublicUser> i = this.spectators.iterator();
        boolean isSpectator = false;
        while (i.hasNext() && !isSpectator) {
            String specID = i.next().getId();
            if (specID.equals(userID)) {
                isSpectator = true;
            }
        }
        return isSpectator;
    }

    /**
     *
     * @return If a player of this game is a computer
     */
     @JsonIgnore
    public boolean isComputerGame() {
        return (this.getPlayers().get(0).isComputer() || (this.getPlayers().size()==2 && this.getPlayers().get(1).isComputer()));
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public ComputerPlayer getComputerPlayer() {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).isComputer()) {
                return (ComputerPlayer) players.get(i);
            }
        }
        return null;
    }
}