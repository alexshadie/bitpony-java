package ws.astra.datatype.primitives;

import org.joou.UShort;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public UInt16(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public UShort read(InputStream stream) throws IOException {
        byte[] data = new byte[2];
        if (stream.read(data) != 2) {
            throw new IOException(Datatype.ERR_SHORT_STREAM);
        }

        return ushort(
                data[0] & 0x0ff |
                        data[1] << 8 & 0x0ff00
        );
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        short shortVal = value.shortValue();
        stream.write(
            new byte[]{
                (byte)(shortVal & 0x00ff),
                (byte)(shortVal >> 8)
            }
        );
    }

    /**
     * Scalar value getter
     * @return Scalar-typed value
     */
    public int getScalarValue() {
        return this.getValue().intValue();
    }
}
