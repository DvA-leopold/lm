package converters;

import org.junit.Test;

import static org.junit.Assert.*;

public class MultipleFilesConverterTest {

    @Test
    public void testConvert() throws Exception {
        MultipleFilesConverter converter = new MultipleFilesConverter();
        boolean isOk = converter.convert("E:\\StarkDocs\\testconverter", "GEN");
        assertFalse("covert test fail", !isOk);
    }
}