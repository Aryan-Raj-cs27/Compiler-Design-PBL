import java.util.Scanner;
public class RecursiveDescentParser 
{
	static String input;
	static int pos = 0;

	static char peek() 
    {
		if (pos < input.length()) 
        {
			return input.charAt(pos);
		}
		return '\0';
	}

	static void consume() 
    {
		pos++;
	}

	static void match(char expected)
	{
		if (peek() == expected)
		{
			consume();
		}
		else
		{
			throw new RuntimeException("Syntax Error: Expected '" + expected + "' at position " + pos);
		}
	}

    public static int parseE() 
	{
        int val = parseT();
        while (peek() == '+' || peek() == '-') 
		{
            char op = peek();
            consume();
            if (op == '+') 
			{
                val = val + parseT();
            } 
			else 
			{
                val = val - parseT();
            }
        }
        return val;
    }

    public static int parseT() 
	{
        int val = parseF();
        while (peek() == '*' || peek() == '/') 
		{
            char op = peek();
            consume();
            if (op == '*') 
			{
                val = val * parseF();
            }
			else 
			{
                int nextVal = parseF();
                if (nextVal != 0) 
				{
                    val = val / nextVal;
                } 
				else 
				{
					throw new RuntimeException("Math Error: Division by zero at position "+pos);
                }
            }
        }
        return val;
    }

	static int parseF()
	{
		if (peek() == '(')
		{
			match('(');
			int val = parseE();
			match(')');
			return val;
		}
		if (Character.isDigit(peek()))
		{
			int val = 0;
			while (Character.isDigit(peek()))
			{
				val = val * 10 + (peek()-'0');
				consume();
			}
			return val;
		}

		throw new RuntimeException("Syntax Error: Unexpected character '"+peek()+"' at position "+pos);
	}

	public static void main(String[] args) 
    {
		System.out.println("--- Module 2 & 3: Syntax Parser ---");
		Scanner sc = new Scanner(System.in);
		while (true)
		{
			System.out.print("Enter expression (or type \"exit\" to quit): ");
			String rawInput = sc.nextLine();

			if (rawInput.equalsIgnoreCase("exit"))
			{
				System.out.println("Exiting parser. Goodbye!");
				break;
			}
			if (rawInput.trim().isEmpty())
			{
				continue;
			}
			input = rawInput.replaceAll("\\s+","");
			pos = 0;
			try
			{
				int result = parseE();
				if (pos == input.length())
				{
					System.out.println("Parsing successful! Result: "+result);
				}
				else
				{
					throw new RuntimeException("Syntax Error: Unexpected character at position " + pos);
				}
			}
			catch (RuntimeException e)
			{
				System.out.println(">>> " + e.getMessage());
			}
		}
		sc.close();
	}
}
