package GameScene.PlayerCube;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


import java.net.URL;
import java.util.ResourceBundle;




public class PlayerCubeController implements Initializable {
    @FXML private Pane Card1;
    @FXML private Pane Card2;
    @FXML private Label NameLable;
    @FXML private Label TypeLabel;
    @FXML private Label NumberOfBuyLabel;
    @FXML private Label NumberOfWinsLabel;

    public Pane getCard1() {
        return Card1;
    }

    public Pane getCard2() {
        return Card2;
    }

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

    }



    public void GetPlayerValues()
    {



    }
}
