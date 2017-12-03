package ReturnType;

import Player.PlayerState;
import Player.PlayerType;

public class PlayerReturnType {
    private PlayerType type;
    private PlayerState state;
    private int chips;


    public PlayerReturnType(PlayerType type, PlayerState state, int chips) {
        this.type = type;
        this.state = state;
        this.chips = chips;
    }

    @Override
    public String toString() {
        return this.type.toString();
    }

    public PlayerType GetType() {
        return type;
    }

    public int getChips() {
        return chips;
    }


    public String getState() {
        switch (this.state) {
            case BIG:
                return "B";
            case NONE:
                return " ";
            case SMALL:
                return "S";
            case DEALER:
                return "D";
        }
        return "U";
    }
}
