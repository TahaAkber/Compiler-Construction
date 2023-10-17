package SyntaxAnalyzer;
import LexicalAnalyzer.TokenClass;
public class CFG {
    public static void main(String[] args) {
        TokenClass.loadToken(); // load tokens from file

        var parser = new LL1parser();
        System.out.println("SCOPE STACK: ");
        boolean syntax = parser.validate(TokenClass.tokenlist);
        System.out.println("----------------------------------------------");
        System.out.println("SYNTAX: "+syntax);
        System.out.println("----------------------------------------------");
    }
}
