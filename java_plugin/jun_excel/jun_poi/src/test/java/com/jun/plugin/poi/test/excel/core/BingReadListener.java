package com.jun.plugin.poi.test.excel.core;

import com.jun.plugin.poi.test.excel.core.impl.BingExcelEventImpl.ModelInfo;

public interface BingReadListener {

	void readModel(Object object, ModelInfo modelInfo);

}
