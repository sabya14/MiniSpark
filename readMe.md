### Objective
    1.Create a mini implementation of spark.
    2.Focus on how spark's RDD's work.
    3.Understand how partitions are created and computed
    4.Understand how map, filter, aggregate by  key is performed.
    5. Look into caching and distribution later
  
 ### Learning
 
 #### RDD
 Its an abstract class, who compute and getPartitions have to implemented by subclasses.
 Has all code to look after map, filter and reduce functions on rdd.
  
  
 ####Persistance 
 Spark reads and create partitions, if it has to cached or persisted, it passed the
 info to BlockManager, which tries to store it in memory block, if its not possible then its stores it in 
 disk block. Before getting the data from a partition, its checked whether its cached/persisted or not,
 if so it's then fetch from the block manager.
 
 
