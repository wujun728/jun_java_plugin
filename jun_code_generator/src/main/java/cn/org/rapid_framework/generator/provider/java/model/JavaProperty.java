package cn.org.rapid_framework.generator.provider.java.model;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import cn.org.rapid_framework.generator.util.typemapping.ActionScriptDataTypesUtils;
import cn.org.rapid_framework.generator.util.typemapping.JavaPrimitiveTypeMapping;

public class JavaProperty {
	PropertyDescriptor propertyDescriptor;
	JavaClass clazz; //与property相关联的class
	public JavaProperty(PropertyDescriptor pd, JavaClass javaClass) {
		this.propertyDescriptor = pd;
		this.clazz = javaClass;
	}
	
	public String getName() {
		return propertyDescriptor.getName();
	}
	
	public String getJavaType() {
		return propertyDescriptor.getPropertyType().getName();
	}
	
	public String getPrimitiveJavaType() {
	    return JavaPrimitiveTypeMapping.getPrimitiveType(getJavaType());
	}
	
	public JavaClass getPropertyType() {
		return new JavaClass(propertyDescriptor.getPropertyType());
	}

	public String getDisplayName() {
		return propertyDescriptor.getDisplayName();
	}
	
	public JavaMethod getReadMethod() {
		return new JavaMethod(propertyDescriptor.getReadMethod(),clazz);
	}

	public JavaMethod getWriteMethod() {
		return new JavaMethod(propertyDescriptor.getWriteMethod(),clazz);
	}
	
	public boolean isHasReadMethod() {
	    return propertyDescriptor.getReadMethod() != null;
	}

	public boolean isHasWriteMethod() {
        return propertyDescriptor.getWriteMethod() != null;
    }
	
	public String getAsType() {
		return ActionScriptDataTypesUtils.getPreferredAsType(propertyDescriptor.getPropertyType().getName());
	}
	
	public boolean isPk() {
	    return JPAUtils.isPk(propertyDescriptor.getReadMethod());
	}
	
	public JavaClass getClazz() {
		return clazz;
	}

	public String toString() {
		return "JavaClass:"+clazz+" JavaProperty:"+getName();
	}
	
    public static class JPAUtils {
        private static boolean isJPAClassAvaiable = false;
        static {
            try {
                Class.forName("javax.persistence.Table");
                isJPAClassAvaiable = true;
            } catch (ClassNotFoundException e) {
            }
        }

        public static boolean isPk(Method readMethod) {
            if (isJPAClassAvaiable) {
                if (readMethod != null && readMethod.isAnnotationPresent(classForName("javax.persistence.Id"))) {
                    return true;
                }
            }
            return false;
        }

        private static Class classForName(String clazz)  {
            try {
                return Class.forName(clazz);
            } catch (ClassNotFoundException e) {
                return null;
            }
        }
    }
}
