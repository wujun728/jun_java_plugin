package cn.org.rapid_framework.generator.provider.db.sql.model;

import cn.org.rapid_framework.generator.provider.db.table.model.Column;
import cn.org.rapid_framework.generator.util.BeanHelper;
import cn.org.rapid_framework.generator.util.StringHelper;

public class SqlParameter extends Column {
    	String parameterClass;
//    	int parameterMode;
//    	int parameterType;
//    	String parameterTypeName;
//    	int precision; //no use
//    	int scale; //no use
    	String paramName;
    	boolean isListParam = false;
    	
    	public SqlParameter() {}

        public SqlParameter(Column param) {
        	super(param);
            BeanHelper.copyProperties(this, param);
        }
        
    	public SqlParameter(SqlParameter param) {
    	    super(param);
    		this.isListParam = param.isListParam;
    		this.paramName = param.paramName;
    		
//    		this.parameterClassName = param.parameterClassName;
//    		this.parameterMode = param.parameterMode;
//    		this.parameterType = param.parameterType;
//    		this.parameterTypeName = param.parameterTypeName;
//    		this.precision = param.precision;
//    		this.scale = param.scale;
    	}
    	
//    	public SqlParameter(ParameterMetaData m,int i) throws SQLException {
//    		this.parameterClassName = m.getParameterClassName(i);
//    		this.parameterMode = m.getParameterMode(i);
//    		this.parameterType = m.getParameterType(i);
//    		this.parameterTypeName = m.getParameterTypeName(i);
//    		this.precision = m.getPrecision(i);
//    		this.scale = m.getScale(i);
//    	}

		public String getParameterClass() {
		    if(StringHelper.isNotBlank(parameterClass)) return parameterClass;
		    return getPossibleShortJavaType();
        }
		
        public void setParameterClass(String parameterClass) {
            this.parameterClass = parameterClass;
        }

        public String getPreferredParameterJavaType() {
		    return toListParam(getParameterClass());
		}
        
		private String toListParam(String parameterClassName) {
			if(isListParam) {
				if(parameterClassName.indexOf("[]") >= 0){
					return parameterClassName;
				}
				if(parameterClassName.indexOf("List") >= 0){
					return parameterClassName;
				}
				if(parameterClassName.indexOf("Set") >= 0){
					return parameterClassName;
				}
				return "java.util.List<"+parameterClassName+">";
			}else {
				return parameterClassName;
			}
		}
//		public int getParameterMode() {
//			return parameterMode;
//		}
//		public void setParameterMode(int parameterMode) {
//			this.parameterMode = parameterMode;
//		}
//		public int getParameterType() {
//			return parameterType;
//		}
//		public void setParameterType(int parameterType) {
//			this.parameterType = parameterType;
//		}
//		public String getParameterTypeName() {
//			return parameterTypeName;
//		}
//		public void setParameterTypeName(String parameterTypeName) {
//			this.parameterTypeName = parameterTypeName;
//		}
//		public int getPrecision() {
//			return precision;
//		}
//		public void setPrecision(int precision) {
//			this.precision = precision;
//		}
//		public int getScale() {
//			return scale;
//		}
//		public void setScale(int scale) {
//			this.scale = scale;
//		}
		/**
		 * 参数名称
		 */
		public String getParamName() {
			return paramName;
		}
		public void setParamName(String paramName) {
			this.paramName = paramName;
		}
		/**
		 * 是否是列表参数,主要是in语句,如 username in (:usernames) 则为true, username = :username则false
		 * @return
		 */
		public boolean isListParam() {
			return isListParam;
		}
		public void setListParam(boolean isListParam) {
			this.isListParam = isListParam;
		}
		
		public boolean equals(Object obj) {
			if(obj == this) return true;
			if(obj == null) return false;
			if(obj instanceof SqlParameter) {
				SqlParameter other = (SqlParameter)obj;
				return paramName.equals(other.getParamName());
			}else {
				return false;
			}
		}
		public int hashCode() {
			return paramName.hashCode();
		}
		public String toString() {
			return "paramName:"+paramName+" preferredParameterJavaType:"+getPreferredParameterJavaType();
		}
    }