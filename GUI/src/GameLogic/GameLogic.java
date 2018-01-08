package GameLogic;

import API.InterfaceAPI;
import EndScene.EndSceneController;
import Game.Game;
import GameScene.GameController;
import GameScene.GameData.GameData;
import WelcomeScene.WelcomeController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GameLogic {
    private InterfaceAPI model;
    private WelcomeController cWelcome;
    private GameController cGame;
    private Stage primaryStage;
    private Scene scene;

    static public SimpleIntegerProperty CSS_STYLE_NUMBER  = new SimpleIntegerProperty(1);

    public void SetPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public static void AccomdateSkinNumber() {

        CSS_STYLE_NUMBER.set((CSS_STYLE_NUMBER.get()  ) %3 + 1);
    }
    public void StartNewWelcomeScene()
    {
        model = new Game();

        primaryStage.setTitle("Texas Holdem");

        //if you just want to load the FXML
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            URL url = getClass().getResource("/WelcomeScene/welcome.fxml");
            fxmlLoader.setLocation(url);
            Parent root = fxmlLoader.load();
            WelcomeController welcomeController = fxmlLoader.getController();
            welcomeController.setPrimaryStage(Main.primaryStage);
            welcomeController.setModel(model);
            welcomeController.serLoader(fxmlLoader);
            welcomeController.setGameLogic(this);


            scene = new Scene(root, 1200, 600);

            scene.getStylesheets().addAll(getClass().getResource("/resources/css/welcome" + CSS_STYLE_NUMBER.get()  + ".css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void BuildEndScene(GameData gameData) {
        FXMLLoader loader = new FXMLLoader();
        URL url = getClass().getResource("/EndScene/EndScene.fxml");
        loader.setLocation(url);

        try {
            Parent end = loader.load();

            EndSceneController endScene = loader.getController();
            endScene.setGameData(gameData);
            endScene.setGameLogic(this);
            endScene.BuildWinnersTableArea();
            scene = new Scene(end, 1200, 600);
            scene.getStylesheets().addAll(getClass().getResource("/resources/css/welcome" + CSS_STYLE_NUMBER.get()  + ".css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void SetNextSkin() {
        scene.getStylesheets().clear();
        System.out.println("This is The Style sheet Now " + String.valueOf(CSS_STYLE_NUMBER.get()));
        scene.getStylesheets().addAll(getClass().getResource("/resources/css/welcome" + CSS_STYLE_NUMBER.get()  + ".css").toExternalForm());

    }

    public void SetWelcomeScene() {

    }





 /*
   private void LoadWelcome() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("WelcomeScene/welcome.fxml");

        fxmlLoader.setLocation(url);

        try {
            Parent welcomeScene = fxmlLoader.load(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.cWelcome= fxmlLoader.getController();

    }

    private void LoadGame() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL url = getClass().getResource("GameScene/Game.fxml");
        fxmlLoader.setLocation(url);

        Node gameScene;
        try {
            gameScene = fxmlLoader.load(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.cGame= fxmlLoader.getController();
    }


    public GameLogic() {
        model = new Game();
        this.LoadWelcome();
        this.LoadGame();
    }




    public void Run()
    {
        startStage();

    }

    private void startStage()
    {
        Main.primaryStage.setTitle("Welcome FXML");

        this.cWelcome.setPrimaryStage(Main.primaryStage);
        this.cGame.setPrimaryStage();

        welcomeController.setPrimaryStage(Main.primaryStage);
        welcomeController.setModel(model);
        welcomeController.serLoader(fxmlLoader);

        Scene scene = new Scene(root, 1000, 600);


        primaryStage.setScene(scene);
        primaryStage.show();
    }

*/



}
