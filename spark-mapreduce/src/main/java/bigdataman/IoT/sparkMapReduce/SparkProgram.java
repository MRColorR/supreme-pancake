package bigdataman.IoT.sparkMapReduce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import com.datastax.spark.connector.CassandraRow;
import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.datastax.spark.connector.japi.rdd.CassandraJavaRDD;

import scala.Tuple2;

/**
 * Hello world!
 *
 */
public class SparkProgram 
{
    public static void main( String[] args )
    {
        SparkConf conf = new SparkConf(true)
        		   .setAppName("Spark IoT MapReduce")
        		   .set("spark.cassandra.connection.host", "127.0.0.1")
        		   .setMaster("local[*]");
        SparkContext sc = new SparkContext(conf); 
        
        JavaRDD<Temperatura> cassandraRdd = CassandraJavaUtil.javaFunctions(sc)
                .cassandraTable("iot", "temp", CassandraJavaUtil.mapRowTo(Temperatura.class));
        JavaPairRDD<String, Float> pairs = cassandraRdd.mapToPair( row -> {
        	return new Tuple2<String, Float>(row.getRegion(),row.getAvg_temperature());
        });
        JavaPairRDD<String, Float> count = pairs.reduceByKey((a,b) -> a+b);
        System.out.println(count.first());
        
        /*
        JavaRDD<String> cassandraRowsRDD = CassandraJavaUtil.javaFunctions(sc).cassandraTable("ks", "people")
        		.map(new Function<CassandraRow, String>() {
        			public String call(CassandraRow v1) throws Exception {
						// TODO Auto-generated method stub
						return null;
				});*/
        /*		
        CassandraJavaRDD myData = CassandraJavaUtil.javaFunctions(sc).cassandraTable("iot", "temp");
        System.out.println(myData.first()); */
        		
        		/*
                .map(new Function<CassandraRow, String>() {
                	public String call(CassandraRow row) throws Exception {
						// TODO Auto-generated method stub
						return row.toString();;
					}
				
                }); */
        	

        
        
        
        
    }
}
