package API;

import Exceptions.*;
import Move.*;
import Player.PlayerType;
import ReturnType.PlayerStats;

import java.io.FileNotFoundException;
import java.util.List;
import Game.*;

import javax.xml.bind.JAXBException;

public class API{

    private Game game;

    //ctor
    public API(){
        this.game=new Game();
    }

    //Game related API's
    public void LoadFromXML(String filename) throws GameStartedException, UnexpectedObjectException, FileNotFoundException, BigSmallMismatchException, PlayerDataMissingException, HandsCountDevideException, WrongFileNameException, HandsCountSmallerException, JAXBException, FileNotXMLException, NullObjectException {
        game.LoadFromFile(filename);
    }

    public void StartGame(){
        game.StartGame();
    }

    //Hand related API
    public void StartNewHand(){
        game.SetCurrentHand();
    }

    public int GetCurrentHandNumber(){
        return game.GetHandsNumber();
    }

    public void Flop(){
        game.GetCurrentHand().Flop();
    }

    public void River(){
        game.GetCurrentHand().River();
    }

    public void Turn()
    {
        game.GetCurrentHand().Turn();
    }

    //Bid related API's
    public void StartNewBidCycle() throws NoSufficientMoneyException {
        game.StartNewBidCycle();
    }

    public boolean IsCurrentBidCycleFinished(){
        return game.GetCurrentHand().IsBetsCycleFinished();
    }

    public boolean IsCurrentPlayerHuman(){
        if(game.GetCurrentHand().GetCurrentPlayer().GetType()== PlayerType.HUMAN){return true;}
        return false;
    }

    public boolean IsCurrentPlayerComputer(){
        if(game.GetCurrentHand().GetCurrentPlayer().GetType()== PlayerType.COMPUTER){return true;}
        return false;
    }

    public List<MoveType> GetAllowdedMoves() throws PlayerFoldedException, ChipLessThanPotException {
        return game.GetCurrentHand().GetAllowdedMoves();
    }

    public int[] GetAllowdedStakeRange(){
        return game.GetCurrentHand().GetAllowdedStakeRange();
    }

    public Move GetAutoMove(){
        return null;
    }

    public void SetNewMove(Move move) throws StakeNotInRangeException, PlayerFoldedException, MoveNotAllowdedException, ChipLessThanPotException, NoSufficientMoneyException {
        game.GetCurrentHand().ImplementMove(move.GetMoveType(),move.GetValue());
    }

    //Statistics related API's
    public List<PlayerStats> GetPlayersInfo()
    {
        return game.GetPlayersStats();
    }







}
