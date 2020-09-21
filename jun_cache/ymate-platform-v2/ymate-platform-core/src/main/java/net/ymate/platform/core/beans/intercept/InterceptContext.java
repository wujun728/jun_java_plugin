/*
 * Copyright 2007-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.core.beans.intercept;

import net.ymate.platform.core.YMP;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 拦截器环境上下文对象
 *
 * @author 刘镇 (suninformation@163.com) on 15/5/19 下午3:26
 * @version 1.0
 */
public class InterceptContext {

    private IInterceptor.Direction __direction;

    private YMP __owner;

    private final Object __targetObject;

    private final Method __targetMethod;

    private final Object[] __methodParams;

    private final Map<String, String> __contextParams;

    private Object __resultObject;

    public InterceptContext(IInterceptor.Direction direction, YMP owner, Object targetObject, Method targetMethod, Object[] methodParams, Map<String, String> contextParams) {
        __direction = direction;
        __owner = owner;
        __targetObject = targetObject;
        __targetMethod = targetMethod;
        __methodParams = methodParams;
        __contextParams = contextParams;
    }

    /**
     * @return 获取当前拦截器执行方式，Before或After
     */
    public IInterceptor.Direction getDirection() {
        return __direction;
    }

    /**
     * @return 获取所属YMP框架管理器
     */
    public YMP getOwner() {
        return __owner;
    }

    /**
     * @return 获取被拦截的目标类型
     */
    public Class<?> getTargetClass() {
        return __targetObject.getClass();
    }

    /**
     * @return 获取被拦截的目标对象
     */
    public Object getTargetObject() {
        return __targetObject;
    }

    /**
     * @return 获取被代理目标方法对象
     */
    public Method getTargetMethod() {
        return __targetMethod;
    }

    /**
     * @return 获取方法参数集合
     */
    public Object[] getMethodParams() {
        return __methodParams;
    }

    /**
     * @return 返回上下文参数映射
     */
    public Map<String, String> getContextParams() {
        return __contextParams;
    }

    /**
     * @return 获取返回值对象（一般用于后置拦截器获取当前方法的执行结果）
     */
    public Object getResultObject() {
        return __resultObject;
    }

    /**
     * 设置返回值
     *
     * @param resultObject 方法结果对象
     */
    public void setResultObject(Object resultObject) {
        __resultObject = resultObject;
    }
}
