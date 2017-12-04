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
    public PlayerStats(APlayer player,int num_of_games)
    {
        super(player.GetType(),player.GetPlayerState(),player.GetMoney());
        this.handsWons = player.GetNumOfWins();
        this.numOfGames =num_of_games;
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
