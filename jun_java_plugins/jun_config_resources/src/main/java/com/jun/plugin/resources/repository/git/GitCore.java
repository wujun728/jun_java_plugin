package com.jun.plugin.resources.repository.git;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jun.plugin.resources.repository.RemoteRepository;
import com.jun.plugin.resources.repository.git.config.GitConfig;
import com.jun.plugin.resources.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * Created By Hong on 2018/8/13
 **/
public class GitCore implements RemoteRepository {

    private static Logger LOG = LoggerFactory.getLogger(GitCore.class);
    private static GitConfig CONFIG = GitConfig.get();

    static {
        //第一次初始化，删除本地git仓库
        FileUtils.delNotEmptyDir(new File(CONFIG.getLocalDir()));
    }

    @Override
    public boolean cloneRepository() {
        CredentialsProvider provider = GitBuilder.createProvider();
        CloneCommand command = Git.cloneRepository();
        command.setURI(CONFIG.getUri());
        command.setBranch(CONFIG.getBranch());
        command.setDirectory(new File(CONFIG.getLocalDir()));
        command.setCredentialsProvider(provider);
        try {
            Git git = command.call();
            if (LOG.isInfoEnabled()) {
                LOG.info("Git clone successful, tag name is" + git.tag().getName());
            }
            return true;
        } catch (GitAPIException e) {
            e.printStackTrace();
            if (LOG.isErrorEnabled()) {
                LOG.error("Git clone fail.", e);
            }
        }
        return false;
    }

    @Override
    public boolean pullRepository() {
        CredentialsProvider provider = GitBuilder.createProvider();
        try {
            Git git = new Git(new FileRepository(CONFIG.getLocalDir().concat("/.git")));
            PullResult result = git.pull()
                    .setRemoteBranchName(CONFIG.getBranch())
                    .setCredentialsProvider(provider)
                    .call();
            return result.isSuccessful();
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Git pull fail.", e);
            }
        }
        return false;
    }

    @Override
    public List<String> listFileAll() {
        return FileUtils.listAllFiles(new File(CONFIG.getLocalDir()));
    }
}
