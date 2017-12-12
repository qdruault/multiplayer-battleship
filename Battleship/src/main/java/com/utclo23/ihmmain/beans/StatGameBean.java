/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.beans;

import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.StatGame;
import java.util.List;

/**
 * A class to display replays list easier
 * @author Linxuhao
 */
public class StatGameBean {
    private final StatGame game;
    
    public StatGameBean(StatGame game){
        this.game = game;
    }
    
    public StatGame getGame(){
        return game;
    }
            
    public String getGameName(){
        return game.getName();
    }
    
    public String getWinner(){
        return game.getWinner().getPlayerName();
    }
    
    public String getLosser(){
        String losser = "";
        List<Player> players = game.getPlayers();
        if(players != null){
            for(Player player : players){
                //if the player is not a winner
                if(!player.getLightPublicUser().getId().equals(game.getWinner().getId())){
                    //then is a losser
                    losser = player.getLightPublicUser().getPlayerName();
                }
            }
        }

        return losser;
    }
    
}
