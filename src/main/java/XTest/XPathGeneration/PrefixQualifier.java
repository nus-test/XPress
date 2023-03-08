package XTest.XPathGeneration;

import XTest.XMLGeneration.ContextNode;

import java.util.*;

public class PrefixQualifier {

    static String getAxis(String base) {
        return "/" + base + "::";
    }

    static List<String> getAxesList(List<String> axes) {
        return axes.stream().map(PrefixQualifier::getAxis).toList();
    }

    List<String> getPrefixes(List<ContextNode> currentNodes) {
        boolean noAxes = false, haveChildNode = false, haveParentNode = false;
        boolean haveFollowingSibling = false, haveFollowing = false;
        boolean havePrecedingSibling = false, havePreceding = false;
        List<String> availablePrefix = new ArrayList<>();

        if(currentNodes == null) {
            noAxes = true;
            haveChildNode = true;
            currentNodes = new ArrayList<>();
        }

        for(ContextNode node : currentNodes) {
            if(!node.childList.isEmpty())
                haveChildNode = true;
            if(node.parentNode != null)
                haveParentNode = true;
            if(node.childId != node.parentNode.childList.size())
                haveFollowingSibling = true;
            if(node.childId != 0)
                havePrecedingSibling = true;
            if(node.haveFollowing)
                haveFollowing = true;
            if(node.havePreceding)
                havePreceding = true;
        }
        if(haveChildNode) {
            availablePrefix.addAll(Arrays.asList("/", "//"));
            if(!noAxes)
                availablePrefix.addAll(getAxesList(Arrays.asList("/child","/descendant")));
        }
        if(!noAxes) {
            if(haveParentNode)
                availablePrefix.addAll(getAxesList(Arrays.asList("/parent", "/ancestor")));
            if(haveFollowingSibling)
                availablePrefix.add(getAxis("/following-sibling"));
            if(havePrecedingSibling)
                availablePrefix.add(getAxis("/preceding-sibling"));
            if(haveFollowing)
                availablePrefix.add(getAxis("/following"));
            if(havePreceding)
                availablePrefix.add(getAxis("/preceding"));
            availablePrefix.addAll(Arrays.asList("/self", "/descendant-or-self", "/ancestor-or-self"));
        }
        return availablePrefix;
    }
}