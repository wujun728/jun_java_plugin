package com.baomidou.springwind.common;

import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.kisso.common.captcha.DefaultCaptcha;
import com.baomidou.kisso.common.captcha.ICaptchaStore;
import com.baomidou.kisso.common.captcha.background.LineNoiseBackgroundFactory;
import com.baomidou.kisso.common.captcha.filter.ConfigurableFilterFactory;
import com.baomidou.kisso.common.captcha.filter.library.AbstractImageOp;
import com.baomidou.kisso.common.captcha.filter.library.WobbleImageOp;
import com.baomidou.kisso.common.captcha.font.RandomFontFactory;
import com.baomidou.kisso.common.captcha.service.ConfigurableCaptchaService;

/**
 * 
 * 验证码服务演示实例
 * 
 * @author hubin
 *
 */
public class MyCaptcha extends DefaultCaptcha {

	public static final String CAPTCHA_TOKEN = "ctoken";
	private static final String CAPTCHA_CACHE = "captchaCache";

	/**
	 * <p>
	 * 换掉验证码库， 继承  AbstractCaptcha 
	 * 实现 writeImage 方法即可
	 * </p>
	 */
	public static MyCaptcha getInstance() {
		MyCaptcha mc = new MyCaptcha();
		mc.setCaptchaService(getMyConfigurableCaptchaService());
		mc.setCaptchaStore(new ICaptchaStore() {

			@Override
			public String get(String ticket) {
				Object obj = EhcacheHelper.get(CAPTCHA_CACHE, ticket);
				if (obj != null) {
					return String.valueOf(obj);
				}
				return null;
			}

			@Override
			public boolean put(String ticket, String captcha) {
				EhcacheHelper.put(CAPTCHA_CACHE, ticket, captcha);
				return true;
			}

		});
		return mc;
	}

	/**
	 * <p>
	 * 自定义图片验证码生成规则
	 * </p>
	 */
	public static ConfigurableCaptchaService getMyConfigurableCaptchaService() {
		ConfigurableCaptchaService cs = new ConfigurableCaptchaService();
		// 验证码宽高
		cs.setWidth(100);
		cs.setHeight(40);

		// 设置 6 位自适应验证码
		// AdaptiveRandomWordFactory arw = new AdaptiveRandomWordFactory();
		// arw.setMinLength(6);
		// arw.setMaxLength(6);
		// cs.setWordFactory(arw);

		// 字符大小设置
		RandomFontFactory rf = new RandomFontFactory();
		rf.setMinSize(27);
		rf.setMaxSize(30);
		cs.setFontFactory(rf);

		// 文本渲染
		// cs.setTextRenderer(new RandomYBestFitTextRenderer());

		// 设置一个单一颜色字体
		// cs.setColorFactory(new SingleColorFactory(new Color(11, 182, 114)));
		// cs.setFilterFactory(new
		// CurvesRippleFilterFactory(cs.getColorFactory()));

		// 图片滤镜设置
		ConfigurableFilterFactory filterFactory = new ConfigurableFilterFactory();
		List<BufferedImageOp> filters = new ArrayList<BufferedImageOp>();

		// 摆动干扰
		WobbleImageOp wio = new WobbleImageOp();
		wio.setEdgeMode(AbstractImageOp.EDGE_CLAMP);
		wio.setxAmplitude(2.0);
		wio.setyAmplitude(1.0);
		filters.add(wio);

		// 曲线干扰
		// CurvesImageOp cio = new CurvesImageOp();
		// cio.setColorFactory(new SingleColorFactory(new Color(59, 162, 9)));
		// cio.setEdgeMode(AbstractImageOp.EDGE_ZERO);
		// cio.setStrokeMax(0.3f);
		// cio.setStrokeMin(0.1f);
		// filters.add(cio);

		filterFactory.setFilters(filters);
		cs.setFilterFactory(filterFactory);

		// 椭圆形干扰背景
		// cs.setBackgroundFactory(new OvalNoiseBackgroundFactory(7));

		// 线形干扰背景
		cs.setBackgroundFactory(new LineNoiseBackgroundFactory(37));
		return cs;
	}
}
