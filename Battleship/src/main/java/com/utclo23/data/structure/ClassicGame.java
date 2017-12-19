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
        //1 porte-avions (5) = CARRIER
        //1 croiseur (4) = BATTLESHIP
        //1 contre-torpilleur (3) = AGAINSTDESTROYER
        //1 sous-marin (3) = SUBMARINE
        //1 torpilleur (2) = DESTROYER

        List<Ship> ships = new ArrayList<Ship>();
        ships.add(new Ship(ShipType.CARRIER, 5));
        ships.add(new Ship(ShipType.BATTLESHIP, 4));
        ships.add(new Ship(ShipType.AGAINSTDESTROYER, 3));
        ships.add(new Ship(ShipType.SUBMARINE, 3));
        ships.add(new Ship(ShipType.DESTROYER, 2));
      
        return ships;
    }
}
