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
package net.ymate.platform.webmvc.view.impl;

import net.ymate.platform.webmvc.view.AbstractView;

/**
 * 空视图，用于防止控制器返回值为NULL时受Convention模式影响
 *
 * @author 刘镇 (suninformation@163.com) on 14/10/21 下午5:48
 * @version 1.0
 */
public class NullView extends AbstractView {

    public static NullView bind() {
        return new NullView();
    }

    @Override
    public void render() throws Exception {
    }

    protected void __doRenderView() throws Exception {
    }
}
