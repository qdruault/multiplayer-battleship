/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;
import java.util.Date;
/**
 *
 * @author thibault
 */
public class M_Chat extends Message {
    private com.utclo23.data.structure.Message message;
    private Date timeStamp; 
    
    public M_Chat(PublicUser user, com.utclo23.data.structure.Message m, Date t){
        super(user);
        message = m;
        timeStamp = t;
    }
    
    @Override
    public void callback(IDataCom iDataCom){
        iDataCom.forwardMessage(this.message);
    }
}
