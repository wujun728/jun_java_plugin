package org.frameworkset.spi.remote.http.proxy.route;
/**
 * Copyright 2008 biaoping.yin
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.frameworkset.spi.remote.http.proxy.HttpAddress;
import org.frameworkset.spi.remote.http.proxy.HttpServiceHosts;
import org.frameworkset.spi.remote.http.proxy.RoundRobinList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p></p>
 * <p>Copyright (c) 2018</p>
 * @Date 2019/6/25 15:53
 * @author Wujun
 * @version 1.0
 */
public class RoutingGroup {
	protected RoundRobinList serversList;
	private HttpServiceHosts httpServiceHosts;
	protected List<HttpAddress> addressList = new ArrayList<HttpAddress>();
	private Map<String,HttpAddress> addressMap = new HashMap<String, HttpAddress>();
	public void addHttpAddress(HttpAddress httpAddress){
		this.addressList.add(httpAddress);
		this.addressMap.put(httpAddress.getAddress(),httpAddress);

	}
	public RoutingGroup(HttpServiceHosts httpServiceHosts){
		this.httpServiceHosts = httpServiceHosts;
	}
	public HttpServiceHosts getHttpServiceHosts() {
		return httpServiceHosts;
	}

	public HttpAddress get(){
		return serversList.getFromRouting();
	}
	public HttpAddress getOkOrFailed(){
		return serversList.getOkOrFailedFromRouting();
	}

	public void after(){
		serversList = new RoundRobinList(httpServiceHosts,this.addressList);
	}
	public void after(List<HttpAddress> commonGroup){
		if(commonGroup != null && commonGroup.size() > 0) {
			this.addressList.addAll(commonGroup);
			for (HttpAddress httpAddress: commonGroup){
				this.addressMap.put(httpAddress.getAddress(),httpAddress);
			}
		}
		serversList = new RoundRobinList(httpServiceHosts,this.addressList);
	}
	public String toString(){
		if(addressList != null ){
			return addressList.toString();
		}
		return "{}";
	}
}
