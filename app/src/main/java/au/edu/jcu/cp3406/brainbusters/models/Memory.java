package au.edu.jcu.cp3406.brainbusters.models;


import java.util.ArrayList;
import java.util.Collections;


public class Memory {

    ArrayList<Card> cards;

    public static final int DECK_SIZE = 26;

    public Memory(){
        cards = new ArrayList<>();
        for(int i =0; i<DECK_SIZE;i++){
            cards.add(new Card(i*52/DECK_SIZE));
        }
    }


    public int[] saveOrderState(){
        int[] state = new int[DECK_SIZE];
        for(int i =0; i <state.length; i++){
            Card card = getCard(i);
            state[i] = card.id;
        }
        return state;
    }

    public boolean[] savePairState(){
        boolean[] state = new boolean[DECK_SIZE];
        for(int i =0; i <state.length; i++){
            Card card = getCard(i);
            state[i] = card.paired;
        }
        return state;
    }

    public void loadState(int[] orderState, boolean[] pairedState) {
        cards = new ArrayList<>();
        for(int i =0; i <DECK_SIZE; i++){
            Card card = new Card(orderState[i], pairedState[i]);
            cards.add(card);
        }

    }

    public void sortCards(){
        Collections.sort(cards);
    }

    public void shuffleCards(){
        Collections.shuffle(cards);
    }


    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(int index){
        return cards.get(index);
    }


}



