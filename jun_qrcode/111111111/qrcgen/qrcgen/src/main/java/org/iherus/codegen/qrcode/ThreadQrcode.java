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
package org.iherus.codegen.qrcode;

import java.awt.image.BufferedImage;

import org.iherus.codegen.bean.QrcodeInfo;
import org.iherus.codegen.bean.QrcodeInfo.Logo;

public class ThreadQrcode extends ThreadLocal<QrcodeInfo> {

	@Override
	protected QrcodeInfo initialValue() {
		return new QrcodeInfo();
	}

	public void setLogo(String path, boolean remote) {
		get().setLogo(new Logo(path, remote));
	}

	public void setImage(BufferedImage image) {
		get().setImage(image);
	}

	public BufferedImage getImage() {
		return get().getImage();
	}

	public Logo getLogo() {
		return get().getLogo();
	}

}
