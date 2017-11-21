/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.StatGame;
/**
 * M_JoinGame is a Message generated when a user wants to join a game.
 * @author Thibault CHICHE 
 */
public class M_JoinGame extends Message {
    private final StatGame game;
    private String role;
    
    /**
     * Constructor
     * @param user is the message's sender
     * @param game is the game that the player wants to join to
     */
    public M_JoinGame(PublicUser user, StatGame game) {
        super(user);
        this.game = game;
    }
    @Override
    public void callback(IDataCom iDataCom) {
        //Mettre à jour l'interface updateGameList()
      // iDataCom.updateGameList(user.getLightPublicUser(), game.getId(), role);
    }
    public void setRole(String r){
        role = r;
    }
}
