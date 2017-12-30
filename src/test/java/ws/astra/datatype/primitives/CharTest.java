package ws.astra.datatype.primitives;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.joou.Unsigned.ubyte;
import static org.testng.Assert.*;

public class CharTest {
    @Test(dataProvider = "testData")
    public void testReadFromStream(byte[] data, String expected) throws IOException {
        InputStream stream = new ByteArrayInputStream(data);
        Char d = new Char(stream, data.length);
        assertThat(expected, equalTo(d.getValue()));
    }

    @Test(dataProvider = "testData")
    public void testFromBinary(byte[] data, String expected) throws IOException {
        Char d = new Char(data);
        assertThat(expected, equalTo(d.getValue()));
    }

    @Test(dataProvider = "testData")
    public void testToBinary(byte[] expected, String source) throws IOException {
        Char d = new Char(source);
        assertThat(expected, equalTo(d.getBytes()));
    }



    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {new byte[]{0x21, 0x6f, 0x6c, 0x6c, 0x65, 0x48}, "Hello!"},
        };
    }
}