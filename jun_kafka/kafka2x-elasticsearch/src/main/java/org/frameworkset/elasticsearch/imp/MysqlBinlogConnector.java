package org.frameworkset.elasticsearch.imp;
/**
 * Copyright 2020 bboss
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.frameworkset.util.SimpleStringUtil;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.frameworkset.plugin.kafka.KafkaProductor;
import org.frameworkset.plugin.kafka.KafkaUtil;
import org.frameworkset.runtime.CommonLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2020</p>
 * @Date 2020/4/8 15:06
 * @author biaoping.yin
 * @version 1.0
 */
public class MysqlBinlogConnector {
	private static Logger logger = LoggerFactory.getLogger(MysqlBinlogConnector.class);
	public void binlogClient() throws IOException {
		BinaryLogClient client = new BinaryLogClient("10.13.11.5", 3306, "root", "123456");
		EventDeserializer eventDeserializer = new EventDeserializer();
		eventDeserializer.setCompatibilityMode(
				EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
				EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
		);
		client.setEventDeserializer(eventDeserializer);
		KafkaUtil kafkaUtil = new KafkaUtil("kafka.xml");
		final KafkaProductor productor = kafkaUtil.getProductor("kafkaproductor");
		client.registerEventListener(new BinaryLogClient.EventListener() {

			public void onEvent(Event event) {
				if(event.getHeader().getEventType() == EventType.EXT_DELETE_ROWS){
					Map<String, EventData> data = new HashMap<>();
					String dataevent = SimpleStringUtil.object2json(event);
					logger.info(event.getHeader().getEventType()+"");
					logger.info(dataevent);
					Future<RecordMetadata> recordMetadataFuture = productor.send("mysqlbinlog", dataevent);

				}
				else if(event.getHeader().getEventType() == EventType.EXT_UPDATE_ROWS){
					String dataevent = SimpleStringUtil.object2json(event);
					logger.info(event.getHeader().getEventType()+"");
					logger.info(dataevent);
					Future<RecordMetadata> recordMetadataFuture = productor.send("mysqlbinlog", dataevent);

				}
				else if(event.getHeader().getEventType() == EventType.EXT_WRITE_ROWS){
					String dataevent = SimpleStringUtil.object2json(event);
					logger.info(event.getHeader().getEventType()+"");
					logger.info(dataevent);
					Future<RecordMetadata> recordMetadataFuture = productor.send("mysqlbinlog", dataevent);

				}
				else if(event.getHeader().getEventType() == EventType.QUERY || event.getHeader().getEventType() == EventType.ROWS_QUERY){
					String dataevent = SimpleStringUtil.object2json(event);
					logger.info(event.getHeader().getEventType()+"");
					logger.info(dataevent);
					Future<RecordMetadata> recordMetadataFuture = productor.send("mysqlbinlog", dataevent);

				}
			}
		});
		client.connect();
	}
	public void binlogfile() throws Exception{
		String binlogpath = CommonLauncher.getProperty("mysql.binlog");
		File binlogFile = new File(binlogpath);
		EventDeserializer eventDeserializer = new EventDeserializer();
		eventDeserializer.setCompatibilityMode(
				EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
				EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
		);
		KafkaUtil kafkaUtil = new KafkaUtil("kafka.xml");
		KafkaProductor productor = kafkaUtil.getProductor("kafkaproductor");
		BinaryLogFileReader reader = new BinaryLogFileReader(binlogFile, eventDeserializer);
		try {
			for (Event event; (event = reader.readEvent()) != null; ) {
				if(event.getHeader().getEventType() == EventType.EXT_DELETE_ROWS){
					Map<String, EventData> data = new HashMap<>();

					Future<RecordMetadata> recordMetadataFuture = productor.send("mysqlbinlog", SimpleStringUtil.object2json(event));

				}
				else if(event.getHeader().getEventType() == EventType.EXT_UPDATE_ROWS){
					Future<RecordMetadata> recordMetadataFuture = productor.send("mysqlbinlog", SimpleStringUtil.object2json(event));

				}
				else if(event.getHeader().getEventType() == EventType.EXT_WRITE_ROWS){
					Future<RecordMetadata> recordMetadataFuture = productor.send("mysqlbinlog", SimpleStringUtil.object2json(event));

				}
				else if(event.getHeader().getEventType() == EventType.QUERY || event.getHeader().getEventType() == EventType.ROWS_QUERY){
					Future<RecordMetadata> recordMetadataFuture = productor.send("mysqlbinlog", SimpleStringUtil.object2json(event));

				}
			}
		} finally {
			reader.close();
		}
	}
	public static void main(String[] args) throws IOException {
		MysqlBinlogConnector mysqlBinlogConnector = new MysqlBinlogConnector();
		mysqlBinlogConnector.binlogClient();
	}
}
