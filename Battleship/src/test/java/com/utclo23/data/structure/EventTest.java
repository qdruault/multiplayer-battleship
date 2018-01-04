/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

import java.util.Date;
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
public class EventTest {
    
    public EventTest() {
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
     * Test of getTimestamp method, of class Event.
     */
    @Test
    public void testGetTimestamp() {
        System.out.println("getTimestamp");
        Event instance = new EventImpl();
        Date expResult = null;
        Date result = instance.getTimestamp();
        assertNotEquals(expResult, result);
        
    }

    /**
     * Test of setTimestamp method, of class Event.
     */
    @Test
    public void testSetTimestamp() {
        System.out.println("setTimestamp");
        Date timestamp = new Date();
        Event instance = new EventImpl();
        instance.setTimestamp(timestamp);
       
    }

    public class EventImpl extends Event {
        
    }
    
}
