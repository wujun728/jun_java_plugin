package com.jun.plugin.session;

import java.util.Properties;

import com.jun.plugin.storage.Storage;

public interface Config {

	public int setSessionTimeout();

	public String setCookieName();

	public int setCookieMaxAge();

	public String setCookieDomain();

	public String setCookiePath();

	public Properties setStorageProperties();

	public Storage setStorage();

}