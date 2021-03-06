package ws.astra.blockchain;

import org.joou.UInteger;
import org.joou.ULong;
import org.joou.UShort;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.util.Arrays;

import static org.joou.Unsigned.uint;

public class NetAddr {
    public static final String ERR_INVALID_FORMAT = "Invalid address format";
    private UInteger time;
    private ULong services;
    private byte[] addr;
    private UShort port;

    public NetAddr(ULong services, byte[] addr, UShort port) {
        this(uint(0), services, addr, port);
    }

    public NetAddr(UInteger time, ULong services, byte[] addr, UShort port) {
        this.setTime(time);
        this.setServices(services);
        this.setAddr(addr);
        this.setPort(port);
    }

    public UInteger getTime() {
        return time;
    }

    public void setTime(UInteger time) {
        this.time = time;
    }

    public ULong getServices() {
        return services;
    }

    public void setServices(ULong services) {
        this.services = services;
    }

    public byte[] getAddr() {
        return addr;
    }

    public void setAddr(byte[] addr) {
        if (addr == null) {
            this.addr = null;
        } else if (addr.length == 16) {
            this.addr = addr;
        } else if (addr.length == 4) {
            this.addr = new byte[]{
                    0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, 0x00, 0x00,
                    0x00, 0x00, (byte)0xff, (byte)0xff,
                    addr[0], addr[1], addr[2], addr[3]
            };
        } else {
            throw new RuntimeException(ERR_INVALID_FORMAT);
        }
    }

    public void setIPv4Addr(String addr) throws UnknownHostException {
        this.setIPv4Addr(Inet4Address.getByName(addr).getAddress());
    }

    public void setIPv4Addr(byte[] addr) {
        if (addr.length != 4) {
            throw new RuntimeException(ERR_INVALID_FORMAT);
        }
        this.setAddr(addr);
    }

    public void setIPv6Addr(String addr) throws UnknownHostException {
        this.setIPv6Addr(Inet6Address.getByName(addr).getAddress());
    }

    public void setIPv6Addr(byte[] addr) {
        if (addr.length != 16) {
            throw new RuntimeException(ERR_INVALID_FORMAT);
        }
        this.setAddr(addr);
    }

    public boolean isIpV4() {
        return addr != null && (addr[0] | addr[1] | addr[2] | addr[3] | addr[4] | addr[5] | addr[6] | addr[7] | addr[8] | addr[9] | ~(addr[10]) | ~(addr[11])) == 0x00;
    }

    public UShort getPort() {
        return port;
    }

    public void setPort(UShort port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NetAddr)) return false;

        NetAddr netAddr = (NetAddr) o;

        if (getTime() != null ? !getTime().equals(netAddr.getTime()) : netAddr.getTime() != null) return false;
        if (getServices() != null ? !getServices().equals(netAddr.getServices()) : netAddr.getServices() != null)
            return false;
        if (!Arrays.equals(getAddr(), netAddr.getAddr())) return false;
        return getPort() != null ? getPort().equals(netAddr.getPort()) : netAddr.getPort() == null;
    }

    @Override
    public int hashCode() {
        int result = getTime() != null ? getTime().hashCode() : 0;
        result = 31 * result + (getServices() != null ? getServices().hashCode() : 0);
        result = 31 * result + Arrays.hashCode(getAddr());
        result = 31 * result + (getPort() != null ? getPort().hashCode() : 0);
        return result;
    }
}
