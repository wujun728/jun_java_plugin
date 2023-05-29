package net.jueb.util4j.test;

import java.io.IOException;
import java.util.BitSet;

public class BitMapDemo {
	private BitSet bits;

//	public void perform(String largeFileName, int total, String destLargeFileName, Castor<Integer> castor,
//			int readerBufferSize, int writerBufferSize, boolean asc) throws IOException {
//
//		System.out.println("BitmapSort Started.");
//		long start = System.currentTimeMillis();
//		bits = new BitSet(total);
//		InputPart<Integer> largeIn = PartFactory.createCharBufferedInputPart(largeFileName, readerBufferSize);
//		OutputPart<Integer> largeOut = PartFactory.createCharBufferedOutputPart(destLargeFileName, writerBufferSize);
//		largeOut.delete();
//
//		Integer data;
//		int off = 0;
//		try {
//			while (true) {
//				data = largeIn.read();
//				if (data == null)
//					break;
//				int v = data;
//				set(v);
//				off++;
//			}
//			largeIn.close();
//			int size = bits.size();
//			System.out.println(String.format("lines : %d ,bits : %d", off, size));
//
//			if (asc) {
//				for (int i = 0; i < size; i++) {
//					if (get(i)) {
//						largeOut.write(i);
//					}
//				}
//			} else {
//				for (int i = size - 1; i >= 0; i--) {
//					if (get(i)) {
//						largeOut.write(i);
//					}
//				}
//			}
//
//			largeOut.close();
//			long stop = System.currentTimeMillis();
//			long elapsed = stop - start;
//			System.out.println(String.format("BitmapSort Completed.elapsed : %dms", elapsed));
//		} finally {
//			largeIn.close();
//			largeOut.close();
//		}
//	}

	private void set(int i) {
		bits.set(i);
	}

	private boolean get(int v) {
		return bits.get(v);
	}
}
