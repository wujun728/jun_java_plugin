package com.jun.plugin.jgit.porcelain;

/*
   Copyright 2013, 2014 Dominik Stadler

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import com.jun.plugin.jgit.helper.CookbookHelper;



/**
 * Simple snippet which shows how to use commands for stashing changes.
 *
 * @author dominik.stadler at gmx.at
 */
public class CreateListApplyAndDropStash {

    public static void main(String[] args) throws IOException, GitAPIException {
        final File localPath;
        // prepare a new test-repository
        try (Repository repository = CookbookHelper.createNewRepository()) {
            localPath = repository.getWorkTree();

            try (Git git = new Git(repository)) {
                // create a file
                File file1 = new File(repository.getDirectory().getParent(), "testfile");
                FileUtils.writeStringToFile(file1, "some text", "UTF-8");
                File file2 = new File(repository.getDirectory().getParent(), "testfile2");
                FileUtils.writeStringToFile(file2, "some text", "UTF-8");

                // add and commit the file
                git.add()
                        .addFilepattern("testfile")
                        .call();
                git.add()
                        .addFilepattern("testfile2")
                        .call();
                git.commit()
                        .setMessage("Added testfiles")
                        .call();

                // then modify the file
                FileUtils.writeStringToFile(file1, "some more text", "UTF-8", true);

                // push the changes to a new stash
                RevCommit stash = git.stashCreate()
                        .call();

                System.out.println("Created stash " + stash);

                // then modify the 2nd file
                FileUtils.writeStringToFile(file2, "some more text", "UTF-8", true);

                // push the changes to a new stash
                stash = git.stashCreate()
                        .call();

                System.out.println("Created stash " + stash);

                // list the stashes
                Collection<RevCommit> stashes = git.stashList().call();
                for(RevCommit rev : stashes) {
                    System.out.println("Found stash: " + rev + ": " + rev.getFullMessage());
                }

                // drop the 1st stash without applying it
                ObjectId call = git.stashDrop().setStashRef(0).call();
                System.out.println("StashDrop returned: " + call);

                ObjectId applied = git.stashApply().setStashRef(stash.getName()).call();
                System.out.println("Applied 2nd stash as: " + applied);
            }
        }

        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(localPath);
    }
}
