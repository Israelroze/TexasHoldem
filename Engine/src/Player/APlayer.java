package Player;

import Card.Card;

import Generated.Player;
import java.util.HashMap;
import java.util.Map;

public class APlayer{
    //info
    private String type;
    private int id;
    private String name;

    // global stats
    private int money;
    private int num_of_buys;
    private int num_of_wins;

    //bid stats
    private Card[] cards;
    private PlayerState state;
    private boolean is_placed_bet;
    private boolean is_folded;
    private int stake;

    //Ctors
    public APlayer(String name, String type, int ID)
    {
        this.type=type;
        this.name=name;
        this.id=ID;
        this.ClearBidStats();
    }

    public APlayer(Player player)
    {
        this.id=player.getId();
        this.name=player.getName();
        this.type=player.getType();
        this.ClearBidStats();
    }

    //Getters Setters
    public int getId() { return id; }

    public boolean isPlacedBet() {return is_placed_bet; }

    public void setBetPlaceFlag(boolean state){this.is_placed_bet=state;}

    public void setStake(int stake){this.stake=stake;}

    public int getStake() { return stake; }

    public boolean isFolded() {
        return is_folded;
    }

    public void setFoldedFlag(boolean state){this.is_folded=state;}

    public Card[] GetCards() {
        return cards;
    }

    public int GetMoney() { return money; }

    public int GetNumOfBuys() { return num_of_buys; }
     
    public PlayerState GetPlayerState() {
        return state;
    }

    public String GetName() {
        return name;
    }

    public void AddMoney(int amount) {
        this.money=this.money+amount;
    }

    public void BuyMoney(int amount) {
        AddMoney(amount);
        num_of_buys++;
    }

    public String GetType() {
        return this.type;
    }

    public void DecMoney(int amount) {
        this.money=this.money-amount;
    }

    public void SetCards(Card[] cards) {
        this.cards=cards;
    }

    public void SetMoney(int money) {
        this.money=money;
    }

    public void SetPlayerState(PlayerState state) {
        this.state=state;
    }

    //Methods
    public void ClearBidStats()
    {
        this.stake=0;
        this.is_placed_bet=false;
        this.is_folded=false;
    }
}
