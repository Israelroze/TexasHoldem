
import API.InterfaceAPI;
import Exceptions.*;
import Move.Move;
import Move.MoveType;
import Player.PlayerType;
import ReturnType.CurrentHandState;
import ReturnType.PlayerStats;

import java.io.FileNotFoundException;
import java.util.*;

import Game.Game;

import javax.xml.bind.JAXBException;

public class ConsoleUI{

    ///////////////////////////////members////////////////////////////////
    private static Scanner reader = new Scanner(System.in);
    private InterfaceAPI  engine ;
    private boolean isXMLFileLoaded =false;
    private boolean endGame = false;
    private long startTime;
    private int numberOfHandPlayed = 0;


    ///////////////////////////////private functions////////////////////////////////

    private String GetTimePass() {
        long timeElapsed = System.currentTimeMillis() - this.startTime;
        long hours = timeElapsed/1000/60/60;
        long minute = timeElapsed/1000/60 - hours *60;
        return String.format("%d:%d", hours, minute);
    }

    private Move PlayHumanPlayer() {
        List<String> moveChars = new LinkedList<>();// = Arrays.asList("B","b","F","f","R","r","C","c","K","k");
        List<MoveType> moves=null;
        int amount =0;
        String input;
        MoveType moveType;
        PrintGame(engine.GetCurrentHandState());
        moveChars.clear();
        try {
            moves= engine.GetAllowdedMoves();
        } catch (PlayerFoldedException e) {
            //System.out.println("Player folded,Please enter new choice");// TBD:  shouldn't happen, ask israel the reason for this exception!!!
            engine.MoveToNextPlayer();
        } catch (ChipLessThanPotException e) {
            System.out.println("Chips is less than the Pot, number of chip is: "+ e.GetMaxChips());
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
                        moveChars.add("b");
                        moveChars.add("B");
                        break;
                    case FOLD:
                        System.out.print("F. FOLD ");
                        moveChars.add("f");
                        moveChars.add("F");
                        break;
                    case RAISE:
                        System.out.print("R. RAISE ");
                        moveChars.add("r");
                        moveChars.add("R");
                        break;
                    case CALL:
                        System.out.print("C. CALL ");
                        moveChars.add("c");
                        moveChars.add("C");
                        break;
                    case CHECK:
                        System.out.print("K. CHECK ");
                        moveChars.add("k");
                        moveChars.add("K");
                        break;
                }
            }
            System.out.print("\nPlease choose your move (choose by letter): ");
            Boolean isWrongInput = true;
            do {
                input = reader.nextLine();
                if( input.matches("[A-Za-z]{1}")) isWrongInput = false;
            }while(isWrongInput);
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
                    boolean goodInput = false;
                    do {
                        System.out.format("Please enter The amout between  %d to %d: ", engine.GetAllowdedStakeRange()[0], engine.GetAllowdedStakeRange()[1]);
                        while (!reader.hasNextInt()) {

                            System.out.print("Wrong input, please enter a integer: ");
                            reader.nextLine();
                        }

                        amount = reader.nextInt();
                        reader.nextLine();
                        if (engine.GetAllowdedStakeRange()[0] <= amount && amount <= engine.GetAllowdedStakeRange()[1])
                            goodInput = true;
                        else {
                            System.out.println("Please enter number in the range, thanks");
                            reader.nextLine();
                        }
                    } while (!goodInput);
                }
                return new Move(moveType,amount);

            }else {
                System.out.print("\n Wrong Input, Please choose again: ");
                input = reader.next();

            }

        }while(true);
    }

    private void  PlayOneHand() {
        if (engine.IsAnyPlayerOutOfMoney()) {
            endGame = true;
            this.PrintTheWinners();
        } else {
            this.numberOfHandPlayed++;
            engine.StartNewHand();
            this.PlayBidRound();
            this.EndOfRound();
            engine.Flop();
            this.PlayBidRound();
            this.EndOfRound();
            engine.River();
            this.PlayBidRound();
            this.EndOfRound();
            engine.Turn();
            this.PlayBidRound();
            this.EndOfRound();
            engine.SetWinner();
            // add the winner
            PrintTheWinners();
        }
    }


    private void PrintTheWinners(){



        System.out.println("And the winner is \\ are: " );
        List<String> winners=    engine.GetWinner();

        for (String winner : winners){
            System.out.print("  " + winner+ " ");
        }

        System.out.println("");

    }

    private void EndOfRound() {

        if(!engine.IsHumanPlayerFolded()) {
            this.PrintGame(engine.GetCurrentHandState());
            System.out.println("Round just ended, press enter to contionu to next round [Enter]");
            reader.nextLine();
        }
    }


    private Move GetPlayerMove() {
        if(TexasHoldem.ENABLE_LOG)System.out.println("Player Type:"+this.engine.GetCurrentPlayerInfo().GetType() +" ID:"+this.engine.GetCurrentPlayerInfo().GetID()+"Getting move...");
        Move currentMove = null;

        //if computer
        if (engine.IsCurrentPlayerComputer()) {
            try {
                currentMove = engine.GetAutoMove();
            } catch (PlayerFoldedException e) {
                System.out.println("Computer Player Folded- Need to Be handled");
                engine.MoveToNextPlayer();
                engine.CheckBidStatus();
            } catch (ChipLessThanPotException e) {
                if(TexasHoldem.ENABLE_LOG) System.out.println("Player Type:" + this.engine.GetCurrentPlayerInfo().GetType() + " ID:" + this.engine.GetCurrentPlayerInfo().GetID() + "Chip less than Pot ");
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
        while(!engine.IsCurrentBidCycleFinished())
        {
            System.out.println(" ");
            if(this.engine.IsCurrentPlayerFolded() /*|| this.engine.IsCurrentPlayerNoMoney()*/)
            {
                this.engine.MoveToNextPlayer();
                this.engine.CheckBidStatus();
            }

            else
            {
                Move currentMove = this.GetPlayerMove();

                //if (currentMove != null) {

                    //if(TexasHoldem.ENABLE_LOG) System.out.println("Player Type:"+this.engine.GetCurrentPlayerInfo().GetType() +" ID:"+this.engine.GetCurrentPlayerInfo().GetID()+" Move:" + currentMove.GetMoveType().toString() + "   ");
                    //System.out.print(currentMove.GetValue());

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
                        this.engine.CheckBidStatus();
                    }
                    finally{
                        is_round_finished=engine.IsCurrentBidCycleFinished();
                        if(TexasHoldem.ENABLE_LOG) System.out.println("is finished:"+is_round_finished);
                    }
                //}
               // else {
                //    this.engine.MoveToNextPlayer();
                //    this.engine.CheckBidStatus();
                //}
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
       // this.TestOneHand();
       boolean[] menuOption = new boolean[]{true,false,false,false,false,false,true};
        int choice= -1;
        choice = PrintMainMenu(menuOption);
        while ( !this.endGame ) {
            switch (choice) {
                case 1:
                    engine = new Game();
                    LoadXMLFile();
                    if(this.isXMLFileLoaded) {
                        this.numberOfHandPlayed = 0;
                        menuOption[1] = true;
                    }
                    choice = PrintMainMenu(menuOption);
                    break;

                case 2:

                    this.startTime = System.currentTimeMillis();
                    engine.StartGame();
                    this.PrintGameStat(engine.GetPlayersInfo());
                    menuOption[0] = menuOption[1] = false;
                    menuOption[2] = menuOption[3] = menuOption[4] = menuOption[5] = true;
                    choice = PrintMainMenu(menuOption);
                    break;
                case 3:
                    this.PrintGameStat(engine.GetPlayersInfo());
                    menuOption[0] = menuOption[1] = false;
                    menuOption[2] = menuOption[3] = menuOption[4] = menuOption[5] = true;
                    choice = PrintMainMenu(menuOption);
                    break;
                case 4:
                    if (engine != null) {
                        if (this.numberOfHandPlayed >= engine.GetNumberOfHands()) {
                            System.out.println("Game Over, Number Of Hand is 0");
                            PrintWinner(engine.GetPlayersInfo());
                            menuOption[3]= false;
                            menuOption[0] = menuOption[1]= menuOption[2]  = menuOption[4] = menuOption[5] = true;
                        } else {
                            PlayOneHand();
                            if(this.endGame) {
                                System.out.println("One Player run out of money - sorry, this is the end of the game.");
                                System.out.println("Please press enter to exit [ENTER]");
                                reader.nextLine();

                            }
                            else {
                                menuOption[0] = menuOption[1] = false;
                                menuOption[2] = menuOption[3] = menuOption[4] = menuOption[5] = true;
                                if ((this.numberOfHandPlayed == engine.GetNumberOfHands())) {
                                    PrintWinner(engine.GetPlayersInfo());
                                }
                            }

                        }
                        if(!endGame) choice = PrintMainMenu(menuOption);
                    }
                    break;

                case 5:
                    System.out.println("Time elapsed:  " + this.GetTimePass());
                    System.out.println("The number of hands already played: " + engine.GetCurrentHandNumber());
                    System.out.println("The pot size is: " +engine.GetMoneyInGame());//
                    this.PrintGameStat(engine.GetPlayersInfo());
                    menuOption[0] = menuOption[1] = false;
                    menuOption[2] = menuOption[3] = menuOption[4] = menuOption[5] = true;
                    choice = PrintMainMenu(menuOption);
                    break;

                case 6:
                    engine.Buy();
                    menuOption[0] = menuOption[1] = false;
                    menuOption[2] = menuOption[3] = menuOption[4] = menuOption[5] = true;
                    choice = PrintMainMenu(menuOption);

                    break;
                case 7:
                    this.endGame = true;
                    System.out.println("Game End, Bye Bye!");
                    break;





            }
        }



    }

   public void PrintWinner(List<PlayerStats> currentStat){

      List<String> names= new LinkedList<>();
      int num =-1;
      if((currentStat != null) && currentStat.size()>0) {
          for (PlayerStats player : currentStat) {
            if(player.getChips() > num) {
                names.clear();
                names.add(player.getName());
                num = player.getChips();
            }else if(player.getHandsWons() ==  num)
            {
                names.add(player.getName());
            }

          }
      }
       System.out.println("-------------------------------------------" );
      System.out.print("The winner of the game is / are: ");
      for(String name: names)
      {
          System.out.print(name + "  ");
      }
      System.out.println();
       System.out.println("-------------------------------------------" );
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
        System.out.format("%-9s      %4d        %-9s      %4d\n",playerStats.get(0).getName(), playerStats.get(0).getId(), playerStats.get(3).getName(), playerStats.get(3).getId());
        System.out.format("*******************        *******************\n");
        System.out.format("* Type: %1s         *        * Type: %1s         *\n",playerStats.get(0).GetType(),playerStats.get(3).GetType());
        System.out.format("* State %1s         *        * State %1s         *\n",playerStats.get(0).getState(),playerStats.get(3).getState());
        System.out.format("* Chips: %-8d *        * Chips: %-8d *\n",playerStats.get(0).getChips(),playerStats.get(3).getChips());
        System.out.format("* Buys: %-8d  *        * Buys: %-8d  *\n",playerStats.get(0).getBuy(),playerStats.get(3).getBuy());
        System.out.format("* Hands Won:%2d/%-2d *        * Hands Won:%2d/%-2d *\n",playerStats.get(0).getHandsWons(),playerStats.get(0).getNumOfGames(),playerStats.get(3).getHandsWons(),playerStats.get(3).getNumOfGames());
        System.out.format("*******************        *******************\n");
        System.out.format("\n");
        System.out.format("\n");
        System.out.format("%-9s      %4d        %-9s      %4d\n",playerStats.get(1).getName(), playerStats.get(1).getId(), playerStats.get(2).getName(), playerStats.get(2).getId());
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
            while(!reader.hasNextInt()) {

                System.out.print("Wrong input, please enter a integer: ");
                reader.nextLine();
            }
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


        System.out.format("%-9s  %-4d  %s       %-9s  %-4d  %s\n",curHandState.getPlayersState().get(0).getName(),curHandState.getPlayersState().get(0).getId(),curHandState.getCurrentPlayer() == 0 ? "***": "   ",curHandState.getPlayersState().get(3).getName(),curHandState.getPlayersState().get(3).getId(),curHandState.getCurrentPlayer() == 3 ? "***": "   ");
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
        System.out.format("     %s            ***POT: %d *** \n", curHandState.getStringOfCommunityCard(),curHandState.getPot());
        System.out.format("Big Blind: %-5d   Small Blind : %-5d \n", curHandState.getBigBlind(), curHandState.getSmallBlind());
        System.out.format("\n");
        System.out.format("%-9s  %-4d  %s       %-9s %4d  %s\n",curHandState.getPlayersState().get(1).getName(),curHandState.getPlayersState().get(1).getId(),curHandState.getCurrentPlayer() == 1 ? "***": "   ",curHandState.getPlayersState().get(2).getName(),curHandState.getPlayersState().get(2).getId(),curHandState.getCurrentPlayer() == 2 ? "***": "   ");
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
