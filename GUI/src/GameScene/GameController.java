package GameScene;

import API.InterfaceAPI;
import GameScene.GameData.GameData;
import GameScene.GameData.PlayerData;
import GameScene.GameStatusBox.GameStatusBoxController;
import GameScene.MainOption.MainOptionController;
import GameScene.PlayerCube.PlayerCubeController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private Stage primaryStage;
    private InterfaceAPI model;
    private GameData gameData;
    private int numOfPlayers;
    private List<PlayerCubeController> playersControllers;
    private  List<Node> PlayersNode;

    @FXML private VBox StatusPane;
    @FXML private BorderPane gameBorderPane;
    @FXML private ScrollPane scrollPaneForPlayers;
    @FXML private GridPane playerGrid;
    @FXML private HBox BetOptionsAnchor;
    @FXML private VBox MainOptionVbox;

    public GameController(){};

    public void setModel(InterfaceAPI model) {
        this.model = model;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    void BuildPlayersPane () {
        this.playersControllers = new LinkedList<>();
        this.PlayersNode = new LinkedList<>();
        for (int i=0; i< numOfPlayers; i++)
        {
           this.BuildSinglePlayerPane(i,gameData.getOnePlayerDataForBinding(i));
        }
    }

    void BuildSinglePlayerPane (int playerIndex, PlayerData playerData) {

        try {
            FXMLLoader loader = new FXMLLoader();
            URL url =getClass().getResource("/GameScene/PlayerCube/PlayerCube.fxml");
            loader.setLocation(url);

            Node singlePlayer = loader.load();

            PlayerCubeController singleController = loader.getController();

            singleController.getNameLable().textProperty().bind(playerData.playerNameProperty());
            singleController.getMoneyLabel().textProperty().bind(playerData.numOfChipsProperty());
            singleController.getNumberOfBuyLabel().textProperty().bind(playerData.numOfBuyProperty());
            singleController.getNumberOfWinsLabel().textProperty().bind(playerData.numOfWinsProperty());


            if (playerData.isIsDealer()) singleController.getTypeLabel().setText("Dealer");
            else if (playerData.isIsBig()) singleController.getTypeLabel().setText("Big");
            else if (playerData.isIsSmall()) singleController.getTypeLabel().setText("Small");
            else singleController.getTypeLabel().setText("");


            singleController.currentPlayerIdProperty().bind(gameData.currentPlayerIdProperty());
            singleController.setCards(playerData.getCard1(), playerData.getCard2());
            singleController.setPlayerId(playerData.getId());

            playersControllers.add(singleController);
            PlayersNode.add(singlePlayer);
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private void BuildMainOption(){
        FXMLLoader loader = new FXMLLoader();
        URL url =getClass().getResource("/GameScene/MainOption/MainOption.fxml");
        loader.setLocation(url);

        try {
            Node MenuBox = loader.load();

            MainOptionController MenuContorller=loader.getController();
            this.MainOptionVbox.setAlignment(Pos.CENTER);
            MenuContorller.SetRequiredButton(true,false,true,false);
            MenuContorller.HideButton();
            this.MainOptionVbox.getChildren().add(MenuBox);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BuildStatusBox(){
        FXMLLoader loader = new FXMLLoader();
        URL url =getClass().getResource("/GameScene/GameStatusBox/GameStatusBox.fxml");
        loader.setLocation(url);

        try {
            Node StatusBox = loader.load();

            GameStatusBoxController StatusContorller=loader.getController();

            StatusContorller.getCurrentHandNumberLabel().textProperty().bind(gameData.currentHandNumberProperty());
            StatusContorller.getBigLabel().textProperty().bind(gameData.bigProperty());
            StatusContorller.getSmallLabel().textProperty().bind(gameData.smallProperty());
            StatusContorller.getGameMoneyLabel().textProperty().bind(gameData.maxPotProperty());


            this.StatusPane.getChildren().add(StatusBox);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void StartGameView () {
        this.gameData = new GameData(model);
        this.numOfPlayers = gameData.getNumberOfPlayers();

        BuildMainOption();
        BuildPlayersPane();
        BuildStatusBox();
        PrintAllPlayers();

    }

    private void PrintAllPlayers() {
        playerGrid.add(PlayersNode.get(0),0,0);
        playerGrid.add(PlayersNode.get(1),0,1);
        playerGrid.add(PlayersNode.get(2),2,1);
        playerGrid.add(PlayersNode.get(3),2,0);
    }

    @FXML protected  void test() {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("/GameScene/BetOptions/BetOptions.fxml"));
        try {
            Node hbox= load.load();
            this.BetOptionsAnchor.setAlignment(Pos.CENTER);
            this.BetOptionsAnchor.getChildren().add(hbox);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowGameToPlayer(){


    }
}
