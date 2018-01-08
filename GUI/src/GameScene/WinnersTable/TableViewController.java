package GameScene.WinnersTable;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TableViewController  implements Initializable{

    public TableView<?> getPlayersTable() { return playersTable; }

    public void setPlayersTable(TableView<?> playersTable) {
        this.playersTable = playersTable;
    }


    @FXML private TableView<?> playersTable;
    @FXML private TableColumn<?, ?> nameColumn;
    @FXML private TableColumn<?, ?> typeColumn;
    @FXML private TableColumn<?, ?> buysColumn;
    @FXML private TableColumn<?, ?> hadsWonsColumn;
    @FXML private TableColumn<?, ?> moneyColumn;
    @FXML private HBox HBoxTableView;

    public TableColumn<?, ?> getNameColumn() { return nameColumn; }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        this.typeColumn.setCellValueFactory(new PropertyValueFactory<>("typeAsString"));
        this.buysColumn.setCellValueFactory(new PropertyValueFactory<>("numOfBuy"));
        this.hadsWonsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfWins"));
        this.moneyColumn.setCellValueFactory(new PropertyValueFactory<>("numOfChips"));

    }
}
