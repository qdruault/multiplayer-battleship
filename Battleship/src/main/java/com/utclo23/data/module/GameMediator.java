package com.utclo23.data.module;

import com.utclo23.data.facade.DataFacade;
import com.utclo23.data.structure.Game;
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
    
    
}
