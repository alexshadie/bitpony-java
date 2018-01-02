package ws.astra.datatype.blockchain;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.joou.Unsigned.ulong;
import static org.junit.Assert.assertThat;

public class VarIntTest {
    @Test(dataProvider = "testData")
    public void testWrite(long value, byte[] expected) throws IOException {
        VarInt d = new VarInt(ulong(value));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        d.write(stream);
        assertThat(stream.toByteArray(), equalTo(expected));
    }

    @Test(dataProvider = "testData")
    public void testRead(long expected, byte[] data) throws IOException {
        VarInt d = new VarInt(new ByteArrayInputStream(data));
        assertThat(d.getValue(), equalTo(ulong(expected)));
    }

    @Test(dataProvider = "testInvalidData", expectedExceptions = IOException.class)
    public void testFailing(long expected, byte[] data) throws IOException {
        new VarInt(new ByteArrayInputStream(data));
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
                {0, new byte[]{(byte)0x00}},
                {1, new byte[]{(byte)0x01}},
                {127, new byte[]{(byte)0x7f}},
                {252, new byte[]{(byte)0xfc}},
                {253, new byte[]{(byte)0xfd, (byte)0xfd, 0x00}},
                {254, new byte[]{(byte)0xfd, (byte)0xfe, 0x00}},
                {255, new byte[]{(byte)0xfd, (byte)0xff, 0x00}},
                {256, new byte[]{(byte)0xfd, 0x00, 0x01}},
                {65534, new byte[]{(byte)0xfd, (byte)0xfe, (byte)0xff}},
                {65535, new byte[]{(byte)0xfd, (byte)0xff, (byte)0xff}},
                {65536, new byte[]{(byte)0xfe, 0x00, 0x00, 0x01, 0x00}},
                {4294967294L, new byte[]{(byte)0xfe, (byte)0xfe, (byte)0xff, (byte)0xff, (byte)0xff}},
                {4294967295L, new byte[]{(byte)0xfe, (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff}},
                {4294967296L, new byte[]{(byte)0xff, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00}},
        };
    }

    @DataProvider
    public Object[][] testInvalidData() {
        return new Object[][]{
                {0, new byte[]{(byte)0xfd, (byte)0x00, (byte)0x00}},
                {1, new byte[]{(byte)0xfe, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x00}},
                {127, new byte[]{(byte)0xff, (byte)0x7f, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00}},
                {252, new byte[]{(byte)0xfd}},
                {253, new byte[]{(byte)0xfe, (byte)0xfd, 0x00, 0x00, 0x00}},
                {254, new byte[]{(byte)0xfe}},
                {255, new byte[]{(byte)0xff}},
                {256, new byte[]{(byte)0xff, 0x01}},
        };
    }
}