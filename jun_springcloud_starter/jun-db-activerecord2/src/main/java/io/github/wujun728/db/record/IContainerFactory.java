/**
 * Copyright (c) 2011-2023, James Zhan 詹波 (jfinal@126.com).
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

package io.github.wujun728.db.record;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("rawtypes")
public interface IContainerFactory {
	Map getAttrsMap();
	Map getColumnsMap();
	Set getModifyFlagSet();
	
	static final IContainerFactory defaultContainerFactory = new IContainerFactory() {
		
		public Map<String, Object> getAttrsMap() {
			return new HashMap<String, Object>();
		}
		
		public Map<String, Object> getColumnsMap() {
			return new HashMap<String, Object>();
		}
		
		public Set<String> getModifyFlagSet() {
			return new HashSet<String>();
		}
	};
}
