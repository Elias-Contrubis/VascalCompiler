
package symbolTable;


/*
The ArrayEntry class is a subclass of SymbolTableEntry for the array entry type
*/
public class ArrayEntry extends SymbolTableEntry
{
    /*Fields:
    1) address (int) 
    2) type (string) the token type
    3) upperBound (int) of the array
    4) lowerBound (int) of the array
    */
    private int Address;
    private String Type;
    private int UpperBound;
    private int LowerBound;
    private boolean ParamFlag = false;
    private boolean FuncResultFlag = false;
    
    /*ArrayEntry constructor:
    INPUT: String name, int address, string type, int upperBound, in lowerBound
    */
    public ArrayEntry(String name, int address, String type, int upperbound, int lowerbound)
    {
        super (name);
        this.Address = address;
        this.Type = type;
        this.UpperBound = upperbound;
        this.LowerBound = lowerbound;
        
        
    }
    
    /*IsArray
    INPUT: None
    OUTPUT: boolean true if ste is an array
    */
    @Override
    public boolean IsArray()
    {
        return true;
    }
    
    //---------------------------------------------------------------
    //Getters and Setters for the above fields
    //---------------------------------------------------------------
    
    @Override
    public int GetAddress()
    {
        return this.Address;
    }
    
    public void SetAddress(int address)
    {
        this.Address = address;
    }
    
    @Override
    public String GetType()
    {
        return this.Type;
    }
    
    public void SetType(String type)
    {
        this.Type = type;
    }
    
    public int GetUpperBound()
    {
        return this.UpperBound;
    }
    
    public void setUpperBound(int upperbound)
    {
        this.UpperBound = upperbound;
    }
    
    public int GetLowerBound()
    {
        return this.LowerBound;
    }
    
    public void SetLowerBound(int lowerbound)
    {
        this.LowerBound = lowerbound;
    }
    
    public void SetParameter()
    {
        this.ParamFlag = true;
    }
    
    public void SetFunctionResult()
    {
        this.FuncResultFlag = true;
    }
    
    //---------------------------------------------------------------
    
    /*IsParameter
    INPUT: None
    OUTPUT: boolean trueu is the array is a parameter
    */
    @Override
    public boolean IsParameter()
    {
        return ParamFlag;
    }
    

    /*IsFunctionResult
    INPUT: None
    OUTPUT: boolean true is the array is a function result
    */
    @Override
    public boolean IsFunctionResult()
    {
        return FuncResultFlag;
    }
}
