package com.wmz7year;

import java.io.File;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

public class PluginClassLoader extends URLClassLoader {

	public PluginClassLoader() {
		super(new URL[] {}, findParentClassLoader());
	}

	public void addDirectory(File directory) {
		try {
			addURLFile(directory.toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void addURLFile(URL file) {
		try {
			URLConnection uc = file.openConnection();
			if (uc instanceof JarURLConnection) {
				uc.setUseCaches(true);
				((JarURLConnection) uc).getManifest();
			}
		} catch (Exception e) {
		}
		addURL(file);
	}

	private static ClassLoader findParentClassLoader() {

		return ClassLoader.getSystemClassLoader();
	}

}
