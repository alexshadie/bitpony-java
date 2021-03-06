package ws.astra.blockchain;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Arrays;

public class Hash {
    private byte[] value;

    public Hash(byte[] value) throws IOException {
        this.setValue(value);
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) throws IOException {
        if (value.length != 32) {
            throw new IOException("Malformed hash");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hash)) return false;

        Hash hash = (Hash) o;

        return Arrays.equals(getValue(), hash.getValue());
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
