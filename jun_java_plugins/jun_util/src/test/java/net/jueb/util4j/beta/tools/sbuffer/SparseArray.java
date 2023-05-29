package net.jueb.util4j.beta.tools.sbuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SparseArray<T> {
    private static final int MIN_CAPACITY = 16; // must be power of 2
    private static final float MAX_LOAD_FACTOR = 0.85f;

    private boolean frozen;
    private int size;
    private int capacity;
    private long[] keyHashes; // key in upper 32 bits, and hash in lower
    private T[] values;

    public SparseArray() {
        resize(MIN_CAPACITY);
    }

    public SparseArray(final int initialCapacity) {
        resize(pow2Capacity(initialCapacity));
    }

    public SparseArray(final SparseArray<T> other) {
        int neededCapacity = pow2Capacity(other.size);
        if ((float)other.size / neededCapacity > MAX_LOAD_FACTOR) {
            neededCapacity *= 2;
        }
        size = other.size;
        keyHashes = other.keyHashes;
        values = other.values;
        resize(neededCapacity);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof SparseArray)) {
            return false;
        }
        final SparseArray other = (SparseArray)obj;
        if (other.size != size) {
            return false;
        }
        int found = 0;
        for (int i = 0; found < size; ++i) {
            final long kh = keyHashes[i];
            if ((int)kh != 0) {
                final int j = other.find(kh);
                if (j < 0 || !values[i].equals(other.values[j])) {
                    return false;
                }
                ++found;
            }
        }
        return true;
    }

    public int[] keys() {
        final int[] keys = new int[size];
        int found = 0;
        for (int i = 0; found < size; ++i) {
            final long kh = keyHashes[i];
            if ((int)kh != 0) {
                keys[found++] = (int)(kh >> 32);
            }
        }
        return keys;
    }

    public Iterable<T> values() {
        return new Iterable<T>()  {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    int index = 0;
                    int found = 0;

                    @Override
                    public boolean hasNext() {
                        return found < size;
                    }

                    @Override
                    public T next() {
                        for (; index < capacity; ++index) {
                            final T value = values[index];
                            if (value != null) {
                                ++index;
                                ++found;
                                return value;
                            }
                        }
                        throw new NoSuchElementException();
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return capacity;
    }

    public boolean containsKey(final int key) {
        return find(calcHashKey(key)) >= 0;
    }

    public T get(final int key) {
        return get(key, null);
    }

    public T get(final int key, final T defaultValue) {
        final int index = find(calcHashKey(key));
        if (index >= 0) {
            return values[index];
        }
        return defaultValue;
    }

    public void freeze() {
        frozen = true;
    }

    public void clear() {
        if (frozen) {
            throw new UnsupportedOperationException();
        }
        for (int i = 0; i < capacity; ++i) {
            keyHashes[i] = 0;
            values[i] = null;
        }
        size = 0;
    }

    public void put(final int key, final T value) {
        if (frozen) {
            throw new UnsupportedOperationException();
        }
        if (value == null) {
            throw new IllegalArgumentException("value is null");
        }
        if ((float)size / capacity > MAX_LOAD_FACTOR) {
            resize(capacity * 2);
        }
        put(calcHashKey(key), value);
    }

    public boolean remove(final int key) {
        if (frozen) {
            throw new UnsupportedOperationException();
        }
        final int index = find(calcHashKey(key));
        if (index < 0) {
            return false;
        }

        for (int i = 0; i < capacity; ++i) {
            final int curr = (index + i) & (capacity - 1);
            final int next = (index + i + 1) & (capacity - 1);

            final int h = (int)keyHashes[next];
            if (h == 0 || distToHome(h, next) == 0) {
                keyHashes[curr] = 0;
                values[curr] = null;
                --size;
                return true;
            }

            // shift back next value closer to its home slot
            keyHashes[curr] = keyHashes[next];
            values[curr] = values[next];
        }

        throw new RuntimeException("control flow should not get here");
    }

    private void put(long keyHash, T value) {
        final int startIndex = (int)keyHash & (capacity - 1);
        int probe = 0; // probe distance from home slot

        for (int i = 0; i < capacity; ++i, ++probe) {
            final int index = (startIndex + i) & (capacity - 1);
            final long kh = keyHashes[index];
            final int h = (int)kh;

            if (h == 0) {
                keyHashes[index] = keyHash;
                values[index] = value;
                ++size;
                return;
            }

            if (kh == keyHash) {
                values[index] = value;
                return;
            }

            final int d = distToHome(h, index);
            if (probe > d) {
                // if we are farther from home than the encountered value, then we take its place
                probe = d;
                long tempHK = keyHashes[index];
                T tempVal = values[index];
                keyHashes[index] = keyHash;
                values[index] = value;
                keyHash = tempHK;
                value = tempVal;
            }
        }

        throw new RuntimeException("control flow should not get here");
    }

    private int find(final long keyHash) {
        final int startIndex = (int)keyHash & (capacity - 1);

        for (int i = 0; i < capacity; ++i) {
            final int index = (startIndex + i) & (capacity - 1);
            final long kh = keyHashes[index];

            if (kh == keyHash) {
                return index;
            }

            final int h = (int)kh;
            if (h == 0) {
                // if we encounter an empty slow we give up, since backward shifting on delete
                // will leave no empty slots keeping a value away from its home slot
                return -1;
            }

            int d = distToHome(h, index);
            if (i > d) {
                // if current probe distance is farther from home than that of encountered value,
                // then we give up, since our value would have taken its place had it been present
                return -1;
            }
        }

        return -1;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        if (newCapacity < size) {
            throw new RuntimeException("new capacity is too small");
        }

        final int oldSize = size;
        final long[] oldHashKeys = keyHashes;
        final T[] oldValues = values;

        size = 0;
        capacity = newCapacity;
        keyHashes = new long[newCapacity];
        values = (T[]) new Object[newCapacity]; // unchecked cast

        int found = 0;
        for (int i = 0; found < oldSize; ++i) {
            final long kh = oldHashKeys[i];
            if ((int)kh != 0) {
                put(kh, oldValues[i]);
                ++found;
            }
        }
    }

    private int distToHome(int hash, int indexStored) {
        final int startIndex = hash & (capacity - 1);
        if (startIndex <= indexStored) {
            return indexStored - startIndex;
        }
        return indexStored + (capacity - startIndex);
    }

    private static long calcHashKey(int key) {
        int hash = smear(key);
        if (hash == 0) {
            hash = 1; // don't allow hash of 0, so we can use that to distinguish lack of value
        }
        return ((long)key << 32) | (hash & 0xFFFFFFFFL);
    }

    /*
     * This method was written by Doug Lea with assistance from members of JCP
     * JSR-166 Expert Group and released to the public domain, as explained at
     * http://creativecommons.org/licenses/publicdomain
     * 
     * As of 2010/06/11, this method is identical to the (package private) hash
     * method in OpenJDK 7's java.util.HashMap class.
     */
    private static int smear(int hashCode) {
        hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
        return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 4);
    }

    private static int pow2Capacity(int x) {
        if (x < MIN_CAPACITY) {
            return MIN_CAPACITY;
        }
        // next higher power of two:
        x = x - 1;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        return x + 1;
    }



    public static void main(String[] args) {
        System.out.println("hei");
        testHashKeyPacking(0);
        testHashKeyPacking(1);
        testHashKeyPacking(-1);
        testHashKeyPacking(255);
        testHashKeyPacking(113254325);
        testHashKeyPacking(Integer.MIN_VALUE);
        testHashKeyPacking(Integer.MAX_VALUE);

        assert pow2Capacity(0) == 16;
        assert pow2Capacity(1) == 16;
        assert pow2Capacity(-1) == 16;
        assert pow2Capacity(10) == 16;
        assert pow2Capacity(15) == 16;
        assert pow2Capacity(16) == 16;
        assert pow2Capacity(129) == 256;
        assert pow2Capacity(255) == 256;
        assert pow2Capacity(256) == 256;

        SparseArray<Integer> arr = new SparseArray<>();
        final int N = 100000;
        for (int i = 0; i < N; ++i) {
            arr.put(i, i);
            arr.put(i, i);
        }
        
        int[] keys = arr.keys();
        assert keys.length == N;
        for (int i = 0; i < N; ++i) {
            assert arr.get(i) == i;
            assert arr.get(keys[i]) == keys[i];
        }
        
        int valueCount = 0;
        SparseArray<Integer> arr2 = new SparseArray<>(N);
        for (Integer val : arr.values()) {
            int i = val;
            assert arr2.get(i) == null;
            arr2.put(i, i);
            assert arr2.get(i) == i;
            assert arr.get(i) == i;
            ++valueCount;
        }

        SparseArray<Integer> arr3 = new SparseArray<>(arr);
        assert arr.equals(arr3);

        assert arr.equals(arr2);
        assert valueCount == N;
        assert arr.size() == N;
        assert arr.containsKey(100);
        assert arr.remove(100);
        assert !arr.remove(100);
        assert !arr.containsKey(100);
        assert arr.size() == N-1;
        assert !arr.equals(arr2);

        for (int i = 0; i < N; ++i) {
            if (i != 100) {
                assert arr.get(i) == i;
            }
        }
        for (int i = 0; i < N; ++i) {
            if (i != 100) {
                assert arr.remove(i);
            }
        }
        assert arr.size() == 0;
        for (int i = 0; i < N; ++i) {
            assert arr.get(i) == null;
        }

        final int COUNT = 10000000;

        long start2 = System.currentTimeMillis();
        SparseArray<Integer> sparseArray = new SparseArray<>();
        for (int i = 0; i < COUNT; ++i) {
            sparseArray.put(i, i);
        }
        for (int i = 0; i < COUNT; ++i) {
            assert sparseArray.containsKey(i);
            assert sparseArray.containsKey(i ^ 13);
        }
        System.out.println("SparseArray: " + ((System.currentTimeMillis() - start2) / 1000.0));

        long start1 = System.currentTimeMillis();
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < COUNT; ++i) {
            hashMap.put(i, i);
        }
        for (int i = 0; i < COUNT; ++i) {
            assert hashMap.containsKey(i);
            assert hashMap.containsKey(i ^ 13);
        }
        System.out.println("HashMap: " + ((System.currentTimeMillis() - start1) / 1000.0));
    }

    private static void testHashKeyPacking(int key) {
        long kh = calcHashKey(key);

        int hash = smear(key);
        if (hash == 0) {
            hash = 1;
        }

        assert key == (int)(kh >> 32) : "error extracting key";
        assert hash == (int)kh : "error extracting hash";
    }
}