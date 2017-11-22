package com.utclo23.battleship;

import com.utclo23.data.facade.*;
import com.utclo23.com.ComFacade;
import com.utclo23.ihmmain.facade.*;
import com.utclo23.ihmtable.*;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

/**
 * This class is call when the program start. It instantiate all the facades and
 * link its together.
 *
 * @author Rémi DI VITA
 */
public class MainApp extends Application {

    /**
     * The start() method is call when the application is launched
     *
     * @param stage is the top level JavaFX container
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        // DataFacade creation
        DataFacade dataFacade = new DataFacade();
        IDataCom iDataCom = dataFacade;
        IDataIHMMain iDataIHMMain = dataFacade;
        IDataIHMTable iDataIHMtable = dataFacade;

        // CommunicationFacade creation
        ComFacade comFacade = new ComFacade(iDataCom);

        // IhmTableFacade creation
        IHMTableFacade ihmTableFacade = new IHMTableFacade(iDataIHMtable);
        IIHMTableToData iIHMTableToData = ihmTableFacade;
        IIHMTableToIHMMain iIHMTableToIHMMain = ihmTableFacade;

        // IhmMainFacade creation
        IHMMainFacade ihmMainFacade = new IHMMainFacade(
                iDataIHMMain,
                iIHMTableToIHMMain,
                stage
        );
        IHMMainToIhmTable iHMMainToIhmTable = ihmMainFacade;
        IHMMainToData iHMMainToData = ihmMainFacade;

        // set link from IhmMainFacade to IhmTable
        ihmTableFacade.setIhmMainLink(iHMMainToIhmTable);

        // set link from IhmMain, Ihmtable and Communication Facade to Data
        dataFacade.setFacadeLinks(comFacade, iIHMTableToData, ihmMainFacade);
        
        // Just for IHM-Table tests, comment for real integration.
        ihmTableFacade.createInGameGUI(stage);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
