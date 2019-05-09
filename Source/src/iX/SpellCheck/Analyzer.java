package iX.SpellCheck;

import java.io.IOException;
import java.util.*;

/**
 * URL:
 * http://app.aspell.net/create?max_size=95&spelling=US&spelling=GBs&spelling=GBz&spelling=CA&spelling=AU&max_variant=0&diacritic=both&special=hacker&special=roman-numerals&download=wordlist&encoding=utf-8&format=inline
 *
 * @author Aaron
 * text analyzer that will guess user input based on incorrectly spelled words.
 *
 * algorithm:
 * 1) based on first letter and last letter
 * 2) percent similarity (50% > to be accepted)
 * 3) based on # of syllables
 *
 * TESTING PURPOSES:
 * pygeun
 * Pigeon
 *
 * epephany
 * Epiphany
 *
 * TODO:
 * - add a graphical user interface (incorporate javaFX because it's easier to use?)
 * - another additional method to use could be similar pronunciations s such as 'k' instead of 'c' or such. (more than just one letter differences)
 * - update it so you can use sentences. perhaps, multiple options of the sentence
 * - Make it so it will show suggestions for a word even if it is correctly spelled
 * - Search for correctly spelled words inside a misspelled word
 */


public class Analyzer {
    private String userInput;
    private List<Word> predictedInput;
    private Organizer organizerInstance;
    private String finalOutput;

    /**
     * This is the main method that does everything.
     * Goes through and calls the methods that get the word list of best predictions and displays the inputted word along with its suggestions.
     * @param organizer
     * @throws IOException
     */
    public void start(Organizer organizer) throws IOException {
        organizerInstance = organizer;
        ArrayList<String> wordsInSentence = separateWordsInSentence(userInput);
        for(int i = 0; i <= wordsInSentence.size() - 1; i++) {
            if (!wordsInSentence.get(i).isEmpty()) {
                predictedInput = getWordList(wordsInSentence.get(i));
                display(wordsInSentence.get(i)); // console output
            }
        }
    }

    public void setInput(String s) {
        userInput = s;
    }

    private void display(String input) {
        System.out.println();
        if (isDuplicate(input, predictedInput)) {
            System.out.println("Correctly spelled word.");
        }
        System.out.println("Input: " + input);
        System.out.println("Predictions: " + getTopWords(3, predictedInput));
    }

    public String getPredictedInput() {
        return getTopWords(3, predictedInput);
    }

    public String getFinalOutput() {
        return finalOutput;
    }

    /**
     * Gets the top predicted words based on the organizer class
     * @param num
     * @param words
     * @return string of the top (num) words of the list
     */
    private String getTopWords(int num, List<Word> words) {
        String list = "";
        int i = 1;
        while((i <= num) && (words.size() != i)) {
            list += words.get(i).getWord() + (i != num ? ", " : ".");
            i += 1;
        }
        finalOutput += list;
        return list;
    }

    /**
     * Checks to see if the user inputted word is one of the predicted words
     * @param userInput
     * @param predicted the list of predicted words
     * @return whether the user input is one of the predicted words
     */
    private boolean isDuplicate(String userInput, List<Word> predicted) {
        int i = 0;
        for (Word word : predicted) {
            if (word.getWord().equalsIgnoreCase(userInput)) {
                predictedInput.remove(i);
                return true;
            }
            i++;
        }
        return false;
    }

    public void setOrganizer(Organizer org) {
    	organizerInstance = org;
    }
    /**
     * Gets the list of predicted words based on the input
     * @param inputWord
     * @return list of words based upon percentage similarity, syllables, and terminal letters
     */
    public List<Word> getWordList(String inputWord) {
        List<Word> wordGroup = organizerInstance.getGroupBasedOnPercentage(inputWord, organizerInstance.getGroupBySyllables(inputWord, organizerInstance.getGroupBasedOnTerminalLetter(inputWord)));
        Collections.sort(wordGroup, new SimilarityComparator()); // sorts the list
        Collections.reverse(wordGroup); // reverses order so the first object is closest
        return wordGroup;
    }

    /**
     * Takes out each individual word from the user input to be checked separately
     * @param userInput
     * @return returns each word from the sentence
     */
    private static ArrayList<String> separateWordsInSentence(String userInput){
        Scanner breakApart = new Scanner(userInput);
        ArrayList<String> words = new ArrayList<String>();
        while(breakApart.hasNext()){
            words.add(breakApart.next().toLowerCase());
        }
        return words;
    }
}