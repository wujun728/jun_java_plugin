package com.ringo.enity;

import java.util.List;

public class StatusList {
	//git add命令后会看到变化
	List<String> AddFile;
	//git rm命令会看到变化，从暂存区删除的文件列表
	List<String> RemoveFile;
	//修改的文件列表
	List<String> ModifiedFile;
	//工作区新增的文件列表
	List<String> UntrackedFile;
	//冲突的文件列表
	List<String> ConfictingFile;
	//工作区删除的文件列表
	List<String> MissingFile;
	public List<String> getAddFile() {
		return AddFile;
	}
	public void setAddFile(List<String> addFile) {
		AddFile = addFile;
	}
	public List<String> getRemoveFile() {
		return RemoveFile;
	}
	public void setRemoveFile(List<String> removeFile) {
		RemoveFile = removeFile;
	}
	public List<String> getModifiedFile() {
		return ModifiedFile;
	}
	public void setModifiedFile(List<String> modifiedFile) {
		ModifiedFile = modifiedFile;
	}
	public List<String> getUntrackedFile() {
		return UntrackedFile;
	}
	public void setUntrackedFile(List<String> untrackedFile) {
		UntrackedFile = untrackedFile;
	}
	public List<String> getConfictingFile() {
		return ConfictingFile;
	}
	public void setConfictingFile(List<String> confictingFile) {
		ConfictingFile = confictingFile;
	}
	public List<String> getMissingFile() {
		return MissingFile;
	}
	public void setMissingFile(List<String> missingFile) {
		MissingFile = missingFile;
	}
}
