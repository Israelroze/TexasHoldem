package Player;

import Exceptions.PlayerDataMissingException;
import Generated.Player;
import Generated.Players;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class APlayers {
    private List<APlayer> aplayers;
    private APlayer dealer;
    private APlayer big;
    private APlayer small;



    public APlayers(Players players) throws PlayerDataMissingException {
        aplayers=new LinkedList<APlayer>();

        for (Player player: players.getPlayer())
        {
            APlayer nplayer=new APlayer(player);
            aplayers.add(nplayer);
        }
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

    private APlayer GetNextPlayer(int index)
    {
        if((index+1)<=aplayers.size()-1) {
            return aplayers.get(index + 1);
        }
        else
        {
            return aplayers.get(0);
        }
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
