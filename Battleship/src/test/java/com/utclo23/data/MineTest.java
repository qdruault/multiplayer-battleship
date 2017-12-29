/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.MineResult;
import com.utclo23.data.structure.Owner;
import com.utclo23.data.structure.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Davy
 */
public class MineTest {
    
    public MineTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getResult method, of class Mine.
     */
    @Test
    public void testGetResult() {
        System.out.println("getResult");
        Mine instance = new Mine();
        MineResult expResult =  MineResult.UNKNOWN; ;
        MineResult result = instance.getResult();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setResult method, of class Mine.
     */
    @Test
    public void testSetResult() {
        System.out.println("setResult");
        MineResult result_2 = MineResult.FAILURE;
        Mine instance = new Mine();
        instance.setResult(result_2);
      
    }

    /**
     * Test of getOwner method, of class Mine.
     */
    @Test
    public void testGetOwner() {
        System.out.println("getOwner");
        Mine instance = new Mine();
        Player expResult = null;
        Player result = instance.getOwner();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setOwner method, of class Mine.
     */
    @Test
    public void testSetOwner() {
        System.out.println("setOwner");
        Player owner = new Player();
        Mine instance = new Mine();
        instance.setOwner(owner);
        
    }

    /**
     * Test of getCoord method, of class Mine.
     */
    @Test
    public void testGetCoord() {
        System.out.println("getCoord");
        Mine instance = new Mine();
        Coordinate expResult = null;
        Coordinate result = instance.getCoord();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setCoord method, of class Mine.
     */
    @Test
    public void testSetCoord() {
        System.out.println("setCoord");
        Coordinate coord = new Coordinate(0,0);
        Mine instance = new Mine();
        instance.setCoord(coord);
        
    }
    
}
