package MiniSpark.RDD;

import MiniSpark.Partitions.Partition;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
};
