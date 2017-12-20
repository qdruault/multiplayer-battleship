/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.ihmtable;

import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.StatGame;
import com.utclo23.data.structure.Message;

/**
 *
 * @author pjeannot
 */
public interface IIHMTableToData {
    public void notifyGameReady();
    public void printMessage(Message message);
    public void feedBack(Coordinate coord, boolean bool, Ship destroyedShip);
    public void finishGame(StatGame stGame);
    public void opponentHasLeftGame();
    public void connectionLostWithOpponent();
}
