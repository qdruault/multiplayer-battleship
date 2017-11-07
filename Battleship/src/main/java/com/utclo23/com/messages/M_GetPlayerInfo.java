/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import com.utclo23.com.OutSocket;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;
/**
 *
 * @author thibault
 */
public class M_GetPlayerInfo extends Message {
    @Override
    public void callback(IDataCom iDataCom){
        PublicUser user = iDataCom.getMyPublicUserProfile();
        M_PlayerInfo m_PlayerInfo = new M_PlayerInfo(user);
        OutSocket sender = new OutSocket(this.IP_sender.toString(), 80, m_PlayerInfo);
    }
}
