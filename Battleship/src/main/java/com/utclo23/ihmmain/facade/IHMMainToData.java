/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.facade;

import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.PublicUser;
import java.io.IOException;

/**
 *
 * @author Linxuhao
 */
public interface IHMMainToData {
    /**
     * refreshes the player list if player list is currently displaying
     * @throws IOException 
     */
    public abstract void refreshUserList() throws IOException;
    
    /**
     * refreshes the game list if player list is currently displaying
     * @throws IOException 
     */
    public abstract void refreshGameList() throws IOException;
    
     /**
     * obtain info of other player asked by user
     * @param player
     * @throws IOException 
     */
    public abstract void recievePublicUserProfile(PublicUser player) throws IOException;
    
   /**
   * receive game i asked for !
   * @param game 
   */
    public abstract void receptionGame(Game game);
    
    public abstract void connectionImpossible();
    
}
