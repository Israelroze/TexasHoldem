package Game;

import Card.*;
import Exceptions.*;
import Generated.GameDescriptor;
import Generated.JAXB_Generator;
import Player.*;
import ReturnType.*;
import API.*;


import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Move.*;

public class Game implements InterfaceAPI {
<<<<<<< HEAD

    final static Boolean ENABLE_LOG = true;
=======
>>>>>>> parent of 003e11a... Merge pull request #10 from Israelroze/master
    //members
    GameDescriptor configuration;
    CurrentHandState state;
    APlayers players;
    boolean is_game_started=false;
    private int num_of_hands=0;
<<<<<<< HEAD
    private int global_num_of_buys = 4;
    private Hand current_hand;
    private boolean Is_replay=false;
=======
    Hand current_hand;
>>>>>>> parent of 003e11a... Merge pull request #10 from Israelroze/master

    //Private Methods
    private void LoadPlayers() throws PlayerDataMissingException {this.players=new APlayers(configuration.getPlayers());}
    private void SetPlayersChips() {
        for(APlayer player : this.players.GetPlayers())
        {
            player.SetMoney(configuration.getStructure().getBuy());
        }
    }
    private APlayers GetPlayers(){return this.players;}
    private Hand GetCurrentHand(){
        return this.current_hand;
    }


    //for testing
    private Move GetConsoleMove(APlayer player) {
        MoveType type = null;
        int value=0;

        boolean is_get_value=false;

        System.out.println("Please provide the player move for:"+player.GetName());
        Scanner stdin=new Scanner(System.in);
        String move=stdin.nextLine();

        switch(move)
        {
            case "B":
                type=MoveType.BET;
                is_get_value=true;
                break;
            case "F":
                type=MoveType.FOLD;
                break;
            case "C":
                type=MoveType.CALL;
                break;
            case "K":
                type=MoveType.CHECK;
                break;
            case "R":
                type=MoveType.RAISE;
                is_get_value=true;
                break;
        }

        if(is_get_value)
        {
            System.out.println("Please provide the value:"+player.GetName());
            value=stdin.nextInt();
        }

        return new Move(type,value);
    }
    private void PringCurrentAvailable(APlayer current,List<MoveType> allowded_moves,int[]  range) {
        boolean is_get_value=false;
        System.out.println("Allowded move for player"+current.GetName()+":");
        for(MoveType type:allowded_moves)
        {
            switch(type)
            {
                case BET:
                    System.out.println("Bet(B)");
                    is_get_value=true;
                    break;
                case CALL:
                    System.out.println("Call(C)");
                    break;
                case CHECK:
                    System.out.println("Check(K)");
                    break;
                case FOLD:
                    System.out.println("Fold(F)");
                    break;
                case RAISE:
                    System.out.println("Raise(R)");
                    is_get_value=true;
                    break;
            }
        }

        System.out.println("Allowded range: low:"+range[0]+" high:"+range[1]);

    }
    private void NewHumanMove() throws PlayerFoldedException, ChipLessThanPotException, StakeNotInRangeException, MoveNotAllowdedException, NoSufficientMoneyException {
        while(!this.current_hand.IsBetsCycleFinished()) {
            APlayer current = this.current_hand.GetCurrentPlayer();
            List<MoveType> allowded_moves=this.current_hand.GetAllowdedMoves();
            int [] range=this.current_hand.GetAllowdedStakeRange();
            PringCurrentAvailable(current,allowded_moves,range);
            Move new_move=this.GetConsoleMove(current);
            this.current_hand.ImplementMove(new_move.GetMoveType(),new_move.GetValue());
        }
    }



    /////////////////////////////////////////////////////////////API's/////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void LoadFromXML(String file_name) throws FileNotFoundException, FileNotXMLException, WrongFileNameException, JAXBException, NullObjectException, UnexpectedObjectException, HandsCountDevideException, BigSmallMismatchException, HandsCountSmallerException, GameStartedException, PlayerDataMissingException {

        if(!this.is_game_started) {
            JAXB_Generator generator = new JAXB_Generator((file_name));
            generator.GenerateFromXML();
            generator.ValidateXMLData();
            this.configuration = generator.getContainer();
            this.LoadPlayers();
            this.SetPlayersChips();
            this.players.RandomPlayerSeats();
            this.players.ForwardStates();
            //TBD - insert function pass result
        }
        else
        {
            throw new GameStartedException();
        }
    }

    @Override
    public void StartGame() {
        this.is_game_started=true;
        //TBD - insert function pass result
    }

    //Hand Methods
    @Override
    public void StartNewHand(){
        //init new hand
        this.current_hand=new Hand(this.players,this.configuration.getStructure());

        //forward states
        this.players.ForwardStates();

        //inc hands counter
        this.num_of_hands++;
<<<<<<< HEAD

        //init placed bet flag of the players
        List<APlayer> players = this.GetPlayers().GetPlayers();
        for (APlayer player :players )
        {
            player.setFoldedFlag(false);
        }
    }

    @Override
    public boolean IsCurrentHandOver(){
        return this.current_hand.GetIsHandOver();
    }

    @Override
    public void PlayerPerformBuy(int id) {
        //Written by avishay
        for (APlayer player : players.GetPlayers()) {
            if (player.getId() == id) player.BuyMoney(this.configuration.getStructure().getBuy());
            //MUST TO BE UPDATE, Changing bilnds
            this.global_num_of_buys++;
        }

    }

    @Override
    public void CheckCurrentHandStatus(){
        this.current_hand.CheckHandStatus();
    }
    //////////////////////TBD////////////////////////
    @Override
    public void PlayerPerformQuitFromGame(int id) {

    }


    @Override
    public int GetCurrentPlayerID(){
       return this.current_hand.GetCurrentPlayer().getId();
=======
>>>>>>> parent of 003e11a... Merge pull request #10 from Israelroze/master
    }

    @Override
    public int GetNumberOfHands() {
        return this.configuration.getStructure().getHandsCount();
    }

    @Override
    public int GetCurrentHandNumber(){
        return this.num_of_hands;
    }

    @Override
    public boolean IsCurrentHandFinished(){
        return this.current_hand.IsHandOver();
    }

    @Override
    public void Flop(){
        this.current_hand.Flop();
    }

    @Override
    public void River(){
        this.current_hand.River();
    }

    @Override
    public void Turn(){
        this.current_hand.Turn();
    }

    //Bid Cycle Methods
    @Override
    public void StartNewBidCycle() throws NoSufficientMoneyException {
        this.current_hand.StartNewBidCycle();
    }

    @Override
    public boolean IsCurrentBidCycleFinished(){
        return this.GetCurrentHand().IsBetsCycleFinished();
    }

    @Override
    public boolean IsCurrentPlayerHuman(){
        if(this.GetCurrentHand().GetCurrentPlayer().GetType()== PlayerType.HUMAN){return true;}
        return false;
    }

    @Override
    public boolean IsCurrentPlayerComputer(){
        if(this.GetCurrentHand().GetCurrentPlayer().GetType()== PlayerType.COMPUTER){return true;}
        return false;
    }

    @Override
    public List<MoveType> GetAllowdedMoves() throws PlayerFoldedException, ChipLessThanPotException {
        return this.GetCurrentHand().GetAllowdedMoves();
    }

    @Override
    public int[] GetAllowdedStakeRange(){
        return this.GetCurrentHand().GetAllowdedStakeRange();
    }

    @Override
    public void SetNewMove(Move move) throws StakeNotInRangeException, PlayerFoldedException, MoveNotAllowdedException, ChipLessThanPotException, NoSufficientMoneyException{
        this.GetCurrentHand().ImplementMove(move.GetMoveType(),move.GetValue());
    }


    ///////////////////////////////TBD////////////////////////////////
    @Override
    public Move GetAutoMove(){
        return null;
    }

    @Override
    public void SetWinner(){

    }
    @Override
<<<<<<< HEAD
    public void Buy() {
        for(APlayer player:this.players.GetPlayers())
        {
            if(player.GetType()==PlayerType.HUMAN) {
                player.BuyMoney(this.configuration.getStructure().getBuy());
                this.global_num_of_buys++;
            }
        }
    }

    @Override
    public boolean IsHumanPlayerFolded() {
        for(APlayer player:this.players.GetPlayers())
        {
            if(player.GetType()==PlayerType.HUMAN)
            {
                if(player.isFolded())
                {
                    return true;
                }
            }
        }
        return false;
=======
    public String GetWinner(){
        return "null";
>>>>>>> parent of 003e11a... Merge pull request #10 from Israelroze/master
    }

    ///////////////////////////////////////////////////////////////

    //Stats Methods
    @Override
    public List<PlayerStats>  GetPlayersInfo() {
        List<PlayerStats> stats=new LinkedList<>();
        for(APlayer player:this.players.GetPlayers())
        {
            stats.add(new PlayerStats(player,configuration.getStructure().getHandsCount()));
        }
        return stats;
    }
    @Override
    public CurrentHandState GetCurrentHandState(){

        List <Card> comCards = new LinkedList<Card>();
        Card[] community=this.current_hand.GetCommunity();
        for(int i=0;i<4;i++)
        {
            if (community[i] != null) comCards.add(community[i]);
        }

        List<PlayerHandState> PlayersHands =new LinkedList<>();
        for(APlayer player:this.players.GetPlayers())
        {
            if(player.GetType() == PlayerType.HUMAN ) {
                List <Card> HumanCards = new LinkedList<Card>();
                Card[] cards = player.GetCards();
                HumanCards.add(cards[0]);
                HumanCards.add(cards[1]);

                PlayersHands.add(new PlayerHandState(PlayerType.HUMAN, player.GetPlayerState(), player.GetMoney(), player.getStake(),HumanCards ));
            }
            else
            {
                PlayersHands.add(new PlayerHandState(PlayerType.COMPUTER, player.GetPlayerState(), player.GetMoney(), player.getStake(),Card.UnknownComputerCards));
            }
        }

       return  new CurrentHandState(PlayersHands,comCards,this.current_hand.GetPot(),this.players.GetPlayers().indexOf(this.current_hand.GetCurrentPlayer()));

    }
<<<<<<< HEAD

    ////////////////////////////////////////////////////////////////
    ///////////// Replay////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    public void StartReplay(){
        this.Is_replay=true;

    }
    @Override
    public void ReverseHandToStart(){
        this.current_hand.RevertToStart();
    }

    @Override
    public String GetPreviousEvent(){
        return this.current_hand.RevertEvent();
    }

    @Override
    public String GetNextEvent(){
        return this.current_hand.PerformEvent();
    }

    @Override
    public String GetPlayerWinChance(int id){
        return this.players.GetPlayer(id).GetWinChance();
    }

    private void InitPlayersWinChance(){
        for(APlayer player:this.players.GetPlayers())
        {
            player.SetWinChance("0%");
        }
    }

    @Override
    public void SetReplayMode(boolean state){
        this.current_hand.SetReplayMode(state);
    }

=======
>>>>>>> parent of 003e11a... Merge pull request #10 from Israelroze/master
}
