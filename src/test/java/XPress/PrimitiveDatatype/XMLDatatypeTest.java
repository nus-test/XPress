package XPress.PrimitiveDatatype;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XMLDatatypeTest {
    @Test
    void getRandomDataTypeTest() {
        for(int i = 0; i < 5; i ++) {
            XMLDatatype xmlDatatype = XMLDatatype.getRandomDataType();
            assertNotNull(xmlDatatype);
        }
    }
}
