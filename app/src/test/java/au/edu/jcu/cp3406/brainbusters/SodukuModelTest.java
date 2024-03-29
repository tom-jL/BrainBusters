package au.edu.jcu.cp3406.brainbusters;

import org.junit.Assert;
import org.junit.Test;

import au.edu.jcu.cp3406.brainbusters.models.Soduku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SodukuModelTest {

    @Test
    public void soduku(){
        for(int i = 0; i < 100; i++) {
            Soduku soduku = new Soduku(null);
            soduku.newGame();
            System.out.println(soduku);
            assertTrue(soduku.isValid());
            soduku.setDifficulty(Soduku.Difficulty.easy);
            System.out.println(soduku);
            Assert.assertFalse(soduku.isValid());
        }
    }
}
