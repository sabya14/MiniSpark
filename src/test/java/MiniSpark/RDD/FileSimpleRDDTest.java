package MiniSpark.RDD;

import MiniSpark.MiniSparkContext;
import MiniSpark.Partitions.FilePartition;
import MiniSpark.Partitions.Partition;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileSimpleRDDTest {



    @Test
    void shouldReturnCorrectListOfPartitions() throws IOException {
        List<String> lines = Arrays.asList("1st line", "2nd line", "3st line", "4nd line");

        Files.write(Paths.get("testfile.txt"),
                lines,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);

        MiniSparkContext miniSparkContext = mock(MiniSparkContext.class);
        when(miniSparkContext.getParallelism()).thenReturn(4);
        RandomAccessFile randomAccessFile = new RandomAccessFile("testfile.txt", "r");
        FileSimpleRDD rdd = new FileSimpleRDD(miniSparkContext, randomAccessFile);

        // TODO Better the test using assertIterableEquals
        List<Partition> filePartitions = rdd.getPartitions();
        assertEquals(new FilePartition(0, new Pair<>(1, 8)).toString(), filePartitions.get(0).toString());
        assertEquals(new FilePartition(1, new Pair<>(10, 17)).toString(), filePartitions.get(1).toString());
        assertEquals(new FilePartition(2, new Pair<>(19, 26)).toString(), filePartitions.get(2).toString());
        assertEquals(new FilePartition(3, new Pair<>(28, 36)).toString(), filePartitions.get(3).toString());

    }
}