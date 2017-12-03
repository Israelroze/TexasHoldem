package ReturnType;

import Player.APlayer;
import Player.PlayerState;
import Player.PlayerType;

public class PlayerStats extends PlayerReturnType {
    private int buy;
    private int handsWons;
    private int numOfGames;

    public PlayerStats(PlayerType type, PlayerState state, int chips, int buy, int handsWons, int numOfGames)
    {
        super(type,state,chips);
        this.handsWons = handsWons;
        this.numOfGames = numOfGames;
        this.buy = buy;
    }
    public PlayerStats(APlayer player,)
    {
        super(player.GetType(),player.GetPlayerState(),player.GetMoney());
        this.handsWons = player.GetNumOfWins();
        this.numOfGames =0;
        this.buy = player.GetNumOfBuys();
    }

    public int getBuy() {
        return buy;
    }


    public int getHandsWons() {
        return handsWons;
    }

    public int getNumOfGames() {
        return numOfGames;
    }



}
