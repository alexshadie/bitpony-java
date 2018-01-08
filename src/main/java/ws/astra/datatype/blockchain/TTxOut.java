package ws.astra.datatype.blockchain;

import ws.astra.blockchain.TxOut;
import ws.astra.datatype.Datatype;
import ws.astra.datatype.primitives.TByteArray;
import ws.astra.datatype.primitives.UInt64;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.joou.Unsigned.ulong;

public class TTxOut extends Datatype<TxOut> {
    /**
     * Ctor from value
     * @param value Value
     */
    public TTxOut(TxOut value) {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public TTxOut(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public TxOut read(InputStream stream) throws IOException {
        UInt64 amount = new UInt64(stream);
        VarInt scriptLength = new VarInt(stream);
        TByteArray scriptPubKey = new TByteArray(stream, scriptLength.getValue().intValue());

        return new TxOut(amount.getValue(), scriptPubKey.getValue());
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        new UInt64(this.value.getAmount()).write(stream);
        new VarInt(ulong(this.value.getScriptPubKey().getValue().length)).write(stream);
        new TByteArray(this.value.getScriptPubKey()).write(stream);
    }
}
