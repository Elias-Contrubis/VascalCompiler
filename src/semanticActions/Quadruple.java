
package semanticActions;

/*
The quadruple class represents one TVI operation
*/
public class Quadruple
{
    /* Fields
    The four strings that populate a quadruple
    */
    private String String0;
    private String String1;
    private String String2;
    private String String3;
    
    /*Quadruple creater
    INPUT: Four strings which will make up the tvi operation
    OUTPUT: None
    */
    public Quadruple(String string0, String string1, String string2, String string3)
    {
        this.String0 = string0;
        this.String1 = string1;
        this.String2 = string2;
        this.String3 = string3;
    }
    
    //---------------------------------------------------------------------
    //Getters and Setters for the Quadruple fields
    //---------------------------------------------------------------------
    
    public String GetString0()
    {
        return this.String0;
    }
    
    public void SetString0(String s)
    {
        this.String0 = s;
    }
    public String GetString1()
    {
        return this.String1;
    }
    
    public void SetString1(String s)
    {
        this.String1 = s;
    }
    
    public String GetString2()
    {
        return this.String2;
    }
    
    public void SetString2(String s)
    {
        this.String2 = s;
    }
    
    public String GetString3()
    {
        return this.String3;
    }
    
    public void SetString3(String s)
    {
        this.String3 = s;
    }
}
