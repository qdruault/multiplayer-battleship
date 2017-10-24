/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.com.messages;

import java.io.Serializable;
import com.utclo23.data.structure.PublicUser;

/**
 *
 * @author remid
 */
public abstract class Message implements Serializable{
    PublicUser user;
    String name;
    
    public abstract void callback();
}