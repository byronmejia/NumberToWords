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
        if(this.number == 0.00) {
            this.constructionWord.add(NUMBERCONSTANTS.NUMBERS[0]);
            return constructString("");
        } else if(this.number < 0.00){
            this.constructionWord.add("Negative");
            return toString(Math.abs((int)this.number));
        } else {
            return toString((int)this.number);
        }
    }

    private String toString(int temp){
        if((temp/NUMBERCONSTANTS.PREDECIMAL_INT[3]) > 0){
            this.constructionWord.add(
                    extractString(
                            (temp/NUMBERCONSTANTS.PREDECIMAL_INT[3])
                    )
            );
            this.constructionWord.add(NUMBERCONSTANTS.PREDECIMAL[3]);
            return toString(temp/NUMBERCONSTANTS.PREDECIMAL_INT[3]);
        }

        if((temp/NUMBERCONSTANTS.PREDECIMAL_INT[2]) > 0){
            this.constructionWord.add(
                    extractString(
                            (temp/NUMBERCONSTANTS.PREDECIMAL_INT[2])
                    )
            );
            this.constructionWord.add(NUMBERCONSTANTS.PREDECIMAL[2]);
            return toString(temp/NUMBERCONSTANTS.PREDECIMAL_INT[2]);
        }

        if((temp/NUMBERCONSTANTS.PREDECIMAL_INT[1]) > 0){
            this.constructionWord.add(
                    extractString(
                            (temp/NUMBERCONSTANTS.PREDECIMAL_INT[1])
                    )
            );
            this.constructionWord.add(NUMBERCONSTANTS.PREDECIMAL[1]);
            return toString(temp/NUMBERCONSTANTS.PREDECIMAL_INT[1]);
        }

        if((temp/NUMBERCONSTANTS.PREDECIMAL_INT[0]) > 0){
            this.constructionWord.add(
                    extractString(
                            (temp/NUMBERCONSTANTS.PREDECIMAL_INT[0])
                    )
            );
            this.constructionWord.add(NUMBERCONSTANTS.PREDECIMAL[0]);
            return toString(temp/NUMBERCONSTANTS.PREDECIMAL_INT[0]);
        }

        this.constructionWord.add(
                extractString(
                        (temp)
                )
        );

        return constructString("");
    }

    private String extractString(int temp){
        if(temp < 10){
            return NUMBERCONSTANTS.NUMBERS[(int)temp] + " ";
        } else if(temp < 20) {
            return NUMBERCONSTANTS.TEENS[temp - 10] + " ";
        } else {
            String string = NUMBERCONSTANTS.TENS[temp/10];
            int tempUnit = temp % 10;
            if((temp % 10) > 0) {
                return string + "-" + NUMBERCONSTANTS.NUMBERS[tempUnit] + " ";
            } else {
                return string + " ";
            }
        }
    }

    private String constructString(String solution) {
        System.out.println(this.constructionWord.toString());
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
