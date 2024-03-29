package XPress.DatatypeControl.ValueHandler;


import XPress.DatatypeControl.PrimitiveDatatype.XMLInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XMLIntegerHandlerTest {

    @Test
    public void parseIntTest() {
      Integer num1 = XMLInteger.parseInt("-7.9091506E7");
      assertEquals(-79091506, num1);
      Integer num2 = XMLInteger.parseInt("89.991E5");
      assertEquals(8999100, num2);
    }
}
