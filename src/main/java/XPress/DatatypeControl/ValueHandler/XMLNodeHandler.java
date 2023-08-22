package XPress.DatatypeControl.ValueHandler;

import XPress.DatatypeControl.XMLSimple;

public class XMLNodeHandler extends ValueHandler implements XMLSimple {

    @Override
    public String getValue() {
        return "<A>2</A>";
//        double prob = GlobalRandom.getInstance().nextDouble();
//        if(prob < 0.3)
//            return "<A>2</A>";
//        if(prob < 0.8)
//            return "//[@id=\"" + (GlobalRandom.getInstance().nextInt(50) + 1) + "\"]";
//        return "()";
    }

    @Override
    public String mutateValue(String baseString) {
        return null;
    }
}
