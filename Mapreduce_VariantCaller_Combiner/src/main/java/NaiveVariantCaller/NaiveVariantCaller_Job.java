package NaiveVariantCaller;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.seqdoop.hadoop_bam.BAMInputFormat;
import utils.NaiveVariantCallerKeyWritable;
import utils.NaiveVariantCallerOutputFormat;
import utils.NaiveVariantCallerValueWritable;
import utils.NaiveVariantCallerBaseRecordWritable;

/**
 * master-thesis Clemens Banas
 * Organization: DBIS - University of Innsbruck
 * Created 02.10.15.
 */
public class NaiveVariantCaller_Job {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.set("mapreduce.map.java.opts", "-Xmx4000M");
        Job job = Job.getInstance(conf, "MR_VariantCaller_Combiner");

        job.setJarByClass(NaiveVariantCaller_Job.class);
        job.setMapperClass(NaiveVariantCaller_Mapper.class);
        job.setCombinerClass(NaiveVariantCaller_Combiner.class);
        job.setReducerClass(NaiveVariantCaller_Reducer.class);

        job.setInputFormatClass(BAMInputFormat.class);
        job.setMapOutputKeyClass(NaiveVariantCallerKeyWritable.class);
        job.setMapOutputValueClass(NaiveVariantCallerBaseRecordWritable.class);

        job.setOutputKeyClass(NaiveVariantCallerKeyWritable.class);
        job.setOutputValueClass(NaiveVariantCallerValueWritable.class);

        job.setOutputFormatClass(NaiveVariantCallerOutputFormat.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        if (!job.waitForCompletion(true)) {
            System.err.println("sort :: Job failed.");
        }
    }
}
