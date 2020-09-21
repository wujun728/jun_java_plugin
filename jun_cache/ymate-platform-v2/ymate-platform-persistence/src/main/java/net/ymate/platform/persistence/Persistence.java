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
package net.ymate.platform.persistence;

import net.ymate.platform.core.Version;
import net.ymate.platform.core.YMP;
import net.ymate.platform.core.module.IModule;
import net.ymate.platform.core.module.annotation.Module;
import net.ymate.platform.persistence.annotation.Entity;
import net.ymate.platform.persistence.handle.EntityHandler;

/**
 * @author 刘镇 (suninformation@163.com) on 15/8/1 上午10:11
 * @version 1.0
 */
@Module
public class Persistence implements IModule {

    public static final Version VERSION = new Version(2, 0, 3, Persistence.class.getPackage().getImplementationVersion(), Version.VersionType.Release);

    private YMP __owner;

    private boolean __inited;

    public String getName() {
        return "persistence";
    }

    public void init(YMP owner) throws Exception {
        if (!__inited) {
            //
            owner.registerHandler(Entity.class, new EntityHandler(owner));
            //
            __inited = true;
        }
    }

    public boolean isInited() {
        return __inited;
    }

    public YMP getOwner() {
        return __owner;
    }

    public void destroy() throws Exception {
        if (__inited) {
            __inited = false;
        }
    }
}
