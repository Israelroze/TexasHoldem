package GameScene.PlayerCube;

import Utils.ImageUtils;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;



import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;



public class PlayerCubeController implements Initializable {
    @FXML private Pane Card1;
    @FXML private Pane Card2;
    @FXML private Label NameLable;
    @FXML private Label TypeLabel;
    @FXML private Label NumberOfBuyLabel;
    @FXML private Label NumberOfWinsLabel;



    private final String UnknownCardImageName ="UU.png";


    ImageView Card1View;
    ImageView Card2View;


    public Label getNameLable() {
        return NameLable;
    }

    public Label getTypeLabel() {
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



    @FXML private Label MoneyLabel;


    @FXML
    void FirstCardPressedHandle(MouseEvent event) {

    }

    @FXML
    void FirstCardReleasedHandle(MouseEvent event) {

    }

    @FXML
    void SecondCardPressedHandle(MouseEvent event) {

    }

    @FXML
    void SecondCardReleasedHandle(MouseEvent event){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       Card1View = ImageUtils.getImageView(this.UnknownCardImageName);
       Card1View.fitHeightProperty().bind(Card1.heightProperty());
       Card1View.fitWidthProperty().bind(Card1.widthProperty());
        Card2View = ImageUtils.getImageView(this.UnknownCardImageName);
        Card2View.fitHeightProperty().bind(Card2.heightProperty());
        Card2View.fitWidthProperty().bind(Card2.widthProperty());
    }



    public void AllUnbind()
    {

        Card1View.fitHeightProperty().unbind();
        Card2View.fitHeightProperty().unbind();
        Card1View.fitWidthProperty().unbind();
        Card2View.fitWidthProperty().unbind();
    }
}
