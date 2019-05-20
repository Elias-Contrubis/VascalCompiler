
package symbolTable;

/*
The IODeviceEntry class is a subclass of symbolTableEntry for the IODevice type
*/
public class IODeviceEntry extends SymbolTableEntry
{
    
    /*IODeviceEntry method:
    INPUT: String name
    */
    public IODeviceEntry(String name)
    {
        super (name);
        
    }
    
    /*IsReserved
    INPUT: None
    OUTPUT: boolean true if ste is reserverd
    */
    @Override
    public boolean IsReserved()
    {
        return true;
    }
}
