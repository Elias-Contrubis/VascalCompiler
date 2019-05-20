package lexer;

/*
The token class represents a singular token object
*/
public class Token
{
    /*Fields
    1) Type- the token's type
    2) Value- the token's value
    */
    private final String Type; 
    private final String Value; 
    
    /*Token creater
    INPUT: String- the token's type, String- the token's value
    OUTPUT: None
    SIDE EFFECT: creates a token with the given type and value
    */
    public Token(String t, String v)
    {
        Type = t;
        Value = v;
        
    }
    
    //---------------------------------------------------------------
    //Getters for Type and Value
    //---------------------------------------------------------------
    
    public String GetType(){
        return this.Type;
    }
    
    public String GetValue(){
        return this.Value;
    }
    
    //---------------------------------------------------------------

    
    /*toString
    INPUT: NONE
    OUTPUT: the token in the form of a string
    */
    @Override
    public String toString()
    {
        return "[" + Type + ", " + Value + "]";
    }    
}
    
