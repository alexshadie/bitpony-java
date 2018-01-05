package ws.astra;

import com.google.common.io.BaseEncoding;

import javax.xml.bind.DatatypeConverter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

public class TestHelper {
    public static byte[] HexStringToBytes(String source) {
        return DatatypeConverter.parseHexBinary(source);
//        HexBinaryAdapter adapter = new HexBinaryAdapter();
//        return adapter.unmarshal(source);
    }

    public static String BytesToHexString(byte[] source) {
        return DatatypeConverter.printHexBinary(source);
    }
}
