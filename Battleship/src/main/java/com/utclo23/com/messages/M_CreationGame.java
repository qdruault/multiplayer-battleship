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
 * M_CreationGame is a Message generated when a game is created,
 * sent to all connected user.
 * @author Thibault CHICHE
 */
public class M_CreationGame extends Message{
    public StatGame game;
    
    /**
    * Constructor.
    * @param user is the message's sender
    * @param game is the created game
    */
    public M_CreationGame(PublicUser user, StatGame game){
        super(user);
        this.game = game;
    }
    
    @Override
    public void callback(IDataCom iDataCom){
        iDataCom.addNewGame(game);
    }
}
