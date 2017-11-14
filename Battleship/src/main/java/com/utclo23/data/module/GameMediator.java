package com.utclo23.data.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.utclo23.com.ComFacade;
import com.utclo23.data.configuration.Configuration;
import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.structure.Game;
import com.utclo23.data.structure.GameType;
import com.utclo23.data.structure.LightPublicUser;

import com.utclo23.data.structure.StatGame;
import java.io.File;
import java.rmi.server.UID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Game Mediator related to games features
 * @author tboulair
 */
public class GameMediator {
    /**
     * reference to the data facade
     */
    private DataFacade dataFacade;

    private Map<String, StatGame> gamesList; 
    private GameFactory gameFactory;


    /**
     * Constructor 
     */
    public GameMediator(DataFacade dataFacade) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Création du Game Mediator");
        
        this.dataFacade = dataFacade;
        this.gamesList = new HashMap<>();
    }
    
  /**
     * Create a game
     * @param name
     * @param spectator
     * @param spectatorChat
     * @param type
     */

    public Game createGame(String name, boolean spectator, boolean spectatorChat, GameType type) throws DataException{
        //empty game name
        if(name.isEmpty()){
            throw new DataException("Data: error due to empty game name ");
        }
        
        //uppercase game name
        name = name.toUpperCase();
        
        //get information of creator
        LightPublicUser creator = this.dataFacade.getMyPublicUserProfile().getLightPublicUser();
       
        //creat Game for realGame
        Game game = this.gameFactory.createGame(type);
        
        //create game
        String id = new UID().toString();
        StatGame statgame = new StatGame(id, type, name, spectator, spectatorChat, creator, game);
        
        //set this stateGame for game
        game.setStatGame(statgame);
        
        //to Com : notify a new game
        ComFacade comFacade = this.dataFacade.getComfacade();
        if (comFacade != null) {
            comFacade.notifyNewGame(statgame);
        }
        
        return game;
    }

    
    /**
     * add a new game
     *
     * @param game
     */
    public void addNewGame(StatGame statgame){
        if(!this.gamesList.containsKey(statgame.getId())){
            this.gamesList.put(statgame.getId(), statgame);
        } else {
            throw new RuntimeException("Game " + statgame.getName() + " was already in the list of game.");
        }
    } 

     /* get list of games
     *
     * @return list of games
     */
    public List<StatGame> getGamesList() {
        List<StatGame> listGame = new ArrayList<>(this.gamesList.values());
        return listGame;
    }
    
      
}
