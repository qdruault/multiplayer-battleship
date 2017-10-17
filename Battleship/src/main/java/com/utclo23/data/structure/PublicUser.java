/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.util.Date;

/**
 *
 * @author Davy
 */
public class PublicUser {
    private LightPublicUser lightPublicUser;
    private String lastName;
    private String firstName;
    private Date birthDate;
    //TODO avatar : picture

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
    
    
}
