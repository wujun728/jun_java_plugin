package klg.common.utils;

import java.io.Serializable;

public class Logrole implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer logRoleId;
	private String logRoleName;
	private Integer logRoleRight;

	private String r1;
	private String r2;
	private String r3;
	private String r4;
	private String r5;

	public Logrole() {
	}

	public Integer getLogRoleId() {
		return this.logRoleId;
	}

	public void setLogRoleId(Integer logRoleId) {
		this.logRoleId = logRoleId;
	}

	public String getLogRoleName() {
		return this.logRoleName;
	}

	public void setLogRoleName(String logRoleName) {
		this.logRoleName = logRoleName;
	}

	public Integer getLogRoleRight() {
		return this.logRoleRight;
	}

	public void setLogRoleRight(Integer logRoleRight) {
		this.logRoleRight = logRoleRight;
	}

	public String getR1() {
		return this.r1;
	}

	public void setR1(String r1) {
		this.r1 = r1;
	}

	public String getR2() {
		return this.r2;
	}

	public void setR2(String r2) {
		this.r2 = r2;
	}

	public String getR3() {
		return this.r3;
	}

	public void setR3(String r3) {
		this.r3 = r3;
	}

	public String getR4() {
		return this.r4;
	}

	public void setR4(String r4) {
		this.r4 = r4;
	}

	public String getR5() {
		return this.r5;
	}

	public void setR5(String r5) {
		this.r5 = r5;
	}

	// public List<User> getUsers() {
	// return users;
	// }
	//
	//
	// public void setUsers(List<User> users) {
	// this.users = users;
	// }

	@Override
	public String toString() {
		return "Logrole [logRoleId=" + logRoleId + ", logRoleName="
				+ logRoleName + ", logRoleRight=" + logRoleRight + "]";
	}
}