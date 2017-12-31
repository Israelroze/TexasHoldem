package GameScene.GameData;

import API.InterfaceAPI;
import GameScene.PlayerCube.PlayerCubeController;
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
    private HandData currentHand;

    public GameData (InterfaceAPI model) {
        this.model = model;
        this.big = new SimpleStringProperty("Big "+(Integer.toString(model.GetBig())));
        this.small = new SimpleStringProperty("Small "+(Integer.toString(model.GetSmall()) ));
        this.maxPot = new SimpleStringProperty("Game Money "+(Integer.toString(0)));
        this.currentHandNumber = new SimpleStringProperty("Hand Number "+(Integer.toString(model.GetCurrentHandNumber())));
        currentPlayerId = new SimpleIntegerProperty(-1);
        //this.currentHand=new HandData(this.model);
        this.LoadPlayers();
    }


    //Setters

    public void setMaxPot() { this.maxPot.set((Integer.toString(model.GetMaxBuys()) + " Game Money")); }

    public void setBig() { this.big.set((Integer.toString(model.GetBig()) + " Big")); }

    public void setSmall() { this.small.set((Integer.toString(model.GetSmall()) + " Small"));}

    public void setCurrentPlayerId() {
        this.currentPlayerId.set(model.GetCurrentPlayerID());
        this.currentHand.setCurrent_player_id();
    }

    public void setCurrentHandNumber() { this.currentHandNumber.set((Integer.toString(model.GetCurrentHandNumber()) + " Hand Number")); }

    public void setCurrentHand() {
        this.currentHand=new HandData(this.model);
    }


    //Getters
    public PlayerData GetPlayerData(int id) {
        for( PlayerData pd:this.playerData)
        {
            if(pd.getId()==id)
            {
                return pd;
            }
        }
        return null;
    }

    public HandData getCurrentHand() {
        return this.currentHand;
    }

    public int getMaxPot() { return model.GetMaxBuys();}

    public int getBig() { return model.GetBig(); }

    public int getSmall() { return model.GetSmall(); }

    public int getCurrentHandNumber() { return model.GetCurrentHandNumber(); }

    public int getCurrentPlayerId() { return currentPlayerId.get(); }

    public int getNumberOfPlayers() {return this.playerData.size();}

    public SimpleStringProperty bigProperty() { return big; }

    public SimpleStringProperty smallProperty() { return small; }

    public SimpleStringProperty maxPotProperty() { return maxPot; }

    public SimpleStringProperty currentHandNumberProperty() {
        return currentHandNumber;
    }

    public SimpleIntegerProperty currentPlayerIdProperty() { return this.currentPlayerId; }

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
    
    public void UpdatePlayers(){
        for(PlayerData p_date:this.playerData)
        {
            p_date.UpdatePlayer();
        }
    }

    public void UpdateAll(){
        this.UpdatePlayers();
        this.currentHand.UpdateHand();
        this.setCurrentPlayerId();
        this.setCurrentHandNumber();
    }
}

