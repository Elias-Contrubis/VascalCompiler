
package semanticActions;
import java.util.*;

/*
The Quadruples class aka Quadruples Stores stores all the tvi code that is generate
*/
public class Quadruples
{
    private Vector<String[]> Quadruple;
    private int NextQuad;
    
    /*Quadruples creater
    INPUT: NONE
    OUTPUT: NONE
    SIDE EFFECT: initiates the necessities for Quadruple Stores
    */
    public Quadruples()
    {
        Quadruple = new Vector<String[]>();
        NextQuad = 0;
        String[] dummy_quadruple = new String[4];
        dummy_quadruple[0] = dummy_quadruple[1] = dummy_quadruple[2] = dummy_quadruple[3] = null;
        Quadruple.add(NextQuad, dummy_quadruple);
        NextQuad++;
    }
    
    //-----------------------------------------------------------------------
    //Getters and Setters for Quadruples' fields
    //-----------------------------------------------------------------------
    
    public String GetField(int quadindex, int field)
    {
        return Quadruple.elementAt(quadindex)[field];
    }
    
    public void SetField(int quadindex, int index, String field)
    {
        Quadruple.elementAt(quadindex)[index] = field;
    }
    
    public int GetNextQuad()
    {
        return NextQuad;
    }
    
    public String[] GetQuad(int index)
    {
        return (String []) Quadruple.elementAt(index);
    }
    
    //-----------------------------------------------------------------------
    
    /*IncrementNextQuad
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: increments NextQuad
    */
    public void IncrementNextQuad()
    {
        NextQuad++;
    }
    
    /*AddQuad 
    INPUT: a list of strings that make up a quad
    OUTPUT: Void
    SIDE EFFECT: add a quad with the string to quadruples
    */
    public void AddQuad(String[] quad)
    {
        Quadruple.add(NextQuad, quad);
        NextQuad++;
    }
    
    /*Print
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: prints out the quadrules in quadrules
    */
    public void Print()
    {
        int quadlabel = 1;
        //String separator;
        
        System.out.println("CODE");
        
        Enumeration<String[]> e = this.Quadruple.elements();
        e.nextElement();
        
        while (e.hasMoreElements())
        {
            String[] quad = e.nextElement();
            System.out.print(quadlabel + ": " + quad[0]);
            
            if (quad[1] != null)
                System.out.print(" " + quad[1]);

            
            if (quad[2] != null)
                System.out.print(", " + quad[2]);

            
            if (quad[3] != null)
                System.out.print(", " + quad[3]);

            
            System.out.println();
            quadlabel++;
        }
    }
}
