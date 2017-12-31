/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

/**
 *
 * @author Davy
 */
public class PublicUser extends SerializableEntity {

    private LightPublicUser lightPublicUser;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private byte[] avatar;

    @JsonIgnore
    private int numberDefeats;
    @JsonIgnore
    private int numberVictories;
    @JsonIgnore
    private int numberAbandons;

    @JsonIgnore
    public int getNumberDefeats() {
        return numberDefeats;
    }

    @JsonIgnore
    public void setNumberDefeats(int nbLost) {
        this.numberDefeats = nbLost;
    }

    @JsonIgnore
    public int getNumberVictories() {
        return numberVictories;
    }

    @JsonIgnore
        public void setNumberVictories(int nbWin) {
        this.numberVictories = nbWin;
    }

    @JsonIgnore
    public int getNumberAbandons() {
        return numberAbandons;
    }

    @JsonIgnore
    public void setNumberAbandons(int nbOther) {
        this.numberAbandons = nbOther;
    }

    /**
     *
     */
    public PublicUser() {
        this.numberDefeats = 0;
        this.numberVictories = 0;
        this.numberAbandons = 0;
    }

    /**
     *
     * @param lightPublicUser
     * @param lastName
     * @param firstName
     * @param birthDate
     */
    public PublicUser(LightPublicUser lightPublicUser, String lastName, String firstName, Date birthDate) {
        this.lightPublicUser = lightPublicUser;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;

        this.numberDefeats = 0;
        this.numberAbandons = 0;
        this.numberVictories = 0;
    }

    /**
     *
     * @return
     */
    public LightPublicUser getLightPublicUser() {
        return lightPublicUser;
    }

    /**
     *
     * @param lightPublicUser
     */
    public void setLightPublicUser(LightPublicUser lightPublicUser) {
        this.lightPublicUser = lightPublicUser;
    }

    /**
     *
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     *
     * @param birthDate
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public String getId() {
        return this.getLightPublicUser().getId();

    }

    /**
     *
     * @return
     */
    @JsonIgnore
    public String getPlayerName() {
        return this.getLightPublicUser().getPlayerName();
    }

    /**
     *
     * @return
     */
    public byte[] getAvatar() {
        return avatar;
    }

    /**
     *
     * @param avatar
     */
    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
