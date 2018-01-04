/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utclo23.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.utclo23.data.module.GameFactory;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;
import java.io.IOException;
import junit.framework.TestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Davy
 */
public class GameTest extends TestCase {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void testClassicShips() 
    {
        GameFactory gameFactory = new GameFactory();
        Game game = gameFactory.createGame("TEST-game", LightPublicUser.generateComputerProfile(), true, true, true, GameType.CLASSIC);
        
        if(game == null || game.getTemplateShips() == null || game.getTemplateShips().size()!=5)
        {
            fail();
        }
    }        
     
    @Test
    public void testBelgianShips() 
    {
        GameFactory gameFactory = new GameFactory();
        Game game = gameFactory.createGame("TEST-game", LightPublicUser.generateComputerProfile(), true, true, true, GameType.BELGIAN);
        
        if(game == null || game.getTemplateShips() == null || game.getTemplateShips().size()!=10)
        {
            fail();
        }
    }        
     
  
    
}
