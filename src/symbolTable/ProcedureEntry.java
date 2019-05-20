
package symbolTable;
import java.util.List;

/*
A subclass extending symbolTableEntry for the procedureEntry type
*/
public class ProcedureEntry extends SymbolTableEntry
{
    
    /*Fields:
    1) int numberOfParameters: the number of parameters
    2) List parameterInfo: a list containing parameter info
    */
    int NumberOfParameters;
    List ParameterInfo;
    
    /*ProcedureEntry constructor:
    INPUT: String name, int numberOfParameters, List parameterInfo
    */
    public ProcedureEntry(String name, int numberofparameters, List parameterinfo)
    {
        super (name);
        this.NumberOfParameters = numberofparameters;
        this.ParameterInfo = parameterinfo;
    }
    
    //----------------------------------------------------------------
    //Setters and Getters for the above fields
    //----------------------------------------------------------------
    
    public int GetNumberOfParameters()
    {
        return this.NumberOfParameters;
    }
    
    public void setAddress(int numberofparameters)
    {
        this.NumberOfParameters = numberofparameters;
    }
    
    public List GetParameterInfo()
    {
        return this.ParameterInfo;
    }
    
    public void SetParameterInfo(List parameterinfo)
    {
        this.ParameterInfo = parameterinfo;
    }
    
    @Override
    public void SetNumberOfParameters(int numberofparameters)
    {
        this.NumberOfParameters = numberofparameters;
    }
    
    //----------------------------------------------------------------
    
    /*AddParameter
    INPUT: a ste
    OUTPUT: void 
    SIDE EFFECT: adds an ste to the procedure's parameters
    */
    @Override
    public void AddParameter(SymbolTableEntry adder)
    {
        this.ParameterInfo.add(adder);
    }
    
    /*IsProcedure
    INPUT: None
    OUTPUT: boolean true is this ste is a procedure
    */
    @Override
    public boolean IsProcedure()
    {
        return true;
    }
    
}
