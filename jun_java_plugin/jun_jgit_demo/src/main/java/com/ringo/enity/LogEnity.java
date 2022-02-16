package com.ringo.enity;


public class LogEnity {
	//提交人
	String commitAuthor;
	
	//提交SHA1
	String 	commitId;
	
	//提交信息
	String commitMassage;
	
	//提交日期
	String commitDate;

	public String getCommitAuthor() {
		return commitAuthor;
	}

	public void setCommitAuthor(String commitAuthor) {
		this.commitAuthor = commitAuthor;
	}

	public String getCommitId() {
		return commitId;
	}

	public void setCommitId(String commitId) {
		this.commitId = commitId;
	}

	public String getCommitMassage() {
		return commitMassage;
	}

	public void setCommitMassage(String commitMassage) {
		this.commitMassage = commitMassage;
	}

	public String getCommitDate() {
		return commitDate;
	}

	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}
}
