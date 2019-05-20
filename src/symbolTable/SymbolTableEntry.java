
package symbolTable;

import java.util.ArrayList;
import java.util.List;

/*
The SymbolTableEntry is the parent class for ArrayEntry, ConstantEntry,
FunctionEntry, IODeviceEntry, ProcedureEntry, and VariableEntry which are all
objects which can be entered into the symbol tables.
*/
public class SymbolTableEntry 
{
    
    /*Fields:
    1)name (String): is the only common field to all sub classes
    */
    private String Name;
    
    /*SymbolTableEntry constructor:
    INPUT: String name
    */
    public SymbolTableEntry(String name)
    {
        this.Name = name;
    }
    
    
    /*Boolean methods:----------------------------------------------------
    INPUT: None
    OUTPUT: boolean
    are overrided by specific entries as needed
    ----------------------------------------------------------------------
    */
    
    public boolean IsVariable()
    {
        return false;
    }
    
    public boolean IsConstant()
    {
        return false;
    }
    
    public boolean IsProcedure()
    {
        return false;
    }
    
    public boolean IsFunction()
    {
        return false;
    }
    
    public boolean IsFunctionResult()
    {
        return false;
    }
    
    public boolean IsParameter()
    {
        return false;
    }
    
    public boolean IsArray()
    {
        return false;
    }
    
    public boolean IsReserved()
    {
        return false;
    }
    
    //------------------------------------------------------------
    //Getter and Setter for Name
    //------------------------------------------------------------
    
    public String GetName()
    {
        return this.Name;
    }
    
    public void SetName(String name)
    {
        this.Name = name;
    }
    
    //-------------------------------------------------------------
    
    /*GetType
    INPUT: None
    OUTPUT: the type of the ste
    ** overridden by other STEs that have a "type"
    */
    public String GetType()
    {
        return "";
    }
    
    /*GetAddress
    INPUT: None
    OUTPUT: an address
    ** overridden by ArrayEntry and VariableEntry
    */
    public int GetAddress()
    {
        return 0;
    }
    
    /*AddParameter
    INPUT: a ste
    OUTPUT: Void
    SIDE EFFECT: adds a parameter
    ** overridden by FunctionEntry and ProcedureEntry
    */
    public void AddParameter(SymbolTableEntry adder)
    {
    }
    
    /*GetParameterInfo
    INPUT: None
    OUTPUT: a list of parameters
    ** overridden by FunctionEntry and ProcedureEntry
    */
    public List GetParameterInfo()
    {
        return new ArrayList();
    }
    
    /*GetNumerOfParameters
    INPUT: None
    OUTPUT: the number of parameters
    ** overridden by FunctionEntry and ProcedureEntry
    */
    public int GetNumberOfParameters()
    {
        return 0;
    }
    
    /*SetNumberOfParameters
    INPUT: the number of parameters
    OUTPUT: Void
    ** overridden by FunctionEntry and ProcedureEntry
    */
    public void SetNumberOfParameters(int numberofparameters)
    {
    }
}
