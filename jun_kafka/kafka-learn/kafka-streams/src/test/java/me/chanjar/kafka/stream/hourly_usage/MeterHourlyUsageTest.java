package me.chanjar.kafka.stream.hourly_usage;

import kafka.server.KafkaConfig;
import me.chanjar.kafka.stream.model.MeterHourlyUsage;
import me.chanjar.kafka.stream.model.MeterUsage;
import me.chanjar.kafka.stream.testtool.AbstractKafkaStreamTest;
import me.chanjar.kafka.stream.util.SimpleStreamsConfigurationConfigurer;
import me.chanjar.kafka.stream.util.StreamsFactory;
import me.chanjar.kafka.stream.util.TopologyFactory;
import me.chanjar.kafka.testtool.IntegrationTestUtils;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import static org.testng.Assert.assertEquals;

public class MeterHourlyUsageTest extends AbstractKafkaStreamTest {

  private static final String INPUT_TOPIC = "meter-number-delta";
  private static final String OUTPUT_TOPIC = "meter-hourly-usage";

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
    return "meter-hourly-usage-test";
  }

  @Override
  protected String getConsumerGroupId() {
    return "meter-hourly-usage-test-consumer";
  }

  @Override
  protected Properties getBrokerConfig() {
    Properties brokerConfig = new Properties();
    brokerConfig.put(KafkaConfig.TransactionsTopicMinISRProp(), 1);
    brokerConfig.put(KafkaConfig.TransactionsTopicReplicationFactorProp(), (short) 1);
    return brokerConfig;
  }

  @Test
  public void test() throws ExecutionException, InterruptedException, ParseException {

    //    TopologyFactory topologyFactory = new MeterHourlyUsageTopologyFactory(
    //        getSchemaRegistryUrl(), INPUT_TOPIC, OUTPUT_TOPIC);

    TopologyFactory topologyFactory = new MeterHourlyUsageTopologyFactory2(
        getSchemaRegistryUrl(), INPUT_TOPIC, OUTPUT_TOPIC);

    StreamsFactory streamsFactory = new MeterHourlyUsageStreamsFactory(topologyFactory);

    streamsFactory.addStreamsConfigurationConfigurer(new SimpleStreamsConfigurationConfigurer(
        getBasicStreamsConfiguration()));

    KafkaStreams streams = streamsFactory.build();

    streams.start();

    //
    // Step 2: Produce some input data to the input topic.
    //

    List<KeyValue<String, MeterUsage>> inputValues = Arrays.asList(
        new KeyValue<>("foo", meterNumberDelta("foo", null, 50D, null, "2018-01-01 09:01:15")),
        new KeyValue<>("bar", meterNumberDelta("bar", null, 40D, null, "2018-01-01 09:01:30")),
        new KeyValue<>("foo", meterNumberDelta("foo", 50D, 100D, 50D, "2018-01-01 09:15:15")),
        new KeyValue<>("bar", meterNumberDelta("bar", 40D, 100D, 60D, "2018-01-01 09:15:30")),
        new KeyValue<>("foo", meterNumberDelta("foo", 100D, 200D, 100D, "2018-01-01 09:55:15")),
        new KeyValue<>("bar", meterNumberDelta("bar", 100D, 150D, 50D, "2018-01-01 09:55:30")),
        new KeyValue<>("foo", meterNumberDelta("foo", 200D, 200D, 0D, "2018-01-01 10:01:30")),
        new KeyValue<>("bar", meterNumberDelta("bar", 150D, 210D, 60D, "2018-01-01 10:01:15"))
    );

    Properties producerConfig = getBasicProducerConfiguration();
    IntegrationTestUtils.produceKeyValuesSynchronously(INPUT_TOPIC, inputValues, producerConfig);

    //
    // Step 3: Verify the application's output data.
    //

    List<KeyValue<String, MeterHourlyUsage>> expectedRecords = Arrays.asList(
        new KeyValue<>("2018-01-01 09-foo", meterHourlyUsage("foo", 50D, 200D, 150D, "2018-01-01 09")),
        new KeyValue<>("2018-01-01 09-bar", meterHourlyUsage("bar", 40D, 150D, 110D, "2018-01-01 09")),
        new KeyValue<>("2018-01-01 10-foo", meterHourlyUsage("foo", 200D, 200D, 0D, "2018-01-01 10")),
        new KeyValue<>("2018-01-01 10-bar", meterHourlyUsage("bar", 150D, 210D, 60D, "2018-01-01 10"))
    );

    Properties consumerConfig = getBasicConsumerConfiguration();
    List<KeyValue<String, MeterHourlyUsage>> actualValues = IntegrationTestUtils
        .waitUntilMinKeyValueRecordsReceived(consumerConfig, OUTPUT_TOPIC, 2);

    for (KeyValue<String, MeterHourlyUsage> actualValue : actualValues) {
      System.out.println(actualValue.key);
      System.out.println(actualValue.value);
    }
    streams.close();
    assertEquals(actualValues, expectedRecords);

  }

  private MeterUsage meterNumberDelta(String meterId, Double prevNumber, Double currNumber, Double delta,
      String dateString) throws ParseException {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = simpleDateFormat.parse(dateString);
    MeterUsage r = new MeterUsage();
    r.setMeterId(meterId);
    r.setPrevNumber(prevNumber);
    r.setCurrNumber(currNumber);
    r.setDelta(delta);
    r.setTimestamp(date.getTime());
    return r;
  }

  private MeterHourlyUsage meterHourlyUsage(String meterId, Double prevNumber, Double currNumber, Double usage,
      String bucket) {

    MeterHourlyUsage meterHourlyUsage = new MeterHourlyUsage();
    meterHourlyUsage.setMeterId(meterId);
    meterHourlyUsage.setBucket(bucket);
    meterHourlyUsage.setUsage(usage);
    meterHourlyUsage.setPrevNumber(prevNumber);
    meterHourlyUsage.setCurrNumber(currNumber);
    return meterHourlyUsage;
  }
}

