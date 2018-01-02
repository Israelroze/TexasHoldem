package GameScene.Community;


import GameScene.GameData.HandData;
import Utils.ImageUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        communityCardsImages= new LinkedList<>();
        PotLabel.setTextFill(Color.BLUE);
    }

    public void SetHandData(HandData handData){
        this.handData= handData;
    }

    public void UpdateCommunityCards(){

        communityCardsImages.clear();
        ObservableList<String> cardTemp = this.handData.getCommunityCards();

        for(int i=0; i<cardTemp.size();i++ )
        {
            communityCardsImages.add(ImageUtils.getImageView(cardTemp.get(0) + ".png"));
        }
        commVbox.getChildren().addAll(communityCardsImages);

    }

    public void HideCommunityCards()
    {
        this.CardHBox.getChildren().clear();
    }
    public Label getPotLabel() { return PotLabel; }
}
