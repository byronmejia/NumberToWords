package web;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byron on 16/06/2016.
 */
public class NumberToWord {
    private double number;
    private double numberTemp;
    private String userInput;
    private List<String> constructionWord;

    NumberToWord(String word) throws NumberToWordException {
        // Number cannot be null
        if (word == null) throw new NumberToWordException("Number cannot be null");
        // Use built in double parse library
        this.number = Double.parseDouble(word);
        // Save a copy of original string
        this.userInput = word;
        // Instantiate a string array list
        constructionWord = new ArrayList<String>();
    }

    public String toString() {
        int decimalIndex = 0;
        int size;

        for (int i = 0; i < userInput.length(); i++) {
            constructionWord.add(Character.toString(userInput.charAt(i)));
        }

        // Replace decimal with and, if possible
        if (constructionWord.contains(".")) {
            decimalIndex = constructionWord.indexOf(".");
            constructionWord.set(decimalIndex, "and");
        } else {
            decimalIndex = constructionWord.size();
        }

        size = constructionWord.size();

        if (size - (size - decimalIndex) >= 3) {
            for(int i = decimalIndex - 3; i >= 0; i -= 3){
                if(i < 0) break;
                int replacement = i;
                String tempString = constructionWord.get(replacement);
                if(!tempString.equals("0")) constructionWord.set(
                        replacement,
                        NUMBERCONSTANTS.NUMBERS[Integer.parseInt(tempString)] +
                                " " +
                                NUMBERCONSTANTS.PREDECIMAL[0]
                );

                boolean teenFlag = true;

                replacement += 1;
                String tempString0 = constructionWord.get(replacement);
                if(!tempString0.equals("0") && !tempString0.equals("1")) {
                    constructionWord.set(
                            replacement,
                            NUMBERCONSTANTS.TENS[Integer.parseInt(tempString0) - 1]
                    );
                    teenFlag = false;
                }
                replacement += 1;

                if(!teenFlag){
                    String tempString1 = constructionWord.get(replacement);
                    if(!tempString0.equals("0")) constructionWord.set(
                            replacement,
                            NUMBERCONSTANTS.NUMBERS[Integer.parseInt(tempString1)]
                    );
                }

            }
        }

        return constructString("");
    }

    private String constructString(String solution) {
        String tempString = this.constructionWord.get(0);
        this.constructionWord.remove(0);
        if (constructionWord.isEmpty()){
            return solution + tempString;
        } else {
            return constructString(solution + tempString + " ");
        }
    }
}
