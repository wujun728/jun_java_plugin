package com.jun.plugin.util4j.queue.queueExecutor.queue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.jun.plugin.util4j.queue.queueExecutor.RunnableQueue;

/**
 * 队列包装
* @author juebanlin
*/
public class RunnableQueueWrapper implements RunnableQueue{
	
	private final Queue<Runnable> queue;
	
	public RunnableQueueWrapper(Queue<Runnable> queue) {
		Objects.requireNonNull(queue);
		this.queue=queue;
	}
	
	@Override
	public boolean add(Runnable e) {
		return queue.add(e);
	}

	@Override
	public Runnable remove() {
		return queue.remove();
	}

	@Override
	public Runnable poll() {
		return queue.poll();
	}

	@Override
	public Runnable element() {
		return queue.element();
	}

	@Override
	public Runnable peek() {
		return queue.peek();
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return queue.contains(o);
	}

	@Override
	public Iterator<Runnable> iterator() {
		return queue.iterator();
	}

	@Override
	public Object[] toArray() {
		return queue.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return queue.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		return queue.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return queue.containsAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return queue.removeAll(c);
	}

	@Override
	public boolean removeIf(Predicate<? super Runnable> filter) {
		return queue.removeIf(filter);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return queue.retainAll(c);
	}
	
	@Override
	public void clear() {
		queue.clear();
	}

	@Override
	public Spliterator<Runnable> spliterator() {
		return queue.spliterator();
	}

	@Override
	public Stream<Runnable> stream() {
		return queue.stream();
	}

	@Override
	public Stream<Runnable> parallelStream() {
		return queue.parallelStream();
	}

	@Override
	public void forEach(Consumer<? super Runnable> action) {
		queue.forEach(action);
	}

	@Override
    public boolean offer(Runnable e) {
        return queue.offer(e);
    }
		
	@Override
	public boolean addAll(Collection<? extends Runnable> c) {
        return queue.addAll(c);
	}

	@Override
	public String toString() {
		return "RunnableQueueWrapper [queue=" + queue + "]";
	}
}