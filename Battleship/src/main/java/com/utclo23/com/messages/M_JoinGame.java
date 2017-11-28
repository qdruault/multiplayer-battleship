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
public class M_JoinGame extends Message {
    private StatGame game;
    private String role;
    
    public M_JoinGame(PublicUser user, StatGame g) {
        super(user);
        game = g;
    }
    @Override
    public void callback(IDataCom iDataCom) {
        try {
            //Mettre à jour l'interface updateGameList()
            iDataCom.updateGameList(user.getLightPublicUser(), game.getId(), role);
        } catch (DataException ex) {
            Logger.getLogger(M_JoinGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setRole(String r){
        role = r;
    }
}
