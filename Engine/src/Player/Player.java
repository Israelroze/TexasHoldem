package Player;

import Card.Card;

import java.util.Map;

//interface to hold the functions for BOT/Human players
public interface Player {
    //Money
    int GetMoney();
    int GetNumOfBuys();
    void SetMoney(int money);
    void AddMoney(int amount);
    void DecMoney(int amount);
    void BuyMoney(int amount);

    //Name
    void SetName(String name);
    String GetName();

    //Serial Number
    int GetSerialNumber();
    void SetSerialNumber(int SR);

    //Cards
    Card[] GetCards();
    void SetCards(Card[] cards);

    //Player State
    PlayerState GetPlayerState();
    void SetPlayerState(PlayerState state);

    //to Map
    Map<String,String> ToMap();
}
