/**
 * Copyright (c) 2011-2014, guaika (junchen1314@foxmail.com).
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
package xin.guaika.cloud.drools.model;

import java.io.Serializable;

/**
 * <P>
 * TODO
 * </P>
 * 
 * @author Wujun
 * @Data 2017年8月30日 上午10:26:06
 */
public class User implements Serializable{

	private static final long serialVersionUID = 9205098871660752398L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
