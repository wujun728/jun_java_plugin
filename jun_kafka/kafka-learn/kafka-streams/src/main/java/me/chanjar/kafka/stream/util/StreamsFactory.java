package me.chanjar.kafka.stream.util;

import org.apache.kafka.streams.KafkaStreams;

public interface StreamsFactory {

  void addStreamsConfigurationConfigurer(StreamsConfigurationConfigurer configurer);

  KafkaStreams build();

}
