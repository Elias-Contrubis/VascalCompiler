
package lexer;


public class LexerError extends Exception
{
    
    /*LexerError constructor
    INPUT: String errorMessage
    OUTPUT: None
    SIDE EFFECT: Throws error message
    */
    public LexerError(String errorMessage)
    {
        super (errorMessage);
    }
    
}
