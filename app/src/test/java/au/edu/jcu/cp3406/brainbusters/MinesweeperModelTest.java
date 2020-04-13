package au.edu.jcu.cp3406.brainbusters;

import org.junit.Test;

import au.edu.jcu.cp3406.brainbusters.models.Minesweeper;

public class MinesweeperModelTest {

    @Test
    public void minesweeper_easy(){
        Minesweeper minesweeper = new Minesweeper();
        System.out.println(minesweeper.toString());

    }

    @Test
    public void minesweeper_med() {
        Minesweeper minesweeper = new Minesweeper(Minesweeper.Difficulty.med);
        System.out.println(minesweeper.toString());
    }

    @Test
    public void minesweeper_hard(){
        Minesweeper minesweeper = new Minesweeper(Minesweeper.Difficulty.hard);
        System.out.println(minesweeper.toString());
    }
}
