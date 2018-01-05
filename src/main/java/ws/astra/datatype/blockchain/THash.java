package ws.astra.datatype.blockchain;

import com.google.common.primitives.Bytes;
import ws.astra.blockchain.Hash;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class THash extends Datatype<Hash> {
    /**
     * Ctor from value
     * @param value Value
     */
    public THash(Hash value) throws IOException {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public THash(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public Hash read(InputStream stream) throws IOException {
        byte[] val = new byte[32];
        if (stream.read(val) != 32) {
            throw new IOException(Datatype.ERR_MALFORMED_BINARY);
        }
        Bytes.reverse(val);
        return new Hash(val);
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        byte[] output = Arrays.copyOf(value.getValue(), 32);
        Bytes.reverse(output);
        stream.write(output);
    }
}
