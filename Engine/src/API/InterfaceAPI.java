package API;

import ReturnType.PlayerStats;

import java.util.List;

public interface  InterfaceAPI{
    /*
    As No 2 requirement.
    PlayerStats includes:
    Type - computer or human
    State -Dealer, Big or Small
    Chips
    Buys
    Number of wins
    number of Games
     */
    public List<PlayerStats> GetPlayersState();

    /*
    As No 3 requirement.
    PlayerStats includes:
    Type - computer or human
    State -Dealer, Big or Small
    Chips

    Number of wins
    number of Games
     */
    public List<PlayerStats> GetHandState();

    public Boolean IsAnyErrorExist

}
