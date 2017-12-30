package ws.astra.datatype.primitives;

import org.joou.UByte;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
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
        assertThat(expected, equalTo(d.getValue()));
        assertThat((short)scalarValue, equalTo(d.getScalarValue()));
    }

    @Test
    public void testReadFromStreamRemaining() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{0x01, 0x11, (byte)0xfa});
        UInt8 d = new UInt8(source);
        assertThat(ubyte(1), equalTo(d.getValue()));
        assertThat(2, equalTo(source.available()));
        d = new UInt8(source);
        assertThat(ubyte(17), equalTo(d.getValue()));
        assertThat(1, equalTo(source.available()));
        d = new UInt8(source);
        assertThat(ubyte(250), equalTo(d.getValue()));
        assertThat(0, equalTo(source.available()));
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFromEmptyStream() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{});
        new UInt8(source);
    }

    @Test(dataProvider = "testData")
    public void testFromBinary(byte[] data, UByte expected, int scalarValue) throws Exception {
        UInt8 d = new UInt8(data);
        assertThat(expected, equalTo(d.getValue()));
        assertThat((short)scalarValue, equalTo(d.getScalarValue()));
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfLowLength() throws Exception {
        new UInt8(new byte[]{});
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfExtraLength() throws Exception {
        new UInt8(new byte[]{0x01, 0x02});
    }

    @Test(dataProvider = "testData")
    public void testGetBytes(byte[] expected, UByte source, int scalarValue) throws Exception {
        UInt8 d = new UInt8(source);
        assertArrayEquals(expected, d.getBytes());
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