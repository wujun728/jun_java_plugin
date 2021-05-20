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

import org.iherus.codegen.Generator;
import org.iherus.codegen.bean.QrcodeConfig;

public interface QrcodeGenerator extends Generator {

	QrcodeConfig getQrcodeConfig();

	QrcodeGenerator generate(String content, String logoPath);

	QrcodeGenerator setLogo(String path, boolean remote);

	BufferedImage getImage(boolean clear);

	default QrcodeGenerator setLogo(String path) {
		return setLogo(path, false);
	}

	default QrcodeGenerator setRemoteLogo(String path) {
		return setLogo(path, true);
	}

	@Override
	default BufferedImage getImage() {
		return getImage(true);
	}

}
