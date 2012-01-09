/* Provide a BamInputFormat compatible with old-style FileInputFormat API.
   
The new-style API, included in hadoop-bam, is not compatible with
Cascading Schemes.

Useful references for mapping between old style JobConf and new style contexts:

https://gist.github.com/808035
http://hadoop.apache.org/common/docs/r0.20.2/mapred_tutorial.html#Task+JVM+Reuse
http://hadoop.apache.org/common/docs/current/api/org/apache/hadoop/mapred/JobConf.html
http://hadoop.apache.org/common/docs/r0.20.1/api/org/apache/hadoop/mapreduce/TaskAttemptContext.html
*/

/*
package bcbio.bam;

import java.io.IOException;
import java.util.List;

import org.apache.hadoop.mapred.RecordReader;
import org.apache.hadoop.mapred.InputSplit;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import fi.tkk.ics.hadoop.bam.BAMInputFormat;
import fi.tkk.ics.hadoop.bam.SAMRecordWritable;

public class BamSchemeInputFormat extends FileInputFormat<LongWritable, SAMRecordWritable> {
	@Override
	public InputSplit[] getSplits(JobConf conf, int numSplits) throws IOException {
		BAMInputFormat newapi = new BAMInputFormat();
        JobID curJobID = JobID.forName(conf.get("mapred.job.id"));
        JobContext ctx = new JobContext(conf, curJobID);
		List<InputSplit> out = newapi.getSplits(ctx);
    }

	@Override
	public RecordReader<LongWritable, SAMRecordWritable> 
		   getRecordReader(InputSplit split, JobConf conf, Reporter reporter)
		   throws IOException {
		BAMInputFormat newapi = new BAMInputFormat();
		TaskAttemptID attemptID = TaskAttemptID.forName(conf.get("mapred.task.id"));
        TaskAttemptContext ctx = new TaskAttemptContext(conf, attemptID);
		return newapi.createRecordReader(split, ctx);
	}
    
    @Override
    public boolean isSplitable(FileSystem fs, Path filename) {
		return true;
    }

}
*/
