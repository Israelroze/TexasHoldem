package GameScene;

import API.InterfaceAPI;
import Exceptions.*;
import GameScene.BetOptions.BetOptionsController;
import GameScene.Community.CommunityController;
import GameScene.GameData.HandData;
import GameScene.LoadingReplayMode.LoadingReplayMode;
import GameScene.WinnersTable.TableViewController;
import GameScene.ReplayBox.ReplayBoxController;
import Move.*;
import GameLogic.GameLogic;
import GameScene.GameData.GameData;
import GameScene.GameData.PlayerData;
import GameScene.GameStatusBox.GameStatusBoxController;
import GameScene.MainOption.MainOptionController;
import GameScene.PlayerCube.PlayerCubeController;


import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HorizontalDirection;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private Stage primaryStage;
    private InterfaceAPI model;
    private GameData gameData;
    private HandData handData;
    private GameLogic gameLogic;
    private int numOfPlayers;
    private List<PlayerCubeController> playersControllers;
    private CommunityController communityController;
    private ReplayBoxController replayContorller;
    private  MainOptionController MenuContorller;
    private List<Node> PlayersNode;
    private Boolean IsGameStarted = false;
    private Boolean IsGameEnded = false;
    private MoveType currentMove;
    private VBox CommArea;

    @FXML private StackPane StackMainBoard;
    @FXML private VBox StatusPane;
    @FXML private BorderPane gameBorderPane;
    @FXML private ScrollPane scrollPaneForPlayers;
    @FXML private GridPane playerGrid;
    @FXML private HBox BetOptionsAnchor;
    @FXML private VBox MainOptionVbox;
    private boolean IsReplayMode;


    public GameController(){}

    //private
    private void BuildMainOption(){
        FXMLLoader loader = new FXMLLoader();
        URL url =getClass().getResource("/GameScene/MainOption/MainOption.fxml");
        loader.setLocation(url);

        try {
            Node MenuBox = loader.load();

            MenuContorller=loader.getController();
            MenuContorller.ConnectToMainGame(this);

            this.MainOptionVbox.setAlignment(Pos.CENTER);

            if(!this.IsGameStarted) {
                MenuContorller.SetRequiredButton(true, true, false, false);
            }
            else {
                MenuContorller.SetRequiredButton(false, false, true, true);
            }




            MenuContorller.HideButton();
            MenuContorller.getReplayButton().visibleProperty().bind(Bindings.createBooleanBinding(()->this.model.IsFirstHand()));
            MenuContorller.getReplayButton().managedProperty().bind(Bindings.createBooleanBinding(()->this.model.IsFirstHand()));
            this.MainOptionVbox.getChildren().removeAll();
            this.MainOptionVbox.getChildren().clear();
            this.MainOptionVbox.getChildren().add(MenuBox);
            MenuContorller.setGameData(this.gameData);
            MenuContorller.SetBuyButton();
            MenuContorller.SetQuitButton();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ShowAllPlayersCards() {

        for (PlayerCubeController playerCubeController : this.playersControllers)
        {
            playerCubeController.ShowCards();
        }

    }

    private void BuildCommunityArea() {
        FXMLLoader loader = new FXMLLoader();
        URL url =getClass().getResource("/GameScene/Community/Community.fxml");
        loader.setLocation(url);

        try {
            //this.CommArea.getChildren().removeAll();
            this.CommArea = loader.load();

            this.communityController  = loader.getController();
            //communityController.UpdateCommunityCards();


            this.StackMainBoard.getChildren().add(0,CommArea);

            //this.StackMainBoard.getChildren().add(CommArea);

            this.StackMainBoard.setAlignment(Pos.CENTER);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BuildWinnersTableArea() {
        FXMLLoader loader = new FXMLLoader();
        URL url =getClass().getResource("/GameScene/WinnersTable/TableView.fxml");
        loader.setLocation(url);

        try {



            VBox WinnerVbox= loader.load();

              TableViewController winnersTable = loader.getController();
            //communityController.UpdateCommunityCards();

            TableView tableView = winnersTable.getPlayersTable();
            ObservableList<PlayerData> data = gameData.GetDataForTable();
            tableView.setItems(data);
            //this.StackMainBoard.getChildren().add(CommArea);

            this.BetOptionsAnchor.getChildren().removeAll();
            this.BetOptionsAnchor.getChildren().add(WinnerVbox);

            TableColumn<?,?> a= (TableColumn<?, ?>) tableView.getColumns().get(tableView.getColumns().size() -1 );
            a.setSortType(TableColumn.SortType.DESCENDING);

            tableView.getSortOrder().add(tableView.getColumns().get(tableView.getColumns().size() -1 ));
           tableView.setFixedCellSize(25);
            tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(30));
                       tableView.setFixedCellSize(25);


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

    private void BuildSinglePlayerPane (int playerIndex, PlayerData playerData) {

        try {
            FXMLLoader loader = new FXMLLoader();
            URL url =getClass().getResource("/GameScene/PlayerCube/PlayerCube.fxml");
            loader.setLocation(url);

            Node singlePlayer = loader.load();

            PlayerCubeController singleController = loader.getController();

            singleController.getNameLable().textProperty().bind(playerData.playerNameProperty());
            singleController.getNameLable().textFillProperty().bind(Bindings.
                    when(singleController.currentPlayerIdProperty())
                    .then(Color.BLUE)
                    .otherwise(Color.WHITE));
            singleController.getMoneyLabel().textProperty().bind(playerData.numOfChipsProperty());
            singleController.getNumberOfBuyLabel().textProperty().bind(playerData.numOfBuyProperty());
            singleController.getNumberOfWinsLabel().textProperty().bind(playerData.numOfWinsProperty());
            singleController.getStateLabel().textProperty().bind(playerData.playerStateProperty());
            singleController.isInReplayModeProperty().bind(gameData.isInReplayProperty());
            singleController.currentPlayerIdProperty().bind(gameData.currentPlayerIdProperty().isEqualTo(playerData.getId()));
            singleController.getIDLabel().textProperty().setValue(String.valueOf(playerData.getId()));
            singleController.getComOrHumanLabel().textProperty().bind(Bindings.when(playerData.isHumanProperty()).then("Human").otherwise("Computer"));
            //singleController.setCards(playerData.getCard1(), playerData.getCard2());
            singleController.setPlayerId(playerData.getId());

            playersControllers.add(singleController);
            PlayersNode.add(singlePlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdateCommunityToCurrentHand() {

        this.communityController.SetHandData(this.gameData.getCurrentHand());
        this.communityController.getPotLabel().textProperty().bind(this.gameData.getCurrentHand().potProperty());

    }

    private void UpdatePlayerState(PlayerData playerData, PlayerCubeController singleController ){

        if (playerData.isIsDealer()) singleController.getStateLabel().setText("Dealer");
        else if (playerData.isIsBig()) singleController.getStateLabel().setText("Big");
        else if (playerData.isIsSmall()) singleController.getStateLabel().setText("Small");
        else singleController.getStateLabel().setText("");
    }

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

    public void OnClickReplayBack() {
        String message=this.model.GetPreviousEvent();
        this.replayContorller.getEventTextBox().setWrapText(true);
        this.replayContorller.getEventTextBox().setText(message);
        this.gameData.UpdateAllReplayMode();
        if(message.contains("Flop")||message.contains("River")||message.contains("Turn")) this.communityController.UpdateCommunityCards();
    }

    public void OnClickReplayForward() {
        String message=this.model.GetNextEvent();
        this.replayContorller.getEventTextBox().setWrapText(true);
        this.replayContorller.getEventTextBox().setText(message);
        this.gameData.UpdateAllReplayMode();
        if(message.contains("Flop")||message.contains("River")||message.contains("Turn")) this.communityController.UpdateCommunityCards();
    }

    public void OnClickReplay(){
        this.IsReplayMode=true;

        //this.MainOptionVbox.getChildren().clear();

        //this.model.ReverseHandToStart();////!!!@#!@#!@#!@#!@#!@#!@#!@#!@#!@#!@#!@#!@#!@#!@3123
        MakeReverseForReplay(() -> {
            this.BuildRaplayMenu();
            this.model.SetReplayMode(true);
            this.gameData.UpdateAllReplayMode();
            this.communityController.UpdateCommunityCards();
            ShowAllPlayersCards();

        });

    }

    public void OnClickEndRepaly() {
        this.StatusPane.getChildren().removeAll();
        this.StatusPane.getChildren().clear();
        this.BuildMainOption();

        this.BuildStatusBox();
    }


    private void UpdateCardsForPlayerInControllers() {
        this.gameData.UpdatePlayersCards();
        int index = 0;
        for (PlayerCubeController controller : this.playersControllers) {
            if (!this.gameData.getOnePlayerDataForBinding(index).isIsQuit()) {
                controller.setCards(this.gameData.getOnePlayerDataForBinding(index).getCard1(), this.gameData.getOnePlayerDataForBinding(index).getCard2());
                index++;
            }
        }
    }

    private void PlayOneHand(){

        // Deleting the Option box
        // this.gameData.RemoveDeletedPlayers();
        //PrintAllPlayers();
        this.MainOptionVbox.getChildren().removeAll();
        this.MainOptionVbox.getChildren().clear();

        if (this.model.IsAnyPlayerOutOfMoney()) {
            System.out.println("Players with out money!");
            this.model.SetGameOver(true);
            this.gameData.setIsGameOver();
            return;
        }
        else {
            this.model.StartNewHand();

            //update properties
            this.UpdateCardsForPlayerInControllers();
            this.gameData.setCurrentHand();
            if( this.communityController != null) this.communityController.InitCommunityCards();
            this.gameData.setCurrentHandNumber();
            this.gameData.setIsCurrentHandFinished();
            this.gameData.setBig();
            this.gameData.setSmall();
            this.gameData.setMaxPot();
            //this.gameData.setIsInReplay();
            //this.gameData.UpdateAll();

            //Hand over listener
            this.gameData.isCurrentHandFinishedProperty().addListener((observable, oldValue, newValue) -> {
                //System.out.println("Hand Finished changed to :"+newValue);
                if(newValue==true) {
                    if(this.gameData.getCurrentHandNumber()==this.model.GetAllowdedHandNumber())
                    {
                        this.model.SetGameOver(true);
                        this.gameData.setIsGameOver();
                    }
                    this.BuildMainOption();
                    this.BetOptionsAnchor.getChildren().removeAll();
                    this.BetOptionsAnchor.getChildren().clear();
                    this.gameData.getCurrentHand().setPot("0");

                    //Show Winner TBD
                    BuildWinnersTableArea();
                }
                return;
            });

            //current bid cycle finished listener
            this.gameData.getCurrentHand().is_current_bid_cycle_finishedProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue == true)
                {
                    System.out.println("Current Bid Finished");
                    this.gameData.getCurrentHand().IncBidNumber();
                    RunCommunity();
                    //init a new bid round
                    if(this.gameData.getCurrentHand().getCurrent_bid_number()<4) {
                        try {
                            this.model.StartNewBidCycle();
                        } catch (NoSufficientMoneyException e) {
                            //it means one of the players do not enough to put the big or small blind
                            this.IsGameEnded = true;
                            return;
                        }
                    }
                    else
                    {
                        return;
                    }
                }
                else
                {
                    this.HandEventshandler(this.gameData.getCurrentHand());
                }
            });
            try {
                this.model.StartNewBidCycle();
            } catch (NoSufficientMoneyException e) {
                //it means one of the players do not enough to put the big or small blind
                this.IsGameEnded = true;
                return;
            }
            this.gameData.getCurrentHand().UpdateHand();
            this.gameData.setCurrentPlayerId();
            this.gameData.UpdatePlayers();
            UpdateCommunityToCurrentHand();
            //BuildCommunityArea();
            this.HandEventshandler(this.gameData.getCurrentHand());
        }
    }

    private void RunCommunity() {
        switch(this.gameData.getCurrentHand().getCurrent_bid_number())
        {
            case 1:
                this.model.Flop();
                this.gameData.getCurrentHand().UpdateHand();
                this.communityController.UpdateCommunityCards();
                break;
            case 2:
                this.model.River();
                this.gameData.getCurrentHand().UpdateHand();
                this.communityController.UpdateCommunityCards();
                break;
            case 3:
                this.model.Turn();
                this.gameData.getCurrentHand().UpdateHand();
                this.communityController.UpdateCommunityCards();
                break;
            case 4:
                this.model.SetWinner();
                this.gameData.setIsCurrentHandFinished();
                this.gameData.UpdateAll();

                break;
        }
    }

    private void HandEventshandler(HandData hand) {

        // cycle moved to next player listener
        hand.current_player_idProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("Player Moved!!!");
            this.model.CheckBidStatus();
            this.model.CheckCurrentHandStatus();
            this.gameData.getCurrentHand().UpdateHand();
            if(this.gameData.getCurrentHand().isIs_current_bid_cycle_finished()) {
                return;
            }
            else
            {
                this.GetPlayerMove();
            }
        });

        this.GetPlayerMove();

        //hand.setCurrent_player_id();
    }

    private void MoveToNextPlayerAndUpdate(){
        this.model.MoveToNextPlayer();
        this.model.CheckBidStatus();
        this.model.CheckCurrentHandStatus();
        this.gameData.UpdateAll();
        //this.gameData.getCurrentHand().UpdateHand();
    }

    private void SetMoveAndUpdate(Move move) {
        try {
            this.model.SetNewMove(move);
            this.model.CheckBidStatus();
            this.model.CheckCurrentHandStatus();
            this.model.CheckNoActiveHumans();
            this.gameData.UpdateAll();
            //this.gameData.getCurrentHand().UpdateHand();
            //this.gameData.UpdatePlayers();
        } catch (StakeNotInRangeException e) {
            this.GetPlayerMove();
        } catch (PlayerFoldedException e) {
            this.MoveToNextPlayerAndUpdate();
        } catch (MoveNotAllowdedException e) {
            this.GetPlayerMove();
        } catch (ChipLessThanPotException e) {
            this.GetPlayerMove();
        } catch (NoSufficientMoneyException e) {
            this.GetPlayerMove();
        } catch (PlayerAlreadyBetException e) {
            this.MoveToNextPlayerAndUpdate();
        }
    }

    private void GetPlayerMove() {
        System.out.println("INSIDE GetPlayerMove, player id"+this.gameData.getCurrentHand().getCurrent_player_id());
//        this.PrintGame(model.GetCurrentHandState());

        Move move=null;
        if(this.model.IsCurrentPlayerFolded()) {
            System.out.println("current player"+this.gameData.GetPlayerData(this.model.GetCurrentPlayerID()).getPlayerName()+" already folded, moving to next player");
            this.MoveToNextPlayerAndUpdate();
        }
        else {
            //if computer
            if (this.model.IsCurrentPlayerComputer()) {
                try {
                    move = this.model.GetAutoMove();
                } catch (PlayerFoldedException e) {
                    e.printStackTrace();
                } catch (ChipLessThanPotException e) {
                    e.printStackTrace();
                }
                this.SetMoveAndUpdate(move);
            } else //if human
            {
                System.out.println("getting human move");
                if (this.model.IsCurrentPlayerHuman()) {

                    List<MoveType> moves = null;
                    MoveType moveType;
                    Move res;

                    //Get Allowded moves
                    try {
                        moves = this.model.GetAllowdedMoves();
                    } catch (PlayerFoldedException e) {
                        this.MoveToNextPlayerAndUpdate();
                    } catch (ChipLessThanPotException e) {
                        System.out.println("Chips is less than the Pot, number of chip is: " + e.GetMaxChips());
                    }
                    if (moves == null) {
                        System.out.println("Moves not avaliable");
                    }

                    //Get move
                    this.GetHumanNaxtMoveFromGUI(moves);
                }
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

        //Game Over Listener
        this.gameData.isGameOverProperty().addListener((observable, oldValue, newValue) -> {
            this.gameLogic.BuildEndScene(this.gameData);
        });

        this.numOfPlayers = gameData.getNumberOfPlayers();

        BuildCommunityArea();
        BuildMainOption();
        BuildPlayersPane();
        BuildStatusBox();
        PrintAllPlayers();

    }

    public void MoveToEndScene() {



        }

    private void PrintAllPlayers() {

        this.playerGrid.getChildren().removeAll();

        if(PlayersNode.size() == 3 ) {
            playerGrid.add(PlayersNode.get(0), 1, 0);
            playerGrid.add(PlayersNode.get(1), 0, 1);
            playerGrid.add(PlayersNode.get(2), 2, 1);
        }
        else if(PlayersNode.size() == 4) {
            playerGrid.add(PlayersNode.get(0), 0, 0);
            playerGrid.add(PlayersNode.get(1), 0, 1);
            playerGrid.add(PlayersNode.get(2), 2, 1);
            playerGrid.add(PlayersNode.get(3), 2, 0);
        }
        else if(PlayersNode.size() == 5) {
            playerGrid.add(PlayersNode.get(0), 1, 0);
            playerGrid.add(PlayersNode.get(1), 0, 0);
            playerGrid.add(PlayersNode.get(2), 0, 1);
            playerGrid.add(PlayersNode.get(3), 2, 1);
            playerGrid.add(PlayersNode.get(4), 2, 0);

        }else if(PlayersNode.size() == 6) {
            playerGrid.add(PlayersNode.get(0), 0, 0);
            playerGrid.add(PlayersNode.get(1), 0, 1);
            playerGrid.add(PlayersNode.get(2), 1, 1);
            playerGrid.add(PlayersNode.get(3), 2, 1);
            playerGrid.add(PlayersNode.get(4), 2, 0);
            playerGrid.add(PlayersNode.get(5), 1, 0);

        }
    }

    private void GetHumanNaxtMoveFromGUI(List<MoveType> AllowedMove) {
        FXMLLoader load = new FXMLLoader();
        URL url = getClass().getResource("/GameScene/BetOptions/BetOptions.fxml");
        load.setLocation(url);

        try {
            Node hbox= load.load();
            BetOptionsController optionsController = load.getController();
            optionsController.setModel(this.model);
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

    private void  BuildRaplayMenu() {
        FXMLLoader loader = new FXMLLoader();
        URL url =getClass().getResource("/GameScene/ReplayBox/ReplayBox.fxml");
        loader.setLocation(url);

        try {
            Node ReplayBox = loader.load();
            this.replayContorller=loader.getController();
            this.replayContorller.setGameData(this.gameData);
            this.replayContorller.ConnectToMainGame(this);

            this.replayContorller.InitChanceTable();
            Node Seperator=new Separator();
            Seperator.nodeOrientationProperty().setValue(NodeOrientation.INHERIT);
            this.StatusPane.getChildren().add(Seperator);
            this.StatusPane.getChildren().add(ReplayBox);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onLoadRepalyFinished(Optional<Runnable> onFinish) {
        this.MainOptionVbox.getChildren().clear();
        this.MainOptionVbox.getChildren().removeAll();
        this.MenuContorller.getReplayProgressBar().progressProperty().unbind();
        onFinish.ifPresent(Runnable::run);

    }

    private void MakeReverseForReplay(Runnable onFinish) {
        LoadingReplayMode replay = new LoadingReplayMode(this.model);
        this.MenuContorller.getReplayProgressBar().setDisable(false);
        this.MenuContorller.getReplayProgressBar().setVisible(true);
        this.MenuContorller.getReplayProgressBar().progressProperty().bind(replay.progressProperty());

        replay.valueProperty().addListener((observable, oldValue, newValue) -> {
            onLoadRepalyFinished(Optional.ofNullable(onFinish));
        });

        new Thread(replay).start();



    }

    public void HandleQuitPlayerGeneric(Button quitButton, Button buyButton , int index) {

        this.gameData.isOnlyOnePlayerProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue==true)
            {
                this.model.SetGameOver(true);
            }
        });

        this.gameData.getPlayerData().get(index).QuitFromGame();
        this.gameData.getPlayerData().get(index).playerStateProperty().set("Quit");
        this.gameData.setIsOnlyOnePlayer();

        quitButton.textProperty().set(gameData.getPlayerData().get(index).getPlayerName() + "\nQuit");
        quitButton.setDisable(true);
        buyButton.textProperty().set(gameData.getPlayerData().get(index).getPlayerName() + "\nQuit");
        buyButton.setDisable(true);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
}
