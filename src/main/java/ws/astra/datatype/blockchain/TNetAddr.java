package ws.astra.datatype.blockchain;

import ws.astra.blockchain.NetAddr;
import ws.astra.datatype.Datatype;
import ws.astra.datatype.primitives.UInt16;
import ws.astra.datatype.primitives.UInt32;
import ws.astra.datatype.primitives.UInt64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TNetAddr extends Datatype<NetAddr> {
    /**
     * Ctor from value
     * @param value Value
     */
    public TNetAddr(NetAddr value) {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public TNetAddr(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public NetAddr read(InputStream stream) throws IOException {
        UInt32 time = new UInt32(stream);
        UInt64 services = new UInt64(stream);
        byte[] addr = new byte[16];
        if (16 != stream.read(addr)) {
            throw new IOException(Datatype.ERR_SHORT_STREAM);
        }
        UInt16 port = new UInt16(stream);
        return new NetAddr(time.getValue(), services.getValue(), addr, port.getValue());
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        new UInt32(value.getTime()).write(stream);
        new UInt64(value.getServices()).write(stream);
        stream.write(value.getAddr());
        new UInt16(value.getPort()).write(stream);
    }
}
