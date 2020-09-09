package io.itit.itf.okhttp.interceptor;

/**
 * 
 * @author icecooly
 *
 */
public interface DownloadFileProgressListener {
	void updateProgress(long downloadLenth, long totalLength, boolean done);
}