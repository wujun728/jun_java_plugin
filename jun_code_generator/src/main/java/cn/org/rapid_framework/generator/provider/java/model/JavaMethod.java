/**
 * project:pomer
 * 
 * Copyright 2008 [pomer], Inc. All rights reserved.
 * Website: http://www.pomer.org.cn/
 * 
 */
package cn.org.rapid_framework.generator.provider.java.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import cn.org.rapid_framework.generator.util.StringHelper;
import cn.org.rapid_framework.generator.util.typemapping.JavaImport;

/**
 * 
 * @author badqiu,Linlin Yu
 */
public class JavaMethod {
	Method method;
	private JavaClass clazz; //与method相关联的class
	
	
	public JavaMethod(Method method, JavaClass clazz) {
		super();
		if(method == null) throw new IllegalArgumentException("method must be not null");
		if(clazz == null) throw new IllegalArgumentException("clazz must be not null");
		this.method = method;
		this.clazz = clazz;
	}

	public JavaClass getClazz() {
		return clazz;
	}

	public String getMethodName() {
		return method.getName();
	}

	public JavaClass getReturnType() {
		return new JavaClass(method.getReturnType());
	}
	
	public Annotation[] getAnnotations() {
		return method.getAnnotations();
	}

	public boolean isBridge() {
		return method.isBridge();
	}

    public List<JavaClass> getExceptionTypes() {
        List<JavaClass> result = new ArrayList();
        for(Class c : method.getExceptionTypes()) {
            result.add(new JavaClass(c));
        }
        return result;
    }

    public boolean isSynthetic() {
		return method.isSynthetic();
	}

	public boolean isVarArgs() {
		return method.isVarArgs();
	}

	public Set<JavaClass> getImportClasses() {
		Set<JavaClass> set = new LinkedHashSet<JavaClass>();
        JavaImport.addImportClass(set,method.getParameterTypes());
        JavaImport.addImportClass(set,method.getExceptionTypes());
        JavaImport.addImportClass(set, method.getReturnType());
        return set;
	}
	
	public List<MethodParameter> getParameters() {
		Class[] parameters  = method.getParameterTypes();
		List<MethodParameter> results = new ArrayList<MethodParameter>();
		for(int i = 0; i < parameters.length; i++) {
			results.add(new MethodParameter(i+1,this,new JavaClass(parameters[i])));
		}
		return results;
	}
	
	public String getMethodNameUpper() {
		return StringHelper.capitalize(getMethodName());
	}
	
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((method == null) ? 0 : method.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JavaMethod other = (JavaMethod) obj;
        if (method == null) {
            if (other.method != null)
                return false;
        } else if (!method.equals(other.method))
            return false;
        return true;
    }
    
    public boolean isPropertyMethod() {
    	if(getMethodName().startsWith("set") || getMethodName().startsWith("get") || (getMethodName().startsWith("is") && getReturnType().isBooleanType())) {
    		return true;
    	}
    	return false;
    }

    public String toString() {
		return "JavaClass:"+clazz+" JavaMethod:"+getMethodName();
	}
}
