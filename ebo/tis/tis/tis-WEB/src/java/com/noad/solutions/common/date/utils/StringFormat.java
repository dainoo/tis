
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author edwin
 */
public class StringFormat
{

    public static String formatListToString(List<String> stringList, String seperatior)
    {
        String formatte = "";

        if (stringList == null)
        {
            return formatte;
        }

        if (stringList.isEmpty())
        {
            return formatte;
        }



//        int numberOfItems = stringList.size();

        int counter = 0;
        for (String data : stringList)
        {

            if (counter == 0)
            {
                formatte = data;
                counter++;
            } else
            {
                formatte = formatte + seperatior + data;
            }
        }


        return formatte;
    }

    public static List<String> parseStringToList(String data, String seperator)
    {
        List<String> stringList = new LinkedList<String>();

        if (data == null)
        {
            return stringList;
        }

        String dataParts[] = data.split(seperator);

        for (int i = 0; i < dataParts.length; i++)
        {
            String string = dataParts[i];
            stringList.add(string);

        }

        return stringList;

    }

    public static void main(String[] args)
    {
//
//        List<String> dd = new LinkedList<String>();
//        dd.add("asdfasfasdf");
//        dd.add("fasfa f");
//        System.out.println(format(dd, ","));

        System.out.println(parseStringToList("0f8ca9b2-06e4-4f60-9b17-f404af46476e#d89b8f6e-c8bc-4e58-8252-4666d2ba65fb", "#"));
    }
}
