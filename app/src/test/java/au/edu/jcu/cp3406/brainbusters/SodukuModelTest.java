package au.edu.jcu.cp3406.brainbusters;

import org.junit.Test;

import au.edu.jcu.cp3406.brainbusters.models.Soduku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SodukuModelTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }



    @Test
    public void soduku_isCorrect(){
        Soduku soduku = new Soduku();
        System.out.println(soduku);
        assertTrue(soduku.isValid());
        soduku.setDifficulty(Soduku.Difficulty.easy);
        System.out.println(soduku);
        assertTrue(soduku.isValid());
    }
}
