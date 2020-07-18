/*
 * Created on Nov 23, 2004
 *
 */
package cn.org.rapid_framework.generator.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author MF1180
 * Hashtable that maintains the input order of the data elements - Note
 * only put, putall, clear and remove maintains the ordered list
 *
 */
public class ListHashtable extends Hashtable {
	protected List orderedKeys = new ArrayList();
	public synchronized void clear() {
		super.clear();
		orderedKeys = new ArrayList();
	}
	public synchronized Object put(Object aKey, Object aValue) {
		if (orderedKeys.contains(aKey)) {
			int pos = orderedKeys.indexOf(aKey);
			orderedKeys.remove(pos);
			orderedKeys.add(pos,aKey);
		}
		else {
			if (aKey instanceof Integer) {
				Integer key = (Integer) aKey;
				int pos = getFirstKeyGreater(key.intValue());
				if (pos >= 0)
					orderedKeys.add(pos,aKey);
				else
					orderedKeys.add(aKey);
			}
			else 
				orderedKeys.add(aKey);
		}
		return super.put(aKey, aValue);
	}
	/**
	 * @param aKey
	 * @returns 
	 * calculate position at which the first key is greater
	 * otherwise return -1 if no key can be found which is greater
	 * 
	 */
	private int getFirstKeyGreater(int aKey) {
		int pos = 0;
		int numKeys = getOrderedKeys().size();
		for (int i=0;i<numKeys;i++) {
			Integer key = (Integer) getOrderedKey(i);
			int keyval = key.intValue();
			if (keyval < aKey)
				++pos;
			else
				break;
		}
		if (pos >= numKeys)
			pos = -1;
		return pos;
	}
	public synchronized Object remove(Object aKey) {
		if (orderedKeys.contains(aKey)) {
			int pos = orderedKeys.indexOf(aKey);
			orderedKeys.remove(pos);
		}
		return super.remove(aKey);
	}
	/**
	 *  This method reorders the ListHashtable only if the keys 
	 *  used are integer keys.
	 * 
	 */
	public void reorderIntegerKeys() {
		List keys = getOrderedKeys();
		int numKeys = keys.size();
		if (numKeys <=0 )
			return;
		
		if (!(getOrderedKey(0) instanceof Integer))
			return;
		
		List newKeys   = new ArrayList();
		List newValues = new ArrayList();
		
		for (int i=0;i<numKeys;i++) {
			Integer key = (Integer) getOrderedKey(i);
			Object  val = getOrderedValue(i);
			int numNew = newKeys.size();
			int pos = 0;
			for (int j=0;j<numNew;j++) {
				Integer newKey = (Integer) newKeys.get(j);
				if (newKey.intValue() < key.intValue()) 
					++pos;
				else
					break;
			}
			if (pos >= numKeys) {
				newKeys.add(key);
				newValues.add(val);
			} else {
				newKeys.add(pos,key);
				newValues.add(pos,val);
			}
		}
		// reset the contents
		this.clear();
		for (int l=0;l<numKeys;l++) {
			put(newKeys.get(l),newValues.get(l));
		}
	}
	public String toString() {
		StringBuffer x = new StringBuffer();
		x.append("Ordered Keys: ");
		int numKeys = orderedKeys.size();
		x.append("[");
		for (int i=0;i<numKeys;i++) {
			x.append(orderedKeys.get(i)+ " ");
		}
		x.append("]\n");
		
		x.append("Ordered Values: ");
		x.append("[");
		
		for (int j=0;j<numKeys;j++) {
			x.append(getOrderedValue(j)+" ");
		}
		x.append("]\n");
		return x.toString();
	}
	public void merge(ListHashtable newTable) {
		// This merges the newtable with the current one
		int num = newTable.size();
		for (int i=0;i<num;i++) {
			Object aKey = newTable.getOrderedKey(i);
			Object aVal = newTable.getOrderedValue(i);
			this.put(aKey, aVal);
		}
	}
	/**
	 * @return Returns the orderedKeys.
	 */
	public List getOrderedKeys() {
		return orderedKeys;
	}
	public Object getOrderedKey(int i) {
		return getOrderedKeys().get(i);
	}
	/**
	 * This method looks through the list of values and returns the key
	 * associated with the value.. Otherwise if not found, null is returned
	 * @param aValue
	 * @return
	 */
	public Object getKeyForValue(Object aValue) {
		int num = getOrderedValues().size();
		for (int i=0;i<num;i++) {
			Object tmpVal = getOrderedValue(i);
			if (tmpVal.equals(aValue)) {
				return getOrderedKey(i);
			}
		}
		return null;
	}
	public List getOrderedValues() {
		List values = new ArrayList();
		int numKeys = orderedKeys.size();
		for (int i=0;i<numKeys;i++) {
			values.add(get(getOrderedKey(i)));
		}
		return values;
	}
	public Object getOrderedValue(int i) {
		return get(getOrderedKey(i));
	}
}
