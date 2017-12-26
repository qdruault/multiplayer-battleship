/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.utclo23.data.structure.Coordinate;

import com.utclo23.data.structure.Ship;

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
public class CoordinateTest extends TestCase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void testCoord() 
    {
       
        Coordinate c = new Coordinate(1,2);
        c.setX(0);
        c.setY(0);
        
        if(c.getX()!=0 || c.getY()!=0)
        {
            fail();
        }
    }  
    
    @Test
    public void testEqualsCoordinate() 
    {
        Coordinate c = new Coordinate(1,2);
        c.setX(0);
        c.setY(0);
        
        Coordinate c2 = new Coordinate(1,2);
        c2.setX(0);
        c2.setY(0);
        
        if(!c.equals(c2))
        {
            fail();
        }
        
    } 
    
    
    @Test
    public void testAllowed() 
    {
        Coordinate c = new Coordinate(1,2);
        c.setX(1);
        c.setY(0);
        
        
        List<Ship> ships= new ArrayList<>();
        Ship ship = new Ship();
        ship.setSize(1);
        ship.getListCoord().add(new Coordinate(0,0));
        if(!c.isAllowed(ships))
        {
            fail();
        }

        
    } 
      
  
    
}
