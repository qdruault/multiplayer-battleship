/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.module;

import com.utclo23.battleship.GameException;

/**
 *
 * @author Davy
 */
public class DataException extends GameException 
{

    public DataException(String message) {
        super(message);
    }
    
}
