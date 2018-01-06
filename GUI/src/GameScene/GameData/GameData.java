package GameScene.GameData;

import API.InterfaceAPI;
import GameScene.PlayerCube.PlayerCubeController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.print.attribute.standard.MediaSize;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GameData {

    private SimpleStringProperty currentHandNumber;
    private SimpleStringProperty maxPot;
    private SimpleStringProperty big;
    private SimpleStringProperty small;
    private InterfaceAPI model;
    private List<PlayerData> playerData;
    private SimpleIntegerProperty currentPlayerId;
    private HandData currentHand;
    private SimpleBooleanProperty IsCurrentHandFinished;


    public GameData (InterfaceAPI model) {
        this.model = model;
        this.big = new SimpleStringProperty("Big "+(Integer.toString(model.GetBig())));
        this.small = new SimpleStringProperty("Small "+(Integer.toString(model.GetSmall()) ));
        this.maxPot = new SimpleStringProperty("Game Money "+(Integer.toString(0)));
        this.currentHandNumber = new SimpleStringProperty("Hand Number "+(Integer.toString(model.GetCurrentHandNumber())));
        currentPlayerId = new SimpleIntegerProperty(-1);
        this.IsCurrentHandFinished=new SimpleBooleanProperty(false);
        //this.currentHand=new HandData(this.model);
        this.LoadPlayers();
    }

    public boolean isIsCurrentHandFinished() {
        return this.IsCurrentHandFinished.get();
    }

    public SimpleBooleanProperty isCurrentHandFinishedProperty() {
        return this.IsCurrentHandFinished;
    }

    public void setIsCurrentHandFinished() {
        this.IsCurrentHandFinished.set(this.model.IsCurrentHandOver());
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

    public List<PlayerData> getPlayerData() { return playerData; }

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

    public void RemoveDeletedPlayers()
    {
        for(PlayerData p_date:this.playerData)
        {
            if(!this.model.IsPlayerExist(p_date.getId())) {
                this.playerData.remove(p_date);
            }
        }
    }

    public void UpdatePlayersCards()
    {
        for(PlayerData p_date:this.playerData)
        {
            p_date.SetCards();
        }
    }
    public void UpdateAll(){
        this.UpdatePlayers();
        if (this.currentHand != null) this.currentHand.UpdateHand();
        this.setCurrentPlayerId();
        this.setCurrentHandNumber();
        this.setIsCurrentHandFinished();
    }

    public void UpdateAllReplayMode(){
        this.UpdatePlayers();
        for(PlayerData p_date:this.playerData)
        {
            p_date.setWinChance();
        }
        if (this.currentHand != null) this.currentHand.UpdateHand();
        this.setCurrentPlayerId();
        this.setCurrentHandNumber();
        this.setIsCurrentHandFinished();
    }
    public List<String> GetNameOfPlayers(){
        List<String> Names= new LinkedList<>();

        for (PlayerData player : this.playerData)
        {
            Names.add(player.getPlayerName());
        }
        return Names;
    }
    public List<Runnable> GetBuyFunctions()
    {
        List<Runnable> res = new LinkedList<>();
        for (PlayerData player : this.playerData)
        {
            res.add( ()-> {player.MakeABuy();});
        }
        return res;

    }

}

