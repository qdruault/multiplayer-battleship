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
public interface IHMMainToIhmTable {
    /**

     */
    /**
     * return to menu page
     * linxuhao
     * @throws IOException 
     */
    public abstract void toMenu() throws IOException;
    
    public abstract Stage getPrimaryStage();
    
}
