package com.jun.plugin.jgit.api;

import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;

import com.jun.plugin.jgit.helper.CookbookHelper;

import java.io.IOException;

/**
 * Simple snippet which shows how to retrieve a Ref for some reference string.
 */
public class ReadBlobContents {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            // the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)
            Ref head = repository.exactRef("refs/heads/master");
            System.out.println("Ref of refs/heads/master: " + head);

            System.out.println("\nPrint contents of head of master branch, i.e. the latest commit information");
            ObjectLoader loader = repository.open(head.getObjectId());
            loader.copyTo(System.out);

            System.out.println("\nPrint contents of tree of head of master branch, i.e. the latest binary tree information");

            // a commit points to a tree
            try (RevWalk walk = new RevWalk(repository)) {
                RevCommit commit = walk.parseCommit(head.getObjectId());
                RevTree tree = walk.parseTree(commit.getTree().getId());
                System.out.println("Found Tree: " + tree);
                loader = repository.open(tree.getId());
                loader.copyTo(System.out);

                walk.dispose();
            }
        }
    }
}
