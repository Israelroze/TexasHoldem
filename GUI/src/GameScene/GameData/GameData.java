package GameScene.GameData;

import API.InterfaceAPI;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.LinkedList;
import java.util.List;

public class GameData {


    private SimpleIntegerProperty currentHandNumber;
    private SimpleIntegerProperty maxPot;
    private SimpleIntegerProperty big;
    private SimpleIntegerProperty small;
    private InterfaceAPI model;
    private List<PlayerData> playerData;

    public GameData (InterfaceAPI model)
    {
        this.big = new SimpleIntegerProperty(model.GetBig());
        this.small = new SimpleIntegerProperty(model.GetSmall());
        this.maxPot = new SimpleIntegerProperty(model.GetPot());
        this.currentHandNumber = new SimpleIntegerProperty(model.GetCurrentHandNumber());
        this.model = model;
        this.LoadPlayers();
    }
    //Setters
    public void setCurrentHandNumber() { this.currentHandNumber.set(model.GetCurrentHandNumber()); }

    public void setMaxPot() { this.maxPot.set(model.GetPot()); }

    public void setBig() { this.big.set(model.GetBig()); }

    public void setSmall() { this.small.set(model.GetSmall());}

    //Getters
    public int getMaxPot() { return maxPot.get(); }

    public SimpleIntegerProperty maxPotProperty() { return maxPot; }

    public int getBig() { return big.get(); }

    public SimpleIntegerProperty bigProperty() { return big; }

    public int getSmall() { return small.get(); }

    public SimpleIntegerProperty smallProperty() { return small; }

    public int getCurrentHandNumber() { return currentHandNumber.get(); }

    public SimpleIntegerProperty currentHandNumberProperty() {
        return currentHandNumber;
    }



    public void LoadPlayers()
    {
        playerData=new LinkedList<>();
        int current_id=model.GetFirstPlayerID();

        for(int i=0;i<model.GetTotalNumberOfPlayers();i++)
        {
            playerData.add(new PlayerData(model,i, current_id));
            current_id=model.GetNextPlayerID(current_id);
        }
    }








}

