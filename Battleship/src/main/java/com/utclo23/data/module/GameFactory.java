/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utclo23.data.module;

import java.util.ArrayList;

import com.utclo23.data.structure.BelgianGame;
import com.utclo23.data.structure.ClassicGame;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Message;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.StatGame;
/**
 *
 * @author tboulair
 */
public class GameFactory {
    public Game createGame(String type){
        Game game = null;
        StatGame stateGame = null;
        ArrayList<Player> players = null;
        ArrayList<LightPublicUser> spectators = null;
        ArrayList<Message> messages = null;
        switch(type){
            case "Classic":
                
                game = new ClassicGame(stateGame, players, spectators, messages); 
                break;
            case "Belgian":
                game = new BelgianGame(stateGame, players, spectators, messages); 
                break;
        }
        return game;
    }
}
