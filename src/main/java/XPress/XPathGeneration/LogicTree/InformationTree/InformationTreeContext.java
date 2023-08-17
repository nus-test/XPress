package XPress.XPathGeneration.LogicTree.InformationTree;

import XPress.XPathGeneration.LogicTree.LogicTreeContext;

public class InformationTreeContext extends LogicTreeContext {
    /**
     * Supplementary context only works for sequence of nodes, includes id of one random node in sequence
     */
    public String supplementaryContext = null;

    public InformationTreeContext() {}

    public InformationTreeContext(LogicTreeContext context) {
        setContext(context);
    }

    @Override
    public void setContext(LogicTreeContext context) {
        super.setContext(context);
        if(context instanceof InformationTreeContext) {
            supplementaryContext = ((InformationTreeContext) context).supplementaryContext;
        }
    }

    public void setContext(String context) {
        super.setContext(context);
    }
}
