package me.chanjar.kafka.stream.util;

import java.util.Properties;

public interface StreamsConfigurationConfigurer {

  void configure(Properties streamsConfiguration);

}
