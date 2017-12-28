package GameScene;

import API.InterfaceAPI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {




    private Stage primaryStage;
    private InterfaceAPI model;

    @FXML private BorderPane gameBorderPane;
    @FXML private StackPane pane;
    @FXML private Button b1;
    @FXML private Button b2;
    @FXML private Button b3;
    @FXML private Button b4;




    public GameController(){};
    public void setModel(InterfaceAPI model) {
        this.model = model;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        b2.setVisible(false);
        b3.setVisible(false);
        b4.setVisible(false);
        b1.translateXProperty().bind(pane.widthProperty().divide(5));
        b1.translateYProperty().bind(pane.heightProperty().divide(5));
        //b2.translateXProperty().bind(pane.heightProperty().multiply());
//        b2.translateYProperty().bind(pane.widthProperty(). multiply(1/2));
//        b3.translateXProperty().bind(pane.heightProperty().multiply(1));
//        b3.translateYProperty().bind(pane.widthProperty(). multiply(1/2));
//        b4.translateXProperty().bind(pane.heightProperty().multiply(1));
//        b4.translateYProperty().bind(pane.widthProperty(). multiply(1/2));

    }
    @FXML protected  void test()
    {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(getClass().getResource("/GameScene/BetOptions/BetOptions.fxml"));
        try {
            Node vbox= load.load();
            this.gameBorderPane.setRight(vbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void ShowGameToPlayer(){


    }
}
