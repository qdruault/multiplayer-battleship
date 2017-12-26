/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;


import java.rmi.server.UID;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


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
    
    /**
     *
     * @return
     */
    public GameType getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
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
    public boolean isSpectator() {
        return spectator;
    }

    /**
     *
     * @return
     */
    public boolean isSpectatorChat() {
        return spectatorChat;
    }

    /**
     *
     * @return
     */
    public LightPublicUser getWinner() {
        return winner;
    }

    /**
     *
     * @return
     */
    public LightPublicUser getCreator() {
        return creator;
    }

    /**
     *
     * @return
     */
    public Game getRealGame() {
        return realGame;
    }

    /**
     *
     * @return
     */
    public boolean isGameAbandonned() {
        return gameAbandonned;
    }

    /**
     *
     * @return
     */
    public boolean isComputerMode() {
        return computerMode;
    }

    /**
     *
     * @param computerMode
     */
    public void setComputerMode(boolean computerMode) {
        this.computerMode = computerMode;
    }

    /**
     *
     * @param type
     */
    public void setType(GameType type) {
        this.type = type;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param players
     */
    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    /**
     *
     * @param spectator
     */
    public void setSpectator(boolean spectator) {
        this.spectator = spectator;
    }

    /**
     *
     * @param spectatorChat
     */
    public void setSpectatorChat(boolean spectatorChat) {
        this.spectatorChat = spectatorChat;
    }

    /**
     *
     * @param winner
     */
    public void setWinner(LightPublicUser winner) {
        this.winner = winner;
    }

    /**
     *
     * @param creator
     */
    public void setCreator(LightPublicUser creator) {
        this.creator = creator;
    }

    /**
     *
     * @param realGame
     */
    public void setRealGame(Game realGame) {
        this.realGame = realGame;
    }

    /**
     *
     * @param gameAbandonned
     */
    public void setGameAbandonned(boolean gameAbandonned) {
        this.gameAbandonned = gameAbandonned;
    }


    
    /**
     *
     * @param id
     * @param type
     * @param name
     * @param computerMode
     * @param spectator
     * @param spectatorChat
     * @param creator
     */
    public StatGame(String id,  GameType type, String name, boolean computerMode, boolean spectator, boolean spectatorChat, LightPublicUser creator){
        this.id = id;
        this.type = type;
        this.name = name;
        this.spectator = spectator;
        this.spectatorChat = spectatorChat;
        this.players = new ArrayList<>();;
        this.winner = null;
        this.creator = creator;
 
        this.gameAbandonned = false;
        this.computerMode = computerMode;
    }

    /**
     *
     */
    public StatGame() {
        this.id = UUID.randomUUID().toString();
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }
   

}
