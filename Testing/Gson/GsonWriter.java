
        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import com.google.gson.reflect.TypeToken;
        import java.io.File;

        import java.io.FileWriter;   // Import the FileWriter class
        import java.io.IOException;  // Import the IOException class to handle errors
        import java.util.ArrayList;
        import java.util.List;
/**
 *
 * @author omer
 */
public class GsonWriter {
    public static void main(String[] args) {
        ABC.createTokenList();//CLASS STATIC VARIABLE AK ARRAY INTIALZIE
        ABC Hello1 = new ABC(5,"ID","Taha");
        ABC Hello2 = new ABC(2,"DT","String");
        ABC Hello3 = new ABC(7,"for","for");

        ABC.addToken(Hello1);//tokens are added in loops
        ABC.addToken(Hello2);
        ABC.addToken(Hello3);



        ABC.saveToken();//When all tokens are generated






//        GsonBuilder builder = new GsonBuilder();
//        builder.setPrettyPrinting();
//        Gson gson = builder.create();
//
//        System.out.println(gson.toJson(tokenList));
//        ABC NewHello = gson.fromJson(gson.toJson(Hello), ABC.class);
//        System.out.println(NewHello.a);


//        try {
//            FileWriter myWriter = new FileWriter("Test\\JsonTesting\\json.json");
//            myWriter.write(gson.toJson(tokenList));
//            myWriter.close();
//            System.out.println("Successfully wrote to the file.");
//          } catch (IOException e) {
//            System.out.println("An error occurred.");
//          }

    }
}
class ABC {

    // Class Attribute
    static List <ABC> tokenList;

    // Object Attributes
    String classPart;
    String valuePart;
    int line;
    String error;


    //Constructor--------------------------------------------------------------
    public ABC(int line,String classPart, String valuePart) {

        this.classPart = classPart;
        this.line = line;
        // Class part is not equal to value then add value else
        if ( !(classPart.equals(valuePart)) ) {
            this.valuePart = valuePart;
        }
    }


    // Static SAVE METHOD-------------------------------------------------------
    // Create TokenList
    static void createTokenList() {
        ABC.tokenList = new ArrayList<>();
    }

    // Add Token
    static void addToken(ABC t) {
        ABC.tokenList.add(t);
    }

    // Save Token in Json File
    static void saveToken() {
        GsonBuilder builder = new GsonBuilder();
        //builder.setPrettyPrinting();
        Gson gson = builder.create();
        try {
            //Create Token Json File
            File myObj = new File("D:\\test\\json.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            // Write Token Json File
            FileWriter writer = new FileWriter("D:\\test\\json.txt");

            String jsonString  = "[";
            if (!ABC.tokenList.isEmpty()){
                jsonString += gson.toJson(ABC.tokenList.get(0));
            }
            for (int i = 1; i < ABC.tokenList.size(); i++) {
                jsonString += ",\n";
                jsonString += gson.toJson(ABC.tokenList.get(i));

            }
            jsonString += "]";
            writer.write(jsonString);
            writer.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }


    // Object Method------------------------------------------------------------
    // Set The Error Message
    public void setError(String error) {
        this.error = error;
    }

    // Override function
    // When Print Show attributes
    @Override
    public String toString() {
        String returnString = this.classPart+"-"+this.valuePart+"-"+this.line;
        if (this.error != null){
            returnString += "-"+this.error;
        }
        return returnString;
    }
}