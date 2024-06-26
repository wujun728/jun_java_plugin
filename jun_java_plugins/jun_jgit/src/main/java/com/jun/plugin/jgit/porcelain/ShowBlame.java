package com.jun.plugin.jgit.porcelain;

/*
    Copyright 2013, 2014, 2017 Dominik Stadler

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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.eclipse.jgit.api.BlameCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.blame.BlameResult;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import com.jun.plugin.jgit.helper.CookbookHelper;



/**
 * Simple snippet which shows how to get a diff showing who
 * changed which line in a file.
 *
 * It uses HEAD~~ to select the version of README.md two commits ago
 * and reads the blame information for it.
 *
 * Then it prints out the number of lines and the actual number of lines in the
 * latest/local version of the file.
 *
 * @author dominik.stadler at gmx.at
 */
public class ShowBlame {

    public static void main(String[] args) throws IOException, GitAPIException {
        // prepare a new test-repository
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            BlameCommand blamer = new BlameCommand(repository);
            ObjectId commitID = repository.resolve("HEAD~~");
            blamer.setStartCommit(commitID);
            blamer.setFilePath("README.md");
            BlameResult blame = blamer.call();

            // read the number of lines from the given revision, this excludes changes from the last two commits due to the "~~" above
            int lines = countLinesOfFileInCommit(repository, commitID, "README.md");
            for (int i = 0; i < lines; i++) {
                RevCommit commit = blame.getSourceCommit(i);
                System.out.println("Line: " + i + ": " + commit);
            }

            final int currentLines;
            try (final FileInputStream input = new FileInputStream("README.md")) {
                currentLines = IOUtils.readLines(input, "UTF-8").size();
            }

            System.out.println("Displayed commits responsible for " + lines + " lines of README.md, current version has " + currentLines + " lines");
        }
    }

    private static int countLinesOfFileInCommit(Repository repository, ObjectId commitID, String name) throws IOException {
        try (RevWalk revWalk = new RevWalk(repository)) {
            RevCommit commit = revWalk.parseCommit(commitID);
            RevTree tree = commit.getTree();
            System.out.println("Having tree: " + tree);

            // now try to find a specific file
            try (TreeWalk treeWalk = new TreeWalk(repository)) {
                treeWalk.addTree(tree);
                treeWalk.setRecursive(true);
                treeWalk.setFilter(PathFilter.create(name));
                if (!treeWalk.next()) {
                    throw new IllegalStateException("Did not find expected file 'README.md'");
                }

                ObjectId objectId = treeWalk.getObjectId(0);
                ObjectLoader loader = repository.open(objectId);

                // load the content of the file into a stream
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                loader.copyTo(stream);

                revWalk.dispose();

                return IOUtils.readLines(new ByteArrayInputStream(stream.toByteArray()), "UTF-8").size();
            }
        }
    }
}
