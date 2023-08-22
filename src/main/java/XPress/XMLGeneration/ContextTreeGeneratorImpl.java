package XPress.XMLGeneration;

import XPress.DatatypeControl.PrimitiveDatatype.XMLString;
import XPress.DatatypeControl.ValueHandler.XMLStringHandler;
import XPress.GlobalRandom;
import XPress.GlobalSettings;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class ContextTreeGeneratorImpl implements ContextTreeGenerator {

    @Override
    public ContextNode generateRandomTree(int contextTreeSize, List<String> namespaceList) {
        List<ContextNode> nodeList = new ArrayList<>();
        for(int i = 0; i < contextTreeSize; i ++) {
            ContextNode currentNode = new ContextNode();
            nodeList.add(currentNode);
            currentNode.id = i + 1;
            Integer parent = null;
            if(i != 0) {
                parent = GlobalRandom.getInstance().nextInt(i);
                nodeList.get(parent).addChild(currentNode);
            }
            if(GlobalSettings.useNamespace) {
                double prob = GlobalRandom.getInstance().nextDouble();
                if(parent != null)
                    currentNode.prefixList = new ArrayList<>(nodeList.get(parent).prefixList);
                if(prob < 0.4) {
                    addPrefix(currentNode, namespaceList);
                }
                if(prob < 0.1) {
                    addPrefix(currentNode, namespaceList);
                }
                if(prob < 0.2 && !currentNode.prefixList.isEmpty()) {
                    currentNode.prefix = GlobalRandom.getInstance().getRandomFromList(currentNode.prefixList);
                }
                prob = GlobalRandom.getInstance().nextDouble();
                if(prob < 0.25 && !namespaceList.isEmpty()) {
                    currentNode.declareNamespace = GlobalRandom.getInstance().getRandomFromList(namespaceList);
                }
            }
        }
        ContextNode root = nodeList.get(0);
        ContextTreeGenerator.markPrecedingFollowing(root, MarkChoice.PRECEDING);
        ContextTreeGenerator.markPrecedingFollowing(root, MarkChoice.FOLLOWING);
        return root;
    }

    public void addPrefix(ContextNode currentNode, List<String> namespaceList) {
        String prefix = "", namespace;
        boolean flag = false;
        while(!flag) {
            double prob = GlobalRandom.getInstance().nextDouble();
            if (prob < 0.1 && !currentNode.prefixList.isEmpty()) {
                prefix = GlobalRandom.getInstance().getRandomFromList(currentNode.prefixList);
            } else {
                prefix = ((XMLStringHandler) XMLString.getInstance().getValueHandler()).getRandomValueEng(GlobalRandom.getInstance().nextInt(5) + 1);
            }
            if(!currentNode.declarePrefixList.contains(prefix))
                flag = true;
        }
        namespace = GlobalRandom.getInstance().getRandomFromList(namespaceList);
        currentNode.declarePrefixNamespacePair.add(Pair.of(prefix, namespace));
        currentNode.prefixList.add(prefix);
        currentNode.declarePrefixList.add(prefix);
    }
}
