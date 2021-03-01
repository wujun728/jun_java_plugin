package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import swift.net.lfs.LFSByteArray;
import swift.net.lfs.LFSConnection;

public class TestAPI {

	static private String FILE_NAME = "Music/Music";

	public TestAPI()
	{
		testAPI();
		testData();

		selectByIndex("习惯了自己过");
		select("习惯了自己过");
	}

	private void testAPI()
	{
		System.out.println("\n TestAPI");
		Socket con = LFSConnection.getConnection();
		LFSByteArray lb = new LFSByteArray();

		try {
			lb.setStatement(""
					+ "local time = clock();"

					//print 只有在服务器开启打印模式的时候才有效
					+ "print(\"\\n*** test vars ***\");"
					+ "print(\"收到的字节长度：\", vars.messageLength);"
					+ "local varsLength = vars.varsLength;"
					+ "print(\"变量数组的长度：\", varsLength);"

					+ "print(\"当前的状态：\", vars.state);"
					+ "setState(1);"
					+ "print(\"当前的状态：\", vars.state);"

					+ "for i = 0, varsLength - 1 do "
					+ "	print(\"索引为 \" .. i .. \" 的长度和内容：\", getVarsLength(i), vars[i]);"
					+ "	putString(vars[i], -1);"
					+ "end;"

					+ "putInt(123);"
					+ "putLong(567);"
					+ "putFloat(123.123);"
					+ "putDouble(567.567);"
					+ "putNull();"
					+ "putString(\"来一碗星星\");"

					+ "print(\"返回的数据大小：\", getMessageLength(true));"
					+ "print(\"服务器的字节序：\", (swap16(1) == 1) and \"Big Endian\" or \"Little Endian\");"
					+ "print(\"下面用一个自定义的对象来拷贝其他的内容\");"
					+ "print(\"但是不建议这么用，变量从发送端就准备好会比较好。一般情况下，语句写的比较好是不需要使用新的变量的\");"
					+ "local obj = newObject(getVarsLength(0) + 1);"
					+ "setObject(obj, 0, vars[0], String, -1);"
					+ "print(\"转换成字符窜：\", cast(obj, String));"
					+ "clearObject(obj);"

					+ "clock(1, time);"
					+ "print(\"脚本用时（毫秒）：\", diffClock(time) / 1000000);"

					+ "function __exit() "
					+ "	print(\"测试 exit 函数\");"
					+ "	exit();"
					+ "end;"

					+ "__exit();"

					+ "print(\"这里不会执行了\");"
					+ "");

			lb.putString("习惯了自己过");
			lb.putString("我要夏天");
			lb.putString("Bad Blood");

			lb.writeTo(con);

			for (int i = 0, l = lb.getVarsLength(); i < l; i++) {
				System.out.println(i + ": " + lb.getLength(i) + "  " + lb.get(i));
			}

			System.out.println("全部用时：" + lb.getTotalTimeMillis());
			System.out.println("服务用时：" + lb.getTimeStringMillis());
		} catch (Exception e) {
			LFSConnection.close(con);
			e.printStackTrace();
		}
		lb.clear();
		LFSConnection.put(con);
	}

	private void testData()
	{
		System.out.println("\n TestData");
		Socket con = LFSConnection.getConnection();
		LFSByteArray lb = new LFSByteArray();

		try {
			lb.setStatement(""
					//data_open(fileName:String):int;
					//成功返回 1
					+ "local r = data_open(vars[0]);"

					+ "if (r == 1) then "
					+ "	local len = vars.varsLength - 1;"
					+ "	local id;"
					+ "	local obj;"
					//写“唯一索引”的工具（是线程安全的）
					//使用唯一索引，其实就是使用 key value
					+ "	local iw = vars.indexWrite;"
					+ "	for i = 1, len do "
					+ "		obj = vars[i];"
					//设置需要写入的文件内容
					+ "		iw.buffer = obj;"
					//设置索引的 key，此处以文件内容本身作为 key
					+ "		iw:setIndex(obj);"
					//执行写入，如果已经存在该索引，则从索引处取出“文件 ID”（注意：不是索引 ID，是存储实际文件内容的 ID）
					+ "		id = iw:write();"
					+ "		putInt(id);"
					+ "	end;"
//					** 注释掉的“唯一索引”只能支持单线程，所以更安全的是 IndexWrite，当需要写索引时应使用 IndexWrite
//					** 这里的应废弃
//					+ "	for i = 1, len do "
//					//		先查找索引
//					+ "		id = data_read_index(obj, -1);"
//					+ "		if (id < 0) then "
//					//			写入文件
//					//			第一个参数为什么是 -1，因为这是追加写入一个新文件，非负数表示覆盖写入指定的文件
//					//			size 为 -1 表示的是最大值，和实际大小取最小值
//					//			data_write(file:long, buffer:Object, size:long):long
//					+ "			id = data_write(-1, obj, -1);"
//					//			构建索引
//					+ "			data_write_index(id, obj, -1);"
//					+ "		end;"
//					//		返回文件 ID
//					+ "		putInt(id);"
//					+ "	end;"
					+ "end;"
					+ "");

			lb.putString(FILE_NAME);
			lb.putString("习惯了自己过");
			lb.putString("我要夏天");
			lb.putString("Bad Blood");

			lb.writeTo(con);

			for (int i = 0, l = lb.getVarsLength(); i < l; i++) {
				System.out.println("FILE ID: " + lb.get(i));
			}

			System.out.println("全部用时：" + lb.getTotalTimeMillis());
			System.out.println("服务用时：" + lb.getTimeStringMillis());
		} catch (Exception e) {
			LFSConnection.close(con);
			e.printStackTrace();
		}
		lb.clear();
		LFSConnection.put(con);
	}

	private void selectByIndex(String value)
	{
		selectByIndex(value, FILE_NAME);
	}

	private void selectByIndex(String value, String fileName)
	{
		System.out.println("\n SelectByIndex");
		Socket con = LFSConnection.getConnection();
		LFSByteArray lb = new LFSByteArray();

		try {
			lb.setStatement(""
					+ "local r = data_open(vars[0]);"
					+ "if (r == 1) then "
					+ "	local id = data_read_index(vars[1], -1);"
					+ "	putLong(id);"
					+ "	if (id >= 0) then "
					//根据 id 读取文件内容
					+ "		data_read(id);"
					//读取的内容在 vars.dataData 内，建议将 vars.dataData 赋值给一个变量，避免多一次访问属性
					+ "		putString(vars.dataData);"
					+ "	else "
					+ "		putNull();"
					+ "	end;"
					//返回全部文件个数（包含已经删除的个数）
					+ "	putInt(getTotalFile(vars.dataFile));"
					+ "end;"
					+ "");

			lb.putString(fileName);
			lb.putString(value);

			lb.writeTo(con);

			System.out.println("FILE: " + lb.getLong(0) + " " + lb.get(1));
			System.out.println("TotalFile: " + lb.get(2));
			System.out.println("TIME: " + lb.getTotalTimeMillis() + " " + lb.getTimeStringMillis());
		} catch (Exception e) {
			LFSConnection.close(con);
			e.printStackTrace();
		}
		lb.clear();
		LFSConnection.put(con);
	}

	private void select(String value)
	{
		select(value, FILE_NAME);
	}

	private void select(String value, String fileName)
	{
		System.out.println("\n Select");
		Socket con = LFSConnection.getConnection();
		LFSByteArray lb = new LFSByteArray();

		try {
			lb.setStatement(""
					+ "local r = data_open(vars[0]);"
					+ "if (r == 1) then "
					+ "	local id = data_select(vars[1], -1);"
					+ "	putLong(id);"
					+ "	if (id >= 0) then "
					//根据 id 读取文件内容
					+ "		data_read(id);"
					//读取的内容在 vars.dataData 内，建议将 vars.dataData 赋值给一个变量，避免多一次访问属性
					+ "		putString(vars.dataData);"
					+ "	else "
					+ "		putNull();"
					+ "	end;"
					//返回全部文件个数（包含已经删除的个数）
					+ "	putInt(getTotalFile(vars.dataFile));"
					+ "end;"
					+ "");

			lb.putString(FILE_NAME);
			lb.putString(value);

			lb.writeTo(con);

			System.out.println("FILE: " + lb.getLong(0) + " " + lb.get(1));
			System.out.println("TotalFile: " + lb.get(2));
			System.out.println("TIME: " + lb.getTotalTimeMillis() + " " + lb.getTimeStringMillis());
		} catch (Exception e) {
			LFSConnection.close(con);
			e.printStackTrace();
		}
		lb.clear();
		LFSConnection.put(con);
	}

	private void addAllWords()
	{
		System.out.println("\n AddAllWords");
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("WordDict.txt");
		if (is == null){
			System.out.println("Main Dictionary not found!!!");
		}

		Socket con = LFSConnection.getConnection();
		LFSByteArray lb = new LFSByteArray();

		try {
			lb.setStatement(""
					+ "local r = data_open(vars[0]);"
					+ "if (r == 1) then "
					+ "	if (getTotalFile(vars.dataFile) == 0) then "
					//第一行写入空，使 id 从 1 开始
					+ "		data_write(0, nil, 0);"
					+ "	end;"
					+ "	local wordsLength = vars.varsLength - 1;"
					+ "	local id;"
					+ "	local obj;"
					+ "	local iw = vars.indexWrite;"
					+ "	for i = 1, wordsLength do "
					+ "		obj = vars[i];"
					+ "		iw.buffer = obj;"
					+ "		iw:setIndex(obj);"
					+ "		id = iw:write();"
					+ "		putInt(id);"
					+ "	end;"
					+ "end;"
					+ "");

			lb.putString("WordDict/WordDict");

			BufferedReader br = new BufferedReader(new InputStreamReader(is , "UTF-8"), 512);
			String theWord = null;
			do {
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim())) {
					theWord = theWord.trim().toLowerCase();
					lb.putString(theWord);
				}
			} while (theWord != null);

			lb.writeTo(con);

			//for (int i = 0, l = lb.getVarsLength(); i < l; i++) {
			//	System.out.println(lb.get(i));
			//}

			System.out.println("TIME: " + lb.getTotalTimeMillis());
			System.out.println("TIME: " + lb.getTimeStringMillis());

		} catch (Exception ioe) {
			LFSConnection.close(con);
			System.err.println("Main Dictionary loading exception.");
			ioe.printStackTrace();

		} finally {
			try {
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		lb.clear();
		LFSConnection.put(con);
	}

	private void loadAllWords()
	{
		Socket con = LFSConnection.getConnection();
		LFSByteArray lb = new LFSByteArray();

		try {
			lb.setStatement(""
					+ "local r = data_open(vars[0]);"
					+ "if (r == 1) then "
					+ "	local dataData = vars.dataData;"
					+ "	local wordsLength = getTotalFile(vars.dataFile);"
					+ "	for i = 0, wordsLength do "
					+ "		r = data_read(i);"
					+ "		if (r >= 0) then "
					+ "			putInt(i);"
					+ "			putString(dataData);"
					+ "		end;"
					+ "	end;"
					+ "end;"
					+ "");

			lb.putString("WordDict/WordDict");

			lb.writeTo(con);

			//String theWord = null;
			//for (int i = 0, l = lb.getVarsLength(); i < l; i += 2) {
			//	theWord = lb.getString(i + 1);
			//	theWord = theWord.trim().toLowerCase();
			//	System.out.println(lb.getInt(i) + " " + theWord);
			//}

			System.out.println("TIME: " + lb.getTotalTimeMillis());
			System.out.println("TIME: " + lb.getTimeStringMillis());
		} catch (Exception ioe) {
			LFSConnection.close(con);
			System.err.println("Main Dictionary loading exception.");
			ioe.printStackTrace();
		}
		lb.clear();
		LFSConnection.put(con);
	}

}