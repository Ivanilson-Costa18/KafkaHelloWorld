package com.kafka;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputFilter.Config;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.Serdes.StringSerde;

public class Producer {
    public Producer(){
        Properties properties = null;
        try {
            properties = loadConfig("./kafka-prod/src/main/resources/kafka.config");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties, new StringSerializer(), new StringSerializer());

        for(int i = 101; i < 200; i++){
          ProducerRecord<String, String> record = new ProducerRecord<String, String>("topic_1", 0, "T"+i, "test"+i);
          producer.send(record);
          System.out.println(record.toString());
        }

        producer.close();
    }

    public static Properties loadConfig(final String configFile) throws IOException {
        if (!Files.exists(Paths.get(configFile))) {
          throw new IOException(configFile + " not found.");
        }
        final Properties cfg = new Properties();
        try (InputStream inputStream = new FileInputStream(configFile)) {
          cfg.load(inputStream);
        }
        return cfg;
      }
}
