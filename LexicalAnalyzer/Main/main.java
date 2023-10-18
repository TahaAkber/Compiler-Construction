    package Main;
    import LexicalAnalyzer.Tokenizer;
    import SyntaxAnalyzer.CFG;
public class main {
    public static void main(String[] args){
        Tokenizer.main();// Create Token and save it into Tokens.txt
        CFG.main(args);// parse token from stream

    }
}
