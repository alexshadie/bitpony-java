package ws.astra.blockchain;

import java.util.Vector;

public class Block {
    private Header header;
    private Vector<Tx> tx;

    public Block(Header header, Vector<Tx> tx) {
        this.header = header;
        this.tx = tx;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Vector<Tx> getTx() {
        return tx;
    }

    public void setTx(Vector<Tx> tx) {
        this.tx = tx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block)) return false;

        Block block = (Block) o;

        if (!getHeader().equals(block.getHeader())) return false;
        return getTx().equals(block.getTx());
    }

    @Override
    public int hashCode() {
        int result = getHeader().hashCode();
        result = 31 * result + getTx().hashCode();
        return result;
    }
}
