package au.edu.jcu.cp3406.brainbusters.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Soduku {

    private Random random = new Random();
    private int[][] game;


    public Soduku() {
        game = new int[9][9];
    }

    private static int getBlockIndex(int row, int col) {
        int index = 0;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                if (row < i * 3 && col < j * 3) {
                    return index;
                }
                ++index;
            }
        }
        return index;
    }

    private static boolean isDistinct(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {

                    if ( (i != j && array[i] == array[j] && array[i] != 0) || array[j] == -1) {
                        return false;
                    }

            }
        }
        return true;
    }

    public void newGame() {
        boolean failed = true;
        while (failed) {
            failed = false;
            this.game = new int[9][9];
            for (int row = 0; row < 9 && !failed; row++) {
                for (int col = 0; col < 9 && !failed; col++) {
                    ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
                    int index = random.nextInt(numbers.size());
                    game[row][col] = numbers.get(index);
                    while ((!isValidRow(row) || !isValidColumn(col) || !isValidBlock(getBlockIndex(row, col))) && !failed) {
                        index = random.nextInt(numbers.size());
                        game[row][col] = numbers.get(index);
                        numbers.remove(index);
                        if (numbers.isEmpty()) {
                            failed = true;
                        }
                    }


                }

            }
        }
    }

    public int[][] getGame() {
        return game;
    }

    public int[] saveState() {
        int[] gameArray = new int[9 * 9];
        int copyPos = 0;
        for (int[] row : game) {
            System.arraycopy(row, 0, gameArray, copyPos, row.length);
            copyPos += row.length;
        }
        return gameArray;
    }

    public void loadState(int[] gameArray) {
        for (int row = 0; row < 9; row++) {
            System.arraycopy(gameArray, row * 9, game[row], 0, 9);
        }
    }

    public void setDifficulty(Difficulty difficulty) {
        int hidden = (difficulty.ordinal()+1) * 10;
        for (int i = hidden; i > 0; --i) {
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            while (game[row][col] == -1) {
                row = random.nextInt(9);
                col = random.nextInt(9);
            }
            game[row][col] = -1;
        }
    }

    public int getCell(int row, int col) {
        return game[row][col];
    }

    public int[] getRow(int index) {
        return game[index];
    }

    private int[] getColumn(int index) {
        int[] col = new int[9];
        for (int i = 0; i < 9; i++) {
            col[i] = game[i][index];
        }
        return col;
    }

    private int[] getBlock(int index) {
        int[] block = new int[9];
        int blockColumn = (index % 3) * 3;
        int blockRow = index < 3 ? 0 : index < 6 ? 3 : index < 9 ? 6 : 0;
        for (int row = 0; row < 3; row++) {
            System.arraycopy(game[blockRow + row], blockColumn, block, row * 3, 3);
        }
        return block;
    }

    private boolean isValidRow(int rowIndex) {
        int[] row = getRow(rowIndex);
        return isDistinct(row);
    }

    private boolean isValidColumn(int colIndex) {
        int[] col = getColumn(colIndex);
        return isDistinct(col);
    }

    private boolean isValidBlock(int blockIndex) {
        int[] block = getBlock(blockIndex);
        return isDistinct(block);
    }

    public boolean isValid(int row, int col){
        return (isValidRow(row) && isValidColumn(col) && isValidBlock(getBlockIndex(row,col)));
    }

    public boolean isValid() {
        for (int index = 0; index < 9; index++) {
            if (!(isValidRow(index) && isValidColumn(index) && isValidBlock(index))) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder sodukuArray = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sodukuArray.append("\n").append(Arrays.toString(game[i]));
        }
        return sodukuArray.toString();
    }

    public void setCell(int row, int col, int number) {
        game[row][col] = number;
    }

    public enum Difficulty {
        easy,
        med,
        hard,
    }
}


