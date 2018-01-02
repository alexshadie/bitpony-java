package ws.astra.datatype.primitives;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

// @todo: Moar tests
public class CharTest {
    @Test(dataProvider = "testData")
    public void testRead(byte[] data, String expected) throws IOException {
        InputStream stream = new ByteArrayInputStream(data);
        Char d = new Char(stream, data.length);
        assertThat(d.getValue(), equalTo(expected));
    }

    @Test(dataProvider = "testData")
    public void testWrite(byte[] expected, String source) throws IOException {
        Char d = new Char(source);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        d.write(stream);
        assertThat(stream.toByteArray(), equalTo(expected));
    }

    @Test(expectedExceptions = IOException.class)
    public void testShortStream() throws IOException {
        InputStream stream = new ByteArrayInputStream("Test".getBytes());
        new Char(stream, 5);
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {new byte[]{0x21, 0x6f, 0x6c, 0x6c, 0x65, 0x48}, "Hello!"},
        };
    }
}