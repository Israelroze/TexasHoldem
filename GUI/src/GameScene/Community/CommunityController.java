package GameScene.Community;


import Utils.ImageUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class CommunityController implements Initializable {

    @FXML private VBox commVbox;
    @FXML private HBox CardHBox;
    @FXML private HBox PotHBox;

    private List<String> communityCards;
    private List<ImageView> communityCardsImages;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void UpdateCommunityCards(List<String> communityCards) {this.communityCards = communityCards; }

    public void UpdateCommunityCards(){

        communityCardsImages.clear();

        for(int i=0; i<communityCards.size();i++ )
        {
            communityCardsImages.add(ImageUtils.getImageView(communityCards.get(0) + ".png"));
        }
        commVbox.getChildren().addAll(communityCardsImages);

    }

    public void HideCommunityCards()
    {
        this.CardHBox.getChildren().clear();
    }
}
