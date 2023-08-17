package XPress.XPathGeneration;

import XPress.GlobalSettings;
import XPress.XMLGeneration.ContextNode;

import java.util.*;

public class PrefixQualifier {

    static String getAxis(String base) {
        return "/" + base + "::";
    }

    static List<String> getAxesList(List<String> axes) {
        return axes.stream().map(PrefixQualifier::getAxis).toList();
    }

    List<String> getPrefixes(List<ContextNode> currentNodes) {
        boolean haveChildNode = false, haveParentNode = false;
        boolean haveFollowingSibling = false, haveFollowing = false;
        boolean havePrecedingSibling = false, havePreceding = false;
        List<String> availablePrefix = new ArrayList<>();

        if((!GlobalSettings.starNodeSelection && !GlobalSettings.targetedParameter) ||
                (currentNodes != null && currentNodes.size() == 0)) {
            availablePrefix.addAll(Arrays.asList("/", "//"));
            availablePrefix.addAll(getAxesList(Arrays.asList("child", "descendant", "parent", "ancestor",
                    "following-sibling", "preceding-sibling", "following", "preceding",
                    "self", "descendant-or-self", "ancestor-or-self")));
            return availablePrefix;
        }

        if(currentNodes == null) {
            haveChildNode = true;
            currentNodes = new ArrayList<>();
        }

        for(ContextNode node : currentNodes) {
            if(!node.childList.isEmpty())
                haveChildNode = true;
            if(node.parentNode != null)
                haveParentNode = true;
            if(node.parentNode != null) {
                if (node.childId != node.parentNode.childList.size())
                    haveFollowingSibling = true;
                if (node.childId != 1)
                    havePrecedingSibling = true;
            }
            if(node.hasFollowing)
                haveFollowing = true;
            if(node.hasPreceding)
                havePreceding = true;
        }
        if(haveChildNode) {
            availablePrefix.addAll(Arrays.asList("/", "//"));
            availablePrefix.addAll(getAxesList(Arrays.asList("child","descendant")));
        }
        if(haveParentNode)
            availablePrefix.addAll(getAxesList(Arrays.asList("parent", "ancestor")));
        if(haveFollowingSibling)
            availablePrefix.add(getAxis("following-sibling"));
        if(havePrecedingSibling)
            availablePrefix.add(getAxis("preceding-sibling"));
        if(haveFollowing)
            availablePrefix.add(getAxis("following"));
        if(havePreceding)
            availablePrefix.add(getAxis("preceding"));
        availablePrefix.addAll(getAxesList(Arrays.asList("self", "descendant-or-self", "ancestor-or-self")));
        return availablePrefix;
    }
}
