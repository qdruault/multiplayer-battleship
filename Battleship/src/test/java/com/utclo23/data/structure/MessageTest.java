/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data.structure;

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
public class MessageTest {
    
    public MessageTest() {
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
     * Test of getSender method, of class Message.
     */
    @Test
    public void testGetSender() {
        System.out.println("getSender");
        Message instance = new Message();
        LightPublicUser expResult = null;
        LightPublicUser result = instance.getSender();
        assertEquals(expResult, result);
    }

    /**
     * Test of setSender method, of class Message.
     */
    @Test
    public void testSetSender() {
        System.out.println("setSender");
        LightPublicUser sender = null;
        Message instance = new Message();
        instance.setSender(sender);
        
    }

    /**
     * Test of getContent method, of class Message.
     */
    @Test
    public void testGetContent() {
        System.out.println("getContent");
        Message instance = new Message();
        String expResult = "";
        String result = instance.getContent();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of setContent method, of class Message.
     */
    @Test
    public void testSetContent() {
        System.out.println("setContent");
        String content = "";
        Message instance = new Message();
        instance.setContent(content);
       
    }

    /**
     * Test of getRecipients method, of class Message.
     */
    @Test
    public void testGetRecipients() {
        System.out.println("getRecipients");
        Message instance = new Message();
        List<LightPublicUser> expResult = null;
        List<LightPublicUser> result = instance.getRecipients();
        assertNotEquals(expResult, result);
        
    }

    /**
     * Test of setRecipients method, of class Message.
     */
    @Test
    public void testSetRecipients() {
        System.out.println("setRecipients");
        List<LightPublicUser> recipients = null;
        Message instance = new Message();
        instance.setRecipients(recipients);
        
    }
    
}
