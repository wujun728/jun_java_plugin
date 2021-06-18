/*package com.jd.ssh.sshdemo.lucene;

import java.beans.Statement;
import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.util.Version;

import com.mysql.jdbc.Connection;

public class AdvanceSearchService {

	 
	 public List<IndexRecord> search(QueryConditionQuestion qcq) {
	  //测试的时候使用，真正调用时jsonstr是传递过来的
	  String jsonstr="{end:\"40\",isDynsort:\"0\",metadbId:\"402881be3387158c01338716928d0000\",start:\"0\","
	            +"queryConditions:[{queryGroups:[" +
	              "{queryType:\"range\",phraseSlop:\"5\",stypeIds:[\"pubtime\"]," +
	              "keyWords:[\"2010\",\"2011\"],operator:\"or\",rankWeight:\"1\"," +
	              "groupId:\"1\",groupOperator:\"and\"}," +
	              "{queryType:\"range\",phraseSlop:\"5\",stypeIds:[\"pubtime\"]," +
	              "keyWords:[\"2006\",\"2008\"],groupOperator:\"and\",operator:\"or\",groupId:\"1\"," +
	              "}]}," +
	              "{queryGroups:[" +
	              "{queryType:\"wildcard\",phraseSlop:\"5\",stypeIds:[\"title\",\"lib\"]," +
	              "keyWords:[\"人?\"],operator:\"or\",rankWeight:\"1\"," +
	              "groupId:\"1\",groupOperator:\"and\"}," +
	              "{queryType:\"fuzzy\",phraseSlop:\"5\",stypeIds:[\"title\",\"lib\"]," +
	              "keyWords:[\"test\"],operator:\"or\",rankWeight:\"1\"," +
	              "groupId:\"1\",groupOperator:\"not\"}"+
	              "]}" +
	              "]}";
	 // QueryConditionQuestion qcq=JsonstrToQueryConditonQuestion.getQueryConditionQuestion(jsonstr);
	  int start=qcq.getStart();
	  int end=qcq.getEnd();
	  List<IndexRecord> recordsList = new ArrayList<IndexRecord>();
	  MssMetadbInfo mssMetadbInfo = mssMetadbInfoDAO.getMssMetadbInfoByid(qcq.getMetadbId());
	  List<MssMetadbStruct> listStruct = mssMetadbStructDAO.getMssMetadbStruct(qcq.getMetadbId()); 
	  String indexPath = mssMetadbInfo.getIndexpath();
	  

	  
	  
	  //拼接查询字符串
	  Map<Integer,QueryConditionVO[]> vos=qcq.getQueryConditions();
	  Similarity similarity = new IKSimilarity();
	  IndexSearcher isearcher = null;
	  Directory directory = null;
	  File file = null;
	  TopDocs topDocs = null;
	  try {
	   file = new File(indexPath);
	   directory = NIOFSDirectory.open(file);
	   isearcher = new IndexSearcher(IndexReader.open(directory));
	   isearcher.setSimilarity(similarity);
	   
	   topDocs = isearcher.search(getAdvanceQuery(vos), isearcher.maxDoc());
	      start--;// 调用方传入的参数从1开始，所以相应的数组下标应该 -1
	   if (start < 0)
	    start = 0;
	   int flag = start;
	   if (topDocs.totalHits > 0) {
	    ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	    if (scoreDocs.length > 0) {
	     IndexField indexField = null;
	     for (int j = start; j < scoreDocs.length; j++) {
	      // 一次循环生成一个indexRecord
	      IndexRecord indexRecord = new IndexRecord();
	      indexRecord.setTotalHits(topDocs.totalHits);
	      indexRecord.setScore((scoreDocs[j].score) * 100);// 分数

	      List<IndexField> listIndexField = new ArrayList<IndexField>();

	      // 添加固有字段 字段id
	      Document targetDoc = isearcher.doc(scoreDocs[j].doc);
	      indexField = new IndexField();
	      indexField.setName(Consts.INDEXFIELD_ID);
	      indexField.setText(targetDoc.get(Consts.INDEXFIELD_ID));
	      listIndexField.add(indexField);

	      indexField = new IndexField();
	      indexField.setName(Consts.INDEXFIELD_ORIGID);
	      indexField.setText(targetDoc.get(Consts.INDEXFIELD_ORIGID));
	      listIndexField.add(indexField);

	      indexField = new IndexField();
	      indexField.setName(Consts.INDEXFIELD_INTIME);
	      indexField.setText(targetDoc.get(Consts.INDEXFIELD_INTIME));
	      listIndexField.add(indexField);

	      indexField = new IndexField();
	      indexField.setName(Consts.INDEXFIELD_TABLE_ID);
	      indexField.setText(targetDoc.get(Consts.INDEXFIELD_TABLE_ID));
	      listIndexField.add(indexField);

	      indexField = new IndexField();
	      indexField.setName(Consts.INDEXFIELD_METADBID);
	      indexField.setText(targetDoc.get(Consts.INDEXFIELD_METADBID));
	      listIndexField.add(indexField);

	      indexField = new IndexField();
	      indexField.setName(Consts.INDEXFIELD_STORE_SERVERID);
	      indexField.setText(targetDoc.get(Consts.INDEXFIELD_STORE_SERVERID));
	      listIndexField.add(indexField);

	      indexField = new IndexField();
	      indexField.setName(Consts.INDEXFIELD_TBLNAME);
	      indexField.setText(targetDoc.get(Consts.INDEXFIELD_TBLNAME));
	      listIndexField.add(indexField);
	      
	    
	      indexField = new IndexField();
	      indexField.setName(Consts.INDEXFIELD_DOCNUM);
	      indexField.setText(String.valueOf(scoreDocs[j].doc));
	      listIndexField.add(indexField);

	      // 根据listStruct添加其他字段
	      for (MssMetadbStruct mms : listStruct) {
	       indexField = new IndexField();
	       indexField.setName(mms.getStcode());
	       indexField.setText(targetDoc.get(mms.getStcode()));
	       listIndexField.add(indexField);
	      }

	      // 添加推荐次数，评分次数，分数总和，指定的排名顺序
	      IndexField[] docf = new IndexField[listIndexField.size()];
	      int i = 0;
	      for (IndexField a : listIndexField) {
	       docf[i++] = a;
	      }
	      indexRecord.setDocfields(docf);
	      // 添加Record对象的id
	      indexRecord.setId(targetDoc.get(Consts.INDEXFIELD_ID));

	      MssStoreServer mss = mssStoreServerDAO.getMssStoreServer(targetDoc.get(Consts.INDEXFIELD_STORE_SERVERID));
	      Connection conn = null;
	      ResultSet resultSet = null;
	      Statement statement = null;
	      try {
	       conn = ConnectionUtil.getConnection(mss);
	       String sql = "select * from " + targetDoc.get(Consts.INDEXFIELD_TBLNAME) + " where id = '" + targetDoc.get(Consts.INDEXFIELD_ID) + "'";
	       statement = conn.createStatement();
	       resultSet = statement.executeQuery(sql);
	       while (resultSet.next()) {
	        indexRecord.setRankcount(resultSet.getLong("rankcount")); // 添加评分次数
	        indexRecord.setRanksum(resultSet.getLong("ranksum"));// 添加分数总和
	        indexRecord.setUpcount(resultSet.getLong("upcount"));// 添加推荐次数
	        indexRecord.setCustidx(resultSet.getLong("custidx"));// 指定的排名顺序
	       }

	      } catch (Exception ex) {
	       ex.printStackTrace();
	      } finally {
	       ConnectionUtil.closeResultSet(resultSet);
	       ConnectionUtil.closeStatement(statement);
	       ConnectionUtil.closeConnection(conn);
	      }
	      if (null != indexRecord)
	       recordsList.add(indexRecord);
	      flag++;
	      if (flag >= end)
	       break;
	     }
	    }
	   }
	   //防止反编译用
	   if (654789 == new Random().nextInt()){
	           throw new Exception("try again 654789 == new Random().nextInt()");
	    }
	  } catch (Exception e) {
	   e.printStackTrace();
	   System.out.println("*******SearchService search方法查询索引报错 ********");
	  } finally {
	   try {
	    if (isearcher != null) {
	     isearcher.close();
	    }
	    if (directory != null) {
	     directory.close();
	    }
	    //防止反编译用
	    if (654789 == new Random().nextInt()){
	            throw new Exception("try again 654789 == new Random().nextInt()");
	     }
	   } catch (Exception e) {
	    System.out.println("*******SearchService isearcher,directory关闭报错 ********");
	   }

	  }
	  return recordsList;
	 }
	 
	 
	 *//**
	  * 得到组合查询
	  *//*
	 public Query getAdvanceQuery(Map<Integer,QueryConditionVO[]> vos){
	  BooleanQuery bQueryAll=null;
	  Query query = null;
	  if(vos==null){
	   //return recordsList;
	  }else{
	   try{
	    QueryConditionVO[] queryConditions=null;
	    QueryConditionVO vo=null;
	    bQueryAll=new BooleanQuery();
	    BooleanQuery bQueryEvery=null;
	    for(int i=0;i<vos.size();i++){//所有组查询
	     queryConditions=vos.get(i);//具体某一组查询
	     bQueryEvery=new BooleanQuery(); 
	     for(int j=0;j<queryConditions.length;j++){//每一组查询内部拼接条件
	      vo=queryConditions[j];//下一个vo
	      query=getTermQuery(vo);//获得具体的某个查询对象
	      if(queryConditions.length>1){//分组内部组查询组合
	       if(vo.getOperator().equalsIgnoreCase("AND")){
	        bQueryEvery.add(query,Occur.MUST);
	       }else if(vo.getOperator().equalsIgnoreCase("NOT")){
	        bQueryEvery.add(query,Occur.MUST_NOT);
	       }else if(vo.getOperator().equalsIgnoreCase("OR")){
	        bQueryEvery.add(query,Occur.SHOULD);
	       }
	      }  
	  
	     }
	     //添加每一个分组的查询条件组合成组合查询条件，最外层外部组合查询条件
	     if(queryConditions.length>1){
	      if(vo.getGroupOperator().equalsIgnoreCase("AND")){
	       bQueryAll.add(bQueryEvery,Occur.MUST);
	      }else if(vo.getGroupOperator().equalsIgnoreCase("NOT")){
	       bQueryAll.add(bQueryEvery,Occur.MUST_NOT);
	      }else if(vo.getGroupOperator().equalsIgnoreCase("OR")){
	       bQueryAll.add(bQueryEvery,Occur.SHOULD);
	      }
	     }else{
	      if(vo.getGroupOperator().equalsIgnoreCase("AND")){
	       bQueryAll.add(query,Occur.MUST);
	      }else if(vo.getGroupOperator().equalsIgnoreCase("NOT")){
	       bQueryAll.add(query,Occur.MUST_NOT);
	      }else if(vo.getGroupOperator().equalsIgnoreCase("OR")){
	       bQueryAll.add(query,Occur.SHOULD);
	      }
	     }
	    }
	   }catch(Exception e){
	    e.printStackTrace();
	   }finally{
	    try{
	     //防止反编译用
	     if (654789 == new Random().nextInt()){
	            throw new Exception("try again 654789 == new Random().nextInt()");
	     }
	    }catch(Exception e){
	     e.printStackTrace();
	    }
	    
	   }
	 
	  }
	  System.out.println("bqueryall="+bQueryAll.toString());
	  return bQueryAll;
	 }
	 
	 *//**
	  * 
	  * @param vo
	  * @return
	  *//*

	 public Query getTermQuery(QueryConditionVO vo){
	  Query query=null;
	  QueryParser queryParser=null;
	  try{
	   if(vo.getQueryType().equalsIgnoreCase(Consts.QUERYTYPE_CONTAINS) 
	     || vo.getQueryType().equalsIgnoreCase(Consts.QUERYTYPE_CONTAINSNONE)
	     || vo.getQueryType().equalsIgnoreCase(Consts.QUERYTYPE_CONTAINSALL)){//包含以下关键词
	       queryParser=new MultiFieldQueryParser(Version.LUCENE_33,vo.getStypeIds(),new IKAnalyzer());
	     
	    if(vo.getQueryType().equalsIgnoreCase(Consts.QUERYTYPE_CONTAINSNONE)){//不包括此关键词
	     vo.setOperator("not");//设置内部操作关系为must_not类型,不满足此条件
	    }else if(vo.getQueryType().equalsIgnoreCase(Consts.QUERYTYPE_CONTAINSALL)){
	     queryParser.setDefaultOperator(Operator.AND);//关键字经过IKAnalyzer分词后仍然为并且的关系
	    }
	    queryParser.setPhraseSlop(vo.getPhraseSlop());
	    try {
	     
	      query=queryParser.parse(vo.getKeyWords()[0]);
	      
	    } catch (ParseException e) {
	     e.printStackTrace();
	    }
	   }else if(vo.getQueryType().equalsIgnoreCase(Consts.QUERYTYPE_RANGE)){//范围查询
	    
	    query=new TermRangeQuery(vo.getStypeIds()[0],vo.getKeyWords()[0], vo.getKeyWords()[1], true, true);
	    
	   }else if(vo.getQueryType().equalsIgnoreCase(Consts.QUERYTYPE_FUZZY)){//模糊查询
	    
	    query=new FuzzyQuery(new Term(vo.getStypeIds()[0],vo.getKeyWords()[0]));
	    
	   }else if(vo.getQueryType().equalsIgnoreCase(Consts.QUERYTYPE_WILDCARD)){//通配符查询
	    query=new WildcardQuery(new Term(vo.getStypeIds()[0],vo.getKeyWords()[0])); 
	   }
	   query.setBoost(vo.getRankWeight());//设置权重，改变得分情况，原有得分乘以设定的值为改变后的查询结果得分情况,默认设置为1
	   //防止反编译用
	   if (654789 == new Random().nextInt()){
	          throw new Exception("try again 654789 == new Random().nextInt()");
	   }
	  }catch(Exception e){
	   e.printStackTrace();
	  }finally{
	   try{
	    //防止反编译用
	    if (654789 == new Random().nextInt()){
	           throw new Exception("try again 654789 == new Random().nextInt()");
	    }
	   }catch(Exception e){
	    e.printStackTrace();
	   }
	   
	  }
	  
	  return query;
	 }
	 
	}*/