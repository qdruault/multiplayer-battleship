package com.utclo23.data.module;

import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.structure.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author tboulair
 */
public class GameMediator {
    private DataFacade dataFacade;
    private List<Game> gamesList; 

    public GameMediator(DataFacade dataFacade) {
        this.dataFacade = dataFacade;
        this.gamesList = new ArrayList<Game>();
    }
    
    /**
     * get list of games
     *
     * @return list of games
     */
    public List<StatGame> getGamesList() {
        List<StatGame> listStatGames = new ArrayList<StatGame>();
        for (int i = 0; i < this.gamesList.size(); i++)
            listStatGames.add(this.gamesList.get(i).getStatGame());        
        return listStatGames;
    }
    
}
