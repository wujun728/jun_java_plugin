package com.techsoft.modules;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.techsoft.DataConverter;
import com.techsoft.ServletModule;
import com.techsoft.Session;
import com.techsoft.TechException;
import com.techsoft.session.SessionManager;
import com.techsoft.utils.DataSet;

public class LoginModule implements ServletModule {
	private static final Logger Log = LoggerFactory
			.getLogger(LoginModule.class);
	private static final String name = "login";
	private static LoginModule instance = null;

	private LoginModule() {
	}

	public static LoginModule getInstance() {
		if (instance == null) {
			instance = new LoginModule();
		}

		return instance;
	}

	@Override
	public String getName() {
		return name;
	}

	public Map<String, Object> readUserInfo(String username, String password)
			throws TechException {
		Map<String, Object> result = null;
		DataSet dataset = new DataSet();
		dataset.setSqlid(Session.loginsqlid);
		dataset.getParams().put("psusercode", username);
		dataset.getParams().put("pspassword", password);
		try {
			dataset.open();
			if (dataset.getRecordCount() > 0) {
				result = new HashMap<String, Object>(dataset.getDatas().get(0));
			} else {
				throw new TechException(String.valueOf(dataset
						.getResultParams().get("pserrinfo")));
			}
		} catch (Exception e) {
			throw new TechException(e.getMessage());
		} finally {
			dataset.close();
		}

		return result;
	}

	public void createSession(Map<String, Object> in, InputStream input,
			OutputStream output, DataConverter dataConverter,
			Map<String, Object> result) throws TechException {
		Map<String, Object> sessionobj = new HashMap<String, Object>();
		try {
			Session session = null;
			JSONObject inputjson = (JSONObject) dataConverter
					.readFromInputStream(input);
			String sessionid = inputjson.getString(Session.sessionid);
			if ((sessionid != null) && (!sessionid.equals(""))) {
				session = SessionManager.getInstance().getSession(sessionid);
				if (session != null) {
					// JSONObject userjson = (JSONObject) JSON.parse(session
					// .getUserInfo());
					// sessionobj.put(StringConsts.username,
					// userjson.getString(StringConsts.userNameField));
					// sessionobj.put(StringConsts.username,
					// userjson.getString(StringConsts.passwordField));
					// sessionobj.put(StringConsts.sessionid, sessionid);
				} else {
					Map<String, Object> userinfo = this.readUserInfo(
							inputjson.getString(Session.userNameField),
							inputjson.getString(Session.userPasswordField));
					session = SessionManager.getInstance().CreateLocalSession();
					session.setLastAccessTime(System.currentTimeMillis());
					String passwordEntry = String.valueOf(userinfo
							.get(Session.userPasswordField));
					userinfo.put(Session.userPasswordField, passwordEntry);
					// session.setUserInfo(JSON.toJSONString(userinfo));
					SessionManager.getInstance().setSession(sessionid, session);
				}
			} else {
				Map<String, Object> userinfo = this.readUserInfo(
						inputjson.getString(Session.userNameField),
						inputjson.getString(Session.userPasswordField));
				session = SessionManager.getInstance().CreateLocalSession();
				session.setLastAccessTime(System.currentTimeMillis());
				SessionManager.getInstance().setSession(
						String.valueOf(in.get(Session.sessionid)), session);
			}
		} catch (Exception e) {
			Log.error(e.getClass().getName() + " \n" + e.getMessage());
			throw new TechException(e.getClass().getName() + " \n"
					+ e.getMessage());
		}
	}

	@Override
	public void process(Map<String, Object> in, boolean isMultiPart,
			InputStream inputs, OutputStream outs, List<FileItem> list,
			Map<String, Object> results, DataConverter dataConverter)
			throws TechException {
		// createSession(in, input, output, serialization, results);
	}
}
