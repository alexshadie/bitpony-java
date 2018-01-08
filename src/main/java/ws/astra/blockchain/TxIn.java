package ws.astra.blockchain;

import org.joou.UInteger;

public class TxIn {
    private Hash hash;
    private UInteger index;
    private ByteArray scriptSig;
    private UInteger sequence;

    public TxIn(Hash hash, UInteger index, ByteArray scriptSig, UInteger sequence) {
        this.hash = hash;
        this.index = index;
        this.scriptSig = scriptSig;
        this.sequence = sequence;
    }

    public Hash getHash() {
        return hash;
    }

    public void setHash(Hash hash) {
        this.hash = hash;
    }

    public UInteger getIndex() {
        return index;
    }

    public void setIndex(UInteger index) {
        this.index = index;
    }

    public ByteArray getScriptSig() {
        return scriptSig;
    }

    public void setScriptSig(ByteArray scriptSig) {
        this.scriptSig = scriptSig;
    }

    public UInteger getSequence() {
        return sequence;
    }

    public void setSequence(UInteger sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TxIn)) return false;

        TxIn txIn = (TxIn) o;

        if (!getHash().equals(txIn.getHash())) return false;
        if (!getIndex().equals(txIn.getIndex())) return false;
        if (!getScriptSig().equals(txIn.getScriptSig())) return false;
        return getSequence().equals(txIn.getSequence());
    }

    @Override
    public int hashCode() {
        int result = getHash().hashCode();
        result = 31 * result + getIndex().hashCode();
        result = 31 * result + getScriptSig().hashCode();
        result = 31 * result + getSequence().hashCode();
        return result;
    }
}
