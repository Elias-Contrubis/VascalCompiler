
package parser;


public class ParserError extends Exception{
    
    /*ParserError constructor
    INPUT: String errorMessage
    OUTPUT: None
    SIDE EFFECT: Throws error message
    */
    public ParserError(String errorMessage)
    {
        super (errorMessage);
    }
}
