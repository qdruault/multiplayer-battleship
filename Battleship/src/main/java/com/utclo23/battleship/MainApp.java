package com.utclo23.battleship;

import com.utclo23.ihmmain.IHMMain;
import com.utclo23.ihmmain.facade.IHMMainFacade;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;


public class MainApp extends Application {

    IHMMain ihmmain;
    IHMMainFacade ihmMainFacade;
    
    @Override
    public void start(Stage stage) throws Exception {
        ihmmain = new IHMMain();
        ihmmain.start(stage);
        ihmMainFacade = new IHMMainFacade(ihmmain);
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
