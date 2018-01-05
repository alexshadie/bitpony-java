package ws.astra.datatype.blockchain;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ws.astra.TestHelper;
import ws.astra.blockchain.Hash;

import java.io.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class THashTest {
    @Test
    public void testConstruct() throws Exception {
        byte[] src = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
                0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f
        };
        THash h = new THash(new Hash(src));
        assertThat(h.getValue(), equalTo(new Hash(src)));
    }

    @Test(dataProvider = "testData")
    public void testReadFromStream(byte[] data, String expectedHex) throws Exception {
        InputStream stream = new ByteArrayInputStream(data);
        THash h = new THash(stream);
        assertThat(h.getValue(), equalTo(new Hash(TestHelper.HexStringToBytes(expectedHex))));
    }

    @Test(dataProvider = "testData")
    public void testWriteToStream(byte[] expected, String sourceHex) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        THash h = new THash(new Hash(TestHelper.HexStringToBytes(sourceHex)));
        h.write(stream);
        assertThat(stream.toByteArray(), equalTo(expected));
    }

    @Test(expectedExceptions = IOException.class)
    public void testReadFailsOnShortStream() throws Exception{
        byte[] source = new byte[]{
                0x00, 0x01, 0x02, 0x03
        };
        InputStream stream = new ByteArrayInputStream(source);
        new THash(stream);
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][]{
            {
                new byte[]{
                    0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
                    0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
                    0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17,
                    0x18, 0x19, 0x1a, 0x1b, 0x1c, 0x1d, 0x1e, 0x1f
                },
                "1f1e1d1c1b1a191817161514131211100f0e0d0c0b0a09080706050403020100",
            },
            {
                new byte[]{
                    0x48, 0x60, (byte)0xeb, 0x18, (byte)0xbf, 0x1b, 0x16, 0x20,
                    (byte)0xe3, 0x7e, (byte)0x94, (byte)0x90, (byte)0xfc, (byte)0x8a, 0x42, 0x75,
                    0x14, 0x41, 0x6f, (byte)0xd7, 0x51, 0x59, (byte)0xab, (byte)0x86,
                    0x68, (byte)0x8e, (byte)0x9a, (byte)0x83, 0x00, 0x00, 0x00, 0x00,
                },
                "00000000839a8e6886ab5951d76f411475428afc90947ee320161bbf18eb6048",
            },
        };
    }
}