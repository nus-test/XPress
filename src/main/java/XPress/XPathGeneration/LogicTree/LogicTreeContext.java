package XPress.XPathGeneration.LogicTree;

public class LogicTreeContext {
    /**
     * If is calculable, contains the real value of evaluated context for the starred node
     * For a sequence, context contains the length of the sequence
     * For a node, context refers to the id number of the node
     */
    public String context = null;


    public void setContext(LogicTreeContext context) {
        this.context = context.context;
    }

    public void setContext(String context) {
        this.context = context;
    }

}
