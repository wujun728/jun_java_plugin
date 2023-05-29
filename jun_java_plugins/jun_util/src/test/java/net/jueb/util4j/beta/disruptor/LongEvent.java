package net.jueb.util4j.beta.disruptor;
public class LongEvent<T>
{
    private T value;
    public void set(T value)
    {
        this.value = value;
    }
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "LongEvent [value=" + value + "]";
	}
}