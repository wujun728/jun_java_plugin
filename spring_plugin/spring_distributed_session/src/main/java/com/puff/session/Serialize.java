package com.puff.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.nustaq.serialization.FSTObjectInput;
import org.nustaq.serialization.FSTObjectOutput;

public class Serialize {

	public static byte[] serialize(Object value) {
		return fstserialize(value);
	}

	public static <T> T deserialize(byte[] b) {
		return fstdeserialize(b);
	}

	public static byte[] jdkserialize(Object obj) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			return baos.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
				}
			}
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T jdkdeserialize(byte[] bits) {
		ObjectInputStream ois = null;
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(bits);
			ois = new ObjectInputStream(bais);
			return (T) ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
				}
			}
			if (bais != null) {
				try {
					bais.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static byte[] fstserialize(Object obj) {
		ByteArrayOutputStream out = null;
		FSTObjectOutput fout = null;
		try {
			out = new ByteArrayOutputStream(1024 * 16);
			fout = new FSTObjectOutput(out);
			fout.writeObject(obj);
			fout.flush();
			return out.toByteArray();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (fout != null) {
				try {
					fout.close();
				} catch (IOException e) {
					// ignore close exception
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T fstdeserialize(byte[] bytes) {
		if (bytes == null) {
			return null;
		}
		FSTObjectInput in = null;
		try {
			in = new FSTObjectInput(new ByteArrayInputStream(bytes));
			return (T) in.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
