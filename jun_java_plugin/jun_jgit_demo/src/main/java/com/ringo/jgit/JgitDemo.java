package com.ringo.jgit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.AbortedByHookException;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.CannotDeleteCurrentBranchException;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.NotMergedException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotAdvertisedException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.StopWalkException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;

import com.ringo.enity.LogEnity;
import com.ringo.enity.StatusList;
import com.rinog.connect.SshConnect;

import ch.ethz.ssh2.Connection;

public class JgitDemo {
	private static String GITPATH = "D:\\source-code\\temp2\\.git";

	/**
	 * 用于创建Git文件目录,如果没有此目录会自动创建
	 * 
	 * @param request
	 * @param response
	 * @throws GitAPIException
	 * @throws IllegalStateException
	 */
	public void creatGitFolder() throws IllegalStateException, GitAPIException {
		File dir = new File(GITPATH);
		Git.init().setGitDir(dir).setDirectory(dir.getParentFile()).call();
	}

	/**
	 * 用于删除Git文件目录
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteGitFolder(String path) {
		File file = new File(path);
		if (file.exists()) {// 判断文件是否存在
			if (file.isFile()) {// 判断是否是文件
				file.delete();// 删除文件
			} else if (file.isDirectory()) {// 否则如果它是一个目录
				String[] filesName = file.list();// 声明目录下所有的文件 filesName[];
				for (int i = 0; i < filesName.length; i++) {// 遍历目录下所有的文件
					this.deleteGitFolder(filesName[i]);// 把每个文件用这个方法进行迭代
				}
				file.delete();// 删除文件夹
			}
		} else {
			System.out.println("所删除的文件不存在");
		}

	}

	/**
	 * 用于递归删除Git文件目录
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteFile(File file) {
		if (file.exists()) {// 判断文件是否存在
			if (file.isFile()) {// 判断是否是文件
				file.delete();// 删除文件
			} else if (file.isDirectory()) {// 否则如果它是一个目录
				File[] files = file.listFiles();// 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) {// 遍历目录下所有的文件
					this.deleteFile(files[i]);// 把每个文件用这个方法进行迭代
				}
				file.delete();// 删除文件夹
			}
		} else {
			System.out.println("所删除的文件不存在");
		}
	}

	/**
	 * 用于Git-add文件到缓冲区
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws GitAPIException
	 * @throws NoFilepatternException
	 */
	public void gitAddFile(String filePath, String fileName)
			throws IOException, NoFilepatternException, GitAPIException {
		Git git = Git.open(new File(filePath));
		git.add().addFilepattern(fileName).call(); // 相当与git add -A添加所有的变更文件git.add().addFilepattern("*.java")这种形式是不支持的
		// git.add().addFilepattern("src/main/java/").call(); // 添加目录，可以把目录下的文件都添加到暂存区
		// jgit当前还不支持模式匹配的方式，例如*.java
	}

	/**
	 * 用于Git-rm -f将文件移出缓冲区和工作区,没有add直接rm会无效,有add但没有commit直接rm会git status看不到记录,
	 * 
	 * @throws IOException
	 * @throws GitAPIException
	 * @throws NoFilepatternException 删除只能通过这个来删除,手动删除的话用add操作无法更新
	 */
	public void gitRmFile(String filePath, String fileName)
			throws IOException, NoFilepatternException, GitAPIException {
		Git git = Git.open(new File(filePath));
		git.rm().addFilepattern(fileName).call();
	}

	/**
	 * 用于Git-commit文件到缓冲区
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws GitAPIException
	 * @throws AbortedByHookException
	 * @throws WrongRepositoryStateException
	 * @throws ConcurrentRefUpdateException
	 * @throws UnmergedPathsException
	 * @throws NoMessageException
	 * @throws NoHeadException
	 */
	public void gitCommitFile(String filePath, String massage)
			throws IOException, NoHeadException, NoMessageException, UnmergedPathsException,
			ConcurrentRefUpdateException, WrongRepositoryStateException, AbortedByHookException, GitAPIException {
		Git git = Git.open(new File(filePath));
		CommitCommand commitCommand = git.commit().setMessage(massage).setAllowEmpty(true);
		commitCommand.call();
	}

	/**
	 * 查看git文件夹状态(相当于git status)
	 * 
	 * @throws IOException
	 * @throws GitAPIException
	 * @throws NoWorkTreeException
	 */
	public StatusList getStatus(String filePath) throws IOException, NoWorkTreeException, GitAPIException {
		Git git = Git.open(new File(filePath));
		StatusList sl = new StatusList();
		Status status = git.status().call(); // 返回的值都是相对工作区的路径，而不是绝对路径
		List<String> addFile = new ArrayList<String>();
		List<String> removeFile = new ArrayList<String>();
		List<String> modifiedFile = new ArrayList<String>();
		List<String> untrackedFile = new ArrayList<String>();
		List<String> confictingFile = new ArrayList<String>();
		List<String> missingFile = new ArrayList<String>();
		System.out.println(status.getAdded().isEmpty());
		status.getAdded().forEach(it -> addFile.add(it)); // git add命令后会看到变化(one.doc),返回缓冲区里面的文件列表
		status.getRemoved().forEach(it -> removeFile.add(it)); /// git rm命令会看到变化，从暂存区和工作区删除的文件列表,只有commit过的才会被返回
		status.getModified().forEach(it -> modifiedFile.add(it)); // 修改的文件列表(one.doc)
		status.getUntracked().forEach(it -> untrackedFile.add(it)); // 工作区新增的文件列表(未跟踪:打开文件没有关闭也会有这个状态)(one.doc)
		status.getConflicting().forEach(it -> confictingFile.add(it)); // 冲突的文件列表
		status.getMissing().forEach(it -> missingFile.add(it)); // 工作区删除的文件列表(即你手动删除的文件)
		sl.setAddFile(addFile);
		sl.setRemoveFile(removeFile);
		sl.setModifiedFile(modifiedFile);
		sl.setRemoveFile(removeFile);
		sl.setUntrackedFile(untrackedFile);
		sl.setConfictingFile(confictingFile);
		sl.setMissingFile(missingFile);
		return sl;
	}

	/**
	 * 查看git提交日志(相当于git log)
	 * 
	 * @throws IOException
	 * @throws GitAPIException
	 * @throws NoHeadException
	 */
	public List<LogEnity> getLog(String filePath) throws IOException, NoHeadException, GitAPIException {
		Git git = Git.open(new File(filePath));
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Iterable<RevCommit> result = git.log().setRevFilter(new RevFilter() {
			@Override
			// 将日志结构按提交人的名字进行筛选,这里选择不筛选
			public boolean include(RevWalk walker, RevCommit cmit)
					throws StopWalkException, MissingObjectException, IncorrectObjectTypeException, IOException {
				return cmit.getAuthorIdent().getName().equals(cmit.getAuthorIdent().getName());
			}

			@Override
			public RevFilter clone() {
				return this;
			}
		}).call();
		List<LogEnity> ls = new ArrayList<LogEnity>();
		result.forEach(commit -> {
			LogEnity log = new LogEnity();
			PersonIdent authoIdent = commit.getAuthorIdent();
			log.setCommitAuthor(authoIdent.getName() + "     <" + authoIdent.getEmailAddress() + ">");
			log.setCommitId(commit.getId().name());
			log.setCommitMassage(commit.getShortMessage());
			log.setCommitDate(format.format(authoIdent.getWhen()));
			ls.add(log);
		});
		return ls;
	}

	/**
	 * 查看特定文件具体被前后两个版本的差异(相当于git diff)
	 * 
	 * @throws IOException
	 * @throws GitAPIException
	 */
	public void getDiff(String filePath, String fileName) throws IOException, GitAPIException {
//		Git git = Git.open(new File(filePath));
//		Iterable<DiffEntry> result=git.diff().call();
//		ByteArrayOutputStream out = new ByteArrayOutputStream();  
//        DiffFormatter df = new DiffFormatter(out); 
//        //设置比较器为忽略空白字符对比（Ignores all whitespace）
//        df.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
//        df.setRepository(git.getRepository().res); 
//        System.out.println("------------------------------start-----------------------------");
//        //每一个diffEntry都是第个文件版本之间的变动差异
//        for (DiffEntry diffEntry : result) { 
//        	//打印文件差异具体内容
//            df.format(diffEntry);  
//            String diffText = out.toString("UTF-8");  
//            System.out.println(diffText);  
//            
//            //获取文件差异位置，从而统计差异的行数，如增加行数，减少行数
//            FileHeader fileHeader = df.toFileHeader(diffEntry);
//            List<HunkHeader> hunks = (List<HunkHeader>) fileHeader.getHunks();
//            int addSize = 0;
//            int subSize = 0;
//            for(HunkHeader hunkHeader:hunks){
//            	EditList editList = hunkHeader.toEditList();
//            	for(Edit edit : editList){
//            		subSize += edit.getEndA()-edit.getBeginA();
//            		addSize += edit.getEndB()-edit.getBeginB();
//            		
//            	}
//            }
//            System.out.println("addSize="+addSize);
//            System.out.println("subSize="+subSize);
//            System.out.println("------------------------------end-----------------------------");
//            out.reset();  
//       }
//        out.reset(); 
//	}
//		Git git = Git.open(new File(filePath));
//		List<RevCommit> list=new ArrayList<RevCommit>();
//	    Iterable<RevCommit> iterable=git.log().setMaxCount(2).call();
//	    for(RevCommit revCommit:iterable){
//	                list.add(revCommit);
//	        }
//	    if(list.size()==2){
//	    	String newCommitId=list.get(0).getId().name();
//	    	System.out.println(newCommitId);
//	    	ByteArrayOutputStream outputStream=read(newCommitId,git,"test.txt");
//	    	System.out.println(outputStream.toString("UTF-8"));
//	    }

		Git git = Git.open(new File(filePath));
		Repository repository = git.getRepository();
		List<RevCommit> list = new ArrayList<RevCommit>();
		Iterable<RevCommit> iterable = git.log().addPath(fileName).setMaxCount(2).call();
		for (RevCommit revCommit : iterable) {
			list.add(revCommit);
		}
		if (list.size() == 2) {
			AbstractTreeIterator newCommit = getAbstractTreeIterator(list.get(0), repository);
			AbstractTreeIterator oldCommit = getAbstractTreeIterator(list.get(1), repository);
			List<DiffEntry> diff = git.diff().setOldTree(oldCommit).setNewTree(newCommit).call();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DiffFormatter diffFormatter = new DiffFormatter(outputStream);
			// 设置比较器为忽略空白字符对比（Ignores all whitespace）
			diffFormatter.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
			diffFormatter.setRepository(repository); // 这里为什么还要设置它
			int i = 1;
			for (DiffEntry diffEntry : diff) {
				diffFormatter.format(diffEntry);
				System.out.println(i + ":" + outputStream.toString("UTF-8"));
				outputStream.reset();
				i++;
			}
		}

		git.close();

	}

	public static AbstractTreeIterator getAbstractTreeIterator(RevCommit commit, Repository repository) {
		RevWalk revWalk = new RevWalk(repository);
		CanonicalTreeParser treeParser = null;
		try {
			RevTree revTree = revWalk.parseTree(commit.getTree().getId());
			treeParser = new CanonicalTreeParser();
			treeParser.reset(repository.newObjectReader(), revTree.getId());
			revWalk.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return treeParser;
	}

	public static ByteArrayOutputStream read(String revision, Git git, String fileName) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Repository repository = null;
		try {
			// gitDir表示git库目录
			// Git git = Git.open(new File("gitProject"));
			repository = git.getRepository();
			RevWalk walk = new RevWalk(repository);
			ObjectId objId = repository.resolve(revision);
			RevCommit revCommit = walk.parseCommit(objId);
			RevTree revTree = revCommit.getTree();
			walk.close();
			// child表示相对git库的文件路径
			TreeWalk treeWalk = TreeWalk.forPath(repository, fileName, revTree);
			if (treeWalk == null)
				System.out.println("null");
			ObjectId blobId = treeWalk.getObjectId(0);
			ObjectLoader loader = repository.open(blobId);
			loader.copyTo(out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JGitInternalException e) {
			e.printStackTrace();
		} finally {
			if (repository != null)
				repository.close();
		}
		return out;
	}

	/**
	 * 查看前后两个版本的差异(相当于git diff)
	 * 
	 * @throws IOException
	 * @throws NoHeadException
	 * @throws GitAPIException
	 */
	public void getDiff(String filePath) throws IOException, NoHeadException, GitAPIException {
		Git git = Git.open(new File(filePath));
		Repository repository = git.getRepository();
		List<RevCommit> list = new ArrayList<RevCommit>();
		Iterable<RevCommit> iterable = git.log().setMaxCount(2).call();
		for (RevCommit revCommit : iterable) {
			list.add(revCommit);
		}
		if (list.size() == 2) {
			AbstractTreeIterator newCommit = getAbstractTreeIterator(list.get(0), repository);
			AbstractTreeIterator oldCommit = getAbstractTreeIterator(list.get(1), repository);
			List<DiffEntry> diff = git.diff().setOldTree(oldCommit).setNewTree(newCommit).call();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			DiffFormatter diffFormatter = new DiffFormatter(outputStream);
			// 设置比较器为忽略空白字符对比（Ignores all whitespace）
			diffFormatter.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
			diffFormatter.setRepository(repository); // 这里为什么还要设置它
			int i = 1;
			for (DiffEntry diffEntry : diff) {
				// 打印文件差异具体内容
				diffFormatter.format(diffEntry);
				System.out.println(i + ":" + outputStream.toString("UTF-8"));
				outputStream.reset();
				i++;
			}
		}
		git.close();
	}

	/**
	 * 回退到上一个版本(相当于git reset --hard HEAD^)
	 * 
	 * @throws IOException
	 * @throws GitAPIException
	 * @throws CheckoutConflictException
	 * 
	 */
	public void gitReset(String filePath) throws IOException, CheckoutConflictException, GitAPIException {
		Git git = Git.open(new File(filePath));
		Repository repository = git.getRepository();
		RevWalk walk = new RevWalk(repository);
		Ref head = repository.findRef("HEAD");
		RevCommit revCommit = walk.parseCommit(head.getObjectId());
		String preVision = revCommit.getParent(0).getName();
		git.reset().setMode(ResetType.HARD).setRef(preVision).call();
		repository.close();
		walk.close();
	}

	/**
	 * 回退到指定版本(相当于git reset --hard 版本号)
	 * 
	 * @throws IOException
	 * @throws GitAPIException
	 * @throws CheckoutConflictException
	 * 
	 */
	public void gitReset(String filePath, String version)
			throws CheckoutConflictException, GitAPIException, IOException {
		Git git = Git.open(new File(filePath));
		git.reset().setMode(ResetType.HARD).setRef(version).call();
	}

	/**
	 * 创建分支(相当于git branch <name> )
	 * 
	 * @throws IOException
	 * @throws GitAPIException
	 * @throws InvalidRefNameException
	 * @throws RefNotFoundException
	 * @throws RefAlreadyExistsException
	 * 
	 */
	public void gitCreateBranch(String filePath, String bname) throws IOException, RefAlreadyExistsException,
			RefNotFoundException, InvalidRefNameException, GitAPIException {
		Git git = Git.open(new File(filePath));
		git.branchCreate().setName(bname).call();
	}

	/**
	 * 删除分支(相当于git branch -d<name> )
	 * 
	 * @throws GitAPIException
	 * @throws CannotDeleteCurrentBranchException
	 * @throws NotMergedException
	 * @throws IOException
	 */
	public void gitDeleteBranch(String filePath, String bname)
			throws NotMergedException, CannotDeleteCurrentBranchException, GitAPIException, IOException {
		Git git = Git.open(new File(filePath));
		git.branchDelete().setBranchNames(bname).call();
	}

	/**
	 * 切换分支(相当于git checkout <name> 如果有文件没有commit就不会执行)
	 * 
	 * @throws IOException
	 * @throws GitAPIException
	 * @throws CheckoutConflictException
	 * @throws InvalidRefNameException
	 * @throws RefNotFoundException
	 * @throws RefAlreadyExistsException
	 */
	public void gitCheckOutBranch(String filePath, String bname) throws IOException, RefAlreadyExistsException,
			RefNotFoundException, InvalidRefNameException, CheckoutConflictException, GitAPIException {
		Git git = Git.open(new File(filePath));
		git.checkout().setName(bname).call();
	}

	/**
	 * 把某个分支合并到当前分支(相当于git merge <name>)
	 * 
	 * @throws IOException
	 * @throws GitAPIException 
	 * 
	 */
	public void gitMerge(String filePath, String bname) throws IOException, GitAPIException {
		Git git = Git.open(new File(filePath));
		MergeCommand mgCmd = git.merge();
		Ref ref = git.branchList().setContains(bname).call().get(0);
		mgCmd.include(ref); // ref被认为是对分支的引用
		MergeResult res = mgCmd.call(); // 实际上做合并
		System.out.println(res.getMergeStatus());
		if(res.getMergeStatus().equals(MergeResult.MergeStatus.CONFLICTING)){
			 System.out.println(res.getConflicts().toString()); 
			 //告知用户他必须处理冲突
		}
	}
	
	/**
	 * 把远程仓库clone到指定目录(相当于git clone )
	 * @throws TransportException 
	 * @throws InvalidRemoteException 
	 * 
	 * @throws IOException
	 * @throws GitAPIException 
	 * 
	 */
	public void gitClone(String url,String filePath) throws InvalidRemoteException, TransportException, GitAPIException {
		Git.cloneRepository().setURI(url)
        .setDirectory(new File(filePath)).setCredentialsProvider(new UsernamePasswordCredentialsProvider("phper", "123456")).call();
	}
	
	/**
	 * 在linux的gitroot目录建立远程仓库
	 * @throws IOException 
	 * 
	 */
	public void gitCreatRemote(String url,String userName,String password,String remoteName) throws IOException {
		SshConnect sc=new SshConnect(url,userName,password);
		Connection conn=null;
		if((conn=sc.login())!=null) {
			String cmd="sh /gitroot/creatgit.sh "+remoteName;
			sc.exec(conn,cmd);
		}
		else 
			System.out.println("建库失败!");
	}
	
	/**
	 * 向远程仓库某个分支push((相当于git push))
	 * 或者用于新建分支向远程仓库的推送
	 * @throws GitAPIException 
	 * @throws TransportException 
	 * @throws InvalidRemoteException 
	 * @throws IOException 
	 * 
	 */
	public void gitPush(String filePath,String branchName,String url,String userName,String password) throws InvalidRemoteException, TransportException, GitAPIException, IOException {
		Git git = Git.open(new File(filePath));
		Ref ref = git.branchList().setContains(branchName).call().get(0);
		git.push().add(ref).setCredentialsProvider(new UsernamePasswordCredentialsProvider(userName, password)).setRemote(url).call();
		git.close();
	}
	
	/**
	 * 从远程仓库拉取((相当于git pull))
	 * 或者用于新建分支向远程仓库的推送
	 * @throws GitAPIException 
	 * @throws TransportException 
	 * @throws NoHeadException 
	 * @throws RefNotAdvertisedException 
	 * @throws RefNotFoundException 
	 * @throws CanceledException 
	 * @throws InvalidRemoteException 
	 * @throws InvalidConfigurationException 
	 * @throws WrongRepositoryStateException 
	 * @throws IOException 
	 * 
	 */
	public void gitPull(String filePath,String url,String userName,String password) throws WrongRepositoryStateException, InvalidConfigurationException, InvalidRemoteException, CanceledException, RefNotFoundException, RefNotAdvertisedException, NoHeadException, TransportException, GitAPIException, IOException {
		Git git = Git.open(new File(filePath));
		git.pull().setCredentialsProvider(new UsernamePasswordCredentialsProvider(userName, password)).setRemote(url).call();
		git.close();
	}
}