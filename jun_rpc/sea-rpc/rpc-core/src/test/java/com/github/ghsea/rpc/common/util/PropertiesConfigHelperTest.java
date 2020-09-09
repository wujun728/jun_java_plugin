package com.github.ghsea.rpc.common.util;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Assert;
import org.junit.Test;

import com.github.ghsea.rpc.common.constants.PropertiesConstants;

public class PropertiesConfigHelperTest {

	@Test
	public void testLoadIfAbsent() {
		PropertiesConfiguration config = PropertiesConfigHelper.loadIfAbsent(PropertiesConstants.ZK_CONFIG_FILE);
		String zkservers = config.getString(PropertiesConstants.KEY_ZK_SERVERS);
		Assert.assertTrue(zkservers != null);
	}
}
