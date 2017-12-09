package Game;

import Card.Card;
import Card.Deck;
import Exceptions.*;
import Generated.Structure;
import Player.*;
import Move.*;
import com.rundef.poker.EquityCalculator;
import com.rundef.poker.HandEquity;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Hand {

    //winners
    List<Integer> winners;
    //players data
    private APlayers players;
    private APlayer current_player;
    //cards
    private Card[] community;
    private Deck deck;
    //flags
    private boolean is_bets_started;
    private boolean is_bets_finished;
    private boolean is_hand_over;
    //bet stats
    private int higest_stake;
    private int poorest_player_chips;
    //money
    private int pot;
    //bets
    private int bet_num;
    private int big;
    private int small;

    //ctor
    public Hand(APlayers players, Structure structure) {
        this.players=players;
        this.bet_num=0;
        this.pot=0;
        this.big=structure.getBlindes().getBig();
        this.small=structure.getBlindes().getSmall();
        this.deck=new Deck();
        this.winners=new LinkedList<>();
    }

    //Private Methods
    private APlayer GetFirstPlayer() {
        if(bet_num==1){
            return this.players.GetBigPlayer();
        }
        else{
            return this.players.GetSmallPlayer();
        }
    }

    private void SetBlinds() throws NoSufficientMoneyException {
        //for small
        APlayer small=this.players.GetSmallPlayer();
        small.DecMoney(this.small);
        //small.setBetPlaceFlag(true);
        small.setStake(this.small);
        this.higest_stake=this.small;

        //for big
        APlayer big=this.players.GetBigPlayer();
        big.DecMoney(this.big);
        big.setBetPlaceFlag(true);
        big.setStake(this.big);
        this.higest_stake=this.big;

        //set pot
        this.pot=this.small+this.big;

        this.current_player=this.players.GetNextPlayer(big);
    }

    private void DealCards() {
        for(APlayer player:this.players.GetPlayers() )
        {
            player.SetCards(new Card[]{this.deck.PopCard(), this.deck.PopCard()});
        }
    }

    private boolean IsAllStakesEqual() {
        for(APlayer player :this.players.GetPlayers())
        {
            if(player.isPlacedBet()!=true)
            {
                return false;
            }
        }
        return true;
    }

    private boolean IsAllPlayersPlacedBet() {
        for(APlayer player : this.players.GetPlayers())
        {
            if(!player.isPlacedBet())
            {
                return false;
            }
        }
        return true;
    }

    private boolean IsAllFolded(){
        int count=0;
        for(APlayer player :this.players.GetPlayers())
        {
            if(player.isFolded()==false)
            {
                count++;
            }
        }
        if(count<=1) return true;
        return false;
    }

    private void InitPlayerFlags() {

        //init players flags
        for(APlayer player:this.players.GetPlayers())
        {
            if(!player.isFolded()) {
                player.ClearBidStats();
            }
        }
    }

    private void InitPlayersBetFlag(APlayer Cplayer) {
        //init players flags
        for(APlayer player:this.players.GetPlayers())
        {
            if(Cplayer.getId()!=player.getId() && !player.isFolded())
            {
                player.setBetPlaceFlag(false);
            }
        }
    }

    private void IncPot(int amount)
    {
        this.pot=this.pot+amount;
    }



    //Public Methods
    public int GetPoorestChipsValue() {
        int min=0;
        for(APlayer player : this.players.GetPlayers())
        {
            if(this.higest_stake==0)
            {
                if (min == 0) {
                    min = player.GetMoney();
                } else {
                    if (min > player.GetMoney()) {
                        if (player.getStake() < this.higest_stake) {
                            min = player.GetMoney();
                        }
                    }
                }
            }
            else {
                if (player.getStake() != this.higest_stake) {
                    if (min == 0) {
                        min = player.GetMoney();
                    } else {
                        if (min > player.GetMoney()) {
                            if (player.getStake() < this.higest_stake) {
                                min = player.GetMoney();
                            }
                        }
                    }
                }
            }
        }
        return min;
    }

    public void SetIsBetCycleFinished() {
        if(this.IsAllPlayersPlacedBet()) this.is_bets_finished=true;
    }

    public Card[] GetCommunity() {
        return this.community;
    }

    public void StartNewBidCycle() throws NoSufficientMoneyException {
        //increase bet cycle number
        this.bet_num++;

        //init highest stake
        this.higest_stake=0;

        //init flags
        this.is_bets_started=true;
        this.is_bets_finished=false;

        for(APlayer player: this.players.GetPlayers())
        {
            if(player.GetType()==PlayerType.HUMAN)
            {
                if(player.isFolded())
                {
                    this.is_bets_finished=true;
                }
            }
        }

        //init players flags
        this.InitPlayerFlags();

        //deal cards
        this.DealCards();

        //forward states
        this.players.ForwardStates();

        //set first playing player
        this.current_player=this.GetFirstPlayer();

        //blinds
        if(this.bet_num==1)
        {
            this.SetBlinds();
        }
    }


    public int[] GetAllowdedStakeRange() {
        if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"getting allowded stake range");

        int low=0;
        if(this.current_player.getStake()<this.higest_stake)
        {
            low=this.higest_stake;
        }

        if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"higest stake:"+this.higest_stake);
        int high=this.higest_stake;
        int by_pot=this.pot;
        if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"pot:"+this.pot);

        int by_poorest=this.GetPoorestChipsValue();
        if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"poorest chips:"+by_poorest);

        if(by_poorest<=this.higest_stake)
        {
            low=0;
            high=by_poorest;
        }
        else {
            if (by_pot > by_poorest) {
                high = by_poorest;
            } else {
                high = by_pot;
            }
        }
        if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"the range is: low:"+low+" high:"+high+"....");
        return new int[]{low, high};
    }
    
    public List<MoveType> GetAllowdedMoves() throws PlayerFoldedException, ChipLessThanPotException {

        if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"Getting  allowded moves...");
        List<MoveType> allowded_moves=new LinkedList<>();

        if(this.current_player.GetIsFoldedFlag())
        {
            throw new PlayerFoldedException();
        }
        if(this.current_player.GetMoney()<this.higest_stake)
        {
            throw new ChipLessThanPotException(this.current_player.GetMoney());
        }
        if(this.higest_stake==0) // no bet placed, the player can Check,Bet,Fold
        {
            allowded_moves.add(MoveType.BET);
            allowded_moves.add(MoveType.CHECK);
            allowded_moves.add(MoveType.FOLD);
            //moves.AddMove(new Move(MoveType.CHECK,0));
            //moves.AddMove(new Move(MoveType.FOLD,0));
            if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"allowded moves: bet,check,fold");
        }
        else
        {
            if(this.current_player.getStake()<this.higest_stake)//player need to Call,Raise or Fold
            {
                if(Game.ENABLE_LOG) System.out.println("FROM HAND:current player stake less than highest stake");
                allowded_moves.add(MoveType.RAISE);
                allowded_moves.add(MoveType.CALL);
                allowded_moves.add(MoveType.FOLD);
                if(Game.ENABLE_LOG)  System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"allowded moves: raise,call,fold");
            }
            else
            {
                if(this.current_player.getStake()==this.higest_stake)
                {
                    if(Game.ENABLE_LOG)System.out.println("FROM HAND:current player stake equal than highest stake");
                    allowded_moves.add(MoveType.RAISE);
                    allowded_moves.add(MoveType.CHECK);
                    allowded_moves.add(MoveType.FOLD);
                    if(Game.ENABLE_LOG)System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"allowded moves: raise,check");
                }
                else //current player stake bigger than highest stake
                {
                    if(Game.ENABLE_LOG)System.out.println("FROM HAND:current player stake bigger than highest stake");
                }
            }
        }
        return allowded_moves;
    }

    public int GetHigestStake() {
        return this.higest_stake;
    }

    public boolean IsMoveAllowded(MoveType mtype) throws PlayerFoldedException, ChipLessThanPotException {
        List<MoveType> allowded_moves=this.GetAllowdedMoves();
        for(MoveType move:allowded_moves)
        {
            if(mtype==move)
            {
                return true;
            }
        }
        return false;
    }

    public boolean IsStakeInRange(int stake) {
        int[] range=this.GetAllowdedStakeRange();

        if(stake>=range[0] && stake<=range[1])
        {
            return true;
        }
        return false;
    }

    public void ImplementMove(MoveType move,int stake) throws NoSufficientMoneyException, PlayerFoldedException, ChipLessThanPotException, MoveNotAllowdedException, StakeNotInRangeException, PlayerAlreadyBetException {

        if(move==null)
        {
            if(Game.ENABLE_LOG) System.out.println("FROM GAME: current player:"+this.current_player.getId()+" cant play any more");
            this.current_player.setBetPlaceFlag(true);
        }
//        if( move == MoveType.FOLD)
//        {
//            // ADD BY AVISHAY
//            if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"Implementing move... type:fold stake:"+stake);
//            this.current_player.setBetPlaceFlag(true);
//            this.current_player.setFoldedFlag(true);
//
//        }

        if(this.current_player.isPlacedBet())
        {
            throw new PlayerAlreadyBetException();
        }

        if(!this.IsMoveAllowded(move))
        {
            throw new MoveNotAllowdedException();
        }

        if(move==MoveType.BET || move==MoveType.RAISE){
            if(!this.IsStakeInRange(stake))
            {
                throw new StakeNotInRangeException();
            }
        }

        int delta;
        switch(move){
            case BET:
                if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"Implementing move... type:bet stake:"+stake);
                this.current_player.DecMoney(stake);
                this.current_player.setStake(stake);
                this.current_player.setBetPlaceFlag(true);
                this.IncPot(stake);
                this.InitPlayersBetFlag(this.current_player);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                this.higest_stake=stake;
                break;
            case RAISE:
                if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"Implementing move... type:raise stake:"+stake);
                delta=stake-this.current_player.getStake();
                this.current_player.DecMoney(delta);
                this.current_player.setStake(stake);
                this.current_player.setBetPlaceFlag(true);
                this.IncPot(delta);
                this.InitPlayersBetFlag(this.current_player);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                this.higest_stake=stake;
                break;
            case CALL:
                if(Game.ENABLE_LOG)System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"Implementing move... type:call stake:"+stake);
                delta=this.higest_stake-this.current_player.getStake();
                this.current_player.DecMoney(delta);
                this.current_player.setStake(this.higest_stake);
                this.current_player.setBetPlaceFlag(true);
                this.IncPot(delta);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                break;
            case CHECK:
                if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"Implementing move... type:check stake:"+stake);
                this.current_player.setBetPlaceFlag(true);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                break;
            case FOLD:
                if(Game.ENABLE_LOG) System.out.println("Player Type:"+this.current_player.GetType() +" ID:"+this.current_player.getId()+"Implementing move... type:fold stake:"+stake);
                this.current_player.setBetPlaceFlag(true);
                this.current_player.setFoldedFlag(true);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                break;
        }
        this.SetIsBetCycleFinished();
    }

    public void Flop() {
        this.community=new Card[5];
        for(int i=0;i<3;i++)
        {
            this.community[i]=this.deck.PopCard();
        }
    }

    public void MoveToNextPlayer()
    {
        this.current_player=this.players.GetNextPlayer(this.current_player);
    }

    public void River()
    {
        this.community[3]=this.deck.PopCard();
    }

    public void Turn()
    {
        this.community[4]=this.deck.PopCard();
    }

    public APlayer GetCurrentPlayer() {
        return this.current_player;
    }

    public boolean IsBetsCycleFinished() {
        return this.is_bets_finished;
    }

    public boolean IsHandOver() {
        return this.is_hand_over;
    }

    public int GetPot() {
        return this.pot;
    }

    public int GetBetCycleNumber() {
        return this.bet_num;
    }

    public APlayer GetNextPlayer() {
       return this.players.GetNextPlayer(this.current_player);
    }

    public void SetWinner() {
        EquityCalculator calculator = new EquityCalculator();

        //boards cards to string
        String board="";
        for(Card card: this.community)
        {
            String type;
            String number;
            type=card.GetSuit().toString().toLowerCase();
            number=card.GetNumber().toString();
            if(number.equals("10"))
            {
                number="T";
            }
            if(number.equals("1"))
            {
                number="A";
            }
            number=number.toLowerCase();
            board+=number+type;
        }

        if(Game.ENABLE_LOG) System.out.println("FROM HAND: building Winner calculation, board string:"+board);
        try {
            calculator.setBoardFromString(board);
        } catch (Exception e) {
            e.printStackTrace();
        }

        int num_of_hands=0;
        //player cards to string
        for(APlayer player:this.players.GetPlayers())
        {
            if(!player.isFolded()) {
                String str_hand = "";
                for (Card card : player.GetCards()) {
                    String type;
                    String number;
                    type = card.GetSuit().toString().toLowerCase();
                    number = card.GetNumber().toString();
                    if (number.equals("10")) {
                        number = "T";
                    }
                    if (number.equals("1")) {
                        number = "A";
                    }
                    number = number.toLowerCase();
                    str_hand += number + type;
                }
                if(Game.ENABLE_LOG) System.out.println("FROM HAND: Player ID:"+player.getId()+ "building Winner calculation, Hand string:" + str_hand);
                try {
                    com.rundef.poker.Hand hand = com.rundef.poker.Hand.fromString(str_hand);
                    calculator.addHand(hand);
                    num_of_hands++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //calculate the winner
        try {
            calculator.calculate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < num_of_hands; i++) {
            HandEquity equity=calculator.getHandEquity(i);
            if(equity.getEquity()!=0)//we have a winner
            {
                this.winners.add(i);
            }
            if(Game.ENABLE_LOG) System.out.println("Player ID"+this.players.GetPlayers().get(i).getId()+"Hand #" + (i+1) + ": Rank: " + calculator.getHandRanking(i) + " Equity: " + calculator.getHandEquity(i));
        }

        //set the number of wins for the players
        this.SetWinnersCounter();

        //devide the pot between the winners
        this.PassPot();
    }

    private void SetWinnersCounter()
    {
        for(Integer index:this.winners)
        {
            this.players.GetPlayers().get(index).IncWinner();
        }
    }

    private void PassPot()
    {
        if(this.winners.size()<1)
        {
            if(Game.ENABLE_LOG) System.out.println("Basa");
        }
        else {
            if (this.winners.size() > 1)//we have more than one winner
            {
                int share = this.pot / this.winners.size();
                //int seerit=this.pot%share;
                for (Integer index : this.winners) {
                    this.players.GetPlayers().get(index).AddMoney(share);
                }
            } else {//only one
                this.players.GetPlayers().get(this.winners.get(0)).AddMoney(this.pot);
            }
        }
    }

    public List<String> GetWinnerNames() {
        List<String> win_names=new LinkedList<>();
        for (Integer index : this.winners) {
            win_names.add(this.players.GetPlayers().get(index).GetName());
        }
        return win_names;
    }
}
