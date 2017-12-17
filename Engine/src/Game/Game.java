package Game;

import Card.*;
import Exceptions.*;
import Generated.GameDescriptor;
import Generated.JAXB_Generator;
import Generated.Player;
import Player.*;
import ReturnType.*;
import API.*;


import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

import Move.*;

public class Game implements InterfaceAPI {

    final static Boolean ENABLE_LOG = false;
    //members
    private GameDescriptor configuration;
    private CurrentHandState state;
    private APlayers players;
    boolean is_game_started=false;
    private int num_of_hands=0;
    private int global_num_of_buys = 4;
    private Hand current_hand;

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

        if(ENABLE_LOG) System.out.println("Please provide the player move for:"+player.GetName());
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
            if(ENABLE_LOG) System.out.println("Please provide the value:"+player.GetName());
            value=stdin.nextInt();
        }

        return new Move(type,value);
    }
    private void PringCurrentAvailable(APlayer current,List<MoveType> allowded_moves,int[]  range) {
        boolean is_get_value=false;
       if(ENABLE_LOG) System.out.println("Allowded move for player"+current.GetName()+":");
        for(MoveType type:allowded_moves)
        {
            switch(type)
            {
                case BET:
                    if(ENABLE_LOG) System.out.println("Bet(B)");
                    is_get_value=true;
                    break;
                case CALL:
                    if(ENABLE_LOG)  System.out.println("Call(C)");
                    break;
                case CHECK:
                    if(ENABLE_LOG) System.out.println("Check(K)");
                    break;
                case FOLD:
                    if(ENABLE_LOG) System.out.println("Fold(F)");
                    break;
                case RAISE:
                    if(ENABLE_LOG) System.out.println("Raise(R)");
                    is_get_value=true;
                    break;
            }
        }

        if(ENABLE_LOG) System.out.println("Allowded range: low:"+range[0]+" high:"+range[1]);

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
    public void AddNewPlayer(String name, PlayerType type, int ID){
        this.players.GetPlayers().add(new APlayer(name,type,ID));
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
        List<APlayer> players = this.GetPlayers().GetPlayers();
        for (APlayer player :players )
        {
            player.setFoldedFlag(false);
        }
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
    public void CheckBidStatus(){
        this.current_hand.SetIsBetCycleFinished();
    }
    @Override
    public boolean IsCurrentPlayerFolded() {
        if(this.GetCurrentHand().GetCurrentPlayer().isFolded())
        {
            if(ENABLE_LOG) System.out.println("FROM GAME: current player folded!!!");
            return true;
        }
        return false;
    }
    @Override
    public boolean IsCurrentPlayerNoMoney()
    {
        if(this.GetCurrentHand().GetCurrentPlayer().GetMoney()<= 0)
        {
            if(ENABLE_LOG) System.out.println("FROM GAME: current Player has no money- such a loser!!!!");
            return true;
        }
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
        if(move==null){
            if (Game.ENABLE_LOG)System.out.println("FROM GAME: SetNewMove got null move, implementing...");
            this.GetCurrentHand().ImplementMove(null,0);
        }
        else {
            if(ENABLE_LOG) System.out.println("FROM GAME: SetNewMove got move:"+move.GetMoveType()+ "value:"+move.GetValue()+", implementing...");
            this.GetCurrentHand().ImplementMove(move.GetMoveType(), move.GetValue());
        }
    }

    @Override
    public PlayerStats GetCurrentPlayerInfo() {
        return new PlayerStats(this.current_hand.GetCurrentPlayer(),this.GetNumberOfHands() );
    }

    @Override
    public Move GetAutoMove() throws PlayerFoldedException, ChipLessThanPotException {
        if(ENABLE_LOG) System.out.println("Player Type:"+this.current_hand.GetCurrentPlayer().GetType() +" ID:"+this.current_hand.GetCurrentPlayer().getId()+"Getting  auto move...");
        List<MoveType> possible_moves=this.current_hand.GetAllowdedMoves();
        int[] range=this.current_hand.GetAllowdedStakeRange();

        if(ENABLE_LOG)  System.out.println("Player Type:"+this.current_hand.GetCurrentPlayer().GetType() +" ID:"+this.current_hand.GetCurrentPlayer().getId()+"Got the range...");

        MoveType type=null;
        Random rnd=new Random();
        int i;

        if(ENABLE_LOG) System.out.println("Player Type:"+this.current_hand.GetCurrentPlayer().GetType() +" ID:"+this.current_hand.GetCurrentPlayer().getId()+"posdsible moves number:"+possible_moves.size());
        if(possible_moves.size()==1)
        {
            type=possible_moves.get(0);
        }
        else
        {
            if(possible_moves.size()>0){
                i = rnd.nextInt(possible_moves.size() - 1);
                if(ENABLE_LOG) System.out.println("Player Type:"+this.current_hand.GetCurrentPlayer().GetType() +" ID:"+this.current_hand.GetCurrentPlayer().getId()+"random:"+i);
                type =possible_moves.get(i);
            }
        }
        if(type!=null)
        {
            switch(type)
            {
                case RAISE:
                    if(range[0]==range[1])
                    {
                        if(ENABLE_LOG) System.out.println("Player Type:"+this.current_hand.GetCurrentPlayer().GetType() +" ID:"+this.current_hand.GetCurrentPlayer().getId()+"only one:"+range[0]);
                        return new Move(type,range[0]);
                    }
                    else
                    {
                        i = rnd.nextInt((range[1] - range[0]) + 1) + range[0];
                        if(ENABLE_LOG) System.out.println("Player Type:"+this.current_hand.GetCurrentPlayer().GetType() +" ID:"+this.current_hand.GetCurrentPlayer().getId()+"random:"+i);
                        return new Move(type,i);
                    }
                case BET:
                    if(range[0]==range[1])
                    {
                        if(ENABLE_LOG) System.out.println("Player Type:"+this.current_hand.GetCurrentPlayer().GetType() +" ID:"+this.current_hand.GetCurrentPlayer().getId()+"only one:"+range[0]);
                        return new Move(type,range[0]);
                    }
                    else
                    {
                        i = rnd.nextInt((range[1] - range[0]) + 1) + range[0];
                        if(ENABLE_LOG) System.out.println("Player Type:"+this.current_hand.GetCurrentPlayer().GetType() +" ID:"+this.current_hand.GetCurrentPlayer().getId()+"random:"+i);
                        return new Move(type,i);
                    }
                case CALL:
                    return new Move(type,0);
                case FOLD:
                    return new Move(type,0);
                case CHECK:
                    return new Move(type,0);
            }
        }
        return null;
    }

    @Override
    public void MoveToNextPlayer()
    {
        this.current_hand.MoveToNextPlayer();
    }


    @Override
    public void SetWinner(){
        this.current_hand.SetWinner();
    }

    @Override
    public List<String> GetWinner(){
        return  this.current_hand.GetWinnerNames();
    }
    @Override
    public void Buy()
    {
        for(APlayer player:this.players.GetPlayers())
        {
            if(player.GetType()==PlayerType.HUMAN) {
                player.BuyMoney(this.configuration.getStructure().getBuy());
                this.global_num_of_buys++;
            }
        }
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
            for (int i = 0; i < 5; i++) {
                if (community[i] != null) comCards.add(community[i]);
            }
        }
        List<PlayerHandState> PlayersHands =new LinkedList<>();
        int humanPlayerIndex = -1;
        int c=0;
        for(APlayer player:this.players.GetPlayers())
        {
            if(player.GetType() == PlayerType.HUMAN ) {
                List <Card> HumanCards = new LinkedList<Card>();
                Card[] cards = player.GetCards();
                HumanCards.add(cards[0]);
                HumanCards.add(cards[1]);
                humanPlayerIndex = c;

                PlayersHands.add(new PlayerHandState(PlayerType.HUMAN, player.GetPlayerState(), player.GetMoney(), player.getStake(),HumanCards, player.GetName(),player.getId()));
            }
            else
            {
                PlayersHands.add(new PlayerHandState(PlayerType.COMPUTER, player.GetPlayerState(), player.GetMoney(), player.getStake(),Card.UnknownComputerCards, player.GetName(), player.getId()));
            }
            c++;
        }

       return  new CurrentHandState(PlayersHands,comCards,this.current_hand.GetPot(),humanPlayerIndex , this.configuration.getStructure().getBlindes().getBig(),this.configuration.getStructure().getBlindes().getSmall());

    }
    @Override
    public int GetMoneyInGame()
    {
        return this.global_num_of_buys * this.configuration.getStructure().getBuy();
    }
    @Override
    public boolean IsHumanPlayerFolded()
    {
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
    }
    @Override
    public boolean IsAnyPlayerOutOfMoney()
    {
    List<APlayer> players = this.players.GetPlayers();
        for (APlayer player: players)
        {
            if( player.GetMoney() <= 0)
                return true;
        }
        return false;
    }

}
