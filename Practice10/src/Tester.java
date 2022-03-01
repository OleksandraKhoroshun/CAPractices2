import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Tester {
    public static void main(String[] args) throws Exception {

        SearchDictionary sd = new SearchDictionary();

        Scanner sc = new Scanner(new File("text.txt"));

        while (sc.hasNext()) {
            String ss = sc.next();
            sd.addWord(ss);
        }
        sd.delWord("Oleh");
        sd.addWord("Oleha");

        System.out.println(sd.countWords());

        Iterable<String> string = sd.query("ol*");

        System.out.println(((ArrayList<String>) string).size());

        for (String st : string) {
            System.out.println(st);

        }
        Iterable<String> string1 = sd.query("*");
        for (String str : string1) {
          //  System.out.println(str);

        }
    }

}
