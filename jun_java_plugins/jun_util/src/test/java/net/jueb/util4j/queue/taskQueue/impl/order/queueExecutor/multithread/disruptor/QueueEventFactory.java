package net.jueb.util4j.queue.taskQueue.impl.order.queueExecutor.multithread.disruptor;

import com.lmax.disruptor.EventFactory;

class QueueEventFactory implements EventFactory<QueueEvent>
{
        public QueueEvent newInstance()
        {
            return new QueueEvent();
        }
}