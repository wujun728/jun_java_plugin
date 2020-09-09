package me.chanjar.kafka.stream.util;

import java.util.Map;
import java.util.Properties;

public class SimpleStreamsConfigurationConfigurer implements StreamsConfigurationConfigurer{


  private final Map<String, Object> configs;

  public SimpleStreamsConfigurationConfigurer(Map<String, Object> configs) {
    this.configs = configs;
  }

  @Override
  public void configure(Properties streamsConfiguration) {

    for (Map.Entry<String, Object> entry : configs.entrySet()) {
      streamsConfiguration.put(entry.getKey(), entry.getValue());
    }

  }

}
