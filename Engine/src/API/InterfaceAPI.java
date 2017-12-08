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

    public void MoveToNextPlayer();

    public int[] GetAllowdedStakeRange();

    public Move GetAutoMove() throws PlayerFoldedException, ChipLessThanPotException;

    public void SetNewMove(Move move) throws StakeNotInRangeException, PlayerFoldedException, MoveNotAllowdedException, ChipLessThanPotException, NoSufficientMoneyException, PlayerAlreadyBetException;

    public void SetWinner();

    public String GetWinner();

    public PlayerStats GetCurrentPlayerInfo();

    //Statistics related API's
    public List<PlayerStats> GetPlayersInfo();

    public CurrentHandState GetCurrentHandState();

}