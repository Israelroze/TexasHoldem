package Game;


import Card.Card;
import Card.Deck;
import Exceptions.NoSufficientMoneyException;
import Move.Moves;
import Player.APlayer;

import Generated.Structure;
import Player.APlayers;
import Move.MoveType;

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

    //money
    private int pot;

    //bets
    private int bet_num;
    private int big;
    private int small;

    //moves
    private Moves moves;

    //Private Methods
    private APlayer GetFirstPlayer()
    {
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
        this.moves.AddMove(MoveType.BET,this.small);

        //for big
        APlayer big=this.players.GetBigPlayer();
        big.DecMoney(this.big);
        big.setBetPlaceFlag(true);
        this.moves.AddMove(MoveType.BET,this.big);
    }

    private boolean IsAllPlayersPlacedBet()
    {
        for(APlayer player : this.players.GetPlayers())
        {
            if(player.getStake()!=this.higest_stake)
            {
                return false;
            }
        }
        return true;
    }

    private void IsBetCycleFinished()
    {
        if(this.IsAllPlayersPlacedBet()&&this.IsAllStakesEqual()) this.is_bets_finished=true;
    }

    private boolean IsAllStakesEqual()
    {
        for(APlayer player :this.players.GetPlayers())
        {
            if(player.isPlacedBet()!=true)
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

    private void IncPot(int amount)
    {
        this.pot=this.pot+amount;
    }


    //TBD//
    private void DealCards()
    {

    }

    //Ctors
    public Hand(APlayers players, Structure structure)
    {
        this.players=players;
        this.bet_num=0;
        this.pot=0;
        this.big=structure.getBlindes().getBig();
        this.small=structure.getBlindes().getSmall();
        this.moves=new Moves();
    }


    //Public Methods
    @API
    public void StartNewBidCycle() throws NoSufficientMoneyException {
        //increase bet cycle number
        this.bet_num++;

        //init highest stake
        this.higest_stake=0;

        //init flags
        this.is_bets_started=true;
        this.is_bets_finished=false;

        //init pot
        this.pot=0;

        //init players flags
        for(APlayer player:this.players.GetPlayers())
        {
            player.ClearBidStats();
        }

        //set first playing player
        this.current_player=this.GetFirstPlayer();

        //blinds
        if(this.bet_num==1)
        {
            this.SetBlinds();
        }
    }

    @API
    public void ImplementMove(MoveType move,int stake)
    {

    }

    @API
    public void Flop()
    {
        this.community=new Card[5];
        for(int i=0;i<3;i++)
        {
            this.community[i]=this.deck.PopCard();
        }
    }

    @API
    public void River()
    {
        this.community[3]=this.deck.PopCard();
    }

    @API
    public void Turn()
    {
        this.community[4]=this.deck.PopCard();
    }

    @API
    public APlayer GetCurrentPlayer() {
        return current_player;
    }

    @API
    public boolean IsBetsCycleFinished() {
        return is_bets_finished;
    }

    @API
    public boolean IsHandOver() {
        return is_hand_over;
    }

    @API
    public int GetPot() {
        return pot;
    }

    @API
    public int GetBetCycleNumber() {
        return bet_num;
    }

    @API
    public APlayer GetNextPlayer() {
       return this.players.GetNextPlayer(this.current_player);
    }
}
