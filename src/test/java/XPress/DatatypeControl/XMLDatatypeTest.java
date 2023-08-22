package XPress.DatatypeControl;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class XMLDatatypeTest {
    @Test
    void getRandomDataTypeTest() {
        for(int i = 0; i < 5; i ++) {
            XMLDatatype_t xmlDatatype = XMLDatatype_t.getRandomDataType();
            assertNotNull(xmlDatatype);
        }
    }
}
