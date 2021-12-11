package bigdataman.IoT.SparkIoTProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.datastax.spark.connector.japi.CassandraJavaUtil;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;


import kafka.serializer.StringDecoder;

public class SparkIoTProcessor {

	public static void main(String[] args) throws IOException, InterruptedException {

		System.out.println("Spark Streaming started now .....");

		SparkConf conf = new SparkConf().setAppName("kafka-sandbox")
				.setMaster("local[*]")
				.set("spark.cassandra.connection.host", "mattia");
		JavaSparkContext sc = new JavaSparkContext(conf);

		// batchDuration - The time interval at which streaming data will be divided
		// into batches
		JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(5000));

		// kafka setup
		Map<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list", "127.0.0.1:9092");
		Set<String> topics = Collections.singleton("temperature");
		JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc, String.class,
				String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);

		directKafkaStream.foreachRDD(rdd -> {

			System.out.println(
					"New data arrived  " + rdd.partitions().size() + " Partitions and " + rdd.count() + " Records");
			if (rdd.count() > 0) {
				List<Temperatura> allRecord = new ArrayList<Temperatura>();
				rdd.collect().forEach(rawRecord -> {
					
					String row = rawRecord._2;
					StringTokenizer sToken = new StringTokenizer(row, ",");
					
					if (sToken.countTokens() == 8) {
						String region = sToken.nextToken();
						String country = sToken.nextToken();
						String city = sToken.nextToken();
						String month = sToken.nextToken();
						String day = sToken.nextToken();
						String year = sToken.nextToken();
						float avg = Float.parseFloat(sToken.nextToken());
						String time = sToken.nextToken();
						
						String timestamp = createTimeStamp(year, month, day, time);
						
						Temperatura newTemp = new Temperatura(region, country, city, timestamp,avg);
						allRecord.add(newTemp);
					} else {
						System.out.println("Formato dati errato");
					}
								

					

				});
				System.out.println("All records OUTER MOST :" + allRecord.size());
				JavaRDD<Temperatura> rdd2 = sc.parallelize(allRecord);
				CassandraJavaUtil.javaFunctions(rdd2)
						.writerBuilder("iot", "temperature", CassandraJavaUtil.mapToRow(Temperatura.class))
						.saveToCassandra();
				System.out.println("dati caricati nel db");
			}
		});

		ssc.start();
		ssc.awaitTermination();
	}

	private static String createTimeStamp(String year, String month, String day, String time) {
		
		month = (month.length() == 1) ? "0"+month : month;
		day = (day.length() == 1) ? "0"+day : day;
	
		return String.format("%s-%s-%s %sZ", year, month, day, time);
	}
	
}

