package com.jun.plugin.jgit.unfinished;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.Repository;

import com.jun.plugin.jgit.helper.CookbookHelper;

import java.io.IOException;
import java.util.Collection;
import java.util.List;



/**
 * Note: This snippet is not done and likely does not show anything useful yet
 *
 * @author dominik.stadler at gmx.at
 */
public class ListRefLog {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            try (Git git = new Git(repository)) {
                List<Ref> refs = git.branchList().call();
                for (Ref ref : refs) {
                    System.out.println("Branch: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());

                    listReflog(repository, ref);
                }

                List<Ref> call = git.tagList().call();
                for (Ref ref : call) {
                    System.out.println("Tag: " + ref + " " + ref.getName() + " " + ref.getObjectId().getName());

                    listReflog(repository, ref);
                }
            }
        }
    }

    private static void listReflog(Repository repository, Ref ref) throws GitAPIException {
        /*
         * Ref head = repository.getRef(ref.getName());
         * RevWalk walk = new RevWalk(repository);
         * RevCommit commit = walk.parseCommit(head.getObjectId());
         */

        try (Git git = new Git(repository)) {
            Collection<ReflogEntry> call = git.reflog().setRef(ref.getName()).call();
            for (ReflogEntry reflog : call) {
                System.out.println("Reflog: " + reflog);
            }
        }
    }
}
