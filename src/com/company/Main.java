package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    public static final int SYMBOL_O = 1;
    public static final int SYMBOL_X = 2;
    public static final int SYMBOL_NOT_SET = 0;

    public boolean won = false;

    private final int[] field = new int[9];

    public static void main(String[] args) {
        new Main().play();
    }

    public boolean setSymbol(int symbol, int index) {
        if (0 > index || index > 8) {
            return false;
        }

        if ((symbol == SYMBOL_O || symbol == SYMBOL_X)) {
            if (field[index] != 0) {
                return false;
            }

            field[index] = symbol;

            return true;
        }

        return false;
    }

    public boolean gameEnd() {
        for (int i = 0; i < field.length - 1; i++) {
            if (field[i] == SYMBOL_NOT_SET) {
                return false;
            }
        }

        indecisiveMessage();

        return won = true;
    }

    public boolean hasWon(int symbol, String player) {
        if (checkColumn(symbol) || checkRow(symbol) || checkDiagonal(symbol)) {
            whoWonMessage(player);

            return won = true;
        }

        return won = false;
    }

    private void whoWonMessage(String player) {
        System.out.println(player + " hat gewonnen!");
    }

    private void indecisiveMessage() {
        System.out.println("Unentschieden");
    }

    private boolean checkColumn(int symbol) {
        for (int i = 0; i < 3; i++) {
            if (field[i] == field[i + 3] && field[i + 3] == field[i + 6] && field[i] == symbol) {
                return true;
            }
        }

        return false;
    }

    private boolean checkDiagonal(int symbol) {
        if (field[0] == field[4] && field[4] == field[8] && field[8] == symbol) {
            return true;
        }

        return field[2] == field[4] && field[4] == field[6] && field[6] == symbol;
    }

    private boolean checkRow(int symbol) {
        for (int i = 0; i < 3; i++) {
            if (field[i * 3] == symbol && field[1 + i * 3] == symbol && field[2 + i * 3] == symbol) {
                return true;
            }
        }

        return false;
    }

    /**
     * Displays the field in a 3x3 grid
     */
    private void displayField() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(Arrays.copyOfRange(field, i * 3, i * 3 + 3)));
        }
    }

    private void systemMove() {
        boolean next = true;
        int systemField = 0;

        // as long as the system wants to put a symbol in a place where it doesn't work, a new number is generated for the place
        while (next) {
            systemField = random.nextInt(8);
            next = !setSymbol(SYMBOL_O, systemField);
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }

        try {
            Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    private void playerMove() {
        boolean next = true;
        String input;

        // as long as the player wants to put a symbol in a place where it doesn't work, the player is asked again
        while (next) {
            System.out.println("Wo soll dein X (im Raster die 2) hin? : ");
            input = scanner.next();

            if (isNumeric(input)) {
                next = !setSymbol(SYMBOL_X, Integer.parseInt(input) - 1);
            } else {
                System.out.println("Bitte gib nur Zahlen von 1 bis 9 ein\n");
            }

        }
    }

    public void play() {
        while (!won) {
            displayField();
            playerMove();
            // If the symbol from the player could be set and the game is not yet over, the system should set a symbol
            if (!hasWon(SYMBOL_X, "Du") && !gameEnd()) {
                systemMove();
                hasWon(SYMBOL_O, "System");
            }
        }
    }
}