import java.util.Scanner;

public class GameDemo {

    public static void main(String[] args) {

        // Generate new game and random movie title
        Game game = new Game();
        game.generateRandomTitle();

        Scanner scanner = new Scanner(System.in);

        // Always ask for input and check it for one time, then keep asking for input while the limit of 10 wrong guesses
        // hasn't been reached and the player hasn't won.
        do {
            System.out.println("\nGuess a letter: ");
            String s = scanner.next().toLowerCase();
            game.checkLetter(s);

            if (game.getWrongGuesses() == 10) {
                System.out.println("You reached the maximum number of wrong guesses, you lost.");
            }
        } while (game.getWrongGuesses() < 10 && !game.hasWon());
    }
}