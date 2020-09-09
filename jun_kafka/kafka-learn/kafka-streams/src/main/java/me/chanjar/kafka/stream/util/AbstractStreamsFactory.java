package me.chanjar.kafka.stream.util;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class AbstractStreamsFactory implements StreamsFactory {

  private final TopologyFactory topologyFactory;

  private final List<StreamsConfigurationConfigurer> streamsConfigurationConfigurers = new ArrayList<>();

  public AbstractStreamsFactory(TopologyFactory topologyFactory) {
    this.topologyFactory = topologyFactory;
  }

  @Override
  public void addStreamsConfigurationConfigurer(StreamsConfigurationConfigurer configurer) {
    streamsConfigurationConfigurers.add(configurer);
  }

  @Override
  public KafkaStreams build() {

    StreamsBuilder streamsBuilder = new StreamsBuilder();
    Topology topology = topologyFactory.build(streamsBuilder);
    Properties streamsConfiguration = new Properties();

    streamsConfigurationConfigurers.forEach(configurer -> configurer.configure(streamsConfiguration));
    KafkaStreams streams = new KafkaStreams(topology, streamsConfiguration);

    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    return streams;

  }

}
