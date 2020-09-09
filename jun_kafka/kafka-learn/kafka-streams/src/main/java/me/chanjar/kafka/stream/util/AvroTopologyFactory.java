package me.chanjar.kafka.stream.util;

public abstract class AvroTopologyFactory implements TopologyFactory {

  private final String schemaRegistryUrl;

  public AvroTopologyFactory(String schemaRegistryUrl) {
    this.schemaRegistryUrl = schemaRegistryUrl;
  }

  public String getSchemaRegistryUrl() {
    return schemaRegistryUrl;
  }

}

