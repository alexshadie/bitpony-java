package ws.astra.datatype.primitives;

import org.joou.UByte;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.joou.Unsigned.ubyte;
import static org.junit.Assert.*;

public class UInt8Test {
    @Test(dataProvider = "testData")
    public void testReadFromStream(byte[] data, UByte expected, int scalarValue) throws Exception {
        InputStream source = new ByteArrayInputStream(data);
        UInt8 d = new UInt8(source);
        assertThat(d.getValue(), equalTo(expected));
        assertThat(d.getScalarValue(), equalTo((short)scalarValue));
    }

    @Test
    public void testReadOneByOne() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{0x01, 0x11, (byte)0xfa});
        UInt8 d = new UInt8(source);
        assertThat(d.getValue(), equalTo(ubyte(1)));
        assertThat(source.available(), equalTo(2));
        d = new UInt8(source);
        assertThat(ubyte(17), equalTo(d.getValue()));
        assertThat(source.available(), equalTo(1));
        d = new UInt8(source);
        assertThat(ubyte(250), equalTo(d.getValue()));
        assertThat(source.available(), equalTo(0));
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFromEmptyStream() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{});
        new UInt8(source);
    }

    @Test(dataProvider = "testData")
    public void testWrite(byte[] expected, UByte source, int scalarValue) throws Exception {
        UInt8 d = new UInt8(source);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        d.write(stream);
        assertThat(stream.toByteArray(), equalTo(expected));
    }

    @Test
    public void testWriteBatch() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        (new UInt8(ubyte(12))).write(stream);
        (new UInt8(ubyte(127))).write(stream);
        (new UInt8(ubyte(255))).write(stream);
        assertThat(3, equalTo(stream.size()));
        assertThat(stream.toByteArray(), equalTo(new byte[]{0x0c, 0x7f, (byte)0xff}));
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {new byte[]{0x00}, ubyte(0x00), 0},
                {new byte[]{(byte)0xff}, ubyte(0xff), 255},
                {new byte[]{0x11}, ubyte(0x11), 17},
        };
    }
}