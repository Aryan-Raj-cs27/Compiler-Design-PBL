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
            System.out.println("--- Lexical Analysis (Module 1) ---");

            // To make this project work on ANY computer after download:
            // - If a file path is provided, tokenize that file.
            // - Otherwise, tokenize a small built-in demo program.

            if (args.length > 0) {
                File inputFile = new File(args[0]);
                tokenizeFromScanner(new Scanner(inputFile));
            } else {
                String demoProgram = "int a = 2 * 3 + 4; print a;";
                tokenizeLine(demoProgram);
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Input File NOT FOUND.");
            e.printStackTrace();
        }
    }

    private static void tokenizeFromScanner(Scanner sc) {
        System.out.println("--- Reading Input File ---");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            tokenizeLine(line);
        }
        sc.close();
    }

    private static void tokenizeLine(String line) {
        String L = line.trim();
        if (L.startsWith("//") || L.isEmpty()) {
            return;
        }

        // Add whitespace around operators/punctuators so split() works.
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

        // Fix accidental spacing for multi-character operators after initial replaces.
        L = L.replace(" =  = ", " == ");
        L = L.replace(" <  = ", " <= ");
        L = L.replace(" >  = ", " >= ");
        L = L.replace(" !  = ", " != ");

        String[] words = L.trim().split("\\s+");
        for (String w : words) {
            token_type(w);
        }
    }
}