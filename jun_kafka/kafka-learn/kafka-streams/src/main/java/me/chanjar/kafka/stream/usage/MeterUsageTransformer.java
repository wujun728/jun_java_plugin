package me.chanjar.kafka.stream.usage;

import me.chanjar.kafka.stream.model.MeterNumber;
import me.chanjar.kafka.stream.model.MeterUsage;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Optional;

public class MeterUsageTransformer implements ValueTransformer<MeterNumber, MeterUsage> {

  private final String stateStoreName;
  private KeyValueStore<String, Double> stateStore;

  public MeterUsageTransformer(String stateStoreName) {
    this.stateStoreName = stateStoreName;
  }

  @Override
  public void init(ProcessorContext context) {
    stateStore = (KeyValueStore<String, Double>) context.getStateStore(stateStoreName);
  }

  @Override
  public MeterUsage transform(MeterNumber value) {

    Optional<Double> prevNumber = Optional.ofNullable(stateStore.get(value.getMeterId()));

    MeterUsage delta = new MeterUsage();
    delta.setMeterId(value.getMeterId());
    delta.setCurrNumber(value.getNumber());
    delta.setTimestamp(value.getTimestamp());

    prevNumber.ifPresent(pv -> {
      delta.setPrevNumber(prevNumber.get());
      delta.setDelta(value.getNumber() - prevNumber.get());
    });
    stateStore.put(value.getMeterId(), value.getNumber());

    return delta;
  }

  @Override
  public MeterUsage punctuate(long timestamp) {
    return null;
  }

  @Override
  public void close() {
    // Nothing to do
  }
}
