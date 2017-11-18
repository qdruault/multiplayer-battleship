/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;


import java.rmi.server.UID;
import java.util.List;


/**
 *
 * @author Davy
 */
public class StatGame extends SerializableEntity{

    private String id;
    private GameType type;
    private String name;
    private List<Player> players;
    private boolean spectator;
    private boolean spectatorChat;
    private LightPublicUser winner;
    private LightPublicUser creator;
    private Game realGame;
    
    private boolean computerMode;
    
    private boolean gameAbandonned;
    
 

    public GameType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isSpectator() {
        return spectator;
    }

    public boolean isSpectatorChat() {
        return spectatorChat;
    }

    public LightPublicUser getWinner() {
        return winner;
    }

    public LightPublicUser getCreator() {
        return creator;
    }

    public Game getRealGame() {
        return realGame;
    }

    public boolean isGameAbandonned() {
        return gameAbandonned;
    }

    public boolean isComputerMode() {
        return computerMode;
    }

    public void setComputerMode(boolean computerMode) {
        this.computerMode = computerMode;
    }


    public void setType(GameType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }

    public void setSpectatorChat(boolean spectatorChat) {
        this.spectatorChat = spectatorChat;
    }

    public void setWinner(LightPublicUser winner) {
        this.winner = winner;
    }

    public void setCreator(LightPublicUser creator) {
        this.creator = creator;
    }

    public void setRealGame(Game realGame) {
        this.realGame = realGame;
    }

    public void setGameAbandonned(boolean gameAbandonned) {
        this.gameAbandonned = gameAbandonned;
    }

    public StatGame(GameType type, String name, List<Player> players, boolean computerMode, boolean spectator, boolean spectatorChat, LightPublicUser winner, LightPublicUser creator,  boolean gameAbandonned) {
        this.id = new UID().toString();
        
        this.type = type;
        this.name = name;
        this.players = players;
        this.spectator = spectator;
        this.spectatorChat = spectatorChat;
        this.winner = winner;
        this.creator = creator;
        //this.realGame = realGame;
        this.gameAbandonned = gameAbandonned;
        this.computerMode = computerMode;
    }
    
    public StatGame(String id, GameType type, String name, boolean computerMode, boolean spectator, boolean spectatorChat, LightPublicUser creator){
        this.id = id;
        this.type = type;
        this.name = name;
        this.spectator = spectator;
        this.spectatorChat = spectatorChat;
        this.players = null;
        this.winner = null;
        this.creator = creator;
       // this.realGame = game;
        this.gameAbandonned = false;
        this.computerMode = computerMode;
    }

    public StatGame() {
    }

    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
   

}
