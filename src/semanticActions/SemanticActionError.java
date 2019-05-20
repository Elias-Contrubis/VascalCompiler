
package semanticActions;

/*
The semanticActionError class handles the errors that occur during the 
semantic actions
*/
public class SemanticActionError extends Exception
{
    
    /*SemanticActionError constructor
    INPUT: String errorMessage
    OUTPUT: None
    SIDE EFFECT: Throws error message
    */
    public SemanticActionError(String errorMessage)
    {
        super (errorMessage);
    }
    
}
