package XTest.XPathGeneration.LogicTree;

import XTest.DatabaseExecutor.MainExecutor;

public class LogicTreeContextInfo {
    public MainExecutor mainExecutor;
    public String XPathExpr;

    public LogicTreeContextInfo() {}

    public LogicTreeContextInfo(MainExecutor mainExecutor, String XPathExpr) {
        this.mainExecutor = mainExecutor;
        this.XPathExpr = XPathExpr;
    }

    public LogicTreeContextInfo(LogicTreeContextInfo infoNode) {
        inheritInfo(infoNode);
    }

    public LogicTreeContextInfo(LogicTreeNode logicTreeNode) {
        inheritInfo(logicTreeNode.contextInfo);
    }

    public void inheritInfo(LogicTreeContextInfo infoNode) {
        mainExecutor = infoNode.mainExecutor;
        XPathExpr = infoNode.XPathExpr;
    }
}
