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
        this.model = model;


    }

/*
    public void LoadPlayers()
    {
        playerData=new LinkedList<>();
        int current_id=model.GetFirstPlayerID();

        for(int i=0;i<model.GetTotalNumberOfPlayers();i++)
        {
            model.GetPlayerPot(current_id);
            model.GetPlayerIsBig(current_id);
            current_id=model.GetNextPlayerID(current_id);
        }
    }
*/

    public int getCurrentHandNumber()
    {
         //
        return currentHandNumber.get();
    }

    public SimpleIntegerProperty currentHandNumberProperty() {
        return currentHandNumber;
    }





}

