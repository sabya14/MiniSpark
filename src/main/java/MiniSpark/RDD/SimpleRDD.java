package MiniSpark.RDD;

import MiniSpark.MiniSparkContext;
import MiniSpark.Partitions.Partition;

import java.util.List;
import java.util.Map;
import java.util.function.Function;


public abstract class SimpleRDD<T, SomePartitionType extends Partition> {
    protected MiniSparkContext msc;
    private List<SomePartitionType> partitions;
    public Map<String, String> compute;

    public SimpleRDD(MiniSparkContext msc) {
        this.msc = msc;
    }

    // Get the partitions for this RDD.
    public abstract List<SomePartitionType> getPartitions();

    // Run a compute over a partition
    public abstract Map<String, T> compute(SomePartitionType p);

    public <U> SimpleRDD<U, SomePartitionType> mapRDD(final Function<T, U> mapper) {
        return new MapSimpleRDD<>(this, mapper);
    }
}

