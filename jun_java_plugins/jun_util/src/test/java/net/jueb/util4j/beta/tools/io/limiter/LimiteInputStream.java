package net.jueb.util4j.beta.tools.io.limiter;
import java.io.IOException;
import java.io.InputStream;
/**
 * 限速流
 * @author helin
 *
 */
public class LimiteInputStream extends InputStream {
     private InputStream in = null;
     private Limiter limiter = null;
     /**
      * 
      * @param out
      * @param maxRate 		 
	  *            the download or upload speed in KBytes
      */
     public LimiteInputStream(InputStream in, int maxRate)
     {
         this.in = in;
         this.limiter = new Limiter(maxRate);
     }
     @Override
     public int read() throws IOException {
         if(this.limiter != null)
             this.limiter.limitNextBytes();
         return this.in.read();
     }
 
     @Override
	public int read(byte b[], int off, int len) throws IOException
     {
         if (limiter != null)
        	 limiter.limitNextBytes(len);
         return this.in.read(b, off, len);
     }
     
     
     /**
      * 限速器
      * @author helin
      *
      */
     public class Limiter {
    	  
         /* KB */
         private  Long KB = 1024l;
     
         /* The smallest count chunk length in bytes */
         private  Long CHUNK_LENGTH = 1024l;
     
         /* How many bytes will be sent or receive */
         private int bytesWillBeSentOrReceive = 0;
     
         /* When the last piece was sent or receive */
         private long lastPieceSentOrReceiveTick = System.nanoTime();
     
         /* Default rate is 1024KB/s */
         private int maxRate = 1024;
     
         /* Time cost for sending CHUNK_LENGTH bytes in nanoseconds */
         private long timeCostPerChunk = (1000000000l * CHUNK_LENGTH)
                 / (this.maxRate * KB);
     
         /**
          * Initialize a Limiter object with a certain rate.
          * 
          * @param maxRate
          *            the download or upload speed in KBytes
          */
         public Limiter(int maxRate) {
             this.setMaxRate(maxRate);
         }
     
         /**
          * Set the max upload or download rate in KB/s. maxRate must be grater than
          * 0. If maxRate is zero, it means there is no bandwidth limit.
          * 
          * @param maxRate
          *            If maxRate is zero, it means there is no bandwidth limit.
          * @throws IllegalArgumentException
          */
         public synchronized void setMaxRate(int maxRate)
                 throws IllegalArgumentException {
             if (maxRate < 0) {
                 throw new IllegalArgumentException("maxRate can not less than 0");
             }
             this.maxRate = maxRate < 0 ? 0 : maxRate;
             if (maxRate == 0)
                 this.timeCostPerChunk = 0;
             else
                 this.timeCostPerChunk = (1000000000l * CHUNK_LENGTH)
                         / (this.maxRate * KB);
         }
     
         /**
          * Next 1 byte should do bandwidth limit.
          */
         public synchronized void limitNextBytes() {
             this.limitNextBytes(1);
         }
     
         /**
          * Next len bytes should do bandwidth limit
          * 
          * @param len
          */
         public synchronized void limitNextBytes(int len) {
             this.bytesWillBeSentOrReceive += len;
     
             /* We have sent CHUNK_LENGTH bytes */
             while (this.bytesWillBeSentOrReceive > CHUNK_LENGTH) {
                 long nowTick = System.nanoTime();
                 long missedTime = this.timeCostPerChunk
                         - (nowTick - this.lastPieceSentOrReceiveTick);
                 if (missedTime > 0) {
                     try {
                         Thread.sleep(missedTime / 1000000,
                                 (int) (missedTime % 1000000));
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
                 this.bytesWillBeSentOrReceive -= CHUNK_LENGTH;
                 this.lastPieceSentOrReceiveTick = nowTick
                         + (missedTime > 0 ? missedTime : 0);
             }
         }
     }
     
 }
