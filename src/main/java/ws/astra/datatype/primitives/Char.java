package ws.astra.datatype.primitives;

import org.apache.commons.lang3.ArrayUtils;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

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
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public Char(InputStream stream) throws IOException {
        throw new IOException("Unbounded stream");
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @param length bytes to be captured
     * @throws IOException
     */
    public Char(InputStream stream, int length) throws IOException {
        this.value = read(stream, length);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public String read(InputStream stream) throws IOException {
        throw new IOException("Unbounded stream");
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @param length Bytes to be captured
     * @return Value
     * @throws IOException
     */
    public String read(InputStream stream, int length) throws IOException {
        byte[] data = new byte[length];
        int readCount = stream.read(data, 0, length);
        if (readCount < length) {
            throw new IOException(Datatype.ERR_SHORT_STREAM);
        }
        ArrayUtils.reverse(data);
        return new String(data);
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        byte[] data = Arrays.copyOf(this.value.getBytes(), this.value.getBytes().length);
        ArrayUtils.reverse(data);
        stream.write(data);
    }
}
