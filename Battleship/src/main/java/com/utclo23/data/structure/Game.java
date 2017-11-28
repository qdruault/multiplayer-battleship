/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import com.utclo23.data.module.Caretaker;
import com.utclo23.data.module.DataException;
import com.utclo23.data.module.Memento;
import java.util.ArrayList;
import java.util.List;

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

        this.currentPlayer = null;
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
        if(role.compareTo("player") == 0) {
            if(this.players.size() == 1) {
                Player player = new Player(user);
                this.players.add(player);
            } else {
                throw new DataException("Data : already two players in this "
                        + "game, you can not add another one.");
            }
        } else if(role.compareTo("spectator") == 0) {
            this.spectators.add(user);
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

    public Ship attack(Player player, Coordinate coordinate)  throws DataException{
        
        //Create mine
        Mine mine = new Mine(player, coordinate);
        
        //For see if the mine is in the place of a ship(succeed at attatck)
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
        for(int i = 0; i < shipsOpponent.size(); i++){
            Ship shipA = shipsOpponent.get(i);
            List<Coordinate> shipCoord = shipA.getListCoord();
            //See if the mine is put in the place of a ship
            for(int j = 0; j < shipCoord.size(); j++){
                if(shipCoord.get(j) == coordinate){
                    succeedAtteck = 1;
                    shipTouch = shipA;
                    break;
                }
            }
            if(succeedAtteck == 1){
                break;
            }
        }
        //Change the state of MineResult according to value of succeedAtteck
        if(succeedAtteck == 1){
            mine.setResult(MineResult.SUCCESS);
        }
        else{
            mine.setResult(MineResult.FAILURE);
        }
        //Add mine to player
        player.getMines().add(mine);
        
        //Return the ship. If is not in right place, shipReturn = null
        if(isShipDestroyed(shipTouch, player.getMines()) ){
            shipReturn = shipTouch;
        }
        nextTurn();
        return shipReturn;
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
    
    public boolean isShipDestroyed(Ship ship, List<Mine> mines) {
        List<Coordinate> coord = ship.getListCoord() ;
        boolean shipDestroyed = true;
         for (int i = 0; i < coord.size() ; i++) {
             for (int j = 0; j < mines.size (); j++) {
                 if (mines.get(j).getCoord() == coord.get(i)) {
                     shipDestroyed = false ;
                     return shipDestroyed ; 
                 }                     
             }
         }
         return shipDestroyed ;
    }
    
    public boolean isGameFinished () {
        
    }
}
