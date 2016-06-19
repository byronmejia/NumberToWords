package web;

/**
 * This class consists of only constants, that return important strings necessary for mapping integers to
 * words.
 *
 * @author Byron Mejia
 * @version 1.0
 */
class NUMBERCONSTANTS {
    static final String[] NUMBERS = {
            "ZERO",
            "ONE",
            "TWO",
            "THREE",
            "FOUR",
            "FIVE",
            "SIX",
            "SEVEN",
            "EIGHT",
            "NINE"
    };

    static final String[] TEENS = {
            "TEN",
            "ELEVEN",
            "TWELVE",
            "THIRTEEN",
            "FOURTEEN",
            "FIFTEEN",
            "SIXTEEN",
            "SEVENTEEN",
            "EIGHTEEN",
            "NINETEEN"
    };

    static final String [] TENS = {
            "TEN",
            "TWENTY",
            "THIRTY",
            "FORTY",
            "FIFTY",
            "SIXTY",
            "SEVENTY",
            "EIGHTY",
            "NINETY",
    };

    static final String[] PREDECIMAL = {
            "HUNDRED",
            "THOUSAND",
            "MILLION",
            "BILLION"
    };

    static final int[] PREDECIMAL_INT = {
            100,
            1000,
            1000000,
            1000000000
    };
}