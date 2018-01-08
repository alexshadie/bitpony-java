package ws.astra.blockchain;

import org.joou.ULong;

public class TxOut {
    private ULong amount;
    private ByteArray scriptPubKey;

    public TxOut(ULong amount, ByteArray scriptPubKey) {
        this.amount = amount;
        this.scriptPubKey = scriptPubKey;
    }

    public ULong getAmount() {
        return amount;
    }

    public void setAmount(ULong amount) {
        this.amount = amount;
    }

    public ByteArray getScriptPubKey() {
        return scriptPubKey;
    }

    public void setScriptPubKey(ByteArray scriptPubKey) {
        this.scriptPubKey = scriptPubKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TxOut)) return false;

        TxOut txOut = (TxOut) o;

        if (!getAmount().equals(txOut.getAmount())) return false;
        return getScriptPubKey().equals(txOut.getScriptPubKey());
    }

    @Override
    public int hashCode() {
        int result = getAmount().hashCode();
        result = 31 * result + getScriptPubKey().hashCode();
        return result;
    }
}
