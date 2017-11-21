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
 * M_Chat is a Message generated when a user sends a new chat message. 
 * 
 * @author Thibault CHICHE 
 */
public class M_Chat extends Message {
    private com.utclo23.data.structure.Message message;
    private Date timeStamp; 
    
    /**
     * Constructor.  
     * @param user is the player who sent the message.
     * @param message_content is the content of the message. 
     * @param date is the date in which the message was sent. 
     */
    public M_Chat(PublicUser user, com.utclo23.data.structure.Message message_content, Date date){
        super(user);
        message = message_content;
        timeStamp = date;
    }
    
    
    @Override
    public void callback(IDataCom iDataCom){
        iDataCom.forwardMessage(this.message);
    }
}
