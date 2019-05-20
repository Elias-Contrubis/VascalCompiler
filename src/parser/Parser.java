package parser;
import java.io.*;
import java.util.*;
import lexer.*;
import semanticActions.*;
import symbolTable.SymbolTableError;
import compiler.*;


/*
The parser class enuseres that the stream of tokens conforms to the rules of
language (Makes sure the syntax is correct)
*/
public final class Parser
{
    /*Fields
    1) ParseStack - a stack
    2) RhsTable - a table that holds the right hand side of the grammer
    3) ParseTable - a hashmap that contains the parse table
    4) DumpFlag - boolean to turn on/off the stack printing
    5) DsCounter - numbers the lines when the stack is printed
    6) Token - holds a token
    7) PrevToken - holds the previous token
    8) Sem - a semanticAction object
    */
    private  Stack ParseStack = new Stack();
    private List<String> RhsTable = new ArrayList<>();
    private final HashMap<String, String> ParseTable = new HashMap<>();
    private boolean DumpFlag = true; 
    private int DsCounter = 1; 
    private Token Token;
    private Token PrevToken;
    SemanticAction Sem = new SemanticAction();
    private LinkedList<Token> TokenList;
    
    /*Parser creater
    INPUT: A list of Tokens
    OUTPUT: None
    SIDE EFFECT: calls function needed before the start of parsing
    */
    public Parser(LinkedList<Token> list) throws IOException, SymbolTableError, SemanticActionError, ParserError
    {
        TokenList = list;
        InitializeStack(); 
        ReadParseTable(); 
        ReadGrammer();  
        EvalStack();  
        
    }
    
    /*EvalStack
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: parses the tokens from the list of tokens created in lexer
    */
    private void EvalStack() throws SymbolTableError, SemanticActionError, ParserError
    {
        DumpStack();  
        Token = TokenList.peek(); 
        Object top = ParseStack.peek(); 
        String topStack = top.toString(); 
        
        while (!topStack.equals("ENDOFFILE"))
        { 
            if (topStack.contains("#"))
            {    
                Sem.Execute(GetActionIndex(), PrevToken);
                ParseStack.pop();   
            }
            else if (!topStack.contains("<"))
            {
                
                if (topStack.equalsIgnoreCase(Token.GetType()))
                { 
                    ParseStack.pop(); 
                    Token = NextToken(); 
                    
                }
                else
                    Error(1); 
          
                
            }
            else
            { 
                
                if (ParseTable.containsKey(topStack + "," + Token.GetType()))
                { 
                    String string = ParseTable.get(topStack + "," + Token.GetType()); 
                    int i = Integer.parseInt(string); 
                    if (i > 0)
                        
                    //if int value isnt negative i.e. epsilon
                        
                    { 
                        String production = RhsTable.get(i - 1);
                        production = production.trim(); 
                        String[] line = production.split(" "); 
                        int length = line.length;
                        ParseStack.pop();

                        for (int j = length-1; j >= 0; j--)
                            ParseStack.push(line[j]);

                    }
                    else
                        
                    //- numbers are epsilon just pop
                        
                    {
                        i = i * (-1) - 1;
                        String production = RhsTable.get(i);
                        production = production.trim();
                        if (!production.isEmpty())
                        {
                            ParseStack.pop();
                            ParseStack.push(production);                          
                        }
                        else
                            ParseStack.pop();

                    }

                    
                }
                else
                    Error(2);

                
            }
            top = ParseStack.peek(); 
            topStack = top.toString();
            DumpStack(); 
            Sem.DumpStack();
            System.out.println();
        }
        if (!Token.GetType().equals("ENDOFFILE"))
            Error(3); 

        Sem.Quad.Print();
    }
    
    /*NextToken
    INPUT: None
    OUTPUT: the next token from the list of tokens
    */
    private Token NextToken()
    { 
        PrevToken = TokenList.getFirst();
        TokenList.removeFirst(); 
        return TokenList.getFirst(); 
    }
     
    /*InitializeStack
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: adds <Goal> and ENDOFLINE to stack
    */
    private void InitializeStack(){
        ParseStack.push("ENDOFFILE"); 
        ParseStack.push("<Goal>");
        
    }
    
    /*GetActionIndex
    INPUT: None
    OUTPUT: the integer from the parse table which correspond to a semantic action
    */
    private int GetActionIndex()
    {
        Object top = ParseStack.peek(); 
        String topStack = top.toString();
        topStack = topStack.replace("#", "");
        return Integer.parseInt(topStack);
    }
    
    /*DumpStack
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: prints out the parse stack
    */
    private void DumpStack()
    {
        if (DumpFlag)
        { 
            Stack cloneStack = (Stack)ParseStack.clone(); 
            System.out.println(">>- " + DsCounter + " -<<"); 
            System.out.print("Stack ::==> [ ");
            while (!cloneStack.isEmpty())
                System.out.print(cloneStack.pop() + " ");

            
            System.out.println("]");
            System.out.print("Popped: " + ParseStack.peek().toString() + " ,");
            System.out.println(" Token: " + TokenList.peek().GetType());
            DsCounter++;
        }
    }
    
    /*Error
    INPUT: an int corresponding to the error type
    OUTPUT: Void
    SIDE EFFECT
    */
    private void Error(int errortype) throws ParserError
    {
        switch (errortype)
        {
            
            //1: error for terminals. prints the missing terminal
            
            case 1: 
                throw new ParserError("Parser Error: missing " + ParseStack.peek());
                
            //2: error for non terminals prints the non terminal which was invalid 
                
            case 2: 
                throw new ParserError("Parser Error: " + ParseStack.peek() + " is not a valid statement");
            
            //3: error with the ENDOFFILE
                
            case 3:
                throw new ParserError("Parser Error: on the last line, improper ending to file");
        }
    }
    
    /*ReadParseTable
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: parses the parseTable document into a hashmap
    */
    private void ReadParseTable() throws IOException{ 
        BufferedReader reader;
        
        try
        {
            reader = new BufferedReader(new FileReader("parsetable.txt"));
            String line = reader.readLine(); 
            String[] firstLine = line.split(","); 
            line = reader.readLine(); 
            
            while (line != null)
            {   
                String[] nextLine = line.split(",");
                for (int i=1; i<nextLine.length; i++)
                {
                    if (!nextLine[i].equals("999"))
                        ParseTable.put(firstLine[i] + "," + nextLine[0], nextLine[i]);

                }
                
                line = reader.readLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /*ReadGramer
    INPUT: NONE
    OUTPUT: Void
    SIDE EFFECT: parses the grammer documentinto an array list
    */
    private void ReadGrammer()
    { 
        BufferedReader reader;
        try
        {
            reader = new BufferedReader(new FileReader("augmentedGrammar.txt"));
            String line = reader.readLine();
            while (line != null)
            { 
                String catLine = line.replaceAll(".*= ", ""); 
                RhsTable.add(catLine); 
                line = reader.readLine(); 
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        

    }   
}