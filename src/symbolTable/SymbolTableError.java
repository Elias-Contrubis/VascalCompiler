
package symbolTable;

/*
The SymbolTableError class handles errors that are generated in the symbol
tables
*/
public class SymbolTableError extends Exception
{
    
    /*SymbolTableError constructor
    INPUT: String errorMessage
    OUTPUT: None
    SIDE EFFECT: throws an error with a message
    */
    public SymbolTableError(String errormessage)
    {
        super(errormessage);
    }
}
