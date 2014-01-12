/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Edwin
 */
/* Importing Java IO class */
import java.io.*;

public class SentenceCases
{



    public static final int UPPER_CASE = 1;
    public static final int lower_case = 2;

    public static int CASE = 0;

    public static final int TITLE_CASE = 4;
    
    private static StringBuffer buffer = new StringBuffer();

    public static String toggle(String text, int cases)
    {
        if(text == null)
            return "";

        
        buffer.delete(0, buffer.length());

        switch(cases)
        {
            case TITLE_CASE:
                toTitleCases(text);
                break;
        }

        return buffer.toString();

    }

    private static void toTitleCases(String text)
    {
        text = text.trim();
        
        buffer.append(Character.toUpperCase(text.charAt(0)));
        for (int i = 1; i < text.length(); i++)
        {
            if (text.charAt(i) == ' ')
            {
                buffer.append(" ");
                buffer.append(Character.toUpperCase(text.charAt(i + 1)));
                i++;
            } else
            {
                buffer.append(Character.toLowerCase(text.charAt(i)));
            }
        }
    }

    static void toggle() throws IOException
    {
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(in);
        System.out.println("Enter a sentence: ");
        String text = br.readLine();
        /* Trimming the sentence incase the user enters unwanted whitespaces. */
        text.trim();
        System.out.print("What do you want to do?\n1)UPPERCASE\n2)lowercase\n3)Sentence case\n4)Title Case\n5)tOGGLE cASE\n6)Enter another sentence\n7)Quit\n\nOption: ");
        String s1 = br.readLine();
        /* Parsing the choice the user entered to an integer. */
        int x = Integer.parseInt(s1);
        while (x != 7)
        {
            if (x == 1)
            {
                System.out.println(text.toUpperCase());
            } else if (x == 2)
            {
                System.out.println(text.toLowerCase());
            } else if (x == 3)
            {
                String s2 = "";
                s2 += Character.toUpperCase(text.charAt(0));
                for (int i = 1; i < text.length(); i++)
                {
                    if (Character.isUpperCase(text.charAt(i)))
                    {
                        s2 += Character.toLowerCase(text.charAt(i));
                    } else
                    {
                        s2 += text.charAt(i);
                    }
                }
                System.out.println(s2);
            } else if (x == 4)
            {
                String s2 = "";
                s2 += Character.toUpperCase(text.charAt(0));
                for (int i = 1; i < text.length(); i++)
                {
                    if (text.charAt(i) == ' ')
                    {
                        s2 += " ";
                        s2 += Character.toUpperCase(text.charAt(i + 1));
                        i++;
                    } else
                    {
                        s2 += text.charAt(i);
                    }
                }
                System.out.println(s2);
            } else if (x == 5)
            {
                String s2 = "";
                for (int i = 0; i < text.length(); i++)
                {
                    if (Character.isLowerCase(text.charAt(i)))
                    {
                        s2 += Character.toUpperCase(text.charAt(i));
                    } else if (Character.isUpperCase(text.charAt(i)))
                    {
                        s2 += Character.toLowerCase(text.charAt(i));
                    } else if (text.charAt(i) == ' ')
                    {
                        s2 += text.charAt(i);
                    }
                }
                System.out.println(s2);
            } else if (x == 6)
            {
                System.out.println("\nEnter another sentence:");
                text = br.readLine();
                System.out.print("\nWhat do you want to do?\n1)UPPERCASE\n2)lowercase\n3)Sentence case\n4)Title Case\n5)tOGGLE cASE\n6)Enter another sentence\n7)Quit\n\nOption: ");
                s1 = br.readLine();
                x = Integer.parseInt(s1);
                continue;
            }
            System.out.print("\nWhat do you want to do?\n1)UPPERCASE\n2)lowercase\n3)Sentence case\n4)Title Case\n5)tOGGLE cASE\n6)Enter another sentence\n7)Quit\n\nOption: ");
            s1 = br.readLine();
            x = Integer.parseInt(s1);
        }
    }

    public static void setSentenceCase()
    {
        
    }
}
