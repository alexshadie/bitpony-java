package ws.astra.datatype.primitives;

import org.joou.ULong;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.joou.Unsigned.ulong;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;

public class UInt64Test {
    @Test(dataProvider = "testData")
    public void testReadFromStream(byte[] data, ULong expected, long scalarValue) throws Exception {
        InputStream source = new ByteArrayInputStream(data);
        UInt64 d = new UInt64(source);
        ULong v = d.getValue();
        assertThat(expected, equalTo(d.getValue()));
        if (scalarValue >= 0) {
            // Skip for very big long, that are treated as negative
            assertThat((long) scalarValue, equalTo(d.getScalarValue()));
        }
    }

    @Test
    public void testReadFromStreamRemaining() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{
                0x23, 0x01, (byte)0xff, 0x55, 0x00, 0x00, 0x00, 0x00,
                (byte)0xde, 0x54, (byte)0xcc, 0x11, 0x00, 0x00, 0x00, 0x00,
                0x05, (byte)0xfa, (byte)0x85, 0x65, 0x00, 0x00, 0x00, 0x00,
        });
        UInt64 d = new UInt64(source);
        assertThat(ulong(1442775331), equalTo(d.getValue()));
        assertThat(16, equalTo(source.available()));
        d = new UInt64(source);
        assertThat(ulong(298603742), equalTo(d.getValue()));
        assertThat(8, equalTo(source.available()));
        d = new UInt64(source);
        assertThat(ulong(1703279109), equalTo(d.getValue()));
        assertThat(0, equalTo(source.available()));
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFromEmptyStream() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{});
        new UInt64(source);
    }

    @Test(dataProvider = "testData")
    public void testFromBinary(byte[] data, ULong expected, long scalarValue) throws Exception {
        UInt64 d = new UInt64(data);
        assertThat(expected, equalTo(d.getValue()));
        if (scalarValue >= 0) {
            // Skip for very big long, that are treated as negative
            assertThat((long) scalarValue, equalTo(d.getScalarValue()));
        }
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfLowLength() throws Exception {
        new UInt64(new byte[]{});
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfLowLength1() throws Exception {
        new UInt64(new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06});
    }

    @Test(expectedExceptions = IOException.class)
    public void testFromBinaryFailsIfExtraLength() throws Exception {
        new UInt64(new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09});
    }

    @Test(dataProvider = "testData")
    public void testGetBytes(byte[] expected, ULong source, long scalarValue) throws Exception {
        UInt64 d = new UInt64(source);
        assertArrayEquals(expected, d.getBytes());
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00}, ulong(0x0000000000000000), 0},
                {new byte[]{(byte)0xff, 0x00, 0x01, 0x43, 0x00, 0x00, 0x00, 0x00}, ulong(0x00000000430100ff), 1124139263},
                {new byte[]{0x11, 0x00, 0x34, 0x56, 0x00, 0x00, 0x00, 0x00}, ulong(0x0000000056340011), 1446248465},
                {new byte[]{(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, 0x00, 0x00, 0x00, 0x00}, ulong(0x00000000ffffffffL), 4294967295L},
                {new byte[]{0x23, 0x01, (byte)0xff, 0x55, 0x54, 0x43, 0x32, 0x21} , ulong(0x2132435455ff0123L), 2392048381586243875L},
                {new byte[]{(byte)0xde, 0x54, (byte)0xcc, 0x11, 0x54, 0x43, 0x32, 0x21}, ulong(0x2132435411cc54deL), 2392048380442072286L},
                {new byte[]{0x05, (byte)0xfa, (byte)0x85, 0x65, 0x54, 0x43, 0x32, 0x21}, ulong(0x213243546585fa05L), 2392048381846747653L},
                {new byte[]{0x05, (byte)0xfa, (byte)0x85, 0x65, 0x54, 0x43, 0x32, (byte)0xff}, ulong(0xff3243546585fa05L), -1L},
        };
    }
}