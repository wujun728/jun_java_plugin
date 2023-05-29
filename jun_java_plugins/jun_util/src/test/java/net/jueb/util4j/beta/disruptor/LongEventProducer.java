package net.jueb.util4j.beta.disruptor;

import com.lmax.disruptor.RingBuffer;

public class LongEventProducer
{
    private final RingBuffer<LongEvent<Runnable>> ringBuffer;

    public LongEventProducer(RingBuffer<LongEvent<Runnable>> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void publish(Runnable txt)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            LongEvent<Runnable> event = ringBuffer.get(sequence); // Get the entry in the Disruptor
            event.set(txt);  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}