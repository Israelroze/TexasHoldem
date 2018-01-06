package GameScene.Community;


import GameScene.GameData.HandData;
import Utils.ImageUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


public class CommunityController implements Initializable {

    @FXML private VBox commVbox;
    @FXML private HBox CardHBox;
    @FXML private HBox PotHBox;

    @FXML private Label PotLabel;


    private List<ImageView> communityCardsImages;





    private HandData handData;


    @FXML
    void Wrong(MouseEvent event) {
    System.out.println("You are touching wrong Area!");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        communityCardsImages= new LinkedList<>();
        PotLabel.setTextFill(Color.BLUE);
    }

    public void SetHandData(HandData handData){
        this.handData= handData;
    }

    public void  UpdateCommunityCards(){

        communityCardsImages.clear();

        ObservableList<String> cardTemp = this.handData.getCommunityCards();
        CardHBox.getChildren().clear();


        for(int i=0; i<cardTemp.size();i++ )
        {

            communityCardsImages.add(ImageUtils.getImageView(cardTemp.get(i)));
            communityCardsImages.get(i).setFitHeight(72.6);
            communityCardsImages.get(i).setFitWidth(50);
        }
        //CardHBox.setPadding(new Insets(0, 100, 0, 100));
        CardHBox.getChildren().addAll(communityCardsImages);
        //CardHBox.setPadding(new Insets(0, 100, 0, 100));

    }

    public void HideCommunityCards()
    {
        this.CardHBox.getChildren().clear();
    }
    public Label getPotLabel() { return PotLabel; }
}
