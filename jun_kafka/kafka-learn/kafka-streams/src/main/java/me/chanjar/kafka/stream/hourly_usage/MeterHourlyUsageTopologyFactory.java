package me.chanjar.kafka.stream.hourly_usage;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import me.chanjar.kafka.stream.model.MeterHourlyUsage;
import me.chanjar.kafka.stream.model.MeterUsage;
import me.chanjar.kafka.stream.util.AvroTopologyFactory;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.util.Collections.singletonMap;

public class MeterHourlyUsageTopologyFactory extends AvroTopologyFactory {

  private final String inputTopic;
  private final String outputTopic;

  public MeterHourlyUsageTopologyFactory(String schemaRegistryUrl, String inputTopic, String outputTopic) {
    super(schemaRegistryUrl);
    this.inputTopic = inputTopic;
    this.outputTopic = outputTopic;
  }

  @Override
  public Topology build(StreamsBuilder builder) {

    Map<String, String> serdeConfig = singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
        getSchemaRegistryUrl());

    final Serde<MeterUsage> meterNumberDeltaAvroSerde = new SpecificAvroSerde<>();
    meterNumberDeltaAvroSerde.configure(serdeConfig, false);

    final Serde<MeterHourlyUsage> meterHourlyUsageAvroSerde = new SpecificAvroSerde<>();
    meterHourlyUsageAvroSerde.configure(serdeConfig, false);

    // Read the input data.  (In this example we ignore whatever is stored in the record keys.)
    KStream<String, MeterUsage> stream = builder
        .stream(inputTopic, Consumed.with(Serdes.String(), meterNumberDeltaAvroSerde));

    KStream<String, MeterHourlyUsage> meterHourlyUsageStream = stream
        .filter((key, value) -> value.getDelta() != null)
        .groupByKey(Serialized.with(Serdes.String(), meterNumberDeltaAvroSerde))
        .windowedBy(TimeWindows.of(TimeUnit.HOURS.toMillis(1)))
        .aggregate(
            () -> {
              MeterHourlyUsage r = new MeterHourlyUsage();
              r.setUsage(0D);
              return r;
            },
            (aggKey, value, aggValue) -> {

              if (aggValue.getMeterId() == null) {
                aggValue.setMeterId(value.getMeterId());
              }
              if (aggValue.getPrevNumber() == null) {
                aggValue.setPrevNumber(value.getPrevNumber());
              }
              aggValue.setCurrNumber(value.getCurrNumber());
              aggValue.setUsage(aggValue.getUsage() + value.getDelta());
              return aggValue;
            },
            Materialized
                .<String, MeterHourlyUsage, WindowStore<Bytes, byte[]>>as("hourly-usage-window")
                .withKeySerde(Serdes.String())
                .withValueSerde(meterHourlyUsageAvroSerde)
        )
        .toStream()
        .map((windowKey, value) -> {
          String meterId = windowKey.key();
          Window window = windowKey.window();
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
          String bucket = sdf.format(new Date(window.start()));
          value.setBucket(bucket);
          return new KeyValue<>(meterId, value);
        });

    meterHourlyUsageStream.to(outputTopic, Produced.with(Serdes.String(), meterHourlyUsageAvroSerde));

    Topology topology = builder.build();
    return topology;
  }

}
