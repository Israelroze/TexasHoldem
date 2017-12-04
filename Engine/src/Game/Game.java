package Game;

import Exceptions.*;
import Generated.GameDescriptor;
import Generated.JAXB_Generator;
import Generated.Player;
import Player.*;
import ReturnType.CurrentHandState;
import ReturnType.PlayerStats;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class Game {
    GameDescriptor configuration;
    CurrentHandState state;
    APlayers players;
    boolean is_game_started=false;

    //Methods
    public void LoadPlayers() throws PlayerDataMissingException { this.players=new APlayers(configuration.getPlayers());}
    private void SetPlayersChips()
    {
        for(APlayer player : this.players.GetPlayers())
        {
            player.SetMoney(configuration.getStructure().getBuy());
        }
    }

    public APlayers GetPlayers(){return this.players;}

    //Methods for menu
    @API //Option 1
    public void LoadFromFile(String file_name) throws FileNotFoundException, FileNotXMLException, WrongFileNameException, JAXBException, NullObjectException, UnexpectedObjectException, HandsCountDevideException, BigSmallMismatchException, HandsCountSmallerException, GameStartedException, PlayerDataMissingException {

        if(!this.is_game_started) {
            JAXB_Generator generator = new JAXB_Generator((file_name));
            generator.GenerateFromXML();
            generator.ValidateXMLData();
            this.configuration = generator.getContainer();
            this.LoadPlayers();
            this.SetPlayersChips();
            this.players.ForwardStates();
            //TBD - insert function pass result
        }
        else
        {
            throw new GameStartedException();
        }
    }

    @API // Option 2
    public void StartGame()
    {
        this.is_game_started=true;
        //TBD - insert function pass result
    }

    // Option 3
    public List<PlayerStats> GetPlayersStats()
    {
        List<PlayerStats> stats=new LinkedList<>();
        for(APlayer player:this.players.GetPlayers())
        {
            stats.add(new PlayerStats(player,configuration.getStructure().getHandsCount()));
        }
        return stats;
    }

    //TBD



    // Option 4






}
