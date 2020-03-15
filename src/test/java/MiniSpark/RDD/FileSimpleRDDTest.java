package MiniSpark.RDD;

import MiniSpark.MiniSparkContext;
import MiniSpark.Partitions.FilePartition;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileSimpleRDDTest {


    @AfterAll
    static void deleteTestFile() {
        try {
            Files.deleteIfExists(Paths.get("testfile.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void shouldReturnCorrectListOfPartitions() throws IOException {
        List<String> lines = Arrays.asList("1st line", "2nd line", "3rd line", "4th line");

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
        List<FilePartition> filePartitions = rdd.getPartitions();
        assertEquals(new FilePartition(0, 0, 9).toString(), filePartitions.get(0).toString());
        assertEquals(new FilePartition(1, 9, 18).toString(), filePartitions.get(1).toString());
        assertEquals(new FilePartition(2, 18, 27).toString(), filePartitions.get(2).toString());
        assertEquals(new FilePartition(3, 27, 36).toString(), filePartitions.get(3).toString());
    }

    @Test
    void shouldReturnCorrectComputedValueOfPartition() throws IOException {
        List<String> lines = Arrays.asList("1st line", "2nd line", "3rd line", "4th line");

        Files.write(Paths.get("testfile.txt"),
                lines,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);

        MiniSparkContext miniSparkContext = mock(MiniSparkContext.class);
        when(miniSparkContext.getParallelism()).thenReturn(4);
        RandomAccessFile randomAccessFile = new RandomAccessFile("testfile.txt", "r");
        FileSimpleRDD rdd = new FileSimpleRDD(miniSparkContext, randomAccessFile);
        assertEquals(rdd.compute(new FilePartition(3, 18,27)),  Collections.singletonMap("18", "3rd line"));
        assertEquals(rdd.compute(new FilePartition(2, 9,18)),  Collections.singletonMap("9", "2nd line"));
    }
}