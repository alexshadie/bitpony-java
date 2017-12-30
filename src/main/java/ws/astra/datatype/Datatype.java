package ws.astra.datatype;

import java.io.IOException;
import java.io.InputStream;

/**
 * Common parent for all datatypes
 * @param <T> Value type
 */
public abstract class Datatype<T> {
    protected T value;

    /**
     * Default ctor
     */
    public Datatype() {
        value = null;
    }

    /**
     * Ctor from value
     * @param value Initializing value
     */
    public Datatype(T value) {
        this.value = value;
    }

    /**
     * Binary ctor
     * @param value Initializing byte array
     * @throws IOException
     */
    public Datatype(byte[] value) throws IOException {
        this.value = this.fromBinary(value);
    }

    /**
     * Stream ctor
     * @param stream Initializing stream
     * @throws IOException
     */
    public Datatype(InputStream stream) throws IOException {
        this.value = readFromStream(stream);
    }

    /**
     * Gets binary representation of object
     * @return Binary data
     */
    public byte[] getBytes() {
        return toBinary(value);
    }

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
    public abstract T readFromStream(InputStream stream) throws IOException;

    /**
     * Binary data reader
     * @param value Source data
     * @return Value
     * @throws IOException
     */
    public abstract T fromBinary(byte[] value) throws IOException;

    /**
     * Binary representation of value
     * @param value Source value
     * @return Binary data
     */
    public abstract byte[] toBinary(T value);
}
