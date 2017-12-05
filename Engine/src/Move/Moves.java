package Move;

import java.util.LinkedList;
import java.util.List;

public class Moves {

    List<Move> moves;

    public Moves()
    {
        this.moves=new LinkedList<>();
    }

    public void AddMove(Move move)
    {
        this.moves.add(move);
    }

    public void AddMove(MoveType type,int value)
    {
        this.moves.add(new Move(type,value));
    }

    public Move GetLastMove()
    {
        return this.moves.get(this.moves.size()-1);
    }

    public boolean IsExist(Move move)
    {
        for(Move Amove:this.moves)
        {
            if(Amove.GetMoveType()==move.GetMoveType()){
                return true;
            }
        }
        return false;
    }

    public int GetHighestStake()
    {
        int max=0;
        for( Move move: this.moves)
        {
            if(move.GetValue()>max)
            {
                max=move.GetValue();
            }
        }
        return max;
    }


}
