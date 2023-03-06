package XTest.XPathGeneration;

import XTest.XMLGeneration.ContextNode;

import java.util.Arrays;
import java.util.List;

public class PrefixQualifier {
    static List<String> prefixes = Arrays.asList("/", "//");


    static List<String> axes = Arrays.asList("child", "descendant", "parent", "ancestor", "following-sibling",
        "preceding-sibling", "following", "preceding", "self", "descendant-or-self", "ancestor-or-self");

    static {
        prefixes.add("/");
        prefixes.add("//");
        for(String axis: axes)
            prefixes.add("/" + axis);
    }

    List<String> getPrefixes(List<ContextNode> currentNodes) {
        return null;
    }
}
