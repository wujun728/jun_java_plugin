package com.redis.proxy.net.resps;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author zhanggaofeng
 */
public class MultiBulkRdsResp extends RdsResp<RdsResp[]> {

        public static final char MARKER = '*';
        public static final MultiBulkRdsResp EMPTY = new MultiBulkRdsResp(new RdsResp[0]);

        private RdsResp[] replies;

        public MultiBulkRdsResp() {
        }

        public MultiBulkRdsResp(RdsResp[] replies) {
        		if (replies != null) {
        			this.replies = replies;
        		} else {
        			this.replies = new RdsResp[0];
        		}
        }

        public MultiBulkRdsResp(Map<byte[], byte[]> map) {
        		if (map == null || map.isEmpty()) {
        			this.replies = new RdsResp[0];
        		} else {
	                int i = 0;
	                replies = new BulkRdsResp[map.size() * 2];
	                Set<byte[]> keys = map.keySet();
	                for (byte[] key : keys) {
	                        replies[i++] = new BulkRdsResp(key);
	                        replies[i++] = new BulkRdsResp(map.get(key));
	                }
        		}
        }

        public MultiBulkRdsResp(Set<byte[]> set) {
	        	if (set == null || set.isEmpty()) {
	    			this.replies = new RdsResp[0];
	    		} else {
	                int i = 0;
	                replies = new BulkRdsResp[set.size()];
	                for (byte[] key : set) {
	                        replies[i++] = new BulkRdsResp(key);
	                }
	    		}
        }

        public MultiBulkRdsResp(List<String> set) {
	        	if (set == null || set.isEmpty()) {
	    			this.replies = new RdsResp[0];
	    		} else {
	                int i = 0;
	                replies = new BulkRdsResp[set.size()];
	                for (String key : set) {
	                        replies[i++] = new BulkRdsResp(key.getBytes(Charset.forName("UTF-8")));
	                }
	    		}
        }

        public MultiBulkRdsResp(Collection<byte[]> coll) {
	        	if (coll == null || coll.isEmpty()) {
	    			this.replies = new RdsResp[0];
	    		} else {
	                int i = 0;
	                replies = new BulkRdsResp[coll.size()];
	                for (byte[] key : coll) {
	                        replies[i++] = new BulkRdsResp(key);
	                }
	    		}
        }

        @Override
        public RdsResp[] data() {
                return replies;
        }

        @Override
        public void write(ByteBuf os) throws IOException {
                os.writeByte(MARKER);
                if (replies == null) {
                        os.writeBytes(RdsResp.NEG_ONE_WITH_CRLF);
                } else {
                        os.writeBytes(RdsResp.numToBytes(replies.length, true));
                        for (RdsResp reply : replies) {
                                reply.write(os);
                        }
                }
        }

}
