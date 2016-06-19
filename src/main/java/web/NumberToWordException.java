package web;

/**
 * My custom exception class.
 */
class NumberToWordException extends Exception
{
    public NumberToWordException(String message)
    {
        super(message);
    }
}