/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import com.utclo23.data.module.Caretaker;
import com.utclo23.data.module.DataException;
import com.utclo23.data.module.Memento;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private Caretaker caretaker;

    private Player currentPlayer;

    public Game(StatGame statGame, List<Player> players, List<LightPublicUser> spectators, List<Message> messages) {
        this.statGame = statGame;
        this.players = players;
        this.spectators = spectators;

        this.messages = messages;

        this.currentPlayer = players.get(0);
        /* creation of caretaker */
        this.caretaker = new Caretaker();
    }
    
    /**
     * Adding a user to the game, as spectator or player.
     * 
     * @param user
     * @param role
     * @throws DataException 
     */
    public void addUser(LightPublicUser user, String role) throws DataException {
        System.out.println("inside addUser");
        
        role = role.toLowerCase();
        
        if(role.equals("player")) {
            
            if(this.players == null)
            {
                System.out.println("players == null");
            }
            
            if(this.players.size() == 1) {
                
                Player player = new Player(user);
                this.players.add(player);
                
                System.out.println("player "+player.getLightPublicUser().getId());
                
            } else 
            {
                throw new DataException("Data : already two players in this "
                        + "game, you can not add another one.");
            }
        } else if(role.equals("spectator")) {
            this.spectators.add(user);
            
            System.out.println("spectator "+user.getId());
               
            
        } else {
            throw new DataException("Data : given role is not known.");
        }
        
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<LightPublicUser> getSpectators() {
        return spectators;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    
    public boolean isReady() {
        return this.players.size() == 2;
    }

    public void nextTurn() {
        if (this.isReady() && this.currentPlayer == null) {
            this.currentPlayer = this.players.get(0);
        } else {
                
            this.currentPlayer = this.ennemyOf(currentPlayer);
        }          
    }

    public Player ennemyOf(Player player) {
        Player ennemy = null;
        if (player.equals(this.players.get(0))) {
            ennemy = this.players.get(1);
        } else {
            ennemy = this.players.get(0);
        }

        return ennemy;
    }
    
    
    
    
    public Player getPlayer(String id)
    {
        Player player = null;
        for(Player p : this.players)
        {
            if(p.getLightPublicUser().getId().equals(id))
            {
                player = p;
            }
        }
                
        return player;
    }
  
    public List<LightPublicUser> getRecipients() {
        List<LightPublicUser> listRecipients = new ArrayList<>();
        for(int i = 0; i < this.getPlayers().size(); ++i)
            listRecipients.add(this.getPlayers().get(i).getLightPublicUser());
        listRecipients.addAll(this.getSpectators()) ;
        return listRecipients;
    }

    public StatGame getStatGame() {
        return statGame;
    }
    
    public String getId() {
        return this.statGame.getId();
    }

    public void setStatGame(StatGame statGame) {
        this.statGame = statGame;
    }
    
     /**
     * get templates of ships for a given  game
     * @return 
     */
    
    public abstract List<Ship> getTemplateShips();
    
    // This is for deep copy of a List
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {  
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();  
        ObjectOutputStream out = new ObjectOutputStream(byteOut);  
        out.writeObject(src);  

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());  
        ObjectInputStream in = new ObjectInputStream(byteIn);  
        @SuppressWarnings("unchecked")  
        List<T> dest = (List<T>) in.readObject();  
        return dest;  
    }
    
    public Pair<Integer, Ship> attack(Player player, Coordinate coordinate, boolean isTrueAttack)  throws DataException, IOException, ClassNotFoundException{
        
        //Create mine
        Mine mine = new Mine(player, coordinate);
        
        //For see if the mine is in the place of a ship(succeed at attack)
        // 0=fail . 1=succeed
        int succeedAtteck = 0;
        // The ship if the mine is in the place of it
        Ship shipTouch = null;
        Ship shipReturn = null;
        
        //Get player opponent
        Player playerOpponent = null;
        playerOpponent = ennemyOf(player);
        //Get opponent's ships
        if (playerOpponent == null) {
            throw new DataException("Data : player opponent doesn't exist");
        }
        List<Ship> shipsOpponent = playerOpponent.getShips();
        if(shipsOpponent.size()==0){
            throw new DataException("Data : player opponent didn't set any ship");
        }
        for(int i = 0; i < shipsOpponent.size(); i++){
            Ship shipA = shipsOpponent.get(i);
            List<Coordinate> shipCoord = shipA.getListCoord();
            //See if the mine is put in the place of a ship
            for(int j = 0; j < shipCoord.size(); j++){
                if(shipCoord.get(j).getX() == coordinate.getX() && shipCoord.get(j).getY() == coordinate.getY()){
                    succeedAtteck = 1;
                    shipTouch = shipA;
                    break;
                }
            }
            if(succeedAtteck == 1){
                break;
            }
        }
        
        if(isTrueAttack == true){   //When this is a real attack
            //Change the state of MineResult according to value of succeedAtteck
            if(succeedAtteck == 1){
                mine.setResult(MineResult.SUCCESS);
                //Add mine to player
                player.getMines().add(mine);
                //If is not in right place, shipReturn = null
                if(isShipDestroyed(shipTouch, player.getMines()) ){
                    shipReturn = shipTouch;
                }
            }
            else{
                mine.setResult(MineResult.FAILURE);
                //Add mine to player
                player.getMines().add(mine);
            }
            
            //Let opponent be the current player
            nextTurn();
        }
        else{
            //We creat a list of mines to test if one ship is destroyed 
            //by adding the mine of test
            List<Mine> minesAdd = deepCopy(player.getMines());
            if(succeedAtteck == 1){
                minesAdd.add(mine);
                if( isShipDestroyed(shipTouch, minesAdd) ){
                    shipReturn = shipTouch;
                }
            }
        }
        Pair attackShip;
        attackShip = new Pair(succeedAtteck, shipReturn);
        return attackShip;
    }
    
    
    
    
    
    public Memento saveStateToMemento()
    {
       //notify caretaker
        List<Event> events = new ArrayList<>();
        //Add msgs
        events.addAll(this.messages);
        for(Player p : players)
        {
            events.addAll(p.getMines());
        }
        //creation of memento
        Memento memento = new Memento(events);
        return memento;
    }
    
    
    public void restoreStateToMemento(Memento memento)
    {
        //reset
        this.messages.clear();
        for(Player p : players)
        {
            p.getMines().clear();;
        }
        
        List<Event> events = memento.getState();
        for(Event e : events)
        {
            if(e instanceof Message)
            {
                Message m = (Message) e;
                this.messages.add(m);
            }
            else  if(e instanceof Mine)
            {
                Mine m = (Mine) e;
                m.getOwner().getMines().add(m);
            }
            
        }
    }

    public Caretaker getCaretaker() {
        return caretaker;
    }

    public void setCaretaker(Caretaker caretaker) {
        this.caretaker = caretaker;
    }
    
    public void setCurrentPlayer(Player player){
        this.currentPlayer = player;
    }
    /**
     * clear mines and messages (all moves are stored now in caretaker)
     * it enables player to have  a blank game at the beginning of the next review
     */
    public void prepareToBeSaved()
    {
        this.messages.clear();
        for(Player p : players)
        {
            p.getMines().clear();
        }
    }
    

     /**
     * test if a ship is destroyed 
     * @param ship the ship to test
     * @param mines list of the mines to compare with the coord of the ship
     * @return boolean true if ship destroyed false otherwise
     */
    public boolean isShipDestroyed(Ship ship, List<Mine> mines) {
        List<Coordinate> shipCoord = ship.getListCoord() ;
        boolean shipDestroyed = true ;
        for (int i = 0; i < shipCoord.size() ; i++) {
            int j = 0;
            for (j = 0; j < mines.size (); j++) {
                // if the mine is in one case of ship, we skip the loop and to verify the next one
                if (shipCoord.get(i).getX() == mines.get(j).getCoord().getX() && shipCoord.get(i).getY() == mines.get(j).getCoord().getY()) {
                     break;
                }
            }
            // when there isn't any mine in the place of a square of ship
            if(j == mines.size ()){
                shipDestroyed = false;
                break;
            }
        }
        return shipDestroyed ;
    }
    
      
   /**
     * test if a ship is touched
     * @param ship the ship to test
     * @param mine the mine to test
     * @return boolean true if ship touched false otherwise
     */
    public boolean isShipTouched(Ship ship, Mine mine) {
        List<Coordinate> coord = ship.getListCoord() ;
        boolean shipTouched = false ;
        for (Coordinate c : coord) {
            if (c == mine.getCoord()) {
                shipTouched = true ; 
                return shipTouched ; 
            }
        }
        return shipTouched ;
    }

    
     /**
     * test if the game is finished
     * @return boolean true if game finished false otherwise
     */
    public boolean isGameFinishedByCurrentPlayer() {
        List<Mine> mines = this.currentPlayer.getMines() ;
        List<Ship> ships = this.ennemyOf(this.currentPlayer).getShips() ; 
        boolean gameFinished = true ;
        for (Ship s : ships) {
            if (!isShipDestroyed(s,mines))
                gameFinished = false; 
        }
        return gameFinished; 
    }
    
    /**
     * test if the game is finished
     * @return boolean true if game finished false otherwise
     */
    public boolean isGameFinishedByEnnemy() {
        List<Mine> mines = this.ennemyOf(this.currentPlayer).getMines() ;
        List<Ship> ships = this.currentPlayer.getShips() ; 
        boolean gameFinished = true ;
        for (Ship s : ships) {
            if (!isShipDestroyed(s,mines))
                gameFinished = false; 
        }
        return gameFinished; 
    }        

    /**
     * Get the winner of this game's StatGame.
     * 
     * @return 
     */
    public LightPublicUser getWinner() {
        return this.statGame.getWinner();
    }
    
    /**
     * Set the winner of this game's StatGame.
     * 
     * @param winner 
     */
    public void setWinner(LightPublicUser winner) {
        this.statGame.setWinner(winner);
    }
    
    /**
     * Check if user is a player of the game.
     * 
     * @param user
     * @return 
     */
    public boolean isPlayer(LightPublicUser user) {
        String userID = user.getId();
        Iterator<Player> i = this.players.iterator();
        boolean isPlayer = false;
        while(i.hasNext() && !isPlayer) {
            String playerID = i.next().getLightPublicUser().getId();
            if(playerID == userID) {
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
    public boolean isSpectator(LightPublicUser user) {
        String userID = user.getId();
        Iterator<LightPublicUser> i = this.spectators.iterator();
        boolean isSpectator = false;
        while(i.hasNext() && !isSpectator) {
            String specID = i.next().getId();
            if(specID == userID) {
                isSpectator = true;
            }
        }
        return isSpectator;
    }

    
}
