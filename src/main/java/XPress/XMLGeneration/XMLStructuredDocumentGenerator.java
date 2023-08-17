package XPress.XMLGeneration;

import XPress.GlobalRandom;

import java.util.List;

import static XPress.XMLGeneration.ContextTreeGenerator.markPrecedingFollowing;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class XMLStructuredDocumentGenerator extends XMLDocumentGenerator {
    int tot = 1;

    @Override
    public ContextNode generateXMLDocument(int contextNodeSize) {
        ContextNode root = generateStructuredXMLDocument(contextNodeSize);
        markPrecedingFollowing(root, ContextTreeGeneratorImpl.MarkChoice.PRECEDING);
        markPrecedingFollowing(root, ContextTreeGeneratorImpl.MarkChoice.FOLLOWING);
        traverseToAssign(root);
        return root;
    }

    void traverseToAssign(ContextNode currentNode) {
        currentNode.id = tot;
        super.assignValueToNode(currentNode);
        tot += 1;
        for(ContextNode child: currentNode.childList) {
            child.depth = currentNode.depth + 1;
            traverseToAssign(child);
        }
    }

    ContextNode generateStructuredXMLDocument(int documentSize) {
        int templateSize = documentSize / 2;
        List<ContextNode> contextNodeList = contextTemplateGenerator.generateContextTemplate(templateSize);
        contextNodeList = GlobalRandom.getInstance().shuffleList(contextNodeList);
        int bound = 0;
        while(templateSize > 0) {
            int currentSize = GlobalRandom.getInstance().nextInt(templateSize) + 1;
            if(templateSize == currentSize && currentSize != 1)
                currentSize -= 1;
            if(bound != 0) {
                for(int i = bound; i < bound + currentSize; i ++) {
                    ContextNode contextNode = contextNodeList.get(i);
                    contextNodeList.get(i).size = 1;
                    int childSize = GlobalRandom.getInstance().nextInt(min(10, bound)) + 1;
                    List<Integer> childIdList = GlobalRandom.getInstance().nextIntListWithRep(childSize, bound);
                    for(int id : childIdList) {
                        contextNode.assignChildIterative(contextNodeList.get(id));
                        contextNode.size += contextNodeList.get(id).size;
                        if(contextNode.size > documentSize)
                            return contextNode;
                    }
                }
            }
            else {
                for(int i = 0; i < currentSize; i ++) {
                    contextNodeList.get(i).hasLeaf = true;
                    contextNodeList.get(i).size = 1;
                }
            }
            bound += currentSize;
            templateSize -= currentSize;
        }
        int maxSize = 0, maxId = 0;
        for(int i = 0; i < bound; i ++) {
            if(contextNodeList.get(i).size > maxSize) {
                maxSize = contextNodeList.get(i).size;
                maxId = i;
            }
        }
        return contextNodeList.get(maxId);
    }

    @Override
    public void clearContext() {
        super.clearContext();
        tot = 1;
    }
}
