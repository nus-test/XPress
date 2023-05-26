package XTest.XPathGeneration.LogicTree.InformationTree;

import XTest.DatabaseExecutor.MainExecutor;
import XTest.XPathGeneration.LogicTree.LogicTreeContextInfo;

public class InformationTreeContextInfo extends LogicTreeContextInfo {
    /**
     * Starred node id which is recorded in the unique context node.
     */
    public int starredNodeId;

    /**
     * Only set to true when there is a context node in represented subtree.
     */
    public boolean containsContext = false;

    /**
     * If within subtree there is no context node or an ancestor node of the context node is
     * with calculated context value is set to true.
     */
    public boolean constantExpr = false;

    /**
     * If set to true unique context node refers to the starred node in context of XPath prefix.
     * Else refers to a derived sequence from the starred node with itself as the context or does not contain
     * any context node.
     */
    public boolean selfContext = true;

    public InformationTreeContextInfo() {}

    public InformationTreeContextInfo(MainExecutor mainExecutor, String XPathPrefix, int starredNodeId,
                                      boolean containsContext, boolean constantExpr, boolean selfContext) {
        super(mainExecutor, XPathPrefix);
        this.starredNodeId = starredNodeId;
        this.containsContext = containsContext;
        this.constantExpr = constantExpr;
        this.selfContext = selfContext;
    }

    public InformationTreeContextInfo(InformationTreeContextInfo infoNode) {
        inheritInfo(infoNode);
    }

    public InformationTreeContextInfo(InformationTreeNode informationTreeNode) {
        inheritInfo(informationTreeNode.getContextInfo());
    }

    public void inheritInfo(InformationTreeContextInfo infoNode) {
        starredNodeId = infoNode.starredNodeId;
        containsContext = infoNode.containsContext;
        constantExpr = infoNode.constantExpr;
        selfContext = infoNode.selfContext;
        super.inheritInfo(infoNode);
    }
}
