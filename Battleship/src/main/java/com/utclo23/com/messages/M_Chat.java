/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import java.util.Date;
/**
 *
 * @author thibault
 */
public class M_Chat extends Message {
    private com.utclo23.data.structure.Message message;
    private Date timeStamp; 
    
    public M_Chat(com.utclo23.data.structure.Message m, Date t){
        message = m;
        timeStamp = t;
    }
    
    @Override
    public void callback(IDataCom iDataCom){
        iDataCom.forwardMessage(this.message);
    }
}
