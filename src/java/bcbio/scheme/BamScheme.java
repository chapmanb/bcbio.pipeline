/*
package bcbio.bam;

import cascading.scheme.Scheme;
import cascading.tap.Tap;
import cascading.tuple.Fields;
import cascading.tuple.Tuple;
import cascading.tuple.TupleEntry;
import java.io.IOException;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.OutputCollector;
import bcbio.bam.BamSchemeInputFormat;

public class BamScheme extends Scheme {
    public BamScheme( Fields fields ) {
        super(fields);
    }

    @Override
    public void sourceInit( Tap tap, JobConf conf ) {
        conf.setInputFormat(BamSchemeInputFormat.class);
    }
    
    @Override
    public Tuple source( Object key, Object value )
    {
        Tuple tuple = new Tuple();
        tuple.add(key);
        tuple.add(value);
        return tuple;
    }
    
    @Override
    public void sinkInit(Tap tap, JobConf conf) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void sink(TupleEntry te, OutputCollector oc) throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
} 
*/
