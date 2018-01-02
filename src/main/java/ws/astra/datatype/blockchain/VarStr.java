package ws.astra.datatype.blockchain;

import ws.astra.datatype.Datatype;
import ws.astra.datatype.primitives.Char;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.joou.Unsigned.ulong;

public class VarStr extends Datatype<String> {
    /**
     * Ctor from value
     * @param value Value
     */
    public VarStr(String value) {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public VarStr(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public String read(InputStream stream) throws IOException {
        VarInt length = new VarInt(stream);
        Char value = new Char(stream, length.getValue().intValue());
        return value.getValue();
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        new VarInt(ulong(this.value.getBytes().length)).write(stream);
        new Char(this.value).write(stream);
    }
}
