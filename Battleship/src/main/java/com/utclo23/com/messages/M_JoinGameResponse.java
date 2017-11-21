/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;

/**
 * M_JoinGameResponse is a Message generated to allow or not a user to join a
 * game, sent to all connected users if success, otherwise just to the user who 
 * wants to join the game.
 * @author Thibault CHICHE
 */
public class M_JoinGameResponse extends Message {
    private boolean success;
    /**
    * Constructor.
    * @param user is the message's sender
    * @param success is the response
    */
    public M_JoinGameResponse(PublicUser user, boolean success){
        super(user);
        this.success = success;
    }
    @Override
    public void callback(IDataCom iDataCom){
        //if success call xxx
        //else connexionImpossible()
    }
}
