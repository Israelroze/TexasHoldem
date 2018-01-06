package GameScene.PlayerCube;


import Game.Game;
import Utils.ImageUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.naming.Binding;


public class PlayerCubeController implements Initializable {
    @FXML private Pane Card1;
    @FXML private Pane Card2;
    @FXML private Label NameLable;
    @FXML private Label TypeLabel;
    @FXML private Label NumberOfBuyLabel;
    @FXML private Label NumberOfWinsLabel;
    @FXML private Label MoneyLabel;



    @FXML private HBox PlayerHbox;

    private ImageView Card1View;
    private ImageView Card2View;
    private final String UnknownCardImageName ="UU.png";
    private String firstCardName;
    private String secondCardName;
    private int PlayerId;

    private SimpleBooleanProperty IsInReplayMode;
    private SimpleBooleanProperty CurrentPlayerId;


    public void setCards(String card1, String card2) {
        firstCardName = card1 + ".png";
        secondCardName= card2+ ".png";
        Card1View = ImageUtils.getImageView(firstCardName);
        Card1View.fitHeightProperty().bind(Card1.heightProperty());
        Card1View.fitWidthProperty().bind(Card1.widthProperty());
        Card2View = ImageUtils.getImageView(secondCardName);
        Card2View.fitHeightProperty().bind(Card2.heightProperty());
        Card2View.fitWidthProperty().bind(Card2.widthProperty());
        Card1.getChildren().add(Card1View);
        Card2.getChildren().add(Card2View);
        this.HideCards();
    }


    public boolean isIsInReplayMode() { return IsInReplayMode.get(); }

    public SimpleBooleanProperty isInReplayModeProperty() { return IsInReplayMode;}

    public void setPlayerId(int id) { this.PlayerId = id; }

    public boolean isCurrentPlayerId() { return CurrentPlayerId.get(); }

    public SimpleBooleanProperty currentPlayerIdProperty() { return CurrentPlayerId;}

    public Label getNameLable() {
        return NameLable;
    }

    public Label getStateLabel() {
        return TypeLabel;
    }

    public Label getNumberOfBuyLabel() {
        return NumberOfBuyLabel;
    }

    public Label getNumberOfWinsLabel() {
        return NumberOfWinsLabel;
    }

    public Label getMoneyLabel() {
        return MoneyLabel;
    }

    private void ShowCards() {
        if (this.CurrentPlayerId.get() || this.IsInReplayMode.get() )
        {
            Card1View.setImage(ImageUtils.getImage(this.firstCardName));
            Card2View.setImage(ImageUtils.getImage(this.secondCardName));
        }

    }

    private void HideCards() {

        if(!this.IsInReplayMode.get()) {
            Card1View.setImage(ImageUtils.getImage(this.UnknownCardImageName));
            Card2View.setImage(ImageUtils.getImage(this.UnknownCardImageName));
        }

    }

    @FXML
    void FirstCardPressedHandle(MouseEvent event) { ShowCards(); }

    @FXML
    void FirstCardReleasedHandle(MouseEvent event) { HideCards(); }

    @FXML
    void SecondCardPressedHandle(MouseEvent event) { ShowCards(); }

    @FXML
    void SecondCardReleasedHandle(MouseEvent event){ HideCards(); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.CurrentPlayerId = new SimpleBooleanProperty(false);
        this.IsInReplayMode = new SimpleBooleanProperty(false);

    }


    public void AllUnbind() {

        Card1View.fitHeightProperty().unbind();
        Card2View.fitHeightProperty().unbind();
        Card1View.fitWidthProperty().unbind();
        Card2View.fitWidthProperty().unbind();
    }
}
