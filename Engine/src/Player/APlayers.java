package Player;

import Exceptions.PlayerDataMissingException;
import Generated.Player;
import Generated.Players;

import java.util.*;

public class APlayers {
    private List<APlayer> aplayers;
    private APlayer dealer;
    private APlayer big;
    private APlayer small;
    private Map<Integer,Integer> ids;

    public APlayers(Players players) throws PlayerDataMissingException {
        aplayers=new LinkedList<APlayer>();
        ids=new HashMap<>();

        int index=0;
        for (Player player: players.getPlayer())
        {
            APlayer nplayer=new APlayer(player);
            aplayers.add(nplayer);
        }
    }

    public APlayers()  {
        aplayers=new LinkedList<APlayer>();
        ids=new HashMap<>();
    }

    public void RandomPlayerSeats() {
        Random rnd = new Random();
        Collections.shuffle(this.aplayers,rnd);
    }

    public void PrintPlayers(){
        for(APlayer player: this.aplayers)
        {
            System.out.println(player.GetName());
        }
    }

    public APlayer GetNextPlayer(int index)
    {
        if((index+1)<=aplayers.size()-1) {
            return aplayers.get(index + 1);
        }
        else
        {
            return aplayers.get(0);
        }
    }

    public void DeletePlayerById(int id){

        for(APlayer player: this.aplayers)
        {
            if(player.getId()==id)
            {
                this.aplayers.remove(player);
<<<<<<< HEAD
                int num_of_humans=0;
                for(APlayer hp:this.aplayers) { if(hp.GetType()==PlayerType.HUMAN) num_of_humans++; }
                if(num_of_humans==1)
                {
                    if(this.aplayers.size()==num_of_humans) this.is_only_one_player=true;
                }
                else if(num_of_humans<1) this.is_only_one_player=true;

=======
>>>>>>> parent of 912f167... Almost final
                return;
            }
        }
    }

    public boolean IsPlayerExist(int id){
        for(APlayer player: this.aplayers)
        {
            if(player.getId()==id)
            {
                return true;
            }
        }
        return false;
    }

    public int GetSize()
    {
        return aplayers.size();
    }

    public int GetFirstPlayerID(){
        return this.aplayers.get(0).getId();
    }

    public APlayer GetPlayer(int id)
    {
        for(APlayer player: this.aplayers)
        {
            if(player.getId()==id)
            {
                return player;
            }
        }
        return null;
        //return aplayers.get(index);
    }

    public APlayer GetNextPlayer(APlayer player)
    {
        int index=this.aplayers.indexOf(player);
        return this.GetNextPlayer(index);
    }

    private void RandomDealer()
    {
        Random rnd = new Random();
        int i = rnd.nextInt(aplayers.size());
        this.dealer=aplayers.get(i);
        dealer.SetPlayerState(PlayerState.DEALER);
    }

    private void SetSmall()
    {
        this.small=this.GetNextPlayer(this.dealer);
        small.SetPlayerState(PlayerState.SMALL);
    }

    private void SetBig()
    {
        this.big=this.GetNextPlayer(this.small);
        big.SetPlayerState(PlayerState.BIG);
    }

    public void ForwardStates()
    {
        if(this.dealer==null)
        {
            this.RandomDealer();
        }
        else {
            this.dealer.SetPlayerState(PlayerState.NONE);
            this.dealer = this.small;
            this.dealer.SetPlayerState(PlayerState.DEALER);
        }
        this.SetSmall();
        this.SetBig();
    }

    public APlayer GetDealer()
    {
       return this.dealer;
    }

    public APlayer GetSmallPlayer()
    {
        return this.small;
    }

    public APlayer GetBigPlayer()
    {
        return this.big;
    }

    public List<APlayer> GetPlayers()
    {
        return this.aplayers;
    }
}
