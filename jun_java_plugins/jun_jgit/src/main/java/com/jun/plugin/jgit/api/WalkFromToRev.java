package com.jun.plugin.jgit.api;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import com.jun.plugin.jgit.helper.CookbookHelper;

import java.io.IOException;


/**
 * Simple snippet which shows how to use RevWalk to iterate over objects
 */
public class WalkFromToRev {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            String from = "3408efc41a51555d488d30d8a91ea560c5e13311";
            String to = "7228de6ebe2a3087118562414061af4e189624c0";

            // a RevWalk allows to walk over commits based on some filtering that is defined
            try (RevWalk walk = new RevWalk(repository)) {
                RevCommit commit = walk.parseCommit(repository.resolve(to));
                System.out.println("Start-Commit: " + commit);

                System.out.println("Walking all commits starting at " + to + " until we find " + from);
                walk.markStart(commit);
                int count = 0;
                for (RevCommit rev : walk) {
                    System.out.println("Commit: " + rev);
                    count++;

                    if(rev.getId().getName().equals(from)) {
                        System.out.println("Found from, stopping walk");
                        break;
                    }
                }
                System.out.println(count);

                walk.dispose();
            }
        }
    }
}
