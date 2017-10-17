/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.rmi.server.UID;
import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public class StatGame {
    private UID id;
    private GameType type;
    private String name;
    private ArrayList<Player> LightPublicUser;
    private boolean spectator;
    private boolean spectatorChat;
    private LightPublicUser winner;
    private LightPublicUser creator;
    private Game realGame;
    private boolean gameAbandonned;
    
 

    public GameType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Player> getLightPublicUser() {
        return LightPublicUser;
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


    public void setType(GameType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLightPublicUser(ArrayList<Player> LightPublicUser) {
        this.LightPublicUser = LightPublicUser;
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

    public StatGame(GameType type, String name, ArrayList<Player> LightPublicUser, boolean spectator, boolean spectatorChat, LightPublicUser winner, LightPublicUser creator, Game realGame, boolean gameAbandonned) {
        this.type = type;
        this.name = name;
        this.LightPublicUser = LightPublicUser;
        this.spectator = spectator;
        this.spectatorChat = spectatorChat;
        this.winner = winner;
        this.creator = creator;
        this.realGame = realGame;
        this.gameAbandonned = gameAbandonned;
    }
    
    
}
