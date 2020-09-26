					
					package com.cn.dq.utils;
					
					import java.lang.reflect.Field;
					
					/**
					 * 获取相应的SQL语句
					 * @author dengqin
					 *
					 */
					public class GetSQL {
					
						
						/**
						 * 得到删除语句
						 * @param c
						 * @param params
						 * @return
						 */
						@SuppressWarnings("rawtypes")
						public static  String getDeleteSQL(Class c,Object[] params){
								StringBuilder s =new StringBuilder("delete from "+c.getSimpleName().toLowerCase());
								if(params.length!=0){
										String q=" where "+c.getDeclaredFields()[0].getName()+"=?";
										s.append(q);
										return s.toString();
								}
								return s.toString();
						}
						
						
						/**
						 * 得到查询语句
						 * @param tbName
						 * @param params
						 * @param className
						 * @return sql
						 */
						@SuppressWarnings("rawtypes")
						public static String getQuerySQL(Class c,Object[] params){
								String sql="select  *  from"+" "+c.getSimpleName().toLowerCase();
								
									if(params.length==0){//如果参数为空，即查询所有
											return sql;
									}
									if(params.length==1){//一个参数
												 sql=sql+" "+"where id=?";
											}else{//多个参数
												sql=sql+" "+"where ";
											for(int i=0;i<params.length;i++){
													sql=sql+ "id=? and";
												}
											}
									return sql;
						}
						
						
						/**
						 * 传入表名和参数动态生成插入语句
						 * @param tbName
						 * @param params
						 * @return sql
						 */
						@SuppressWarnings("rawtypes")
						public static String getInsertSQL(Class c,Object[] params){
							StringBuilder s= new StringBuilder("insert into"+" "+ c.getSimpleName().toLowerCase() +" "+" values(" );
								String q="?";
								for(int i=1;i<params.length;i++){
										q=q+",?";
								}
								System.out.println("q="+q);
								s.append(q+")");
								System.out.println("s="+s);
								return s.toString();
						}
						
						
						
						/**
						 * 把实体类名、要修改的表名和参数传入方法中动态生成修改语句
						 * @param className
						 * @param tbName
						 * @param values
						 * @return  sql
						 * @throws Exception
						 */
						@SuppressWarnings("rawtypes")
						public static String getUpdateSQL(Class c,Object[] values) throws Exception{
					
							//通过反射得到类的属性信息
								Field[] field=c.getDeclaredFields();
							//field[i].getName()得到属性名
								StringBuilder s=new StringBuilder("update"+" "+c.getSimpleName().toLowerCase()+" "+"set");
								String q=" ";
								for(int i=1;i<field.length;i++){
									if(i<values.length-1)
										q=q+field[i].getName()+"=?,";
									else
										q=q+field[i].getName()+"=?";
								}
								System.out.println(q);
								String l=" "+"where"+" "+field[0].getName()+"=?";
								s.append(q+l);
								System.out.println(s);
								return s.toString();
						}
					}
