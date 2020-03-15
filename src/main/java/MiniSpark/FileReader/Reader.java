package MiniSpark.FileReader;

import MiniSpark.RDD.SimpleRDD;

public abstract class Reader {
    public abstract SimpleRDD read(String filepath);
    public abstract void delete(SimpleRDD simpleRDD);
}
