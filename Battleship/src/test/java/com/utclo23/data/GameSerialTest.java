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
import com.utclo23.data.structure.Player;
import java.io.IOException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Davy
 */
public class GameSerialTest {
    
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    @Test
    public void testSerial() throws JsonProcessingException, IOException
    {
        GameFactory gameFactory = new GameFactory();
        Game game = gameFactory.createGame("TEST-game", LightPublicUser.generateComputerProfile(), true, true, true, GameType.CLASSIC);
        
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(game);
        System.out.println("game "+game.getStatGame().getName());
        
        System.out.println(s);
        
        game = objectMapper.readValue(s, Game.class);
        System.out.println("game "+game.getStatGame().getName());
  
        
        
        
        
    }        
     
  
    
}
