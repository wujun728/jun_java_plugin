package com.ringo.jgit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.junit.Test;

import com.ringo.enity.LogEnity;
import com.ringo.enity.StatusList;

public class JgitDemoTest {

	@Test
	public void testCreatGitFolder() {
		JgitDemo j = new JgitDemo();
		try {
			j.creatGitFolder();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testdeleteGitFolder() {
		JgitDemo j = new JgitDemo();
		j.deleteGitFolder("D:\\source-code\\aaa");
	}

	@Test
	public void testdeleteFile() {
		JgitDemo j = new JgitDemo();
		File file = new File("D:\\source-code\\temp2");
		j.deleteFile(file);
	}

	@Test
	public void testgitAddFile() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitAddFile("D:\\source-code\\testClone", ".");
		} catch (NoFilepatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitCommitFile() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitCommitFile("D:\\source-code\\testClone", "新建了一个ddd.txt");
		} catch (NoHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoMessageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnmergedPathsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConcurrentRefUpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WrongRepositoryStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbortedByHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetStatus() {
		JgitDemo j = new JgitDemo();
		try {
			StatusList s = j.getStatus("D:\\source-code\\testClone");
			s.getAddFile().forEach(it -> System.out.println("AddFile:" + it));
			s.getRemoveFile().forEach(it -> System.out.println("RemoveFile:" + it));
			s.getModifiedFile().forEach(it -> System.out.println("ModifiedFile:" + it));
			s.getUntrackedFile().forEach(it -> System.out.println("UntrackedFile:" + it));
			s.getConfictingFile().forEach(it -> System.out.println("ConfictingFile:" + it));
			s.getMissingFile().forEach(it -> System.out.println("MissingFile:" + it));
		} catch (NoWorkTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitRmFile() {
		JgitDemo j = new JgitDemo();
		try {
			try {
				j.gitRmFile("D:\\source-code\\temp2", "test.txt");
			} catch (NoFilepatternException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetLog() {
		JgitDemo j = new JgitDemo();
		try {
			List<LogEnity> ls = j.getLog("D:\\source-code\\temp2");
			ls.forEach(log -> {
				System.out.println("提交人：  " + log.getCommitAuthor());
				System.out.println("提交SHA1：  " + log.getCommitId());
				System.out.println("提交信息：  " + log.getCommitMassage());
				System.out.println("提交时间：  " + log.getCommitDate());
			});
		} catch (NoHeadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetDiffOneFile() {
		JgitDemo j = new JgitDemo();
		try {
			j.getDiff("D:\\source-code\\temp2", "test.txt");
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetDiff() {
		JgitDemo j = new JgitDemo();
		try {
			j.getDiff("D:\\source-code\\temp2");
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitReset() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitReset("D:\\source-code\\temp2");
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitResetByVersion() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitReset("D:\\source-code\\temp2", "557282f1bcb51b9eb2b943071d91ec092b27d428");
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitCreateBranch() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitCreateBranch("D:\\source-code\\temp2", "text");
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitDeleteBranch() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitDeleteBranch("D:\\source-code\\temp2", "text");
		} catch (GitAPIException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitCheckOutBranch() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitCheckOutBranch("D:\\source-code\\testClone2", "remotes/origin/branch1");
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testC() {
		Process process = null;
		try {
			// process = Runtime.getRuntime().exec(new String [] {"cmd"," /c ","cd
			// d://gitCmd"," d: ","git add -A"});
			process = Runtime.getRuntime().exec("cmd /c cd d://gitCmd & d: & git status");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			process.waitFor();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 读取屏幕输出
		BufferedReader strCon = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line = null;
		String line2 = null;
		System.out.println("循环开始~~~~~~~~~~~~");
		try {
			while ((line = strCon.readLine()) != null) {
				// System.out.println(line);
				line2 += line;
			}
			System.out.println(line2);
			if ("nullOn branch masternothing to commit, working tree clean".equals(line2)) {
				System.out.println("全部提交完成");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("循环结束~~~~~~~~~~~~");

	}

	@Test
	public void testGitMerge() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitMerge("D:\\source-code\\temp2", "text");
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitClone() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitClone("phper@192.168.32.240:/gitroot/java.git", "D:\\source-code\\testClone3");
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitCreatRemote() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitCreatRemote("192.168.32.240", "root", "8b411@", "java");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitPush() {
		JgitDemo j = new JgitDemo();
		try {
//			j.gitClone("phper@192.168.32.240:/gitroot/java.git", "D:\\source-code\\testClone");
//			j.gitCommitFile("D:\\source-code\\testClone", "为了创建分支而提交一次");
//			j.gitCreateBranch("D:\\source-code\\testClone", "branch1");
			j.gitPush("D:\\source-code\\testClone", "branch1", "phper@192.168.32.240:/gitroot/java.git", "phper",
					"123456");
		} catch (IOException | GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGitPull() {
		JgitDemo j = new JgitDemo();
		try {
			j.gitPull("D:\\source-code\\testClone3", "origin", "phper", "123456");
			// TODO Auto-generated catch block
		} catch (GitAPIException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
