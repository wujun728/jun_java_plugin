
<!-- Resolves views selected for rendering by @Controllers to .jsp resources
in the /WEB-INF/views directory -->
<bean id="contentNegotiationManagerFactoryBean"
	class="org.springframework.web.accept.ContentNegotiationManagerFactoryBean">
	<!-- 设置为true以忽略对Accept Header的支持 -->
	<property name="ignoreAcceptHeader" value="true" />
	<!-- 在没有扩展名时即: "/user/1" 时的默认展现形式 -->
	<property name="defaultContentType" value="text/html" />
	<!-- 用于开启 /userinfo/123?format=json 的支持，false为关闭之,我更喜欢.json的方式，因为可以少敲几次键盘 -->
	<property name="favorParameter" value="false" />
	 
	<!-- 扩展名至mimeType的映射,即 /user.json => application/json -->
	<property name="mediaTypes">
		<map>
		<entry key="html" value="text/html" />
		<entry key="json" value="application/json" />
		<entry key="xml" value="application/xml" />
		</map>
	</property>
</bean>
 
<bean
	class="org.springframework.web.servlet.view.ContentNegotiatingViewResolver">
	<property name="order" value="1" />
	<property name="viewResolvers">
		<list>
			<bean id="viewResolver"
				class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
				<property name="viewClass"
				value="org.springframework.web.servlet.view.freemarker.FreeMarkerView" />
				<property name="contentType" value="text/html; charset=utf-8" />
				<property name="cache" value="true" />
				<property name="prefix" value="" />
				<property name="suffix" value=".ftl" />
				<property name="order" value="1" />
				<property name="requestContextAttribute" value="request" />
			</bean>
			<bean
				class="org.springframework.web.servlet.view.InternalResourceViewResolver">
				<property name="prefix" value="/WEB-INF/jsp/" />
				<property name="suffix" value=".jsp" />
				<property name="order" value="2" />
			</bean>
		</list>
	</property>
	<property name="defaultViews">
	<list>
		<!-- for application/json -->
		<bean
		class="org.springframework.web.servlet.view.json.MappingJackson2JsonView" />
	</list>
	</property>
</bean>