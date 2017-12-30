package ws.astra.datatype.primitives;

import org.joou.UInteger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.joou.Unsigned.uint;
import static org.junit.Assert.*;

public class UInt32Test {
    @Test(dataProvider = "testData")
    public void testReadFromStream(byte[] data, UInteger expected, long scalarValue) throws Exception {
        InputStream source = new ByteArrayInputStream(data);
        UInt32 d = new UInt32(source);
        assertThat(expected, equalTo(d.getValue()));
        assertThat((long)scalarValue, equalTo(d.getScalarValue()));
    }

    @Test
    public void testReadFromStreamRemaining() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{
                0x23, 0x01, (byte)0xff, 0x55,
                (byte)0xde, 0x54, (byte)0xcc, 0x11,
                0x05, (byte)0xfa, (byte)0x85, 0x65
        });
        UInt32 d = new UInt32(source);
        assertThat(uint(1442775331), equalTo(d.getValue()));
        assertThat(8, equalTo(source.available()));
        d = new UInt32(source);
        assertThat(uint(298603742), equalTo(d.getValue()));
        assertThat(4, equalTo(source.available()));
        d = new UInt32(source);
        assertThat(uint(1703279109), equalTo(d.getValue()));
        assertThat(0, equalTo(source.available()));
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFromEmptyStream() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{});
        new UInt32(source);
    }

    @Test(dataProvider = "testData")
    public void testFromBinary(byte[] data, UInteger expected, long scalarValue) throws Exception {
        UInt32 d = new UInt32(data);
        assertThat(expected, equalTo(d.getValue()));
        assertThat((long)scalarValue, equalTo(d.getScalarValue()));
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfLowLength() throws Exception {
        new UInt32(new byte[]{});
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfLowLength1() throws Exception {
        new UInt32(new byte[]{0x00, 0x01, 0x02});
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfExtraLength() throws Exception {
        new UInt32(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(dataProvider = "testData")
    public void testGetBytes(byte[] expected, UInteger source, long scalarValue) throws Exception {
        UInt32 d = new UInt32(source);
        assertArrayEquals(expected, d.getBytes());
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {new byte[]{0x00, 0x00, 0x00, 0x00}, uint(0x00000000), 0},
                {new byte[]{(byte)0xff, 0x00, 0x01, 0x43}, uint(0x430100ff), 1124139263},
                {new byte[]{0x11, 0x00, 0x34, 0x56}, uint(0x56340011), 1446248465},
                {new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff}, uint(0xffffffff), 4294967295L},
        };
    }
}