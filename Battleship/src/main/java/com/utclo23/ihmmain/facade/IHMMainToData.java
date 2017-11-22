/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.facade;

import java.io.IOException;
import javafx.stage.Stage;

/**
 *
 * @author Linxuhao
 */
public interface IHMMainToData {
    /**
     * refreshes the player list if player list is currently displaying
     * @throws IOException 
     */
    public abstract void refreshPlayerList() throws IOException;
    
    /**
     * refreshes the game list if player list is currently displaying
     * @throws IOException 
     */
    public abstract void refreshGameList() throws IOException;
}
