/*
 * Copyright (c) 2009 Piotr Piastucki
 * 
 * This file is part of Patchca CAPTCHA library.
 * 
 *  Patchca is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  Patchca is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with Patchca. If not, see <http://www.gnu.org/licenses/>.
 */
package org.springrain.frame.util.patchca.service;

import java.awt.Color;

import org.springrain.frame.util.patchca.background.SingleColorBackgroundFactory;
import org.springrain.frame.util.patchca.color.SingleColorFactory;
import org.springrain.frame.util.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.springrain.frame.util.patchca.font.RandomFontFactory;
import org.springrain.frame.util.patchca.text.renderer.BestFitTextRenderer;
import org.springrain.frame.util.patchca.word.AdaptiveRandomWordFactory;

public class ConfigurableCaptchaService extends AbstractCaptchaService {

	public ConfigurableCaptchaService() {
		backgroundFactory = new SingleColorBackgroundFactory();
		//backgroundFactory = new GradientBackgroundFactory();
		
		
		wordFactory = new AdaptiveRandomWordFactory();
		fontFactory = new RandomFontFactory();
		textRenderer = new BestFitTextRenderer();
		colorFactory = new SingleColorFactory();
		filterFactory = new CurvesRippleFilterFactory(colorFactory);
		textRenderer.setLeftMargin(3);
		textRenderer.setRightMargin(3);
		textRenderer.setBottomMargin(3);
		textRenderer.setTopMargin(3);
		width = 100;
		height = 40;
		
		 setColorFactory(new SingleColorFactory(Color.BLACK));
		 setFilterFactory(new CurvesRippleFilterFactory(colorFactory));
	}

}
