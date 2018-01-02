package ws.astra.datatype.primitives;

import org.joou.ULong;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public UInt64(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public ULong read(InputStream stream) throws IOException {
        byte[] data = new byte[8];
        int length = stream.read(data);

        if (length != 8) {
            throw new IOException(Datatype.ERR_SHORT_STREAM);
        }

        return ulong(
                ((long)data[0] & 0x0ff) |
                        ((long)data[1] & 0x0ff) << 8 |
                        ((long)data[2] & 0x0ff) << 16 |
                        ((long)data[3] & 0x0ff) << 24 |
                        ((long)data[4] & 0x0ff) << 32 |
                        ((long)data[5] & 0x0ff) << 40 |
                        ((long)data[6] & 0x0ff) << 48 |
                        ((long)data[7] & 0x0ff) << 56
        );
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        long intVal = value.longValue();
        stream.write(new byte[]{
                (byte)(intVal & 0x0ff),
                (byte)(intVal >> 8 & 0x0ff),
                (byte)(intVal >> 16 & 0x0ff),
                (byte)(intVal >> 24 & 0x0ff),
                (byte)(intVal >> 32 & 0x0ff),
                (byte)(intVal >> 40 & 0x0ff),
                (byte)(intVal >> 48 & 0x0ff),
                (byte)(intVal >> 56),
        });
    }

    /**
     * Scalar value getter
     * @return Scalar-typed value
     */
    public long getScalarValue() {
        return this.getValue().longValue();
    }
}
