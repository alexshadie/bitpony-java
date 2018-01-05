package ws.astra.blockchain;

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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Hash)) return false;

        Hash that = (Hash)obj;

        if (value != null ? !Arrays.equals(value, that.value) : that.value != null) {
            return false;
        }

        return true;
    }
}
