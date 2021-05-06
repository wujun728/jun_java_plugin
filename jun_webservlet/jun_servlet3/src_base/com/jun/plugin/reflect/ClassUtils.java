package com.jun.plugin.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
/**
 * ���͹�����
 * @author lihuoming
 *
 */
public class ClassUtils {
	/**  
     * ͨ����,���ָ����ĸ���ķ��Ͳ����ʵ������. ��BuyerServiceBean extends DaoSupport<Buyer>  
     *  
     * @param clazz clazz ��Ҫ�������,�������̳з��͸���
     * @param index ���Ͳ�����������,��0��ʼ.  
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */  
    @SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz, int index) {    
        Type genType = clazz.getGenericSuperclass();//�õ����͸���  
        //���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�ֱ�ӷ���Object.class   
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;   
        }  
        //���ر�ʾ������ʵ�����Ͳ����Type���������,������ŵĶ��Ƕ�Ӧ���͵�Class, ��BuyerServiceBean extends DaoSupport<Buyer,Contact>�ͷ���Buyer��Contact����   
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();                   
        if (index >= params.length || index < 0) { 
        	 throw new RuntimeException("�����������"+ (index<0 ? "����С��0" : "�����˲��������"));
        }      
        if (!(params[index] instanceof Class)) {
            return Object.class;   
        }   
        return (Class) params[index];
    }
	/**  
     * ͨ����,���ָ����ĸ���ĵ�һ�����Ͳ����ʵ������. ��BuyerServiceBean extends DaoSupport<Buyer>  
     *  
     * @param clazz clazz ��Ҫ�������,�������̳з��͸���
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */  
    @SuppressWarnings("unchecked")
	public static Class getSuperClassGenricType(Class clazz) {
    	return getSuperClassGenricType(clazz,0);
    }
	/**  
     * ͨ����,��÷�������ֵ���Ͳ����ʵ������. ��: public Map<String, Buyer> getNames(){}
     *  
     * @param Method method ����
     * @param int index ���Ͳ�����������,��0��ʼ.
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */ 
    @SuppressWarnings("unchecked")
	public static Class getMethodGenericReturnType(Method method, int index) {
    	Type returnType = method.getGenericReturnType();
    	if(returnType instanceof ParameterizedType){
    	    ParameterizedType type = (ParameterizedType) returnType;
    	    Type[] typeArguments = type.getActualTypeArguments();
            if (index >= typeArguments.length || index < 0) { 
            	 throw new RuntimeException("�����������"+ (index<0 ? "����С��0" : "�����˲��������"));
            } 
    	    return (Class)typeArguments[index];
    	}
    	return Object.class;
    }
	/**  
     * ͨ����,��÷�������ֵ��һ�����Ͳ����ʵ������. ��: public Map<String, Buyer> getNames(){}
     *  
     * @param Method method ����
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */ 
    @SuppressWarnings("unchecked")
	public static Class getMethodGenericReturnType(Method method) {
    	return getMethodGenericReturnType(method, 0);
    }
    
	/**  
     * ͨ����,��÷�����������index�������������з��Ͳ����ʵ������. ��: public void add(Map<String, Buyer> maps, List<String> names){}
     *  
     * @param Method method ����
     * @param int index �ڼ����������
     * @return �������ķ��Ͳ����ʵ�����ͼ���, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ��ؿռ���
     */ 
    @SuppressWarnings("unchecked")
	public static List<Class> getMethodGenericParameterTypes(Method method, int index) {
    	List<Class> results = new ArrayList<Class>();
    	Type[] genericParameterTypes = method.getGenericParameterTypes();
    	if (index >= genericParameterTypes.length ||index < 0) {
             throw new RuntimeException("�����������"+ (index<0 ? "����С��0" : "�����˲��������"));
        } 
    	Type genericParameterType = genericParameterTypes[index];
    	if(genericParameterType instanceof ParameterizedType){
    	     ParameterizedType aType = (ParameterizedType) genericParameterType;
    	     Type[] parameterArgTypes = aType.getActualTypeArguments();
    	     for(Type parameterArgType : parameterArgTypes){
    	         Class parameterArgClass = (Class) parameterArgType;
    	         results.add(parameterArgClass);
    	     }
    	     return results;
    	}
    	return results;
    }
	/**  
     * ͨ����,��÷�����������һ�������������з��Ͳ����ʵ������. ��: public void add(Map<String, Buyer> maps, List<String> names){}
     *  
     * @param Method method ����
     * @return �������ķ��Ͳ����ʵ�����ͼ���, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ��ؿռ���
     */ 
    @SuppressWarnings("unchecked")
	public static List<Class> getMethodGenericParameterTypes(Method method) {
    	return getMethodGenericParameterTypes(method, 0);
    }
	/**  
     * ͨ����,���Field���Ͳ����ʵ������. ��: public Map<String, Buyer> names;
     *  
     * @param Field field �ֶ�
     * @param int index ���Ͳ�����������,��0��ʼ.
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */ 
    @SuppressWarnings("unchecked")
	public static Class getFieldGenericType(Field field, int index) {
    	Type genericFieldType = field.getGenericType();
    	
    	if(genericFieldType instanceof ParameterizedType){
    	    ParameterizedType aType = (ParameterizedType) genericFieldType;
    	    Type[] fieldArgTypes = aType.getActualTypeArguments();
    	    if (index >= fieldArgTypes.length || index < 0) { 
    	    	throw new RuntimeException("�����������"+ (index<0 ? "����С��0" : "�����˲��������"));
            } 
    	    return (Class)fieldArgTypes[index];
    	}
    	return Object.class;
    }
	/**  
     * ͨ����,���Field���Ͳ����ʵ������. ��: public Map<String, Buyer> names;
     *  
     * @param Field field �ֶ�
     * @param int index ���Ͳ�����������,��0��ʼ.
     * @return ���Ͳ����ʵ������, ���û��ʵ��ParameterizedType�ӿڣ�����֧�ַ��ͣ�����ֱ�ӷ���<code>Object.class</code>
     */ 
    @SuppressWarnings("unchecked")
	public static Class getFieldGenericType(Field field) {
    	return getFieldGenericType(field, 0);
    }
}
