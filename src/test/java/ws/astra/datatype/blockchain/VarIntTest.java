package ws.astra.datatype.blockchain;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.joou.Unsigned.ulong;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.*;

public class VarIntTest {
    @Test(dataProvider = "testData")
    public void testToBinary(long value, byte[] expected) {
        VarInt d = new VarInt(ulong(value));
        assertArrayEquals(expected, d.toBinary(ulong(value)));
    }

    @Test(dataProvider = "testData")
    public void testFromBinary(long expected, byte[] data) throws IOException {
        VarInt d = new VarInt(ulong(expected));
        assertThat(expected, equalTo(d.fromBinary(data).longValue()));
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
}