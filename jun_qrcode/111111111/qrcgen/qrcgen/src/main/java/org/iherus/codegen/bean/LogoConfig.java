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

public class LogoConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2922625672639593125L;

	private int ratio = Codectx.DEFAULT_LOGO_RATIO;

	private int arcWidth = Codectx.DEFAULT_LOGO_ARCWIDTH;

	private int arcHeight = Codectx.DEFAULT_LOGO_ARCWIDTH;

	private int borderSize = Codectx.DEFAULT_LOGO_BORDER_SIZE;

	private int padding = Codectx.DEFAULT_LOGO_PADDING;

	private String borderColor = Codectx.DEFAULT_LOGO_BORDER_COLOR;

	private String backgroundColor = Codectx.DEFAULT_LOGO_BACKGROUND_COLOR;

	private int margin = Codectx.DEFAULT_LOGO_MARGIN;

	private String panelColor = Codectx.DEFAULT_LOGO_PANEL_COLOR;

	private int panelArcWidth = Codectx.DEFAULT_LOGO_PANEL_RADIUS;

	private int panelArcHeight = Codectx.DEFAULT_LOGO_PANEL_RADIUS;

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public int getArcWidth() {
		return arcWidth;
	}

	public void setArcWidth(int arcWidth) {
		this.arcWidth = arcWidth;
	}

	public int getArcHeight() {
		return arcHeight;
	}

	public void setArcHeight(int arcHeight) {
		this.arcHeight = arcHeight;
	}

	public int getBorderSize() {
		return borderSize;
	}

	public void setBorderSize(int borderSize) {
		this.borderSize = borderSize;
	}

	public int getPadding() {
		return padding;
	}

	public void setPadding(int padding) {
		this.padding = padding;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getMargin() {
		return margin;
	}

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public String getPanelColor() {
		return panelColor;
	}

	public void setPanelColor(String panelColor) {
		this.panelColor = panelColor;
	}

	public int getPanelArcWidth() {
		return panelArcWidth;
	}

	public void setPanelArcWidth(int panelArcWidth) {
		this.panelArcWidth = panelArcWidth;
	}

	public int getPanelArcHeight() {
		return panelArcHeight;
	}

	public void setPanelArcHeight(int panelArcHeight) {
		this.panelArcHeight = panelArcHeight;
	}

}
