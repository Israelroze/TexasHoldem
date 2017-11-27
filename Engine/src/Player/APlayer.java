package Player;

import Card.Card;

import java.util.HashMap;
import java.util.Map;

public abstract class APlayer implements Player{
    private int money;
    private int num_of_buys;
    private int num_of_wins;
    private int serial_number;
    private String name;
    private Card[] cards;
    private PlayerState state;

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
    public int GetSerialNumber() {
        return serial_number;
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
    public void SetName(String name) {
        this.name=name;
    }

    @Override
    public void SetPlayerState(PlayerState state) {
        this.state=state;
    }

    @Override
    public void SetSerialNumber(int SR) {
        this.serial_number=SR;
    }

    @Override
    public Map<String, String> ToMap() {
        Map<String,String> result=new HashMap<>();

        result.put("Name",this.name);
        return result;
    }
}
