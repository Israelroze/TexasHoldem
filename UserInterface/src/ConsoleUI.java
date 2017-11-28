import Player.PlayerState;
import Player.PlayerType;
import ReturnType.PlayerStats;
import com.sun.media.jfxmedia.events.PlayerStateEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConsoleUI{
    public static void PrintGameStat(List<PlayerStats> playerStats){
        System.out.format("*******************        *******************\n");
        System.out.format("* Type: %1s         *        * Type: %1s         *\n",playerStats.get(0).getType(),playerStats.get(1).getType());
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
        System.out.format("* Type: %1s         *        * Type: %1s         *\n",playerStats.get(2).getType(),playerStats.get(3).getType());
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
        PrintGameStat(a);


    }

    public static void main(String[] args) {
        PrintGameStatTest();
    }

}
