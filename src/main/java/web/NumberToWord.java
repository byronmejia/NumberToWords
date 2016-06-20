package web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: Add class documentation
class NumberToWord {
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

    String niceString() throws NumberToWordException {
        int decimalIndex = 0;
        int size;

        // Setup the constructionWord list
        constructionWord.clear();
        userInputToList();

        // Replace decimal with and, if possible
        decimalIndex = replaceDecimal(decimalIndex);

        size = constructionWord.size();

        if (size - (size - decimalIndex) >= 3) {
            for(int i = decimalIndex - 3; i >= 0; i -= 3){
                // We hit a bad place, break out!
                if(i < 0) break;

                // Replacement counting flag
                int replacement = i;
                // Flag for numbers that are not 'normal'
                boolean teenFlag = true;

                // Did we hit a 'hundred' number? Let's replace it.
                String tempString = constructionWord.get(replacement);
                if(!tempString.equals("0")) constructionWord.set(
                        replacement,
                        NUMBERCONSTANTS.NUMBERS[Integer.parseInt(tempString)]
                                + " "
                                + NUMBERCONSTANTS.PREDECIMAL[0]
                                + " AND "
                );

                replacement += 1;

                // Did we hit a 'normal' number? Let's the 'tens' column
                String tempString0 = constructionWord.get(replacement);
                if(!tempString0.equals("0") && !tempString0.equals("1")) {
                    constructionWord.set(
                            replacement,
                            NUMBERCONSTANTS.TENS[Integer.parseInt(tempString0) - 1]
                    );
                    teenFlag = false;
                }
                replacement += 1;

                // Did we hit a normal number? Let's replace the 'ones' column
                if(!teenFlag){
                    String tempString1 = constructionWord.get(replacement);
                    if(!tempString1.equals("0"))
                        constructionWord.set(
                            replacement,
                            NUMBERCONSTANTS.NUMBERS[Integer.parseInt(tempString1)]
                        );
                    else
                        constructionWord.set(
                                replacement,
                                ""
                        );

                }
                // Else, we must deal with these pesky teenagers and pre-teens
                else {
                    // Rewind time back to checking for normal numbers
                    int tempReplacement = replacement - 1;

                    // Dirty integer constructor
                    int teenInteger = Integer.parseInt(
                            constructionWord.get(tempReplacement)
                                    + constructionWord.get(tempReplacement + 1)
                    );

                    // If a teen number, construct 'teen'
                    if (teenInteger >= 10) {
                        constructionWord.set(tempReplacement, NUMBERCONSTANTS.TEENS[teenInteger - 10]);
                        constructionWord.set(tempReplacement + 1, "");
                    }
                    // Else, construct small number
                    else if (teenInteger > 0){
                        constructionWord.set(tempReplacement, "");
                        constructionWord.set(tempReplacement, NUMBERCONSTANTS.NUMBERS[teenInteger]);
                    } else {
                        constructionWord.set(tempReplacement, "");
                        constructionWord.set(tempReplacement + 1, "");
                    }
                }
            }

            solveEdgeCase(size - (size - decimalIndex));

            for (int i = 1, j = 4; i < NUMBERCONSTANTS.PREDECIMAL.length; i++, j += 3){
                editPreDecimal(size, decimalIndex, j, i);
            }

        }

        return constructString("");
    }

    private boolean editPreDecimal(int size, int decimalIndex, int placement, int preDecimalKey) {
        if (size - (size - decimalIndex) >= placement) {
            int replacement = decimalIndex - placement;

            String tempString = constructionWord.get(replacement);
            String tempString1;

            if ((replacement - 1) < 0) {
                tempString1 = "";
            } else {
                tempString1 = constructionWord.get(replacement - 1);
            }

            List<String> teenAsList = Arrays.asList(NUMBERCONSTANTS.TEENS);

            boolean shouldCheck = ( !tempString.contains("0") && !tempString.isEmpty() ) || teenAsList.contains(tempString1);

            if ( shouldCheck ) {
                constructionWord.set(
                        replacement,
                        tempString
                                + " "
                                + NUMBERCONSTANTS.PREDECIMAL[preDecimalKey]
                                + ","
                );
            }
        }
        return false;
    }

    private int replaceDecimal(int decimalIndex) {
        if (constructionWord.contains(".")) {
            decimalIndex = constructionWord.indexOf(".");
            constructionWord.set(decimalIndex, "AND");
            return decimalIndex;
        } else {
            decimalIndex = constructionWord.size();
            return decimalIndex;
        }
    }

    private boolean solveEdgeCase(int size) throws NumberToWordException {
        int remainder = size % 3;
        switch(remainder){
            case 0:
                return false;
            case 1:
                // Do something
                int digit = Integer.parseInt(constructionWord.get(0));
                if (digit <= 0) {
                    constructionWord.set(0, "");
                    return false;
                }
                String temp = NUMBERCONSTANTS.NUMBERS[digit];
                constructionWord.set(0, temp);

                return true;
            case 2:
                int digit0 = Integer.parseInt(constructionWord.get(0) + constructionWord.get(1));

                if (digit0 <= 0) {
                    constructionWord.set(0, "");
                    constructionWord.set(1, "");
                } else if (digit0 <= 9) {
                    constructionWord.set(0, "");
                    constructionWord.set(1, NUMBERCONSTANTS.NUMBERS[digit0]);
                } else if (digit0 <= 19) {
                    constructionWord.set(0, "");
                    constructionWord.set(1, NUMBERCONSTANTS.TEENS[digit0 - 10]);
                } else {
                    int tens = digit0 / 10;
                    int number = digit0 - (tens * 10);
                    constructionWord.set(0, NUMBERCONSTANTS.TENS[tens - 1]);
                    constructionWord.set(1, NUMBERCONSTANTS.NUMBERS[number]);
                }

                return true;
        }
        throw new NumberToWordException("Reached Impossible Code: solveEdgeCase remainder should never be more than 2");
    }

    private boolean userInputToList() throws NumberToWordException {
        if (constructionWord.isEmpty()) {
            for (int i = 0; i < userInput.length(); i++) {
                constructionWord.add(Character.toString(userInput.charAt(i)));
            }
            return true;
        } else {
            throw new NumberToWordException("Never convert userInput to the constructionWord WITHOUT clearing first!");
        }
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
