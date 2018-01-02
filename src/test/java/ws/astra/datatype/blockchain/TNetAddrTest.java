package ws.astra.datatype.blockchain;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ws.astra.blockchain.NetAddr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.joou.Unsigned.uint;
import static org.joou.Unsigned.ulong;
import static org.joou.Unsigned.ushort;
import static org.junit.Assert.assertThat;

public class TNetAddrTest {
    @Test(dataProvider = "testData")
    public void testRead(byte[] source, NetAddr expected) throws IOException {
        TNetAddr d = new TNetAddr(new ByteArrayInputStream(source));
        assertThat(d.getValue(), equalTo(expected));
    }

    @Test(dataProvider = "testData")
    public void testWrite(byte[] expected, NetAddr source) throws IOException {
        TNetAddr d = new TNetAddr(source);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        d.write(stream);
        assertThat(stream.toByteArray(), equalTo(expected));
    }

    @DataProvider
    public Object[][] testData() {
        return new Object[][] {
                {
                    new byte[]{
                            (byte)0x94, (byte)0x88, 0x01, 0x00,
                            (byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff, 0x00, 0x00, 0x00, 0x00,
                            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xff, (byte)0xff, 0x7f, 0x00, 0x00, 0x01,
                            (byte)0x01, 0x04
                    },
                    new NetAddr(
                            uint(100500),
                            ulong(0xffffffffL),
                            new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xff, (byte)0xff, 0x7f, 0x00, 0x00, 0x01},
                            ushort(1025)
                        )
                }
        };
    }

}