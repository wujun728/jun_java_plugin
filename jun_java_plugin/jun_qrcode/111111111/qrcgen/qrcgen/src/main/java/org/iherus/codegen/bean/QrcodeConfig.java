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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.iherus.codegen.bean.Codectx.BorderStyle;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrcodeConfig extends GenericCodeConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8107373062688869595L;

	private static ConcurrentHashMap<EncodeHintType, Object> hints = null;

	static {
		hints = new ConcurrentHashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, 0); // Redraw BitMatrix.
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	}

	private int margin = Codectx.DEFAULT_CODE_MARGIN;

	private int padding = Codectx.DEFAULT_CODE_PADDING;

	private int borderSize = Codectx.DEFAULT_CODE_BORDER_SIZE;

	private int borderRadius = Codectx.DEFAULT_CODE_BORDER_RADIUS;

	private String borderColor = Codectx.DEFAULT_CODE_BORDER_COLOR;

	private BorderStyle borderStyle = Codectx.BorderStyle.DASHED;

	private int borderDashGranularity = Codectx.DEFAULT_CODE_BORDER_DASH_GRANULARITY;

	private final LogoConfig logoConfig = new LogoConfig();

	public QrcodeConfig() {
		this(Codectx.DEFAULT_CODE_WIDTH, Codectx.DEFAULT_CODE_HEIGHT);
	}

	public QrcodeConfig(int width, int height) {
		super(width, height);
	}

	public int getPadding() {
		return padding;
	}

	public QrcodeConfig setPadding(int padding) {
		this.padding = padding;
		return this;
	}

	public int getBorderSize() {
		return borderSize;
	}

	public QrcodeConfig setBorderSize(int borderSize) {
		this.borderSize = borderSize;
		return this;
	}

	public int getBorderRadius() {
		return borderRadius;
	}

	public QrcodeConfig setBorderRadius(int borderRadius) {
		this.borderRadius = borderRadius;
		return this;
	}

	public BorderStyle getBorderStyle() {
		return borderStyle;
	}

	public QrcodeConfig setBorderStyle(BorderStyle style) {
		this.borderStyle = style;
		return this;
	}

	public int getBorderDashGranularity() {
		return borderDashGranularity;
	}

	/**
	 * This setting does not work, if the style is not BorderStyle.DASHED.
	 */
	public QrcodeConfig setBorderDashGranularity(int granularity) {
		this.borderDashGranularity = granularity;
		return this;
	}

	public LogoConfig getLogoConfig() {
		return logoConfig;
	}

	public int getMargin() {
		return margin;
	}

	public QrcodeConfig setMargin(int margin) {
		this.margin = margin;
		return this;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public QrcodeConfig setBorderColor(String borderColor) {
		this.borderColor = borderColor;
		return this;
	}

	@Override
	public QrcodeConfig setWidth(int width) {
		return (QrcodeConfig) super.setWidth(width);
	}

	@Override
	public QrcodeConfig setHeight(int height) {
		return (QrcodeConfig) super.setHeight(height);
	}

	@Override
	public QrcodeConfig setMasterColor(String masterColor) {
		return (QrcodeConfig) super.setMasterColor(masterColor);
	}

	@Override
	public QrcodeConfig setSlaveColor(String slaveColor) {
		return (QrcodeConfig) super.setSlaveColor(slaveColor);
	}

	public QrcodeConfig setLogoRatio(int ratio) {
		getLogoConfig().setRatio(ratio);
		return this;
	}

	public QrcodeConfig setLogoBorderSize(int borderSize) {
		getLogoConfig().setBorderSize(borderSize);
		return this;
	}

	public QrcodeConfig setLogoPadding(int padding) {
		getLogoConfig().setPadding(padding);
		return this;
	}

	public QrcodeConfig setLogoBorderColor(String borderColor) {
		getLogoConfig().setBorderColor(borderColor);
		return this;
	}

	public QrcodeConfig setLogoBackgroundColor(String backgroundColor) {
		getLogoConfig().setBackgroundColor(backgroundColor);
		return this;
	}

	public QrcodeConfig setLogoMargin(int margin) {
		getLogoConfig().setMargin(margin);
		return this;
	}

	public QrcodeConfig setLogoPanelArcWidth(int arcWidth) {
		getLogoConfig().setPanelArcWidth(arcWidth);
		return this;
	}

	public QrcodeConfig setLogoPanelArcHeight(int arcHeight) {
		getLogoConfig().setPanelArcHeight(arcHeight);
		return this;
	}

	public QrcodeConfig setPanelColor(String panelColor) {
		getLogoConfig().setPanelColor(panelColor);
		return this;
	}

	public Map<EncodeHintType, Object> getHints() {
		return hints;
	}

	public Map<EncodeHintType, Object> addHint(EncodeHintType type, Object value) {
		Map<EncodeHintType, Object> hints = getHints();
		hints.put(type, value);
		return hints;
	}

	public void setErrorCorrectionLevel(ErrorCorrectionLevel errorCorrectionLevel) {
		addHint(EncodeHintType.ERROR_CORRECTION, errorCorrectionLevel);
	}

}
