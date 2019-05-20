
package symbolTable;
import java.util.*;


/*
The SymbolTable class is a symbol table management routine which is accessed
by the lexical analyzer and the semantic routines.  Three types of symbol table
will be created using this class: a global table, and local table, and a 
constant table.
*/
public class SymbolTable
{
    
    /*Fields:
    1) sTable (Hashtable): initailizes as Hashtable to hold SymbolTableEntries
    */
    private Hashtable<String, SymbolTableEntry> STable;
    
    /*SymbolTable constructor:
    INPUT: int size
    */
    public SymbolTable(int size)
    {
        STable = new Hashtable<>(size);
        
    }
    
    /*LookUp method:
    INPUT: String a key to the hashtable
    OUTPUT: the value mapped to the key
    */
    public SymbolTableEntry LookUp(String key)
    {
        return STable.get(key);
    }
    
    /*Insert method:
    INPUT: String key, SymbolTableEntry value mapped to the key
    OUTPUT: Void
    Throws exception if key already exists in the hashtable
    Enters the key and value pair into the hashtable if it doesn't already exist
    in the hashtable 
    */
    public void Insert(String key, SymbolTableEntry value) throws SymbolTableError
    {
        if (STable.containsKey(key))
           throw new SymbolTableError("The key word: "  + key + " is already taken."); 

        else
            STable.put(key,value);

    }
    
    /*Size method:
    INPUT: None
    OUTPUT: int, the size of the hashTable
    */
    public int Size()
    {
        return STable.size();
    }
    
    /*DumpTable
    INPUT: None
    OUTPUT: Void
    prints the content of the table
    */
    public void DumpTable()
    {
        System.out.println(STable);
    }

}
