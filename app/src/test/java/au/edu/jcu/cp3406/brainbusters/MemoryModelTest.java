package au.edu.jcu.cp3406.brainbusters;

import org.junit.Test;

import au.edu.jcu.cp3406.brainbusters.models.Card;
import au.edu.jcu.cp3406.brainbusters.models.Memory;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class MemoryModelTest {

    @Test
    public void memory(){
        Memory memory = new Memory();
        Card card = memory.getCards().get(0);
        assertSame(card.getRank(), Card.Rank.Two);
        assertSame(card.getSuit(), Card.Suit.Clubs);
        card = memory.getCards().get(51);
        assertSame(card.getRank(), Card.Rank.ACE);
        assertSame(card.getSuit(), Card.Suit.Hearts);

        memory.shuffleCards();
        memory.sortCards();

        card = memory.getCards().get(0);
        assertSame(card.getRank(), Card.Rank.Two);
        assertSame(card.getSuit(), Card.Suit.Clubs);
        card = memory.getCards().get(51);
        assertSame(card.getRank(), Card.Rank.ACE);
        assertSame(card.getSuit(), Card.Suit.Hearts);

        memory.selectCard(memory.getCard(0));
        memory.selectCard(memory.getCard(13*2));
        assertTrue(memory.getCard(0).isPaired());
        assertTrue(memory.getCard(13*2).isPaired());

    }


}
