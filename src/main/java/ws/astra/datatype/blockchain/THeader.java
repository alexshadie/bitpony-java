package ws.astra.datatype.blockchain;

import ws.astra.blockchain.Header;
import ws.astra.datatype.Datatype;
import ws.astra.datatype.primitives.UInt32;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class THeader extends Datatype<Header> {
    /**
     * Ctor from value
     * @param value Value
     */
    public THeader(Header value) {
        this.value = value;
    }

    /**
     * Stream ctor
     * @param stream Source stream
     * @throws IOException
     */
    public THeader(InputStream stream) throws IOException {
        this.value = this.read(stream);
    }

    /**
     * Stream reader
     * @param stream Source stream
     * @return Value
     * @throws IOException
     */
    @Override
    public Header read(InputStream stream) throws IOException {
        UInt32 version = new UInt32(stream);
        THash prevBlock = new THash(stream);
        THash merkleRoot = new THash(stream);
        UInt32 timestamp = new UInt32(stream);
        UInt32 bits = new UInt32(stream);
        UInt32 nonce = new UInt32(stream);
        VarInt txCount = new VarInt(stream);

        return new Header(
                version.getValue(),
                prevBlock.getValue(),
                merkleRoot.getValue(),
                timestamp.getValue(),
                bits.getValue(),
                nonce.getValue(),
                txCount.getValue()
        );
    }

    /**
     * Stream writer
     * @param stream Destination stream
     * @throws IOException
     */
    @Override
    public void write(OutputStream stream) throws IOException {
        new UInt32(this.value.getVersion()).write(stream);
        new THash(this.value.getPrevBlock()).write(stream);
        new THash(this.value.getMerkleRoot()).write(stream);
        new UInt32(this.value.getTimestamp()).write(stream);
        new UInt32(this.value.getBits()).write(stream);
        new UInt32(this.value.getNonce()).write(stream);
        new VarInt(this.value.getTxCount()).write(stream);
    }
}
