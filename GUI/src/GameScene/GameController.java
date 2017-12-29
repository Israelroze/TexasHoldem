package GameScene;

import API.InterfaceAPI;
import GameScene.GameData.GameData;
import GameScene.GameData.PlayerData;
import GameScene.PlayerCube.PlayerCubeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {




    private Stage primaryStage;
    private InterfaceAPI model;
    private GameData gameData;
    private int numOfPlayers;
    private List<PlayerCubeController> playersControllers;
    @FXML private BorderPane gameBorderPane;
    @FXML private ScrollPane scrollPaneForPlayers;
    @FXML private StackPane stackForGrid;
    @FXML private GridPane playerGrid;





    public GameController(){};
    public void setModel(InterfaceAPI model) {
        this.model = model;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void BindGameDataToGameScene ()
    {
        this.gameData = new GameData(model);
        this.numOfPlayers = gameData.getNumberOfPlayers();
        //BuildPlayersPane

    }
    void BuildPlayersPane ()
    {
        for (int i=0; i< numOfPlayers; i++)
        {

           this.BuildSinglePlayerPane(i,gameData.getOnePlayerDataForBinding(i));
        }


    }

    void BuildSinglePlayerPane (int playerIndex, PlayerData playerData)
    {
        PlayerCubeController singleController = new PlayerCubeController();

        singleController.getNameLable().textProperty().bind(playerData.playerNameProperty());
        singleController.getMoneyLabel().textProperty().bind(playerData.numOfChipsProperty().asString());
        singleController.getNumberOfBuyLabel().textProperty().bind(playerData.numOfBuyProperty().asString());

        if(playerData.isIsDealer())  singleController.getTypeLabel().setText("Dealer");
        else if(playerData.isIsBig())  singleController.getTypeLabel().setText("Big");
        else if(playerData.isIsSmall()) singleController.getTypeLabel().setText("Small");
        else singleController.getTypeLabel().setText("");




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



        b2.setVisible(false);
        b3.setVisible(false);
        b4.setVisible(false);
        b1.translateXProperty().bind(pane.widthProperty().divide(5));
        b1.translateYProperty().bind(pane.heightProperty().divide(5));
        //b2.translateXProperty().bind(pane.heightProperty().multiply());
//        b2.translateYProperty().bind(pane.widthProperty(). multiply(1/2));
//        b3.translateXProperty().bind(pane.heightProperty().multiply(1));
//        b3.translateYProperty().bind(pane.widthProperty(). multiply(1/2));
//        b4.translateXProperty().bind(pane.heightProperty().multiply(1));
//        b4.translateYProperty().bind(pane.widthProperty(). multiply(1/2));

    }
    @FXML protected  void test()
    {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("/GameScene/BetOptions/BetOptions.fxml"));
        try {
            Node vbox= load.load();
            this.gameBorderPane.setRight(vbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ShowGameToPlayer(){


    }
}
