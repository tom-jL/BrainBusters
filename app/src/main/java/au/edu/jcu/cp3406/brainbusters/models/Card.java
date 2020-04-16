package au.edu.jcu.cp3406.brainbusters.models;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import au.edu.jcu.cp3406.brainbusters.R;

public class Card implements Comparable<Card>{

    boolean paired;
    int id;
    int cardImage;
    String uri;
    Suit suit;
    Rank rank;

    public enum Rank{
        Two,
        Three,
        Four,
        Five,
        Six,
        Seven,
        Eight,
        Nine,
        Ten,
        JACK,
        QUEEN,
        KING,
        ACE
    }

    public enum Suit{
        Clubs,
        Diamonds,
        Spades,
        Hearts
    }



    Card(int id){
        this.id = id;
        paired = false;
        uri = "@drawable/c"+id;
        cardImage = R.drawable.back;
        rank = Rank.values()[id%13];
        suit = Suit.values()[(int) id/13];
    }


    @NonNull
    @Override
    public String toString() {
        return rank.toString() + " Of " + suit.toString();
    }

    @Override
    public int compareTo(Card card) {
        if(this.rank == card.rank && this.suit.ordinal()%2 == card.suit.ordinal()%2) return 0;
        return Integer.compare(this.id, card.id);
    }


    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isPaired() {
        return paired;
    }


}
