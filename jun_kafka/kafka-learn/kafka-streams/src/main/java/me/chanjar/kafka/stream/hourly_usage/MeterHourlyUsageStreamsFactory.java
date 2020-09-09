package me.chanjar.kafka.stream.hourly_usage;

import me.chanjar.kafka.stream.util.SimpleStreamsConfigurationConfigurer;
import me.chanjar.kafka.stream.util.AbstractStreamsFactory;
import me.chanjar.kafka.stream.util.TopologyFactory;
import org.apache.kafka.streams.StreamsConfig;

import java.util.HashMap;
import java.util.Map;

public class MeterHourlyUsageStreamsFactory extends AbstractStreamsFactory {

  public MeterHourlyUsageStreamsFactory(TopologyFactory topologyFactory) {

    super(topologyFactory);

    Map<String, Object> config = new HashMap<>();
    config.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, "exactly_once");
    config.put(StreamsConfig.DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, MeterUsageTimeExtractor.class.getName());
    addStreamsConfigurationConfigurer(new SimpleStreamsConfigurationConfigurer(config));
  }

}
