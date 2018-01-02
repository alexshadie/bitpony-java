package ws.astra.datatype.primitives;

import org.joou.UInteger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        assertThat(d.getScalarValue(), equalTo(scalarValue));
    }

    @Test
    public void testReadOneByOne() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{
                0x23, 0x01, (byte)0xff, 0x55,
                (byte)0xde, 0x54, (byte)0xcc, 0x11,
                0x05, (byte)0xfa, (byte)0x85, 0x65,
                0x05, (byte)0xfa, (byte)0x85, (byte)0xff,
        });
        UInt32 d = new UInt32(source);
        assertThat(d.getValue(), equalTo(uint(1442775331)));
        assertThat(source.available(), equalTo(12));
        d = new UInt32(source);
        assertThat(d.getValue(), equalTo(uint(298603742)));
        assertThat(source.available(), equalTo(8));
        d = new UInt32(source);
        assertThat(d.getValue(), equalTo(uint(1703279109)));
        assertThat(source.available(), equalTo(4));
        d = new UInt32(source);
        assertThat(d.getValue(), equalTo(uint(4286970373L)));
        assertThat(source.available(), equalTo(0));
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFromEmptyStream() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{});
        new UInt32(source);
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFromShortStream() throws IOException {
        InputStream source = new ByteArrayInputStream(new byte[]{0x00, 0x00, 0x00});
        new UInt32(source);
    }

    @Test(dataProvider = "testData")
    public void testWrite(byte[] expected, UInteger source, long scalarValue) throws Exception {
        UInt32 d = new UInt32(source);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        d.write(stream);
        assertThat(stream.toByteArray(), equalTo(expected));
    }

    @Test
    public void testWriteBatch() throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        (new UInt32(uint(12))).write(stream);
        (new UInt32(uint(127))).write(stream);
        (new UInt32(uint(255))).write(stream);
        (new UInt32(uint(32767))).write(stream);
        (new UInt32(uint(65535))).write(stream);
        (new UInt32(uint(2147483647))).write(stream);
        (new UInt32(uint(4294967295L))).write(stream);
        assertThat(stream.size(), equalTo(28));
        assertThat(
            stream.toByteArray(),
            equalTo(
                new byte[]{
                        0x0c, 0x00, 0x00, 0x00,
                        0x7f, 0x00, 0x00, 0x00,
                        (byte)0xff, 0x00, 0x00, 0x00,
                        (byte)0xff, 0x7f, 0x00, 0x00,
                        (byte)0xff, (byte)0xff, 0x00, 0x00,
                        (byte)0xff, (byte)0xff, (byte)0xff, 0x7f,
                        (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff,
                }
            )
        );
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