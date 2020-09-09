package me.chanjar.kafka.stream.util;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;

public interface TopologyFactory {

  Topology build(final StreamsBuilder builder);

}
