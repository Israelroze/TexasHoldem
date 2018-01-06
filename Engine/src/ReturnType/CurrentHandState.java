package ReturnType;

import Card.Card;

import java.util.List;
public class CurrentHandState {

    private List<PlayerHandState> playersState;
    private List<Card> communityCard;
    private int pot;
    private int currentPlayer;

    public CurrentHandState(List<PlayerHandState> playersState, List<Card> communityCards, int pot, int currentPlayer)
    {
        this.playersState = playersState;
        this.communityCard = communityCards;
        this.pot = pot;
        this.currentPlayer= currentPlayer;
    }

    public List<PlayerHandState> getPlayersState() {
        return playersState;
    }

    public List<Card> getCommunityCard() {
        return communityCard;
    }
    public String getStringOfCommunityCard() {
        String res = "";
        for (int i=0; i< communityCard.size(); i++)
        {
            res += communityCard.get(i).toString();
            if (i != communityCard.size() -1) res += " | ";
        }
        return res;
    }

    public int getPot() {
        return pot;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
