package ws.astra.datatype.primitives;

import org.apache.commons.lang3.ArrayUtils;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;

/**
 * Naive String representation.
 * Doesn't really makes sense.
 */
public class Char extends Datatype<String> {
    /**
     * Ctor from value
     * @param value Value
     */
    public Char(String value) {
        super(value);
    }

    /**
     * Binary ctor
     * @param value Binary value
     * @throws IOException
     */
    public Char(byte[] value) throws IOException {
        super(value);
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public Char(InputStream stream) throws IOException {
        super(stream);
    }

    public Char(InputStream stream, int length) throws IOException {
        value = readFromStream(stream, length);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    public String readFromStream(InputStream stream) throws IOException {
        throw new RuntimeException("Stream reading not supported");
    }

    public String readFromStream(InputStream stream, int length) throws IOException {
        byte[] data = new byte[length];
        stream.read(data);
        return fromBinary(data);
    }

    /**
     * Binary reader
     * @param value Source data
     * @return Value
     * @throws IOException
     */
    public String fromBinary(byte[] value) throws IOException {
        ArrayUtils.reverse(value);
        return new String(value);
    }

    /**
     * Binary representation of value
     * @param value Source value
     * @return Binary value
     */
    public byte[] toBinary(String value) {
        byte[] data = value.getBytes();
        ArrayUtils.reverse(data);
        return data;
    }
}
