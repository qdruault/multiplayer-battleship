/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.util.ArrayList;
import java.util.List;

/**
 * Classic game
 * it extends Game
 * @author Davy
 */
public class ClassicGame extends Game{

    
    public ClassicGame(StatGame statGame, List<Player> players, List<LightPublicUser> spectators, List<Message> messages) {
        super(statGame, players, spectators, messages);
    }
    
     /**
     * get templates of ships for classic game
     * @return 
     */
    
    public  List<Ship> getTemplateShips()
    {
        List<Ship> ships = new ArrayList<Ship>();
        ships.add(new Ship(ShipType.CARRIER, 5));
        ships.add(new Ship(ShipType.BATTLESHIP, 4));
        ships.add(new Ship(ShipType.CRUISER, 3));
        ships.add(new Ship(ShipType.SUBMARINE, 2));
        ships.add(new Ship(ShipType.DESTROYER, 1));
      
        return ships;
    }
}
