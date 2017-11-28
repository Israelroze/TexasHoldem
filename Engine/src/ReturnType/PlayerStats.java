package ReturnType;

import Player.PlayerState;
import Player.PlayerType;

public class PlayerStats {
    private PlayerType type;
    private PlayerState state;
    private int chips;
    private int buy;
    private int handsWons;
    private int numOfGames;

    public PlayerStats(PlayerType type, PlayerState state, int chips, int buy, int handsWons, int numOfGames)
    {
        this.type = type;
        this.state = state;
        this.chips = chips;
        this.handsWons = handsWons;
        this.numOfGames = numOfGames;
    }

    public int getBuy() {
        return buy;
    }

    public String getType() {
        switch (this.type) {
            case HUMAN:
                return "H";
            case COMPUTER:
                return "C";

        }
        return "U";
    }

    public int getChips() {
        return chips;
    }

    public int getHandsWons() {
        return handsWons;
    }

    public int getNumOfGames() {
        return numOfGames;
    }

    public String getState() {
        switch (this.state){
            case BIG: return "B";
            case NONE: return " ";
            case SMALL: return "S";
            case DEALER: return "D";
        }
        return "U";
    }
}
