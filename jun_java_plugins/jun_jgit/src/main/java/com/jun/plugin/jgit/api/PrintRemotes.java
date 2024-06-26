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
import java.util.Set;

import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Repository;

import com.jun.plugin.jgit.helper.CookbookHelper;

/**
 * Simple snippet which shows how to retrieve the list of remotes from the configuration
 */
public class PrintRemotes {

    public static void main(String[] args) throws IOException {
        try (Repository repository = CookbookHelper.openJGitCookbookRepository()) {
            Config storedConfig = repository.getConfig();
            Set<String> remotes = storedConfig.getSubsections("remote");

            for (String remoteName : remotes) {
                String url = storedConfig.getString("remote", remoteName, "url");
                System.out.println(remoteName + " " + url);
            }
        }
    }
}
