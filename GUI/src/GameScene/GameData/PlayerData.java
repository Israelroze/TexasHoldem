package GameScene.GameData;

import API.InterfaceAPI;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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



    private SimpleStringProperty playerName;
    private SimpleIntegerProperty id;


    public PlayerData(InterfaceAPI model, int placeInTable, int id) {
        this.model = model;
        this.id = new SimpleIntegerProperty(id);
        this.placeInTable= new SimpleIntegerProperty(placeInTable);
        this.numOfChips = new SimpleIntegerProperty(model.GetPlayerPot(id));
        this.numOfBuy = new SimpleIntegerProperty(model.GetPlayerNumOfBuy(id));
        this.numOfWins = new SimpleIntegerProperty(model.GetPlayerNumOfWins(id));
        this.isDealer = new SimpleBooleanProperty(model.GetPlayerIsDealer(id));
        this.isBig = new SimpleBooleanProperty(model.GetPlayerIsBig(id));
        this.isSmall = new SimpleBooleanProperty(model.GetPlayerIsSmall(id));
        this.isHuman = new SimpleBooleanProperty(model.GetPlayerIsHuman(id));
        this.playerName = new SimpleStringProperty(model.GetPlayerName(id));
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

}

