
package symbolTable;

/*
The ConstantEntry class is a subclass for symbolTableEntry constantEntry types.
*/
public class ConstantEntry extends SymbolTableEntry
{
    /*Fields:
    1) type - the token's type
    2) ParamFlag - if this ste is a parameter
    3) FuncReslutFlag - if this ste is a func result
    */
    
    private String Type;
    private boolean ParamFlag = false;
    private boolean FuncResultFlag = false;
    
    /*ConstantEntry constructor
    INPUT: String name, String type of token
    OUTPUT: None
    */
    public ConstantEntry(String name, String type)
    {
        super (name);
        this.Type = type;
        
    }
    
    
    //---------------------------------------------------------------
    //Getter and Setter for type
    //---------------------------------------------------------------
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
    
    //---------------------------------------------------------------
    
    /*IsConstant
    INPUT: None
    OUTPUT: boolean true if this ste is a constant
    */
    @Override
    public boolean IsConstant()
    {
        return true;
    }
    
    /*IsParameter
    INPUT: None
    OUTPUT: boolean true if this ste is a parameter
    */
    @Override
    public boolean IsParameter()
    {
        return ParamFlag;
    }
    
    /*IsFunctionResult
    INPUT: None
    OUTPUT: boolean true if this ste is a function result
    */
    @Override
    public boolean IsFunctionResult()
    {
        return FuncResultFlag;
    }
}
