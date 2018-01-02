package ws.astra.datatype.blockchain;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

// @todo: Moar tests!
public class VarStrTest {
    @Test(dataProvider = "testData")
    public void testRead(byte[] source, String expected) throws IOException {
        VarStr d = new VarStr(new ByteArrayInputStream(source));
        assertThat(d.getValue(), equalTo(expected));
    }

    @Test(dataProvider = "testData")
    public void testWrite(byte[] expected, String source) throws IOException {
        VarStr d = new VarStr(source);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        d.write(stream);
        assertThat(stream.toByteArray(), equalTo(expected));
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][] {
                {new byte[]{0x06, 0x21, 0x6f, 0x6c, 0x6c, 0x65, 0x48}, "Hello!"}
        };
    }
}