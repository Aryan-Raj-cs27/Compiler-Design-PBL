import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Lexer 
{
    public static boolean isKey(String w)
    {
        String[] key = {"int","double","float","String","if","else","elseif","while","for","class","public","static","void","return"};
        for(String k:key)
        {
            if(w.equals(k))
                return true;
        }
        return false;    
    }

    public static boolean isOp(String w)
    {
        String[] op = {"=",";","+","-","*","/","(",")","{","}","<",">","&&","||","%","==","<=",">=","!="};
        for (String o:op)
        {
            if(w.equals(o))
                return true;
        }
        return false;
    }

    public static void token_type(String w)
    {
        if (w.isEmpty())
            return;
        if(isKey(w))
        {
            System.out.println("Token: "+w+" -> Type : KEYWORD");
        }
        else if (isOp(w)) 
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
    public static void main(String[] args) 
    {
        try
        {
            File myCodeFile = new File("Module_1_Lexical_Analyzer/input_code.txt");
            Scanner sc = new Scanner(myCodeFile);
            System.out.println("--- Reading Input ---");
            while (sc.hasNextLine()) 
            {
                String L = sc.nextLine().trim();
                if(L.startsWith("//")||L.isEmpty())
                {
                    continue;
                }
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
                L = L.replace(" =  = ", " == ");
                L = L.replace(" <  = ", " <= ");
                L = L.replace(" >  = ", " >= ");
                L = L.replace(" !  = ", " != ");

                String[] words = L.trim().split("\\s+");
                for (String w : words)
                {
                    token_type(w);
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