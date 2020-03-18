package MiniSpark.RDD;

import MiniSpark.Partitions.Partition;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;


public class MapSimpleRDD<T, U, SomePartitionType extends Partition> extends SimpleRDD<U, SomePartitionType> {

    private final SimpleRDD<T, SomePartitionType> parent;
    private final Function<T, U> mapper;

    public MapSimpleRDD(SimpleRDD<T, SomePartitionType> parent, Function<T, U> mapper) {
        super(parent.msc);
        this.parent = parent;
        this.mapper = mapper;
    }

    @Override
    public List<SomePartitionType> getPartitions() {
        return parent.getPartitions();
    }

    @Override
    public Map<String, U> compute(SomePartitionType p) {
        Map<String, T> parentData = parent.compute(p);
        Map<String, U> mappedData = new LinkedHashMap<>();
        parentData.forEach((key,value) -> mappedData.put(key, mapper.apply(value)));
        return mappedData;
    }

    // Given keys with same value, it will combine it into a single value
    public MapSimpleRDD<T, U, SomePartitionType> reduceByKey(BiFunction<T, T, T> aggregator) {
        // In each partion reduce by key, return a RDD from it.
        // RDD with same keys s
        // This is an action. It will call the compute functions. It should do it in parallel.
        // Then based on partitioner it will send data to different RDD where the data will be reduced.
    }

};
