package MiniSpark.RDD;

import MiniSpark.MiniSparkContext;
import MiniSpark.Partitions.Partition;

import java.util.List;
import java.util.Map;



public abstract class SimpleRDD {
    protected MiniSparkContext msc;
    private List<Partition> partitions;
    public Map<String, String> compute;

    public SimpleRDD(MiniSparkContext msc) {
        this.msc = msc;
    }

    // Get the partitions for this RDD.
    public abstract List<Partition> getPartitions();

    // Run a compute over a partition
    public abstract Map<String, ?> compute (Partition p);

}
