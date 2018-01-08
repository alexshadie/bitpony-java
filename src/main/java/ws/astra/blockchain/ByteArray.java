package ws.astra.blockchain;

import javax.annotation.Nonnull;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

/**
 * todo longer than MAX_INT
 */
public class ByteArray {
    byte[] value;

    public ByteArray(int length) {
        this.value = new byte[length];
    }

    public ByteArray(@Nonnull byte[] value) {
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ByteArray)) return false;

        ByteArray byteArray = (ByteArray) o;

        return Arrays.equals(getValue(), byteArray.getValue());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getValue());
    }

    @Override
    public String toString() {
        return DatatypeConverter.printHexBinary(this.value).toLowerCase();
    }
}
