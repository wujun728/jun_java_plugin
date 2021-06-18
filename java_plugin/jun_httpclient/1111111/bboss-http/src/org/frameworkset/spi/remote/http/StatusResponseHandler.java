package org.frameworkset.spi.remote.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.frameworkset.spi.remote.http.proxy.HttpProxyRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class StatusResponseHandler {
	private static Logger _logger =  LoggerFactory.getLogger(StatusResponseHandler.class);
	protected int reponseStatus;
	public int getReponseStatus() {
		return reponseStatus;
	}

	public void setReponseStatus(int reponseStatus) {
		this.reponseStatus = reponseStatus;
	}

	protected int initStatus(HttpResponse response){
		reponseStatus = response.getStatusLine().getStatusCode();
		return reponseStatus;
	}
	protected String url;

	public void setUrl(String url) {
		this.url = url;
	}
	protected RuntimeException throwException(int status, HttpEntity entity) throws IOException {

		if (entity != null ) {
			if(_logger.isDebugEnabled()) {
				_logger.debug(new StringBuilder().append("Request url:").append(url).append(",status:").append(status).toString());
			}
			return new HttpProxyRequestException(EntityUtils.toString(entity));
		}
		else
			return new HttpProxyRequestException(new StringBuilder().append("Request url:").append(url).append(",Unexpected response status: ").append( status).toString());
	}
}
