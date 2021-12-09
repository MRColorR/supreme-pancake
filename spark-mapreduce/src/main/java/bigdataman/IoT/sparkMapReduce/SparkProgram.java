package bigdataman.IoT.sparkMapReduce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.fs.shell.Count;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import com.datastax.spark.connector.CassandraRow;
import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.datastax.spark.connector.japi.rdd.CassandraJavaRDD;

import model.AvgByCity;
import model.AvgByYear;
import model.AvgTempByRegionYear;
import model.Temperatura;
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
        JavaSparkContext sc = new JavaSparkContext(conf);
        
        JavaRDD<Temperatura> cassandraRDD = CassandraJavaUtil.javaFunctions(sc)
                .cassandraTable("iot", "temp", CassandraJavaUtil.mapRowTo(Temperatura.class));
        
        averageTempByYear(cassandraRDD, sc);
        averageTempByYearAndRegion(cassandraRDD, sc);
        averageTempByCity(cassandraRDD, sc);

        
             
    }
    
    public static void averageTempByYearAndRegion (JavaRDD<Temperatura> cassandraRDD, JavaSparkContext sc) {
    	//funzione map da cui ho coppie chiave valore del tipo RegionYear-AvgTemp
    	JavaPairRDD<String, Float> pairsYearTemp = cassandraRDD.mapToPair( row -> {
        	String timestamp = row.getTimestamp();
        	String year = timestamp.split(" ")[0].split("-")[0];
        	String key = row.getRegion() +"-"+ year;
        	return new Tuple2<String, Float>(key,row.getAvg_temperature());
        });
    
    	//conto quante coppie hanno la stessa chiave per poi calocare la media
    	Map<String, Long> countRegionYearTemp = pairsYearTemp.countByKey();
    	//funzione reduce in cui sommo le temperature con la stessa chiave
    	JavaPairRDD<String, Float> reduceRegionYearTemp = pairsYearTemp.reduceByKey((a,b) -> a+b);
    	//vado a calcolare la media
    	Map<String, Float> RegionYearTempMap = reduceRegionYearTemp.collectAsMap();
    	List<AvgTempByRegionYear> allrecords = new ArrayList<AvgTempByRegionYear>();
    	
        for (Object key : RegionYearTempMap.keySet()) {
        	String strKey = (String) key;
    	    String[] keySplitted = strKey.split("-");
        	String region =  keySplitted[0];
        	String year = keySplitted[1];
        	
        	float sum = RegionYearTempMap.get(key);
        	float div = (float) countRegionYearTemp.get(key);
        	
        	AvgTempByRegionYear record = new AvgTempByRegionYear(year, region, sum/div);
        	allrecords.add(record);     	
    	}
        
        JavaRDD<AvgTempByRegionYear> rdd = sc.parallelize(allrecords);
		CassandraJavaUtil.javaFunctions(rdd)
				.writerBuilder("iot", "avgtempbyregionyear", CassandraJavaUtil.mapToRow(AvgTempByRegionYear.class))
				.saveToCassandra();
		System.out.println("dati caricati nel db");
    	
    	
    	
    	
    }
    
    public static void averageTempByCity( JavaRDD<Temperatura> cassandraRDD, JavaSparkContext sc ) {
    	//funzione map da cui ho coppie chiave valore del tipo city-AvgTemp
    	JavaPairRDD<String, Float> pairsCityTemp = cassandraRDD.mapToPair( row -> {
        	return new Tuple2<String, Float>(row.getCity(),row.getAvg_temperature());
        });
    	//conto quante coppie hanno la stessa chiave per poi calocare la media
    	Map<String, Long> countCityTemp = pairsCityTemp.countByKey();
    	//funzione reduce in cui sommo le temperature con la stessa chiave
    	JavaPairRDD<String, Float> reduceYearTemp = pairsCityTemp.reduceByKey((a,b) -> a+b);
    	//vado a calcolare la media
    	Map<String, Float> YearCityMap = reduceYearTemp.collectAsMap();
    	List<AvgByCity> allrecords = new ArrayList<AvgByCity>();
    	
        for (Object key : YearCityMap.keySet()) {
    	    
        	float sum = YearCityMap.get(key);
        	float div = (float) countCityTemp.get(key);
        	
        	AvgByCity record = new AvgByCity((String) key, sum/div);
        	allrecords.add(record);   	
    	}
        
        JavaRDD<AvgByCity> rdd = sc.parallelize(allrecords);
		CassandraJavaUtil.javaFunctions(rdd)
				.writerBuilder("iot", "avgtempbycity", CassandraJavaUtil.mapToRow(AvgByCity.class))
				.saveToCassandra();
		System.out.println("dati caricati nel db");
    	
    }
    
    
    public static void averageTempByYear( JavaRDD<Temperatura> cassandraRDD, JavaSparkContext sc) {
    	//funzione map da cui ho coppie chiave valore del tipo Year-AvgTemp
    	JavaPairRDD<String, Float> pairsYearTemp = cassandraRDD.mapToPair( row -> {
        	String timestamp = row.getTimestamp();
        	String year = timestamp.split(" ")[0].split("-")[0];
        	return new Tuple2<String, Float>(year,row.getAvg_temperature());
        });
    	//conto quante coppie hanno la stessa chiave per poi calocare la media
    	Map<String, Long> countYearTemp = pairsYearTemp.countByKey();
    	//funzione reduce in cui sommo le temperature con la stessa chiave
    	JavaPairRDD<String, Float> reduceYearTemp = pairsYearTemp.reduceByKey((a,b) -> a+b);
    	//vado a calcolare la media
    	Map<String, Float> YearTempMap = reduceYearTemp.collectAsMap();
    	List<AvgByYear> allrecords = new ArrayList<AvgByYear>();
    	
        for (Object key : YearTempMap.keySet()) {
    	    
        	float sum = YearTempMap.get(key);
        	float div = (float) countYearTemp.get(key);
        	
        	AvgByYear record = new AvgByYear((String) key, sum/div);
        	allrecords.add(record);   	
    	}
        
        JavaRDD<AvgByYear> rdd = sc.parallelize(allrecords);
		CassandraJavaUtil.javaFunctions(rdd)
				.writerBuilder("iot", "avgtempbyyear", CassandraJavaUtil.mapToRow(AvgByYear.class))
				.saveToCassandra();
		System.out.println("dati caricati nel db");
        
    	
    }
    
    
}
