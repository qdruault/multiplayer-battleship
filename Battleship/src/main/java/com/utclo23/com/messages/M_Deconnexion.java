/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;
/**
 *
 * @author thibault
 */
public class M_Deconnexion extends Message {
    public PublicUser user;
    
    public M_Deconnexion(PublicUser u){
        user = u;
    }
    
    @Override
    public void callback(IDataCom iDataCom){
        iDataCom.removeConnectedUser(user.getLightPublicUser());
    }
}
