/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;

/**
 * M_LeaveGame is a Message generated when a user wants to leave a game.
 * @author Thibault CHICHE
 */
public class M_RemoveGame extends Message {
    private final String idGame;
    /**
     * Constructor.
     * @param user is the message's sender
     * @param idGame is the id of the game to remove
     */
    public M_RemoveGame(PublicUser user, String idGame){
        super(user);
        this.idGame = idGame;
    }
    
    @Override
    public void callback(IDataCom iDataCom){
        iDataCom.removeGame(idGame);
    }
}
