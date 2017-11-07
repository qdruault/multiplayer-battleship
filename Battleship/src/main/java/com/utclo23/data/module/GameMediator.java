package com.utclo23.data.module;

import com.utclo23.data.facade.DataFacade;

import com.utclo23.data.structure.StatGame;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author tboulair
 */
public class GameMediator {
    private DataFacade dataFacade;
    private List<StatGame> gamesList; 

    public GameMediator(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
        this.gamesList = new ArrayList<>();
    }
    
    /**
<<<<<<< HEAD
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
