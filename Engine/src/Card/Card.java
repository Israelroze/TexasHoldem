package Card;

public class Card {
    private CardNumber number;
    private CardSuit suit;


    public Card(CardNumber num,CardSuit suit) {
        this.number=num;
        this.suit=suit;
    }
    public String ToString() {
        String result = null;

        switch(this.number)
        {
            case ONE:
                result="1";
                break;
            case TWO:
                result="2";
                break;
            case THREE:
                result="3";
                break;
            case FOUR:
                result="4";
                break;
            case FIVE:
                result="5";
                break;
            case SIX:
                result="6";
                break;
            case SEVEN:
                result="7";
                break;
            case EIGHT:
                result="8";
                break;
            case NINE:
                result="9";
                break;
            case TEN:
                result="10";
                break;
            case JACK:
                result="J";
                break;
            case QUEEN:
                result="Q";
                break;
            case KING:
                result="K";
                break;
            case ACE:
                result="A";
                break;
        }
        switch(this.suit)
        {
            case Diamonds:
                result+="D";
                break;
            case Clubs:
                result+="C";
                break;
            case Spades:
                result+="S";
                break;
            case Hearts:
                result+="H";
                break;
        }
        return result;
    }

    public CardSuit GetSuit() {
        return this.suit;
    }

    public CardNumber GetNumber() {
        return this.number;
    }

    public void setNumber(CardNumber number) {
        this.number = number;
    }

    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }
}
