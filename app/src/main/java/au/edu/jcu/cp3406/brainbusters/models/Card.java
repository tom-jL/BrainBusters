package au.edu.jcu.cp3406.brainbusters.models;

import android.util.AttributeSet;

import androidx.annotation.NonNull;


public class Card implements Comparable<Card> {

    boolean paired;

    int id;
    Suit suit;
    Rank rank;
    String fileName;


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
        rank = Rank.values()[id%13];
        suit = Suit.values()[(int) id/13];
        setFileName();
    }

    public Card(int id, boolean paired) {
        this.id = id;
        this.paired = paired;
        rank = Rank.values()[id%13];
        suit = Suit.values()[(int) id/13];
        setFileName();
    }


    private void setFileName() {
        StringBuilder stringBuilder = new StringBuilder();
        if((id%13)+2 < 11) {
            stringBuilder.append((id% 13)+2);
        } else {
            stringBuilder.append(rank.toString().toUpperCase().substring(0, 1));
        }
        stringBuilder.append(suit.toString().toUpperCase().substring(0,1));
        fileName = stringBuilder.toString();
    }

    public String getFileName() {
        return fileName;
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

    public void setPaired(boolean paired) {
        this.paired = paired;
    }
}
