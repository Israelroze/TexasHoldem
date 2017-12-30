package GameScene.GameData;

import API.InterfaceAPI;
import Card.Card;
import Utils.ImageUtils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;
import java.util.PrimitiveIterator;

public class PlayerData {


    private InterfaceAPI model;
    private SimpleIntegerProperty placeInTable;
    private SimpleIntegerProperty numOfChips;
    private SimpleIntegerProperty numOfBuy;
    private SimpleIntegerProperty numOfWins;
    private SimpleBooleanProperty isDealer;
    private SimpleBooleanProperty isBig;
    private SimpleBooleanProperty isSmall;
    private SimpleBooleanProperty isHuman;
    private SimpleStringProperty Card1;
    private SimpleStringProperty Card2;
    private List<Card> playerCards;
    private SimpleStringProperty playerName;
    private SimpleIntegerProperty id;

    private final String UnknownCardImageName ="UU.png";



    public PlayerData(InterfaceAPI model, int placeInTable, int id) {
        this.model = model;
        this.id = new SimpleIntegerProperty(id);
        this.placeInTable= new SimpleIntegerProperty(placeInTable);
//        this.numOfChips = new SimpleIntegerProperty(model.GetPlayerPot(id));
//        this.numOfBuy = new SimpleIntegerProperty(model.GetPlayerNumOfBuy(id));
//        this.numOfWins = new SimpleIntegerProperty(model.GetPlayerNumOfWins(id));
//        this.isDealer = new SimpleBooleanProperty(model.GetPlayerIsDealer(id));
//        this.isBig = new SimpleBooleanProperty(model.GetPlayerIsBig(id));
//        this.isSmall = new SimpleBooleanProperty(model.GetPlayerIsSmall(id));
        this.numOfChips = new SimpleIntegerProperty(0);
        this.numOfBuy = new SimpleIntegerProperty(0);
        this.numOfWins = new SimpleIntegerProperty(0);
        this.isDealer = new SimpleBooleanProperty(false);
        this.isBig = new SimpleBooleanProperty(false);
        this.isSmall = new SimpleBooleanProperty(false);
        this.isHuman = new SimpleBooleanProperty(model.GetPlayerIsHuman(id));
        this.playerName = new SimpleStringProperty(model.GetPlayerName(id));
        this.playerCards = model.GetPlayersCards(id);
        this.Card1 = new SimpleStringProperty();
        this.Card1 = new SimpleStringProperty(UnknownCardImageName);
        this.Card2 = new SimpleStringProperty();
        this.Card2 = new SimpleStringProperty(UnknownCardImageName);

    }

    public boolean isIsHuman() { return isHuman.get(); }

    public SimpleBooleanProperty isHumanProperty() { return isHuman; }

    public String getPlayerName() { return playerName.get(); }

    public SimpleStringProperty playerNameProperty() { return playerName; }

    public void setNumOfChips() { this.numOfChips.set(model.GetPlayerPot(this.id.get())); }

    public void setNumOfBuy() { this.numOfBuy.set(model.GetPlayerNumOfBuy(this.id.get()));}

    public void setNumOfWins() { this.numOfWins.set(model.GetPlayerNumOfWins(this.id.get())); }

    public void setIsDealer() { this.isDealer.set(model.GetPlayerIsDealer(this.id.get())); }

    public void setIsBig() { this.isBig.set(model.GetPlayerIsBig(this.id.get())); }

    public void setIsSmall() { this.isSmall.set(model.GetPlayerIsSmall(this.id.get())); }

    public boolean isIsDealer() {
        return isDealer.get();
    }

    public SimpleBooleanProperty isDealerProperty() {
        return isDealer;
    }

    public boolean isIsBig() {
        return isBig.get();
    }

    public SimpleBooleanProperty isBigProperty() {
        return isBig;
    }

    public boolean isIsSmall() {
        return isSmall.get();
    }

    public SimpleBooleanProperty isSmallProperty() {
        return isSmall;
    }

    public int getPlaceInTable() {
        return placeInTable.get();
    }

    public SimpleIntegerProperty placeInTableProperty() {
        return placeInTable;
    }

    public int getNumOfChips() {
        return numOfChips.get();
    }

    public SimpleIntegerProperty numOfChipsProperty() {
        return numOfChips;
    }

    public int getNumOfBuy() {
        return numOfBuy.get();
    }

    public SimpleIntegerProperty numOfBuyProperty() {
        return numOfBuy;
    }

    public int getNumOfWins() {
        return numOfWins.get();
    }

    public SimpleIntegerProperty numOfWinsProperty() {
        return numOfWins;
    }

    public int getId() { return id.get(); }

    public SimpleIntegerProperty idProperty() { return id; }
    public String getCard1() { return Card1.get(); }
    public SimpleStringProperty card1Property() { return Card1; }
    public String getCard2() { return Card2.get(); }
    public SimpleStringProperty card2Property() { return Card2; }



    private void SetRealCard1() { this.Card1.set(this.playerCards.get(0).toString()); }
    private void SetRealCard2() { this.Card2.set(this.playerCards.get(0).toString()); }
    private void HideCard1() { this.Card1.set(this.UnknownCardImageName); }
    private void HideCard2() { this.Card2.set(this.UnknownCardImageName); }

    public void ShowCard()
    {
        SetRealCard1();
        SetRealCard2();
    }

    public void HideCards(){
        HideCard1();
        HideCard2();
    }

}

