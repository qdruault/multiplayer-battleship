/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.facade.IDataCom;
import com.utclo23.data.structure.PublicUser;

/**
 * M_Chat is a Message generated when a user sends a new chat message. 
 * @author Thibault CHICHE 
 */
public class M_Chat extends Message {
    private final com.utclo23.data.structure.Message message;
    
    /**
     * Constructor.  
     * @param user is the message's sender
     * @param messageContent is the content of the message
     */
    public M_Chat(PublicUser user, com.utclo23.data.structure.Message messageContent){
        super(user);
        message = messageContent;
    }
    
    
    @Override
    public void callback(IDataCom iDataCom){
        iDataCom.forwardMessage(this.message);
    }
}
