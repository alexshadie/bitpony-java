package ws.astra.datatype.blockchain;

import com.google.common.primitives.Bytes;
import org.joou.ULong;
import ws.astra.datatype.Datatype;
import ws.astra.datatype.primitives.UInt16;
import ws.astra.datatype.primitives.UInt32;
import ws.astra.datatype.primitives.UInt64;
import ws.astra.datatype.primitives.UInt8;
import ws.astra.helper.UMath;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.joou.Unsigned.*;

public class VarInt extends Datatype<ULong> {
    /**
     * Ctor from value
     * @param value Value
     */
    public VarInt(ULong value) {
        super(value);
    }

    /**
     * Binary ctor
     * @param value Binary value
     * @throws IOException
     */
    public VarInt(byte[] value) throws IOException {
        super(value);
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public VarInt(InputStream stream) throws IOException {
        super(stream);
    }

    /**
     * Stream reader
     * @todo Add error hanling
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    public ULong readFromStream(InputStream stream) throws IOException {
        byte mark[] = new byte[1];
        byte content[];

        stream.read(mark);
        switch (mark[0]) {
            case (byte)0xff:
                content = new byte[8];
                stream.read(content);
                break;
            case (byte)0xfe:
                content = new byte[4];
                stream.read(content);
                break;
            case (byte)0xfd:
                content = new byte[2];
                stream.read(content);
                break;
            default:
                content = new byte[0];
                break;
        }

        return this.fromBinary(Bytes.concat(mark, content));
    }

    /**
     * Binary reader
     * @todo Add error hanling
     * @param value Source data
     * @return Value
     * @throws IOException
     */
    public ULong fromBinary(byte[] value) throws IOException {
        switch (value[0]) {
            case (byte)0xff:
                return (new UInt64(Arrays.copyOfRange(value, 1, 9))).getValue();
            case (byte)0xfe:
                return UMath.UIntegerToULong((new UInt32(Arrays.copyOfRange(value, 1, 5))).getValue());
            case (byte)0xfd:
                return UMath.UShortToULong((new UInt16(Arrays.copyOfRange(value, 1, 3))).getValue());
            default:
                return UMath.UByteToULong((new UInt8(ubyte(value[0]))).getValue());
        }
    }

    /**
     * Binary representation of value
     * @param value Source value
     * @return Binary value
     */
    public byte[] toBinary(ULong value) {
        if (value.compareTo(ulong(0x0fcL)) <= 0) {
            // Single byte
            return (new UInt8(UMath.ULongToUByte(value))).getBytes();
        } else if (value.compareTo(ulong(0x0ffffL)) <= 0) {
            return Bytes.concat(new byte[]{(byte)0xfd}, new UInt16(UMath.ULongToUShort(value)).getBytes());
        } else if (value.compareTo(ulong(0x0ffffffffL)) <= 0) {
            return Bytes.concat(new byte[]{(byte)0xfe}, (new UInt32(UMath.ULongToUInteger(value)).getBytes()));
        } else {
            return Bytes.concat(new byte[]{(byte)0xff}, (new UInt64(value).getBytes()));
        }
    }
}
