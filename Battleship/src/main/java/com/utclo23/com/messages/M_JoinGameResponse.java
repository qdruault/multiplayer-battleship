/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.data.facade.IDataCom;

/**
 *
 * @author thibault
 */
public class M_JoinGameResponse extends Message {
    private boolean success;
    
    public M_JoinGameResponse(boolean s){
        success = s;
    }
    @Override
    public void callback(IDataCom iDataCom){

    }
}
