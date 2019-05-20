
package symbolTable;
import java.util.List;

/*
The FunctionEntry class is a subclass of SymbolTableEntry for the function entry
type
*/
public class FunctionEntry extends SymbolTableEntry
{
    /*Fields:
    1) numberOfParameters - the number of parameters
    2) parameterInfo - the parameters
    3) result - the result of the function
    4) type - the type of the function
    5) resultType - the type of the result of the function
    */
    
    private int NumberOfParameters;
    private List ParameterInfo;
    private VariableEntry Result;
    private String Type;
    private String ResultType;
    
    /*FunctionEntry constructor:
    INPUT:String name, int numberOfParameters, List parameterInfo, VariableEntry result
    */
    public FunctionEntry(String name, int numberofparameters, List parameterinfo, VariableEntry result)
    {
        super (name);
        this.NumberOfParameters = numberofparameters;
        this.ParameterInfo = parameterinfo;
        this.Result = result;
        
    }
    
    /*
    Overrides boolean method from super class
    */
    @Override
    public boolean IsFunction()
    {
        return true;
    }
    
    //--------------------------------------------------------------------
    //Setters and Getters for the above fields
    //--------------------------------------------------------------------
    
    @Override
    public int GetNumberOfParameters()
    {
        return this.NumberOfParameters;
    }
    
    @Override
    public void SetNumberOfParameters(int numberofparameters)
    {
        this.NumberOfParameters = numberofparameters;
    }
    
    @Override
    public List GetParameterInfo()
    {
        return this.ParameterInfo;
    }
    
    public void SetParameterInfo(List parameterinfo)
    {
        this.ParameterInfo = parameterinfo;
    }
    
    public VariableEntry GetResult()
    {
        return this.Result;
    }
    
    public void SetResult(VariableEntry result)
    {
        this.Result = result;
    }
    
    public void SetType(String type)
    {
        this.Type = type;
    }
    
    public String GetType()
    {
        return this.Type;
    }
    
    public void SetResultType(String resulttype)
    {
        this.Result.SetType(resulttype);
    }
    
    //--------------------------------------------------------------------
    
    /*AddParameter
    INPUT: a ste
    OUTPUT: Void
    SIDE EFFECT: adds to the function's parameters
    */
    @Override
    public void AddParameter(SymbolTableEntry adder)
    {
        this.ParameterInfo.add(adder);
    }
     
}
