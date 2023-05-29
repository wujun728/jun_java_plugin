package net.jueb.util4j.beta.serializable.official;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

public class SerializableInterface implements Serializable{ 
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -6544992372489659391L;
	public SerializableInterface(){ 
	
    } 
    
    /**
     * 对象序列化
     * @param 实现了Serializable的对象
     * @return
     */
    public static byte[] serialize(Object obj)
    {   
    	if(obj instanceof Serializable)
    	{
    	 	try {
    	 		 ByteArrayOutputStream mem_out = new ByteArrayOutputStream(); 
                 ObjectOutputStream out = new ObjectOutputStream(mem_out); 
                 out.writeObject(obj); 
                 out.close(); 
                 mem_out.close(); 
                 byte[] bytes =  mem_out.toByteArray(); 
                 return bytes; 
			} catch (Exception e) {
				return null;
			}
    	}else
    	{
    		return null;
    	}
    } 
    /**
     * 对象反序列化
     * @param bytes
     * @return 失败返回null
     */
    public static Object deserialize(byte[] bytes){ 
        try { 
            ByteArrayInputStream mem_in = new ByteArrayInputStream(bytes); 
            ObjectInputStream in = new ObjectInputStream(mem_in); 
            Object obj = in.readObject(); 
            in.close(); 
            mem_in.close(); 
            return obj; 
        } catch (StreamCorruptedException e) { 
            return null; 
        } catch (ClassNotFoundException e) { 
            return null; 
        }   catch (IOException e) { 
            return null; 
        } 
     } 
} 