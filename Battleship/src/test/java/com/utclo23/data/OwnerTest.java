/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.Owner;
import com.utclo23.data.structure.PublicUser;
import com.utclo23.data.structure.StatGame;
import java.util.ArrayList;
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
public class OwnerTest {
    
    public OwnerTest() {
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
     * Test of getUserIdentity method, of class Owner.
     */
    @Test
    public void testGetUserIdentity() {
        System.out.println("getUserIdentity");
        Owner instance = new Owner();
        
        PublicUser expResult = null;
        PublicUser result = instance.getUserIdentity();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setUserIdentity method, of class Owner.
     */
    @Test
    public void testSetUserIdentity() {
        System.out.println("setUserIdentity");
        PublicUser userIdentity = new PublicUser();
        Owner instance = new Owner();
        instance.setUserIdentity(userIdentity);
        
        
    }

    /**
     * Test of getPassword method, of class Owner.
     */
    @Test
    public void testGetPassword() {
        System.out.println("getPassword");
        Owner instance = new Owner();
        instance.setPassword("a");
        String expResult = "a";
        String result = instance.getPassword();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setPassword method, of class Owner.
     */
    @Test
    public void testSetPassword() {
        System.out.println("setPassword");
        String password = "aaa";
        Owner instance = new Owner();
        instance.setPassword(password);
        
    }

    /**
     * Test of getDiscoveryNodes method, of class Owner.
     */
    @Test
    public void testGetDiscoveryNodes() {
        System.out.println("getDiscoveryNodes");
        Owner instance = new Owner();
        List<String> expResult = null;
        List<String> result = instance.getDiscoveryNodes();
        assertNotEquals(expResult, result);
        
    }

    /**
     * Test of setDiscoveryNodes method, of class Owner.
     */
    @Test
    public void testSetDiscoveryNodes() {
        System.out.println("setDiscoveryNodes");
        List<String> discoveryNodes = new ArrayList<>();
        Owner instance = new Owner();
        instance.setDiscoveryNodes(discoveryNodes);
        
    }

    /**
     * Test of getSavedGamesList method, of class Owner.
     */
    @Test
    public void testGetSavedGamesList() {
        System.out.println("getSavedGamesList");
        Owner instance = new Owner();
        List<Game> expResult = null;
        List<Game> result = instance.getSavedGamesList();
        assertNotEquals(expResult, result);
        
    }

    /**
     * Test of setSavedGamesList method, of class Owner.
     */
    @Test
    public void testSetSavedGamesList() {
        System.out.println("setSavedGamesList");
        List<Game> savedGamesList = new ArrayList<>();
        Owner instance = new Owner();
        instance.setSavedGamesList(savedGamesList);
        
    }

    /**
     * Test of getPlayedGamesList method, of class Owner.
     */
    @Test
    public void testGetPlayedGamesList() {
        System.out.println("getPlayedGamesList");
        Owner instance = new Owner();
        List<StatGame> expResult = null;
        List<StatGame> result = instance.getPlayedGamesList();
        assertNotEquals(expResult, result);
       
    }

    /**
     * Test of setPlayedGamesList method, of class Owner.
     */
    @Test
    public void testSetPlayedGamesList() {
        System.out.println("setPlayedGamesList");
        List<StatGame> playedGamesList = null;
        Owner instance = new Owner();
        instance.setPlayedGamesList(playedGamesList);
        
    }

    /**
     * Test of addPlayedGame method, of class Owner.
     */
    @Test
    public void testAddPlayedGame() {
        System.out.println("addPlayedGame");
        StatGame game = new StatGame();
        Owner instance = new Owner();
        instance.addPlayedGame(game);
       
        
    }
    
}
