package XPress;

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

    public static <T> String getListString(List<T> list) {
        return getListString(list, ",");
    }

    public static <T> String getListString(List<T> list, String delimiter) {
        String resultStr = "";
        boolean needDelim = false;
        for(T child: list) {
            if(needDelim) resultStr += delimiter;
            resultStr += child.toString();
            if(!needDelim) needDelim = true;
        }
        return resultStr;
    }
}
