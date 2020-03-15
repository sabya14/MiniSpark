package MiniSpark.RDD;

import MiniSpark.MiniSparkContext;
import MiniSpark.Partitions.FilePartition;
import MiniSpark.Partitions.Partition;
import javafx.util.Pair;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FileSimpleRDD extends SimpleRDD {
    RandomAccessFile randomAccessFile;

    public FileSimpleRDD(MiniSparkContext msc, RandomAccessFile randomAccessFile) {
        super(msc);
        this.randomAccessFile = randomAccessFile;
    }

    @Override
    public List<Partition> getPartitions() {
        List<Partition> filePartitions = new ArrayList<>();
        try {
            long fileLength = randomAccessFile.length();
            long noOfPartitions = msc.getParallelism();
            long approxPartitionLength = fileLength / noOfPartitions;

            ArrayList<Pair<Long, Long>> readBlocks = new ArrayList<>();

            long startPosition = 1;
            long seekToPosition = approxPartitionLength - 1;
            randomAccessFile.seek(seekToPosition);

            // Iter till next newline

            while (readBlocks.size() < msc.getParallelism() - 1) {
                long goAhead = 0;
                while (randomAccessFile.readByte() != 10) {
                    goAhead++;
                }
                readBlocks.add(new Pair<>(startPosition, seekToPosition + goAhead));
                seekToPosition = startPosition = seekToPosition + goAhead + 2;
                randomAccessFile.seek(seekToPosition);
            }
            readBlocks.forEach(position ->
                    filePartitions.add(new FilePartition(filePartitions.size(), position))
            );
            filePartitions.add(new FilePartition(filePartitions.size(), new Pair<>(seekToPosition, randomAccessFile.length())));
            return filePartitions;

        } catch (IOException e) {
            // TODO introduce logging later
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Map<String, ?> compute(Partition p) {
        return null;
    }


}
