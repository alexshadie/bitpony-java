package ws.astra.datatype.blockchain;

import ws.astra.blockchain.*;
import ws.astra.datatype.Datatype;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

public class TBlock extends Datatype<Block> {
    /**
     * Ctor from value
     * @param value Value
     */
    public TBlock(Block value) {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public TBlock(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public Block read(InputStream stream) throws IOException {
        THeader header = new THeader(stream);

        Vector<Tx> tx = new Vector<Tx>();
        for (long i = 0; i < header.getValue().getTxCount().longValue(); i++) {
            tx.add(new TTx(stream).getValue());
        }
        return new Block(header.getValue(), tx);
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        new THeader(this.value.getHeader()).write(stream);
        for (Tx tx : this.value.getTx()) {
            new TTx(tx).write(stream);
        }
    }
}
