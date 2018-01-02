package ws.astra.datatype.primitives;

import org.joou.UByte;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.joou.Unsigned.ubyte;

/**
 * Unsigned byte datatype representation.
 * 1 byte in binary data, 1 UByte value, short as scalar representation
 */
public class UInt8 extends Datatype<UByte> {
    /**
     * Ctor from value
     * @param value Value
     */
    public UInt8(UByte value) {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public UInt8(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public UByte read(InputStream stream) throws IOException {
        byte[] data = new byte[1];
        if (stream.read(data) < 1) {
            throw new IOException(Datatype.ERR_SHORT_STREAM);
        }
        return ubyte(data[0]);
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        byte data[] = new byte[1];
        data[0] = this.value.byteValue();
        stream.write(data);
    }

    /**
     * Scalar value getter
     * @return Scalar-typed value
     */
    public short getScalarValue() {
        return this.getValue().shortValue();
    }
}
