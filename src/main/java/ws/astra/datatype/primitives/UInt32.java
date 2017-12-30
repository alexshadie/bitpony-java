package ws.astra.datatype.primitives;

import org.joou.UInteger;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;

import static org.joou.Unsigned.uint;

/**
 * Unsigned byte datatype representation.
 * 4 bytes in binary data, 1 UInteger value, long as scalar representation
 */
public class UInt32 extends Datatype<UInteger> {
    /**
     * Ctor from value
     * @param value Value
     */
    public UInt32(UInteger value) {
        super(value);
    }

    /**
     * Binary ctor
     * @param value Binary value
     * @throws IOException
     */
    public UInt32(byte[] value) throws IOException {
        super(value);
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public UInt32(InputStream stream) throws IOException {
        super(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    public UInteger readFromStream(InputStream stream) throws IOException {
        byte[] data = new byte[4];
        int length = stream.read(data);

        if (length != 4) {
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
    public UInteger fromBinary(byte[] value) throws IOException {
        if (value.length != 4) {
            throw new IOException("");
        }
        return uint(
                ((short)value[0] & 0x00ff) |
                        ((short)value[1] & 0x00ff) << 8 |
                        ((short)value[2] & 0x00ff) << 16 |
                        ((short)value[3] & 0x00ff) << 24
        );
    }

    /**
     * Binary representation of value
     * @param value Source value
     * @return Binary value
     */
    public byte[] toBinary(UInteger value) {
        int intVal = value.intValue();
        return new byte[]{
                (byte)(intVal & 0x00ff),
                (byte)(intVal >> 8 & 0x00ff),
                (byte)(intVal >> 16 & 0x00ff),
                (byte)(intVal >> 24),

        };
    }

    /**
     * Scalar value getter
     * @return Scalar-typed value
     */
    public long getScalarValue() {
        return this.getValue().longValue();
    }
}
