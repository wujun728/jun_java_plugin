/**
 * IK 中文分词  版本 6.5.0
 * IK Analyzer release 6.5.0
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * 
 * 
 */
package org.springrain.frame.util.IK.lucene;

import java.io.Reader;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.util.IOUtils;


/**
 * IK分词器，Lucene Analyzer接口实现
 * 兼容Lucene 6.5.0版本
 */
public final class IKAnalyzer extends Analyzer{
	

	/**
	 * IK分词器Lucene  Analyzer接口实现类
	 * 
	 * 默认细粒度切分算法
	 */
	public IKAnalyzer(){
	    super();
	}
	
	
	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		 Reader reader=null;
	        try{
	            reader=new StringReader(fieldName);
	            IKTokenizer it = new IKTokenizer(reader);
	            return new Analyzer.TokenStreamComponents(it);
	        }finally {
	            IOUtils.closeWhileHandlingException(reader);
	        }
	}

}
