package MiniSpark.Partitions;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public  class FilePartition extends Partition {
    int partitionIndex;
    Pair posAndOffsets;

    @Override
    public String toString() {
        return partitionIndex + posAndOffsets.toString();
    }
}
