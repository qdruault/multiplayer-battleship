/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.StatGame;
/**
 *
 * @author thibault
 */
public class M_JoinGame extends Message {
    private StatGame game;
    private String role;
    
    public M_JoinGame(StatGame g) {
        game = g;
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
