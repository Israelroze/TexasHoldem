package GameScene.MainOption;

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



    public void SetRequiredButton(Boolean satrtGame, Boolean backButton, Boolean startNewGameButton, Boolean replayButton)
    {
        this.isRequiredBackButton= backButton;
        this.isRequiredReplayButton= replayButton;
        this.isRequiredStartGameButton = satrtGame;
        this.isRequiredStartNewHandButton = startNewGameButton;

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

    }

    @FXML void HandleReplayButton(MouseEvent event) {

    }

    @FXML void HandleStartGameButtom(MouseEvent event) {

    }

    @FXML void HandleStartNewHandButton(MouseEvent event) {

    }

}
