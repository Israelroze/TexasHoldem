
package GameScene.GameStatusBox;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GameStatusBoxController {
    @FXML
    private Label CurrentHandNumberLabel;

    @FXML
    private Label GameMoneyLabel;

    @FXML
    private Label BigLabel;

    @FXML
    private Label SmallLabel;

    @FXML
    void initialize() {


    }

    public Label getCurrentHandNumberLabel() {
        return CurrentHandNumberLabel;
    }

    public Label getGameMoneyLabel() {
        return GameMoneyLabel;
    }

    public Label getBigLabel() {
        return BigLabel;
    }

    public Label getSmallLabel() {
        return SmallLabel;
    }

}


