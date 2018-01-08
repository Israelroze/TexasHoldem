package GameScene.ReplayBox;

import GameScene.GameController;
import GameScene.GameData.GameData;
import GameScene.GameData.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ReplayBoxController {

    @FXML
    private Button BackButton;

    @FXML
    private Button ForwardButton;

    @FXML
    private TextArea EventTextBox;

    @FXML
    private GridPane WinChanceTable;

    @FXML
    void ClickBackHandle(MouseEvent event) {
        this.mainGame.OnClickReplayBack();
    }

    @FXML
    void ClickForwardHandle(MouseEvent event) {
        this.mainGame.OnClickReplayForward();
    }

    @FXML
    void HandleEndReplay(ActionEvent event) {
            this.mainGame.OnClickEndRepaly();
    }

    public TextArea getEventTextBox() {
        return EventTextBox;
    }

    public void setEventTextBox(TextArea eventTextBox) {
        EventTextBox = eventTextBox;
    }

    public GridPane getWinChanceTable() {
        return WinChanceTable;
    }

    public void setWinChanceTable(GridPane winChanceTable) {
        WinChanceTable = winChanceTable;
    }


    private GameController mainGame;
    private GameData gameData;
    public void ConnectToMainGame(GameController game)
    {
        this.mainGame =game;
    }
    public void setGameData(GameData gameData) { this.gameData = gameData; }
    public void InitChanceTable() {
        this.WinChanceTable.getChildren().removeAll();
        int index=0;
        for(PlayerData player: this.gameData.getPlayerData())
        {
            Label playerName=new Label();
            playerName.setText(player.getPlayerName());

            Label playerWinChance=new Label();
            playerWinChance.textProperty().bind(this.gameData.getPlayerData().get(index).winChanceProperty());
            this.WinChanceTable.add(playerName,0,index);
            this.WinChanceTable.add(playerWinChance,1,index);
            index++;
        }
    }
}
