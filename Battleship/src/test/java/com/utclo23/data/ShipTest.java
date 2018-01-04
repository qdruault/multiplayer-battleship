/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utclo23.data.module.GameFactory;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import com.utclo23.data.structure.Player;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.ShipType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Davy
 */
public class ShipTest extends TestCase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void testShip() 
    {
        Player player = new Player(LightPublicUser.generateComputerProfile());
        List<Coordinate> listCoord = new ArrayList<>();
        for(int i = 0; i<5; ++i)
        {
            listCoord.add(new Coordinate(0,0));
        }
        
        Ship ship = new Ship(ShipType.CARRIER, player,  listCoord, 5);
        if(!ship.getType().equals(ShipType.CARRIER) || ship.getSize()!=5 || ship.getListCoord().size()!=listCoord.size())
        {
            fail();
        }
    }  
    
    @Test
    public void testEqualsShip() 
    {
        Player player = new Player(LightPublicUser.generateComputerProfile());
        List<Coordinate> listCoord = new ArrayList<>();
        for(int i = 0; i<5; ++i)
        {
            listCoord.add(new Coordinate(0,0));
        }
        
        Ship ship = new Ship(ShipType.CARRIER, player,  listCoord, 5);
        Ship ship2 = new Ship(ShipType.CARRIER, player,  listCoord, 5);
        
        if(!ship.equals(ship2)|| ship.hashCode()!=ship2.hashCode())
        {
            fail();
        }
    } 
      
  
    
}
