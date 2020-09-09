package me.chanjar.kafka.stream.testtool;

import io.confluent.common.utils.TestUtils;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import me.chanjar.kafka.testtool.EmbeddedSingleNodeKafkaCluster;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractKafkaStreamTest {

  private EmbeddedSingleNodeKafkaCluster cluster;
  private String schemaRegistryUrl;
  private String bootstrapServers;

  @BeforeMethod(alwaysRun = true)
  public void init() throws Exception {

    cluster = new EmbeddedSingleNodeKafkaCluster(getBrokerConfig());
    cluster.start();

    cluster.createTopic(getInputTopicName());
    cluster.createTopic(getOutputTopicName());

    schemaRegistryUrl = cluster.schemaRegistryUrl();
    bootstrapServers = cluster.bootstrapServers();

  }

  @AfterMethod(alwaysRun = true)
  public void tearDown() {
    if (cluster != null) {
      cluster.stop();
      cluster = null;
    }

  }

  protected final String getSchemaRegistryUrl() {
    return schemaRegistryUrl;
  }

  protected final String getBootstrapServers() {
    return bootstrapServers;
  }

  public abstract String getInputTopicName();

  public abstract String getOutputTopicName();

  public abstract String getApplicationId();

  protected abstract String getConsumerGroupId();

  protected Properties getBrokerConfig() {
    return new Properties();
  };

  protected Map<String, Object> getBasicStreamsConfiguration() {

    Map<String, Object> streamsConfiguration = new HashMap<>();
    streamsConfiguration.put(StreamsConfig.APPLICATION_ID_CONFIG, getApplicationId());
    streamsConfiguration.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
    streamsConfiguration.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.ByteArray().getClass().getName());
    streamsConfiguration.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
    streamsConfiguration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    // Use a temporary directory for storing state, which will be automatically removed after the test.
    streamsConfiguration.put(StreamsConfig.STATE_DIR_CONFIG, TestUtils.tempDirectory().getAbsolutePath());

    return streamsConfiguration;

  }

  public Properties getBasicProducerConfiguration() {

    Properties producerConfig = new Properties();
    producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
    producerConfig.put(ProducerConfig.ACKS_CONFIG, "all");
    producerConfig.put(ProducerConfig.RETRIES_CONFIG, 0);
    producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
    producerConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);

    return producerConfig;

  }

  public Properties getBasicConsumerConfiguration() {

    Properties consumerConfig = new Properties();
    consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
    consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, getConsumerGroupId());
    consumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class);
    consumerConfig.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaRegistryUrl);
    consumerConfig.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

    return consumerConfig;
  }
}
