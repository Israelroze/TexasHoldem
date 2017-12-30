package GameScene.MainOption;

import GameScene.GameController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MainOptionController {

    @FXML private Button StartGameButton;
    @FXML private Button BackButton;
    @FXML private Button StartNewHandButton;
    @FXML private Button ReplayButton;

    private Boolean isRequiredStartGameButton;
    private Boolean isRequiredBackButton;
    private Boolean isRequiredStartNewHandButton;
    private Boolean isRequiredReplayButton;
    private GameController mainGame;


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
        this.mainGame.OnClickBack();
    }

    @FXML void HandleReplayButton(MouseEvent event) {
        this.mainGame.OnClickReplay();
    }

    @FXML void HandleStartGameButtom(MouseEvent event) {
        this.mainGame.OnClickStart();
    }

    @FXML void HandleStartNewHandButton(MouseEvent event) {
        this.mainGame.OnClickHand();
    }

}
