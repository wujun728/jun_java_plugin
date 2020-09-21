package com.redis.proxy.net.resps;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 *
 * @author zhanggaofeng
 */
public class BulkRdsResp extends RdsResp<ByteBuf> {

        public static final BulkRdsResp NIL_REPLY = new BulkRdsResp();

        public static final char MARKER = '$';
        private final ByteBuf bytes;
        private final int capacity;

        public BulkRdsResp() {
                bytes = null;
                capacity = -1;
        }

        public BulkRdsResp(byte[] bytes) {
        		if (bytes != null) {
        			this.bytes = Unpooled.wrappedBuffer(bytes);
        			this.capacity = bytes.length;
        		} else {
        			this.bytes = null;
                    this.capacity = -1;
        		}
        }

        public BulkRdsResp(Double value) {
                byte[] data = value.toString().getBytes(Charset.forName("UTF-8"));
                this.bytes = Unpooled.wrappedBuffer(data);
                capacity = data.length;
        }

        @Override
        public ByteBuf data() {
                return bytes;
        }

        public String asAsciiString() {
                if (bytes == null) {
                        return null;
                }
                return bytes.toString(Charsets.US_ASCII);
        }

        public String asUTF8String() {
                if (bytes == null) {
                        return null;
                }
                return bytes.toString(Charsets.UTF_8);
        }

        public String asString(Charset charset) {
                if (bytes == null) {
                        return null;
                }
                return bytes.toString(charset);
        }

        @Override
        public void write(ByteBuf os) throws IOException {
                os.writeByte(MARKER);
                os.writeBytes(RdsResp.numToBytes(capacity, true));
                if (capacity > 0) {
                        os.writeBytes(bytes);
                        os.writeBytes(CRLF);
                }
        }

        public String toString() {
                return asUTF8String();
        }
}
