package me.chanjar.kafka.stream.usage;

import me.chanjar.kafka.stream.model.MeterNumber;
import me.chanjar.kafka.stream.model.MeterUsage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;

public class MeterUsageTransformerSupplier implements ValueTransformerSupplier<MeterNumber, MeterUsage> {

  private final String stateStoreName;

  public MeterUsageTransformerSupplier(String stateStoreName) {
    this.stateStoreName = stateStoreName;
  }

  @Override
  public ValueTransformer<MeterNumber, MeterUsage> get() {
    return new MeterUsageTransformer(stateStoreName);
  }

}
