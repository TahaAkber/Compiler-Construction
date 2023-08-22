
package LexicalAnalyzer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TokenClass {

    public static List<TokenClass> tokenlist;
    public int line;
    public String classP;
    public String valueP;
    public String error;

    public TokenClass( String classP, String valueP,int line) {
        this.classP = classP;
        this.valueP = valueP;
        this.line = line;
    }




    // set error / show error
    public void setError(String Error) {
        this.error = Error;
        System.out.println(Error + " IM IN TOKEN CLASS----------");
    }

    public String toString() {
        String returnString = this.classP + "-" + this.valueP + "-" + this.line;
        if (this.error != null) {
            returnString += "-" + this.error;
        }
        return returnString;
    }

    //Create token
    static void createTokenList() {
        TokenClass.tokenlist = new ArrayList<>();
    }

    ;

    //add token
    public static void addToken(TokenClass t) {
        TokenClass.tokenlist.add(t);
    }

    public static void saveToken() {
        // Save Token in Json File
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        Gson gson = builder.create();

        try {
            File myobj = new File("C:\\Users\\taha_\\OneDrive\\Documents\\GitHub\\Compiler-Construction\\LexicalAnalyzer\\LexicalAnalyzer\\Tokens.txt");
            if (myobj.createNewFile()) System.out.println("File Created" + myobj.getName());
            else {
                System.out.println("File already existed");
            }
            FileWriter Writer = new FileWriter("C:\\Users\\taha_\\OneDrive\\Documents\\GitHub\\Compiler-Construction\\LexicalAnalyzer\\LexicalAnalyzer\\Tokens.txt");
            String jsonString = "[";
            if (!TokenClass.tokenlist.isEmpty()) {
                jsonString += gson.toJson(TokenClass.tokenlist.get(0));
            }
            for (int i = 1; i < TokenClass.tokenlist.size(); i++) {
                jsonString += ",\n";
                jsonString += gson.toJson(TokenClass.tokenlist.get(i));

            }
            Writer.write(jsonString);
            Writer.close();
            System.out.println("Successfully save Token to the file.");

            jsonString += "]";
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }


    }
};

//public static void main(String [] args){
//
//
//            // Create some tokens and add them to the token list
//            TokenClass.createTokenList();
//
//            TokenClass token1 = new TokenClass(1, "Keyword", "if", null);
//            TokenClass token2 = new TokenClass(2, "Identifier", "variable", null);
//            TokenClass token3 = new TokenClass(3, "Operator", "+", null);
//
//            TokenClass.addToken(token1);
//            TokenClass.addToken(token2);
//            TokenClass.addToken(token3);
//
//            // Set an error for a token
//            token2.setError("Undefined variable");
//
//            // Save the token list to a file
//            TokenClass.saveToken();
//
//            // Print the token list
//            System.out.println("Token List:");
//            for (TokenClass token : TokenClass.tokenlist) {
//                System.out.println(token);
//            }
//        }
//
//};


