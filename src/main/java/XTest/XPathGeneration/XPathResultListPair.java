package XTest.XPathGeneration;

import XTest.XMLGeneration.ContextNode;

import java.util.ArrayList;
import java.util.List;

public class XPathResultListPair {
    String XPath;
    List<ContextNode> contextNodeList;

    public XPathResultListPair(String XPath, List<ContextNode> contextNodeList) {
        this.XPath = XPath;
        this.contextNodeList = contextNodeList;
    }

    XPathResultListPair(XPathResultListPair xPathResultListPair) {
        XPath = xPathResultListPair.XPath;
        if(xPathResultListPair.contextNodeList == null)
            contextNodeList = null;
        else
            contextNodeList = new ArrayList<>(xPathResultListPair.contextNodeList);
    }
}