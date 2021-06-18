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
package org.iherus.codegen.bean;

import java.io.Serializable;

public abstract class GenericCodeConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 95683283981376217L;

	private int width;

	private int height;

	private String masterColor = Codectx.DEFAULT_CODE_MASTER_COLOR;

	private String slaveColor = Codectx.DEFAULT_CODE_SLAVE_COLOR;

	public GenericCodeConfig(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public GenericCodeConfig(int width, int height, String masterColor, String slaveColor) {
		this(width, height);
		this.masterColor = masterColor;
		this.slaveColor = slaveColor;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getMasterColor() {
		return masterColor;
	}

	public String getSlaveColor() {
		return slaveColor;
	}

	public GenericCodeConfig setWidth(int width) {
		this.width = width;
		return this;
	}

	public GenericCodeConfig setHeight(int height) {
		this.height = height;
		return this;
	}

	public GenericCodeConfig setSlaveColor(String slaveColor) {
		this.slaveColor = slaveColor;
		return this;
	}

	public GenericCodeConfig setMasterColor(String masterColor) {
		this.masterColor = masterColor;
		return this;
	}

}
