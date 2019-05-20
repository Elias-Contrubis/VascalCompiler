
package lexer;
import java.io.*;
import java.util.*;

/*
The Lexer class isolates tokens from an input source file written in Vascal
*/
public class Lexer
{
    /*Fields
    1) EOF - an integer value for the end of file
    2) VALID_CHARS - the characters allowed in the Vascal language
    { was removed to handle comments
    3) Line - keeps track of the line count
    4) FileString - initiates the string that will hold the input characters
    5) Str - holds strings when needed
    6) Peek - hold the value peeked from the stack
    7) CharCount - counts the number of characters
    8) Keywords - a hashMap that holds the keywords for the language
    9) TokenList - a linked list holding tokens. used in GetPlusMinus()
    10)
    */
    
    private final int EOF = -1;
    private final String VALID_CHARS =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890" +
            ".,;:<>/*[]+-=()}\t\n "; 
    
    public int Line = 1; 
    private String FileString = ""; 
    private String Str = ""; 
    private char Peek = ' '; 
    public int CharCount = 0; 
    private int CharIndex = 0; //char index
    private final HashMap<String, Token> Keywords = new HashMap<String, Token>();
    private LinkedList<Token> TokenList = new LinkedList<Token>();
    private int MaxLengthIdentifier = 25;
    
    /*Lexer creater
    INPUT: String - the file name to be compiled
    OUPUT: None
    SIDE EFFECT: creates a lexer and add the keywords to their hashmap
    */
    public Lexer(String file) throws IOException, LexerError
    {
        Keywords.put("program", new Token("PROGRAM", null));
        Keywords.put("begin", new Token("BEGIN", null));
        Keywords.put("end", new Token("END", null));
        Keywords.put("var", new Token("VAR", null));
        Keywords.put("function", new Token("FUNCTION", null));
        Keywords.put("procedure", new Token("PROCEDURE", null));
        Keywords.put("result", new Token("RESULT", null));
        Keywords.put("integer", new Token("INTEGER", null));
        Keywords.put("real", new Token("REAL", null));
        Keywords.put("array", new Token("ARRAY", null));
        Keywords.put("of", new Token("OF", null));
        Keywords.put("not", new Token("NOT", null));
        Keywords.put("if", new Token("IF", null));
        Keywords.put("then", new Token("THEN", null));
        Keywords.put("else", new Token("ELSE", null));
        Keywords.put("while", new Token("WHILE", null));
        Keywords.put("do", new Token("DO", null));
        
        
        FileReader filereader = new FileReader(file);
        do
        {
            int currentInt = filereader.read(); 
            if (currentInt == EOF)
                break;

            char currentChar = (char) currentInt;
           
            FileString = FileString + currentChar;
          
        }
        while (true);
        filereader.close();
        
        FileString = FileString.toLowerCase();
        
        for (; CharIndex < FileString.length(); )
        {
            Token t = GetNextToken(FileString.charAt(CharIndex));
            System.out.println(t); 
            Str = ""; 
            Peek = ' ';
        }
        Token endOfFile = new Token("ENDOFFILE", null);
        TokenList.add(endOfFile);
        System.out.println(endOfFile);
        
    }
    
    
    /*GetNextToken
    INPUT: a single character taken from the input file
    OUTPUT: The next token obtained from the input file
    SIDE EFFECT: calls EvalNextChar
    */
    private Token GetNextToken(char ch) throws IOException, LexerError
    {
        //the all important getNextToken() function
        Token t = EvalNextChar(ch);
        TokenList.add(t); //adds token to the linkedlist
        return t; //returns the token
    }
    
    /*EvalNextChar
    INPUT: a character
    OUTPUT: a token
    SIDE EFFECT: calles the appropiate function corresponding the the character
    */
    private Token EvalNextChar(char ch) throws IOException, LexerError
    {
        if (VALID_CHARS.indexOf(ch) >= 0)
        {

            switch (ch)
            {
                case '.':
                    return GetDoubleDot(ch);
                case ',':
                    return GetComma();
                case ';':
                    return GetSemicolon();
                case ':':
                    return GetColon(ch);
                case '<':
                    return GetLT(ch);
                case '>':
                    return GetGT(ch);
                case '/':
                    return GetSlash();
                case '*':
                    return GetStar();
                case '[':
                    return GetLeftbracket();
                case ']':
                    return GetRightbracket();
                case '+':
                    return GetPlusMinus(ch);
                case '-':
                    return GetPlusMinus(ch);
                case '=':
                    return GetEquals();
                case '(':
                    return GetLeftparen();
                case ')':
                    return GetRightparen();
                case '}':
                    throw new LexerError("Lexer Error: Imporper Comment at line " + Line);
                case '\t':
                    if (BoundsChecker())
                    {
                        CharIndex++;
                        return EvalNextChar(FileString.charAt(CharIndex));
                    }
                    else
                    {
                        CharIndex++;
                        return new Token("ENDOFFILE", null);
                    }
                case '\n':
                    if (BoundsChecker())
                    {
                        CharIndex++;
                        Line++; 
                        return EvalNextChar(FileString.charAt(CharIndex));
                    }
                    else
                    {
                        CharIndex++;
                        return new Token("ENDOFFILE", null);
                    }
                case ' ':
                    if (BoundsChecker())
                    {
                        CharIndex++;
                        return EvalNextChar(FileString.charAt(CharIndex));
                    }
                    else
                    {
                        CharIndex++;
                        return new Token("ENDOFFILE", null);
                    }
                default:

                    //if the char hasn't yet been matched it must be a number or letter

                    return GetAlphaNumeric(ch); 
            }
        }
            
        //if not in Valid_Chars could be the start of comment
            
        else if (ch == '{')
        { 
            while (FileString.charAt(CharIndex) != '}')
                CharIndex++; 

            CharIndex++; 
        }
        else
            throw new LexerError("Lexer Error: Bad Character at line " + Line);
   
        if (CurrIndexBoundsChecker())
            return EvalNextChar(FileString.charAt(CharIndex));

        else
            return new Token("ENDOFFILE", null);

     
    }
       
    /*GetDoubleDot
    INPUT: a character
    OUTPUT: a token: either "endmarker" or "doubledot"
    */
    private Token GetDoubleDot(char ch)
    {
        if (BoundsChecker())
        {
            Peek = FileString.charAt(CharIndex + 1);
            if (Peek != '.')
            {
                //if only one dot it's an endmarker
                
                CharIndex++; 
                return new Token("ENDMARKER", null);
            }
            
            //if it is two dots need to skip ahead to characters
            
            CharIndex += 2; 
            return new Token("DOUBLEDOT", null);
        }
        else
        {
            
            //if peek is out of bounds then must be an endmarker
            
            CharIndex++;
            return new Token("ENDMARKER", null);
        }
    }
    
    /*GetColon
    INPUT: a character
    OUPUT: a token: either "colon" or "assignop"
    */
    private Token GetColon(char ch)
    {
        if (BoundsChecker())
        { 
            Peek = FileString.charAt(CharIndex + 1);
            if (Peek != '=')
                
            //need to check if just colon or assignop
                
            { 
                CharIndex++;
                return new Token("COLON", null);
            }
            CharIndex += 2; 
            return new Token("ASSIGNOP", null);
        }
        else
        { 
            CharIndex++;
            return new Token("COLON", null);
        }
    }
    
    /*GetLT
    INPUT: a char
    OUTPUT: a token: "relop"
    */
    private Token GetLT(char ch)
    { 
        if (BoundsChecker())
        { 
            Peek = FileString.charAt(CharIndex + 1);
            if (Peek == '>')
            { 
                CharIndex += 2;
                return new Token("RELOP", "2");
            }
            else if (Peek == '=')
            { 
                CharIndex += 2;
                return new Token("RELOP", "5");
            }
            CharIndex++; 
            return new Token("RELOP", "3");
        }
        else
        { 
            CharIndex++;
            return new Token("RELOP", "3");
        }    
    }
    
    /*GetGT
    INPUT: a character
    OUTPUT: a token: "relop"
    */
    private Token GetGT(char ch)
    {
        if (BoundsChecker())
        { 
            Peek = FileString.charAt(CharIndex + 1);
            if (Peek != '=')
            { 
                CharIndex++;
                return new Token("RELOP", "4");
            }
            else
            {
                CharIndex += 2; 
                return new Token("RELOP", "6");
            }
        }
        else
        { 
            CharIndex++;
            return new Token("RELOP", "6");
        }
    }
    
    /*GetAlphaNumeric
    INPUT: a character
    OUPUT: a token
    SIDE EFFECT: either calls GetLetters or GetNumbers
    */
    private Token GetAlphaNumeric(char ch) throws LexerError
    { //for numbers & letters
        if (Character.isLetter(ch))
            return GetLetters(ch);

        return GetNumbers(ch);
    }
    
    /*GetLetters
    INPUT: a character
    OUTPUT: a token: either "addop", "mulop", or "identifier"
    */
    private Token GetLetters(char ch) throws LexerError
    {
        if (Str.length() < MaxLengthIdentifier)
            
        //maximum length for identifiers
            
        { 
            if (CurrIndexBoundsChecker())
            {
                if (Character.isLetterOrDigit(ch)){
                    Str = Str + ch; 
                    CharIndex++;
                    return GetLetters(FileString.charAt(CharIndex));  
                }
                if (Keywords.containsKey(Str)) 
                    return Keywords.get(Str); 

                if (Str.equals("or"))
                    return new Token("ADDOP", Str);

                else if (Str.equals("div"))
                    return new Token("MULOP", Str);

                else if (Str.equals("mod"))
                    return new Token("MULOP", Str);

                else if (Str.equals("and"))
                    return new Token("MULOP", Str);

                return new Token("IDENTIFIER", Str.toUpperCase()); 
            }
            else
            {
                Str = Str + ch;
                CharIndex++;
                return new Token("IDENTIFIER", Str.toUpperCase());
            }
        }
        throw new LexerError("Lexer Error: Identifier too long at line " + Line);  
    }
    
    //-----------------------------------------------------
    //each following function is an node in the numbers DFA
    //-----------------------------------------------------
    
    /*GetNumbers
    INPUT: a character
    OUTPUT: a token: "intconstant"
    */
    private Token GetNumbers(char ch) throws LexerError
    {
        if (BoundsChecker())
        {
            Peek = FileString.charAt(CharIndex + 1);
            if (Character.isDigit(ch)){
                Str = Str + ch;
                CharIndex++;
                return GetNumbers(FileString.charAt(CharIndex));

            }
            else if (ch == '.')
            {
                if (Peek == '.')
                    return new Token("INTCONSTANT", Str);

                else
                {
                    Str = Str + ch;
                    CharIndex++;
                    return GetInt(FileString.charAt(CharIndex));
                }

            }
            else if (ch == 'e' || ch == 'E')
            {
                Str = Str + ch;
                CharIndex++;
                return GetSci(FileString.charAt(CharIndex));
            }
            else
                return new Token("INTCONSTANT", Str);

        }
        else
        { 
            CharIndex++;
            Str = Str + ch;
            return new Token("INTCONSTANT", Str);
        }
    }
    
    /*GetInt
    INPUT: a character
    OUTPUT: a token: "intconstant"
    */
    private Token GetInt(char ch) throws LexerError
    {
        if (BoundsChecker())
        {
            Peek = FileString.charAt(CharIndex + 1);
            if (Peek == '.')
            {
                Str = Str + ch;
                return new Token("INTCONSTANT", Str);
            }
            else if (Peek == 'e')
            {
                Str = Str + ch;
                CharIndex++;
                return GetSci(FileString.charAt(CharIndex));
            }
            else
            {
                Str = Str + ch;
                CharIndex++;
                return GetIntDig(FileString.charAt(CharIndex));
            }
        }
        else
        { 
            Str = Str + ch;
            CharIndex++;
            return new Token("INTCONSTANT", Str);
        }
    }
    
    /*GetIntDig
    INPUT: a character
    OUTPUT: a token: "realconstant"
    */
    private Token GetIntDig(char ch) throws LexerError
    {
        if (Character.isDigit(ch))
        {
            Str = Str + ch;
            CharIndex++;
            return GetIntDig(FileString.charAt(CharIndex));
        }
        else if (ch == 'e')
        {
            Str = Str + ch;
            CharIndex++;
            return GetSci(FileString.charAt(CharIndex));
        }
 
        return new Token("REALCONSTANT", Str);
    }
    
    /*GetSci
    INPUT: a character
    OUTPUT: a token
    SIDE EFFECT: calls either GetSciPlusMinus or GetScieDigits
    */
    private Token GetSci(char ch) throws LexerError
    {
        if (ch == '+' || ch == '-')
        {
            Str = Str + ch;
            CharIndex++;
            return GetSciPlusMinus(FileString.charAt(CharIndex));
        }
        Str = Str;
        CharIndex++;
        return GetSciDigits(FileString.charAt(CharIndex));
    }
    
    /*GetSciPlusMinus
    INPUT: a character
    OUTPUT: a token
    SIDE EFFECT: calls GetSciDigits
    */
    private Token GetSciPlusMinus(char ch) throws LexerError
    {
        Str = Str + ch;
        CharIndex++;
        return GetSciDigits(FileString.charAt(CharIndex));
    }
    
    /*GetSciDigits
    INPUT: a character
    OUTPUT: a token: "realconstant"
    */
    private Token GetSciDigits(char ch) throws LexerError
    {
        if (Character.isDigit(ch))
        {
            Str = Str + ch;
            CharIndex++;
            return GetSciDigits(FileString.charAt(CharIndex));  
        }
        else if (Character.isLetter(ch))
            throw new LexerError("Lexer Error: Bad character at line " + Line);


        return new Token("REALCONSTANT", Str);
    }
    
    //-----------------------------------------
    //This concludes the numbers DFA
    //-----------------------------------------
    
    /*GetPlusMinus
    INPUT: a character
    OUTPUT: a token: either "addop" or "unaryminus" 
    */
    private Token GetPlusMinus(char ch)
    { //handles +, -
        //System.out.println(tokenList.getLast());
        Token token = TokenList.getLast(); //gets last token from linked lst
        
        String type = token.GetType(); //if last token was:
        if (type.equals("RIGHTPAREN") || type.equals("RIGHTBRACKET")
                || type.equals("IDENTIFIER") || type.equals("INTCONSTANT")
                || type.equals("REALCONSTANT"))
        {
            if (ch == '+')
            {
                CharIndex++;
                return new Token("ADDOP", "1"); //either addop for +
            }
            else
            {
                CharIndex++;
                return new Token("ADDOP", "2"); //or addop for -
            }
        }
        else if(ch == '+')
        { //otherwise the +,- will be concidered UNARY(P/M)
            CharIndex++;
            return new Token("UNARYPLUS", null);
        }
        CharIndex++;
        return new Token("UNARYMINUS", null);
    }
    
    //-------------------------------------------------------------------
    //the following functions only need to return their token and inc. charIndex
    //-------------------------------------------------------------------
    
    //OUTPUT: "relop" token
    private Token GetEquals()
    {
        CharIndex++;
        return new Token("RELOP", "1");
    }
    
    //OUTPUT: "comma" token
    private Token GetComma()
    {
        CharIndex++;
        return new Token("COMMA", null);
    }
    
    //OUTPUT: "mulop" token
    private Token GetSlash()
    {
        CharIndex++;
        return new Token("MULOP", "2");
    }
    
    //OUTPUT: "mulop" token
    private Token GetStar()
    {
        CharIndex++;
        return new Token("MULOP", "1");
    }
    
    //OUTPUT: "semicolon" token
    private Token GetSemicolon()
    {
        CharIndex++;
        return new Token("SEMICOLON", null);
    }
    
    //OUTPUT: "leftbracket" token
    private Token GetLeftbracket()
    {
        CharIndex++;
        return new Token("LEFTBRACKET", null);
    }
    
    //OUTPUT: "rightbracket" token
    private Token GetRightbracket()
    {
        CharIndex++;
        return new Token("RIGHTBRACKET", null);
    }
    
    //OUTPUT: "leftparen" token
    private Token GetLeftparen()
    {
        CharIndex++;
        return new Token("LEFTPAREN", null);
    }
    
    //OUTPUT: "rightparen" token
    private Token GetRightparen()
    {
        CharIndex++;
        return new Token("RIGHTPAREN", null);
    }
    
//------------------------------------------------------------
// conclusion of simple functions
//------------------------------------------------------------
    
    /*GetTokenList
    INPUT: None
    OUTPUT: a linkedList of tokens
    */
    public LinkedList GetTokenList(){
        return this.TokenList;
    }
    
    /*BoundsChecker
    INPUT: None
    OUTPUT: Boolean false if FileString.peek() will produce an out of bounds error
    on FileString
    */
    private boolean BoundsChecker(){
        return (CharIndex + 1) <= (FileString.length() - 1);
    }
    
    /*BoundsCheckerAlt
    INPUT: None
    OUTPUT: Boolean false if the current index will produce an out of bounds error 
    on FileString
    */
    private boolean CurrIndexBoundsChecker(){
        return (CharIndex) <= (FileString.length() - 1);
    }
}

