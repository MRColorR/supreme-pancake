package bigdataman.IoT.KafkaIoTProducer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.time.LocalTime;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;



public class KafkaIoTProducer {

	private static String KafkaBrokerEndpoint = "localhost:9092";
    private static String KafkaTopic = "test1";
    private static String CsvFile = "/fout.csv";
    
  
    private Producer<String, String> ProducerProperties(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaBrokerEndpoint);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaCsvProducer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //<Chiave, Valore>
        return new KafkaProducer<String, String>(properties);
    }

    public static void main(String[] args) throws IOException,URISyntaxException {
        
    	Configuration conf = new Configuration();
    	Path path = new Path("hdfs://mattia:9000/data/fout.csv");
    	FileSystem fs = path.getFileSystem(conf);
    	FSDataInputStream inputStream = fs.open(path);
    	BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    	
        KafkaIoTProducer kafkaProducer = new KafkaIoTProducer();
        kafkaProducer.PublishMessages(reader);
        reader.close();
        inputStream.close();
        System.out.println("Producing job completed");
    }

    private LocalTime randomTime() {
    	Random random = new Random();
    	LocalTime timestamp = LocalTime.of(
    			random.nextInt(24),
    			random.nextInt(60),
    			random.nextInt(60)
    	);
    	return  timestamp;
    }
    
    private void PublishMessages(BufferedReader inputStream) throws URISyntaxException{
    	
    	
    	
        final Producer<String, String> csvProducer = ProducerProperties();
        
        try{
        	//ricavo il path
        	//InputStream in = getClass().getResourceAsStream(CsvFile);
        	//BufferedReader input = new BufferedReader(new InputStreamReader(in));
        	//leggo il file CSV          
            
            String line; //riga che leggo da file del dataset fout
            while ( (line = inputStream.readLine()) != null) {
            	//System.out.println(line);
            	String data = String.format("%s,%s", line, randomTime().toString());

            	
            	try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
            	
            	//prende come argomenti topic - chiave - valore 
                final ProducerRecord<String, String> csvRecord = new ProducerRecord<String, String>(
                        KafkaTopic, UUID.randomUUID().toString(), data);
              
                csvProducer.send(csvRecord, (metadata, exception) -> {
                    if(metadata != null){

                    	//se si verifica un errore metadata = null.
                        //System.out.println("CsvData: -> "+ csvRecord.key()+" | "+ csvRecord.value());
                    }
                    else{
                        System.out.println("Error Sending Csv Record -> "+ csvRecord.value());
                    }
                });  
            }
            
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

