//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//
//public class SerializeUtil {
// public static void main(String [] args){
//  Jedis jedis = new Jedis("172.16.135.2");
//  String keys = "name";
//  //删数据
//  //jedis.del(keys);
//  //存数据
//  jedis.set(keys, "zy");
//  //取数据
//  String value = jedis.get(keys);
//System.out.println(value);
//  
//  //存对象
//  Person p=new Person(); //peson类记得实现序列化接口Serializable
//  p.setAge(20);
//  p.setName("姚波");
//  p.setId(1);
//  jedis.set("person".getBytes(), serialize(p));
//  byte[] byt=jedis.get("person".getBytes());
//  Object obj=unserizlize(byt);
//  if(obj instanceof Person){
//System.out.println(obj);
//  }
// }
// 
// //序列化
// public static byte [] serialize(Object obj){
//  ObjectOutputStream obi=null;
//  ByteArrayOutputStream bai=null;
//  try {
//   bai=new ByteArrayOutputStream();
//   obi=new ObjectOutputStream(bai);
//   obi.writeObject(obj);
//   byte[] byt=bai.toByteArray();
//   return byt;
//  } catch (IOException e) {
//   e.printStackTrace();
//  }
//  return null;
// }
// 
// //反序列化
// public static Object unserizlize(byte[] byt){
//  ObjectInputStream oii=null;
//  ByteArrayInputStream bis=null;
//  bis=new ByteArrayInputStream(byt);
//  try {
//   oii=new ObjectInputStream(bis);
//   Object obj=oii.readObject();
//   return obj;
//  } catch (Exception e) {
//   
//   e.printStackTrace();
//  }
// 
//  
//  return null;
// }
//}
