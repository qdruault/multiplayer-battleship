/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public abstract class Game {
    private StatGame statGame;
    private ArrayList<Player> players;
    private ArrayList<LightPublicUser> spectators;
    private ArrayList<Message> messages;

    public Game(StatGame statGame, ArrayList<Player> players, ArrayList<LightPublicUser> spectators, ArrayList<Message> messages) {
        this.statGame = statGame;
        this.players = players;
        this.spectators = spectators;
        this.messages = messages;
    }
    
    public ArrayList<LightPublicUser> getRecipients()
    {
        return null;
    }

    public StatGame getStatGame() {
        return statGame;
    }

    public void setStatGame(StatGame statGame) {
        this.statGame = statGame;
    }
    
    
    
}
