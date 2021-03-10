package org.typroject.tyboot.core.auth.enumeration;


public enum AuthType implements ProvidedAuthType{
	ID_PASSWORD("idPasswordLoginAuthenticator");




	 AuthType(String authenticator)
	{
			this.authenticator = authenticator;
	}

	private String  authenticator;


	public String getAuthenticator() {
		return authenticator;
	}

	public String getAuthType()
	{
		return this.name();
	}

}

/*
 * $Log: lexingbuild.bat,v $
 */