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
    @FXML private VBox VboxBuy;

    @FXML private Button buyButtonPlayer1;
    @FXML private Button buyButtonPlayer2;
    @FXML private Button buyButtonPlayer3;
    @FXML private Button buyButtonPlayer4;
    @FXML private Button buyButtonPlayer5;
    @FXML private Button buyButtonPlayer6;


    @FXML private VBox VboxQuitGame;
    private Boolean isRequiredStartGameButton;
    private Boolean isRequiredBackButton;
    private Boolean isRequiredStartNewHandButton;
    private Boolean isRequiredReplayButton;
    private GameController mainGame;
    private GameData gameData;



    public void setGameData(GameData gameData) { this.gameData = gameData; }

    public void SetRequiredButton(Boolean startGame, Boolean backButton, Boolean startNewGameButton, Boolean replayButton)
    {
        this.isRequiredBackButton= backButton;
        this.isRequiredReplayButton= replayButton;
        this.isRequiredStartGameButton = startGame;
        this.isRequiredStartNewHandButton = startNewGameButton;

    }

    public void ConnectToMainGame(GameController game)
    {
        this.mainGame =game;
    }

    public void HideButton()
    {
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
    public void SetBuyButton()
    {




        HideBuyButton();

        if (gameData == null || gameData.getCurrentHand() == null) return;
        if(0 < this.gameData.getCurrentHand().getCurrent_bid_number() &&  this.gameData.getCurrentHand().getCurrent_bid_number()<4) {


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


}
