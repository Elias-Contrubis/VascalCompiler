package compiler;
import java.io.IOException;
import lexer.*;
import parser.*;
import semanticActions.*;
import symbolTable.SymbolTableError;

/*
The Compiler Class wraps up the rest of the compiler's classes.
It is responsible for managing operations
*/
public class Compiler 
{
    
    
    /* Compiler class creator
    INPUT: String - the file name for the file to be compiled
    OUTPUT: No output
    SIDE EFFECT: instantiates a lexer object and a parser object
    */
    public Compiler(String file) throws IOException, SymbolTableError, SemanticActionError, LexerError, ParserError
    {
        Lexer lexer = new Lexer(file); 
        Parser parser = new Parser(lexer.GetTokenList()); 
    }
    
    /*Main method
    INPUT: List of strings
    OUPUT: Void
    SIDE EFFECT: instatiates a compiler object
    */
    public static void main(String[] args) throws IOException, SymbolTableError, SemanticActionError, LexerError, ParserError
    {
       Compiler compiler = new Compiler("/TestFiles/ultimate.txt");
        
    }
    

    
}
    
