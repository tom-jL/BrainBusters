package au.edu.jcu.cp3406.brainbusters.models;


import java.util.Arrays;
import java.util.Random;

public class Minesweeper {
    //9 is a bomb!
    private Random random = new Random();
    private int[][] grid;
    private Difficulty difficulty;


    public Minesweeper() {
        difficulty = Difficulty.easy;
        newGame();
    }

    public Minesweeper(Difficulty difficulty) {
        this.difficulty = difficulty;
        newGame();

    }

    public void newGame() {
        int gridSize = (difficulty.ordinal() + 1) * 8;
        grid = new int[gridSize][8];
        for (int i = 0; i < Math.pow(gridSize, 2) / 8; i++) {
            int row = random.nextInt(grid.length);
            int col = random.nextInt(grid[0].length);
            while (grid[row][col] != 0) {
                row = random.nextInt(grid.length);
                col = random.nextInt(grid[0].length);
            }
            grid[row][col] = 9;
        }
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] != 9) {
                    grid[row][col] = getBombCount(getBlock(row, col));
                }
            }
        }

    }

    public int[][] getGrid() {
        return grid;
    }

    public int[] saveState() {
        int[] gameArray = new int[grid.length * grid[0].length];
        int copyPos = 0;
        for (int[] row : grid) {
            System.arraycopy(row, 0, gameArray, copyPos, row.length);
            copyPos += row.length;

        }
        return gameArray;
    }

    public void loadState(int[] gameArray) {
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(gameArray, row * grid.length, grid[row], 0, grid.length);
        }
    }

    private int getBombCount(int[] block) {
        int count = 0;
        for (int i : block) {
            if (i == 9) {
                count++;
            }
        }
        return count;
    }

    private int[] getBlock(int row, int col) {
        int[] block = new int[8];
        int count = 0;
        for (int x = -1; x < 2; x++) {
            for (int y = -1; y < 2; y++) {
                try {
                    if (!(x == 0 && y == 0)) {
                        block[count] = grid[row + x][col + y];
                        count++;
                    }
                } catch (Exception ignored) {

                }
            }
        }
        return block;
    }

    @Override
    public String toString() {
        StringBuilder gridArray = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            gridArray.append(Arrays.toString(grid[i])).append("\n");
        }
        return gridArray.toString();
    }

    public enum Difficulty {
        easy,
        med,
        hard,
    }

}
