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

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class QrcodeInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9064635833671724433L;

	private BufferedImage image;

	private Logo logo;

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Logo getLogo() {
		return logo;
	}

	public void setLogo(Logo logo) {
		this.logo = logo;
	}

	public static final class Logo {

		private String path;

		private boolean remote;

		public Logo(String path, boolean remote) {
			this.path = path;
			this.remote = remote;
		}

		public String getPath() {
			return path;
		}

		public boolean isRemote() {
			return remote;
		}

	}

}
