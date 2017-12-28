package WelcomeScene;


import java.net.URL;

import API.InterfaceAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import Game.Game;


public class Main extends Application {
    
    private InterfaceAPI model;
    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
            model = new Game();

        primaryStage.setTitle("Welcome FXML");

        //if you just want to load the FXML
//       Parent root = FXMLLoader.load(Main.class.getResource("welcome.WelcomeScene"));
        
        //if you want to load the FXML and get access to its controller
        fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("welcome.fxml");
        fxmlLoader.setLocation(url);
        Parent root = fxmlLoader.load(url.openStream());

        WelcomeController welcomeController = fxmlLoader.getController();
        welcomeController.setPrimaryStage(primaryStage);
        welcomeController.setModel(model);
        welcomeController.serLoader(fxmlLoader);

        Scene scene = new Scene(root, 1000, 600);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
