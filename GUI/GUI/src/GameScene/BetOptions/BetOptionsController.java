package GameScene.BetOptions;

import Move.MoveType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BetOptionsController implements Initializable {

    private List<MoveType> allowedMoves;

    @FXML private VBox betOptionVbox;
    @FXML private Label betLabel;
    @FXML private TextField betTextFiled;
    @FXML private Button betButton;
    @FXML private Label raiseLabel;
    @FXML private TextField raiseTextFiled;
    @FXML private Button raiseButton;
    @FXML private Label callLabel;
    @FXML private Button callButton;
    @FXML private Label checkLabel;
    @FXML private Button checkButton;
    @FXML private Label foldLabel;
    @FXML private Button foldButton;


    @FXML void HandleHumanBet(ActionEvent event) {

    }

    @FXML void HandleHumanCall(ActionEvent event) {

    }

    @FXML void HandleHumanFold(ActionEvent event) {

    }

    @FXML
    void HandleHumanRaise(ActionEvent event) {

    }



    public void setAllowedMoves(List<MoveType> allowedMoves) {
        this.allowedMoves = allowedMoves;
    }

    private void removeController()
    {
        if(!allowedMoves.contains(MoveType.FOLD))
        {
         this.foldLabel.setVisible(false);
         this.foldLabel.setManaged(false);
         this.foldButton.setVisible(false);
         this.foldButton.setManaged(false);

        }
        if(!allowedMoves.contains(MoveType.BET))
        {
            this.betLabel.setVisible(false);
            this.betLabel.setManaged(false);
            this.betButton.setVisible(false);
            this.betButton.setManaged(false);
            this.betTextFiled.setVisible(false);
            this.betTextFiled.setManaged(false);
        }
        if(!allowedMoves.contains(MoveType.CHECK))
        {
            this.checkLabel.setVisible(false);
            this.checkLabel.setManaged(false);
            this.checkButton.setVisible(false);
            this.checkButton.setManaged(false);
        }
        if(!allowedMoves.contains(MoveType.RAISE))
        {
            this.raiseLabel.setVisible(false);
            this.raiseLabel.setManaged(false);
            this.raiseButton.setVisible(false);
            this.raiseButton.setManaged(false);
            this.raiseTextFiled.setVisible(false);
            this.raiseTextFiled.setManaged(false);
        }
        if(!allowedMoves.contains(MoveType.CALL))
        {
            this.callLabel.setVisible(false);
            this.callLabel.setManaged(false);
            this.callButton.setVisible(false);
            this.callButton.setManaged(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        removeController();
    }


}
