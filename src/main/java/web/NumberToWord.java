package web;

import java.util.ArrayList;
import java.util.List;

/**
 * This class specifically for converting numbers into 'words'. Uses built in java Double parser for throwing
 * exceptions. Uses ArrayLists of Strings over a string builder, for no particularly good reason at all.
 *
 * @author byron
 * @see Double
 * @see ArrayList
 * @see List
 */
class NumberToWord {
    private String userInput;
    private String preDecimal;
    private String postDecimal;
    private int decimalIndex;
    private int userInputSize;
    private int preDecimalSize;
    private boolean negative = false;

    /**
     * Constructor method used to test the given number for multiple conditions before saving it to the class
     * parameters.
     *
     * <p>This constructor will test for negative numbers, by checking if the first char is a dash ('-')</p>
     * @param word The given string from the user
     * @throws NumberToWordException if word given is null (as this will result in segmentation faults).
     *
     */
    NumberToWord(String word) throws NumberToWordException {
        // Null Tester - Without we get stuck in process loops
        if (word == null) throw new NumberToWordException("Number cannot be null");

        // Check if we are dealing with a negative
        if (word.charAt(0) == '-') {
            this.negative = true;
            word = word.substring(1);
        }
        stringTester(word);
        wordSaver(word);
        subStringSaver(word);
        subStringTester();
    }
/* --------------------------------------- END Constructor --------------------------------------------------------- */

    /**
     *
     * @param word a given string to test
     * @throws NumberFormatException if the number does not follow Double.parseDouble()
     * @throws NumberToWordException if the number contains an 'f', which Double.parseDouble() will miss
     * @see Double
     */
    private void stringTester(String word) throws NumberToWordException {
        // Use built in double parse library, and throw errors
        double doubleTest = Double.parseDouble(word);

        // Number cannot include 'f' (mistaken for hex?)
        if (word.contains("f")) throw new NumberToWordException("Number cannot contain hexadecimal values " +
                "(assuming decimal notation)");

    }

    /**
     * Saves the given word to the class parameter, and the decimal index.
     *
     * @param word a given string to save
     */
    private void wordSaver(String word) {
        // Save a copy of the word size
        this.userInputSize = word.length();

        // Save a copy of original string
        this.userInput = word;

        // Save location of decimal place
        if (word.contains(".")) this.decimalIndex = word.indexOf('.');
        else this.decimalIndex = -1;

    }

    /**
     * This method saves the given word into substrings of Pre and Post decimal numbers.
     *
     * @param word the word to manipulate and save into parameters
     * @throws NumberToWordException if attempting to save a subString before saving the userInput parameter
     */
    private void subStringSaver(String word) throws NumberToWordException {
        // Throws error if being used too early
        if (this.userInput == null) throw new NumberToWordException("Cannot place subStringSaver before wordSaver");

        // Save a copy of preDecimal string
        // && Save a copy of postDecimal string
        switch (this.decimalIndex) {
            case -1:
                this.preDecimal = divisibleByThreePreSpacer(word);
                this.postDecimal = null;
                break;
            case 0:
                this.preDecimal = null;
                this.postDecimal = divisibleByThreePostSpacer(word.substring(1));
                break;
            default:
                this.preDecimal = divisibleByThreePreSpacer(word.substring(0, decimalIndex));
                if (this.userInputSize == decimalIndex + 1) this.postDecimal = null;
                else this.postDecimal = divisibleByThreePostSpacer(word.substring(decimalIndex + 1));
                break;
        }

        // Save a copy of preDecimal substring lengths
        if (this.preDecimal != null) this.preDecimalSize = this.preDecimal.length();
        else this.preDecimalSize = -1;

    }

    /**
     * Method tests to see that the subStrings are within reasonable numbers, following the Wikipedia apparent standard
     * english standards of large and small numbers.
     * @throws NumberToWordException if number is not within wikipedia standard english dictionary decimal numbers
     */
    private void subStringTester() throws NumberToWordException {
        if (this.preDecimal != null)
            if (this.preDecimal.length() > 66) throw new NumberToWordException("PreDecimal exceeds VIGINTILLION " +
                    "(66 significant figures before decimal)");

        if (this.postDecimal != null) {
            if (this.postDecimal.length() > 3) throw new NumberToWordException("PostDecimal exceeds Cents Limit " +
                    "(2 significant figures after decimal)");
            if (this.postDecimal.charAt(0) != '0') throw new NumberToWordException("PostDecimal exceeds Cents Limit " +
                    "(2 significant figures after decimal)");
        }
    }

    /**
     * Converts the given word to a word that's divisble by 3, due to how numbers are pronounced.
     * @param word places 'zero' buffers to round the number into a group of 3 (due to nature of numbers)
     * @return the new number in multiples of 3
     */
    private String divisibleByThreePreSpacer(String word) {
        int remainder = word.length() % 3;
        if (remainder > 0) return divisibleByThreePreSpacer("0" + word);
        return word;
    }

    private String divisibleByThreePostSpacer(String word) {
        if (word.length() == 1) return divisibleByThreePreSpacer(word + "0");
        return divisibleByThreePreSpacer(word);
    }

/* ----------------------------------------- END Constructor helper methods ----------------------------------------- */

    /**
     * Generates a nice english representation of the word.
     * @return the word representation
     */
    String niceString() {
        if (Double.parseDouble(this.userInput) == 0) {
            return "ZERO DOLLARS";
        }
        // Generate Pre-Decimal Numbers
        List<String> preDecimalBuffer = new ArrayList<>();
        String preDecimalString;
        for (int i = 0; i < this.preDecimalSize; i += 3) {
            preDecimalBuffer.add(threeDigitParse(i, this.preDecimal));
        }
        preDecimalBuffer = suffixify(preDecimalBuffer);
        preDecimalString = textify(preDecimalBuffer);

        // Should we add dollars?
        if (!preDecimalString.isEmpty()) {
            // Did we just hit ONE?
            if (preDecimalString.equals("ONE")) preDecimalString = preDecimalString + " DOLLAR ";
            else preDecimalString = preDecimalString + " DOLLARS ";
        }

        // Generate Post-Decimal Numbers
        List<String> postDecimalBuffer = new ArrayList<>();
        String postDecimalString = "";
        if (this.decimalIndex != -1 && this.decimalIndex != this.userInputSize - 1) {
            postDecimalBuffer.add(threeDigitParse(0, this.postDecimal));
            postDecimalBuffer = suffixify(postDecimalBuffer);
            postDecimalString = textify(postDecimalBuffer);
        }

        // Should we add cents?
        if (!postDecimalString.isEmpty()) {
            // Did we just hit ONE?
            if (postDecimalString.equals("ONE")) postDecimalString = postDecimalString + " CENT ";
            else postDecimalString = postDecimalString + " CENTS ";
        }

        String finalString = "";
        if (this.decimalIndex == -1 || this.decimalIndex == this.userInputSize - 1) {
            finalString = preDecimalString;
        } else if (preDecimalString.isEmpty()) {
            finalString = (postDecimalString).trim();
        } else {
            finalString = (preDecimalString + " AND " + postDecimalString).trim();
        }

        // Test for if negative number, and return
        if (this.negative) {
            return ("NEGATIVE " + finalString).trim();
        } else {
            return finalString.trim();
        }
    }

    /**
     * builds a string from the given list, appropiately placing " AND " where appropiate
     * @param preDecimalBuffer
     * @return the build number in words
     */
    private String textify(List<String> preDecimalBuffer) {
        StringBuilder builder = new StringBuilder();
        int size = preDecimalBuffer.size();
        for (int i = 0; i < size; i++) {
            if (i != size - 2 && i != 0 && !preDecimalBuffer.get(i).contains("HUNDRED")) {
                builder.append(" AND ");
            }

            builder.append(preDecimalBuffer.get(i));

            if (i != size - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

    /**
     * Adds the predecimal constants (MILLION, BILLION, -ILLION...) to the list of words where appropriate.
     * @param preDecimalBuffer
     * @return
     */
    private List<String> suffixify(List<String> preDecimalBuffer) {
        for (int i = preDecimalBuffer.size() - 1, j = 0; i >= 0; i--, j++) {
            String temp = preDecimalBuffer.get(i);
            if (temp != "ZERO") {
                if (j != 0) preDecimalBuffer.set(i, temp + " " + NUMBERCONSTANTS.PREDECIMAL[j]);
            } else {
                preDecimalBuffer.remove(i);
            }
        }

        return preDecimalBuffer;
    }

    /**
     * Parses the digit, assuming 3 significant figures
     * @param index the starting significant figure
     * @param string the string to parse for a number
     * @return a three sig figure number (up to hundreds)
     */
    private String threeDigitParse(int index, String string) {
        int number = Integer.parseInt(string.substring(index, index + 3));
        if (number > 99) {
            String temp0 = NUMBERCONSTANTS.NUMBERS[number / 100]
                    + " "
                    + NUMBERCONSTANTS.PREDECIMAL[0];
            String temp1 = twoDigitParseInt(number - (number / 100 * 100));

            if (temp1.equals("ZERO")) {
                return temp0;
            } else {
                return temp0 + " AND " + temp1;
            }
        } else {
            return twoDigitParseInt(number);
        }
    }

    /**
     * Runs like the three digit parser, however, creates the word representation from an
     * integer that is less than one hundred.
     * @param number the number to parse
     * @return the string representation of a two digit number
     */
    private String twoDigitParseInt(int number) {
        if (number < 10) {
            return NUMBERCONSTANTS.NUMBERS[number];
        } else if (number < 20) {
            return NUMBERCONSTANTS.TEENS[number - 10];
        } else {
            String tempFirst = NUMBERCONSTANTS.TENS[number / 10 - 1];
            String tempSecond = NUMBERCONSTANTS.NUMBERS[number - (number / 10 * 10)];

            if (tempSecond.equals("ZERO")) {
                return tempFirst;
            } else {
                return tempFirst + "-" + tempSecond;
            }
        }
    }
}
