/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.utclo23.data.structure.ClassicGame;
import com.utclo23.data.structure.ComputerPlayer;
import com.utclo23.data.structure.Coordinate;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.Mine;
import com.utclo23.data.structure.Ship;
import com.utclo23.data.structure.ShipType;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
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
public class ComputerPlayerTest {

    public ComputerPlayerTest() {
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
     * Test of loseFocus method, of class ComputerPlayer.
     */
    @Test
    public void testLoseFocus() {
        System.out.println("loseFocus");

        Ship ship = new Ship();
        for (int i = 0; i < 2; ++i) {
            ship.getListCoord().add(new Coordinate(i, i));
        }

        ship.setSize(2);
        ship.setType(ShipType.BATTLESHIP);

        ComputerPlayer instance = new ComputerPlayer();
        Coordinate c = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(0, 1);
        Coordinate c3 = new Coordinate(1, 1);
        Coordinate c4 = new Coordinate(1, 0);
        instance.setFocus(c);
        instance.setFocus(c2);
        instance.setFocus(c3);
        instance.setFocus(c4);

        instance.loseFocus(ship);

    }

    /**
     * Test of getFocus method, of class ComputerPlayer.
     */
    @Test
    public void testGetFocus() {
        System.out.println("getFocus");
        ComputerPlayer instance = new ComputerPlayer();

        Coordinate c = new Coordinate(0, 0);
        instance.setFocus(c);
        Coordinate result = instance.getFocus();
        assertEquals(c, result);

    }

    /**
     * Test of setFocus method, of class ComputerPlayer.
     */
    @Test
    public void testSetFocus() {
        System.out.println("getFocus");
        ComputerPlayer instance = new ComputerPlayer();
        Coordinate expResult = null;
        Coordinate c = new Coordinate(0, 0);
        instance.setFocus(c);

    }

    /**
     * Test of setShips method, of class ComputerPlayer.
     */
    @Test
    public void testSetShips() {
        System.out.println("setShips");
        List<Ship> ships = null;
        ComputerPlayer instance = new ComputerPlayer();
        ClassicGame game = new ClassicGame();

        instance.setShips(game.getTemplateShips());

    }

    /**
     * Test of randomMine method, of class ComputerPlayer.
     */
    @Test
    public void testRandomMine() {
        Game game = new ClassicGame();
        System.out.println("randomMine");

        Ship ship = new Ship();
        for (int i = 0; i < 2; ++i) {
            ship.getListCoord().add(new Coordinate(i, i));
        }

        ship.setSize(2);
        ship.setType(ShipType.BATTLESHIP);

        List<Ship> ships = new ArrayList<>();
        ships.add(ship);

        ComputerPlayer instance = new ComputerPlayer();
        Mine expResult = null;
        Mine result = instance.randomMine(ships, game);
   
        assertNotEquals(expResult, result);
        
       Coordinate c = new Coordinate(0,2);
       instance.setFocus(c);
       
        result = instance.randomMine(ships, game);
        assertNotEquals(expResult, result);
       

    }

    /**
     * Test of getStackFocus method, of class ComputerPlayer.
     */
    @Test
    public void testGetStackFocus() {
        System.out.println("getStackFocus");
        ComputerPlayer instance = new ComputerPlayer();
        Deque<Coordinate> expResult = null;
        Deque<Coordinate> result = instance.getStackFocus();
        assertNotEquals(expResult, result);

    }

    /**
     * Test of setStackFocus method, of class ComputerPlayer.
     */
    @Test
    public void testSetStackFocus() {
        System.out.println("setStackFocus");
        Deque<Coordinate> stackFocus = null;
        ComputerPlayer instance = new ComputerPlayer();
        instance.setStackFocus(stackFocus);

    }

}
