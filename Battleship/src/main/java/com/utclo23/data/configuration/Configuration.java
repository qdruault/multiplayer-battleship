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

    public Configuration() {
    }
    
    /**
     * directory of saved profiles
     */
    public final static String SAVE_DIR = System.getProperty("user.home")+File.separator+"MyData";
   
    
    
}
