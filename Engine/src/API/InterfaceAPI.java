package API;

import Exceptions.*;
import Game.Game;
import Move.Move;
import Move.MoveType;
import Player.PlayerType;
import ReturnType.CurrentHandState;
import ReturnType.PlayerStats;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface  InterfaceAPI {

    //option 1
    public void LoadFromXML(String filename) throws GameStartedException, UnexpectedObjectException, FileNotFoundException, BigSmallMismatchException, PlayerDataMissingException, HandsCountDevideException, WrongFileNameException, HandsCountSmallerException, JAXBException, FileNotXMLException, NullObjectException;

    //option2
    public void StartGame();


    //Hand related API
    public void StartNewHand();

    public int GetNumberOfHands();

    public boolean IsCurrentHandFinished();

    public int GetCurrentHandNumber();

    public void Flop();

    public void River();

    public void Turn();


    //Bid related API's
    public void StartNewBidCycle() throws NoSufficientMoneyException;

    public boolean IsCurrentBidCycleFinished();

    public boolean IsCurrentPlayerHuman();

    public boolean IsCurrentPlayerComputer();

    public List<MoveType> GetAllowdedMoves() throws PlayerFoldedException, ChipLessThanPotException;

    public int[] GetAllowdedStakeRange();

    public Move GetAutoMove();

    public void SetNewMove(Move move) throws StakeNotInRangeException, PlayerFoldedException, MoveNotAllowdedException, ChipLessThanPotException, NoSufficientMoneyException;

    public void SetWinner();

    public String GetWinner();


    //Statistics related API's
    public List<PlayerStats> GetPlayersInfo();

    public CurrentHandState GetCurrentHandState();

<<<<<<< HEAD
    public boolean IsAnyPlayerOutOfMoney();
    public boolean IsCurrentPlayerNoMoney();


    /////API FOR TARGIL 2

    //public int GetCurrentHandNumber(); // TBD -Check
    public int GetMaxBuys();
    public int GetSmall();
    public int GetBig();
    public int GetPot();
    public int GetTotalNumberOfPlayers();
    public int GetFirstPlayerID();
    public int GetCurrentPlayerID();
    public int GetNextPlayerID(int id);
    public int GetPlayerPot(int id);
    public int GetPlayerNumOfWins(int id);
    public boolean GetPlayerIsDealer(int id);
    public boolean GetPlayerIsBig(int id);
    public boolean GetPlayerIsSmall(int id);
    public boolean GetPlayerIsHuman(int id);
    public boolean GetPlayerIsFolded(int id);
    public int GetPlayerNumOfBuy(int id);
    public String GetPlayerName(int id);
    public List<Card> GetPlayersCards(int id);
    public List<Card> GetCommunityCards();
    public boolean IsCurrentHandOver();
    public void CheckCurrentHandStatus();
    public void PlayerPerformBuy(int id);
    public void PlayerPerformQuitFromGame(int id);



        //for replaye
    public void ReverseHandToStart();
    public String GetPreviousEvent();
    public String GetNextEvent();
    public String GetPlayerWinChance(int id);
    public void SetReplayMode(boolean state);
=======
>>>>>>> parent of 003e11a... Merge pull request #10 from Israelroze/master
}