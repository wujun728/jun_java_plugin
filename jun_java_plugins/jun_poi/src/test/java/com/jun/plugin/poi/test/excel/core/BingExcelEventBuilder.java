package com.jun.plugin.poi.test.excel.core;


import com.jun.plugin.poi.test.common.Builder;
import com.jun.plugin.poi.test.excel.converter.FieldValueConverter;
import com.jun.plugin.poi.test.excel.core.handler.ConverterHandler;
import com.jun.plugin.poi.test.excel.core.handler.LocalConverterHandler;
import com.jun.plugin.poi.test.excel.core.impl.BingExcelEventImpl;

/**
 * <p>
 * Title: BingExcelBuilder<／p>
 * <p>
 * Description: <code>BingExcel</code>的构造类，可以添加自定义转换器等。<／p>
 * <p>
 * Company: bing<／p>
 * 
 * @author Wujun
 * @date 2015-12-8
 */
/**
 * <p>
 * Title: BingExcelBuilder<／p>
 * <p>
 * Description: <／p>
 * <p>
 * Company: bing<／p>
 * 
 * @author Wujun
 * @date 2015-12-8
 */
public class BingExcelEventBuilder implements Builder<BingExcelEvent> {
	private final ConverterHandler defaultLocalConverterHandler = new LocalConverterHandler();

	private BingExcelEvent bingExcelEvent;

	/**
	 * <p>
	 * Title: <／p>
	 * <p>
	 * Description:<／p>
	 */
	private BingExcelEventBuilder() {

	}

	public static Builder<BingExcelEvent> toBuilder() {

		return new BingExcelEventBuilder();

	}
	/**
	 * @return BingExcelEvent实例
	 */
	public static BingExcelEvent builderInstance(){
		return (new BingExcelEventBuilder()).builder();
	}
	@Override
	public Builder<BingExcelEvent> registerFieldConverter(Class<?> clazz,
			FieldValueConverter converter) {
		
		defaultLocalConverterHandler.registerConverter(clazz, converter);
			
		return this;
	}

	@Override
	public BingExcelEvent builder() {
		if (bingExcelEvent == null) {
			bingExcelEvent = new BingExcelEventImpl(defaultLocalConverterHandler);
		}

		return this.bingExcelEvent;
	}

}
