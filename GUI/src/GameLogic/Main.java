package GameLogic;


import java.net.URL;

import API.InterfaceAPI;
import WelcomeScene.WelcomeController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import Game.Game;


public class Main extends Application {
    
    private InterfaceAPI model;
    private FXMLLoader fxmlLoader;

    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {

        primaryStage = stage;
        GameLogic GL=new GameLogic();
        GL.SetPrimaryStage(stage);
        GL.StartNewWelcomeScene();

//        model = new Game();
//
//        primaryStage.setTitle("Welcome FXML");
//
//        //if you just want to load the FXML
////       Parent root = FXMLLoader.load(Main.class.getResource("GameLogic.WelcomeScene"));
//
//        //if you want to load the FXML and get access to its controller
//        fxmlLoader = new FXMLLoader();
//        URL url = getClass().getResource("GameLogic.fxml");
//        fxmlLoader.setLocation(url);
//        Parent root = fxmlLoader.load(url.openStream());
//
//        WelcomeController welcomeController = fxmlLoader.getController();
//        welcomeController.setPrimaryStage(primaryStage);
//        welcomeController.setModel(model);
//        welcomeController.serLoader(fxmlLoader);
//
//    Scene scene = new Scene(root, 1000, 600);
//
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
