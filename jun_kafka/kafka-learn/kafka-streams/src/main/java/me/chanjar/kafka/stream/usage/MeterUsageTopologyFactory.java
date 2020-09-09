package me.chanjar.kafka.stream.usage;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
import me.chanjar.kafka.stream.util.AvroTopologyFactory;
import me.chanjar.kafka.stream.model.MeterNumber;
import me.chanjar.kafka.stream.model.MeterUsage;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.Consumed;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;

import static java.util.Collections.singletonMap;

public class MeterUsageTopologyFactory extends AvroTopologyFactory {

  private final String inputTopic;

  private final String outputTopic;

  public MeterUsageTopologyFactory(String schemaRegistryUrl, String inputTopic, String outputTopic) {
    super(schemaRegistryUrl);
    this.inputTopic = inputTopic;
    this.outputTopic = outputTopic;
  }

  @Override
  public Topology build(StreamsBuilder builder) {

    // Create a state store manually.
    StoreBuilder<KeyValueStore<String, Double>> meterNumberStore = Stores.keyValueStoreBuilder(
        Stores.persistentKeyValueStore("meter-number-store"),
        Serdes.String(),
        Serdes.Double())
        .withCachingEnabled();

    // Important (1 of 2): You must add the state store to the topology, otherwise your application
    // will fail at run-time (because the state store is referred to in `transform()` below.
    builder.addStateStore(meterNumberStore);

    final Serde<MeterNumber> meterNumberAvroSerde = new SpecificAvroSerde<>();

    meterNumberAvroSerde.configure(
        singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, getSchemaRegistryUrl()),
        false);

    final Serde<MeterUsage> meterNumberDeltaAvroSerde = new SpecificAvroSerde<>();

    meterNumberDeltaAvroSerde.configure(
        singletonMap(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, getSchemaRegistryUrl()),
        false);

    // Read the input data.  (In this example we ignore whatever is stored in the record keys.)
    KStream<String, MeterNumber> stream = builder
        .stream(inputTopic, Consumed.with(Serdes.String(), meterNumberAvroSerde));

    // Important (2 of 2):  When we call `transform()` we must provide the name of the state store
    // that is going to be used by the `Transformer` returned by `WordCountTransformerSupplier` as
    // the second parameter of `transform()` (note: we are also passing the state store name to the
    // constructor of `WordCountTransformerSupplier`, which we do primarily for cleaner code).
    // Otherwise our application will fail at run-time when attempting to operate on the state store
    // (within the transformer) because `ProcessorContext#getStateStore("WordCountsStore")` will
    // return `null`.
    KStream<String, MeterUsage> meterNumberDelta =
        stream.transformValues(new MeterUsageTransformerSupplier(meterNumberStore.name()), meterNumberStore.name());

    meterNumberDelta.to(outputTopic, Produced.with(Serdes.String(), meterNumberDeltaAvroSerde));

    return builder.build();
  }


}
