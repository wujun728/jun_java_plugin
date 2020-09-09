package me.chanjar.kafka.stream.daily_usage;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import me.chanjar.kafka.stream.model.MeterDailyUsage;
import me.chanjar.kafka.stream.model.MeterUsage;
import me.chanjar.kafka.stream.util.AvroTopologyFactory;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.Serialized;
import org.apache.kafka.streams.state.KeyValueStore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static java.util.Collections.singletonMap;

public class MeterDailyUsageTopologyFactory extends AvroTopologyFactory {

  private final String inputTopic;
  private final String outputTopic;

  public MeterDailyUsageTopologyFactory(String schemaRegistryUrl, String inputTopic, String outputTopic) {
    super(schemaRegistryUrl);
    this.inputTopic = inputTopic;
    this.outputTopic = outputTopic;
  }

  @Override
  public Topology build(StreamsBuilder builder) {

    Map<String, String> serdeConfig = singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG,
        getSchemaRegistryUrl());

    final Serde<MeterUsage> meterUsageAvroSerde = new SpecificAvroSerde<>();
    meterUsageAvroSerde.configure(serdeConfig, false);

    final Serde<MeterDailyUsage> meterHourlyUsageAvroSerde = new SpecificAvroSerde<>();
    meterHourlyUsageAvroSerde.configure(serdeConfig, false);

    // Read the input data.  (In this example we ignore whatever is stored in the record keys.)
    KStream<String, MeterUsage> stream = builder
        .stream(inputTopic, Consumed.with(Serdes.String(), meterUsageAvroSerde));

    KStream<String, MeterDailyUsage> meterHourlyUsageStream = stream
        .filter((key, value) -> value.getDelta() != null)
        .groupBy((key, value) -> {
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          String hour = sdf.format(new Date(value.getTimestamp()));
          return hour + "-" + value.getMeterId();
        }, Serialized.with(Serdes.String(), meterUsageAvroSerde))
        .aggregate(() -> {
          MeterDailyUsage r = new MeterDailyUsage();
          r.setUsage(0D);
          return r;
        }, (key, newValue, aggValue) -> {
          if (aggValue.getBucket() == null) {
            aggValue.setBucket(key.substring(0, key.lastIndexOf('-')));
          }
          if (aggValue.getMeterId() == null) {
            aggValue.setMeterId(newValue.getMeterId());
          }
          if (aggValue.getPrevNumber() == null) {
            aggValue.setPrevNumber(newValue.getPrevNumber());
          }
          aggValue.setCurrNumber(newValue.getCurrNumber());
          aggValue.setUsage(aggValue.getUsage() + newValue.getDelta());
          return aggValue;
        }, Materialized
            .<String, MeterDailyUsage, KeyValueStore<Bytes, byte[]>>as("daily-usage")
            .withKeySerde(Serdes.String())
            .withValueSerde(meterHourlyUsageAvroSerde)
        )
        .toStream()
    ;

    meterHourlyUsageStream.to(outputTopic, Produced.with(Serdes.String(), meterHourlyUsageAvroSerde));

    Topology topology = builder.build();
    return topology;
  }

}
