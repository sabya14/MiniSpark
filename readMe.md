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
 
 #### ReduceByKey
 reduceByKey is an specialization of aggregateByKey aggregateByKey takes 2 functions:
 one that is applied to each partition (sequentially) and one that is applied among 
 the results of each partition (in parallel). reduceByKey uses the same associative 
 function on both cases: to do a sequential computing on each partition and then
 combine those results in a final result as we have illustrated here.
 
 reduceByKey() is quite similar to reduce(); both take a function and use it to 
 combine values. reduceByKey() runs several parallel reduce operations, 
 one for each key in the dataset, where each operation combines values 
 that have the same key. Because datasets can have very large numbers of keys, 
 reduceByKey() is not implemented as an action that returns a value to the user 
 program. Instead, it returns a new RDD consisting of each key and the reduced 
 value for that key. Thats we need to shuffle this data into partitions with sames keys, 
 and run the same aggregator function over them.
 


 #### Why is sorting needed in MapReduce before reducing?
Sort phase in MapReduce covers merging and sorting of map outputs. Map generates 
intermediate key-value pairs.All these intermediate key-value pairs are sorted by key.
Each reduce task takes list of key-value pairs as input,but Sorting at mapper saves 
time for the reducer helping it to easily distinguish when a new reduce task should 
start.It simple starts a new reduce task,when next key in sorted input data is 
different than previous. If we want the sorted values then we can use the 
secondary sorting technique at the reducer.