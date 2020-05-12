package au.edu.jcu.cp3406.brainbusters;

import org.junit.Assert;
import org.junit.Test;

import au.edu.jcu.cp3406.brainbusters.models.Soduku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SodukuModelTest {

    @Test
    public void soduku(){
        Soduku soduku = new Soduku(Soduku.Difficulty.easy);
        soduku.newGame();
        System.out.println(soduku);
        assertTrue(soduku.isValid());
        soduku.setDifficulty(Soduku.Difficulty.easy);
        System.out.println(soduku);
        Assert.assertFalse(soduku.isValid());
    }
}
