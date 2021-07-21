package com.ketayao.learn.test.mockito.powermock;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DirectoryStructure.class)
public class DirectoryStructureTest {
	/**
	 * 构造函数模拟
	 * 使用 whenNew().withArguments().thenReturn() 语句即可实现对具体类的构造函数的模拟操作。
	 * 然后对于之前创建的模拟对象 directoryMock使用 When().thenReturn() 语句，即可实现需要的所有功能，
	 * 从而实现对被测对象的覆盖测试。在本测试中，因为实际的模拟操作是在类 DirectoryStructureTest 中实现，
	 * 所以需要指定的 @PrepareForTest 对象是 DirectoryStructureTest.class。
	 * 描述
	 * @throws Exception
	 */
	@Test
	public void createDirectoryStructureWhenPathDoesntExist() throws Exception {
		final String directoryPath = "mocked path";

		File directoryMock = mock(File.class);
		
		// This is how you tell PowerMockito to mock construction of a new File.
		whenNew(File.class).withArguments(directoryPath).thenReturn(
				directoryMock);

		// Standard expectations
		when(directoryMock.exists()).thenReturn(false);
		when(directoryMock.mkdirs()).thenReturn(true);

		assertTrue(new DirectoryStructure().create(directoryPath));

		// Optionally verify that a new File was "created".
		verifyNew(File.class).withArguments(directoryPath);
	}
}
