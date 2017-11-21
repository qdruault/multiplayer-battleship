/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.constants;

/**
 *a enum class to save all scene names, in order to automaticly generate scenes from this class
 * @author Linxuhao
 */
public enum SceneName {
    /**
     * name of login scene
     */
    LOGIN("Login"),
    /**
     * name of create user scene
     */
    CREATE_USER("CreateUser"),
    /**
     * name of menu scene
     */
    MENU("Menu"),
    /**
     * name of player list scene
     */
    PLAYER_LIST("PlayerList"),
    /**
     * name of player profile scene
     */
    PLAYER_PROFILE("PlayerProfile"),
    /**
     * name of list of ip scene
     */
    IP_LIST("IpList"),
    /**

     * name of create game scene
     */
    CREATE_GAME("CreateGame"),

     /* name of play a game scene
     */

    GAME_LIST("GameList");
    
    private String name;
    SceneName(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
