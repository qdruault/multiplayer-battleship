/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.PublicUser;
/**
 * M_PlaceMine is a Message to send the position of a new placed mine.
 * @author Thibault CHICHE
 */
public class M_PlaceMine extends Message {
    private final Mine mine;
    
    /**
     * Constructor.
     * @param user is the message's sender
     * @param mine is a mine object
     */
    public M_PlaceMine(PublicUser user, Mine mine){
        super(user);
        this.mine = mine;
    }
    @Override
    public void callback(IDataCom iDataCom) {
        iDataCom.forwardCoordinates(mine);
    }
}
