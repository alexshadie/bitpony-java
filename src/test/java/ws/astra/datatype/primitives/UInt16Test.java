package ws.astra.datatype.primitives;

import org.joou.UShort;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        assertThat(d.getValue(), equalTo(expected));
        assertThat(d.getScalarValue(), equalTo(scalarValue));
    }

    @Test
    public void testReadOneByOne() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{0x23, 0x01, (byte)0xde, 0x11, 0x05, (byte)0xfa});
        UInt16 d = new UInt16(source);
        assertThat(d.getValue(), equalTo(ushort(291)));
        assertThat(source.available(), equalTo(4));
        d = new UInt16(source);
        assertThat(d.getValue(), equalTo(ushort(4574)));
        assertThat(source.available(), equalTo(2));
        d = new UInt16(source);
        assertThat(d.getValue(), equalTo(ushort(64005)));
        assertThat(source.available(), equalTo(0));
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFromEmptyStream() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{});
        new UInt16(source);
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFromShortStream() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{0x12});
        new UInt16(source);
    }

    @Test(dataProvider = "testData")
    public void testWrite(byte[] expected, UShort source, int scalarValue) throws Exception {
        UInt16 d = new UInt16(source);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        d.write(stream);
        assertThat(stream.toByteArray(), equalTo(expected));
    }

    @Test
    public void testWriteBatch() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        (new UInt16(ushort(12))).write(stream);
        (new UInt16(ushort(127))).write(stream);
        (new UInt16(ushort(255))).write(stream);
        (new UInt16(ushort(32767))).write(stream);
        (new UInt16(ushort(65535))).write(stream);
        assertThat(stream.size(), equalTo(10));
        assertThat(
            stream.toByteArray(),
            equalTo(new byte[]{
                0x0c, 0x00,
                0x7f, 0x00,
                (byte)0xff, 0x00,
                (byte)0xff, 0x7f,
                (byte)0xff, (byte)0xff
            })
        );
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