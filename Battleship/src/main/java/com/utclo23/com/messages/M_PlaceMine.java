/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.Mine;
/**
 *
 * @author thibault
 */
public class M_PlaceMine extends Message {
    public Mine mine;
    
    @Override
    public void callback(IDataCom iDataCom) {
        iDataCom.forwardCoordinates(mine);
    }
}
