		
		package com.cn.dq.utils;
		
		import java.lang.reflect.Method;
		import java.sql.Connection;
		import java.sql.PreparedStatement;
		import java.sql.ResultSet;
		import java.sql.ResultSetMetaData;
		import java.sql.SQLException;
		import java.util.ArrayList;
		import java.util.List;
		import com.cn.bean.User;
		import org.junit.Test;
		
		/**
		 * 
		 * @author dengqin
		 *
		 */
		public class DBOperate {
			
					static Connection conn=null;
					static PreparedStatement pstmt=null;
					Object[] params={};
					
					
					@Test
					public void test(){
						try {
						//List  list=getResult(User.class,params);
							System.out.println(getResult(User.class,params));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					/**
					 * 返回查询结果信息集合
					 * @param t
					 * @param params
					 * @return
					 * @throws Exception
					 */
					
					public  List<Object>  getResult(Class<?> t,Object[] params) throws Exception{
								ResultSet rs =Query(t,params);
								List<Object> list=new ArrayList<Object>();
								Method[] method=t.getMethods();//取得bean类的所有方法
								Object obj=null;
									while(rs.next()){
										obj=t.newInstance();//获取bean的实例对象
										setValues(obj, method, rs);//通过反射把bean的set方法把值设到bean对象中
										list.add(obj);
									}
										return list;
			        	 }
				
					/**
					 * 向bean中设置值
					 * @param obj
					 * @param rsmd
					 * @param method
					 * @param rs
					 */
				public void setValues(Object obj ,	Method[] method,ResultSet rs){
				
					try {
						ResultSetMetaData rsmd =rs.getMetaData();// 获取此 ResultSet 对象的列的编号、类型和属性的对象
							for(int i=1;i<=rsmd.getColumnCount();i++){
								String colname=rsmd.getColumnName(i);//获取当前列名
								String setName="set"+colname;//set的方法名
								Method setmethod =null;
								for(int j=0;j<method.length;j++){
											if(method[j].getName().equalsIgnoreCase(setName)){//比较是否存在与set方法相同的
													setName=method[j].getName();//得到set方法名
													setmethod=method[j];//得到set方法
												System.out.println(rs.getObject(colname));
												Object value=rs.getObject(colname);//获取当前位置列的值
													if(value==null){
														continue;
													}
												    // 将结果的值设到bean中
							                        try {
							                            //  利用反射获取对象
							                            // JavaBean内部属性和ResultSet中一致时候
							                            setmethod.invoke(obj, value);
							                            break;
							                        } catch (Exception e) {
							                            // JavaBean内部属性和ResultSet中不一致时候，使用String来输入值。
							                            e.printStackTrace();
							                            setmethod.invoke(obj, value.toString());
							                        }
										 }
								}
							}
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
					
					/**
					 * 查询信息,返回结果集
					 * @param c
					 * @param params
					 * @return
					 * @throws SQLException
		
					 */
					@SuppressWarnings("rawtypes")
					public static ResultSet Query(Class c,Object[] params) throws SQLException{
								String sql=GetSQL.getQuerySQL(c,params);
											conn=JdbcUtils.getconnection();
											pstmt=conn.prepareStatement(sql);
											pstmtSetValue(params);
											ResultSet rs = pstmt.executeQuery();
										return rs;
					} 
					
					/**
					 * 插入数据
					 * @param c
					 * @param values
					 * @throws SQLException
					 */
					@SuppressWarnings("rawtypes")
					public void insert(Class c,Object[] values) throws SQLException{
								String sql=GetSQL.getInsertSQL(c,values).toString();
								conn=JdbcUtils.getconnection();
								pstmt=conn.prepareStatement(sql);
								pstmtSetValue(values);
								pstmt.executeUpdate();
					}
					
					
					/**
					 * 修改信息
					 * @param c
					 * @param values
					 * @throws Exception
					 */
					@SuppressWarnings("rawtypes")
					public void update(Class c,Object[] values) throws Exception{
						String sql=GetSQL.getUpdateSQL(c,values).toString();
							conn =JdbcUtils.getconnection();
							pstmt=conn.prepareStatement(sql);
							pstmtSetValue(values);
							pstmt.executeUpdate();
					} 
					
					/**
					 * 删除信息
					 * @param c
					 * @param params
					 * @throws SQLException
					 */
					@SuppressWarnings("rawtypes")
					public void delete(Class c,Object[] params) throws SQLException{
							String sql=GetSQL.getDeleteSQL(c, params);
							conn=JdbcUtils.getconnection();
							pstmt=conn.prepareStatement(sql);
							pstmtSetValue(params);
							pstmt.executeUpdate();
					}

					
					/**
					 * 向pstmt里面设值
					 * @param values
					 * @throws SQLException
					 */
					public static void pstmtSetValue(Object[] values) throws SQLException{
								for( int i=1;i<=values.length;i++){
										pstmt.setObject(i,values[i-1]);
								}
					}
		}
