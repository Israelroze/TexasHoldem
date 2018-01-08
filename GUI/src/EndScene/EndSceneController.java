package EndScene;

import GameScene.GameData.GameData;
import GameScene.GameData.PlayerData;
import GameScene.WinnersTable.TableViewController;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import GameLogic.*;
public class EndSceneController implements Initializable{

    @FXML private VBox EndVbox;
    @FXML private Button endButton;
    @FXML private HBox endHBox;
    private GameData gameData;
    private GameLogic gameLogic;



    //Setter



    public void BuildWinnersTableArea() {
        FXMLLoader loader = new FXMLLoader();
        URL url =getClass().getResource("/GameScene/WinnersTable/TableView.fxml");
        loader.setLocation(url);

        try {
            VBox winnerVbox = loader.load();


            TableViewController winnersTable = loader.getController();
            ObservableList<PlayerData> data = gameData.GetDataForTable();

            TableView tableView = winnersTable.getPlayersTable();
            tableView.setItems(data);
            //this.StackMainBoard.getChildren().add(CommArea);


            this.endHBox.getChildren().add(tableView);

            TableColumn<?,?> a= (TableColumn<?, ?>) tableView.getColumns().get(tableView.getColumns().size() -1 );
            a.setSortType(TableColumn.SortType.DESCENDING);

            tableView.getSortOrder().add(tableView.getColumns().get(tableView.getColumns().size() -1 ));

            tableView.prefWidthProperty().set(300);
            tableView.setFixedCellSize(25);
            tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(30));

            // tableView.prefWidthProperty().bind(Bindings.size(tableView.getColumns()).multiply(tableView.getFixedCellSize()).add(5));
            tableView.prefWidthProperty().set(300);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setGameLogic(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }




    @FXML
    void handleEndButton(ActionEvent event) {
        this.endButton.setDisable(true);
        gameLogic.StartNewWelcomeScene();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
