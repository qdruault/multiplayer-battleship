/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.com.Sender;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author thibault
 */
public class M_GetPlayerInfo extends Message {
    
    public M_GetPlayerInfo(IDataCom iDataCom) throws UnknownHostException{
        super(iDataCom);
    }
    
    @Override
    public void callback(){
        PublicUser user = iDataCom.getMyPublicUserProfile();
        M_PlayerInfo m_PlayerInfo;
        try {
            m_PlayerInfo = new M_PlayerInfo(iDataCom, user);
   
        Sender os = new Sender(this.IP_sender.getHostAddress(), 80, m_PlayerInfo);
        new Thread(os).start();
             } catch (UnknownHostException ex) {
            Logger.getLogger(M_GetPlayerInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
