package com.ketayao.learn.test.mockito.powermock;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
				 
 @RunWith(PowerMockRunner.class) 
 @PrepareForTest(PrivatePartialMockingExample.class) 
 public class PrivatePartialMockingExampleTest { 
    @Test 
    public void demoPrivateMethodMocking() throws Exception { 
        final String expected = "TEST VALUE"; 
        final String nameOfMethodToMock = "methodToMock"; 
        final String input = "input"; 

        PrivatePartialMockingExample underTest = spy(new PrivatePartialMockingExample()); 

        /* 
         * Setup the expectation to the private method using the method name 
         */ 
        when(underTest, nameOfMethodToMock, input).thenReturn(expected); 

        assertEquals(expected, underTest.methodToTest()); 

        // Optionally verify that the private method was actually called 
        verifyPrivate(underTest).invoke(nameOfMethodToMock, input); 
    } 
 }