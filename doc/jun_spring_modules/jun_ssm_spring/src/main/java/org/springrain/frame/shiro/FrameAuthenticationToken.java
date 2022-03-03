package org.springrain.frame.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;
/**
 * 扩展token
 * @author caomei
 *
 */
public class FrameAuthenticationToken extends UsernamePasswordToken {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userType;

	public FrameAuthenticationToken(final String username, final String password) {
		super(username, password);
	}
	
	
	public FrameAuthenticationToken(){
		
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

}
