/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.module.DataException;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.StatGame;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thibault
 */
public class M_JoinGameResponse extends Message {
    private boolean success;
    private StatGame game;
    public M_JoinGameResponse(PublicUser user, boolean success, StatGame game){
        super(user);
        this.success = success;
        this.game = game;
    }
    @Override
    public void callback(IDataCom iDataCom){
        //if success call xxx
        //else connexionImpossible()
        if(success){
            try {
                iDataCom.receptionGame(game.getRealGame());
            } catch (DataException ex) {
                Logger.getLogger(M_JoinGameResponse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else {
            try {
                iDataCom.connectionImpossible();
            } catch (DataException ex) {
                Logger.getLogger(M_JoinGameResponse.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
