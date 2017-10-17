/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public class Message extends Event{
    private LightPublicUser sender;
    private String content;
    private ArrayList<LightPublicUser> recipients;

    public Message(LightPublicUser sender, String content, ArrayList<LightPublicUser> recipients) {
        this.sender = sender;
        this.content = content;
        this.recipients = recipients;
    }

    public LightPublicUser getSender() {
        return sender;
    }

    public void setSender(LightPublicUser sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<LightPublicUser> getRecipients() {
        return recipients;
    }

    public void setRecipients(ArrayList<LightPublicUser> recipients) {
        this.recipients = recipients;
    }
    
    
    
}
