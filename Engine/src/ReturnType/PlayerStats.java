package ReturnType;

import Player.APlayer;
import Player.PlayerState;
import Player.PlayerType;

public class PlayerStats extends PlayerReturnType {
    private int buy;
    private int handsWons;
    private int numOfGames;
    private int id;
    private PlayerType type;

    public PlayerStats(PlayerType type, PlayerState state, int chips, int buy, int handsWons, int numOfGames)
    {
        super(type,state,chips);
        this.handsWons = handsWons;
        this.numOfGames = numOfGames;
        this.buy = buy;
        this.id=0;
        this.type=PlayerType.COMPUTER;
    }
    public PlayerStats(APlayer player,int num_of_games)
    {
        super(player.GetType(),player.GetPlayerState(),player.GetMoney());
        this.handsWons = player.GetNumOfWins();
        this.numOfGames =num_of_games;
        this.buy = player.GetNumOfBuys();
        this.id=player.getId();
        this.type=player.GetType();
    }

    public PlayerType GetType()
    {
     return this.type;
    }

    public int GetID()
    {
        return this.id;
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
