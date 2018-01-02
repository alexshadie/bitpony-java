package ws.astra.datatype.blockchain;

import org.joou.UInteger;
import org.joou.ULong;
import org.joou.UShort;
import ws.astra.datatype.Datatype;
import ws.astra.datatype.primitives.UInt16;
import ws.astra.datatype.primitives.UInt32;
import ws.astra.datatype.primitives.UInt64;
import ws.astra.datatype.primitives.UInt8;
import ws.astra.helper.UMath;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.joou.Unsigned.*;

public class VarInt extends Datatype<ULong> {
    /**
     * Ctor from value
     * @param value Value
     */
    public VarInt(ULong value) {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public VarInt(InputStream stream) throws IOException {
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
        UInt8 mark = new UInt8(stream);

        switch (mark.getValue().byteValue()) {
            case (byte)0xff:
                ULong readLong = new UInt64(stream).getValue();
                if (readLong.compareTo(ulong(0x0ffffffffL)) <= 0) {
                    throw new IOException(Datatype.ERR_MALFORMED_BINARY);
                }
                return readLong;

            case (byte)0xfe:
                UInteger readInt = new UInt32(stream).getValue();
                if (readInt.compareTo(uint(0x0ffffL)) <= 0) {
                    throw new IOException(Datatype.ERR_MALFORMED_BINARY);
                }
                return UMath.UIntegerToULong(readInt);

            case (byte)0xfd:
                UShort readShort = new UInt16(stream).getValue();
                if (readShort.compareTo(ushort(0x0fc)) <= 0) {
                    throw new IOException(Datatype.ERR_MALFORMED_BINARY);
                }
                return UMath.UShortToULong(readShort);

            default:
                return UMath.UByteToULong(mark.getValue());
        }
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        if (value.compareTo(ulong(0x0fcL)) <= 0) {
            new UInt8(UMath.ULongToUByte(value)).write(stream);
        } else if (value.compareTo(ulong(0x0ffffL)) <= 0) {
            stream.write(new byte[]{(byte)0xfd});
            new UInt16(UMath.ULongToUShort(value)).write(stream);
        } else if (value.compareTo(ulong(0x0ffffffffL)) <= 0) {
            stream.write(new byte[]{(byte)0xfe});
            new UInt32(UMath.ULongToUInteger(value)).write(stream);
        } else {
            stream.write(new byte[]{(byte)0xff});
            new UInt64(value).write(stream);
        }
    }
}
