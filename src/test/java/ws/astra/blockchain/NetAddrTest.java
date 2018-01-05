package ws.astra.blockchain;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.InetAddress;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.joou.Unsigned.uint;
import static org.joou.Unsigned.ulong;
import static org.joou.Unsigned.ushort;

public class NetAddrTest {
    private NetAddr addr1;
    private NetAddr addr2;

    @BeforeMethod
    public void init() throws Exception {
        addr1 = new NetAddr(uint(100500), ulong(100100500), InetAddress.getByName("192.168.0.1").getAddress(), ushort(3128));
        addr2 = new NetAddr(uint(100501), ulong(100100501), InetAddress.getByName("1080::8:800:200C:417A").getAddress(), ushort(8080));
    }

    @Test
    public void testConstructors() throws Exception {
        assertThat(addr1.getTime(), equalTo(uint(100500)));
        assertThat(addr1.getServices(), equalTo(ulong(100100500)));
        assertThat(addr1.getAddr(), equalTo(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xff, (byte)0xff, (byte)0xc0, (byte)0xa8, 0x00, 0x01}));
        assertThat(addr1.getPort(), equalTo(ushort(3128)));

        assertThat(addr2.getTime(), equalTo(uint(100501)));
        assertThat(addr2.getServices(), equalTo(ulong(100100501)));
        assertThat(addr2.getAddr(), equalTo(new byte[]{0x10, (byte)0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x08, 0x00, 0x20, 0x0c, 0x41, 0x7a}));
        assertThat(addr2.getPort(), equalTo(ushort(8080)));
    }

    @Test
    public void testGettersSetters() throws Exception {
        addr1.setTime(uint(200500));
        addr1.setServices(ulong(0x0ff));
        addr1.setPort(ushort(1024));
        assertThat(addr1.getTime(), equalTo(uint(200500)));
        assertThat(addr1.getServices(), equalTo(ulong(255)));
        assertThat(addr1.getPort(), equalTo(ushort(1024)));
    }

    @Test
    public void testAddrGettersSetters() throws Exception {
        addr1.setIPv4Addr("192.168.0.1");
        assertThat(addr1.getAddr(), equalTo(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xff, (byte)0xff, (byte)0xc0, (byte)0xa8, 0x00, 0x01}));
        assertThat(addr1.isIpV4(), is(true));
        addr1.setIPv4Addr(new byte[] {(byte)0xc0, (byte)0xa8, 0x00, 0x02});
        assertThat(addr1.getAddr(), equalTo(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xff, (byte)0xff, (byte)0xc0, (byte)0xa8, 0x00, 0x02}));
        assertThat(addr1.isIpV4(), is(true));
        addr1.setIPv6Addr("1080::8:800:200C:417A");
        assertThat(addr1.getAddr(), equalTo(new byte[]{0x10, (byte)0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x08, 0x00, 0x20, 0x0c, 0x41, 0x7a}));
        assertThat(addr1.isIpV4(), is(false));
        addr1.setIPv6Addr(new byte[]{0x10, (byte)0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x08, 0x00, 0x20, 0x0c, 0x41, 0x7b});
        assertThat(addr1.getAddr(), equalTo(new byte[]{0x10, (byte)0x80, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x08, 0x08, 0x00, 0x20, 0x0c, 0x41, 0x7b}));
        assertThat(addr1.isIpV4(), is(false));
    }

    @Test
    public void testEquals() throws Exception {
        NetAddr a = new NetAddr(null, null, null, null);
        NetAddr b = new NetAddr(null, null, null, null);

        assertThat(a, equalTo(b));

        a.setTime(uint(0));
        assertThat(a, not(equalTo(b)));

        b.setTime(uint(1));
        assertThat(a, not(equalTo(b)));

        a.setTime(uint(1));
        assertThat(a, equalTo(b));

        a.setServices(ulong(0x0ff));
        assertThat(a, not(equalTo(b)));

        b.setServices(ulong(0x01ff));
        assertThat(a, not(equalTo(b)));

        a.setServices(ulong(0x01ff));
        assertThat(a, equalTo(b));

        a.setPort(ushort(3128));
        assertThat(a, not(equalTo(b)));

        b.setPort(ushort(1024));
        assertThat(a, not(equalTo(b)));

        a.setPort(ushort(1024));
        assertThat(a, equalTo(b));

        a.setAddr(new byte[]{0x7f, 0x00, 0x00, 0x01});
        assertThat(a, not(equalTo(b)));

        b.setAddr(new byte[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xff, (byte)0xff, 0x7f, 0x00, 0x00, 0x02});
        assertThat(a, not(equalTo(b)));

        a.setAddr(InetAddress.getByName("127.0.0.2").getAddress());
        assertThat(a, equalTo(b));
    }

}