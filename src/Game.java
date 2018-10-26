import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class represents a movie title guessing game similar to Hangman.
 */
public class Game {

    private ArrayList<String> movieTitlesList= new ArrayList<>();
    private String movieTitle;
    private String guessedMovieTitle;
    private ArrayList<String> wrongLetters = new ArrayList<>();
    private boolean hasWon = false;
    private int wrongGuesses = 0;

    public Game() {
        File file = new File("movies.txt");
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String currentTitle = fileScanner.nextLine();
                movieTitlesList.add(currentTitle);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error opening file. File name not found.");
        }
    }

    public void generateRandomTitle () {
        Random random = new Random();
        int upperLimit = movieTitlesList.size();

        int randomNumber = random.nextInt(upperLimit);

        movieTitle = movieTitlesList.get(randomNumber);

        convertTitleToUnderscores();
    }

    private void convertTitleToUnderscores () {

        StringBuilder guessMovieTitle = new StringBuilder();

        for (int i = 0; i < movieTitle.length(); i++) {
            char currentChar = movieTitle.charAt(i);

            if (currentChar != ' ') {
                guessMovieTitle.append('-');
            } else {
                guessMovieTitle.append(' ');
            }
        }
        guessedMovieTitle = guessMovieTitle.toString();

        System.out.println("You are guessing: " + guessedMovieTitle + "\nYou have 10 chances to guess the movie!");
    }

    public void checkLetter (String letter) {
        // Check if this hasn't been used before and counted as a wrong guess.
        // If this is true, proceed to check if guess is correct.
        if (wrongLetters.indexOf(letter) == -1) {

            String movieTitleLowerCase = movieTitle.toLowerCase();

            StringBuilder guessedMovieTitleBuilder = new StringBuilder(guessedMovieTitle);

            // check if this guess is correct by checking if this movie title contains this letter
            if (movieTitleLowerCase.contains(letter)) {

                int index = movieTitleLowerCase.indexOf(letter);

                guessedMovieTitleBuilder.setCharAt(index, letter.charAt(0));

                while (index >= 0) {
                    index = movieTitleLowerCase.indexOf(letter, index + 1);

                    if (index != -1) {
                        guessedMovieTitleBuilder.setCharAt(index, letter.charAt(0));
                    }
                }
                guessedMovieTitle = guessedMovieTitleBuilder.toString();

                // check if player won by checking if hidden movie title doesn't contain any more dashes
                if (!guessedMovieTitle.contains("-")) {
                    // Player has won
                    hasWon = true;
                    System.out.println("You won!");
                    System.out.println("You guessed \"" + guessedMovieTitle + "\" correctly.");
                } else {
                    // It's only a correct guess but player still hasn't won
                    System.out.println("Correct guess. You are guessing: " + guessedMovieTitle);
                    System.out.println("Number of wrong guesses: " + wrongGuesses + ". Wrong letters: " + formatWrongLetters());
                }
            } else {
                // this guess is incorrect, increment wrong guesses and keep track of wrong letter
                wrongGuesses ++;
                wrongLetters.add(letter);
                System.out.println("Incorrect guess. You are guessing: " + guessedMovieTitle);
                System.out.println("Number of wrong guesses: " + wrongGuesses + ". Wrong letters: " + formatWrongLetters());
            }
        } else {
            // Since this letter was already tested and proven to be a wrong guess, skip the whole the checking process
            System.out.println("This letter was already used. You are guessing: " + guessedMovieTitle);
            System.out.println("Number of wrong guesses: " + wrongGuesses + ". Wrong letters: " + formatWrongLetters());
        }
    }

    private String formatWrongLetters () {
        String wrongLettersList = wrongLetters.toString();
        return wrongLettersList.substring(1, wrongLettersList.length() - 1);

    }

    public boolean hasWon () {
        return hasWon;
    }

    public int getWrongGuesses () {
        return wrongGuesses;
    }
}
