package web;

import java.util.ArrayList;
import java.util.List;

// TODO: Add class documentation
class NumberToWord {
    private String userInput;
    private String preDecimal;
    private String postDecimal;
    private int decimalIndex;
    private int userInputSize;
    private int preDecimalSize;
    private boolean negative = false;

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

    private void stringTester(String word) throws NumberToWordException {
        // Number cannot be null

        // Use built in double parse library, and throw errors
        double doubleTest = Double.parseDouble(word);

        // Number cannot include 'f' (mistaken for hex?)
        if (word.contains("f")) throw new NumberToWordException("Number cannot contain hexadecimal values " +
                "(assuming decimal notation)");

    }

    private void wordSaver(String word) throws NumberToWordException {
        // Save a copy of the word size
        this.userInputSize = word.length();

        // Save a copy of original string
        this.userInput = word;

        // Save location of decimal place
        if (word.contains(".")) this.decimalIndex = word.indexOf('.');
        else this.decimalIndex = -1;

    }

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

    String niceString() throws NumberToWordException {
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

    private String threeDigitParse(int index, String string) throws NumberToWordException {
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
