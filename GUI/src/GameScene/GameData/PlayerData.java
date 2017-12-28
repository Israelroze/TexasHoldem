package GameScene.GameData;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.PrimitiveIterator;

public class PlayerData {

    private SimpleIntegerProperty PlaceInTable;
    private SimpleIntegerProperty numOfChips;
    private SimpleIntegerProperty numOfBuy;
    private SimpleIntegerProperty numOfWins;
    private SimpleBooleanProperty isDealer;
    private SimpleBooleanProperty isBig;
    private SimpleBooleanProperty isSmall;

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
        return PlaceInTable.get();
    }

    public SimpleIntegerProperty placeInTableProperty() {
        return PlaceInTable;
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

}

