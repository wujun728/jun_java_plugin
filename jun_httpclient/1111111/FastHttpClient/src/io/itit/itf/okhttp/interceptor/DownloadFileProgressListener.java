package io.itit.itf.okhttp.interceptor;

/**
 * 
 * @author Wujun
 *
 */
public interface DownloadFileProgressListener {
	void updateProgress(long downloadLenth, long totalLength, boolean done);
}