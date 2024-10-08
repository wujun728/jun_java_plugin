package io.github.wujun728.activerecord.test;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.jfinal.plugin.activerecord.Record;
import io.github.wujun728.activerecord.utils.ModelUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * ModelUtil test
 */
class ModelUtilTest {

	@Test
	void testRecord() {
		String value = "123";
		Record record = new Record();
		record.set("abc_test", value);
		User user = ModelUtil.toBean(record, User.class);
		Assertions.assertEquals(value, user.getAbcTest());
	}

//	@Test
	void test() {
		String value = "123";
		UserModel userModel = new UserModel();
		userModel.setAbcTest(value);
		//User user = ModelUtil.toBean(userModel, User.class);
		User user = new User();
		BeanUtil.copyProperties(userModel, user, CopyOptions.create());
		Assertions.assertEquals(value, user.getAbcTest());
	}

}
