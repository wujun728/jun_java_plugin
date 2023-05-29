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
package org.iherus.shiro.tester;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class MD5Generator {

	@Test
	public void generatorMD5() {

		final String source = "123456"; // 明文密码

		final String salt = "tester"; // 盐值

		final int hashIterations = 2; // 散列次数，例如：2 等于 MD5(md5())

		Md5Hash md5Hash = new Md5Hash(source, salt, hashIterations);

		// 上述的底层实现如下：
		// SimpleHash sh = new SimpleHash("MD5", source, salt, hashIterations);
		
		System.out.println(md5Hash.toString());

	}

}
