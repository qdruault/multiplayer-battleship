/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.rmi.server.UID;
import java.util.Date;

/**
 *
 * @author Davy
 */
public class PublicUser extends SerializableEntity{
    private LightPublicUser lightPublicUser;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private byte[] avatar;

    public PublicUser() {
    }
    
    
    
    public PublicUser(LightPublicUser lightPublicUser, String lastName, String firstName, Date birthDate){
        this.lightPublicUser = lightPublicUser;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
    }

    public LightPublicUser getLightPublicUser() {
        return lightPublicUser;
    }

    public void setLightPublicUser(LightPublicUser lightPublicUser) {
        this.lightPublicUser = lightPublicUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
     @JsonIgnore
    public String getId()
    {
        return this.getLightPublicUser().getId();
  
    }
    
     @JsonIgnore
    public String getPlayerName()
    {
        return this.getLightPublicUser().getPlayerName();
    }
    
    public byte[] getAvatar() {
        return avatar;
    }

    
    
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
