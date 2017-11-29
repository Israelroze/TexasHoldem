package Player;

import Card.Card;

import Generated.Player;
import java.util.HashMap;
import java.util.Map;

public abstract class APlayer implements IPlayer{
    private int money;
    private int num_of_buys;
    private int num_of_wins;
    private int serial_number;
    private String name;
    private Card[] cards;
    private PlayerState state;
    private String type;
    private int id;

    public APlayer(String name, String type, int ID)
    {
        this.type=type;
        this.name=name;
        this.id=ID;
    }

    public APlayer(Generated.Player player)
    {
        this.id=player.getId();
        this.name=player.getName();
        this.type=player.getType();
    }

    @Override
    public Card[] GetCards() {
        return cards;
    }

    @Override
    public int GetMoney() {
        return money;
    }

    @Override
    public int GetNumOfBuys() {
        return num_of_buys;
    }

    @Override
    public PlayerState GetPlayerState() {
        return state;
    }

    @Override
    public String GetName() {
        return name;
    }

    @Override
    public void AddMoney(int amount) {
        this.money=this.money+amount;
    }

    @Override
    public void BuyMoney(int amount) {
        AddMoney(amount);
        num_of_buys++;
    }

    @Override
    public String GetType() {
        return this.type;
    }

    @Override
    public void DecMoney(int amount) {
        this.money=this.money-amount;
    }

    @Override
    public void SetCards(Card[] cards) {
        this.cards=cards;
    }

    @Override
    public void SetMoney(int money) {
        this.money=money;
    }

    @Override
    public void SetPlayerState(PlayerState state) {
        this.state=state;
    }

}
