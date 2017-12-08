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
import java.util.*;

import Move.*;

public class Game implements InterfaceAPI {
    //members
    GameDescriptor configuration;
    CurrentHandState state;
    APlayers players;
    boolean is_game_started=false;
    private int num_of_hands=0;
    Hand current_hand;

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
    private void NewHumanMove() throws PlayerFoldedException, ChipLessThanPotException, StakeNotInRangeException, MoveNotAllowdedException, NoSufficientMoneyException, PlayerAlreadyBetException {
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
        this.current_hand=new Hand(this.players,this.configuration.getStructure());
        this.num_of_hands++;
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
    public void SetNewMove(Move move) throws StakeNotInRangeException, PlayerFoldedException, MoveNotAllowdedException, ChipLessThanPotException, NoSufficientMoneyException, PlayerAlreadyBetException {
        this.GetCurrentHand().ImplementMove(move.GetMoveType(),move.GetValue());
    }

    @Override
    public PlayerStats GetCurrentPlayerInfo()
    {
        return new PlayerStats(this.current_hand.GetCurrentPlayer(),this.GetNumberOfHands());
    }

    ///////////////////////////////TBD////////////////////////////////
    @Override
    public Move GetAutoMove() throws PlayerFoldedException, ChipLessThanPotException {
        List<MoveType> possible_moves=this.current_hand.GetAllowdedMoves();
        int[] range=this.current_hand.GetAllowdedStakeRange();
        MoveType type;
        Random rnd=new Random();
        int i;
        if(possible_moves.size()>0){
            i = rnd.nextInt(possible_moves.size() - 1);
            type =possible_moves.get(i);
        }
        else
        {
            throw new PlayerFoldedException();
            //type= MoveType.FOLD;
        }

        switch(type)
        {

            case RAISE:
                i = rnd.nextInt((range[1] - range[0]) + 1) + range[0];
                return new Move(type,i);
            case BET:
                i = rnd.nextInt((range[1] - range[0]) + 1) + range[0];
                return new Move(type,i);
            case CALL:
                return new Move(type,this.current_hand.GetHigestStake());
            case FOLD:
                return new Move(type,0);
            case CHECK:
                return new Move(type,0);
        }
        return null;
    }

    @Override
    public void SetWinner(){

    }
    @Override
    public String GetWinner(){
        return "null";
    }

    @Override
    public void MoveToNextPlayer()
    {
        this.current_hand.MoveToNextPlayer();
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
        if (community != null) {
            for (int i = 0; i < 4; i++) {
                if (community[i] != null) comCards.add(community[i]);
            }
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

       return  new CurrentHandState(PlayersHands,comCards,this.current_hand.GetPot(),this.players.GetPlayers().indexOf(this.current_hand.GetCurrentPlayer())  );

    }
}
