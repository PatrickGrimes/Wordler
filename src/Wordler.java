import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that will help you cheat at Wordle, if you are that type of person.
 */
public class Wordler {

  // -------------
  // Type all letters that you have tried so far:
  static final String LETTERS_GUESSED = "mendspolka".toLowerCase();
  // Type any characters that are in this Wordle
  static final String LETTERS_IN_WORD = "mends".toLowerCase();
  // Type the pattern you are trying to solve.
  //     e.g.  "s_e_t"
  static final char[] GUESS_PATTERN = "me_ds".toLowerCase().toCharArray();
  // -------------

  static final int WORDLE_LENGTH = GUESS_PATTERN.length;
  static final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
  static ArrayList<String> wordsInDictionary = new ArrayList<>();

  /**
   * The startup method for our Wordler.
   *
   * @param args a String array
   */
  public static void main(String[] args) {
    // Load in the dictionary
    try {
      String userDir = System.getProperty("user.dir");
      Scanner s = new Scanner(new File(userDir + "/src/dic.txt"));
      while (s.hasNext()) {
        wordsInDictionary.add(s.next());
      }
      s.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("Possible solutions to your Wordle:");
    doThatWordleThing("");
  }

  /**
   * A method used to print out the possible solutions to the Wordle.
   *
   * @param potentialWord a String representing one of the possible Wordle solution words.
   */
  private static void printIfInDictionary(String potentialWord) {
    // is it in the dictionary?
    if (wordsInDictionary.contains(potentialWord)) {
      // does it have the letters included?
      for (char letter : LETTERS_IN_WORD.toCharArray()) {
        if (potentialWord.indexOf(letter) < 0) {
          return;
        }
      }
      System.out.println(" - " + potentialWord);
    }
  }

  /**
   * A recursive method used to derive possible words to check against the dictionary and the
   * pattern provided by the user.
   *
   * @param currentString a String representing a word being built up.
   */
  private static void doThatWordleThing(String currentString) {

    // We have all the letters - let's print it! (maybe)
    if (WORDLE_LENGTH == currentString.length()) {
      printIfInDictionary(currentString);
      return;
    }

    if (GUESS_PATTERN[currentString.length()] != '_') {
      // It's the actual letter, so we want that.
      currentString += GUESS_PATTERN[currentString.length()];
      doThatWordleThing(currentString);
    } else {
      // We don't know what letter this is, so we'll loop through them
      for (char alpha : alphabet) {
        if (!LETTERS_IN_WORD.contains(alpha + "")) {
          if (LETTERS_GUESSED.contains(alpha + "")) {
            continue;
          }
        }
        currentString += alpha;
        doThatWordleThing(currentString);
        currentString = currentString.substring(0, currentString.length() - 1);
      }
    }
  }
}
