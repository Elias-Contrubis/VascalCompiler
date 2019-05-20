
package symbolTable;
import lexer.*;

/*
A subclass which extends SymbolTableEntry for the VaraibleEntry type
*/
public class VariableEntry extends SymbolTableEntry
{
    /*Fields:
    1) address - its address
    2) type - its type
    3) ParamFlag - if this ste is a parameter
    4) FuncResultFlag - if this ste is a function result
    5) ResultTag - is this ste is a result of a procedure or function
    */
    
    private int Address;
    private String Type;
    private boolean ParamFlag = false;
    private boolean FuncResultFlag = false;
    private boolean ResultTag = false;
    
    /*VariableEntry constructor:
    INPUTS: String name, int address, String type
    */
    public VariableEntry(String name, int address, String type)
    {
        super (name);
        this.Address = address;
        this.Type = type;
        
    }
    
    //----------------------------------------------------------------
    //Getters and Setters for the fields above
    //----------------------------------------------------------------
    
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
    
    public void SetParameter()
    {
        this.ParamFlag = true;
    }
    
    public void SetFunctionResult()
    {
        this.FuncResultFlag = true;
    }
    
    public void SetResult()
    {
        this.ResultTag = true;
    }
    
    //----------------------------------------------------------------
    
    /*IsVariable
    INPUT: None
    OUTPUT: boolean true if this ste is a variable
    */
    @Override
    public boolean IsVariable()
    {
        return true;
    }
    
    /*IsParameter
    INPUT: None
    OUTPUT: boolean true is this ste is a parameter
    */
    @Override
    public boolean IsParameter()
    {
        return ParamFlag;
    }
    
    /*IsFunctionResult
    INPUT: None
    OUTPUT: boolean true is this ste is a function result
    */
    @Override
    public boolean IsFunctionResult()
    {
        return FuncResultFlag;
    }
       
}
