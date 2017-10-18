/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;
import com.utclo23.data.structure.PublicUser;
import java.util.List;
/**
 *
 * @author thibault
 */
public class M_ReturnIP extends Message{
    public List<PublicUser> listPublicUser;
    
    @Override
    public void callback(){
        
    }
    
}
