package org.frameworkset.spi.remote.http;
/**
 * Copyright 2020 bboss
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2020</p>
 * @Date 2020/2/27 15:40
 * @author Wujun
 * @version 1.0
 */
public abstract class BaseURLResponseHandler<T> implements URLResponseHandler<T> {
	protected String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
//	protected RuntimeException throwException1(int status, HttpEntity entity) throws IOException {
//		if (entity != null ) {
//			return new HttpProxyRequestException(new StringBuilder().append("Request url:").append(url).append("\r\n").append(EntityUtils.toString(entity)).toString());
//		}
//		else{
//			return new HttpProxyRequestException(new StringBuilder().append("Request url:").append(url).append(",Unexpected response status: ").append( status).toString());
//		}
//	}
}
