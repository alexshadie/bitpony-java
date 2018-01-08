package ws.astra.datatype.blockchain;

import ws.astra.blockchain.TxIn;
import ws.astra.datatype.Datatype;
import ws.astra.datatype.primitives.TByteArray;
import ws.astra.datatype.primitives.UInt32;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.joou.Unsigned.ulong;

public class TTxIn extends Datatype<TxIn> {
    /**
     * Ctor from value
     * @param value Value
     */
    public TTxIn(TxIn value) {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public TTxIn(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public TxIn read(InputStream stream) throws IOException {
        // Previous output
        THash hash = new THash(stream);
        UInt32 index = new UInt32(stream);
        VarInt scriptLength = new VarInt(stream);
        TByteArray signatureScript = new TByteArray(stream, scriptLength.getValue().intValue());
        UInt32 sequence = new UInt32(stream);

        return new TxIn(hash.getValue(), index.getValue(), signatureScript.getValue(), sequence.getValue());
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        new THash(this.value.getHash()).write(stream);
        new UInt32(this.value.getIndex()).write(stream);
        new VarInt(ulong(this.value.getScriptSig().getValue().length)).write(stream);
        new TByteArray(this.value.getScriptSig()).write(stream);
        new UInt32(this.value.getSequence()).write(stream);
    }
}
