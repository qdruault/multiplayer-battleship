/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.Ship;
import java.util.List;
/**
 *
 * @author thibault
 */
public class M_PlaceShip extends Message {
    private List<Ship> ships;
    
    public M_PlaceShip(List<Ship> s){
        ships = s;
    }
    @Override
    public void callback(IDataCom iDataCom) {
        iDataCom.setEnnemyShips(ships);
    }
}
