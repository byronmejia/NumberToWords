package web;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byron on 16/06/2016.
 */
public class NumberToWord {
    private double number;
    private String userInput;
    private List<String> constructionWord;

    NumberToWord(String word) throws NumberToWordException {
        // Number cannot be null
        if(word == null) throw new NumberToWordException("Number cannot be null");
        // Use built in double parse library
        this.number = Double.parseDouble(word);
        // Save a copy of original string
        this.userInput = word;
        // Instantiate a string array list
        constructionWord = new ArrayList<String>();
    }

    public String toString() {
        // If zero, just go straight to the zeroes
        if(this.number == 0.00){
            this.constructionWord.add(NumberConstants.NUMBERS[0]);
            return constructString("");
        }


        return "Nothing for now";
    }

    private String constructString(String solution) {
        String tempString = this.constructionWord.get(0);
        this.constructionWord.remove(0);
        System.out.println(solution + tempString);
        if(constructionWord.isEmpty()){
            return solution + tempString;
        } else {
            return constructString(solution + tempString);
        }

    }
}
