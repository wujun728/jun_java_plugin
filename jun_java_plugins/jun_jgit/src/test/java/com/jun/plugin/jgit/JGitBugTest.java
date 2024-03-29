package com.jun.plugin.jgit;

import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Test;

import com.jun.plugin.jgit.helper.CookbookHelper;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * Tests which show issues with JGit that we reported upstream.
 */
public class JGitBugTest {
    @Test
    public void testRevWalkDisposeClosesReader() throws IOException {
        try (Repository repo = CookbookHelper.openJGitCookbookRepository()) {
            try (ObjectReader reader = repo.newObjectReader()) {
                assertNotNull("Need to get back an ObjectReader", reader);
                try (RevWalk walk = new RevWalk(reader)) {
                    walk.dispose();

                    Ref head = repo.exactRef("refs/heads/master");
                    System.out.println("Found head: " + head);
                    assertNotNull("Need to find head on master-branch", head);

                    ObjectLoader loader = reader.open(head.getObjectId());
                    assertNotNull(loader);
                }
            }
        }
    }
}
