package GameScene.MainOption;

import GameScene.GameController;
import GameScene.GameData.GameData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;

public class MainOptionController {

    @FXML private Button StartGameButton;
    @FXML private Button BackButton;
    @FXML private Button StartNewHandButton;
    @FXML private Button ReplayButton;
    @FXML private VBox MainOptionVBox;
    @FXML private Button buyButtonPlayer1;
    @FXML private Button buyButtonPlayer2;
    @FXML private Button buyButtonPlayer3;
    @FXML private Button buyButtonPlayer4;
    @FXML private Button buyButtonPlayer5;
    @FXML private Button buyButtonPlayer6;
    @FXML private Button quitButtonPlayer1;
    @FXML private Button quitButtonPlayer2;
    @FXML private Button quitButtonPlayer3;
    @FXML private Button quitButtonPlayer4;
    @FXML private Button quitButtonPlayer5;
    @FXML private Button quitButtonPlayer6;



    private Boolean isRequiredStartGameButton;
    private Boolean isRequiredBackButton;
    private Boolean isRequiredStartNewHandButton;
    private Boolean isRequiredReplayButton;
    private GameController mainGame;
    private GameData gameData;

    public void SetRequiredButton(Boolean startGame, Boolean backButton, Boolean startNewGameButton, Boolean replayButton)
    {
        this.isRequiredBackButton= backButton;
        this.isRequiredReplayButton= replayButton;
        this.isRequiredStartGameButton = startGame;
        this.isRequiredStartNewHandButton = startNewGameButton;

    }


    public void setGameData(GameData gameData) { this.gameData = gameData; }

    public void ConnectToMainGame(GameController game) { this.mainGame =game; }

    public void HideButton() {
        if(!this.isRequiredStartGameButton)
        {
            this.StartGameButton.setManaged(false);
            this.StartGameButton.setVisible(false);
        }
        if(!this.isRequiredBackButton)
        {
            this.BackButton.setManaged(false);
            this.BackButton.setVisible(false);
        }
        if(!this.isRequiredStartNewHandButton)
        {
            this.StartNewHandButton.setManaged(false);
            this.StartNewHandButton.setVisible(false);
        }
        if(!this.isRequiredReplayButton)
        {
            this.ReplayButton.setManaged(false);
            this.ReplayButton.setVisible(false);
        }
    }

    @FXML void HandleBackButton(MouseEvent event) {
        MainOptionVBox.getChildren().removeAll();
        this.mainGame.OnClickBack();
    }

    @FXML void HandleReplayButton(MouseEvent event) {
        MainOptionVBox.getChildren().removeAll();
        this.mainGame.OnClickReplay();
    }

    @FXML void HandleStartGameButtom(MouseEvent event) {
        MainOptionVBox.getChildren().removeAll();
        this.mainGame.OnClickStart();
    }

    @FXML void HandleStartNewHandButton(MouseEvent event) {
        MainOptionVBox.getChildren().removeAll();
        this.mainGame.OnClickHand();
    }
    private void HideBuyButton()
    {

        this.buyButtonPlayer1.setDisable(true);
        this.buyButtonPlayer1.setManaged(false);
        this.buyButtonPlayer2.setDisable(true);
        this.buyButtonPlayer2.setManaged(false);
        this.buyButtonPlayer3.setDisable(true);
        this.buyButtonPlayer3.setManaged(false);
        this.buyButtonPlayer4.setDisable(true);
        this.buyButtonPlayer4.setManaged(false);
        this.buyButtonPlayer5.setDisable(true);
        this.buyButtonPlayer5.setManaged(false);
        this.buyButtonPlayer6.setDisable(true);
        this.buyButtonPlayer6.setManaged(false);

    }
    private void HideQuitButton()
    {

        this.quitButtonPlayer1.setDisable(true);
        this.quitButtonPlayer1.setManaged(false);
        this.quitButtonPlayer2.setDisable(true);
        this.quitButtonPlayer2.setManaged(false);
        this.quitButtonPlayer3.setDisable(true);
        this.quitButtonPlayer3.setManaged(false);
        this.quitButtonPlayer4.setDisable(true);
        this.quitButtonPlayer4.setManaged(false);
        this.quitButtonPlayer5.setDisable(true);
        this.quitButtonPlayer5.setManaged(false);
        this.quitButtonPlayer6.setDisable(true);
        this.quitButtonPlayer6.setManaged(false);

    }

    public void SetBuyButton() {

        HideBuyButton();

        if (gameData == null || gameData.getCurrentHand() == null) return;
        if(this.gameData.getCurrentHand().Is_current_hand_finished()) {


            if (!gameData.getPlayerData().get(0).isIsQuit() && gameData.getPlayerData().get(0).isIsHuman()) {
                this.buyButtonPlayer1.textProperty().set("Buy: " + gameData.getPlayerData().get(0).getPlayerName());
                this.buyButtonPlayer1.setDisable(false);
                this.buyButtonPlayer1.setManaged(true);
            }

            if (!gameData.getPlayerData().get(1).isIsQuit()  && gameData.getPlayerData().get(1).isIsHuman()) {
                this.buyButtonPlayer2.textProperty().set("Buy: " +gameData.getPlayerData().get(1).getPlayerName());
                this.buyButtonPlayer2.setDisable(false);
                this.buyButtonPlayer2.setManaged(true);
            }
            if (!gameData.getPlayerData().get(2).isIsQuit()  && gameData.getPlayerData().get(2).isIsHuman()) {
                this.buyButtonPlayer3.textProperty().set("Buy: " +gameData.getPlayerData().get(2).getPlayerName());
                this.buyButtonPlayer3.setDisable(false);
                this.buyButtonPlayer3.setManaged(true);
            }
            if(gameData.getPlayerData().size()>3){
                if (!gameData.getPlayerData().get(3).isIsQuit()  && gameData.getPlayerData().get(3).isIsHuman()) {
                    this.buyButtonPlayer4.textProperty().set("Buy: " +gameData.getPlayerData().get(3).getPlayerName());
                    this.buyButtonPlayer4.setDisable(false);
                    this.buyButtonPlayer4.setManaged(true);
                }
            }
            if(gameData.getPlayerData().size()>4){
                if (!gameData.getPlayerData().get(4).isIsQuit()  && gameData.getPlayerData().get(4).isIsHuman()) {
                    this.buyButtonPlayer5.textProperty().set("Buy: " +gameData.getPlayerData().get(4).getPlayerName());
                    this.buyButtonPlayer5.setDisable(false);
                    this.buyButtonPlayer5.setManaged(true);
                }
            }
            if(gameData.getPlayerData().size()>5  && gameData.getPlayerData().get(5).isIsHuman()) {
                if (!gameData.getPlayerData().get(5).isIsQuit()) {
                    this.buyButtonPlayer6.textProperty().set("Buy: " +gameData.getPlayerData().get(5).getPlayerName());
                    this.buyButtonPlayer6.setDisable(false);
                    this.buyButtonPlayer6.setManaged(true);
                }
            }

        }



    }


    public void SetQuitButton() {

        HideQuitButton();

        if (gameData == null || gameData.getCurrentHand() == null) return;
        if(this.gameData.getCurrentHand().Is_current_hand_finished()) {


            if (!gameData.getPlayerData().get(0).isIsQuit() && gameData.getPlayerData().get(0).isIsHuman()) {
                this.quitButtonPlayer1.textProperty().set("Quit: " + gameData.getPlayerData().get(0).getPlayerName());
                this.quitButtonPlayer1.setDisable(false);
                this.quitButtonPlayer1.setManaged(true);
            }

            if (!gameData.getPlayerData().get(1).isIsQuit()  && gameData.getPlayerData().get(1).isIsHuman()) {
                this.quitButtonPlayer2.textProperty().set("Quit: " +gameData.getPlayerData().get(1).getPlayerName());
                this.quitButtonPlayer2.setDisable(false);
                this.quitButtonPlayer2.setManaged(true);
            }
            if (!gameData.getPlayerData().get(2).isIsQuit()  && gameData.getPlayerData().get(2).isIsHuman()) {
                this.quitButtonPlayer3.textProperty().set("Quit: " +gameData.getPlayerData().get(2).getPlayerName());
                this.quitButtonPlayer3.setDisable(false);
                this.quitButtonPlayer3.setManaged(true);
            }
            if(gameData.getPlayerData().size()>3){
                if (!gameData.getPlayerData().get(3).isIsQuit()  && gameData.getPlayerData().get(3).isIsHuman()) {
                    this.quitButtonPlayer4.textProperty().set("Quit: " +gameData.getPlayerData().get(3).getPlayerName());
                    this.quitButtonPlayer4.setDisable(false);
                    this.quitButtonPlayer4.setManaged(true);
                }
            }
            if(gameData.getPlayerData().size()>4){
                if (!gameData.getPlayerData().get(4).isIsQuit()  && gameData.getPlayerData().get(4).isIsHuman()) {
                    this.quitButtonPlayer5.textProperty().set("Quit: " +gameData.getPlayerData().get(4).getPlayerName());
                    this.quitButtonPlayer5.setDisable(false);
                    this.quitButtonPlayer5.setManaged(true);
                }
            }
            if(gameData.getPlayerData().size()>5  && gameData.getPlayerData().get(5).isIsHuman()) {
                if (!gameData.getPlayerData().get(5).isIsQuit()) {
                    this.quitButtonPlayer6.textProperty().set("Quit: " +gameData.getPlayerData().get(5).getPlayerName());
                    this.quitButtonPlayer6.setDisable(false);
                    this.quitButtonPlayer6.setManaged(true);
                }
            }

        }



    }

    @FXML void HandleBuyPlayer1(ActionEvent event) {
        gameData.getPlayerData().get(0).MakeABuy();
        gameData.getPlayerData().get(0).UpdatePlayer();

    }

    @FXML void HandleBuyPlayer2(ActionEvent event) {
        gameData.getPlayerData().get(1).MakeABuy();
        gameData.getPlayerData().get(1).UpdatePlayer();

    }

    @FXML
    void HandleBuyPlayer3(ActionEvent event) {
        gameData.getPlayerData().get(2).MakeABuy();
        gameData.UpdatePlayers();


    }

    @FXML
    void HandleBuyPlayer4(ActionEvent event) {
        gameData.getPlayerData().get(3).MakeABuy();
        gameData.UpdatePlayers();


    }

    @FXML
    void HandleBuyPlayer5(ActionEvent event) {
        gameData.getPlayerData().get(4).MakeABuy();
        gameData.UpdatePlayers();


    }

    @FXML
    void HandleBuyPlayer6(ActionEvent event) {
        gameData.getPlayerData().get(5).MakeABuy();
        gameData.UpdatePlayers();

    }

   private void HandleQuitPlayerGeneric(Button quitButton,Button buyButton ,int index)
    {
        gameData.getPlayerData().get(index).QuitFromGame();
        gameData.getPlayerData().get(index).playerStateProperty().set("Quit");
        quitButton.textProperty().set(gameData.getPlayerData().get(index).getPlayerName() + "\nQuit");
        quitButton.setDisable(true);
        buyButton.textProperty().set(gameData.getPlayerData().get(index).getPlayerName() + "\nQuit");
        buyButton.setDisable(true);
    }

    @FXML
    void HandleQuitPlayer1(ActionEvent event) {

        HandleQuitPlayerGeneric(this.quitButtonPlayer1,this.buyButtonPlayer1,0);

    }

    @FXML
    void HandleQuitPlayer2(ActionEvent event) {
        HandleQuitPlayerGeneric(this.quitButtonPlayer2,this.buyButtonPlayer2,1);

    }

    @FXML
    void HandleQuitPlayer3(ActionEvent event) {
        HandleQuitPlayerGeneric(this.quitButtonPlayer3,this.buyButtonPlayer3,2);


    }

    @FXML
    void HandleQuitPlayer4(ActionEvent event) {
        HandleQuitPlayerGeneric(this.quitButtonPlayer4,this.buyButtonPlayer4,3);



    }

    @FXML
    void HandleQuitPlayer5(ActionEvent event) {
        HandleQuitPlayerGeneric(this.quitButtonPlayer5,this.buyButtonPlayer5,4);
    }

    @FXML
    void HandleQuitPlayer6(ActionEvent event) {
        HandleQuitPlayerGeneric(this.quitButtonPlayer6,this.buyButtonPlayer6,5);
    }



}
