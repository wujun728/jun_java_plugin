package com.puff.session;

import java.util.Properties;

import com.puff.storage.Storage;

public interface Config {

	public int setSessionTimeout();

	public String setCookieName();

	public int setCookieMaxAge();

	public String setCookieDomain();

	public String setCookiePath();

	public Properties setStorageProperties();

	public Storage setStorage();

}