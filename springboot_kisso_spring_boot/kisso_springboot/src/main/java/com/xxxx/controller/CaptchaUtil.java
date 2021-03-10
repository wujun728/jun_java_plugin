package com.xxxx.controller;

/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
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
import java.awt.Color;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.kisso.common.captcha.background.LineNoiseBackgroundFactory;
import com.baomidou.kisso.common.captcha.color.SingleColorFactory;
import com.baomidou.kisso.common.captcha.filter.ConfigurableFilterFactory;
import com.baomidou.kisso.common.captcha.filter.library.AbstractImageOp;
import com.baomidou.kisso.common.captcha.filter.library.WobbleImageOp;
import com.baomidou.kisso.common.captcha.font.RandomFontFactory;
import com.baomidou.kisso.common.captcha.service.ConfigurableCaptchaService;
import com.baomidou.kisso.common.captcha.utils.encoder.EncoderHelper;

/**
 * 验证码工具类
 */
public class CaptchaUtil {

	public static String outputImage(OutputStream out) throws IOException {
		ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
		//验证码宽高
		cs.setWidth(85);
		cs.setHeight(35);
		
		//设置 6 位自适应验证码
//		AdaptiveRandomWordFactory arw = new AdaptiveRandomWordFactory();
//		arw.setMinLength(6);
//		arw.setMaxLength(6);
//		cs.setWordFactory(arw);
		
		//字符大小设置
		RandomFontFactory rf = new RandomFontFactory();
		rf.setMinSize(25);
		rf.setMaxSize(28);
		cs.setFontFactory(rf);
		
		//文本渲染
//		cs.setTextRenderer(new RandomYBestFitTextRenderer());
		
		//设置一个单一颜色字体
		cs.setColorFactory(new SingleColorFactory(new Color(59, 162, 9)));
//		cs.setFilterFactory(new CurvesRippleFilterFactory(cs.getColorFactory()));

		
		//图片滤镜设置
		ConfigurableFilterFactory filterFactory = new ConfigurableFilterFactory();
		List<BufferedImageOp> filters = new ArrayList<BufferedImageOp>();
		
		//摆动干扰
		WobbleImageOp wio = new WobbleImageOp();
		wio.setEdgeMode(AbstractImageOp.EDGE_CLAMP);
		wio.setxAmplitude(2.0);
		wio.setyAmplitude(1.0);
		filters.add(wio);

		//曲线干扰
//		CurvesImageOp cio = new CurvesImageOp();
//		cio.setColorFactory(new SingleColorFactory(new Color(59, 162, 9)));
//		cio.setEdgeMode(AbstractImageOp.EDGE_ZERO);
//		cio.setStrokeMax(0.3f);
//		cio.setStrokeMin(0.1f);
//		filters.add(cio);
		
		filterFactory.setFilters(filters);
		cs.setFilterFactory(filterFactory);
		
		//椭圆形干扰背景
//		cs.setBackgroundFactory(new OvalNoiseBackgroundFactory(7));
		
		//线形干扰背景
		cs.setBackgroundFactory(new LineNoiseBackgroundFactory(37));
		
		//输出验证图片
		return EncoderHelper.getChallangeAndWriteImage(cs, "png", out);
	}
}
