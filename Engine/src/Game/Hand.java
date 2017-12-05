package Game;

import Card.Card;
import Card.Deck;
import Exceptions.*;
import Generated.Structure;
import Player.*;
import Move.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Hand {

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
        small.setBetPlaceFlag(true);
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
            if(player.getStake()!=this.higest_stake)
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
            player.ClearBidStats();
        }
    }

    private void InitPlayersBetFlag(APlayer Cplayer) {
        //init players flags
        for(APlayer player:this.players.GetPlayers())
        {
            if(Cplayer.getId()!=player.getId())
            {
                player.setBetPlaceFlag(false);
            }
        }
    }

    private void IsBetCycleFinished() {
        if(this.IsAllPlayersPlacedBet()&&this.IsAllStakesEqual()) this.is_bets_finished=true;
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
            if(min==0)
            {
                min=player.GetMoney();
            }
            else
            {
                if(min>player.GetMoney())
                {
                    min=player.GetMoney();
                }
            }
        }
        return min;
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

        //init players flags
        this.InitPlayerFlags();

        //deal cards
        this.DealCards();

        //set first playing player
        this.current_player=this.GetFirstPlayer();

        //blinds
        if(this.bet_num==1)
        {
            this.SetBlinds();
        }
    }

    public int[] GetAllowdedStakeRange() {
        int low=0;
        if(this.current_player.getStake()<this.higest_stake)
        {
            low=this.higest_stake;
        }

        int high=this.higest_stake;
        int by_pot=this.pot;
        int by_poorest=this.GetPoorestChipsValue();

        if(by_pot>by_poorest){high=by_poorest;}
        else{high=by_pot;}

        return new int[]{low, high};
    }
    
    public List<MoveType> GetAllowdedMoves() throws PlayerFoldedException, ChipLessThanPotException {


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
        }
        else
        {
            if(this.current_player.getStake()<this.higest_stake)//player need to Call,Raise or Fold
            {
                allowded_moves.add(MoveType.RAISE);
                allowded_moves.add(MoveType.CALL);
                allowded_moves.add(MoveType.FOLD);
            }
        }
        return allowded_moves;
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

    public void ImplementMove(MoveType move,int stake) throws NoSufficientMoneyException, PlayerFoldedException, ChipLessThanPotException, MoveNotAllowdedException, StakeNotInRangeException {

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

        switch(move){
            case BET:
                this.current_player.DecMoney(stake);
                this.current_player.setStake(stake);
                this.current_player.setBetPlaceFlag(true);
                this.InitPlayersBetFlag(this.current_player);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                this.higest_stake=stake;
                break;
            case RAISE:
                this.current_player.DecMoney(stake);
                this.current_player.setStake(stake);
                this.current_player.setBetPlaceFlag(true);
                this.InitPlayersBetFlag(this.current_player);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                this.higest_stake=stake;
                break;
            case CALL:
                this.current_player.DecMoney(this.higest_stake);
                this.current_player.setStake(this.higest_stake);
                this.current_player.setBetPlaceFlag(true);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                break;
            case CHECK:
                this.current_player.setBetPlaceFlag(true);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                break;
            case FOLD:
                this.current_player.setBetPlaceFlag(true);
                this.current_player.setFoldedFlag(true);
                this.current_player=this.players.GetNextPlayer(this.current_player);
                break;
        }
        this.IsBetCycleFinished();
    }

    public void Flop() {
        this.community=new Card[5];
        for(int i=0;i<3;i++)
        {
            this.community[i]=this.deck.PopCard();
        }
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
}
