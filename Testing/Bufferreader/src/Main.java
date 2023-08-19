import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        // Press Alt+Enter with your caret at the highlighted text to see how
        // IntelliJ IDEA suggests fixing it.
        BufferedReader reader = new BufferedReader(
                new FileReader("C:\\Users\\taha_\\IdeaProjects\\helloworld\\src\\readme.txt")
        );
        String temp = "";
        ArrayList<String> words = new ArrayList<>();
        for (int c = reader.read(); c != -1; c = reader.read()) {
            char character = (char) c;
            if(character == ' ')
            {
                System.out.println(temp);
                words.add(temp);
                temp = "";
            }
            else {
                temp += character;
            }
        }
        System.out.println(temp);
        words.add(temp);

        System.out.println(words);
        System.out.println(words.size());




    }
}