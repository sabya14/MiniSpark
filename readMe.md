### Objective
 1. Create a map reduce system which can consume a csv file, and can run operation on it in a parallel
 way.
 2. At the end we should have a good understand on how MapReduce/Spark can of systems work.
 
### Components
 * #### FileReader  
  1. Read a csv file, and infer schema from it.
  2. Store the csv in parts, if possible in a compressed way.
  3. Try to implement schema on read.
  
  Steps
  1. Try to read csv and make rdd.
  2. Spark does this -> textFile -> returns RDD[String]
  3. When we use textFile in spark, it create at HadoopRDD, which basically stores the HDFS 
  
 Persistance -> Spark reads and create partitions, if it has to cached or persisted, it passed the
 info to BlockManager, which tries to store it in memory block, if its not possible then its stores it in 
 disk block. Before getting the data from a partition, its checked whether its cached/persisted or not,
 if so it's then fetch from the block manager.
