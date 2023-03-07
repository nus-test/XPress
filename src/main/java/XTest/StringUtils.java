package XTest;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

//    List<Integer> getOccurrenceInString(String baseString, String searchString) {
//        List<Integer> resultList = new ArrayList<>();
//        // TODO
//        return null;
//    }

    public static List<Integer> getOccurrenceInString(String baseString, String searchString) {
        List<Integer> resultList = new ArrayList<>();
        int lastIndex = 0;
        while(lastIndex != -1) {
            lastIndex = baseString.indexOf(searchString,lastIndex);
            if(lastIndex != -1){
                resultList.add(lastIndex);
                lastIndex += 1;
            }
        }
        return resultList;
    }
}
