/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.battleship;

/**
 *
 * Basic exception for project
 * @author Davy
 */
public class GameException extends Exception {

    public GameException() {
    }

    public GameException(String message) {
        super(message);
    }
    
}
