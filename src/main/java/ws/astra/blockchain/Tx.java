package ws.astra.blockchain;

import org.joou.UInteger;

import java.util.Vector;

/**
 * todo: TxWitness
 */
public class Tx {
    private UInteger version;
//    private UInteger inCount;
    private Vector<TxIn> in;
//    private UInteger outCount;
    private Vector<TxOut> out;
    private UInteger lockTime;

    public Tx(UInteger version, Vector<TxIn> in, Vector<TxOut> out, UInteger lockTime) {
        this.version = version;
        this.in = in;
        this.out = out;
        this.lockTime = lockTime;
    }

    public UInteger getVersion() {
        return version;
    }

    public void setVersion(UInteger version) {
        this.version = version;
    }

    public Vector<TxIn> getIn() {
        return in;
    }

    public void setIn(Vector<TxIn> in) {
        this.in = in;
    }

    public Vector<TxOut> getOut() {
        return out;
    }

    public void setOut(Vector<TxOut> out) {
        this.out = out;
    }

    public UInteger getLockTime() {
        return lockTime;
    }

    public void setLockTime(UInteger lockTime) {
        this.lockTime = lockTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tx)) return false;

        Tx tx = (Tx) o;

        if (!getVersion().equals(tx.getVersion())) return false;
        if (!getIn().equals(tx.getIn())) return false;
        if (!getOut().equals(tx.getOut())) return false;
        return getLockTime().equals(tx.getLockTime());
    }

    @Override
    public int hashCode() {
        int result = getVersion().hashCode();
        result = 31 * result + getIn().hashCode();
        result = 31 * result + getOut().hashCode();
        result = 31 * result + getLockTime().hashCode();
        return result;
    }
}
