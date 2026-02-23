import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Lexer 
{
    public static void main(String[] args) 
    {
        try
        {
            File myCodeFile = new File("Module_1_Lexical_Analyzer/input_code.txt");
            Scanner sc = new Scanner(myCodeFile);
            System.out.println("--- Reading Input ---");
            while (sc.hasNextLine()) 
            {
                String l = sc.nextLine();
                System.out.println(l);
            }
            sc.close();
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Input File NOT FOUND.");
            e.printStackTrace();
        }
    }
}