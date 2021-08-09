# Spring Cloud实战小贴士：Zuul处理Cookie和重定向

**原创**

 [2017-05-01](https://blog.didispace.com/spring-cloud-zuul-cookie-redirect/)

 翟永超

 [Spring Cloud](https://blog.didispace.com/categories/Spring-Cloud/)

[【推荐】从业15年的架构师告诉你：如何落地微服务和云原生架构？](https://blog.didispace.com/how-to-implement-microservice-and-cloud-native-architecture/)

由于我们在之前所有的入门教程中，对于HTTP请求都采用了简单的接口实现。而实际使用过程中，我们的HTTP请求要复杂的多，比如当我们将Spring Cloud Zuul作为API网关接入网站类应用时，往往都会碰到下面这两个非常常见的问题：

- 会话无法保持
- 重定向后的HOST错误

本文将帮助大家分析问题原因并给出解决这两个常见问题的方法。

## 会话保持问题

通过跟踪一个HTTP请求经过Zuul到具体服务，再到返回结果的全过程。我们很容易就能发现，在传递的过程中，HTTP请求头信息中的Cookie和Authorization都没有被正确地传递给具体服务，所以最终导致会话状态没有得到保持的现象。

那么这些信息是在哪里丢失的呢？我们从Zuul进行路由转发的过滤器作为起点，来一探究竟。下面是`RibbonRoutingFilter`过滤器的实现片段：

```
public class RibbonRoutingFilter extends ZuulFilter {
	...
	protected ProxyRequestHelper helper;
	
	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		this.helper.addIgnoredHeaders();
		try {
			RibbonCommandContext commandContext = buildCommandContext(context);
			ClientHttpResponse response = forward(commandContext);
			setResponse(response);
			return response;
		}
		...
		return null;
	}
	
		protected RibbonCommandContext buildCommandContext(RequestContext context) {
		HttpServletRequest request = context.getRequest();

		MultiValueMap<String, String> headers = this.helper
				.buildZuulRequestHeaders(request);
		MultiValueMap<String, String> params = this.helper
				.buildZuulRequestQueryParams(request);
		...
	}
}
```

这里有三个重要元素：

- 过滤器的核心逻辑`run`函数实现，其中调用了内部函数`buildCommandContext`来构建上下文内容
- 而`buildCommandContext`中调用了`helper`对象的`buildZuulRequestHeaders`方法来处理请求头信息
- `helper`对象是`ProxyRequestHelper`类的实例

接下来我们再看看`ProxyRequestHelper`的实现：

```
public class ProxyRequestHelper {

	public MultiValueMap<String, String> buildZuulRequestHeaders(
			HttpServletRequest request) {
		RequestContext context = RequestContext.getCurrentContext();
		MultiValueMap<String, String> headers = new HttpHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String name = headerNames.nextElement();
				if (isIncludedHeader(name)) {
					Enumeration<String> values = request.getHeaders(name);
					while (values.hasMoreElements()) {
						String value = values.nextElement();
						headers.add(name, value);
					}
				}
			}
		}
		Map<String, String> zuulRequestHeaders = context.getZuulRequestHeaders();
		for (String header : zuulRequestHeaders.keySet()) {
			headers.set(header, zuulRequestHeaders.get(header));
		}
		headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip");
		return headers;
	}

	public boolean isIncludedHeader(String headerName) {
		String name = headerName.toLowerCase();
		RequestContext ctx = RequestContext.getCurrentContext();
		if (ctx.containsKey(IGNORED_HEADERS)) {
			Object object = ctx.get(IGNORED_HEADERS);
			if (object instanceof Collection && ((Collection<?>) object).contains(name)) {
				return false;
			}
		}
		...
	}
}
```

从上述源码中，我们可以看到构建头信息的方法`buildZuulRequestHeaders`通过`isIncludedHeader`函数来判断当前请求的各个头信息是否在忽略的头信息清单中，如果是的话就不组织到此次转发的请求中去。那么这些需要忽略的头信息是在哪里初始化的呢？在PRE阶段的PreDecorationFilter过滤器中，我们可以找到答案：

```
public class PreDecorationFilter extends ZuulFilter {
	...
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		final String requestURI = this.urlPathHelper.getPathWithinApplication(ctx.getRequest());
		Route route = this.routeLocator.getMatchingRoute(requestURI);
		if (route != null) {
			String location = route.getLocation();
			if (location != null) {
				ctx.put("requestURI", route.getPath());
				ctx.put("proxy", route.getId());
              	 // 处理忽略头信息的部分
				if (!route.isCustomSensitiveHeaders()) {
					this.proxyRequestHelper.addIgnoredHeaders(
						this.properties.getSensitiveHeaders()
						.toArray(new String[0]));
				} else {
					this.proxyRequestHelper.addIgnoredHeaders(
						route.getSensitiveHeaders()
						.toArray(new String[0]));
				}
		...
}
```

从上述源码中，我们可以看到有一段if/else块，通过调用`ProxyRequestHelper`的`addIgnoredHeaders`方法来添加需要忽略的信息到请求上下文中，供后续ROUTE阶段的过滤器使用。这里的if/else块分别用来处理全局设置的敏感头信息和指定路由设置的敏感头信息。而全局的敏感头信息定义于`ZuulProperties`中：

```
@Data
@ConfigurationProperties("zuul")
public class ZuulProperties {
	private Set<String> sensitiveHeaders = new LinkedHashSet<>(
			Arrays.asList("Cookie", "Set-Cookie", "Authorization"));
	...
}
```

所以解决该问题的思路也很简单，我们只需要通过设置sensitiveHeaders即可，设置方法分为两种：

- 全局设置：
  - `zuul.sensitive-headers=`
- 指定路由设置：
  - `zuul.routes..sensitive-headers=`
  - `zuul.routes..custom-sensitive-headers=true`

## 重定向问题

在使用Spring Cloud Zuul对接Web网站的时候，处理完了会话控制问题之后。往往我们还会碰到如下图所示的问题，我们在浏览器中通过Zuul发起了登录请求，该请求会被路由到某WebSite服务，该服务在完成了登录处理之后，会进行重定向到某个主页或欢迎页面。此时，仔细的开发者会发现，在登录完成之后，我们浏览器中URL的HOST部分发生的改变，该地址变成了具体WebSite服务的地址了。这就是在这一节，我们将分析和解决的重定向问题！

[![img](https://blog.didispace.com/assets/zuul-redirect.png)](https://blog.didispace.com/assets/zuul-redirect.png)

出现该问题的根源是Spring Cloud Zuul没有正确的处理HTTP请求头信息中的Host导致。在Brixton版本中，Spring Cloud Zuul的`PreDecorationFilter`过滤器实现时完全没有考虑这一问题，它更多的定位于REST API的网关。所以如果要在Brixton版本中增加这一特性就相对较为复杂，不过好在Camden版本之后，Spring Cloud Netflix 1.2.x版本的Zuul增强了该功能，我们只需要通过配置属性`zuul.add-host-header=true`就能让原本有问题的重定向操作得到正确的处理。关于更多Host头信息的处理，读者可以参考本文之前的分析思路，可以通过查看`PreDecorationFilter`过滤器的源码来详细更多实现细节。