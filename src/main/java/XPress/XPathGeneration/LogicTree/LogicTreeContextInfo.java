package XPress.XPathGeneration.LogicTree;

import XPress.DatabaseExecutor.MainExecutor;

public class LogicTreeContextInfo {

    public MainExecutor mainExecutor;

    /**
     * Record down the XPathPrefix to produce node candidates before applying current information tree selection.
     * e.g. /A1/B1, /A1/(B1, C1)
     */
    public String XPathPrefix;

    public LogicTreeContextInfo() {}

    public LogicTreeContextInfo(MainExecutor mainExecutor, String XPathExpr) {
        this.mainExecutor = mainExecutor;
        this.XPathPrefix = XPathExpr;
    }

    public LogicTreeContextInfo(LogicTreeContextInfo infoNode) {
        inheritInfo(infoNode);
    }

    public LogicTreeContextInfo(LogicTreeNode logicTreeNode) {
        inheritInfo(logicTreeNode.contextInfo);
    }

    public void inheritInfo(LogicTreeContextInfo infoNode) {
        mainExecutor = infoNode.mainExecutor;
        XPathPrefix = infoNode.XPathPrefix;
    }

    public void setInfo(MainExecutor mainExecutor, String XPathExpr) {
        this.mainExecutor = mainExecutor;
        this.XPathPrefix = XPathExpr;
    }
}
