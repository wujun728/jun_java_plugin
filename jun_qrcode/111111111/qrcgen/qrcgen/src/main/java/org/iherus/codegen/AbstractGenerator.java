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
package org.iherus.codegen;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import org.iherus.codegen.bean.Codectx.BorderStyle;

import com.google.zxing.common.BitMatrix;

/**
 * AbstractGenerator
 *
 * @author Bosco.Liao
 * @since 1.0.0
 */
public abstract class AbstractGenerator {

	/**
	 * Translate the color from hex color code.
	 * <p>Usage: getColor("#FFFFFF") or getColor("FFFFFF").</p>
	 * 
	 * @param hexColor
	 * @return An instance of color.
	 */
	public static final Color getColor(final String hexColor) {
		String hexc = hexColor;
		Color result = Color.WHITE;
		if (hexc != null && !"".equals(hexc)) {
			if (hexc.startsWith("#")) {
				hexc = hexc.replaceFirst("#", "");
			}
			result = new Color(Integer.parseInt(hexc, 16));
		}
		return result;
	}

	/**
	 * In order to completely eliminate the white edges, it must be redraw
	 * BitMatrix, determined by the parameter 'margin'.
	 * 
	 * @param bitMatrix An instance of BitMatrix.
	 * @param padding
	 * @return A BitMatrix instance after redrawing.
	 */
	public static BitMatrix redrawBitMatrix(BitMatrix bitMatrix, final int padding) {
		int tempPadding = padding * 2;
		/**
		 * [left,top,width,height]
		 */
		final int[] attributes = bitMatrix.getEnclosingRectangle();
		int rawWidth = attributes[2] + tempPadding;
		int rawHeight = attributes[3] + tempPadding;
		BitMatrix redrawBM = new BitMatrix(rawWidth, rawHeight);
		redrawBM.clear();
		/**
		 * redraw start
		 */
		int left = attributes[0], top = attributes[1];
		for (int x = padding; x < rawWidth - padding; x++) {
			for (int y = padding; y < rawHeight - padding; y++) {
				if (bitMatrix.get(x + left - padding, y + top - padding)) {
					redrawBM.set(x, y);
				}
			}
		}
		return redrawBM;
	}

	/**
	 * Sets the border-radius attribute to BufferedImage.
	 * 
	 * @param image An instance of BufferedImage.
	 * @param radius Attribute value of border-radius.
	 * @param borderSize
	 * @param borderColor
	 * @param style BorderStyle.SOLID || BorderStyle.DASHED
	 * @param borderDashGranularity
	 * @param margin
	 * @return BufferedImage instance with cliping.
	 */
	public static BufferedImage setRadius(BufferedImage image, int radius, int borderSize, String borderColor,
			BorderStyle style, int borderDashGranularity, int margin) {
		
		int width = image.getWidth(), height = image.getHeight();
		int canvasWidth = width + margin * 2, canvasHeight = height + margin * 2;
		BufferedImage canvasImage = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);

		Graphics2D graphics = canvasImage.createGraphics();
		graphics.setComposite(AlphaComposite.Src);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(Color.WHITE);
		graphics.fill(new RoundRectangle2D.Float(0, 0, canvasWidth, canvasHeight, radius, radius));
		graphics.setComposite(AlphaComposite.SrcAtop);
		graphics.drawImage(clip(image, radius), margin, margin, null);

		if (borderSize > 0) {
			Stroke stroke;
			if (BorderStyle.SOLID == style) {
				stroke = new BasicStroke(borderSize);
			} else {
				stroke = new BasicStroke(borderSize, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f,
						new float[] { borderDashGranularity, borderDashGranularity }, 0.0f);
			}
			graphics.setColor(getColor(borderColor));
			graphics.setStroke(stroke);
			graphics.drawRoundRect(margin, margin, width - 1, height - 1, radius, radius);
		}

		graphics.dispose();
		image.flush();

		return canvasImage;
	}
	
	public static BufferedImage clip(final BufferedImage image, final int radius) {
		
		int width = image.getWidth(), height = image.getHeight();
		BufferedImage canvasImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D graphics = canvasImage.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setClip(new RoundRectangle2D.Double(0, 0, width, height, radius, radius));
		graphics.drawImage(image, 0, 0, null);
		
		graphics.dispose();
		image.flush();
		
		return canvasImage;
	}

}
