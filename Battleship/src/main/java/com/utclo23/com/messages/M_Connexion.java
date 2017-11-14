/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;
import java.net.UnknownHostException;
/**
 *
 * @author thibault
 */
public class M_Connexion extends Message{
    public PublicUser user;
    
    public M_Connexion(IDataCom iDataCom, PublicUser u) throws UnknownHostException{
        super(iDataCom);
        user = u;
    }
    
    @Override
    public void callback(){

    }
}
