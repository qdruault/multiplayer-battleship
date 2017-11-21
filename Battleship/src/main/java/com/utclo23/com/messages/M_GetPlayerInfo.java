/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.com.Sender;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;
/**
 * M_GetPlayerInfo is a Message sent to request detailed information about a
 * specific user.
 * @author Thibault CHICHE
 */
public class M_GetPlayerInfo extends Message {
    
    /**
     * Constructor.
     * @param user is the message's sender
     */
    public M_GetPlayerInfo(PublicUser user){
        super(user);
    }
    
    @Override
    public void callback(IDataCom iDataCom){
        PublicUser my_profile = iDataCom.getMyPublicUserProfile();
        M_PlayerInfo m_PlayerInfo = new M_PlayerInfo(my_profile);
        Sender os = new Sender(this.IP_sender.getHostAddress(), 80, m_PlayerInfo);
        new Thread(os).start();
    }
}
