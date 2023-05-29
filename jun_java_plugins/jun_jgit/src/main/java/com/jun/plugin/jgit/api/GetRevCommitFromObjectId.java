package com.jun.plugin.jgit.api;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import com.jun.plugin.jgit.helper.CookbookHelper;

import java.io.IOException;

/**
 * Simple snippet which shows how to use RevWalk to iterate over objects
 */
public class GetRevCommitFromObjectId {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            Ref head = repository.exactRef("refs/heads/master");
            System.out.println("Found head: " + head);

            // a RevWalk allows to walk over commits based on some filtering that is defined
            try (RevWalk walk = new RevWalk(repository)) {
                RevCommit commit = walk.parseCommit(head.getObjectId());
                System.out.println("Found Commit: " + commit);

                // You can also get the commit for an (abbreviated) SHA
                walk.reset();
                ObjectId id = repository.resolve("38d51408bd");
                RevCommit commitAgain = walk.parseCommit(id);
                System.out.println("Found Commit again: " + commitAgain);

                walk.dispose();
            }
        }
    }
}
