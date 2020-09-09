package io.itit.itf.okhttp.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 
 * @author icecooly
 *
 */
public abstract class DownloadFileInterceptor implements Interceptor,DownloadFileProgressListener{
	//
	@Override
	public Response intercept(Chain chain) throws IOException {
		Response rsp = chain.proceed(chain.request());
	    return rsp.newBuilder()
	        .body(new DownloadFileProgressResponseBody(rsp.body(),this))
	        .build();
	}
	//
	public abstract void updateProgress(long downloadLenth, long totalLength, boolean isFinish);
	//
	public static class DownloadFileProgressResponseBody extends ResponseBody{
		private final ResponseBody body;
	    private final DownloadFileProgressListener progressListener;
	    private BufferedSource bufferedSource;
	    //
	    public DownloadFileProgressResponseBody(ResponseBody body, DownloadFileProgressListener progressListener) {
            this.body = body;
            this.progressListener = progressListener;
        }
	    
		@Override
		public MediaType contentType() {
			return body.contentType();
		}

		@Override
		public long contentLength() {
			return body.contentLength();
		}

		@Override
		public BufferedSource source() {
			if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(body.source()));
            }
            return bufferedSource;
		}
		
		private Source source(Source source) {
            return new ForwardingSource(source) {
                long downloadLenth = 0L;

                @Override public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    boolean isFinish=(bytesRead ==-1);
                    if(!isFinish){
                    	downloadLenth+=bytesRead;
                    }
                    progressListener.updateProgress(downloadLenth,body.contentLength(),isFinish);
                    return bytesRead;
                }
            };
        }
	}
}
