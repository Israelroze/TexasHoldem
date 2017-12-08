
import API.InterfaceAPI;
import Exceptions.*;
import Move.Move;
import Move.MoveType;
import Player.PlayerState;
import Player.PlayerType;
import ReturnType.CurrentHandState;
import ReturnType.PlayerHandState;
import ReturnType.PlayerStats;

import java.io.FileNotFoundException;
import java.util.*;

import Card.Card;
import Card.CardSuit;
import Card.CardNumber;
import Game.Game;

import javax.xml.bind.JAXBException;

public class ConsoleUI{

    ///////////////////////////////members////////////////////////////////
    private static Scanner reader = new Scanner(System.in);
    private InterfaceAPI  engine ;
    private boolean isXMLFileLoaded =false;
    private boolean endGame = false;
    private int numberOfHands;
    private long startTime;


    ///////////////////////////////private functions////////////////////////////////

    private String GetTimePass() {
        long timeElapsed = System.currentTimeMillis() - this.startTime;
        long hours = timeElapsed/1000/60/60;
        long minute = timeElapsed/1000/60 - hours *60;
        return String.format("%d:%d", hours, minute);
    }

    private Move PlayHumanPlayer() {
        List<String> moveChars = Arrays.asList("B","b","F","f","R","r","C","c","K","k");
        List<MoveType> moves=null;
        int amount =0;
        String input;
        MoveType moveType;
        PrintGame(engine.GetCurrentHandState());

        try {
            moves= engine.GetAllowdedMoves();
        } catch (PlayerFoldedException e) {
            //System.out.println("Player folded,Please enter new choice");// TBD:  shouldn't happen, ask israel the reason for this exception!!!
            engine.MoveToNextPlayer();
        } catch (ChipLessThanPotException e) {
            System.out.println("Chips is less than the Pot, Number of Chip is: "+ e.GetMaxChips());
        }
        if(moves == null)
        {
            System.out.println("Moves not avaliable");
            return null;
        }

        do {
            //this.PrintGame(engine.GetCurrentHandState());
            for (MoveType move : moves) {
                switch (move) {
                    case BET:
                        System.out.print("B. BET ");
                        break;
                    case FOLD:
                        System.out.print("F. FOLD ");
                        break;
                    case RAISE:
                        System.out.print("R. RAISE ");
                        break;
                    case CALL:
                        System.out.print("C. CALL ");
                        break;
                    case CHECK:
                        System.out.print("K. CHECK ");
                        break;
                }
            }
            System.out.print("\nPlease choose your move (choose by letter): ");
            input = reader.next();
            moveType = MoveType.FOLD;
            //this.PrintGame(engine.GetCurrentHandState());
            if (moveChars.contains(input)) {

                switch (input) {
                    case "B":
                    case "b":
                        moveType = MoveType.BET;
                        break;
                    case "F":
                    case "f":
                        moveType = MoveType.FOLD;
                        break;
                    case "C":
                    case "c":
                        moveType = MoveType.CALL;
                        break;
                    case "K":
                    case "k":
                        moveType = MoveType.CHECK;
                        break;
                    case "R":
                    case "r":
                        moveType = MoveType.RAISE;
                        break;
                }
                if (moveType == MoveType.BET || moveType == MoveType.RAISE) {

                    System.out.print("Please enter The amout: ");
                    amount = reader.nextInt();
                    reader.nextLine();

                }
                return new Move(moveType,amount);

            }else {
                System.out.print("\n Wrong Input, Please choose again: ");
                input = reader.next();

            }

        }while(true);
    }

    private void  PlayOneHand() {
        engine.StartNewHand();
        this.PlayBidRound();
        this.EndOfRound();
        engine.Flop();
        this.PlayBidRound();
        this.EndOfRound();
        engine.Turn();
        this.PlayBidRound();
        this.EndOfRound();
        engine.River();
        this.PlayBidRound();
        this.EndOfRound();

        //TBD
        // add the winner
    }

    private void EndOfRound() {
        this.PrintGame(engine.GetCurrentHandState());
        System.out.println("Round just ended, Press Enter to contionu to next Round");
        reader.nextLine();
    }


    private Move GetPlayerMove() {
        System.out.println("Player Type:"+this.engine.GetCurrentPlayerInfo().GetType() +" ID:"+this.engine.GetCurrentPlayerInfo().GetID()+"Getting move...");
        Move currentMove = null;

        //if computer
        if (engine.IsCurrentPlayerComputer()) {
            try {
                currentMove = engine.GetAutoMove();
            } catch (PlayerFoldedException e) {
                System.out.println("Computer Player Folded- Need to Be handled");
                //engine.MoveToNextPlayer();
            } catch (ChipLessThanPotException e) {
                System.out.println("Player Type:" + this.engine.GetCurrentPlayerInfo().GetType() + " ID:" + this.engine.GetCurrentPlayerInfo().GetID() + "Chip less than Pot" + e.GetMaxChips());
            }
        }
        else //if human
        {
            if (engine.IsCurrentPlayerHuman()) {
                currentMove = PlayHumanPlayer();
            }
        }

        return currentMove;
    }
    private void SetMove(Move move){

    }
    private void PlayBidRound(){

        //init a new bid round
        try {
            engine.StartNewBidCycle();
        } catch (NoSufficientMoneyException e) {
            //it means one of the players do not enough to put the big or small blind
            this.endGame = true;
        }

        boolean is_round_finished=false;
        //bid round cycle
        while(!is_round_finished)
        {
            System.out.println(" ");
            if(this.engine.IsCurrentPlayerFolded())
            {
                this.engine.MoveToNextPlayer();
            }
            else
            {
                Move currentMove = this.GetPlayerMove();

                if (currentMove != null) {

                    System.out.println("Player Type:"+this.engine.GetCurrentPlayerInfo().GetType() +" ID:"+this.engine.GetCurrentPlayerInfo().GetID()+" Move:" + currentMove.GetMoveType().toString() + "   ");
                    System.out.print(currentMove.GetValue());

                    try {
                        engine.SetNewMove(currentMove);
                        //TBD -- HANDLE
                    } catch (StakeNotInRangeException e) {
                        System.out.println("Stake value not in range!");
                    } catch (PlayerFoldedException e) {
                        System.out.println("Player Folded 11");
                        //engine.MoveToNextPlayer();
                    } catch (MoveNotAllowdedException e) {
                        System.out.println("Move Not allowed 22");
                    } catch (ChipLessThanPotException e) {
                        System.out.println("Chip less than Pot 33");
                    } catch (NoSufficientMoneyException e) {
                        System.out.println("No sufficient Money");
                    } catch (PlayerAlreadyBetException e) {
                        System.out.println("Player already bet");
                        this.engine.MoveToNextPlayer();
                    }
                    finally{
                        is_round_finished=engine.IsCurrentBidCycleFinished();
                        System.out.println("is finished:"+is_round_finished);
                    }

                }
            }
        }
    }

    private void LoadXMLFile() {


        System.out.println("Please enter XML file path: ");
        String res = reader.nextLine();


        try { engine.LoadFromXML(res);
        } catch (GameStartedException e) {
            System.out.println("Game Already Start");
            return;
        } catch (UnexpectedObjectException e) {
            System.out.println("No XML file exist");
            return;
        } catch (FileNotFoundException e) {
            System.out.println("No XML file exist");
            return;
        } catch (BigSmallMismatchException e) {
            System.out.println("Problem in XML file, Big Blind and Small Blind Value is incorrect");
            return;
        } catch (PlayerDataMissingException e) {
            System.out.println("Problem in XML file, player data missing");
            return;
        } catch (HandsCountDevideException e) {
            System.out.println("Problem in XML file, Hand count do not divine by number of players");
            return;
        } catch (WrongFileNameException e) {
            System.out.println("Problem in XML file, wrong File Name");
            return;
        } catch (HandsCountSmallerException e) {
            System.out.println("Problem in XML file,");
            return;
        } catch (JAXBException e) {
            System.out.println("Problem in XML file,");
            return;
        } catch (FileNotXMLException e) {
            System.out.println("Problem in XML file,");
            return;
        } catch (NullObjectException e) {
            System.out.println("Problem in XML file,");
            return;
        }
        this.isXMLFileLoaded = true;
    }

    private int PrintMainMenu(boolean[] menuOption){

        if (menuOption.length != 7) {
            System.out.println("MenuOption Size is wrong");
            return -1;
        }


        System.out.println("Please enter your choice: ");
        if(menuOption[0]) System.out.println("1. Load Game Setting From XML File");
        if(menuOption[1]) System.out.println("2. Start Game");
        if(menuOption[2]) System.out.println("3. Show Game State");
        if(menuOption[3]) System.out.println("4. Play One Hand");
        if(menuOption[4]) System.out.println("5. Get Statistics");
        if(menuOption[5]) System.out.println("6. Extra Buy");
        if(menuOption[6]) System.out.println("7. Quit Game");
        System.out.println("");

        return GetOptionNumber(menuOption);
    }

    @Override
    protected void finalize() throws Throwable {
        reader.close();
        super.finalize();
    }

    ///////////////////////////////public functions////////////////////////////////

    public void  RunGame() {
        this.TestOneHand();
//       boolean[] menuOption = new boolean[]{true,false,false,false,false,false,true};
//        int choice= -1;
//        choice = PrintMainMenu(menuOption);
//        while ( !this.endGame ) {
//            switch (choice) {
//                case 1:
//                    engine = new Game();
//                    LoadXMLFile();
//                    this.numberOfHands = engine.GetNumberOfHands();
//                    menuOption[1] = true;
//                    choice = PrintMainMenu(menuOption);
//                    break;
//
//                case 2:
//                    this.startTime = System.currentTimeMillis();
//                    engine.StartGame();
//                    this.PrintGameStat(engine.GetPlayersInfo());
//                    menuOption[0] = menuOption[1] = false;
//                    menuOption[2] = menuOption[3] = menuOption[4] = menuOption[5] = true;
//                    choice = PrintMainMenu(menuOption);
//                    break;
//                case 3:
//                    this.PrintGameStat(engine.GetPlayersInfo());
//                    menuOption[0] = menuOption[1] = false;
//                    menuOption[2] = menuOption[3] = menuOption[4] = menuOption[5] = true;
//                    choice = PrintMainMenu(menuOption);
//                    break;
//                case 4:
//                    if (engine != null && engine.GetNumberOfHands() == 0)
//                        System.out.println("Game Over, Number Of Hand is 0");
//                    else {
//                        PlayOneHand();
//                        menuOption[0] = menuOption[1] = false;
//                        menuOption[2] = menuOption[3] = menuOption[4] = menuOption[5] = true;
//                        choice = PrintMainMenu(menuOption);
//                    }
//                    break;
//
//                case 5:
//                    System.out.println("Time elapsed:  " + this.GetTimePass());
//                    System.out.println("The number of hands already played: " + engine.GetCurrentHandNumber());
//                    System.out.println("The pot size is: " );// TBD- add pot size
//                    this.PrintGameStat(engine.GetPlayersInfo());
//                    menuOption[0] = menuOption[1] = false;
//                    menuOption[2] = menuOption[3] = menuOption[4] = menuOption[5] = true;
//                    choice = PrintMainMenu(menuOption);
//                    break;
//
//                case 6:
//                    // TBD -ADD BUY
//                    break;
//                case 7:
//                    this.endGame = true;
//                    System.out.println("Game End, Bye Bye!");
//                    break;
//
//
//
//
//
//            }
//        }



    }

    public void TestOneHand(){
        engine = new Game();
        LoadXMLFile();
        engine.StartGame();
        this.PrintGameStat(engine.GetPlayersInfo());
        engine.StartNewHand();
        this.PlayBidRound();
    }

    public  void PrintGameStat(List<PlayerStats> playerStats){
        System.out.format("*******************        *******************\n");
        System.out.format("* Type: %1s         *        * Type: %1s         *\n",playerStats.get(0).GetType(),playerStats.get(3).GetType());
        System.out.format("* State %1s         *        * State %1s         *\n",playerStats.get(0).getState(),playerStats.get(3).getState());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",playerStats.get(0).getChips(),playerStats.get(3).getChips());
        System.out.format("* Buys: %-8d  *        * Buys: %-8d  *\n",playerStats.get(0).getBuy(),playerStats.get(3).getBuy());
        System.out.format("* Hands Won:%2d/%-2d *        * Hands Won:%2d/%-2d *\n",playerStats.get(0).getHandsWons(),playerStats.get(0).getNumOfGames(),playerStats.get(3).getHandsWons(),playerStats.get(3).getNumOfGames());
        System.out.format("*******************        *******************\n");
        System.out.format("\n");
        System.out.format("\n");
        System.out.format("*******************        *******************\n");
        System.out.format("* Type: %1s         *        * Type: %1s         *\n",playerStats.get(1).GetType(),playerStats.get(2).GetType());
        System.out.format("* State %1s         *        * State %1s         *\n",playerStats.get(1).getState(),playerStats.get(2).getState());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",playerStats.get(1).getChips(),playerStats.get(2).getChips());
        System.out.format("* Buys: %-8d  *        * Buys: %-8d  *\n",playerStats.get(1).getBuy(),playerStats.get(2).getBuy());
        System.out.format("* Hands Won:%2d/%-2d *        * Hands Won:%2d/%-2d *\n",playerStats.get(1).getHandsWons(),playerStats.get(1).getNumOfGames(),playerStats.get(2).getHandsWons(),playerStats.get(2).getNumOfGames());
        System.out.format("*******************        *******************\n");

    }

    public int GetOptionNumber(boolean[] menuOption) {
        int n;
        boolean goodInput= false;
        System.out.println("Please enter your choice:");
        // Reading from System.in
        do {

            n = reader.nextInt(); reader.nextLine(); // Scans the next token of the input as an int.
            if ((n >= 1 && n <= 7) && menuOption.length == 7 && menuOption[n - 1] == true)
            {

                return n;
            }
            else {
                System.out.print("Wrong input, please enter your choice again: ");
            }
        }while (true);
            //once finished
    }

    public  void PrintGame(CurrentHandState curHandState){


//        System.out.format("%s",curHandState.getPlayersState().get(0).getCard().toString());
//        System.out.format("%s",curHandState.getPlayersState().get(0).getCard().toString());
//        System.out.format("%s",curHandState.getPlayersState().get(1).IsHuman() ?"": "Cards: ");
//        System.out.format("%s",curHandState.getPlayersState().get(1).getCard().toString());


        System.out.format("                 %s                        %s\n",curHandState.getCurrentPlayer() == 0 ? "***": "   ",curHandState.getCurrentPlayer() == 3 ? "***": "   ");
        System.out.format("*****************%s       *****************%s\n",curHandState.getCurrentPlayer() == 0 ? "***": "** ",curHandState.getCurrentPlayer() == 3 ? "***": "** ");
        System.out.format("* Type: %1s        %s       * Type: %1s        %s\n",curHandState.getPlayersState().get(0).GetType().toString(),curHandState.getCurrentPlayer() == 0 ? "***": " * ",curHandState.getPlayersState().get(3).GetType().toString(),curHandState.getCurrentPlayer() == 3 ? "***": " * ");
        System.out.format("* State %1s         *        * State %1s         *\n",curHandState.getPlayersState().get(0).getState().toString(),curHandState.getPlayersState().get(3).getState().toString());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",curHandState.getPlayersState().get(0).getChips(),curHandState.getPlayersState().get(3).getChips());
        System.out.format("* Bets: %-8d  *        * Bets: %-8d  *\n",curHandState.getPlayersState().get(0).getBet(),curHandState.getPlayersState().get(3).getBet());
        if(curHandState.getPlayersState().get(0).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *        ",
                    "Cards: ",curHandState.getPlayersState().get(0).getCard().get(0).toString(),curHandState.getPlayersState().get(0).getCard().get(1).toString());
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
        System.out.format("\n");
        System.out.format("     %s       POT: %d   ", curHandState.getStringOfCommunityCard(),curHandState.getPot());
        System.out.format("\n");
        System.out.format("                 %s                        %s\n",curHandState.getCurrentPlayer() == 1 ? "***": "   ",curHandState.getCurrentPlayer() == 2 ? "***": "   ");
        System.out.format("*****************%s       *****************%s\n",curHandState.getCurrentPlayer() == 1 ? "***": "** ",curHandState.getCurrentPlayer() == 2 ? "***": "** ");
        System.out.format("* Type: %1s        %s       * Type: %1s        %s\n",curHandState.getPlayersState().get(1).GetType().toString(),curHandState.getCurrentPlayer() == 1 ? "***": " * ",curHandState.getPlayersState().get(2).GetType().toString(),curHandState.getCurrentPlayer() == 2 ? "***": " * ");
        System.out.format("* State %1s         *        * State %1s         *\n",curHandState.getPlayersState().get(1).getState().toString(),curHandState.getPlayersState().get(2).getState().toString());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",curHandState.getPlayersState().get(1).getChips(),curHandState.getPlayersState().get(2).getChips());
        System.out.format("* Bets: %-8d  *        * Bets: %-8d  *\n",curHandState.getPlayersState().get(1).getBet(),curHandState.getPlayersState().get(2).getBet());
        if(curHandState.getPlayersState().get(1).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *        ",
                    "Cards: ",curHandState.getPlayersState().get(1).getCard().get(0).toString(),curHandState.getPlayersState().get(1).getCard().get(1).toString());
        }
        else{
            System.out.format("*                 *        ");

        }
        if(curHandState.getPlayersState().get(2).IsHuman() ){
            System.out.format("* %6s %-3s%3s  *\n",
                    "Cards: ",curHandState.getPlayersState().get(2).getCard().get(0).toString(),curHandState.getPlayersState().get(2).getCard().get(1).toString());
        }else
        {
            System.out.format("*                 *\n");
        }
        System.out.format("*******************        *******************\n");


    }



}
