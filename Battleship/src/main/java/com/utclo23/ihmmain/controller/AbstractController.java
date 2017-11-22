/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmmain.controller;

import com.utclo23.ihmmain.IHMMain;
import com.utclo23.ihmmain.facade.IHMMainFacade;
import java.io.IOException;

/**
 * upper class of all ihm-main controller class, contain IHMMain class
 * @author Linxuhao
 */
public class AbstractController {
    /**
     * the reference of ihmmain, to jump between scenes
     */
    public IHMMain ihmmain;
    
    public IHMMainFacade facade;
    
    private boolean isRunning;

    public IHMMainFacade getFacade() {
        return facade;
    }

    public void setFacade(IHMMainFacade facade) {
        this.facade = facade;
    }

    public IHMMain getIhmmain() {
        return ihmmain;
    }

    public void setIhmmain(IHMMain ihmmain) {
        this.ihmmain = ihmmain;
    }

    public boolean isIsRunning() {
        return isRunning;
    }
    
    public void init(){
        stop();
    }
    
    public void start(){
        this.isRunning = true;
    }
    
    public void stop(){
        this.isRunning = false;
    }
    
    /**
     * Override this method to refresh the page when isRunning is true
     * @throws IOException 
     */
    public void refresh() throws IOException {
    }
}
