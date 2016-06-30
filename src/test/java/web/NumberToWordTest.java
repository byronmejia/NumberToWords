package web;

import org.junit.Test;

import static org.junit.Assert.*;

public class NumberToWordTest {

    @Test
    public void sanity() throws Exception {
        assertEquals(true, true);
    }

    @Test(expected = NumberFormatException.class)
    public void constructorBadStringNoDigits() throws Exception {
        String testString = "Lemon";
        new NumberToWord(testString);
    }

    @Test(expected = NumberFormatException.class)
    public void constructorBadManyDecimals() throws Exception {
        String testString = "1.1.1";
        new NumberToWord(testString);
    }

    @Test(expected = NumberToWordException.class)
    public void constructorBadStringNull() throws Exception {
        new NumberToWord(null);
    }

    @Test(expected = NumberFormatException.class)
    public void constructorBadStringNumberMix() throws Exception {
        String testString = "123fmm";
        new NumberToWord(testString);
    }

    @Test(expected = NumberToWordException.class)
    public void constructorBadStringNumberMix2() throws Exception {
        String testString = "123f";
        new NumberToWord(testString);
    }

    @Test
    public void niceStringZero0() throws Exception {
        String testString = "0";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ZERO DOLLARS"));
    }

    @Test
    public void niceStringZero1() throws Exception {
        String testString = "0.0";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ZERO DOLLARS"));
    }

    @Test
    public void niceStringZero2() throws Exception {
        String testString = "00";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ZERO DOLLARS"));
    }

    @Test
    public void niceStringZero3() throws Exception {
        String testString = "0.00";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ZERO DOLLARS"));
    }

    @Test
    public void niceStringZero4() throws Exception {
        String testString = "-0";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ZERO DOLLARS"));
    }

    @Test
    public void niceStringNegativeOne() throws Exception {
        String testString = "-1";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("NEGATIVE ONE DOLLAR"));
    }

    @Test
    public void niceStringOne() throws Exception {
        String testString = "1";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ONE DOLLAR"));
    }

    @Test
    public void niceStringTen() throws Exception {
        String testString = "10";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("TEN DOLLARS"));
    }

    @Test
    public void niceStringFifty() throws Exception {
        String testString = "50";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("FIFTY DOLLARS"));
    }

    @Test
    public void niceStringFiftyFive() throws Exception {
        String testString = "55";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("FIFTY-FIVE DOLLARS"));
    }

    @Test
    public void niceStringOneHundred() throws Exception {
        String testString = "100";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ONE HUNDRED DOLLARS"));
    }

    @Test
    public void niceStringOneThousand() throws Exception {
        String testString = "1000";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ONE THOUSAND DOLLARS"));
    }

    @Test
    public void niceStringOneMillion() throws Exception {
        String testString = "1000000";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ONE MILLION DOLLARS"));
    }

    @Test
    public void niceStringOneTenth() throws Exception {
        String testString = "0.1";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("TEN CENTS"));
    }

    @Test
    public void niceStringOneHundredth() throws Exception {
        String testString = "0.01";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ONE CENT"));
    }
}