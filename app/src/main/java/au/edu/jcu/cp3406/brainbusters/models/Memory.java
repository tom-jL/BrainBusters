package au.edu.jcu.cp3406.brainbusters.models;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import au.edu.jcu.cp3406.brainbusters.R;


public class Memory {

    ArrayList<Card> cards;
    Card guessCard;

    enum Rank{
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

    enum Suit{
        Clubs,
        Diamonds,
        Spades,
        Hearts
    }


    public Memory(){
        cards = new ArrayList<>();
        for(int i =0; i<52;i++){
            cards.add(new Card(i));
            if(i>52/2){
                cards.get(i).pair = cards.get(i-13*2);
                cards.get(i-13*2).pair = cards.get(i);
            }
        }
    }

    private void sortCards(){
        Collections.sort(cards);
    }

    private void shuffleCards(){
        Collections.shuffle(cards);
    }

    void selectCard(Card card){
        if(card != guessCard && !card.isPaired()){
            if(guessCard == null){
                guessCard = card;
            }else{
                if(card.pair == guessCard || guessCard.pair == card){
                    card.paired = true;
                    guessCard.paired = true;
                }
                guessCard = null;
            }
        }
    }


}


class Card implements Comparable<Card>{

    boolean paired;
    int id;
    int cardImage;
    Uri uri;
    Card pair;

    Memory.Suit suit;
    Memory.Rank rank;

    Card(int id){
        this.id = id;
        paired = false;
        uri = Uri.parse("@drawable/c"+id);
        cardImage = R.drawable.back;
        rank = Memory.Rank.values()[id%13];
        suit = Memory.Suit.values()[(int) id/13];
    }


    @NonNull
    @Override
    public String toString() {
        return rank.toString() + " Of " + suit.toString();
    }

    @Override
    public int compareTo(Card card) {
        return Integer.compare(this.id, card.id);
    }

    public Memory.Suit getSuit() {
        return suit;
    }

    public Memory.Rank getRank() {
        return rank;
    }

    public Uri getUri() {
        return uri;
    }

    public Card getPair() {
        return pair;
    }

    public boolean isPaired() {
        return paired;
    }


}

