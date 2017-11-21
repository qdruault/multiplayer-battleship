/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utclo23.data.module;

import java.util.List;

import com.utclo23.data.structure.BelgianGame;
import com.utclo23.data.structure.ClassicGame;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Message;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.StatGame;
import java.rmi.server.UID;
import java.util.ArrayList;
/**
 *
 * @author tboulair
 * 
 */
public class GameFactory {
    public Game createGame(String name, LightPublicUser creator,  boolean computerMode, boolean spectator, boolean spectatorChat, GameType type){
        //create stat game
        String id = new UID().toString();
        StatGame statGame = new StatGame(id, type, name, computerMode, spectator, spectatorChat, creator);
        
        Game game = null;
        List<Player> players = new ArrayList<>();
        
        //Add first player
        Player j1 = new Player(creator);
        players.add(j1);
        
        if(computerMode)
        {
            //add second player
            Player j2 = new Player(LightPublicUser.generateComputerProfile());
            players.add(j2);
        }
        
        List<LightPublicUser> spectators = new ArrayList<>();
        spectators.add(creator);
        
        List<Message> messages = new ArrayList<>();
        switch(type){
            case CLASSIC:
                game = new ClassicGame(statGame, players, spectators, messages); 
                break;
            case BELGIAN:
                game = new BelgianGame(statGame, players, spectators, messages); 
                break;
        }
        
        return game;
    }
}
