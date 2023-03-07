package XTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtils {
    public static String readInputStream(InputStream inputStream) {
        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        return text;
    }

    public static <T> boolean compareList(List<T> listA, List<T> listB) {
        if(listA.size() != listB.size())
            return false;
        for(int i = 0; i < listA.size(); i ++)
            if(!listA.get(i).equals(listB.get(i)))
                return false;
        return true;
    }
}
