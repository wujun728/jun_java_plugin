package me.chanjar.kafka.stream.hourly_usage;

import me.chanjar.kafka.stream.model.MeterUsage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class MeterUsageTimeExtractor implements TimestampExtractor {

  @Override
  public long extract(ConsumerRecord<Object, Object> record, long previousTimestamp) {
    return ((MeterUsage)record.value()).getTimestamp();
  }

}
