import Player.PlayerState;
import Player.PlayerType;
import ReturnType.CurrentHandState;

import ReturnType.PlayerHandState;
import ReturnType.PlayerStats;
import java.util.LinkedList;
import java.util.List;

import Card.Card;
import Card.CardSuit;
import Card.CardNumber;

public class ConsoleUI{
    public static void PrintGameStat(List<PlayerStats> playerStats){
        System.out.format("*******************        *******************\n");
        System.out.format("* Type: %1s         *        * Type: %1s         *\n",playerStats.get(0).GetType(),playerStats.get(1).GetType());
        System.out.format("* State %1s         *        * State %1s         *\n",playerStats.get(0).getState(),playerStats.get(1).getState());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",playerStats.get(0).getChips(),playerStats.get(1).getChips());
        System.out.format("* Buys: %-8d  *        * Buys: %-8d  *\n",playerStats.get(0).getBuy(),playerStats.get(1).getBuy());
        System.out.format("* Hands Won:%2d/%-2d *        * Hands Won:%2d/%-2d *\n",playerStats.get(0).getHandsWons(),playerStats.get(0).getNumOfGames(),playerStats.get(1).getHandsWons(),playerStats.get(1).getNumOfGames());
        System.out.format("*******************        *******************\n");
        System.out.format("\n");
        System.out.format("\n");
        System.out.format("\n");
        System.out.format("\n");
        System.out.format("*******************        *******************\n");
        System.out.format("* Type: %1s         *        * Type: %1s         *\n",playerStats.get(2).GetType(),playerStats.get(3).GetType());
        System.out.format("* State %1s         *        * State %1s         *\n",playerStats.get(2).getState(),playerStats.get(3).getState());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",playerStats.get(2).getChips(),playerStats.get(1).getChips());
        System.out.format("* Buys: %-8d  *        * Buys: %-8d  *\n",playerStats.get(2).getBuy(),playerStats.get(3).getBuy());
        System.out.format("* Hands Won:%2d/%-2d *        * Hands Won:%2d/%-2d *\n",playerStats.get(2).getHandsWons(),playerStats.get(2).getNumOfGames(),playerStats.get(3).getHandsWons(),playerStats.get(3).getNumOfGames());
        System.out.format("*******************        *******************\n");

    }
    static public void PrintGameStatTest(){
        PlayerStats p1 = new PlayerStats(PlayerType.HUMAN, PlayerState.BIG ,100,20,4,10);
        PlayerStats p2 = new PlayerStats(PlayerType.HUMAN, PlayerState.DEALER ,100,20,4,20);
        PlayerStats p3 = new PlayerStats(PlayerType.COMPUTER, PlayerState.SMALL ,1121230,231230,20,11);
        PlayerStats p4 = new PlayerStats(PlayerType.HUMAN, PlayerState.NONE ,100,20,4,55);
        List<PlayerStats> a =new LinkedList<>();
        a.add(p1);
        a.add(p2);
        a.add(p3);
        a.add(p4);


    }


    static public void PrintGameHandTest(){
        List <Card> HumanCards = new LinkedList<Card>();
        HumanCards.add(new Card(CardNumber.ACE,CardSuit.Diamonds));
        HumanCards.add(new Card(CardNumber.TEN,CardSuit.Spades));

        List <Card> comCards = new LinkedList<Card>();
        comCards.add(new Card(CardNumber.EIGHT,CardSuit.Clubs));
        comCards.add(new Card(CardNumber.FIVE,CardSuit.Hearts));
        comCards.add(new Card(CardNumber.KING,CardSuit.Spades));
        comCards.add(new Card(CardNumber.KING,CardSuit.Hearts));



        PlayerHandState p1 = new PlayerHandState(PlayerType.COMPUTER, PlayerState.DEALER ,100,20,Card.UnknownComputerCards);
        PlayerHandState p2 = new PlayerHandState(PlayerType.HUMAN, PlayerState.BIG ,100,20,HumanCards);
        PlayerHandState p3 = new PlayerHandState(PlayerType.COMPUTER, PlayerState.SMALL ,1121230,231230,Card.UnknownComputerCards);
        PlayerHandState p4 = new PlayerHandState(PlayerType.COMPUTER, PlayerState.NONE ,100,20,Card.UnknownComputerCards);
        List<PlayerHandState> a =new LinkedList<>();
        a.add(p1);
        a.add(p2);
        a.add(p3);
        a.add(p4);

        CurrentHandState cur = new CurrentHandState(a,comCards,1000,1);

        PrintGame(cur);

    }

    public static void PrintGame(CurrentHandState curHandState){


//        System.out.format("%s",curHandState.getPlayersState().get(0).getCard().toString());
//        System.out.format("%s",curHandState.getPlayersState().get(0).getCard().toString());
//        System.out.format("%s",curHandState.getPlayersState().get(1).IsHuman() ?"": "Cards: ");
//        System.out.format("%s",curHandState.getPlayersState().get(1).getCard().toString());


        System.out.format("                 %s                        %s\n",curHandState.getCurrentPlayer() == 1 ? "***": "   ",curHandState.getCurrentPlayer() == 2 ? "***": "   ");
        System.out.format("*****************%s       *****************%s\n",curHandState.getCurrentPlayer() == 1 ? "***": "** ",curHandState.getCurrentPlayer() == 2 ? "***": "** ");
        System.out.format("* Type: %1s        %s       * Type: %1s        %s\n",curHandState.getPlayersState().get(0).GetType(),curHandState.getCurrentPlayer() == 1 ? "***": " * ",curHandState.getPlayersState().get(1).GetType(),curHandState.getCurrentPlayer() == 2 ? "***": " * ");
        System.out.format("* State %1s         *        * State %1s         *\n",curHandState.getPlayersState().get(0).getState(),curHandState.getPlayersState().get(1).GetType());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",curHandState.getPlayersState().get(0).getChips(),curHandState.getPlayersState().get(1).getChips());
        System.out.format("* Bets: %-8d  *        * Bets: %-8d  *\n",curHandState.getPlayersState().get(0).getBet(),curHandState.getPlayersState().get(1).getBet());
        if(curHandState.getPlayersState().get(0).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *        ",
                    "Cards: ",curHandState.getPlayersState().get(0).getCard().get(0).toString(),curHandState.getPlayersState().get(0).getCard().get(1).toString());
        }
        else{
            System.out.format("*                 *        ");

        }
        if(curHandState.getPlayersState().get(1).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *\n",
                    "Cards: ",curHandState.getPlayersState().get(1).getCard().get(0).toString(),curHandState.getPlayersState().get(1).getCard().get(1).toString());
        }else
        {
            System.out.format("*                 *\n");
        }

        System.out.format("*******************        *******************\n");
        System.out.format("\n");
        System.out.format("\n");
        System.out.format("     %s       POT: %d   ", curHandState.getStringOfCommunityCard(),curHandState.getPot());
        System.out.format("\n");
        System.out.format("\n");

        System.out.format("                 %s                        %s\n",curHandState.getCurrentPlayer() == 3 ? "***": "   ",curHandState.getCurrentPlayer() == 4 ? "***": "   ");
        System.out.format("*****************%s       *****************%s\n",curHandState.getCurrentPlayer() == 3 ? "***": "** ",curHandState.getCurrentPlayer() == 4 ? "***": "** ");
        System.out.format("* Type: %1s        %s       * Type: %1s        %s\n",curHandState.getPlayersState().get(2).GetType(),curHandState.getCurrentPlayer() == 3 ? "***": " * ",curHandState.getPlayersState().get(3).GetType(),curHandState.getCurrentPlayer() == 4 ? "***": " * ");
        System.out.format("* State %1s         *        * State %1s         *\n",curHandState.getPlayersState().get(2).getState(),curHandState.getPlayersState().get(3).GetType());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",curHandState.getPlayersState().get(2).getChips(),curHandState.getPlayersState().get(3).getChips());
        System.out.format("* Bets: %-8d  *        * Bets: %-8d  *\n",curHandState.getPlayersState().get(2).getBet(),curHandState.getPlayersState().get(3).getBet());
        if(curHandState.getPlayersState().get(2).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *        ",
                    "Cards: ",curHandState.getPlayersState().get(2).getCard().get(0).toString(),curHandState.getPlayersState().get(2).getCard().get(1).toString());
        }
        else{
            System.out.format("*                 *        ");

        }
        if(curHandState.getPlayersState().get(3).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *\n",
                    "Cards: ",curHandState.getPlayersState().get(3).getCard().get(0).toString(),curHandState.getPlayersState().get(3).getCard().get(1).toString());
        }else
        {
            System.out.format("*                 *\n");
        }
        System.out.format("*******************        *******************\n");


    }
    public static void main(String[] args) {

        //PrintGameStatTest();
        PrintGameHandTest();
    }

}
