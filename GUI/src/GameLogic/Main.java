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

    }

    public static void main(String[] args) {
        launch(args);
    }


}
