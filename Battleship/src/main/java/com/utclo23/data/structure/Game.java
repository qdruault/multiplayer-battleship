/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.util.List;

/**
 *
 * @author Davy
 */
public abstract class Game extends SerializableEntity{
    private StatGame statGame;
    private List<Player> players;
    private List<LightPublicUser> spectators;
    private List<Message> messages;

    public Game(StatGame statGame, List<Player> players, List<LightPublicUser> spectators, List<Message> messages) {
        this.statGame = statGame;
        this.players = players;
        this.spectators = spectators;
        this.messages = messages;
    }
    
    public List<LightPublicUser> getRecipients()
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
