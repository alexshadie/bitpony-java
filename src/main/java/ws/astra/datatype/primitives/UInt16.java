package ws.astra.datatype.primitives;

import org.joou.UShort;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;

import static org.joou.Unsigned.ushort;

/**
 * Unsigned byte datatype representation.
 * 2 bytes in binary data, 1 UShort value, int as scalar representation
 */
public class UInt16 extends Datatype<UShort> {
    /**
     * Ctor from value
     * @param value Value
     */
    public UInt16(UShort value) {
        super(value);
    }

    /**
     * Binary ctor
     * @param value Binary value
     * @throws IOException
     */
    public UInt16(byte[] value) throws IOException {
        super(value);
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public UInt16(InputStream stream) throws IOException {
        super(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    public UShort readFromStream(InputStream stream) throws IOException {
        byte[] data = new byte[2];
        int length = stream.read(data);

        if (length != 2) {
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
    public UShort fromBinary(byte[] value) throws IOException {
        if (value.length != 2) {
            throw new IOException("");
        }
        return ushort(
                (value[0] & 0x000000ff) |
                    (value[1] << 8 & 0x0000ff00)
        );
    }

    /**
     * Binary representation of value
     * @param value Source value
     * @return Binary value
     */
    public byte[] toBinary(UShort value) {
        short shortVal = value.shortValue();
        return new byte[]{
                (byte)(shortVal & 0x00ff),
                (byte)(shortVal >> 8)
        };
    }

    /**
     * Scalar value getter
     * @return Scalar-typed value
     */
    public int getScalarValue() {
        return this.getValue().intValue();
    }
}
