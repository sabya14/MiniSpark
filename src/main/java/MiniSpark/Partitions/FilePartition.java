package MiniSpark.Partitions;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public  class FilePartition extends Partition {
    int partitionIndex;
    long startIndex;
    long stopIndex;

    @Override
    public String toString() {
        return String.valueOf(partitionIndex) + String.valueOf(startIndex) + String.valueOf(stopIndex);
    }
}
