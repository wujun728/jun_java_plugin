package me.chanjar.kafka.stream.usage;

import kafka.server.KafkaConfig;
import me.chanjar.kafka.stream.model.MeterNumber;
import me.chanjar.kafka.stream.model.MeterUsage;
import me.chanjar.kafka.stream.testtool.AbstractKafkaStreamTest;
import me.chanjar.kafka.stream.util.SimpleStreamsConfigurationConfigurer;
import me.chanjar.kafka.stream.util.StreamsFactory;
import me.chanjar.kafka.stream.util.TopologyFactory;
import me.chanjar.kafka.testtool.IntegrationTestUtils;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsConfig;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.testng.Assert.assertEquals;

public class MeterUsageTest extends AbstractKafkaStreamTest {

  private static final String INPUT_TOPIC = "meter-number";
  private static final String OUTPUT_TOPIC = "meter-usage";

  @Override
  public String getInputTopicName() {
    return INPUT_TOPIC;
  }

  @Override
  public String getOutputTopicName() {
    return OUTPUT_TOPIC;
  }

  @Override
  public String getApplicationId() {
    return "meter-usage-test";
  }

  @Override
  protected String getConsumerGroupId() {
    return "meter-usage-test-consumer";
  }

  @Override
  protected Properties getBrokerConfig() {
    Properties brokerConfig = new Properties();
    brokerConfig.put(KafkaConfig.TransactionsTopicMinISRProp(), 1);
    brokerConfig.put(KafkaConfig.TransactionsTopicReplicationFactorProp(), (short) 1);
    return brokerConfig;
  }

  @Test
  public void test() throws ExecutionException, InterruptedException {

    TopologyFactory topologyFactory = new MeterUsageTopologyFactory(getSchemaRegistryUrl(),
        INPUT_TOPIC, OUTPUT_TOPIC);

    StreamsFactory streamsFactory = new MeterUsageStreamsFactory(topologyFactory);
    streamsFactory.addStreamsConfigurationConfigurer(new SimpleStreamsConfigurationConfigurer(
        getBasicStreamsConfiguration()));

    KafkaStreams streams = streamsFactory.build();

    streams.start();

    //
    // Step 2: Produce some input data to the input topic.
    //

    List<KeyValue<String, MeterNumber>> inputValues = Arrays.asList(
        new KeyValue<>("foo", meterNumber("foo", 100D, 100L)),
        new KeyValue<>("bar", meterNumber("bar", 100D, 100L)),
        new KeyValue<>("foo", meterNumber("foo", 200D, 200L)),
        new KeyValue<>("bar", meterNumber("bar", 150D, 200L)),
        new KeyValue<>("bar", meterNumber("bar", 210D, 300L)),
        new KeyValue<>("foo", meterNumber("foo", 200D, 300L))
    );

    Properties producerConfig = getBasicProducerConfiguration();
    IntegrationTestUtils.produceKeyValuesSynchronously(INPUT_TOPIC, inputValues, producerConfig);

    //
    // Step 3: Verify the application's output data.
    //

    List<KeyValue<String, MeterUsage>> expectedRecords = Arrays.asList(
        new KeyValue<>("foo", meterNumberDelta("foo", null, 100D, null, 100L)),
        new KeyValue<>("bar", meterNumberDelta("bar", null, 100D, null, 100L)),
        new KeyValue<>("foo", meterNumberDelta("foo", 100D, 200D, 100D, 200L)),
        new KeyValue<>("bar", meterNumberDelta("bar", 100D, 150D, 50D, 200L)),
        new KeyValue<>("bar", meterNumberDelta("bar", 150D, 210D, 60D, 300L)),
        new KeyValue<>("foo", meterNumberDelta("foo", 200D, 200D, 0D, 300L))
    );

    Properties consumerConfig = getBasicConsumerConfiguration();
    List<KeyValue<String, MeterUsage>> actualValues = IntegrationTestUtils
        .waitUntilMinKeyValueRecordsReceived(consumerConfig, OUTPUT_TOPIC, inputValues.size());
    streams.close();
    assertEquals(actualValues, expectedRecords);

  }

  private MeterNumber meterNumber(String meterId, Double number, long timestamp) {
    MeterNumber r = new MeterNumber();
    r.setMeterId(meterId);
    r.setNumber(number);
    r.setTimestamp(timestamp);
    return r;
  }

  private MeterUsage meterNumberDelta(String meterId, Double prevNumber, Double currNumber, Double delta,
      long timestamp) {
    MeterUsage r = new MeterUsage();
    r.setMeterId(meterId);
    r.setPrevNumber(prevNumber);
    r.setCurrNumber(currNumber);
    r.setDelta(delta);
    r.setTimestamp(timestamp);
    return r;
  }

}
