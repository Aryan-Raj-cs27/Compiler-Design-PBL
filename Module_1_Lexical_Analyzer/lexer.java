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
                String L = sc.nextLine();
                L = L.replace("=", " = ");
                L = L.replace(";", " ; ");
                L = L.replace("(", " ( ");
                L = L.replace(")", " ) ");
                L = L.replace("{", " { ");
                L = L.replace("}", " } ");
                L = L.replace("<", " < ");
                L = L.replace(">", " > ");
                L = L.replace("&&", " && ");
                L = L.replace("||", " || ");
                L = L.replace("+", " + ");
                L = L.replace("-", " - ");
                L = L.replace("*", " * ");
                L = L.replace("/", " / ");
                L = L.replace("%", " % ");
                String[] words = L.trim().split("\\s+");
                for (String w : words)
                {
                    if (w.equals("int")||w.equals("if")||w.equals("while")||w.equals("return")||w.equals("float")||w.equals("double")||w.equals("class")||w.equals("public"))
                    {
                        System.out.println("Token: "+w+" -> Type : KEYWORD");
                    }
                    else if(w.equals("=")||w.equals(";")||w.equals("(")||w.equals(")")||w.equals("{")||w.equals("}")||w.equals("<")||w.equals(">")||w.equals("&&")||w.equals("||")||w.equals("+")||w.equals("-")||w.equals("*")||w.equals("/")||w.equals("%"))
                    {
                        System.out.println("Token: "+w+" -> Type : OPERATOR");
                    }
                    else if (Character.isDigit(w.charAt(0))) 
                    {
                        System.out.println("Token: "+w+" -> Type : NUMBER");
                    }
                    else
                    {
                        System.out.println("Token: "+w+" -> Type : IDENTIFIER");
                    }
                }
                
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