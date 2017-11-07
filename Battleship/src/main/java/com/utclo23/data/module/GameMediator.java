package com.utclo23.data.module;

import com.utclo23.data.facade.DataFacade;

import com.utclo23.data.structure.StatGame;

import java.util.ArrayList;
import java.util.List;


/**
 * Game Mediator related to games features
 * @author tboulair
 */
public class GameMediator {
    /**
     * reference to the data facade
     */
    private DataFacade dataFacade;
    
    /**
     * list that stores games
     */
    private List<StatGame> gamesList; 

    /**
     * Constructor 
     */
    public GameMediator(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
        this.gamesList = new ArrayList<>();
    }
    
    /**
     * add a new game
     *
     * @param game
     */
    public void addNewGame(StatGame game){
        if(!this.gamesList.contains(game)){
            this.gamesList.add(game);
        } else {
            throw new RuntimeException("This game already exists in the list");
        }
    } 

     /* get list of games
     *
     * @return list of games
     */
    public List<StatGame> getGamesList() {
        return this.gamesList;
    }

    
}
