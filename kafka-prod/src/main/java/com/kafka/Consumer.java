package com.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer {
    public Consumer(){
        Properties properties = null;
        try {
            properties = loadConfig("./kafka-prod/src/main/resources/kafka.config");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "demo-consumer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        KafkaConsumer consumer = new KafkaConsumer<String, String>(properties);
        consumer.subscribe(Arrays.asList("topic_1"));

        try {
            while (true) {
              ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
              for (ConsumerRecord<String, String> record : records) {
                String key = record.key();
                String value = record.value();
                System.out.printf("Consumed record with key %s and value %s \n", key, value);
              }
            }
          } finally {
            consumer.close();
          }
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
