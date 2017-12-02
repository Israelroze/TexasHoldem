package Game;


import Card.Card;
import Card.Deck;
import Player.APlayer;

import Generated.Structure;
import Player.APlayers;
import Player.MoveType;
import Player.PlayerState;

import java.util.List;

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

    //Private Methods
    private APlayer getFirstPlayer()
    {
        if(bet_num==1){
            return this.players.GetBigPlayer();
        }
        else{
            return this.players.GetSmallPlayer();
        }

    }
    private void SetBlinds()
    {
        //for small
        APlayer small=this.players.GetSmallPlayer();
        small.DecMoney(this.small);
        small.setBetPlaceFlag(true);

        //for big
        APlayer big=this.players.GetBigPlayer();
        big.DecMoney(this.big);
        big.setBetPlaceFlag(true);
    }

    private boolean isAllPlayersPlacedBet()
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

    private void isBetCycleFinished()
    {
        if(this.isAllPlayersPlacedBet()&&this.isAllStakesEqual()) this.is_bets_finished=true;
    }

    private boolean isAllStakesEqual()
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

    private boolean isAllFolded(){
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

    private void incPot(int amount)
    {
        this.pot=this.pot+amount;
    }


    //TBD//
    private void dealCards()
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
    }


    //Public Methods
    @API
    public void startNewBidCycle()
    {
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
        this.current_player=getFirstPlayer();

        //blinds
        if(this.bet_num==1)
        {
            this.SetBlinds();
        }
    }

    @API
    public void implementMove(MoveType move,int stake)
    {

    }

    @API
    public void flop()
    {

    }

    @API
    public void river()
    {

    }

    @API
    public void turn()
    {

    }

    @API
    public APlayer getCurrentPlayer() {
        return current_player;
    }

    @API
    public boolean isBetsCycleFinished() {
        return is_bets_finished;
    }

    @API
    public boolean isHandOver() {
        return is_hand_over;
    }

    @API
    public int getPot() {
        return pot;
    }

    @API
    public int getBetCycleNumber() {
        return bet_num;
    }

    @API
    public APlayer getNextPlayer() {
        int index=this.players.indexOf(this.current_player);
        index++;
        if(this.players.size()<=index) { index=0; }
        return this.players.get(index);
    }
}
