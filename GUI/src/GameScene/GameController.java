package GameScene;

import API.InterfaceAPI;
import Exceptions.*;
import GameScene.BetOptions.BetOptionsController;
import GameScene.GameData.HandData;
import Move.*;
import GameLogic.GameLogic;
import GameScene.GameData.GameData;
import GameScene.GameData.PlayerData;
import GameScene.GameStatusBox.GameStatusBoxController;
import GameScene.MainOption.MainOptionController;
import GameScene.PlayerCube.PlayerCubeController;
import ReturnType.CurrentHandState;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private GameLogic gameLogic;
    private int numOfPlayers;
    private List<PlayerCubeController> playersControllers;
    private List<Node> PlayersNode;
    private Boolean IsGameStarted = false;
    private Boolean IsGameEnded = false;
    private MoveType currentMove;

    @FXML private VBox StatusPane;
    @FXML private BorderPane gameBorderPane;
    @FXML private ScrollPane scrollPaneForPlayers;
    @FXML private GridPane playerGrid;
    @FXML private HBox BetOptionsAnchor;
    @FXML private VBox MainOptionVbox;


    //private
    private void BuildMainOption(){
        FXMLLoader loader = new FXMLLoader();
        URL url =getClass().getResource("/GameScene/MainOption/MainOption.fxml");
        loader.setLocation(url);

        try {
            Node MenuBox = loader.load();

            MainOptionController MenuContorller=loader.getController();
            MenuContorller.ConnectToMainGame(this);

            this.MainOptionVbox.setAlignment(Pos.CENTER);

            if(!this.IsGameStarted) {
                MenuContorller.SetRequiredButton(true, true, false, false);
            }
            else {
                MenuContorller.SetRequiredButton(false, false, true, true);
            }
            MenuContorller.HideButton();
            this.MainOptionVbox.getChildren().removeAll();
            this.MainOptionVbox.getChildren().clear();
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



    private void BuildPlayersPane () {
        this.playersControllers = new LinkedList<>();
        this.PlayersNode = new LinkedList<>();
        for (int i=0; i< numOfPlayers; i++)
        {
            this.BuildSinglePlayerPane(i,gameData.getOnePlayerDataForBinding(i));
        }
    }

    private void UpdatePlayerState(PlayerData playerData, PlayerCubeController singleController ){

        if (playerData.isIsDealer()) singleController.getTypeLabel().setText("Dealer");
        else if (playerData.isIsBig()) singleController.getTypeLabel().setText("Big");
        else if (playerData.isIsSmall()) singleController.getTypeLabel().setText("Small");
        else singleController.getTypeLabel().setText("");
    }

    private void BuildSinglePlayerPane (int playerIndex, PlayerData playerData) {

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
            singleController.getTypeLabel().textProperty().bind(playerData.playerStateProperty());

//            if (playerData.isIsDealer()) singleController.getTypeLabel().setText("Dealer");
//            else if (playerData.isIsBig()) singleController.getTypeLabel().setText("Big");
//            else if (playerData.isIsSmall()) singleController.getTypeLabel().setText("Small");
//            else singleController.getTypeLabel().setText("");


            singleController.currentPlayerIdProperty().bind(gameData.currentPlayerIdProperty());
            //singleController.setCards(playerData.getCard1(), playerData.getCard2());
            singleController.setPlayerId(playerData.getId());

            playersControllers.add(singleController);
            PlayersNode.add(singlePlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void MoveToNextPlayerAndUpdate(){
        this.model.MoveToNextPlayer();
        this.model.CheckBidStatus();
        this.gameData.UpdateAll();
        //this.gameData.getCurrentHand().UpdateHand();
    }

    private void SetMoveAndUpdate(Move move) {
        try {
            this.model.SetNewMove(move);
            this.model.CheckBidStatus();
            this.gameData.UpdateAll();
            //this.gameData.getCurrentHand().UpdateHand();
            //this.gameData.UpdatePlayers();
        } catch (StakeNotInRangeException e) {
            e.printStackTrace();
        } catch (PlayerFoldedException e) {
            e.printStackTrace();
        } catch (MoveNotAllowdedException e) {
            e.printStackTrace();
        } catch (ChipLessThanPotException e) {
            e.printStackTrace();
        } catch (NoSufficientMoneyException e) {
            e.printStackTrace();
        } catch (PlayerAlreadyBetException e) {
            e.printStackTrace();
        }
    }

    public GameController(){};

    public void setModel(InterfaceAPI model) {
        this.model = model;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void SetGameLogic(GameLogic gl)
    {
        this.gameLogic=gl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void OnClickStart() {
        this.IsGameStarted = true;
        BuildMainOption();
    }

    public void OnClickHand() {
        this.PlayOneHand();
    }

    public void OnClickBack() {
        gameLogic.StartNewWelcomeScene();
    }

    public void OnClickReplay(){

    }

    private void UpdateCardsForPlayerInControllers()
    {
        this.gameData.UpdatePlayersCards();
        int index=0;
         for (PlayerCubeController controller: this.playersControllers)
         {
             controller.setCards(this.gameData.getOnePlayerDataForBinding(index).getCard1(),this.gameData.getOnePlayerDataForBinding(index).getCard2());
             index++;
         }
    }

    private void PlayOneHand(){

        if (this.model.IsAnyPlayerOutOfMoney()) {
            this.IsGameEnded = true;
        }
        else {
            this.model.StartNewHand();
            this.UpdateCardsForPlayerInControllers();

            //init a new bid round
            try {
                this.model.StartNewBidCycle();

            } catch (NoSufficientMoneyException e) {
                //it means one of the players do not enough to put the big or small blind
                this.IsGameEnded = true;
            }

            this.gameData.setCurrentHand();
            this.gameData.getCurrentHand().UpdateHand();
            this.gameData.setCurrentPlayerId();
            this.gameData.UpdatePlayers();
            this.HandEventshandler(this.gameData.getCurrentHand());

        }
    }


    private void HandEventshandler(HandData hand) {

        //current bid cycle finished listener
        hand.is_current_bid_cycle_finishedProperty().addListener((observable, oldValue, newValue) -> {
            //TBD
            System.out.println("current bid finished");
            return;
        });

        // cycle moved to next player listener
        hand.current_player_idProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Player Moved!!!");
            this.model.CheckBidStatus();
            this.gameData.getCurrentHand().UpdateHand();
            if(this.gameData.getCurrentHand().isIs_current_bid_cycle_finished()) {
                return;
            }
            else
            {
                this.GetPlayerMove();
            }
        });


        gameData.currentPlayerIdProperty();

        this.GetPlayerMove();

        //hand.setCurrent_player_id();

    }


    public  void PrintGame(CurrentHandState curHandState){


//        System.out.format("%s",curHandState.getPlayersState().get(0).getCard().toString());
//        System.out.format("%s",curHandState.getPlayersState().get(0).getCard().toString());
//        System.out.format("%s",curHandState.getPlayersState().get(1).IsHuman() ?"": "Cards: ");
//        System.out.format("%s",curHandState.getPlayersState().get(1).getCard().toString());


        System.out.format("%-9s  %-4d  %s       %-9s  %-4d  %s\n",curHandState.getPlayersState().get(0).getName(),curHandState.getPlayersState().get(0).getId(),curHandState.getCurrentPlayer() == 0 ? "***": "   ",curHandState.getPlayersState().get(3).getName(),curHandState.getPlayersState().get(3).getId(),curHandState.getCurrentPlayer() == 3 ? "***": "   ");
        System.out.format("*****************%s       *****************%s\n",curHandState.getCurrentPlayer() == 0 ? "***": "** ",curHandState.getCurrentPlayer() == 3 ? "***": "** ");
        System.out.format("* Type: %1s        %s       * Type: %1s        %s\n",curHandState.getPlayersState().get(0).GetType().toString(),curHandState.getCurrentPlayer() == 0 ? "***": " * ",curHandState.getPlayersState().get(3).GetType().toString(),curHandState.getCurrentPlayer() == 3 ? "***": " * ");
        System.out.format("* State %1s         *        * State %1s         *\n",curHandState.getPlayersState().get(0).getState().toString(),curHandState.getPlayersState().get(3).getState().toString());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",curHandState.getPlayersState().get(0).getChips(),curHandState.getPlayersState().get(3).getChips());
        System.out.format("* Bets: %-8d  *        * Bets: %-8d  *\n",curHandState.getPlayersState().get(0).getBet(),curHandState.getPlayersState().get(3).getBet());
        if(curHandState.getPlayersState().get(0).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *        ",
                    "Cards: ",curHandState.getPlayersState().get(0).getCard().get(0).toString(),curHandState.getPlayersState().get(0).getCard().get(1).toString());
        }
        else{
            System.out.format("*                 *        ");

        }
        if(curHandState.getPlayersState().get(3).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *\n",
                    "Cards: ",curHandState.getPlayersState().get(3).getCard().get(0).toString(),curHandState.getPlayersState().get(3).getCard().get(1).toString());
        }else
        {
            System.out.format("*                 *\n");
        }

        System.out.format("*******************        *******************\n");
        System.out.format("\n");
        System.out.format("     %s            ***POT: %d *** \n", curHandState.getStringOfCommunityCard(),curHandState.getPot());
        System.out.format("Big Blind: %-5d   Small Blind : %-5d \n", curHandState.getBigBlind(), curHandState.getSmallBlind());
        System.out.format("\n");
        System.out.format("%-9s  %-4d  %s       %-9s %4d  %s\n",curHandState.getPlayersState().get(1).getName(),curHandState.getPlayersState().get(1).getId(),curHandState.getCurrentPlayer() == 1 ? "***": "   ",curHandState.getPlayersState().get(2).getName(),curHandState.getPlayersState().get(2).getId(),curHandState.getCurrentPlayer() == 2 ? "***": "   ");
        System.out.format("*****************%s       *****************%s\n",curHandState.getCurrentPlayer() == 1 ? "***": "** ",curHandState.getCurrentPlayer() == 2 ? "***": "** ");
        System.out.format("* Type: %1s        %s       * Type: %1s        %s\n",curHandState.getPlayersState().get(1).GetType().toString(),curHandState.getCurrentPlayer() == 1 ? "***": " * ",curHandState.getPlayersState().get(2).GetType().toString(),curHandState.getCurrentPlayer() == 2 ? "***": " * ");
        System.out.format("* State %1s         *        * State %1s         *\n",curHandState.getPlayersState().get(1).getState().toString(),curHandState.getPlayersState().get(2).getState().toString());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",curHandState.getPlayersState().get(1).getChips(),curHandState.getPlayersState().get(2).getChips());
        System.out.format("* Bets: %-8d  *        * Bets: %-8d  *\n",curHandState.getPlayersState().get(1).getBet(),curHandState.getPlayersState().get(2).getBet());
        if(curHandState.getPlayersState().get(1).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *        ",
                    "Cards: ",curHandState.getPlayersState().get(1).getCard().get(0).toString(),curHandState.getPlayersState().get(1).getCard().get(1).toString());
        }
        else{
            System.out.format("*                 *        ");

        }
        if(curHandState.getPlayersState().get(2).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *\n",
                    "Cards: ",curHandState.getPlayersState().get(2).getCard().get(0).toString(),curHandState.getPlayersState().get(2).getCard().get(1).toString());
        }else
        {
            System.out.format("*                 *\n");
        }
        System.out.format("*******************        *******************\n");


    }







    private void GetPlayerMove() {
        System.out.println("INSIDE GetPlayerMove, player id"+this.gameData.getCurrentHand().getCurrent_player_id());
        this.PrintGame(model.GetCurrentHandState());

        Move move=null;
        //if computer
        if (this.model.IsCurrentPlayerComputer()) {
            try {
                move=this.model.GetAutoMove();
            } catch (PlayerFoldedException e) {
                e.printStackTrace();
            } catch (ChipLessThanPotException e) {
                e.printStackTrace();
            }
            this.SetMoveAndUpdate(move);
        }
        else //if human
        {
            System.out.println("getting human move");
            if (this.model.IsCurrentPlayerHuman()) {

                List<MoveType> moves=null;
                MoveType moveType;
                Move res;

                //Get Allowded moves
                try {
                    moves= this.model.GetAllowdedMoves();
                } catch (PlayerFoldedException e) {
                    this.MoveToNextPlayerAndUpdate();
                } catch (ChipLessThanPotException e) {
                    System.out.println("Chips is less than the Pot, number of chip is: "+ e.GetMaxChips());
                }
                if(moves == null)
                {
                    System.out.println("Moves not avaliable");
                }

                //Get move
                this.GetHumanNaxtMoveFromGUI(moves);
            }
        }
    }

    public void setHumanMove(Move move){
        this.SetMoveAndUpdate(move);
    }

    private Move PlayHumanPlayer() {
        //List<String> moveChars = new LinkedList<>();// = Arrays.asList("B","b","F","f","R","r","C","c","K","k");
        //int amount =0;
        // String input;
        //PrintGame(this.model.GetCurrentHandState());
        //moveChars.clear();
        List<MoveType> moves=null;
        MoveType moveType;
        Move res;

        //Get Allowded moves
        try {
            moves= this.model.GetAllowdedMoves();
        } catch (PlayerFoldedException e) {
            //System.out.println("Player folded,Please enter new choice");// TBD:  shouldn't happen, ask israel the reason for this exception!!!
            this.model.MoveToNextPlayer();
        } catch (ChipLessThanPotException e) {
            System.out.println("Chips is less than the Pot, number of chip is: "+ e.GetMaxChips());
        }
        if(moves == null)
        {
            System.out.println("Moves not avaliable");
            return null;
        }

        //Get move
        GetHumanNaxtMoveFromGUI(moves);
        return null;
    }

    public Move GetHumanMove(MoveType move){

        return null;
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

    private void GetHumanNaxtMoveFromGUI(List<MoveType> AllowedMove) {
        FXMLLoader load = new FXMLLoader();
        URL url = getClass().getResource("/GameScene/BetOptions/BetOptions.fxml");
        load.setLocation(url);

        try {
            Node hbox= load.load();
            BetOptionsController optionsController = load.getController();
            optionsController.ConnectToMainGame(this);
            optionsController.setAllowedMoves(AllowedMove);
            optionsController.updateOptions();

            this.BetOptionsAnchor.setAlignment(Pos.CENTER);
            this.BetOptionsAnchor.getChildren().removeAll();
            this.BetOptionsAnchor.getChildren().clear();
            this.BetOptionsAnchor.getChildren().add(hbox);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ShowGameToPlayer(){


    }
}
