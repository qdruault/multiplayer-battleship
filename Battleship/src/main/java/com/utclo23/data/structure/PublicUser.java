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
    private int numberDefeatsClassic;
    @JsonIgnore
    private int numberVictoriesClassic;
    @JsonIgnore
    private int numberAbandonsClassic;
    @JsonIgnore
    private int numberDefeatsBelgian;
    @JsonIgnore
    private int numberVictoriesBelgian;
    @JsonIgnore
    private int numberAbandonsBelgian;

    @JsonIgnore
    public int getNumberDefeatsClassic() {
        return numberDefeatsClassic;
    }
    @JsonIgnore
    public int getNumberDefeatsBelgian() {
        return numberDefeatsBelgian;
    }

    @JsonIgnore
    public void setNumberDefeatsClassic(int nbLost) {
        this.numberDefeatsClassic = nbLost;
    }
    @JsonIgnore
    public void setNumberDefeatsBelgian(int nbLost) {
        this.numberDefeatsBelgian = nbLost;
    }

    @JsonIgnore
    public int getNumberVictoriesClassic() {
        return numberVictoriesClassic;
    }
    @JsonIgnore
    public int getNumberVictoriesBelgian() {
        return numberVictoriesBelgian;
    }

    @JsonIgnore
        public void setNumberVictoriesClassic(int nbWin) {
        this.numberVictoriesClassic = nbWin;
    }
    @JsonIgnore
        public void setNumberVictoriesBelgian(int nbWin) {
        this.numberVictoriesBelgian = nbWin;
    }

    @JsonIgnore
    public int getNumberAbandonsClassic() {
        return numberAbandonsClassic;
    }
    @JsonIgnore
    public int getNumberAbandonsBelgian() {
        return numberAbandonsBelgian;
    }
    @JsonIgnore
    public void setNumberAbandonsClassic(int nbOther) {
        this.numberAbandonsClassic = nbOther;
    }
    @JsonIgnore
    public void setNumberAbandonsBelgian(int nbOther) {
        this.numberAbandonsBelgian = nbOther;
    }

    /**
     *
     */
    public PublicUser() {
        this.numberDefeatsClassic = 0;
        this.numberDefeatsBelgian = 0;
        this.numberVictoriesClassic = 0;
        this.numberVictoriesBelgian = 0;
        this.numberAbandonsClassic = 0;
        this.numberAbandonsBelgian = 0;
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

        this.numberDefeatsClassic = 0;
        this.numberAbandonsClassic= 0;
        this.numberVictoriesClassic = 0;
        this.numberDefeatsBelgian = 0;
        this.numberAbandonsBelgian= 0;
        this.numberVictoriesBelgian = 0;
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
