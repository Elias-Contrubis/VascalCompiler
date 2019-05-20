
package semanticActions;
import java.util.Stack;
import lexer.*;
import symbolTable.*;
import java.util.ArrayList;
import java.util.List;

/*
The semantic action class processes variable declarations, simple expressions,
complex expressions, relational operations, procedure/ fuction declarations,
result variables, and allocation/ deallocation of local storage.
*/
public class SemanticAction
{
    /*Fields: 
    1)semanticStack (Stack) establishes a token stack
    2)dumpFlag (boolean) turn on or off the DumpStack
    3)insert (boolean) true: if in insertion mode in symbol table
                       false: if in search mode in symbol table
    4)global (boolean) true: if in global environment
                       false: if in local environment
    5)array (boolean) true: if next variable should be treated as an array
                      false: if next variable is a simple variable (not array)
    6)globalMemory (int) keeps track of number of glabal variable declarations
    7)localMemory (int) keeps track of number of local variable declarations
    8)localTable (SymbolTable) a new SymbolTable
    9)globalTable (SymbolTable) a globalTable 
    10)constantTable (SymbolTable) a constantTable
    11)globalStore (int) to keep track of alloc statement to be filled in after
        value is known
    12)tempCount (int) to create unique names for in the create funcion
    13)quad (Quadruples) to store the Quads that are generated
    14)currentFunction - a ste with name "curr"
    15)localStore - an int holding an address in the local table
    16)paramCount - a stack for the number of parameters
    17)paramStack - a stack for parameters
    18)nextParam - hold the next parameter's address
    */
    private Stack<Object> SemanticStack = new Stack<>();
    private boolean DumpFlag = true;
    private boolean Insert = true;
    private boolean Global = true; 
    private boolean Array = false;
    private int GlobalMemory = 0;
    private int LocalMemory = 0;   
    private SymbolTable LocalTable = new SymbolTable(50);
    private SymbolTable GlobalTable;
    private SymbolTable ConstantTable;
    private int GlobalStore = 0;
    private int TempCount = 0;
    public Quadruples Quad = new Quadruples();
    public SymbolTableEntry CurrentFunction = new SymbolTableEntry("curr");
    public int LocalStore;
    public Stack<Integer> ParamCount = new Stack<>();
    public Stack<List<SymbolTableEntry>> ParamStack = new Stack<>();
    public int NextParam;
    
    /*SemanticAction Constructer:
    Intializes a new SymbolTable which will be the globalTable and
    populates it with the built in functions
    Also intializes a constantTable which begins as empty
    */
    public SemanticAction() throws SymbolTableError
    {
        GlobalTable = new SymbolTable(70);  
        GlobalTable.Insert("READ", new ProcedureEntry("READ", 0 , new ArrayList()));
        GlobalTable.Insert("WRITE", new ProcedureEntry("WRITE", 0 , new ArrayList()));
        GlobalTable.Insert("MAIN", new IODeviceEntry("MAIN"));
        ConstantTable = new SymbolTable(20);
    }
    
    /*EType
    An enum to keep track of what type of expression the semantic action are 
    dealing with
    */
    enum EType
    {
        ARITHMETIC, RELATIONAL
    }

    /*Execute method:
    INPUT: int corresponding to a semantic action, the previous token
    OUTPUT: Void
    SIDE EFFECT: calls a function corresponding to the correct semanticAction
    */
    public void Execute(int semactionnum, Token token) throws SymbolTableError, SemanticActionError
    {
        switch (semactionnum)
        {
            case 1:
                ActionOne();
                break;
            case 2:
                ActionTwo();
                break;
            case 3:
                ActionThree();
                break;
            case 4:
                ActionFour(token);
                break;
            case 5:
                ActionFive();
                break;
            case 6:
                ActionSix();
                break;
            case 7:
                ActionSeven(token);
                break;
            case 9:
                ActionNine();
                break;
            case 11:
                ActionEleven();
                break;
            case 13:
                ActionThirteen(token);
                break;
            case 15:
                ActionFifteen(token);
                break;
            case 16:
                ActionSixteen();
                break;
            case 17:
                ActionSeventeen(token);
                break;
            case 19:
                ActionNineteen();
                break;
            case 20:
                ActionTwenty();
                break;
            case 21:
                ActionTwentyOne();
                break;
            case 22:
                ActionTwentyTwo();
                break;
            case 24:
                ActionTwentyFour();
                break;
            case 25:
                ActionTwentyFive();
                break;
            case 26:
                ActionTwentySix();
                break;
            case 27:
                ActionTwentySeven();
                break;
            case 28:
                ActionTwentyEight();
                break;
            case 29:
                ActionTwentyNine();
                break;
            case 30:
                ActionThirty(token);
                break;
            case 31:
                ActionThirtyOne();
                break;
            case 32:
                ActionThirtyTwo();
                break;
            case 33:
                ActionThirtyThree();
                break;
            case 34:
                ActionThirtyFour(token);
                break;
            case 35:
                ActionThirtyFive();
                break;
            case 36:
                ActionThirtySix();
                break;
            case 37:
                ActionThirtySeven();
                break;
            case 38:
                ActionThirtyEight(token);
                break;
            case 39:
                ActionThirtyNine();
                break;
            case 40:
                ActionForty(token);
                break;
            case 41:
                ActionFortyOne();
                break;
            case 42:
                ActionFortyTwo(token);
                break;
            case 43:
                ActionFortyThree();
                break;
            case 44:
                ActionFortyFour(token);
                break;
            case 45:
                ActionFortyFive();
                break;
            case 46:
                ActionFortySix(token);
                break;
            case 48:
                ActionFortyEight(token);
                break;
            case 49:
                ActionFortyNine();
                break;
            case 50:
                ActionFifty();
                break;
            case 51:
                ActionFiftyOne(token);
                break;
            case 511:
                Action51READ();
                break;
            case 512:
                Action51WRITE();
                break;
            case 52:
                ActionFiftyTwo();
                break;
            case 53:
                ActionFiftyThree();
                break;
            case 54:
                ActionFiftyFour();
                break;
            case 55:
                ActionFiftyFive();
                break;
            case 56:
                ActionFiftySix();
                break;
        }

    }
    
    /*ActionOne method:
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: sets Insert to true
    */
    private void ActionOne()
    {
        Insert = true;
    }
    
    /*ActionTwo method:
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: sets Insert to false
    */
    private void ActionTwo()
    {
        Insert = false;
    }
    
    /*ActionThree method:
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: if array = true: creates a new ArrayEntry with the correct inputs
    else: it creates a new VariableEntry with the correct inputs
    */
    private void ActionThree() throws SymbolTableError
    {
        Token astack = (Token) SemanticStack.pop();
        String type = astack.GetType();
        
        if (Array)
        {
            Token bstack = (Token) SemanticStack.pop();
            String upbound = bstack.GetValue();
            int upperbound = Integer.parseInt(upbound);
            Token cstack = (Token) SemanticStack.pop();
            String lowbound = cstack.GetValue();
            int lowerbound = Integer.parseInt(lowbound);
            int memorysize = (upperbound - lowerbound) + 1;
        
            while (SemanticStack.empty() == false && 
                    SemanticStack.peek() instanceof Token &&
                    ((Token)SemanticStack.peek()).GetType().equalsIgnoreCase("identifier"))
            {
                
                Token tok = (Token) SemanticStack.pop();
                ArrayEntry id = new ArrayEntry(tok.GetValue(), 0, type, upperbound, lowerbound);

                if (Global)
                {
                    id.SetAddress(GlobalMemory);
                    GlobalTable.Insert(id.GetName(), id);
                    GlobalMemory += memorysize;
                }
                else
                {
                    id.SetAddress(LocalMemory);
                    LocalTable.Insert(id.GetName(), id);
                    LocalMemory += memorysize;
                }
                
                    
            }
        }
        else
        {

            while (SemanticStack.empty() == false && 
                    SemanticStack.peek() instanceof Token &&
                    ((Token)SemanticStack.peek()).GetType().equalsIgnoreCase("identifier"))
            {
                
                Token tok = (Token) SemanticStack.pop();
                VariableEntry id = new VariableEntry(tok.GetValue(), 0, type);

                if (Global)
                {
                    id.SetAddress(GlobalMemory);
                    GlobalTable.Insert(id.GetName(), id);
                    GlobalMemory++;
                }
                else
                {
                    id.SetAddress(LocalMemory);
                    LocalTable.Insert(id.GetName(), id);
                    LocalMemory++;
                }
                
            }
        }
        Array = false;
    }
    
    /*ActionFour method:
    INPUT: A Token
    OUTPUT: Void
    SIDE EFFECT: Pushes a token onto the Semantic Stack
    */
    private void ActionFour(Token token)
    {
        SemanticStack.push(token);
    }
    
    /*ActionFive
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Generate code for start of function.
    */
    private void ActionFive()
    {
        Insert = false;
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        Generate("PROCBEGIN", id.GetName());
        LocalStore = Quad.GetNextQuad();
        Generate("alloc", "_");
    }
    
    /*ActionSix method:
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Sets Array to true
    */
    private void ActionSix()
    {
        Array = true;
    }
    
    /*ActionSeven method:
    INPUT: A Token
    OUTPUT: Void
    SIDE EFFECT: If the input token is of type "identifier", push the token onto the stack
    */
    private void ActionSeven(Token token) throws SemanticActionError
    {
            SemanticStack.push(token);
    }
    
    /*ActionNine method:
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Pops IOdevices Output (id1), Input (id2), and program name (id3)
    Input and Output have already been included in the globalTable
    inserts the program name into the global table
    */
    private void ActionNine() throws SymbolTableError
    {
        Token id1 = (Token) SemanticStack.pop();
        Token id2 = (Token) SemanticStack.pop();
        Token id3 = (Token) SemanticStack.pop();
        
        GlobalTable.Insert(id1.GetValue(), new IODeviceEntry(id1.GetValue()));
        GlobalTable.Insert(id2.GetValue(), new IODeviceEntry(id2.GetValue()));
        GlobalTable.Insert(id3.GetValue(), new ProcedureEntry(id3.GetValue(), 0, new ArrayList()));
        
        Insert = false;
        Generate("call", "main", "0");
        Generate("exit");
    }
    
    /*ActionEleven
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Generate code for end of function.
    */
    private void ActionEleven()
    {
        Global = true;
        LocalTable = new SymbolTable(50);
        CurrentFunction = null;
        Backpatch(LocalStore, LocalMemory);
        Generate("free", LocalMemory);
        Generate("PROCEND");
    }
    
    /*ActionThirteen method: 
    INPUT: A token
    OUTPUT: Void
    SIDE EFFECT: Pushes a token onto the stack if it is of type "identifier"
    */
    private void ActionThirteen(Token token) throws SemanticActionError
    {
        if (token.GetType().equalsIgnoreCase("identifier"))
            SemanticStack.push(token);

        else
            throw new SemanticActionError("Action 13 requires that the token type be an identifier");

    }
    
    /*ActionFifteen
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Store result of function.
    */
    private void ActionFifteen(Token token) throws SymbolTableError
    {
        VariableEntry result = Create(token.GetValue() + "_RESULT", "INTEGER");
        
        // set the result tag of the variable entry class
        
        result.SetResult();
        SymbolTableEntry id = new FunctionEntry(token.GetValue(), 0, new ArrayList(), result);
        GlobalTable.Insert(id.GetName(), id);
        Global = false;
        LocalMemory = 0;
        CurrentFunction = id;
        SemanticStack.push(id);
    }
    
    /*ActionSixteen
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: CSet type of function and its result.
    */
    private void ActionSixteen()
    {
        Token type = (Token) SemanticStack.pop();
        FunctionEntry id = (FunctionEntry) SemanticStack.peek();
        id.SetType(type.GetType());
        id.SetResultType(type.GetType());
        CurrentFunction = id;
    }
    
    /*ActionSeventeen
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Create procedure in symbol table.
    */
    private void ActionSeventeen(Token token) throws SymbolTableError
    {
        SymbolTableEntry id = new ProcedureEntry(token.GetValue(), 0, new ArrayList());
        GlobalTable.Insert(id.GetName(), id);
        Global = false;
        LocalMemory = 0;
        CurrentFunction = id;
        SemanticStack.push(id);
    }
    
    /*ActionNineteen
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Initialise count of formal parameters.
    */
    private void ActionNineteen()
    {
        ParamCount = new Stack<>();
        ParamCount.push(0);
    }
    
    /*ActionTwenty
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Get number of parameters.
    */
    private void ActionTwenty()
    {
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
        int numparams = ParamCount.pop();
        id.SetNumberOfParameters(numparams);
    }
    
    /*ActionTwentyOne
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Create temporary variables to store parameter info.
    */
    private void ActionTwentyOne() throws SymbolTableError
    {
        Token type = (Token) SemanticStack.pop();
        int upperbound = -1;
        int lowerbound = -1;
        if (Array)
        {
            upperbound = Integer.parseInt(((Token)SemanticStack.pop()).GetValue());
            lowerbound = Integer.parseInt(((Token)SemanticStack.pop()).GetValue());
        }
        
        Stack<Token> parameters = new Stack<>();
        
        while (SemanticStack.peek() instanceof Token &&
                ((Token)SemanticStack.peek()).GetType().equalsIgnoreCase("IDENTIFIER"))
        {
            Token pusher = (Token) SemanticStack.pop();
            parameters.push(pusher);
        }
        
        
        while (!parameters.empty())
        {
            Token param = parameters.pop();
            if (Array)
            {
                ArrayEntry var = new ArrayEntry(param.GetValue(), LocalMemory, type.GetType(),
                                                upperbound, lowerbound);
                var.SetParameter();
                LocalTable.Insert(var.GetName(), var);
                
                // current function is either a procedure or function entry
                
                CurrentFunction.AddParameter(var);
            }
            else
            {
                VariableEntry var = new VariableEntry(param.GetValue(), LocalMemory, type.GetType());
                var.SetParameter();
                LocalTable.Insert(var.GetName(), var);
                
                // current function is either a procedure or function entry
                
                CurrentFunction.AddParameter(var);
            }
            
            LocalMemory++;
            ParamCount.push(ParamCount.pop() + 1);
        }
        Array = false;
    }
    
    /*ActionTwentyTwo
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Update branch destination for IF -> #t to next quad
    */
    private void ActionTwentyTwo() throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        if (etype != EType.RELATIONAL)
            throw new SemanticActionError("Error: Invalid use of ARITHMETIC operator. Should use RELATIONAL operator");

        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        Backpatch(etrue, Quad.GetNextQuad());
        SemanticStack.push(etrue);
        SemanticStack.push(efalse);
    }
    
    /*ActionTwentyFour
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Store line number of beginning of loop
    */
    private void ActionTwentyFour()
    {
        int beginloop = Quad.GetNextQuad();
        SemanticStack.push(beginloop);
    }
    
    /*ActionTwentyFive
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Initialisation for a WHILE loop
    */
    private void ActionTwentyFive() throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        if (etype != EType.RELATIONAL)
            throw new SemanticActionError("Error: Invalid use of ARITHMETIC operator. Should use RELATIONAL operator");

        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        Backpatch(etrue, Quad.GetNextQuad());
        SemanticStack.push(etrue);
        SemanticStack.push(efalse);
    }
    
    /*ActionTwentySix
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Write code at end of WHILE loop 
    */
    private void ActionTwentySix()
    {
        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        int beginloop = (int) SemanticStack.pop();
        Generate("goto", beginloop);
        Backpatch(efalse, Quad.GetNextQuad());
    }
    
    /*ActionTwentySeven
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Sets up ELSE case
    */
    private void ActionTwentySeven()
    {
        List<Integer> skipelse = MakeList(Quad.GetNextQuad());
        Generate("goto", "_");
        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        Backpatch(efalse, Quad.GetNextQuad());
        SemanticStack.push(skipelse);
        SemanticStack.push(etrue);
        SemanticStack.push(efalse);
    }
    
    /*ActionTwentyEight
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: End of ELSE stmt
    */
    private void ActionTwentyEight()
    {
        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        List<Integer> skipelse = (List<Integer>) SemanticStack.pop();
        Backpatch(skipelse, Quad.GetNextQuad());
    }
    
    /*ActionTwentyNine
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: End of IF without ELSE
    */
    private void ActionTwentyNine()
    {
        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        Backpatch(efalse, Quad.GetNextQuad());
    }
    
    /*ActionThirty
    INPUT: A token
    OUTPUT: void
    SIDE EFFECT: Check to see if a variable has been declared
    */
    private void ActionThirty(Token token) throws SemanticActionError
    {
       SymbolTableEntry id = LookupID(token);
       if (id == null)
           throw new SemanticActionError("undeclared variable error");

       SemanticStack.push(id);
       SemanticStack.push(EType.ARITHMETIC);
    }
    
    /*ActionThirtyOne
    INPUT: NONE
    OUTPUT: Void
    SIDE EFFECT: Put the value of variable ID2 in ID1
    */
    private void ActionThirtyOne() throws SemanticActionError, SymbolTableError
    {
       EType etype = (EType) SemanticStack.pop();
       if (etype != EType.ARITHMETIC)
           throw new SemanticActionError("Error: Invalid use of RELATIONAL operator. Should use ARITHMETIC operator");
       
       SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
       SymbolTableEntry offset = (SymbolTableEntry) SemanticStack.pop();
       SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();

       
       if (TypeCheck(id1, id2) == 3)
           throw new SemanticActionError("Type mismatch error: Can't assign real to an integer");

       if (TypeCheck(id1, id2) == 2)
       {
           VariableEntry temp = Create("temp", "REAL");
           Generate("ltof", id2, temp);
           if (offset == null)
               Generate("move", temp, id1);

           else
               Generate("stor", temp, offset, id1);

       }
       else
       {
           if (offset == null)
               Generate("move", id2, id1);

           else
               Generate("stor", id2, offset, id1);

       }
    }
    
    /*ActionThirtyTwo
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Ensure TOS is an array & typecheck
    */
    private void ActionThirtyTwo() throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
        if (etype != EType.ARITHMETIC)
            throw new SemanticActionError("Error: Invalid use of RELATIONAL operator. Should use ARITHMETIC operator");

        if (!id.IsArray())
            throw new SemanticActionError("Error: ID is not an array");

    }
    
    /*ActionThirtyThree
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Calculate memory offset for array element
    */
    private void ActionThirtyThree() throws SemanticActionError, SymbolTableError
    {
        EType etype = (EType) SemanticStack.pop();
        if (etype != EType.ARITHMETIC)
            throw new SemanticActionError("Error: Invalid use of RELATIONAL operator. Should use ARITHMETIC operator");

        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        if (!id.GetType().equalsIgnoreCase("integer"))
            throw new SemanticActionError("Error: type mismatch");

        ArrayEntry array = (ArrayEntry) SemanticStack.peek();
        VariableEntry temp1 = Create("temp", "INTEGER");
        VariableEntry temp2 = Create("temp", "INTEGER");
        Generate("move", array.GetLowerBound(), temp1);
        Generate("sub", id, temp1, temp2);
        SemanticStack.push(temp2);
    }
    
    /*ActionThirtyFour
    INPUT: Token
    OUTPUT: Void
    SIDE EFFECT: Function or procedure
    */
    private void ActionThirtyFour(Token token) throws SymbolTableError, SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
        if (id.IsFunction())
        {
            SemanticStack.push(etype);
            Execute(52, token);
        }
        else
            SemanticStack.push(null);

    }
    
    /*ActionThirtyFive
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Set up to call a procedure.
    */
    private void ActionThirtyFive()
    {
        EType etype = (EType) SemanticStack.pop();
        ProcedureEntry id  = (ProcedureEntry) SemanticStack.peek();
        SemanticStack.push(etype);
        ParamCount.push(0);
        ParamStack.push(id.GetParameterInfo());
    }
    
    /*ActionThirtySix
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Generate code to call a procedure.
    */
    private void ActionThirtySix() throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        ProcedureEntry id = (ProcedureEntry) SemanticStack.pop();
        if (id.GetNumberOfParameters() != 0)
            throw new SemanticActionError("Error: Wrong number of parameters");

        Generate("call", id.GetName(), 0);
    }
    
    /*ActionThirtySeven
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Consume actual parameters in a list of parameters.
    */
    private void ActionThirtySeven() throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        if (etype != EType.ARITHMETIC)
            throw new SemanticActionError("Error: Invalid use of RELATIONAL operator. Should use ARITHMETIC operator");

        
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
        if (!(id.IsVariable() || id.IsConstant() || id.IsFunctionResult() || id.IsArray()))
            throw new SemanticActionError("Error: The wrong parameter type is being used");  

        
        ParamCount.push(ParamCount.pop() + 1);
        Stack parameters = new Stack();
        while (!(SemanticStack.peek() instanceof ProcedureEntry || 
                SemanticStack.peek() instanceof FunctionEntry))
            parameters.push(SemanticStack.pop());

        
        SymbolTableEntry funcId = (SymbolTableEntry) SemanticStack.peek();
        while (!parameters.empty())
            SemanticStack.push(parameters.pop());
        
        if (!(funcId.GetName().equals("READ") || funcId.GetName().equals("WRITE")))
        {
            System.out.println("param: " + ParamCount.peek() + " func: " + funcId.GetNumberOfParameters());
            if (ParamCount.peek() > funcId.GetNumberOfParameters())
                throw new SemanticActionError("Error: Wrong number of parameters");

            System.out.println(ParamStack);
            System.out.println(ParamStack.peek().size());
            SymbolTableEntry param = ParamStack.peek().get(NextParam);
            if (!id.GetType().equalsIgnoreCase(param.GetType()))
                throw new SemanticActionError("Error: Bad parameter type");

            if (param.IsArray())
            {
                ArrayEntry arrayparam = (ArrayEntry) param;
                ArrayEntry arrayId = (ArrayEntry) id;
                if ((arrayId.GetLowerBound() != arrayparam.GetLowerBound()) ||
                   (arrayId.GetUpperBound() != arrayparam.GetUpperBound()))
                    throw new SemanticActionError("Error: Bad parameter type");

            }
            NextParam++;
        }

    }
 
    
    /*ActionThiryEight
    INPUT: Token
    OUTPUT: Void
    SIDE EFFECT: Ensure arithmetic operation and push
    */
    private void ActionThirtyEight(Token token) throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        if (etype != EType.ARITHMETIC)
            throw new SemanticActionError("Error: Invalid use of RELATIONAL operator. Should use ARITHMETIC operator");

        SemanticStack.push(token);
    }
    
    /*ActionThirtyNine
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Change to relational & add ET/F as required
    */
    private void ActionThirtyNine() throws SemanticActionError, SymbolTableError
    {
        EType etype = (EType) SemanticStack.pop();
        if (etype != EType.ARITHMETIC)
            throw new SemanticActionError("Error: Invalid use of RELATIONAL operator. Should use ARITHMETIC operator");

        SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
        Token operator = (Token) SemanticStack.pop();
        String opcode = operator.GetValue();
        SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();
        if (TypeCheck(id1, id2) == 2)
        {
            VariableEntry temp = Create("temp", "REAL");
            Generate("ltof", id2, temp);
            Generate(Helper39(opcode), id1, temp, "_");
        }
        else if (TypeCheck(id1, id2) == 3)
        {
            VariableEntry temp = Create("temp", "REAL");
            Generate("ltof", id1, temp);
            Generate(Helper39(opcode), temp, id2, "_");
        }
        else
        {
            String name = id2.GetName();
            Generate(Helper39(opcode), id1, id2, "_");
        }
        Generate("goto", "_");
        List<Integer> etrue = MakeList(Quad.GetNextQuad() - 2);
        List<Integer> efalse = MakeList(Quad.GetNextQuad() - 1);
        SemanticStack.push(etrue);
        SemanticStack.push(efalse);
        SemanticStack.push(EType.RELATIONAL);
    }
    
    private String Helper39(String opcode)
    {
        int op = Integer.parseInt(opcode);
        switch (op)
        {
            case 1:
                return "beq";
            case 2:
                return "bne";
            case 3:
                return "blt";
            case 4:
                return "bgt";
            case 5: 
                return "ble";
            case 6:
                return "bge";
            default:
                return opcode;
        }
    }
    
    /*ActionForty
    INPUT: Token
    OUTPUT: Void
    SIDE EFFECT: Push unary plus/minus to stack
    */
    private void ActionForty(Token token)
    {
        SemanticStack.push(token);
    }
    
    /*ActionFortyOne
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Apply unary plus/minus
    */
    private void ActionFortyOne() throws SymbolTableError, SemanticActionError
    {
       EType etype = (EType) SemanticStack.pop();
       if (etype != EType.ARITHMETIC)
           throw new SemanticActionError("Error: Invalid use of RELATIONAL operator. Should use ARITHMETIC operator");

       SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
       Token sign = (Token) SemanticStack.pop();
       
       if (sign.GetType().equalsIgnoreCase("UNARYMINUS"))
       {
           VariableEntry temp = Create("temp", id.GetType());
           if (id.GetType().equals("INTEGER")) 
               Generate("uminus", id, temp);

           else
               Generate("fuminus", id, temp);

           SemanticStack.push(temp);
       }
       else
           SemanticStack.push(id);

       SemanticStack.push(EType.ARITHMETIC);
    }
    
    /*ActionFortyTwo
    INPUT: Token
    OUTPUT: void
    SIDE EFFECT: Push ADDOP operator (+, -, etc.) on to stack
    */
    private void ActionFortyTwo(Token token) throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        if(token.GetValue().equalsIgnoreCase("or"))
        {
            if (etype != EType.RELATIONAL)
                throw new SemanticActionError("Error: Invalid use of ARITHMETIC operator. Should use RELATIONAL operator");

            List<Integer> efalse = (List<Integer>) SemanticStack.peek();
            Backpatch(efalse, Quad.GetNextQuad());
        }
        else
        {
            if (etype != EType.ARITHMETIC)
                throw new SemanticActionError("Error: Invalid use of RELATIONAL operator. Should use ARITHMETIC operator");

        }
        //token should be an operator
        SemanticStack.push(token);
    }
    
    /*ActionFortyThree
    INPUT: Token
    OUTPUT: void
    SIDE EFFECT: Preform ADDOP based on OP popped from stack
    */
    private void ActionFortyThree() throws SymbolTableError
    {
       EType etype = (EType) SemanticStack.pop();
       if (etype == EType.RELATIONAL)
       {
           List<Integer> e2false = (List<Integer>) SemanticStack.pop();
           List<Integer> e2true = (List<Integer>) SemanticStack.pop();
           Token operator = (Token) SemanticStack.pop();
           List<Integer> e1false = (List<Integer>) SemanticStack.pop();
           List<Integer> e1true = (List<Integer>) SemanticStack.pop();
           
           List<Integer> ETrue = Merge(e1true, e2true);
           List<Integer> EFalse = e2false;
           SemanticStack.push(ETrue);
           SemanticStack.push(EFalse);
           SemanticStack.push(EType.RELATIONAL);
       }
       else
       {
            SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
            Token operator = (Token) SemanticStack.pop();
            String opcode = operator.GetValue();
            SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();
            String name = id1.GetName();
            

            if (TypeCheck(id1, id2) == 0)
            {
                VariableEntry temp = Create("temp", "INTEGER");
                Generate(Helper43(opcode), id1, id2, temp);
                SemanticStack.push(temp);
            }
            else if (TypeCheck(id1, id2) == 1) 
            {
                VariableEntry temp = Create("temp", "REAL");
                Generate("f" + Helper43(opcode), id1, id2, temp);
                SemanticStack.push(temp);
            } 
            else if (TypeCheck(id1, id2) == 2) 
            {
                VariableEntry temp1 = Create("temp", "REAL");
                VariableEntry temp2 = Create("temp", "REAL");
                Generate("ltof", id2, temp1);
                Generate("f" + Helper43(opcode), id1, temp1, temp2);
                SemanticStack.push(temp2);
            } 
            else if (TypeCheck(id1, id2) == 3) 
            {
                VariableEntry temp1 = Create("temp", "REAL");
                VariableEntry temp2 = Create("temp", "REAL");
                Generate("ltof", id1, temp1);
                Generate("f" + Helper43(opcode), temp1, id2, temp2);
                SemanticStack.push(temp2);
            }
            SemanticStack.push(EType.ARITHMETIC);
        }
    }
    
    /*Helper43
    INPUT: String
    OUTPUT: String
    takes the opcode from ActionFortyThree (ex. 1,2,3,4)
    and gets the corresponding ADDOP
    */
    private String Helper43(String opcode)
    {
        int op = Integer.parseInt(opcode);
        switch (op)
        {
            case 1:
                return "add";
            case 2:
                return "sub";
            case 3:
                return "or";
            default:
                return opcode;
        }
    }
    
    /*ActionFortyFour
    INPUT: Token
    OUTPUT: void
    SIDE EFFECT: Push MULOP operator (*, /, etc.) on to stack
    */
    private void ActionFortyFour(Token token)
    {
        EType etype = (EType) SemanticStack.pop();
        if (etype == EType.RELATIONAL){
            List<Integer> efalse = (List<Integer>) SemanticStack.pop();
            List<Integer> etrue = (List<Integer>) SemanticStack.pop();
            
            if (token.GetValue().equalsIgnoreCase("and"))
                Backpatch(etrue, Quad.GetNextQuad());

            SemanticStack.push(etrue);
            SemanticStack.push(efalse);
        }
        SemanticStack.push(token);
    }
    
    /*ActionFortyFive
    INPUT: None
    OUTPUT: void
    SIDE EFFECT: Perfomr MULOP based on OP popped from stack
    */
    private void ActionFortyFive() throws SemanticActionError, SymbolTableError
    {
       EType etype = (EType) SemanticStack.pop();
       if (etype == EType.RELATIONAL)
       {
           List<Integer> e2false = (List<Integer>) SemanticStack.pop();
           List<Integer> e2true = (List<Integer>) SemanticStack.pop();
           Token operator = (Token) SemanticStack.pop();
           
           if (operator.GetValue().equalsIgnoreCase("and"))
           {
               List<Integer> e1false = (List<Integer>) SemanticStack.pop();
               List<Integer> e1true = (List<Integer>) SemanticStack.pop();
               
               List<Integer> etrue = e2true;
               List<Integer> efalse = Merge(e1false, e2false);
               SemanticStack.push(etrue);
               SemanticStack.push(efalse);
               SemanticStack.push(EType.RELATIONAL);
           }
       }
       else
       {

            SymbolTableEntry id2 = (SymbolTableEntry) SemanticStack.pop();
            Token operator = (Token) SemanticStack.pop();
            String opcode = operator.GetValue();
            SymbolTableEntry id1 = (SymbolTableEntry) SemanticStack.pop();

            if (TypeCheck(id1, id2) != 0 && (opcode.equals("4") || opcode.equals("3"))) 
                throw new SemanticActionError("bad parameter error"); 


            if (TypeCheck(id1, id2) == 0) 
            {
                if (opcode.equals("4")) 
                { 
                    VariableEntry temp1 = Create("temp", "INTEGER");
                    VariableEntry temp2 = Create("temp", "INTEGER");
                    VariableEntry temp3 = Create("temp", "INTEGER");
                    Generate("div", id1, id2, temp1);
                    Generate("mul", id2, temp1, temp2);
                    Generate("sub", id1, temp2, temp3);
                    SemanticStack.push(temp3);
                } 
                else if (opcode.equals("2")) 
                { 
                    VariableEntry temp1 = Create("temp", "REAL");
                    VariableEntry temp2 = Create("temp", "REAL");
                    VariableEntry temp3 = Create("temp", "REAL");
                    Generate("ltof", id1, temp1);
                    Generate("ltof", id2, temp2);
                    Generate("fdiv", temp1, temp2, temp3);
                    SemanticStack.push(temp3);
                } 
                else 
                {
                    VariableEntry temp = Create("temp", "INTEGER");
                    if (opcode.equals("1"))
                        Generate(Helper45(opcode), id1, id2, temp);
                    
                    else
                        Generate(opcode, id1, id2, temp);

                    SemanticStack.push(temp);
                }
            } 
            else if (TypeCheck(id1, id2) == 1) 
            {
                VariableEntry temp = Create("temp", "REAL");
                Generate("f" + Helper45(opcode), id1, id2, temp);
                SemanticStack.push(temp);
            } 
            else if (TypeCheck(id1, id2) == 2) 
            {
                VariableEntry temp1 = Create("temp", "REAL");
                VariableEntry temp2 = Create("temp", "REAL");
                Generate("ltof", id2, temp1);
                Generate("f" + Helper45(opcode), id1, temp1, temp2);
                SemanticStack.push(temp2);
            }
            else if (TypeCheck(id1, id2) == 3) 
            {
                VariableEntry temp1 = Create("temp", "REAL");
                VariableEntry temp2 = Create("temp", "REAL");
                Generate("ltof", id1, temp1);
                Generate("f" + Helper45(opcode), temp1, id2, temp2);
                SemanticStack.push(temp2);
            }
            SemanticStack.push(EType.ARITHMETIC);
       }
    }
    /*helper45
    INPUT: String
    OUTPUT: String
    takes the opcode from ActionFortyThree (ex. 1,2,3,4)
    and gets the corresponding MULOP
    */
    private String Helper45(String opcode)
    {
        int op = Integer.parseInt(opcode);
        switch (op)
        {
            case 1:
                return "mul";
            case 2:
                return "div";
            case 3:
                return "DIV";
            case 4:
                return "mod";
            case 5:
                return "and";
            default:
                return opcode;
        }
    }
    
    /*ActionFortySix
    INPUT: Token
    OUTPUT: void
    SIDE EFFECT: Loop of value of variable or constant from SymbolTable
    */
    private void ActionFortySix(Token token) throws SemanticActionError, SymbolTableError
    {
        if (token.GetType().equalsIgnoreCase("IDENTIFIER"))
        {
            SymbolTableEntry id = LookupID(token);
            if (id == null)
                throw new SemanticActionError("undeclared variable error");

            SemanticStack.push(id);
        }
        else if(token.GetType().equalsIgnoreCase("INTCONSTANT") ||
                 token.GetType().equalsIgnoreCase("REALCONSTANT"))
        {
            SymbolTableEntry id = ConstantTable.LookUp(token.GetValue());
            if (id == null)
            {
                if (token.GetType().equalsIgnoreCase("INTCONSTANT"))
                    id = new ConstantEntry(token.GetValue(), "INTEGER");

                else if (token.GetType().equalsIgnoreCase("REALCONSTANT"))
                    id = new ConstantEntry(token.GetValue(), "REAL");

                ConstantTable.Insert(id.GetName(), id);
            }
            SemanticStack.push(id);
        }
        SemanticStack.push(EType.ARITHMETIC);
    }
    
    /*ActionFortySeven
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Reserved word NOT
    */
    private void ActionFortySeven() throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        if (etype != EType.RELATIONAL)
            throw new SemanticActionError("Error: Invalid use of ARITHMETIC operator. Should use RELATIONAL operator");

        List<Integer> efalse = (List<Integer>) SemanticStack.pop();
        List<Integer> etrue = (List<Integer>) SemanticStack.pop();
        SemanticStack.push(etrue);
        SemanticStack.push(efalse);
        SemanticStack.push(EType.RELATIONAL);
    }
    
    /*ActionFortyEight
    INPUT: None
    OUTPUT: void
    SIDE EFFECT: Array lookup
    */
    private void ActionFortyEight(Token token) throws SymbolTableError, SemanticActionError
    {
        SymbolTableEntry offset = (SymbolTableEntry) SemanticStack.pop();
        if (offset != null)
        {
            if(offset.IsFunction())
                Execute(52, token);

            else
            {
                SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
                //VariableEntry id1 = (VariableEntry) id;
                VariableEntry temp = Create("temp", id.GetType());
                Generate("load", id, offset, temp);
                SemanticStack.push(temp);
            }
        }
        SemanticStack.push(EType.ARITHMETIC);
    }
    
    /*ActionFortyNine
    INPUT: Void
    OUPUT: NONE
    SIDE EFFECT: Ensure this is a function & get parameter data.
    */
    private void ActionFortyNine() throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
        SemanticStack.push(etype);
        
        if (etype != EType.ARITHMETIC)
            throw new SemanticActionError("Error: Invalid use of RELATIONAL operator. Should use ARITHMETIC operator");

        ParamCount.push(0);
        //ProcedureEntry procedureId = (ProcedureEntry) id;
        ParamStack.push(id.GetParameterInfo());
    }
    
    /*ActionFifty
    INPUT: Void
    OUPUT: NONE
    SIDE EFFECT: Generate code to assign memory for function parameters & call function.
    */
    private void ActionFifty() throws SemanticActionError, SymbolTableError
    {
        Stack<SymbolTableEntry> parameters = new Stack<>();
        
        while (SemanticStack.peek() instanceof ArrayEntry || 
                SemanticStack.peek() instanceof ConstantEntry || 
                SemanticStack.peek() instanceof VariableEntry)
        {
            SymbolTableEntry pushste = (SymbolTableEntry) SemanticStack.pop();
            parameters.push(pushste);
        }
        
        while (!parameters.empty())
        {
            //this is the one place to use paramPrefix
            GenerateParam("param", parameters.pop());
            LocalMemory++;
        }
        
        EType etype = (EType) SemanticStack.pop();
        FunctionEntry id = (FunctionEntry) SemanticStack.pop();
        int numparams = ParamCount.pop();
        if (numparams > id.GetNumberOfParameters())
            throw new SemanticActionError("Error: Wrong number of parameters");

        Generate("call", id.GetName(), numparams);
        ParamStack.pop();
        NextParam = 0;
        
        VariableEntry temp = Create("temp", id.GetResult().GetType());
        Generate("move", id.GetResult(), temp);
        SemanticStack.push(temp);
        SemanticStack.push(EType.ARITHMETIC);
    }
    
    /*ActionFiftyOne
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Generate code to assign memory for procedure parameters & call function.
    */
    private void ActionFiftyOne(Token token) throws SemanticActionError, SymbolTableError
    {
        Stack<SymbolTableEntry> parameters = new Stack<>();

        while (SemanticStack.peek() instanceof ArrayEntry || 
                SemanticStack.peek() instanceof ConstantEntry || 
                SemanticStack.peek() instanceof VariableEntry)
        {
            SymbolTableEntry pushste = (SymbolTableEntry) SemanticStack.pop();
            parameters.push(pushste);
        }
        
        EType etype = (EType) SemanticStack.pop();
        ProcedureEntry id = (ProcedureEntry) SemanticStack.pop();
        
        if (id.GetName().equalsIgnoreCase("READ") || id.GetName().equalsIgnoreCase("WRITE"))
        {
            SemanticStack.push(id);
            SemanticStack.push(etype);
            while (!parameters.empty())
                SemanticStack.push(parameters.pop());

            if (id.GetName().equalsIgnoreCase("READ"))
                Execute(511, token);

            else
                Execute(512, token);
                
        }
        else
        {
            int numparams = ParamCount.pop();
            if (numparams != id.GetNumberOfParameters())
                throw new SemanticActionError("Error: wrong number of parameters");
 
            while (!parameters.empty())
            {
                //this is the other one place to use paramPrefix
                GenerateParam("param", parameters.pop());
                LocalMemory++;
            }
            Generate("call", id.GetName(), numparams);
            ParamStack.pop();
            NextParam = 0;
        }
    }
    
    /*ActionFiftyOne Read
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Read input from user.
    */
    private void Action51READ() throws SymbolTableError
    {
        Stack<SymbolTableEntry> parameters = new Stack<>();
        
        while (SemanticStack.peek() instanceof VariableEntry)
        {
            SymbolTableEntry pushste = (SymbolTableEntry) SemanticStack.pop();
            parameters.push(pushste);

        }
        
        while (!parameters.empty())
        {
            SymbolTableEntry id = parameters.pop();
            if (id.GetType().equalsIgnoreCase("REAL"))
                Generate("finp", id);

            else
                Generate("inp", id);

        }
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        ParamCount.pop();
    }
    
    /*ActionFiftyOne Write
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Display variable name and contents.
    */
    private void Action51WRITE() throws SymbolTableError
    {
        Stack<SymbolTableEntry> parameters = new Stack<>();
        

        while (SemanticStack.peek() instanceof ConstantEntry ||
                SemanticStack.peek() instanceof VariableEntry)
        {
            SymbolTableEntry pushste = (SymbolTableEntry) SemanticStack.pop();
            parameters.push(pushste);

        }
        
        while (!parameters.empty())
        {
            SymbolTableEntry id = parameters.pop();
            if (id.IsConstant())
            {
                if (id.GetType().equalsIgnoreCase("REAL"))
                    Generate("foutp", id.GetName());

                else
                {
                    Generate("outp", id.GetName());
                }

            }
            else
            {
                Generate("print", "\"" + id.GetName() + " = \"");
                if (id.GetType().equalsIgnoreCase("REAL"))
                    Generate("foutp", id);

                else
                    Generate("outp", id);

            }
            Generate("newl");
        }
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        ParamCount.pop();
    }
    
    /*ActionFiftyTwe
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Case for function with no parameters.
    */
    private void ActionFiftyTwo() throws SemanticActionError, SymbolTableError
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        if (!id.IsFunction())
            throw new SemanticActionError("Error: id is not a function error");

        FunctionEntry funcid = (FunctionEntry) id;
        String st = funcid.GetResult().GetType();
        if (funcid.GetNumberOfParameters() > 0)
            throw new SemanticActionError("Error: wrong number of parameters error");

        Generate("call", funcid.GetName(), 0);
        VariableEntry temp = Create("temp", funcid.GetType());
        Generate("move", funcid.GetResult(), temp);
        SemanticStack.push(temp);
        SemanticStack.push(null);
    }
    
    
    /*ActionFiftyThree
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Look up variable or function result
    */
    private void ActionFiftyThree() throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.pop();
        if (id.IsFunction())
        {
            if (id != CurrentFunction) 
                throw new SemanticActionError("Error: Illegal procedure");

            FunctionEntry funcid = (FunctionEntry) id;
            SemanticStack.push(funcid.GetResult());
            SemanticStack.push(EType.ARITHMETIC);

        }
        else
        {
            SemanticStack.push(id);
            SemanticStack.push(etype);
        }
    }
    
    /*ActionFiftyFour 
    INPUT: None
    OUTPUT: Void
    SIDE EFFECT: Confirm STMT is a procedure call
    */
    private void ActionFiftyFour() throws SemanticActionError
    {
        EType etype = (EType) SemanticStack.pop();
        SymbolTableEntry id = (SymbolTableEntry) SemanticStack.peek();
        SemanticStack.push(etype);
        if (!id.IsProcedure())
            throw new SemanticActionError("Error: Illegal procedure");

    }
    
    
    /*ActionFiftyFive
    INPUT: None
    OUTPUT: void
    SIDE EFFECT: Generate end-of-MAIN:: wrapper code
    */
    private void ActionFiftyFive()
    {
        Backpatch(GlobalStore, GlobalMemory);
        Generate("free", GlobalMemory);
        Generate("procend");
    }
    
    /*ActionFiftSix: Generate start-of-MAIN:: wrapper code.
    INPUT: None
    OUTPUT: void
    SIDE EFFECT: Generate start-of-MAIN:: wrapper code
    */
    private void ActionFiftySix()
    {
        Generate("procbegin", "main");
        GlobalStore = Quad.GetNextQuad();
        Generate("alloc", "_");
    }
    
    
    /*gen
    INPUT: Array of Strings
    OUTPUT: void
    all the quads from the overloaded generate functions send their quads to gen
    which then adds it to "quad" the Quadruple Store
    */
    private void Gen(String[] q)
    {
        Quad.AddQuad(q);
    }
    
    //Start of generate functions-----------------------------------------------
    
    /*Generate
    INPUT: Any exceptable ordering for a quadruple
    OUTPUT: void
    SIDE EFFECT: add the quadruple to quadruple store through gen()
    The following are all overloading with different inputs
    */
    private void Generate(String tvicode, SymbolTableEntry operand1,
            SymbolTableEntry operand2, SymbolTableEntry operand3) throws SymbolTableError
    {
        
        int op1address = GetSTEAddress(operand1);
        int op2address = GetSTEAddress(operand2);
        int op3address = GetSTEAddress(operand3);
        String op1 = getSTEPrefix(operand1) + Integer.toString(op1address);
        String op2 = getSTEPrefix(operand2) + Integer.toString(op2address);
        String op3 = getSTEPrefix(operand3) + Integer.toString(op3address);
        String[] q = {tvicode, op1, op2, op3};
        Gen(q);
    }
    
    private void Generate(String tvicode, String operand1, String operand2)
    {
        String[] q = {tvicode, operand1, operand2, null};
        Gen(q);
    }
    
    private void Generate(String tvicode, int operand1, SymbolTableEntry operand2) throws SymbolTableError
    {
        String op1 = Integer.toString(operand1);
        int op2address = GetSTEAddress(operand2);
        String op2 = getSTEPrefix(operand2) + op2address;
        String[] q = {tvicode, op1, op2, null};
        Gen(q);
    }
    
    private void Generate(String tvicode, SymbolTableEntry operand1) throws SymbolTableError
    {
        int op1address = GetSTEAddress(operand1);
        String op1 = getSTEPrefix(operand1) + op1address;
        String[] q = {tvicode, op1, null, null};
        Gen(q);
    }
    
    private void GenerateParam(String tvicode, SymbolTableEntry operand1) throws SymbolTableError
    {
        int op1address = GetSTEAddress(operand1);
        String op1 = GetParamPrefix(operand1) + op1address;
        String[] q = {tvicode, op1, null, null};
        Gen(q);
        
    }
    
    
    private void Generate(String tvicode, SymbolTableEntry operand1,
            String operand2) throws SymbolTableError
    {
        int op1address = GetSTEAddress(operand1);
        String op1 = getSTEPrefix(operand1) + Integer.toString(op1address);
        String[] q = {tvicode, op1, operand2, null};
        Gen(q);
    }
    
    private void Generate(String tvicode, SymbolTableEntry operand1, 
            SymbolTableEntry operand2, String operand3) throws SymbolTableError
    {
        int op1address = GetSTEAddress(operand1);
        int op2address = GetSTEAddress(operand2);
        String op1 = getSTEPrefix(operand1) + op1address;
        String op2 = getSTEPrefix(operand2) + op2address;
        String[] q = {tvicode, op1, op2, operand3};
        Gen(q);
    }
    
    private void Generate(String tvicode, SymbolTableEntry operand1,
            SymbolTableEntry operand2) throws SymbolTableError
    {
        int op1address = GetSTEAddress(operand1);
        int op2address = GetSTEAddress(operand2);
        String op1 = getSTEPrefix(operand1) + Integer.toString(op1address);
        String op2 = getSTEPrefix(operand2) + Integer.toString(op2address);
        String[] q = {tvicode, op1, op2, null};
        Gen(q);
    }
    
    private void Generate(String tvicode, String operand1,
            SymbolTableEntry operand2) throws SymbolTableError
    {
        int op2address = GetSTEAddress(operand2);
        String op2 = getSTEPrefix(operand2) + Integer.toString(op2address);
        String[] q = {tvicode, operand1, op2, null};
        Gen(q);
    }
    
    private void Generate(String tvicode, String operand1)
    {
        String[] q = {tvicode, operand1, null, null};
        Gen(q);
    }
    
    private void Generate(String tvicode, String operand1, int operand2)
    {
        String op2 = "" + operand2;
        String[] q = {tvicode, operand1, op2, null};
        Gen(q);
    }
    
    private void Generate(String tvicode, int operand1)
    {
        String op1 = Integer.toString(operand1);
        String[] q = {tvicode, op1, null, null};
        Gen(q);
    }
    
    private void Generate(String tvicode)
    {
        String[] q = {tvicode, null, null, null};
        Gen(q);
    }
    
    //end of generate functions-------------------------------------------------
    
    /*getSTEAddress
    INPUT: SymbolTable
    OUTPUT: Int - the address of the STE
    */
    private int GetSTEAddress(SymbolTableEntry ste) throws SymbolTableError
    {
        if (ste.IsArray() || ste.IsVariable())
            return ste.GetAddress();

        if (ste.IsConstant())
        {
            ConstantEntry ce = (ConstantEntry) ste;
            VariableEntry temp = Create("temp", ce.GetType());
            Generate("move", ste.GetName(), temp);
            return temp.GetAddress();
        }
        return 0; // never reaches
    }
    
    /*getSTEPrefix
    INPUT: SymbolTableEntry
    OUTPUT: String - either "_" for a global var or a "%" for a local var
    */
    private String getSTEPrefix(SymbolTableEntry ste){
        if (Global)
            return "_";
        
        else
        {
            SymbolTableEntry entry = LocalTable.LookUp(ste.GetName());
            if (entry == null)
            {
                SymbolTableEntry entry2 = ConstantTable.LookUp(ste.GetName());
                if (entry2 == null)
                    return "_";

                else
                    return "%";
            }
            else
            {
                if (ste.IsParameter())
                    return "^%";
                
                else
                    return "%";
            }
        }
    }
    
    /*VariableEntry
    INPUT: Two strings a name and a type 
    OUTPUT: Variable Entry
    This function is responsible for inserting a new variable entry into the appropriate
    symbol table and associateing it with a valid memory address
    */
    private VariableEntry Create(String name, String type) throws SymbolTableError{
        if(name.equals("temp"))
        {
            VariableEntry ve = new VariableEntry(name + TempCount, -1 ,type);
            if (Global)
            {
                ve.SetAddress(GlobalMemory);
                GlobalMemory++;
                GlobalTable.Insert(ve.GetName(), ve);
                TempCount++;
            }
            else
            {
                ve.SetAddress(LocalMemory);
                LocalMemory++;
                LocalTable.Insert(ve.GetName(), ve);
                TempCount++;
            }
            return ve;
        }
        else
        {
            VariableEntry ve = new VariableEntry(name, -1, type);
            if (Global)
            {
                ve.SetAddress(GlobalMemory);
                GlobalMemory++;
                GlobalTable.Insert(ve.GetName(), ve);
                TempCount++;
            }
            else
            {
                ve.SetAddress(LocalMemory);
                LocalMemory++;
                LocalTable.Insert(ve.GetName(), ve);
                TempCount++;
            }
            return ve;
        }
        
    }
    
    /*typeCheck
    INPUT: two STEs
    OUTPUT: an int which corresponds to the relation or the variable types
    */
    private int TypeCheck(SymbolTableEntry id1, SymbolTableEntry id2){
        String type1 = id1.GetType();
        String type2 = id2.GetType();
        if (type1.equalsIgnoreCase("INTEGER"))
        {
            if (type2.equalsIgnoreCase("INTEGER"))
                return 0;

            else
                return 3;

        } 
        else if (type2.equalsIgnoreCase("INTEGER"))
            return 2;

        else 
            return 1;

    }
    
    /*backpatch
    INPUT: two int's. x is a memory address to be entered in the quad at address i
    OUTPUT: void
    */
    private void Backpatch(int i, int x)
    {
        String y = Integer.toString(x);
        Quad.SetField(i, 1, y);
        
    }
    
    /*backpatch
    INPUT: list- as list of integers and x- an integer
    OUTPUT: void
    */
    private void Backpatch(List<Integer> list, int x)
    {
        for (Integer i : list)
        {
            if (Quad.GetField(i,0).equals("goto"))
                Quad.SetField(i, 1, ""+x);

            else
                Quad.SetField(i, 3, ""+x);

        }
    }
    
    /*lookupID
    INPUT: Token
    OUTPUT: STE of the given id
    */
    private SymbolTableEntry LookupID(Token id)
    {
        SymbolTableEntry ste = LocalTable.LookUp(id.GetValue());
        if (ste == null)
        {
            GlobalTable.DumpTable();
            ste = GlobalTable.LookUp(id.GetValue());
        }
        return ste;
    }
    
    /*merge
    INPUT: list1 and list2 which are both lists of integers
    OUTPUT: A list made by combining the two input lists
    */
    private List<Integer> Merge(List<Integer> list1, List<Integer> list2)
    {
      List<Integer> mergelist = new ArrayList<>();
      mergelist.addAll(list1);
      mergelist.addAll(list2);
      return mergelist;
    }    
    
    /*makeList
    INPUT: i- an int
    OUTPUT: a list with "i" as its only element
    */
    private List<Integer> MakeList(int i)
    {
        List<Integer> newlist = new ArrayList<>();
        newlist.add(i);
        return newlist;
    }
    
    /*getParamPrefix
    INPUT:STE - param
    OUTPUT: A string which indicated the prefix for parametes
    */
    private String GetParamPrefix(SymbolTableEntry param)
    {
        if (Global)
            return "@_";
 
        else 
        {
            if (param.IsParameter())
                return "%";

            else
                return "@%";
        }
    } 
    
    
    /*DumpStack method:
    INPUT: None
    OUTPUT: Void
    prints out the semanticStack
    */
    public void DumpStack()
    {
        if (DumpFlag)
        {
            Stack clonestack = (Stack)SemanticStack.clone(); //clone the stack
            System.out.print("Semantic Stack ::==> [ ");
            while (!clonestack.isEmpty())
                System.out.print(clonestack.pop() + " ");

            System.out.println("]");
        }
    }
    
}
