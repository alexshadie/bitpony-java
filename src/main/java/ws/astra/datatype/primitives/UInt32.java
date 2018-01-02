package ws.astra.datatype.primitives;

import org.joou.UInteger;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public UInt32(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public UInteger read(InputStream stream) throws IOException {
        byte[] data = new byte[4];

        if (stream.read(data) != 4) {
            throw new IOException("");
        }

        return uint(
            ((short)data[0] & 0x00ff) |
                ((short)data[1] & 0x00ff) << 8 |
                ((short)data[2] & 0x00ff) << 16 |
                ((short)data[3] & 0x00ff) << 24
        );
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        int intVal = value.intValue();
        stream.write(
            new byte[]{
                (byte)(intVal & 0x00ff),
                (byte)(intVal >> 8 & 0x00ff),
                (byte)(intVal >> 16 & 0x00ff),
                (byte)(intVal >> 24),
            }
        );
    }

    /**
     * Scalar value getter
     * @return Scalar-typed value
     */
    public long getScalarValue() {
        return this.getValue().longValue();
    }
}
