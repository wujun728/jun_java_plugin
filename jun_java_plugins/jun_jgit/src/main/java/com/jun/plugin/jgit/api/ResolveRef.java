package com.jun.plugin.jgit.api;

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

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;

import com.jun.plugin.jgit.helper.CookbookHelper;



/**
 * Simple snippet which shows how to retrieve an ObjectId for some name.
 */
public class ResolveRef {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            // basic syntax is similar to getRef()
            ObjectId id = repository.resolve("HEAD");
            System.out.println("ObjectId of HEAD: " + id);

            // however resolve() supports almost all of the git-syntax, where getRef() only works on names
            id = repository.resolve("HEAD^1");
            System.out.println("ObjectId of HEAD: " + id);

            id = repository.resolve("b419522521af553ae2752fd1b609f2aa11062243");
            System.out.println("ObjectId of specific commit: " + id);

            id = repository.resolve("05d18a76875716fbdbd2c200091b40caa06c713d");
            System.out.println("ObjectId of merged commit: " + id);
        }
    }
}
