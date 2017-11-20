/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import java.io.Serializable;
import com.utclo23.data.structure.PublicUser;
import java.net.Inet4Address;
import com.utclo23.data.facade.IDataCom;
import java.net.UnknownHostException;
/**
 *
 * @author remid
 */
public abstract class Message implements Serializable{
    protected PublicUser user;
    protected Inet4Address IP_sender;
    public abstract void callback(IDataCom iDataCom);
    
    public void Message(IDataCom idc) throws UnknownHostException{
        setUser(idc.getMyPublicUserProfile());
        setIpSender((Inet4Address)Inet4Address.getLocalHost());
    }
    
    public void setIpSender(Inet4Address ip){
        IP_sender = ip;
    }
    public void setUser(PublicUser u){
        user = u;
    }
}