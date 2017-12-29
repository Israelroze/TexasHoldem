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
    private void ValidateXML(GameDescriptor container) throws NullObjectException, BigSmallMismatchException, HandsCountSmallerException, HandsCountDevideException, MinusZeroValueException, BigBiggerThanBuyException {
        int big;
        int small;
        int buy;
        int num_of_players;
        int num_of_hands;
        try {
            container.getStructure();
            num_of_hands=container.getStructure().getHandsCount();
        }
        catch (NullPointerException e){
            throw  new NullObjectException("Structure");
        }
        try {
            container.getStructure().getBlindes();
        }
        catch (NullPointerException e){
            throw  new NullObjectException("Blindes");
        }

        try {
            container.getPlayers();
        }
        catch (NullPointerException e){
            this.Init4Players();
            num_of_players=4;
        }
        try {
            container.getPlayers().getPlayer();
            num_of_players=container.getPlayers().getPlayer().size();
        }
        catch (NullPointerException e) {
            this.Init4Players();
            num_of_players=4;
        }

        big=container.getStructure().getBlindes().getBig();
        small=container.getStructure().getBlindes().getSmall();
        buy=container.getStructure().getBuy();


        if(big<=0 || small<=0)
        {
            throw new MinusZeroValueException();
        }

        if(big>buy)
        {
            throw new BigBiggerThanBuyException();
        }

        if(big<=small){
            throw new BigSmallMismatchException();
        }

        if(num_of_hands<num_of_players){
            throw new HandsCountSmallerException();
        }
        else
        {
            if(num_of_hands%num_of_players!=0){
                throw new HandsCountDevideException();
            }
        }
    }
    private void Init4Players(){
        this.players=new APlayers();
        this.players.GetPlayers().add(new APlayer("Bluffer",PlayerType.COMPUTER,12));
        this.players.GetPlayers().add(new APlayer("Cheater",PlayerType.COMPUTER,22));
        this.players.GetPlayers().add(new APlayer("Bunker",PlayerType.COMPUTER,33));
        this.players.GetPlayers().add(new APlayer("Camper",PlayerType.HUMAN,65));
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

    //xml file apis
    @Override
    public void LoadFromXML(String file_name) throws FileNotFoundException, FileNotXMLException, WrongFileNameException, JAXBException, UnexpectedObjectException, HandsCountDevideException, BigSmallMismatchException, HandsCountSmallerException, GameStartedException, PlayerDataMissingException, MinusZeroValueException, BigBiggerThanBuyException {

        if(!this.is_game_started) {
            JAXB_Generator generator = new JAXB_Generator((file_name));
            try {
                generator.GenerateFromXML();
                //generator.ValidateXMLData();
                this.ValidateXML(generator.getContainer());
                this.configuration=generator.getContainer();
            }
            catch (NullObjectException e){

            }

            //this.configuration = generator.getContainer();
            if(this.players==null && this.configuration.getPlayers()!=null)
            {
                this.LoadPlayers();
            }
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

        //TBD devide the validation before, xml loading and validation after to apis

    //game apis
    @Override
    public void StartGame() {
        this.is_game_started=true;
        //TBD - insert function pass result
    }

    @Override
    public int GetMoneyInGame()
    {
        return this.global_num_of_buys * this.configuration.getStructure().getBuy();
    }

    @Override
    public int GetMaxBuys(){
        return GetMoneyInGame();
    }

    @Override
    public int GetSmall(){
        return this.configuration.getStructure().getBlindes().getSmall();
    }

    @Override
    public int GetBig(){
        return this.configuration.getStructure().getBlindes().getBig();
    }
    
    @Override
    public int GetPot(){
        return this.current_hand.GetPot();
    }

    //player apis
    @Override
    public void AddNewPlayer(String name, PlayerType type, int ID){
        this.players.GetPlayers().add(new APlayer(name,type,ID));
    }

    @Override
    public int GetTotalNumberOfPlayers(){
        return this.players.GetSize();
    }

    @Override
    public int GetFirstPlayerID(){
        return this.players.GetFirstPlayerID();
    }

    @Override
    public int GetNextPlayerID(int id){
        return this.players.GetNextPlayer(id).getId();
    }

    @Override
    public int GetPlayerPot(int id){
        return this.players.GetPlayer(id).GetMoney();
    }

    @Override
    public int GetPlayerNumOfWins(int id){
        return this.players.GetPlayer(id).GetNumOfWins();
    }

    @Override
    public boolean GetPlayerIsDealer(int id){
        if(this.players.GetPlayer(id).GetPlayerState()==PlayerState.DEALER) return true;
        return false;
    }

    @Override
    public boolean GetPlayerIsBig(int id){
        if(this.players.GetPlayer(id).GetPlayerState()==PlayerState.BIG) return true;
        return false;
    }

    @Override
    public boolean GetPlayerIsSmall(int id){
        if(this.players.GetPlayer(id).GetPlayerState()==PlayerState.SMALL) return true;
        return false;
    }

    @Override
    public boolean GetPlayerIsHuman(int id){
        if(this.players.GetPlayer(id).GetType()==PlayerType.HUMAN) return true;
        return false;
    }

    @Override
    public int GetPlayerNumOfBuy(int id){
        return this.players.GetPlayer(id).GetNumOfBuys();
    }

    @Override
    public String GetPlayerName(int id){
        return this.players.GetPlayer(id).GetName();
    }

    @Override
    public List<Card> GetPlayersCards(int id){
        List<Card> cards=new LinkedList<Card>();
        Card[] arr=this.players.GetPlayer(id).GetCards();
        cards.add(arr[0]);
        cards.add(arr[1]);
        return cards;
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
    public boolean IsCurrentPlayerHuman(){
        if(this.GetCurrentHand().GetCurrentPlayer().GetType()== PlayerType.HUMAN){return true;}
        return false;
    }

    @Override
    public boolean IsCurrentBidCycleFinished(){
        return this.GetCurrentHand().IsBetsCycleFinished();
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
    public boolean IsCurrentPlayerNoMoney() {
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
    public boolean IsCurrentPlayerComputer(){
        if(this.GetCurrentHand().GetCurrentPlayer().GetType()== PlayerType.COMPUTER){return true;}
        return false;
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
    }

    @Override
    public boolean IsAnyPlayerOutOfMoney() {
        List<APlayer> players = this.players.GetPlayers();
        for (APlayer player: players)
        {
            if( player.GetMoney() <= 0)
                return true;
        }
        return false;
    }
    ///////////////////////////////////////////////////////////////
    //Stats apis
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

}
