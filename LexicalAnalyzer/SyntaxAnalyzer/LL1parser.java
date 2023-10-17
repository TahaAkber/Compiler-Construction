package SyntaxAnalyzer;
import LexicalAnalyzer.TokenClass;
import java.util.HashMap;
import java.util.List;



public class LL1Parser {
    //CTRL + SHIFT + - TO MINIMIZE FUNCTIONS AND COMMENTS
    private int index = 0;
    List <TokenClass> tokenList;
    //Selection Set
    private HashMap<String, String[][]> sSet;

    /**
     * Constructor Initializes Selection Set HashMap
     */
    public LL1Parser() {
        initializeSelectionSet();
    }


    /**
     * Validate function takes TokenList and parse through CFG
     * @param wordList TokenList
     * @return true if syntax/ TokenList match with Grammar
     */
    public boolean validate(List<TokenClass> wordList) {
        this.tokenList = wordList;
        boolean result = false;

        if (searchSelectionSet("begin")){
            if ( MAIN() )
                result = ( this.index == this.tokenList.size()-1);
        }

        if ( !result ) {
            TokenClass token = this.tokenList.get(this.index);
            if (token.error == null) {
                String errorTk = this.tokenList.get(this.index).valueP;
                if ( errorTk == null )
                    errorTk = this.tokenList.get(this.index).classP;

                System.err.println("Syntax Error, token:"+ errorTk + " on line number: "+ token.line);
            } else {
                System.err.println(token.error +": "+token.valueP+" on line no# "+ token.line);
                System.err.println(token.error +": "+token.classP+"\t"+token.valueP+" on line no# "+ token.line);
            }
        }

        emptyAndReset();
        return  result;
    }

    /**
     * Reset index and terminal list so that new terminal can be parse
     * @return  void
     */
    private void emptyAndReset() {
        index = 0;
        tokenList = null;
    }

    /**
     * Get Token from TokenList at position of global index current value
     * @return
     */
    private String getTokenCP() {
        return this.tokenList.get(index).classP;
    }

    /**
     * Search First Set words from Selection Set
     * @param
     * @param selectionSet
     * @return
     */
    private boolean searchSelectionSet(String selectionSet) {
        String word = getTokenCP();
        try {
            for (String cp : sSet.get(selectionSet)[0]) {
                if ( word.equals(cp)) {
                    return true;
                }
            }
            for (String cp : sSet.get(selectionSet)[1]) {
                if ( word.equals(cp)) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            System.out.println(word);
            System.out.println(selectionSet);
            String[] error = sSet.get(selectionSet)[0];
        }

        return false;
    }

    /**
     * Search Follow Set words from Selection Set when Non Terminal has null
     * @param selectionSet
     * @return
     */
    private boolean searchFollowSet(String selectionSet) {
        String word = getTokenCP();
        try {
            for (String cp : sSet.get(selectionSet)[1]) {
                if ( word.equals(cp)) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            System.out.println(word);
            System.out.println(selectionSet);
            String[] error = sSet.get(selectionSet)[1];
        }
        return false;
    }

    /**
     * If token match with the grammar word then index is incremented and
     * function return true.
     * @param nonTerminal
     * @return Boolean value, true if match
     */
    private boolean match(String nonTerminal) {
        if (getTokenCP().equals(nonTerminal)) {
            index++;
            return true;
        }
        return false;
    }
    /**
     * If token value part match with the grammar word then index is incremented and
     * function return true.
     * @param nonTerminal
     * @return Boolean value, true if match
     */
    private boolean matchVp(String nonTerminal) {
        String token = this.tokenList.get(index).valueP;
        if (token != null) {
            if (token.equals(nonTerminal)) {
                index++;
                return true;
            }
        }
        return false;
    }

    //selectionSet
    private void initializeSelectionSet(){

        sSet = new HashMap<>();

        // EXPRESSION
        sSet.put("EXPR", new String[][] {{"id", "const", "inc_dec", "("},{}});
        sSet.put("EXPR1", new String[][] {{"or", ")"},{"~"}});
        sSet.put("F", new String[][] {{"id", "const", "(", "inc_dec"},{}});
        sSet.put("F1", new String[][] {{"and","or", ")"},{"~"}});
        sSet.put("G", new String[][] {{"id", "const", "(", "inc_dec"},{}});
        sSet.put("G1", new String[][] {{"rop", "and", "or", ")"},{"~"}});
        sSet.put("H", new String[][] {{"id", "const", "(", "inc_dec"},{}});
        sSet.put("H1", new String[][] {{"pm", "rop", "and","or", ")"},{"~"}});
        sSet.put("I", new String[][] {{"id", "const", "(", "inc_dec"},{}});
        sSet.put("I1", new String[][] {{"mdm", "pm", "rop", "and","or", ")"},{"~"}});
        sSet.put("J", new String[][] {{"id", "const", "(", "inc_dec"},{}});
        sSet.put("J1", new String[][] {{"not", "mdm", "pm", "rop", "and", "or", ")"},{""~""}});
        sSet.put("K", new String[][] {{"id", "const", "(", "inc_dec"},{}});

        //3 DIMENSIONAL ARRAY
        sSet.put("3D_SST", new String[][] {{"int", "binary", "str", "id"},{}});
        sSet.put("SST_2", new String[][] {{"id"},{}});
        sSet.put("3DARRAY", new String[][] {{"["},{}});
        sSet.put("arr_max1", new String[][] {{"[", "=", "{", ";"},{"~"}});
        sSet.put("arr_max2", new String[][] {{"[", "=", "{", ";"},{"~"}});
        sSet.put("ARR_PAR", new String[][] {{"int_const", "]"},{"~"}});
        sSet.put("arr_exp", new String[][] {{"=", "{", ";"},{}});
        sSet.put("arr_exp1", new String[][] {{"{"},{}});
        sSet.put("arr_exp2", new String[][] {{"id", "{"},{}});
        sSet.put("arr_exp3", new String[][] {{"{"},{}});
        sSet.put("2D_PAR", new String[][] {{"{"},{}});
        sSet.put("PAR_LIST2", new String[][] {{",", "}"},{"~"}});
        sSet.put("2D_PAR1", new String[][] {{",", "}"},{"~"}});
        sSet.put("3D_PAR", new String[][] {{"{"},{}});
        sSet.put("PAR_LIST3", new String[][] {{",", "}"},{"~"}});
        sSet.put("PAR_LIST3_1", new String[][] {{",", "}"},{"~"}});
        sSet.put("3D_PAR1", new String[][] {{",", "}"},{"~"}});

        // FUNCTION DECLERATION
        sSet.put("FN_DEC", new String[][] {{"def"},{}});
        sSet.put("RET_TYPE", new String[][] {{"int", "binary", "str"},{}});
        sSet.put("RET_OBJ", new String[][] {{"[", "("},{"~"}});
        sSet.put("ARR_TYPE", new String[][] {{"["},{}});
        sSet.put("ARR_Upto3D", new String[][] {{"[", "("},{"~"}});
        sSet.put("ARR_Upto3D_1", new String[][] {{"[", "("},{"~"}});
        sSet.put("FN_ST", new String[][] {{"("},{}});
        sSet.put("PAR", new String[][] {{"int", "binary", "str", "id", ")"},{"~"}});
        sSet.put("PAR_LIST", new String[][] {{",", ")"},{"~"}});
        sSet.put("DTA", new String[][] {{"int", "binary", "str", "id"},{}});
        sSet.put("TYPE", new String[][] {{"int", "binary", "str", "id"},{}});

        // ASSIGNMENT
        sSet.put("ASSIG_SST", new String[][] {{"int", "binary", "str", "id"},{}});
        sSet.put("SST_2", new String[][] {{"id"},{}});
        sSet.put("ASSIG_ST", new String[][] {{".", "(", "inc_dec"},{"~"}});
        sSet.put("POS", new String[][] {{".", "(", "inc_dec", ";"},{"~"}});
        sSet.put("ARR_SUBSCRIPT", new String[][] {{"("},{}});
        sSet.put("SUBSCRIPT_LIST", new String[][] {{"(", ".", "inc_dec"},{"~"}});
        sSet.put("FN_CALL", new String[][] {{"("},{}});
        sSet.put("FN_BRACKETS", new String[][] {{"("},{}});
        sSet.put("ARG", new String[][] {{"(", "id", "const", "inc_dec", ")"},{"~"}});
        sSet.put("ARG_LIST", new String[][] {{","},{}});
        sSet.put("EXPR_OBJ", new String[][] {{"(", "id", "const", "inc_dec"},{}});
        sSet.put("DOT_ID_SUBSCRIPT", new String[][] {{"(", ".", ";"},{"~"}});
        sSet.put("POS2", new String[][] {{"(", "inc_dec"},{}});
        sSet.put("INC_DEC_DOT", new String[][] {{"inc_dec"},{}});
        sSet.put("INC_DEC_DOT1", new String[][] {{"inc_dec"},{}});
        sSet.put("DOT_ID_SUBSCRIPT", new String[][] {{"(", ".", ";"},{"~"}});

        // DECLERATION
        sSet.put("DEC_SST", new String[][] {{"id", "dt"},{}});
        sSet.put("SST_2", new String[][] {{"id"},{}});
        sSet.put("DEC", new String[][] {{"=", ".", "[", "'", ";"},{}});
        sSet.put("LIST", new String[][] {{";", ","},{}});
        sSet.put("INIT", new String[][] {{"=", "," , ";"},{"~"}});
        sSet.put("INIT4", new String[][] {{"id", "const"},{}});
        sSet.put("DOTTED", new String[][] {{"."},{}});
        sSet.put("ASSIGN", new String[][] {{"."},{}});
        sSet.put("ASSIGN2", new String[][] {{".", ";", ","},{"~"}});
        sSet.put("FN_TWO_ASSIGN", new String[][] {{"."},{}});
        sSet.put("FN_BRACKETS", new String[][] {{"("},{}});
        sSet.put("ARG", new String[][] {{"id", "const", "(", "inc_dec"},{}});
        sSet.put("ARG_LIST", new String[][] {{","},{}});
        sSet.put("EXPR_OBJ", new String[][] {{"id", "const", "(", "inc_dec"},{}});
        sSet.put("LIST2", new String[][] {{";", ","},{}});
        sSet.put("INIT2", new String[][] {{"=", ";", ","},{"~"}});
        sSet.put("INIT3", new String[][] {{"id", "const"},{}});
        sSet.put("VAR_ARR", new String[][] {{"[", "=", ",", ";"},{}});
        sSet.put("IS_INIT", new String[][] {{"=", ",", ";"},{}});
        sSet.put("LIST", new String[][] {{",", ";"},{}});
        sSet.put("INIT", new String[][] {{"parent", "self", "const", "id"},{}});
        sSet.put("IS_ACMETH", new String[][] {{"parent", "self"},{}});
        sSet.put("ACCESS_METH", new String[][] {{"parent", "self"},{}});

        // BEGIN (MAIN)
        sSet.put("MAIN", new String[][] {{"class", "int", "str"}, {} });
        sSet.put("DEF", new String[][] {{"begin", "dt" , "class"},{"~"}});
        sSet.put("MST", new String[][] {{";", "if", "shift", "const", "dt", "str", "id", "parent", "self", "loop", "do", "stop", "ret", "cont"},{}});

        // BREAK / CONTINUE
        sSet.put("BREAK", new String[][] { {"stop"}, {} });
        sSet.put("CONTINUE", new String[][] { {"cont"}, {} });
        sSet.put("L", new String[][] { {"id", ";"}, {} });

        // FOR LOOP
        sSet.put("FOR_LOOP", new String[][] { {"EXCLUDED"}, {}  });
        sSet.put("F1", new String[][] { {"int", "str", "binary", ";"},{}});
        sSet.put("F2", new String[][] {{"id", "const", "(", "inc_dec", ";"},{"~"}});
        sSet.put("F3", new String[][] {{"int", "binary", "str", "inc_dec"},{}});
        sSet.put("BODY", new String[][] {{";", "if", "shift", "const", "dt", "str", "id", "Parent", "Self", "test", "loop", "do", "stop", "ret", "cont", "raise", "{"},{}});

        // WHILE LOOP
        sSet.put("WHILE_LOOP", new String[][] { {"LOOP_TILL"}, {}  });

        // IF ELSE
        sSet.put("IF_ELSE", new String[][] { {"if"}, {}  });
        sSet.put("OELSE", new String[][] { {"else"}, {"~"}  });

        // SWITCH
        sSet.put("SWITCH", new String[][] { {"shift"}, {}  });
        sSet.put("STATE", new String[][] { {"state", "default"},{}});
                sSet.put("BASIC", new String[][] { {"default"}, {}  });
        sSet.put("SWITCH_BODY", new String[][] { {"{", "state", "if", "shift", "const", "dt", "str", "id" , "parent" , "self" ,"test","loop" , "do" , "stop" , "Ret" , "Cont" , "Raise"}, {}  });
        sSet.put("DEFAULT_BODY", new String[][] { {",", "{", "state", "if", "shift", "const", "dt", "str", "id" , "parent" , "self" ,"test","loop" , "do" , "stop" , "Ret" , "Cont" , "Raise"}, {}  });

        // OBJECT DECLERATION
        sSet.put("NEW_OBJ", new String[][] { {"id", "const"}, {}  });
        sSet.put("CNAME", new String[][] { {"id", "const"}, {}  });
                sSet.put("ATTR", new String[][] { {"=", ";"}, {}  });
        sSet.put("CONSTR_ARR", new String[][] { {"id", "dt", "str"}, {}  });

        // CLASS DECLERATION
        sSet.put("GLOBAL_CLASS", new String[][] { {"Class", "Abstract"}, {} });
        sSet.put("CLASS_DEC", new String[][] { {"Class"}, {} });
        sSet.put("CLASS_PAR", new String[][] { {"extends", "("}, {} });
        sSet.put("INHERIT", new String[][] { {"extends", "static", "const", "def", "int", "id" }, {} });

        //Class Body
        sSet.put("CLASS_BODY", new String[][] { {"def", "static", "const", "id", "dt", "str", "}"}, {} });
        sSet.put("ATTR_FUNC", new String[][] { {"def", "static", "const", "id", "dt", "str"}, {} });
        sSet.put("FN_CLASS_DEC", new String[][] { {"def"}, {}  });
        sSet.put("IS_ABSTRACT", new String[][] { {"Abstract", "static", "super"}, {}  });
        sSet.put("WITH_FINAL", new String[][] { {"id", "super", "private", "protected"}, {}  });
        sSet.put("RET_TO", new String[][] { {"id", "private", "protected"}, {}  });
        sSet.put("RET_TYPE_C", new String[][] { {"id", "private", "protected"}, {}  });
        sSet.put("RET_OBJ_C", new String[][] { {"id", "protected", "private","[" ,}, {}  });
        sSet.put("ACCESSMOD_C", new String[][] { {"private", "protected"}, {}  });
        sSet.put("ATTR_CLASS_DEC", new String[][] { {"id", "dt", "static", "const"}, {}  });
        sSet.put("IS_FINAL", new String[][] { {"id", "const", "dt"}, {}  });
        sSet.put("TYPE_VAR_ARR", new String[][] { {"id", "dt"}, {}  });
        sSet.put("VAR_ARR_C", new String[][] { {"id", "private", "protected","def","static", "const"}, {"~"}  });
        sSet.put("LIST_C", new String[][] { {",", ";"}, {} });
        sSet.put("VAR_C", new String[][] { {"id", "private", "protected","def","static", "const"}, {"~"}  });
        sSet.put("IS_INIT_C", new String[][] { {"parent", "self", "const","id",",", ";"}, {}  });


// CFG

// start structure ...

// EXPRESSION
        private boolean EXPR(){
            if(F()){
                if (EXPR1()){
                    return True;
                }
            }
            return False;
        }

        private boolean EXPR1(){
            if("OR" F()){
                if(EXPR1()){
                    return True;
                }
            }
    else{
                return false;
            }
        }

        private boolean F(){
            if(G()){
                if(F1()){
                    return True;
                }
            }
            return false;
        }

        private boolean F1(){
            if("AND" G()){
                if(F1()){
                    return True;
                }
            }
    else{
                return false;
            }
        }

        private boolean G(){
            if(H()){
                if(G1()){
                    return True;
                }
            }
            return false;
        }

        private boolean G1(){
            if("ROP" H()){
                if(G1()){
                    return True;
                }
            }
    else{
                return false;
            }
        }

        private boolean H(){
            if(I()){
                if(H1()){
                    return True;
                }
            }
            return false;
        }

        private boolean H1(){
            if("pm" I()){
                if(H1()){
                    return True;
                }
            }
    else{
                return false;
            }
        }

        private boolean I(){
            if(J()){
                if(I1()){
                    return True;
                }
            }
            return false;
        }

        private boolean I1(){
            if("mdm" J()){
                if(I1()){
                    return True;
                }
            }
    else{
                return false;
            }
        }

        private boolean J(){
            if(K()){
                if(J1()){
                    return True;
                }
            }
            return False;
        }

        private boolean J1(){
            if("NOT" K()){
                if(J1()){
                    return True;
                }
            }
    else{
                return false;
            }
        }

        private boolean K(){
            if(ID K1()){
                return True;
            }
            elseif(CONST()){
                return True;
            }
            elseif(EXPR()){
                return True;
            }
            elseif(INC_DEC()ID){
                return True;
            }
            return false;
        }

        private boolean K1(){
            if(FN_CALL()){
                return True;
            }
            if(INC_DEC()){
                return True;
            }
            else{
                return false;
            }
        }


// ARRAY 3 DIMENSIONAL

        private boolean 3D_SST(){
            if(DT()){
                if(SST_2()){
                    return True;
                }
            }
            elseif(SST_2()){
                return True;
            }
            return False;
        }

        private boolean SST_2(){
            if("ID" 3DARRAY()){
                return True;
            }
            return false;
        }

        private boolean 3DARRAY(){
            if("[" ARR_PAR() "]"){
                if(arr_max1()){
                    if(arr_exp()){
                        return True;
                    }
                }
            }
            return false;
        }

        private boolean arr_max1(){
            if("[" ARR_PAR() "]"){
                if(arr_max2()){
                    return True;
                }
            }
    else{
                return false;
            }
        }

        private boolean arr_max2(){
            if("[" ARR_PAR()"]"){
                return True;
            }
    else{
                return false;
            }
        }

        private boolean ARR_PAR(){
            if("int_const"){
                return True;
            }
            else{
                return false;
            }
        }

        private boolean arr_exp(){
            if(arr_exp1()){
                return True;
            }
            elseif("{};"){
                return True;
            }
            elseif(";"){
                return True;
            }
            return False;
        }

        private boolean arr_exp1(){
            if("{" arr_exp2()"};"){
                return True;
            }
            return false;
        }

        private boolean arr_exp2(){
            if("ID" PAR_LIST()){
                return True;
            }
            elseif(arr_exp3()){
                return True;
            }
            return false;
        }

        private boolean arr_exp3(){
            if(2D_PAR()){
                return True;
            }
            elseif(3D_PAR()){
                return True;
            }
            return false;
        }

        private boolean 2D_PAR(){
            if("{ID" PAR_LIST2() "}"){
                return True;
            }
            elseif("{ }"){
                return false;
            }
            elseif(2D_PAR()){
                return True;
            }
        }

        private boolean PAR_LIST2(){
            if(",ID"){
                return True;
            }
            else{
                return false;
            }
        }

        private boolean 2D_PAR1(){
            if("," 2D_PAR()){
                return True;
            }
    else{
                return false;
            }
        }

        private boolean 3D_PAR(){
            if("{ID" PAR_LIST3() "}" 3D_PAR()){
                return True;
            }
            elseif("{ }" 3D_PAR()){
                return false;
            }
        }

        private boolean PAR_LIST3(){
            if(",ID" PAR_LIST3_1()){
                return True;
            }
    else{
                return false;
            }
        }

        private boolean PAR_LIST3_1(){
            if(",ID" PAR_LIST3()){
                return True;
            }
    else{
                return false;
            }
        }

        private boolean 3D_PAR1(){
            if(3D_PAR()){
                return True;
            }
    else{
                return false;
            }
        }

// FUNCTION DECLERATION

        private boolean FN_DEC(){
            if("def" RET_TYPE()){
                if(FN_ST()){
                    if("{" MST() "}"){
                        return True;
                    }
                }
            }
            return False;
        }

        private boolean RET_TYPE(){
            if(DT() "ID"){
                if(RT_OBJ()){
                    return True;
                }
            }
            return False;
        }

        private boolean RT_OBJ(){
            if(ARR_TYPE()){
                return True;
            }
            else{
                return False;
            }
        }

        private boolean ARR_TYPE(){
            if("[ ]" ARR_Upto3D()){
                return True;
            }
            return False;
        }

        private boolean ARR_Upto3D(){
            if("[]"ARR_Upto3D_1()){
                return True;
            }
    else{
                return False;
            }
        }

        private boolean ARR_Upto3D_1(){
            if("[ ]"){
                return True;
            }
            else{
                return False;
            }
        }

        private boolean FN_ST(){
            if("( " PAR() ")"){
                return True;
            }
            return False;
        }

        private boolean PAR(){
            if(DTA() "ID"){
                if(PAR_LIST()){
                    return True;
                }
            }
    else{
                return False;
            }
        }

        private boolean PAR_LIST(){
            if("," DTA() "ID"){
                if(PAR_LIST()){
                    return True;
                }
            }
    else{
                return False;
            }
        }

        private boolean DTA(){
            if(TYPE()){
                if(ARR_TYPE()){
                    return True;
                }
            }
            return False;
        }

        private boolean TYPE(){
            if(DT()){
                return True;
            }
            elseif("ID"){
                return True;
            }
    else{
                return False;
            }
        }

// ASSIGNMENT

        private boolean ASSIG_SST(){
            if(DT()){
                if(SST_2()){
                    return True;
                }
            }
            elseif(SST_2){
                return True;
            }
            return False;
        }

        private boolean SST_2(){
            if("ID" ASSIG_SST()){
                return True;
            }
            return False;
        }

        private boolean ASSIGN_SST(){
            if(POS() ";"){
                return True;
            }
    else{
                return False;
            }
        }

        private boolean POS(){
            if(DOT_ID_SUBSCRIPT()){
                return True;
            }
            elseif(FN_BRACKET()){
                if(DOT_ID_SUBSCRIPT){
                    return True;
                }
            }
            elseif(POS2()){
                return True;
            }
    else{
                return False;
            }
        }

        private boolean ARR_SUBSCRIPT(){
            if("(" EXPR() ")"){
                if(SUBSCRIPT_LIST()){
                    return True;
                }
            }
            return False;
        }

        private boolean SUBSCRIPT_LIST(){
            if(ARR_SUBSCRIPT()){
                return True;
            }
            else{
                return False;
            }
        }

        private boolean FN_CALL(){
            if(FN_BRACKETS() ";"){
                return True;
            }
            return False;
        }

        private boolean FN_BRACKETS(){
            if("(" ARG() ")"){
                return True;
            }
            return False;
        }

        private boolean ARG(){
            if(EXPR_OBJ()){
                if(ARG_LIST()){
                    return True;
                }
            }
            else{
                return False;
            }
        }

        private boolean ARG_LIST(){
            if("," EXPR_OBJ()){
                if(ARG_LIST()){
                    return True;
                }
            }
            return False;
        }

        private boolean EXPR_OBJ(){
            if(EXPR()){
                return True;
            }
            elseif(NEW_OBJ()){
                return True;
            }
            return False;
        }

        private boolean DOT_ID_SUBSCRIPT(){
            if(".ID " POS()){
                return True;
            }
            elseif(ARR_SUBSCRIPT()){
                if(POS()){
                    return True;
                }
            }
    else{
                return False;
            }
        }

        private boolean POS2(){
            if(INC_DECDOT()){
                return True;
            }
            elseif(FN_BRACKETS()){
                if(DOT_ID_SUBSCRIPT()){
                    return True;
                }
            }
            return False;
        }

        private boolean INC_DECDOT(){
            if(INC_DEC()){
                if(INC_DECDOT1()){
                    return True;
                }
            }
            return False;
        }

        private boolean INC_DECDOT1(){
            if(DOT_ID_SUBSCRIPT2()){
                return True;
            }
            else{
                return False;
            }
        }

        private boolean DOT_ID_SUBSCRIPT2(){
            if(".ID" POS2()){
                return True;
            }
            elseif(ARR_SUBSCRIPT()){
                if(POS2()){
                    return True;
                }
            }
    else{
                return False;
            }
        }

// DECLERATION

        private boolean DEC_SST(){
            if(DT()){
                if(SST_2()){
                    return True;
                }
            }
            elseif(SST_2()){
                return True;
            }
            return False;
        }

        private boolean SST_2(){
            if("ID" DEC()){
                return True;
            }
            return False;
        }

        private boolean DEC(){
            if(INIT()){
                if(LIST()){
                    return True;
                }
            }
            elseif(DOTTED()){
                return True;
            }
            elseif(VAR_ARR()){
                return True;
            }
            return False;
        }

        private boolean LIST(){
            if(";"){
                return True;
            }
            elseif(",ID" INIT()){
                if(LIST()){
                    return True;
                }
            }
            return False;
        }

        private boolean INIT(){
            if("=" INIT4()){
                return True;
            }
    else{
                return False;
            }
        }

        private boolean INIT4(){
            if("ID" INIT()){
                return True;
            }
            elseif(CONST()){
                return True;
            }
            return False;
        }

        private boolean DOTTED(){
            if(ASSIGN()){
                if(LIST2()){
                    return True;
                }
            }
            return False;
        }

        private boolean ASSIGN(){
            if(".ID" ASSIGN2()){
                return True;
            }
            elseif(FN_TWO_ASSIGN()){
                return True;
            }
            return False;
        }

        private boolean ASSIGN2(){
            if(".ID" ASSIGN2()){
                return True;
            }
    else{
                return False;
            }
        }

        private boolean FN_TWO_ASSIGN(){
            if(".fn" FN_BRACKETS()){
                if(ASSIGN2){
                    return True;
                }
            }
            return False;
        }

        private boolean FN_BRACKETS(){
            if(ARG()){
                return True;
            }
            else{
                return False;
            }
        }

        private boolean ARG(){
            if(EXPR_OBJ()){
                if(ARG_LIST()){
                    return True;
                }
            }
            return False;
        }

        private boolean ARG_LIST(){
            if("," EXPR_OBJ()){
                if(ARG_LIST()){
                    return True;
                }
            }
            return False;
        }

        private boolean EXPR_OBJ(){
            if(EXPR()){
                return True;
            }
            elseif(NEW_OBJ()){
                return True;
            }
            return False;
        }

        private boolean LIST2(){
            if(";"){
                return True;
            }
            elseif(",ID" INIT2()){
                if(LIST2()){
                    return True;
                }
            }
            return False;
        }

        private boolean INIT2(){
            if("=" INIT3()){
                return True;
            }
    else{
                return False;
            }
        }

        private boolean INIT3(){
            if("ID" INIT2()){
                return True;
            }
            elseif(CONST()){
                return True;
            }
            return False;
        }

        private boolean VAR_ARR(){
            if(ARR_TYPE()){
                if(IS_INIT()){
                    return True;
                }
            }
            elseif(IS_INIT()){
                return True;
            }
            return False;
        }

        private boolean IS_INIT(){
            if("=" INIT()){
                if(LIST()){
                    return True;
                }
            }
            elseif(LIST()){
                return True;
            }
            return False;
        }

        private boolean LIST(){
            if(",ID" IS_INIT()){
                return True;
            }
            elseif(";"){
                return True;
            }
            return False;
        }

        private boolean INIT(){
            if(IS_ACMETH() "ID"){
                if(EXPR()){
                    return True;
                }
            }
            elseif(NEW_OBJ()){
                return True;
            }
            return False;
        }

        private boolean IS_ACMETH(){
            if(ACCESS_METH()){
                return True;
            }
            else{
                return False;
            }
        }

        private boolean ACCESS_METH(){
            if("parent"){
                return True;
            }
            elseif("self"){
                return True;
            }
            return False;
        }

// BEGIN (MAIN)

        private boolean MAIN(){
            if(DEF() "begin {"){
                if(MST() "}"){
                    if(DEF()){
                        return True;
                    }
                }
            }
            return False;
        }

        private boolean DEF(){
            if(CLASS_DEC()){
                return True;
            }
            elseif(FN_DEC){
                return True;
            }
            elseif(DEC_SST){
                return True;
            }
    else{
                return False;
            }
        }

// BREAK | CONTINUE

        private boolean BREAK(){
            if("stop" L()){
                return True;
            }
            return False;
        }

        private boolean CONTINUE(){
            if("cont" L()){
                return True;
            }
            return False;
        }

        private boolean L(){
            if("ID:"){
                return True;
            }
            elseif(";"){
                return True;
            }
            return False;
        }

// FOR LOOP

        private boolean FOR_LOOP(){
            if("EXCLUDED(" F1()){
                if(F2()){
                    if(";" F3() ")"){
                        if(BODY()){
                            return True;
                        }
                    }
                }
            }
            return False;
        }

        private boolean F1(){
            if(DEC_SST()){
                return True;
            }
            elseif(ASSIGN_SST()){
                return True;
            }
            elseif(";"){
                return True;
            }
            return False;
        }

        private boolean F2(){
            if(EXPR()){
                return True;
            }
            else{
                return False;
            }
        }

        private boolean F3(){
            if(INC_DEC()){
                return True;
            }
            elseif(ASSIGN_SST()){
                return True;
            }
    else{
                return False;
            }
        }

// WHILE LOOP

        private boolean WHILE_LOOP(){
            if("LOOP_TILL(" EXPR() ")"){
                if(BODY()){
                    return True;
                }
            }
            return False;
        }

// IF ELSE

        private boolean IF_ELSE(){
            if("if(" EXPR() ")"){
                if(BODY()){
                    if(OELSE()){
                        return True;
                    }
                }
            }
            return False;
        }

        private boolean OELSE(){
            if("else" BODY()){
                return True;
            }
    else{
                return False;
            }
        }

// SWITCH CASE

        private boolean SWITCH(){
            if("shift(" EXPR() ")"){
                if("{" STATE() "}"){
                    return True;
                }
            }
            return False;
        }

        private boolean STATE(){
            if("state" CONST()){
                if(":" SWITCH_BODY()){
                    return True;
                }
            }
            elseif(BASIC()){
                return True;
            }
            return False;
        }

        private boolean BASIC(){
            if("default:" DEFAULT_BODY()){
                return True;
            }
            return False;
        }

        private boolean SWITCH_BODY(){
            if("{" MST() "}"){
                if(STATE()){
                    return True;
                }
            }
            elseif(MST()){
                if(STATE()){
                    return True;
                }
            }
            return False;
        }

        private boolean DEFAULT_BODY(){
            if(MST()){
                return True;
            }
            elseif("{" MST() "}"){
                return True;
            }
            return False;
        }

// OBJECT DECLERATION

        private boolean NEW_OBJ(){
            if(CNAME() "ID"){
                if(ATTR()){
                    return True;
                }
            }
            return False;
        }

        private boolean CNAME(){
            if(CONST()){
                return True;
            }
            elseif(CLASS_NAME()){
                return True;
            }
            return False;
        }

        private boolean ATTR(){
            if("=" FN_DEC()){
                return True;
            }
            elseif("=new" CONSTR_ARR()){
                return True;
            }
            elseif(";"){
                return True;
            }
            return False;
        }

        private boolean CONSTR_ARR(){
            if("ID" FN_BRACKETS() ";"){
                return True;
            }
            elseif(DT()){
                if(FN_BRACKETS){
                    return True;
                }
            }
            elseif(FN_BRACKETS() ";"){
                return True;
            }
            return False;
        }

// CLASS DECLERATION

        private boolean GLOBAL_CLASS(){
            if(CLASS_DEC()){
                return True;
            }
            elseif("Abstract" CLASS_DEC()){
                return True;
            }
            return False;
        }

        private boolean CLASS_DEC(){
            if("class ID" CLASS_PAR()){
                if(INHERIT()){
                    return True;
                }
            }
            return False;
        }

        private boolean CLASS_PAR(){
            if("(ID)"){
                return True;
            }
            else{
                return False;
            }
        }

        private boolean INHERIT(){
            if("extends ID" CLASS_PAR()){
                if("{" CLASS_BODY() "}"){
                    return True;
                }
            }
            elseif(CLASS_BODY()){
                return True;
            }
            return False;
        }

        private boolean CLASS_BODY(){
            if(ATTR_FUNC()){
                if(CLASS_BODY()){
                    return True;
                }
            }
            return False;
        }

        private boolean ATTR_FUNC(){
            if(FN_CLASS_DEC()){
                return True;
            }
            elseif(ATTR_CLASS_DEC()){
                return True;
            }
            elseif(NEW_OBJ()){
                return True;
            }
            return False;
        }

// FUNCTION DECLERATION IN CLASS

        private boolean FN_CLASS_DEC(){
            if("def" IS_ABSTRACT()){
                return True;
            }
            return False;
        }

        private boolean IS_ABSTRACT(){
            if("Abstract" RET_TO() ";"){
                return True;
            }
            elseif("Static" WITH_FINAL()){
                if("{" MST() "}"){
                    return True;
                }
            }
            elseif( WITH_FINAL()){
                if("{" MST() "}"){
                    return True;
                }
            }
            return False;
        }

        private boolean WITH_FINAL(){
            if("super" RET_TO()){
                return True;
            }
            elseif(RET_TO()){
                return True;
            }
            return False;
        }

        private boolean RET_TO(){
            if("RET_TYPE_C()){
            if(FN_ST()){
                return True;
            }
        }
        return False;
}

        private boolean RET_TYPE_C(){
            if("ID" RET_OBJ_C()){
                return True;
            }
            elseif(ACCESSMOD_C()){
                return True;
            }
            elseif("ID"){
                return True;
            }
            return False;
        }

        private boolean RET_OBJ_C(){
            if(ARR_TYPE()){
                if(ACCESSMOD() "ID"){
                    return True;
                }
            }
            elseif(ACCESSMOD_C() "ID"){
                return True;
            }
            elseif("ID"){
                return True;
            }
    else{
                return False;
            }
        }

        private boolean ACCESSMOD_C(){
            if("private"){
                return True;
            }
            elseif("protected"){
                return True;
            }
            return False;
        }

// ATTRIBUTE DECLERATION IN CLASS

        private boolean ATTR_CLASS_DEC(){
            if("Static" IS_FINAL()){
                return True;
            }
            elseif(IS_FINAL()){
                return True;
            }
            return False;
        }

        private boolean IS_FINAL(){
            if("FINAL" TYPE_VAR_ARR()){
                return True;
            }
            elseif(TYPE_VAR_ARR()){
                return True;
            }
            return False;
        }

        private boolean TYPE_VAR_ARR(){
            if(TYPE()){
                if(VAR_ARR_C()){
                    return True;
                }
            }
            return False;
        }

        private boolean VAR_ARR_C(){
            if(ARR_TYPE()){
                if(VAR_C()){
                    return True;
                }
            }
            elseif(VAR_C()){
                return True;
            }
            return False;
        }

        private boolean VAR_C(){
            if(ACCESSMOD() "ID"){
                if(IS_INIT_C()){
                    return True;
                }
            }
            return False;
        }

        private boolean IS_INIT_C(){
            if("=" INIT()){
                if(LIST_C()){
                    return True;
                }
            }
            elseif(LIST()){
                return True;
            }
            return False;
        }

        private boolean LIST_C(){
            if("," ACCESSMOD()){
                if("ID" IS_INIT_C()){
                    return True;
                }
            }
            elseif(";"){
                return True;
            }
            return False;
        }