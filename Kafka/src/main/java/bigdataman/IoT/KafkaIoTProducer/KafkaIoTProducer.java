package bigdataman.IoT.KafkaIoTProducer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;



public class KafkaIoTProducer {

	private static String KafkaBrokerEndpoint = "localhost:9092";
    private static String KafkaTopic = "test1";
    private static String CsvFile = "fout.csv";
    
  
    private Producer<String, String> ProducerProperties(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaBrokerEndpoint);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "KafkaCsvProducer");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //<Chiave, Valore>
        return new KafkaProducer<String, String>(properties);
    }

    public static void main(String[] args) throws URISyntaxException {
        
        KafkaIoTProducer kafkaProducer = new KafkaIoTProducer();
        kafkaProducer.PublishMessages();
        System.out.println("Producing job completed");
    }

    
    
    private void PublishMessages() throws URISyntaxException{
    	
        final Producer<String, String> csvProducer = ProducerProperties();
        
        try{
        	//ricavo il path
        	URI uri = getClass().getClassLoader().getResource(CsvFile).toURI();
        	//leggo il file CSV
            Stream<String> FileStream = Files.lines(Paths.get(uri));
            
            FileStream.forEach(line -> {
            	//System.out.println(line);
            	//prende come argomenti topic - chiave - valore 
                final ProducerRecord<String, String> csvRecord = new ProducerRecord<String, String>(
                        KafkaTopic, UUID.randomUUID().toString(), line);
              
                csvProducer.send(csvRecord, (metadata, exception) -> {
                    if(metadata != null){
                    	
                    	try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	//se si verifica un errore metadata = null.
                        //System.out.println("CsvData: -> "+ csvRecord.key()+" | "+ csvRecord.value());
                    }
                    else{
                        System.out.println("Error Sending Csv Record -> "+ csvRecord.value());
                    }
                });
            });
            FileStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
