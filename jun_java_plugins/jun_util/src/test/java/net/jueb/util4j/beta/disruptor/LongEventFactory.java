package net.jueb.util4j.beta.disruptor;

import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory<LongEvent<Runnable>>
{
    public LongEvent<Runnable> newInstance()
    {
        return new LongEvent<Runnable>();
    }
}