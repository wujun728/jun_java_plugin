package net.jueb.util4j.beta.serializable.nmap.type;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import net.jueb.util4j.beta.serializable.nmap.falg.Flag;
import net.jueb.util4j.beta.serializable.nmap.util.NMapConvert;
import net.jueb.util4j.beta.tools.convert.typebytes.TypeBytesInputStream;

/**
 * 内部封装一个hashmap
 * @author Administrator
 *
 */
public class NMap extends NType<Map<NType<?>,NType<?>>> implements Map<NType<?>, NType<?>>{

	public NMap() {
		super(new LinkedHashMap<NType<?>, NType<?>>(), Flag.Head.NMap, Flag.End.NMap);
	}
	
	@Override
	public byte[] getBytes() {
		int size=obj.size();//加入当前map的第一层大小
		return addByteArray(getFlagHead(),tb.getBytes(size),getObjectBytes(),getFlagEnd());
	}
	
	@Override
	public byte[] getObjectBytes() {
		byte[] data=new byte[]{};
		Iterator<Entry<NType<?>, NType<?>>> it=obj.entrySet().iterator();
		//entrySet比keySet快,而且不易取到null的值
		while(it.hasNext())
		{
			Entry<NType<?>, NType<?>> kv=it.next();
			data=addByteArray(data,kv.getKey().getBytes(),kv.getValue().getBytes());
		}
		/**
		for(NType<?> key:obj.keySet())
		{//不管key和value是什么类型，只管取数据,如果类型是NMap，会自动嵌套调用它getBytes
			data=addByteArray(data,key.getBytes(),obj.get(key).getBytes());
		}
		 */
		return data;
	}
	
	@Override
	public String getString() {
		return obj.toString();
	}

	@Override
	public int size() {
		return obj.size();
	}

	@Override
	public boolean isEmpty() {
		return obj.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return obj.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return obj.containsValue(value);
	}

	@Override
	public NType<?> get(Object key) {
		return obj.get(key);
	}

	@Override
	public NType<?> put(NType<?> key, NType<?> value) {
		if(key==this || value==this)
		{
			throw new RuntimeException("不能将map自身作为key或者value");
		}
		return obj.put(key, value);
	}

	@Override
	public NType<?> remove(Object key) {
		return obj.remove(key);
	}

	@Override
	public void putAll(Map<? extends NType<?>, ? extends NType<?>> m) {
		obj.putAll(m);
	}

	@Override
	public void clear() {
		obj.clear();
	}

	@Override
	public Set<NType<?>> keySet() {
		return obj.keySet();
	}

	@Override
	public Collection<NType<?>> values() {
		return obj.values();
	}

	@Override
	public Set<java.util.Map.Entry<NType<?>, NType<?>>> entrySet() {
		return obj.entrySet();
	}

	private NType<?> readKey(TypeBytesInputStream ti) throws Exception
	{
		String index=Integer.toHexString(ti.getReadIndex());
		byte keyHead=ti.readNextByte();
		log(index+"-StartReadkey,flagHead:"+keyHead);
		NType<?> key=readNTypeByHeadsMap(ti, keyHead);
		if(key==null)
		{
			throw new RuntimeException("decoder key null atReadIndex:"+index);
		}
		log(Integer.toHexString(ti.getReadIndex())+"-ReadkeyEnd:"+key);
		return key;
	}
	private NType<?> readValue(TypeBytesInputStream ti) throws Exception
	{
		String index=Integer.toHexString(ti.getReadIndex());
		byte valueHead=ti.readNextByte();
		log(index+"-StartReadValue,flagHead:"+valueHead);
		NType<?> value=readNTypeByHeadsMap(ti, valueHead);
		if(value==null)
		{
			throw new RuntimeException("decoder value null atReadIndex:"+index);
		}
		log(Integer.toHexString(ti.getReadIndex())+"-ReadVauleEnd:"+value);
		return value;
	}
	/**
	 * 解析NType对象到map
	 * @param map
	 * @param ti
	 * @throws Exception 
	 */
	private void readNTypesToNMap(final NMap map,TypeBytesInputStream ti) throws Exception
	{
		int num=ti.readInt();
		for(int i=0;i<num;i++)
		{	
			map.put(readKey(ti), readValue(ti));
		}
	}
	
	/**
	 * 注册的NTYpe类型
	 */
	private final static HashMap<Byte, NType<?>> registHeads=new HashMap<Byte, NType<?>>();
	
	{	registNType(this);//默认注册自身类型
		registNType(new NNull());
		registNType(new NBoolean(true));
		registNType(new NBoolean(false));
		registNType(new NInteger(1111));
		registNType(new NUTF8String("UTF8"));
		registNType(new NUTF16LEString("UTF16L4"));
		registNType(new NByteArray(new byte[]{1,0,1,0}));
		//TODO
	}
	
	public final HashMap<Byte, NType<?>> getRegisgHeads()
	{
		return registHeads;
	}
	
	/**
	 * 注册NType类型 决定了该map能解码出哪些对象类型
	 * @param ntype
	 */
	public final static void registNType(NType<?> ntype)
	{
		if(ntype!=null)
		{
			registHeads.put(ntype.getFlagHead(), ntype);
		}
	}
	
	private NType<?> readNTypeByHeadsMap(TypeBytesInputStream ti,byte head) throws Exception
	{
		if(!registHeads.containsKey(head))
		{
			String index=Integer.toHexString(ti.getReadIndex());
			throw new RuntimeException("Known Head by readIndex:"+index);
		}
		return registHeads.get(head).decoderByStream(ti);
	}
	/**
	 * 根据头得到类型
	 * @param ti 这里的ti必须是没有读过head的
	 * @param head
	 * @return
	 * @throws IOException 
	 */
	protected NType<?> readNTypeByHead(TypeBytesInputStream ti,byte head) throws Exception
	{
		NType<?> type=null;
		switch (head) {
		case Flag.Head.NNull:
			type=new NNull().decoderByStream(ti);
			break;
		case Flag.Head.NTrue:
			type=new NBoolean(true).decoderByStream(ti);
			break;
		case Flag.Head.NFalse:
			type=new NBoolean(false).decoderByStream(ti);
			break;
		case Flag.Head.NInteger:
			type=new NInteger(0).decoderByStream(ti);
			break;
		case Flag.Head.NUTF8String:
			type=new NUTF8String("").decoderByStream(ti);
			break;
		case Flag.Head.NUTF16LEString:
			type=new NUTF16LEString("").decoderByStream(ti);
			break;
		case Flag.Head.NMap:
			type=new NMap().decoderByStream(ti);
			break;
		case Flag.Head.NByteArray:
			type=new NByteArray(new byte[]{}).decoderByStream(ti);
			break;
		default:
			break;
		}
		return type;
	}

	@Override
	protected NType<Map<NType<?>, NType<?>>> decoder(TypeBytesInputStream ti)throws Exception {
		if(checkHead(ti))
		{
			final NMap map=new NMap();
			readNTypesToNMap(map, ti);
			return map;
		}
		return null;
	}
	
	public final void saveTo(File file) throws IOException
	{
		FileOutputStream fos=new FileOutputStream(file);
		fos.write(getBytes());
		fos.flush();
		fos.close();
	}
	/**
	 * 根据文件解码一个新nmap对象
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public final NMap load(File file) throws Exception
	{
		if(file.exists())
		{
			FileInputStream fis=new FileInputStream(file);
			BufferedInputStream bis=new BufferedInputStream(fis);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();//定义一个内存输出流
			int i=-1;
			while(true)
			{
				i=bis.read();
				if(i==-1)
				{
					break;
				}
				bos.write(i);//保存到内存数组
			}
			bos.flush();
			bos.close();
			bis.close();
			return (NMap) decoder(bos.toByteArray());
		}
		return null;
	}
	
	public final NMapConvert mapConvert=new NMapConvert(this);
	
	/**
	 * 解码当前NMap为普通对象map
	 * 但是无法从map转换到nmap,比如string这种同类型多形式的
	 * @return
	 */
	public final TreeMap<Object, Object> toMap()
	{
		TreeMap<Object, Object> map=new TreeMap<Object, Object>();
		mapConvert.toTreeMap(this, map);
		return map;
	}
}
