package ws.astra.datatype.primitives;

import org.joou.UByte;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;

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
        super(value);
    }

    /**
     * Binary ctor
     * @param value Binary value
     * @throws IOException
     */
    public UInt8(byte[] value) throws IOException {
        super(value);
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public UInt8(InputStream stream) throws IOException {
        super(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    public UByte readFromStream(InputStream stream) throws IOException {
        byte[] data = new byte[1];
        int length = stream.read(data);

        if (length < 1) {
            throw new IOException("");
        }

        return fromBinary(data);
    }

    /**
     * Binary reader
     * @param value Source data
     * @return Value
     * @throws IOException
     */
    public UByte fromBinary(byte[] value) throws IOException {
        if (value.length != 1) {
            throw new IOException("");
        }
        return ubyte(value[0]);
    }

    /**
     * Binary representation of value
     * @param value Source value
     * @return Binary value
     */
    public byte[] toBinary(UByte value) {
        return new byte[]{this.getValue().byteValue()};
    }

    /**
     * Scalar value getter
     * @return Scalar-typed value
     */
    public short getScalarValue() {
        return this.getValue().shortValue();
    }
}
