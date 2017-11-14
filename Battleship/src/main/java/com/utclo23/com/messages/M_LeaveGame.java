/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.data.facade.IDataCom;
import java.net.UnknownHostException;

/**
 *
 * @author thibault
 */
public class M_LeaveGame extends Message {
    
    public M_LeaveGame(IDataCom iDataCom) throws UnknownHostException{
        super(iDataCom);
    }
    
    @Override
    public void callback(){
        iDataCom.opponentHasLeftGame();
    }
}
