package org.smartboot.service.api.impl;

import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.smartboot.service.api.ApiAuthBean;
import org.smartboot.service.api.ApiCodeEnum;
import org.smartboot.service.api.RestApiHandler;
import org.smartboot.service.api.RestApiResult;
import org.smartboot.service.api.RestApiService;
import org.smartboot.service.api.VersionEnum;
import org.smartboot.service.api.permission.HandlerPerimissionAnnotation;
import org.smartboot.service.api.permission.Permission;
import org.smartboot.service.api.permission.PermissionUtil;
import org.smartboot.service.util.AbstractService;
import org.smartboot.service.util.AssertUtils;
import org.smartboot.service.util.ServiceCallback;
import org.springframework.util.CollectionUtils;

/**
 * API服务实现类
 * 
 * @author Wujun
 * @version RestApiServiceImpl.java, v 0.1 2016年2月10日 下午3:17:16 Seer Exp.
 */
public class RestApiServiceImpl extends AbstractService implements RestApiService {
	private static final Logger LOGGER = LogManager.getLogger(RestApiServiceImpl.class);

	/** API服务集合 */
	private Map<String, RestApiHandler> handlers;

	/*
	 * (non-Javadoc)
	 * 
	 * @see DynApiService#executeBizLogic(java.lang. String, java.lang.String,
	 * java.util.Map)
	 */
	public RestApiResult<Object> execute(ApiAuthBean authBean, Map<String, String> requestMap) {
		final RestApiResult<Object> result = new RestApiResult<Object>();
		operateTemplate.operateWithoutTransaction(result, new ServiceCallback() {
			RestApiHandler handler;
			VersionEnum version;

			/*
			 * (non-Javadoc)
			 * 
			 * @see ServiceCallback#doCheck()
			 */
			@Override
			public void doCheck() {
				AssertUtils.isNotNull(authBean, "鉴权参数异常");
				AssertUtils.isNotBlank(authBean.getSrvname(), "服务名称未指定");

				// 版本校验
				if (StringUtils.isBlank(authBean.getApiVersion())) {
					version = VersionEnum.CURRENT_VERSION;
				} else {
					version = VersionEnum.getVersion(authBean.getApiVersion());
				}
				AssertUtils.isNotNull(version, "服务器不支持版本号" + authBean.getApiVersion() + ",请升级至最新版");
				result.setVersion(version.getVersion());

				// 识别处理器
				handler = findApiBizHandler(authBean.getSrvname(), version);
				AssertUtils.isNotNull(handler, "无法处理该服务[srvname:" + authBean.getSrvname() + "]");

				// 校验本次服务是否具备权限
				HandlerPerimissionAnnotation annotation = handler.getClass()
					.getAnnotation(HandlerPerimissionAnnotation.class);
				if (annotation == null || ArrayUtils.isEmpty(annotation.value())) {
					LOGGER.warn(handler + "未指定权限,操作存在隐患!");
					return;
				}
				Permission[] permission = annotation.value();

				for (Permission p : permission) {
					if (!StringUtils.equals(authBean.getActName(), p.act())) {
						continue;
					}
					// TODO 以下校验逻辑可根据实际业务作调整
					String[] perms = (String[]) authBean.getContext(ApiAuthBean.ContextKey.PERMISSION_LIST);
					AssertUtils.isTrue(ArrayUtils.isNotEmpty(perms), "用户权限为空");
					AssertUtils.isTrue(PermissionUtil.hasPermission(perms, p), "无操作权限!");
					break;
				}
			}

			@Override
			public void doOperate() {
				// 是否进行事务控制
				if (handler.needTransaction(authBean, requestMap)) {
					operateTemplate.operateWithTransaction(result, new ServiceCallback() {

						@Override
						public void doOperate() {
							Object execResult = handler.execute(authBean, requestMap);
							if (LOGGER.isDebugEnabled()) {
								LOGGER.debug(execResult);
							}
							result.setData(execResult);
						}
					});
					AssertUtils.isTrue(result.isSuccess(), result.getMessage());
				} else {
					Object execResult = handler.execute(authBean, requestMap);
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug(execResult);
					}
					result.setData(execResult);
				}

			}
		});
		if (StringUtils.isNotBlank(result.getMessage()) && result.getCode() == ApiCodeEnum.SUCCESS.getCode()) {
			result.setCode(result.getResultCode()==null?ApiCodeEnum.FAIL.getCode():result.getResultCode().getCode());
		}
		return result;
	}

	/**
	 * 根据服务名和版本寻找服务，如果服务实例找不到的话，就自动降级寻找. 这样可以保证混合版本客户端的运行，而不是仅仅只能用一个版本的API。
	 * 使得对老版本移动端的代码增加新功能的时候更加方便。 version的格式1.0.0
	 * 在配置文件中，biz处理器的key是:srvname-version
	 * 
	 * @param srvName
	 * @param version
	 * @return
	 */
	private RestApiHandler findApiBizHandler(String srvName, VersionEnum version) {
		if (version == null || CollectionUtils.isEmpty(handlers)) {
			return null;
		}
		RestApiHandler handler = handlers.get(generateServerKey(srvName, version));
		if (handler != null) {
			return handler;
		}
		return findApiBizHandler(srvName, version.getParent());
	}

	private String generateServerKey(String srvName, VersionEnum version) {
		return srvName + "-" + version.getVersion();
	}

	/**
	 * Setter method for property <tt>handlers</tt>.
	 *
	 * @param handlers
	 *            value to be assigned to property handlers
	 */
	public void setHandlers(Map<String, RestApiHandler> handlers) {
		this.handlers = handlers;
	}

}
