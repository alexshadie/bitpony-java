package ws.astra.datatype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Common parent for all datatypes
 * @param <T> Value type
 */
public abstract class Datatype<T> {
    public static final String ERR_SHORT_STREAM = "Insufficient stream length";
    public static final String ERR_MALFORMED_BINARY = "Malformed binary";
    protected T value;

    /**
     * Gets object value
     * @return Value
     */
    public T getValue() {
        return value;
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    public abstract T read(InputStream stream) throws IOException;

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    public abstract void write(OutputStream stream) throws IOException;
}
