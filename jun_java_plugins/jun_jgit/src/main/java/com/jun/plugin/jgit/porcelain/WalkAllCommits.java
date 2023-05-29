package com.jun.plugin.jgit.porcelain;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import com.jun.plugin.jgit.helper.CookbookHelper;

import java.io.IOException;

/**
 * Simple snippet which shows how to use RevWalk to quickly iterate over all available commits,
 * not just the ones on the current branch
 */
public class WalkAllCommits {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            try (Git git = new Git(repository)) {
                // use the following instead to list commits on a specific branch
                //ObjectId branchId = repository.resolve("HEAD");
                //Iterable<RevCommit> commits = git.log().add(branchId).call();

                Iterable<RevCommit> commits = git.log().all().call();
                int count = 0;
                for (RevCommit commit : commits) {
                    System.out.println("LogCommit: " + commit);
                    count++;
                }
                System.out.println(count);
            }
        }
    }
}
