
package WelcomeScene;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import API.InterfaceAPI;
import FileLoding.FileLoading;
import GameLogic.GameLogic;
import GameScene.GameController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.naming.Binding;
import javax.rmi.CORBA.Util;

public class WelcomeController implements Initializable {

    @FXML private Button loadFromXmlFileButton;
    @FXML private ProgressBar taskProgressBar;
    @FXML private VBox welcomeVbox;
    @FXML private Label XMLErrorMessageLabel;

    private Stage primaryStage;
    private FXMLLoader loader;
    private SimpleStringProperty selectedFileProperty;
    private SimpleBooleanProperty isFileSelected;
    private GameLogic gameLogic;
    private SimpleBooleanProperty isXMLFileLoaded;
    private InterfaceAPI model;


    private FileLoading currentRunningTask;
    public void setModel(InterfaceAPI model) {
        this.model = model;
    }
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    public void serLoader (FXMLLoader loader) {this.loader = loader;}
    public void setGameLogic (GameLogic gl){
        this.gameLogic=gl;
    }

    public WelcomeController()
    {

        selectedFileProperty = new SimpleStringProperty();
        isFileSelected = new SimpleBooleanProperty(false);
        isXMLFileLoaded = new SimpleBooleanProperty(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TextButtonNextSkinNumberInButton();
    }

    @FXML
    private Button ChangeStyle;



    @FXML
    public void TextButtonNextSkinNumberInButton()
    {
        int nextNumber = GameLogic.CSS_STYLE_NUMBER.get() %3 +1;
        this.ChangeStyle.setText("Move to Skin " + String.valueOf(nextNumber));

    }

    public void handleChangeStyle(ActionEvent event) {
        GameLogic.AccomdateSkinNumber();
        TextButtonNextSkinNumberInButton();
        gameLogic.SetNextSkin();



    }

    @FXML protected void handleLoadFromXmlFileButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select XML file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("xml files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile == null) {
            return;
        }
        String absolutePath = selectedFile.getAbsolutePath();
        System.out.println(absolutePath);
        selectedFileProperty.set(absolutePath);

        LoadXMLFile(absolutePath);

        //isFileSelected.set(false);



        // /for testing
       //LoadXMLFile("C:\\Users\\israe\\Google Drive\\Study\\JavaCourse\\TexasHoldem\\Engine\\Resource\\ex1-basic.xml");
       //LoadXMLFile("C:\\Users\\Avishay\\Desktop\\ex1-basic.xml");
    }



    private void LoadXMLFile(String filePath) {
        currentRunningTask = new FileLoading(model,filePath);
        //controller.bindTaskToUIComponents(currentRunningTask, onFinish);

        bindTaskToUIComponents(currentRunningTask, () -> {
            this.XMLErrorMessageLabel.textProperty().bind(Bindings
                    .when(currentRunningTask.isErrorHappendProperty())
                    .then(currentRunningTask.errorMessageProperty().get())
                    .otherwise(""));

        });
        taskProgressBar.setVisible(true);
        new Thread(currentRunningTask).start();
        //log("Loading finished");



    }

    public void bindTaskToUIComponents(Task<Boolean> aTask, Runnable onFinish) {
        // task message
        //taskMessageLabel.textProperty().bind(aTask.messageProperty());

        // task progress bar
        taskProgressBar.progressProperty().bind(aTask.progressProperty());
        taskProgressBar.setPrefWidth(primaryStage.getWidth());

        aTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            onTaskFinished(Optional.ofNullable(onFinish));
        });
    }

    public void onTaskFinished(Optional<Runnable> onFinish) {
  //      this.taskMessageLabel.textProperty().unbind();
    //    this.progressPercentLabel.textProperty().unbind();
        this.taskProgressBar.progressProperty().unbind();
        onFinish.ifPresent(Runnable::run);
        if(!currentRunningTask.isIsErrorHappend())
        StartGameScene();
    }

    private void StartGameScene() {
        try {

            FXMLLoader n = new FXMLLoader();
            URL url = getClass().getResource("/GameScene/Game.fxml");
            n.setLocation(url);
            Parent root1 = n.load();
            GameController gameController = n.getController();
            gameController.setModel(model);
            gameController.SetGameLogic(this.gameLogic);
            model.StartGame();

            gameController.StartGameView();
            setPrimaryStage(this.primaryStage);
            Scene scene = new Scene(root1, 1200, 600);
            scene.getStylesheets().addAll(getClass().getResource("/resources/css/game" + GameLogic.CSS_STYLE_NUMBER.get()  + ".css").toExternalForm());



            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
            Utils.Utils.log("Game FXML Loading error.");


        }
    }
public static void log(String message) { System.out.println(message); }
}