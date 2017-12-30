package ws.astra.datatype.primitives;

import org.joou.UShort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.joou.Unsigned.ushort;
import static org.junit.Assert.*;

public class UInt16Test {
    @Test(dataProvider = "testData")
    public void testReadFromStream(byte[] data, UShort expected, int scalarValue) throws Exception {
        InputStream source = new ByteArrayInputStream(data);
        UInt16 d = new UInt16(source);
        assertThat(expected, equalTo(d.getValue()));
        assertThat((int)scalarValue, equalTo(d.getScalarValue()));
    }

    @Test
    public void testReadFromStreamRemaining() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{0x23, 0x01, (byte)0xde, 0x11, 0x05, (byte)0xfa});
        UInt16 d = new UInt16(source);
        assertThat(ushort(291), equalTo(d.getValue()));
        assertThat(4, equalTo(source.available()));
        d = new UInt16(source);
        assertThat(ushort(4574), equalTo(d.getValue()));
        assertThat(2, equalTo(source.available()));
        d = new UInt16(source);
        assertThat(ushort(64005), equalTo(d.getValue()));
        assertThat(0, equalTo(source.available()));
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFromEmptyStream() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{});
        new UInt16(source);
    }

    @Test(dataProvider = "testData")
    public void testFromBinary(byte[] data, UShort expected, int scalarValue) throws Exception {
        UInt16 d = new UInt16(data);
        assertThat(expected, equalTo(d.getValue()));
        assertThat((int)scalarValue, equalTo(d.getScalarValue()));
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfLowLength() throws Exception {
        new UInt16(new byte[]{});
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfLowLength1() throws Exception {
        new UInt16(new byte[]{0x00});
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfExtraLength() throws Exception {
        new UInt16(new byte[]{0x01, 0x02, 0x03});
    }

    @Test(dataProvider = "testData")
    public void testGetBytes(byte[] expected, UShort source, int scalarValue) throws Exception {
        UInt16 d = new UInt16(source);
        assertArrayEquals(expected, d.getBytes());
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {new byte[]{0x00, 0x00}, ushort(0x0000), 0},
                {new byte[]{(byte)0xff, 0x00}, ushort(0x00ff), 255},
                {new byte[]{0x11, 0x00}, ushort(0x0011), 17},
                {new byte[]{(byte)0xff, (byte)0xff}, ushort(0xffff), 65535},
        };
    }
}