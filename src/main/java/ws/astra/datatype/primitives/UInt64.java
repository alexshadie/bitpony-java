package ws.astra.datatype.primitives;

import org.joou.UByte;
import org.joou.UInteger;
import org.joou.ULong;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

import static org.joou.Unsigned.uint;
import static org.joou.Unsigned.ulong;

/**
 * Unsigned byte datatype representation.
 * 8 bytes in binary data, 1 ULong value, long as scalar representation (doesn't show longs > 2^63, treat it as signed)
 * This affects only debug, so it doesn't seem to be any problem with it.
 */
public class UInt64 extends Datatype<ULong> {
    /**
     * Ctor from value
     * @param value Value
     */
    public UInt64(ULong value) {
        super(value);
    }

    /**
     * Binary ctor
     * @param value Binary value
     * @throws IOException
     */
    public UInt64(byte[] value) throws IOException {
        super(value);
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public UInt64(InputStream stream) throws IOException {
        super(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    public ULong readFromStream(InputStream stream) throws IOException {
        byte[] data = new byte[8];
        int length = stream.read(data);

        if (length != 8) {
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
    public ULong fromBinary(byte[] value) throws IOException {
        if (value.length != 8) {
            throw new IOException("");
        }
        byte byte1 = (byte)((long)value[7] & 0x00ff);
        byte byte2 = (byte)((long)value[6] & 0x00ff);
        byte byte3 = (byte)((long)value[5] & 0x00ff);
        byte byte4 = (byte)((long)value[4] & 0x00ff);
        byte byte5 = (byte)((long)value[3] & 0x00ff);
        byte byte6 = (byte)((long)value[2] & 0x00ff);
        byte byte7 = (byte)((long)value[1] & 0x00ff);
        byte byte8 = (byte)((long)value[0] & 0x00ff);
        return ulong(
                ((long)value[0] & 0x00ff) |
                        ((long)value[1] & 0x00ff) << 8 |
                        ((long)value[2] & 0x00ff) << 16 |
                        ((long)value[3] & 0x00ff) << 24 |
                        ((long)value[4] & 0x00ff) << 32 |
                        ((long)value[5] & 0x00ff) << 40 |
                        ((long)value[6] & 0x00ff) << 48 |
                        ((long)value[7] & 0x00ff) << 56
        );
    }

    /**
     * Binary representation of value
     * @param value Source value
     * @return Binary value
     */
    public byte[] toBinary(ULong value) {
        long intVal = value.longValue();
        return new byte[]{
                (byte)(intVal & 0x00ff),
                (byte)(intVal >> 8 & 0x00ff),
                (byte)(intVal >> 16 & 0x00ff),
                (byte)(intVal >> 24 & 0x00ff),
                (byte)(intVal >> 32 & 0x00ff),
                (byte)(intVal >> 40 & 0x00ff),
                (byte)(intVal >> 48 & 0x00ff),
                (byte)(intVal >> 56),
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
