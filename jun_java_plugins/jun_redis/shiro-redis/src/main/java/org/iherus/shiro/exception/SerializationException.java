/**
 * Copyright (c) 2016-~, Bosco.Liao (bosco_liao@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.iherus.shiro.exception;

/**
 * <p>序列化异常类</p>
 * <p>Description:供序列化工具使用.</p>
 * @author Wujun
 * @version 1.0.0
 * @date 2016年4月10日-下午10:49:36
 */
public class SerializationException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4301915165433653517L;


    public SerializationException() {
        super();
    }

    public SerializationException(final String message) {
        super(message);
    }


    public SerializationException(final Throwable cause) {
        super(cause);
    }

    public SerializationException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
