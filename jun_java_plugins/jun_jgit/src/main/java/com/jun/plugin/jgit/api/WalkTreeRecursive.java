package com.jun.plugin.jgit.api;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

import com.jun.plugin.jgit.helper.CookbookHelper;

import java.io.IOException;

/**
 * Simple snippet which shows how to use RevWalk to iterate over items in a file-tree.
 *
 * See {@link WalkTreeNonRecursive} for a different usage of the {@link TreeWalk} class.
 *
 * @author dominik.stadler at gmx.at
 */
public class WalkTreeRecursive {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            Ref head = repository.findRef("HEAD");

            // a RevWalk allows to walk over commits based on some filtering that is defined
            try (RevWalk walk = new RevWalk(repository)) {
                RevCommit commit = walk.parseCommit(head.getObjectId());
                RevTree tree = commit.getTree();
                System.out.println("Having tree: " + tree);

                // now use a TreeWalk to iterate over all files in the Tree recursively
                // you can set Filters to narrow down the results if needed
                try (TreeWalk treeWalk = new TreeWalk(repository)) {
                    treeWalk.addTree(tree);
                    treeWalk.setRecursive(true);
                    while (treeWalk.next()) {
                        System.out.println("found: " + treeWalk.getPathString());
                    }
                }
            }
        }
    }
}
