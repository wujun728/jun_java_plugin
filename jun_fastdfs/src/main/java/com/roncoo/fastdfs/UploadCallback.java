/**
 * Copyright (C) 2008 Happy Fish / YuQing
 * <p>
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL).
 * Please visit the FastDFS Home Page https://github.com/happyfish100/fastdfs for more detail.
 */

package com.roncoo.fastdfs;

import java.io.IOException;
import java.io.OutputStream;

/**
 * upload file callback interface
 *
 * @author Happy Fish / YuQing
 * @version Version 1.0
 */
public interface UploadCallback {
	/**
	 * 
	 * send file content callback function, be called only once when the file uploaded
	 *
	 * @param out output stream for writing file content
	 * @return 0 success, return none zero(errno) if fail
	 * @throws IOException 异常
	 */
	public int send(OutputStream out) throws IOException;
}
