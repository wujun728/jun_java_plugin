package com.jun.plugin.reflect;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class ReflectUtil {
 
	/**
	 * 通过Beaninfo获取一个类的所有setXxx|getXxx|isXxx方法 
	 */
	@Test
	public void beaninfo() throws Exception{
		BeanInfo info =  Introspector.getBeanInfo(Object.class);
		PropertyDescriptor[] pd = info.getPropertyDescriptors();
		for(PropertyDescriptor p:pd){
			String name = p.getName();
			System.err.println(name);
			Method m =  p.getWriteMethod();//setXxx(....);
			Class<?>[] cls = m.getParameterTypes();
			System.err.println(cls.length);
		}
	}
public static void main111(String[] args) throws Exception {
		
//		HelloWorld hello = new HelloWorld();
		
		//获得字节码对象在内存中的表示
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld"); //HelloWorld
		//获得私有的构造，HelloWorld(String str,Integer i)  形参
		Constructor cons = clazz.getDeclaredConstructor(String.class,Integer.class);
		
		//java.lang.IllegalAccessException
		//System.out.println(cons.isAccessible());
		//强制设置访问权限
		cons.setAccessible(true);
		
		//实例化 new  实参
		Object obj = cons.newInstance("admin",12334);
		
	}
	
	//获得共有的构造方法，并执行
	public void demo11() throws Exception {
		
//		HelloWorld hello = new HelloWorld();
		
		//获得字节码对象在内存中的表示
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld"); //HelloWorld
		//获得默认构造 HelloWorld()
		Constructor cons = clazz.getConstructor();  //HelloWorld()
		//获得实例
		Object obj = cons.newInstance(); // new HelloWorld();
		System.out.println(obj);
	}

	public static void main1(String[] args) throws Exception {
//		HelloWorld h = new HelloWorld();
//		String s = h.name;
		// h.name = xxxx;
		//字节码
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld"); //HelloWorld
		//快捷方法  获得默认构造的实例
//		clazz.getConstructor().newInstance();
		Object obj = clazz.newInstance(); //obj -- h
		
		//获得私有字段private Integer age
		Field field = clazz.getDeclaredField("age");
		//强制设置访问权限
		field.setAccessible(true);
		//关联 obj.age
		Object value = field.get(obj);
		System.out.println(value);
		
		//设置值 obj.age = 67
		field.set(obj, 67);
		//再出输出
		value = field.get(obj);
		System.out.println(value);
		
	}
	
	public void demo1() throws Exception {
//		HelloWorld h = new HelloWorld();
//		String s = h.name;
		//字节码
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld"); //HelloWorld
		//快捷方法  获得默认构造的实例
//		clazz.getConstructor().newInstance();
		Object obj = clazz.newInstance(); //obj -- h
		
		//获得字段 String name =
		Field field = clazz.getField("name");  // name
		//关联 obj.name
		Object value = field.get(obj);
		System.out.println(value);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before method invoke");
		Object returnValue = method.invoke(target, args);
		System.out.println("after method invoke");
		return returnValue;
	}
	 
	private Object target;//闭包所依赖的对象  
    
    private String methodName;//闭包方法的名字  
      
    public ReflectUtil(){}  
  
    public ReflectUtil(Object target, String methodName) {  
        super();  
        this.target = target;  
        this.methodName = methodName;  
    }  
  
    public Object invoke(Object... objects) {  
        Class clazz = target.getClass();  
        try {  
            Method[] ms = clazz.getDeclaredMethods();  
              
            Method targetMethod = null;  
            for(Method m : ms){  
                if(methodName.equals(m.getName())){  
                    targetMethod = m;  
                    break;  
                }  
            }  
            targetMethod.setAccessible(true);  
            return targetMethod.invoke(target, objects);  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            throw new RuntimeException(e);  
        }   
    }  
      
    public void rebund(Object anothertarget){  
        target = anothertarget;  
    }  
    
	public static void main11(String[] args) throws Exception {
		
//		HelloWorld h = new HelloWorld();
//		h.print();
		
		//获得class
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld");
		
		//实例化
		Object obj = clazz.newInstance();
		
		//获得方法 print(String str , int i)   //绕过自动装箱
		Method method = clazz.getDeclaredMethod("print", String.class,int.class);
		//强制设置访问权限
		method.setAccessible(true);
		
		//绑定,指定当前的方法，并将相应的实际参数传递
		method.invoke(obj, "rooot",250);
		
		
		
	}
	
	public void demo() throws Exception {
		

		//获得class
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld");
		
		//实例化
		Object obj = clazz.newInstance();
		
		//获得方法  print()
		Method method = clazz.getMethod("print", null);
		
		//绑定,指定当前的方法，并将相应的实际参数传递
		method.invoke(obj, null);
		
	}
	
	
public static Car initCarParameter() throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException{
		
		Class clazz = Car.class;
		//Class clazz1 = Class.forName("com.test.reflection.test.Car");
		Constructor cars  = clazz.getDeclaredConstructor((Class[])null);
		Car car = (Car)cars.newInstance();
		
		Method setType = clazz.getDeclaredMethod("setType", String.class) ;
		setType.invoke(car, "A388");
		
		Method setColor = clazz.getDeclaredMethod("setColor", String.class);
		setColor.invoke(car, "黑色");
		
		Method setMaxSpeed = clazz.getDeclaredMethod("setMaxSpeed", int.class);
		setMaxSpeed.invoke(car, 200);
		return car;
		
	}
	public static void main(String[] args) throws SecurityException, IllegalArgumentException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Car car  = ReflectUtil.initCarParameter();
		car.introduce();
	}
}


class Car {

	private String type;
	private String color;
	private int maxSpeed;

	public Car() {
		super();
	}

	public Car(String type, String color, int maxSpeed) {
		super();
		this.type = type;
		this.color = color;
		this.maxSpeed = maxSpeed;
	}

	public void introduce() {
		System.out.println("型号：" + type + "颜色：" + color + "最大时速：" + maxSpeed);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

}