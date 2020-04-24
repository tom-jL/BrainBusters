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


    public boolean[] saveState(){
        boolean[] state = new boolean[DECK_SIZE];
        for(int i =0; i <state.length; i++){
            state[cards.get(i).id] = cards.get(i).paired;
        }
        return state;
    }

    public void loadState(boolean[] state) {
        cards = new ArrayList<>();
        for(int i =0; i <state.length; i++){
            Card card = new Card(i);
            card.paired = state[i];
            cards.add(new Card(i));
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



