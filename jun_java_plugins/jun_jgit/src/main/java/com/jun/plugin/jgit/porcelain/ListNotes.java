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

import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.notes.Note;

import com.jun.plugin.jgit.helper.CookbookHelper;



/**
 * Simple snippet which shows how to load Notes in a Git repository
 *
 * @author dominik.stadler at gmx.at
 */
public class ListNotes {

    public static void main(String[] args) throws IOException, GitAPIException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            try (Git git = new Git(repository)) {
                List<Note> call = git.notesList().call();
                System.out.println("Listing " + call.size() + " notes");
                for (Note note : call) {
                    System.out.println("Note: " + note + " " + note.getName() + " " + note.getData().getName() + "\nContent: ");

                    // displaying the contents of the note is done via a simple blob-read
                    ObjectLoader loader = repository.open(note.getData());
                    loader.copyTo(System.out);
                }
            }
        }
    }
}
