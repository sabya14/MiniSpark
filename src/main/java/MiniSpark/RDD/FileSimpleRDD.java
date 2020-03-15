package MiniSpark.RDD;

import MiniSpark.MiniSparkContext;
import MiniSpark.Partitions.FilePartition;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;


public class FileSimpleRDD extends SimpleRDD<String, FilePartition> {
    private RandomAccessFile randomAccessFile;

    public FileSimpleRDD(MiniSparkContext msc, RandomAccessFile randomAccessFile) {
        super(msc);
        this.randomAccessFile = randomAccessFile;
    }

    @Override
    public List<FilePartition> getPartitions() {
        List<FilePartition> filePartitions = new ArrayList<>();
        try {
            long fileLength = randomAccessFile.length();
            long noOfPartitions = msc.getParallelism();
            long approxPartitionLength = fileLength / noOfPartitions;

            Map<Long, Long> readBlocks = new LinkedHashMap<>();

            long startPosition = 0;
            long seekToPosition = approxPartitionLength - 1;
            randomAccessFile.seek(seekToPosition);
            while (readBlocks.size() < msc.getParallelism() - 1) {
                // Iter till next newline
                long goAhead = 0;
                while (randomAccessFile.readByte() != 10) {
                    goAhead++;
                }
                long readTill = seekToPosition + goAhead + 1;
                readBlocks.put(startPosition, readTill);
                seekToPosition = startPosition = readTill;
                randomAccessFile.seek(seekToPosition);
            }
            // For adding the last partition
            readBlocks.forEach((position, endPosition)->
                    filePartitions.add(new FilePartition(filePartitions.size(), position, endPosition))
            );
            filePartitions.add(new FilePartition(filePartitions.size(), seekToPosition, randomAccessFile.length()));
            return filePartitions;

        } catch (IOException e) {
            // TODO introduce logging later
            e.printStackTrace();
            return null;
        }

    }

    @Override
    // We can return iterators here, but for now we will return in memory map.
    public Map<String, String> compute(FilePartition filePartition)  {
        Map<String, String> computedValue = new HashMap<>();
        try {
            long startPosition  =  filePartition.getStartIndex();
            long readTillPosition  = filePartition.getStopIndex();
            randomAccessFile.seek(startPosition);
            while(randomAccessFile.getFilePointer() < readTillPosition) {
                computedValue.put(String.valueOf(randomAccessFile.getFilePointer()), randomAccessFile.readLine());
            }
            return computedValue;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    };


}
