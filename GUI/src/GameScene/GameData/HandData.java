package GameScene.GameData;
import API.InterfaceAPI;
import Card.Card;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedList;
import java.util.List;

public class HandData {
    private List<Card> community;
    private List<String> communityAsString;
    private SimpleStringProperty pot;
    private SimpleBooleanProperty is_current_bid_cycle_finished;
    private SimpleIntegerProperty current_player_id;
    private InterfaceAPI model;

    public HandData(InterfaceAPI model) {
        this.model=model;
        this.community=new LinkedList<Card>();
        this.pot=new SimpleStringProperty(Integer.toString(model.GetPot()));
        this.current_player_id=new SimpleIntegerProperty(-1);
        this.is_current_bid_cycle_finished=new SimpleBooleanProperty(false);
        communityAsString = new LinkedList<>();

    }

    public int getPot() {
        return model.GetPot();
    }

    public SimpleStringProperty potProperty() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot.set(Integer.toString(model.GetPot()));
    }

    public void setCommunityCards() {
        this.community=this.model.GetCommunityCards();
        this.communityAsString.clear();

        for( Card card : this.community)
        {
            this.communityAsString.add(card.toString());
        }
    }

    public int getCurrent_player_id() {
        return current_player_id.get();
    }

    public SimpleIntegerProperty current_player_idProperty() {
        return this.current_player_id;
    }

    public void setCurrent_player_id() {
        this.current_player_id.set(this.model.GetCurrentPlayerID());
    }

    public boolean isIs_current_bid_cycle_finished() {
        return is_current_bid_cycle_finished.get();
    }

    public SimpleBooleanProperty is_current_bid_cycle_finishedProperty() {
        return is_current_bid_cycle_finished;
    }

    public void setIs_current_bid_cycle_finished() {
        this.is_current_bid_cycle_finished.set(this.model.IsCurrentBidCycleFinished());
    }
    public void UpdateHand()
    {
        this.setCurrent_player_id();
        this.setIs_current_bid_cycle_finished();
        System.out.println("Hand Updated");
    }

}
