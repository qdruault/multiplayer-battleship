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
public class M_CreationGame extends Message{
    public StatGame game;
    
    public M_CreationGame(StatGame g){
        game = g;
    }
    
    @Override
    public void callback(IDataCom iDataCom){
        iDataCom.addNewGame(game);
    }
}
