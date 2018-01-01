package GameScene.BetOptions;

import GameScene.GameController;
import Move.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BetOptionsController implements Initializable {

    private List<MoveType> allowedMoves;
    private GameController mainGame;
    @FXML private TextField betTextFiled;
    @FXML private Button betButton;
    @FXML private TextField raiseTextFiled;
    @FXML private Button raiseButton;
    @FXML private Button callButton;
    @FXML private Button checkButton;
    @FXML private Button foldButton;


    @FXML void HandleHumanBet(MouseEvent event) {
        this.mainGame.setHumanMove(new Move(MoveType.BET,0));
    }

    @FXML void HandleHumanCall(MouseEvent event) {
        this.mainGame.setHumanMove(new Move(MoveType.CALL,0));
    }

    @FXML void HandleHumanFold(MouseEvent event) {
        this.mainGame.setHumanMove(new Move(MoveType.FOLD,0));
    }

    @FXML
    void HandleHumanRaise(MouseEvent event) {
        this.mainGame.setHumanMove(new Move(MoveType.RAISE,0));
    }

    @FXML void HandleHumanCheck(MouseEvent event) {
        this.mainGame.setHumanMove(new Move(MoveType.CHECK,0));
    }

    public void ConnectToMainGame(GameController game)
    {
        this.mainGame =game;
    }
    public void setAllowedMoves(List<MoveType> allowedMoves) {
        this.allowedMoves = allowedMoves;
    }

    public void updateOptions()
    {
        if(allowedMoves!=null) {
            if (!allowedMoves.contains(MoveType.FOLD)) {
                this.foldButton.setVisible(false);
                this.foldButton.setManaged(false);

            }
            if (!allowedMoves.contains(MoveType.BET)) {

                this.betButton.setVisible(false);
                this.betButton.setManaged(false);
                this.betTextFiled.setVisible(false);
                this.betTextFiled.setManaged(false);
            }
            if (!allowedMoves.contains(MoveType.CHECK)) {

                this.checkButton.setVisible(false);
                this.checkButton.setManaged(false);
            }
            if (!allowedMoves.contains(MoveType.RAISE)) {

                this.raiseButton.setVisible(false);
                this.raiseButton.setManaged(false);
                this.raiseTextFiled.setVisible(false);
                this.raiseTextFiled.setManaged(false);
            }
            if (!allowedMoves.contains(MoveType.CALL)) {
                this.callButton.setVisible(false);
                this.callButton.setManaged(false);
            }
        }
        else
        {
            this.foldButton.setVisible(false);
            this.foldButton.setManaged(false);
            this.betButton.setVisible(false);
            this.betButton.setManaged(false);
            this.betTextFiled.setVisible(false);
            this.betTextFiled.setManaged(false);
            this.checkButton.setVisible(false);
            this.checkButton.setManaged(false);
            this.raiseButton.setVisible(false);
            this.raiseButton.setManaged(false);
            this.raiseTextFiled.setVisible(false);
            this.raiseTextFiled.setManaged(false);
            this.callButton.setVisible(false);
            this.callButton.setManaged(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
