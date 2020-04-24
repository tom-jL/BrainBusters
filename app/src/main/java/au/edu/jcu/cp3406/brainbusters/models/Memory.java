package au.edu.jcu.cp3406.brainbusters.models;


import java.util.ArrayList;
import java.util.Collections;



public class Memory {

    ArrayList<Card> cards;
    Card guessCard;





    public Memory(){
        guessCard = null;
        cards = new ArrayList<>();
        for(int i =0; i<52;i++){
            cards.add(new Card(i));
        }
    }


    public boolean[] saveState(){
        boolean[] state = new boolean[52];
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

    public void selectCard(Card card){
        if(card != guessCard && !card.isPaired()){
            if(guessCard == null){
                guessCard = card;
            }else{
                if(card.compareTo(guessCard) == 0){
                    card.paired = true;
                    guessCard.paired = true;
                }
                guessCard = null;
            }
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(int index){
        return cards.get(index);
    }


}



