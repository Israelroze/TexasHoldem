package Game;

import Exceptions.*;
import Generated.GameDescriptor;
import Generated.JAXB_Generator;
import Player.*;
import ReturnType.CurrentHandState;
import ReturnType.PlayerStats;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import Move.*;

public class Game {
    GameDescriptor configuration;
    CurrentHandState state;
    APlayers players;
    boolean is_game_started=false;
    private int num_of_hands=0;

    Hand current_hand;
    //Methods
    public void LoadPlayers() throws PlayerDataMissingException { this.players=new APlayers(configuration.getPlayers());}
    private void SetPlayersChips()
    {
        for(APlayer player : this.players.GetPlayers())
        {
            player.SetMoney(configuration.getStructure().getBuy());
        }
    }

    public APlayers GetPlayers(){return this.players;}

    //Methods for menu
    @API //Option 1
    public void LoadFromFile(String file_name) throws FileNotFoundException, FileNotXMLException, WrongFileNameException, JAXBException, NullObjectException, UnexpectedObjectException, HandsCountDevideException, BigSmallMismatchException, HandsCountSmallerException, GameStartedException, PlayerDataMissingException {

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

    @API // Option 2
    public void StartGame()
    {
        this.is_game_started=true;
        //TBD - insert function pass result
    }

    // Option 3
    public List<PlayerStats> GetPlayersStats()
    {
        List<PlayerStats> stats=new LinkedList<>();
        for(APlayer player:this.players.GetPlayers())
        {
            stats.add(new PlayerStats(player,configuration.getStructure().getHandsCount()));
        }
        return stats;
    }

    //TBD

    private Move GetConsoleMove(APlayer player)
    {
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
    public Hand GetCurrentHand(){
        return this.current_hand;
    }
    public void SetCurrentHand(){
        this.current_hand=new Hand(this.players,this.configuration.getStructure());
        this.num_of_hands++;
    }

    public int GetHandsNumber()
    {
        return this.num_of_hands;
    }

    public void StartNewBidCycle() throws NoSufficientMoneyException {
        this.current_hand.StartNewBidCycle();
    }
    private void PringCurrentAvailable(APlayer current,List<MoveType> allowded_moves,int[]  range)
    {
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




    // Option 4

    public void NewHumanMove() throws PlayerFoldedException, ChipLessThanPotException, StakeNotInRangeException, MoveNotAllowdedException, NoSufficientMoneyException {
        while(!this.current_hand.IsBetsCycleFinished()) {
            APlayer current = this.current_hand.GetCurrentPlayer();
            List<MoveType> allowded_moves=this.current_hand.GetAllowdedMoves();
            int [] range=this.current_hand.GetAllowdedStakeRange();
            PringCurrentAvailable(current,allowded_moves,range);
            Move new_move=this.GetConsoleMove(current);
            this.current_hand.ImplementMove(new_move.GetMoveType(),new_move.GetValue());
        }
    }

    public void StartNewHand() throws NoSufficientMoneyException, PlayerFoldedException, ChipLessThanPotException, StakeNotInRangeException, MoveNotAllowdedException {
       this.current_hand=new Hand(this.players,this.configuration.getStructure());
        current_hand.StartNewBidCycle();

        while(!current_hand.IsBetsCycleFinished()) {

            APlayer current = current_hand.GetCurrentPlayer();
            List<MoveType> allowded_moves=current_hand.GetAllowdedMoves();
            int [] range=current_hand.GetAllowdedStakeRange();
            PringCurrentAvailable(current,allowded_moves,range);
            Move new_move=this.GetConsoleMove(current);
            current_hand.ImplementMove(new_move.GetMoveType(),new_move.GetValue());
        }
    }

}
