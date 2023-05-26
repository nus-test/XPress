package XTest.XPathGeneration.LogicTree.InformationTree;

import XTest.XPathGeneration.LogicTree.LogicTreeContext;

public class InformationTreeContext extends LogicTreeContext {
    /**
     * Supplementary context only works for sequence of nodes, includes id of one random node in sequence
     */
    public String supplementaryContext = null;

    /**
     * If is sequence type, contains the length of sequence for the starred node
     */
    int length;
}
