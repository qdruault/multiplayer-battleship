/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.Ship;
import java.util.List;
/**
 * M_PlaceShip is a Message generated after a player placed all his ships,
 * sent to all users connected to the game.
 * @author Thibault CHICHE
 */
public class M_PlaceShip extends Message {
    private final List<Ship> ships;

    /**
    * Constructor.
    * @param user is the message's sender
    * @param ships is the coordinates list of the ships
    */
    public M_PlaceShip(PublicUser user, List<Ship> ships){
        super(user);
        this.ships = ships;
    }
    @Override
    public void callback(IDataCom iDataCom) {
        iDataCom.setEnnemyShips(ships);
    }
}
