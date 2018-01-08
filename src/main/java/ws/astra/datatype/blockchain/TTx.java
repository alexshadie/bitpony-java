package ws.astra.datatype.blockchain;

import ws.astra.blockchain.Tx;
import ws.astra.blockchain.TxIn;
import ws.astra.blockchain.TxOut;
import ws.astra.datatype.Datatype;
import ws.astra.datatype.primitives.UInt32;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import static org.joou.Unsigned.ulong;

public class TTx extends Datatype<Tx> {
    /**
     * Ctor from value
     * @param value Value
     */
    public TTx(Tx value) {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public TTx(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public Tx read(InputStream stream) throws IOException {
        UInt32 version = new UInt32(stream);
        // @todo Witness flag
        VarInt inCount = new VarInt(stream);
        Vector<TxIn> txIn = new Vector<TxIn>();
        for (long i = 0; i < inCount.getValue().longValue(); i++) {
            TTxIn tx = new TTxIn(stream);
            txIn.add(tx.getValue());
        }
        VarInt outCount = new VarInt(stream);
        Vector<TxOut> txOut = new Vector<TxOut>();
        for (long i = 0; i < inCount.getValue().longValue(); i++) {
            TTxOut tx = new TTxOut(stream);
            txOut.add(tx.getValue());
        }
        // @todo: TxWitness
        UInt32 lockTime = new UInt32(stream);

        return new Tx(version.getValue(), txIn, txOut, lockTime.getValue());
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        new UInt32(this.value.getVersion()).write(stream);

        new VarInt(ulong(this.value.getIn().size())).write(stream);
        for (TxIn txIn : this.value.getIn()) {
            new TTxIn(txIn).write(stream);
        }

        new VarInt(ulong(this.value.getOut().size())).write(stream);
        for (TxOut txOut : this.value.getOut()) {
            new TTxOut(txOut).write(stream);
        }
        new UInt32(this.value.getLockTime()).write(stream);
    }
}
