package net.jueb.util4j.beta.disruptor;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

public class LongEventProducerWithTranslator
{
    private final RingBuffer<LongEvent<Runnable>> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent<Runnable>> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    private static final EventTranslatorOneArg<LongEvent<Runnable>, Runnable> TRANSLATOR =
        new EventTranslatorOneArg<LongEvent<Runnable>,Runnable>()
        {
            public void translateTo(LongEvent<Runnable> event, long sequence,Runnable txt)
            {
                event.set(txt);
            }
        };

    public void publishEvent(Runnable bb)
    {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}