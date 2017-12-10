package API;

import Exceptions.*;
import Move.Move;
import Move.MoveType;
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

    //Buy API

    public void Buy();

    public int GetMoneyInGame();

    //Bid related API's
    public boolean IsHumanPlayerFolded();
    public void StartNewBidCycle() throws NoSufficientMoneyException;

    public boolean IsCurrentBidCycleFinished();

    public boolean IsCurrentPlayerHuman();

    public boolean IsCurrentPlayerComputer();

    public List<MoveType> GetAllowdedMoves() throws PlayerFoldedException, ChipLessThanPotException;

    public void MoveToNextPlayer();

    public int[] GetAllowdedStakeRange();

    public Move GetAutoMove() throws PlayerFoldedException, ChipLessThanPotException;

    public void SetNewMove(Move move) throws StakeNotInRangeException, PlayerFoldedException, MoveNotAllowdedException, ChipLessThanPotException, NoSufficientMoneyException, PlayerAlreadyBetException;

    public void SetWinner();

    public List<String> GetWinner();

    public PlayerStats GetCurrentPlayerInfo();

    public void CheckBidStatus();
    public boolean IsCurrentPlayerFolded();

    //Statistics related API's
    public List<PlayerStats> GetPlayersInfo();

    public CurrentHandState GetCurrentHandState();

    public boolean IsAnyPlayerOutOfMoney();
    public boolean IsCurrentPlayerNoMoney();

}