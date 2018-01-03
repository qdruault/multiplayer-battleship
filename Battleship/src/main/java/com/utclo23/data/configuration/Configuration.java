/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.configuration;

import java.io.File;

/**
 *
 * Configuration
 * All properties related to Data module
 */
public class Configuration {

   
    
    /**
     * directory of saved profiles
     */
    public static final String SAVE_DIR = System.getProperty("user.home")+File.separator+"MyData";
    public static final int WIDTH  = 10;
    public static final int HEIGHT = 10;
    public static final String PLAYER = "player";
    public static final String SPECTATOR = "spectator";
    
    
}
