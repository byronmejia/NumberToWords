package web;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NumberToWordTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void sanity() throws Exception {
        assertEquals(true, true);
    }

    @Test(expected=NumberFormatException.class)
    public void constructorBadStringNoDigits() throws Exception {
        String testString = "Lemon";
        NumberToWord number = new NumberToWord(testString);
    }

    @Test(expected=NumberFormatException.class)
    public void constructorBadManyDecimals() throws Exception {
        String testString = "1.1.1";
        NumberToWord number = new NumberToWord(testString);
    }

    @Test(expected=NumberToWordException.class)
    public void constructorBadStringNull() throws Exception {
        String testString = null;
        NumberToWord number = new NumberToWord(testString);
    }

    @Test(expected=NumberToWordException.class)
    public void constructorBadStringNumberMix() throws Exception {
        String testString = "123fmm";
        NumberToWord number = new NumberToWord(testString);
    }

    @Test(expected=NumberToWordException.class)
    public void constructorBadStringNumberMix2() throws Exception {
        String testString = "123f";
        NumberToWord number = new NumberToWord(testString);
    }

    @Test
    public void niceStringOne() throws Exception {
        String testString = "1";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ONE"));
    }

    @Test
    public void niceStringTen() throws Exception {
        String testString = "10";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("TEN"));
    }

    @Test
    public void niceStringFifty() throws Exception {
        String testString = "50";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("FIFTY"));
    }

    @Test
    public void niceStringFiftyFive() throws Exception {
        String testString = "55";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("FIFTY FIVE"));
    }

    @Test
    public void niceStringOneHundred() throws Exception {
        String testString = "100";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ONE HUNDRED"));
    }

    @Test
    public void niceStringOneThousand() throws Exception {
        String testString = "1000";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ONE THOUSAND"));
    }

    @Test
    public void niceStringOneMillion() throws Exception {
        String testString = "1000000";
        NumberToWord number = new NumberToWord(testString);
        assertTrue(number.niceString().equals("ONE MILLION"));
    }
}