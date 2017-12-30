package GameScene.GameData;

import API.InterfaceAPI;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedList;
import java.util.List;

public class GameData {

    private SimpleStringProperty currentHandNumber;
    private SimpleStringProperty maxPot;
    private SimpleStringProperty big;
    private SimpleStringProperty small;
    private InterfaceAPI model;
    private List<PlayerData> playerData;
    private SimpleIntegerProperty currentPlayerId;


    public GameData (InterfaceAPI model) {
        this.model = model;
        this.big = new SimpleStringProperty("Big "+(Integer.toString(model.GetBig())));
        this.small = new SimpleStringProperty("Small "+(Integer.toString(model.GetSmall()) ));
        this.maxPot = new SimpleStringProperty("Game Money "+(Integer.toString(0)));
        this.currentHandNumber = new SimpleStringProperty("Hand Number "+(Integer.toString(model.GetCurrentHandNumber())));
        currentPlayerId = new SimpleIntegerProperty(-1);

        this.LoadPlayers();
    }

    //Setters

    public void setCurrentHandNumber() { this.currentHandNumber.set((Integer.toString(model.GetCurrentHandNumber()) + " Hand Number")); }

    public void setMaxPot() { this.maxPot.set((Integer.toString(model.GetMaxBuys()) + " Game Money")); }

    public void setBig() { this.big.set((Integer.toString(model.GetBig()) + " Big")); }

    public void setSmall() { this.small.set((Integer.toString(model.GetSmall()) + " Small"));}

    public void setCurrentPlayerId() { this.currentPlayerId.set(model.GetCurrentPlayerID()); }


    //Getters

    public int getMaxPot() { return model.GetMaxBuys();}

    public SimpleStringProperty maxPotProperty() { return maxPot; }


    public int getBig() { return model.GetBig(); }

    public SimpleStringProperty bigProperty() { return big; }


    public int getSmall() { return model.GetSmall(); }

    public SimpleStringProperty smallProperty() { return small; }


    public int getCurrentHandNumber() { return model.GetCurrentHandNumber(); }

    public SimpleStringProperty currentHandNumberProperty() {
        return currentHandNumber;
    }


    public int getCurrentPlayerId() { return currentPlayerId.get(); }

    public SimpleIntegerProperty currentPlayerIdProperty() { return currentPlayerId; }


    public int getNumberOfPlayers() {return this.playerData.size();}


    public PlayerData getOnePlayerDataForBinding(int playerIndex) { return  playerData.get(playerIndex); }

    public void LoadPlayers() {
        playerData=new LinkedList<>();
        int current_id=model.GetFirstPlayerID();

        for(int i=0;i<model.GetTotalNumberOfPlayers();i++)
        {
            playerData.add(new PlayerData(model,i, current_id));
            current_id=model.GetNextPlayerID(current_id);
        }
    }

}

