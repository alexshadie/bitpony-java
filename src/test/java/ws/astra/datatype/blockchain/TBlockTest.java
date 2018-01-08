package ws.astra.datatype.blockchain;

import org.testng.annotations.Test;
import ws.astra.blockchain.Header;
import ws.astra.blockchain.Tx;
import ws.astra.blockchain.TxIn;
import ws.astra.blockchain.TxOut;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.joou.Unsigned.uint;
import static org.joou.Unsigned.ulong;

public class TBlockTest {
    @Test
    public void testBlockZero() throws Exception {
        InputStream stream = getClass().getResourceAsStream("/blk000000.dat");
        TBlock block = new TBlock(stream);
        assertThat(stream.available(), equalTo(0));

        Header header = block.getValue().getHeader();
        assertThat(header.getTxCount(), equalTo(ulong(1)));

        assertThat(header.getMerkleRoot().toString(), equalTo("0e3e2357e806b6cdb1f70b54c3a3a17b6714ee1f0e68bebb44a74b1efd512098"));
        assertThat(header.getPrevBlock().toString(), equalTo("000000000019d6689c085ae165831e934ff763ae46a2a6c172b3f1b60a8ce26f"));
        assertThat(header.getBits(), equalTo(uint(	486604799)));
        assertThat(header.getNonce(), equalTo(uint(2573394689L)));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date parsedDate = dateFormat.parse("2009-01-09 02:54:25");
        parsedDate.getTime();

        assertThat(header.getTimestamp(), equalTo(uint((parsedDate.getTime() / 1000))));
        assertThat(header.getVersion(), equalTo(uint(1)));

        Vector<Tx> txs = block.getValue().getTx();
        assertThat(txs.size(), equalTo(1));

        Tx tx = txs.get(0);

        // Test input
        assertThat(tx.getIn().size(), equalTo(1));

        TxIn txIn = tx.getIn().get(0);
        assertThat(txIn.getScriptSig().toString(), equalTo("04ffff001d0104"));

        assertThat(tx.getOut().size(), equalTo(1));

        TxOut txOut = tx.getOut().get(0);
        assertThat(txOut.getAmount(), equalTo(ulong(5000000000L)));
    }
}