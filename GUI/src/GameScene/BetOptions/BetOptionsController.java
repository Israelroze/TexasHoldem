package GameScene.BetOptions;

import API.InterfaceAPI;
import GameScene.GameController;
import Move.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BetOptionsController implements Initializable {

    private List<MoveType> allowedMoves;
    private GameController mainGame;
    private InterfaceAPI model;
    private SimpleIntegerProperty higeStake;
    private SimpleIntegerProperty lowStake;
    @FXML private TextField betTextFiled;
    @FXML private Button betButton;
    @FXML private TextField raiseTextFiled;
    @FXML private Button raiseButton;
    @FXML private Button callButton;
    @FXML private Button checkButton;
    @FXML private Button foldButton;
    @FXML private VBox raiseVbox;
    @FXML private VBox betVbox;
    @FXML private VBox foldVbox;
    @FXML private VBox callVbox;
    @FXML private VBox checkVbox;





    private boolean isIntegerAndInRange(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        int low = this.lowStake.get();
        int high =  this.higeStake.get();
        int asInt = Integer.parseInt(s);

        if (asInt < low || asInt>high) return false;
        return true;
    }

    @FXML void HandleHumanBet(MouseEvent event) {

        String text = betTextFiled.getText();
        if(isIntegerAndInRange(text,10))
            this.mainGame.setHumanMove(new Move(MoveType.BET,Integer.parseInt(text)));
    }

    @FXML void HandleHumanCall(MouseEvent event) {
        this.mainGame.setHumanMove(new Move(MoveType.CALL,0));
    }

    @FXML void HandleHumanFold(MouseEvent event) {
        this.mainGame.setHumanMove(new Move(MoveType.FOLD,0));
    }



    @FXML
    void HandleHumanRaise(MouseEvent event) {

        String text = raiseTextFiled.getText();
        if(isIntegerAndInRange(text,10)) this.mainGame.setHumanMove(new Move(MoveType.RAISE, Integer.parseInt(text)));
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

    private String PromptText() { return "From " + String.valueOf(this.lowStake.get())+ " to " + String.valueOf(this.higeStake.get()); }

    public void updateOptions()
    {

        this.foldButton.setDisable(true);
        this.foldButton.setManaged(false);
        this.betButton.setDisable(true);
        this.betButton.setManaged(false);
        this.betTextFiled.setDisable(true);
        this.betTextFiled.setManaged(false);
        this.checkButton.setDisable(true);
        this.checkButton.setManaged(false);
        this.raiseButton.setDisable(true);
        this.raiseButton.setManaged(false);
        this.raiseTextFiled.setDisable(true);
        this.raiseTextFiled.setManaged(false);
        this.callButton.setDisable(true);
        this.callButton.setManaged(false);
        this.raiseVbox.setDisable(true);
        this.raiseVbox.setManaged(false);
        this.betVbox.setDisable(true);
        this.betVbox.setManaged(false);
        this.checkVbox.setDisable(true);
        this.checkVbox.setManaged(false);
        this.foldVbox.setDisable(true);
        this.foldVbox.setManaged(false);
        this.callVbox.setDisable(true);
        this.callVbox.setManaged(false);

        if(allowedMoves!=null) {
            if (allowedMoves.contains(MoveType.FOLD)) {
                this.foldButton.setDisable(false);
                this.foldButton.setManaged(true);
                this.foldVbox.setDisable(false);
                this.foldVbox.setManaged(true);


            }
            if (allowedMoves.contains(MoveType.BET)) {

                this.betVbox.setDisable(false);
                this.betVbox.setManaged(true);
                this.betButton.setDisable(false);
                this.betButton.setManaged(true);
                this.betTextFiled.setDisable(false);
                this.betTextFiled.setManaged(true);
                SetLowAndHighStake();
                this.betTextFiled.setPromptText(PromptText());
            }
            if (allowedMoves.contains(MoveType.CHECK)) {
                this.checkVbox.setDisable(false);
                this.checkVbox.setManaged(true);
                this.checkButton.setDisable(false);
                this.checkButton.setManaged(true);
            }
            if (allowedMoves.contains(MoveType.RAISE)) {
                this.raiseVbox.setDisable(false);
                this.raiseVbox.setManaged(true);
                this.raiseButton.setDisable(false);
                this.raiseButton.setManaged(true);
                this.raiseTextFiled.setDisable(false);
                this.raiseTextFiled.setManaged(true);
                SetLowAndHighStake();
                this.raiseTextFiled.setPromptText(PromptText());

            }
            if (allowedMoves.contains(MoveType.CALL)) {
                this.callVbox.setDisable(false);
                this.callVbox.setManaged(true);
                this.callButton.setDisable(false);
                this.callButton.setManaged(true);
            }
        }
    }



      //  if(allowedMoves!=null) {
      //      if (!allowedMoves.contains(MoveType.FOLD)) {
      //          this.foldButton.setVisible(false);
      //          this.foldButton.setManaged(false);
//
      //      }
      //      if (!allowedMoves.contains(MoveType.BET)) {
//
      //          this.betButton.setVisible(false);
      //          this.betButton.setManaged(false);
      //          this.betTextFiled.setVisible(false);
      //          this.betTextFiled.setManaged(false);
      //          SetLowAndHighStake();
      //      }
      //      if (!allowedMoves.contains(MoveType.CHECK)) {
//
      //          this.checkButton.setVisible(false);
      //          this.checkButton.setManaged(false);
      //      }
      //      if (!allowedMoves.contains(MoveType.RAISE)) {
//
      //          this.raiseButton.setVisible(false);
      //          this.raiseButton.setManaged(false);
      //          this.raiseTextFiled.setVisible(false);
      //          this.raiseTextFiled.setManaged(false);
      //          SetLowAndHighStake();
//
      //      }
      //      if (!allowedMoves.contains(MoveType.CALL)) {
      //          this.callButton.setVisible(false);
      //          this.callButton.setManaged(false);
      //      }
      //  }
      //  else
      //  {
      //      this.foldButton.setVisible(false);
      //      this.foldButton.setManaged(false);
      //      this.betButton.setVisible(false);
      //      this.betButton.setManaged(false);
      //      this.betTextFiled.setVisible(false);
      //      this.betTextFiled.setManaged(false);
      //      this.checkButton.setVisible(false);
      //      this.checkButton.setManaged(false);
      //      this.raiseButton.setVisible(false);
      //      this.raiseButton.setManaged(false);
      //      this.raiseTextFiled.setVisible(false);
      //      this.raiseTextFiled.setManaged(false);
      //      this.callButton.setVisible(false);
      //      this.callButton.setManaged(false);
      //  }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.higeStake = new SimpleIntegerProperty(-1);
        this.lowStake = new SimpleIntegerProperty(-1);


    }

    private void SetLowAndHighStake()
    {
        this.lowStake.set(model.GetAllowdedStakeRange()[0]);
        this.higeStake.set(model.GetAllowdedStakeRange()[1]);
    }

    public void setModel(InterfaceAPI model) {
        this.model = model;
    }
}
