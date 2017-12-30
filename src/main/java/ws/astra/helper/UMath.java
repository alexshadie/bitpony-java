package ws.astra.helper;

import org.joou.*;

import static org.joou.Unsigned.*;

public class UMath {
    public static UByte ULongToUByte(ULong source) {
        return ubyte((byte)(source.longValue() & 0x0ffL));
    }

    public static UShort ULongToUShort(ULong source) {
        return ushort((short)(source.longValue() & 0x0ffffL));
    }

    public static UInteger ULongToUInteger(ULong source) {
        return uint((int)(source.longValue() & 0x0ffffffffL));
    }

    public static ULong UByteToULong(UByte source) {
        return ulong(source.longValue());
    }

    public static ULong UShortToULong(UShort source) {
        return ulong(source.longValue());
    }

    public static ULong UIntegerToULong(UInteger source) {
        return ulong(source.longValue());
    }
}
