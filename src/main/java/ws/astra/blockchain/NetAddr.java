package ws.astra.blockchain;

import org.joou.UInteger;
import org.joou.ULong;
import org.joou.UShort;

import java.util.Arrays;

public class NetAddr {
    private UInteger time;
    private ULong services;
    private byte[] addr;
    private UShort port;

    public NetAddr(UInteger time, ULong services, byte[] addr, UShort port) {
        this.time = time;
        this.services = services;
        this.addr = addr;
        this.port = port;
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
        this.addr = addr;
    }

    public void setIPv4Addr(String addr) {
        // Parse str and set ipv4
    }

    public void setIPv4Addr(byte[] addr) {
        this.addr = new byte[16];
        for (int i = 0; i < 10; i++) {
            this.addr[i] = 0x00;
        }
        for (int i = 10; i < 12; i++) {
            this.addr[i] = (byte)0xff;
        }
        System.arraycopy(addr, 0, this.addr, 12, 4);
    }

    public void setIPv6Addr(String addr) {
        // Parse str and set ipv6
    }

    public void setIPv6Addr(byte[] addr) {
        this.addr = new byte[16];
        System.arraycopy(addr, 0, this.addr, 0, 16);
    }

    public UShort getPort() {
        return port;
    }

    public void setPort(UShort port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof NetAddr)) return false;

        NetAddr that = (NetAddr) obj;

        if (time != null ? !time.equals(that.time) : that.time != null) {
            return false;
        }
        if (services != null ? !services.equals(that.services) : that.services != null) {
            return false;
        }
        if (addr != null ? !Arrays.equals(addr, that.addr) : that.addr != null) {
            return false;
        }
        if (port != null ? !port.equals(that.port) : that.port != null) {
            return false;
        }
        return true;
    }
}
