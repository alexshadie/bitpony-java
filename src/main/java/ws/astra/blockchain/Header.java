package ws.astra.blockchain;

import org.joou.UInteger;
import org.joou.ULong;

public class Header {
    private UInteger version;
    private Hash prevBlock;
    private Hash merkleRoot;
    private UInteger timestamp;
    private UInteger bits;
    private UInteger nonce;
    private ULong txCount;

    public Header(UInteger version, Hash prevBlock, Hash merkleRoot, UInteger timestamp, UInteger bits, UInteger nonce, ULong txCount) {
        this.version = version;
        this.prevBlock = prevBlock;
        this.merkleRoot = merkleRoot;
        this.timestamp = timestamp;
        this.bits = bits;
        this.nonce = nonce;
        this.txCount = txCount;
    }

    public UInteger getVersion() {
        return version;
    }

    public void setVersion(UInteger version) {
        this.version = version;
    }

    public Hash getPrevBlock() {
        return prevBlock;
    }

    public void setPrevBlock(Hash prevBlock) {
        this.prevBlock = prevBlock;
    }

    public Hash getMerkleRoot() {
        return merkleRoot;
    }

    public void setMerkleRoot(Hash merkleRoot) {
        this.merkleRoot = merkleRoot;
    }

    public UInteger getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(UInteger timestamp) {
        this.timestamp = timestamp;
    }

    public UInteger getBits() {
        return bits;
    }

    public void setBits(UInteger bits) {
        this.bits = bits;
    }

    public UInteger getNonce() {
        return nonce;
    }

    public void setNonce(UInteger nonce) {
        this.nonce = nonce;
    }

    public ULong getTxCount() {
        return txCount;
    }

    public void setTxCount(ULong txCount) {
        this.txCount = txCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Header)) return false;

        Header header = (Header) o;

        if (!getVersion().equals(header.getVersion())) return false;
        if (!getPrevBlock().equals(header.getPrevBlock())) return false;
        if (!getMerkleRoot().equals(header.getMerkleRoot())) return false;
        if (!getTimestamp().equals(header.getTimestamp())) return false;
        if (!getBits().equals(header.getBits())) return false;
        if (!getNonce().equals(header.getNonce())) return false;
        return getTxCount().equals(header.getTxCount());
    }

    @Override
    public int hashCode() {
        int result = getVersion().hashCode();
        result = 31 * result + getPrevBlock().hashCode();
        result = 31 * result + getMerkleRoot().hashCode();
        result = 31 * result + getTimestamp().hashCode();
        result = 31 * result + getBits().hashCode();
        result = 31 * result + getNonce().hashCode();
        result = 31 * result + getTxCount().hashCode();
        return result;
    }
}
