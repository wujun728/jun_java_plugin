package com.jun.plugin.jgit.porcelain;

import org.junit.Test;

import com.jun.plugin.jgit.porcelain.AddAndListNoteOfCommit;
import com.jun.plugin.jgit.porcelain.AddFile;
import com.jun.plugin.jgit.porcelain.BlameFile;
import com.jun.plugin.jgit.porcelain.CheckoutGitHubPullRequest;
import com.jun.plugin.jgit.porcelain.CleanUntrackedFiles;
import com.jun.plugin.jgit.porcelain.CloneRemoteRepository;
import com.jun.plugin.jgit.porcelain.CommitAll;
import com.jun.plugin.jgit.porcelain.CommitFile;
import com.jun.plugin.jgit.porcelain.CreateAndDeleteBranch;
import com.jun.plugin.jgit.porcelain.CreateAndDeleteTag;
import com.jun.plugin.jgit.porcelain.CreateArchive;
import com.jun.plugin.jgit.porcelain.CreateCustomFormatArchive;
import com.jun.plugin.jgit.porcelain.CreateListApplyAndDropStash;
import com.jun.plugin.jgit.porcelain.DiffFilesInCommit;
import com.jun.plugin.jgit.porcelain.DiffRenamedFile;
import com.jun.plugin.jgit.porcelain.FetchRemoteCommits;
import com.jun.plugin.jgit.porcelain.FetchRemoteCommitsWithPrune;
import com.jun.plugin.jgit.porcelain.InitRepository;
import com.jun.plugin.jgit.porcelain.ListBranches;
import com.jun.plugin.jgit.porcelain.ListNotes;
import com.jun.plugin.jgit.porcelain.ListRemoteRepository;
import com.jun.plugin.jgit.porcelain.ListRemotes;
import com.jun.plugin.jgit.porcelain.ListTags;
import com.jun.plugin.jgit.porcelain.ListUncommittedChanges;
import com.jun.plugin.jgit.porcelain.MergeChanges;
import com.jun.plugin.jgit.porcelain.RebaseToOriginMaster;
import com.jun.plugin.jgit.porcelain.RevertChanges;
import com.jun.plugin.jgit.porcelain.RevertCommit;
import com.jun.plugin.jgit.porcelain.ShowBlame;
import com.jun.plugin.jgit.porcelain.ShowBranchDiff;
import com.jun.plugin.jgit.porcelain.ShowChangedFilesBetweenCommits;
import com.jun.plugin.jgit.porcelain.ShowFileDiff;
import com.jun.plugin.jgit.porcelain.ShowLog;
import com.jun.plugin.jgit.porcelain.ShowStatus;
import com.jun.plugin.jgit.porcelain.WalkAllCommits;


public class PorcelainTest {
    @Test
    public void runSamples() throws Exception {
        // simply call all the samples to see any severe problems with the samples
        AddAndListNoteOfCommit.main(null);
        AddFile.main(null);
        BlameFile.main(null);
        CheckoutGitHubPullRequest.main(null);
        CleanUntrackedFiles.main(null);
        CloneRemoteRepository.main(null);
        // does not run without changes: CloneRemoteRepositoryWithAuthentication.main(null);
        // TODO: sometimes fails because there are still files open?!: CollectGarbage.main(null);
        CommitAll.main(null);
        CommitFile.main(null);
        CreateAndDeleteBranch.main(null);
        CreateAndDeleteTag.main(null);
        CreateArchive.main(null);
        CreateCustomFormatArchive.main(null);
        CreateListApplyAndDropStash.main(null);
        DiffFilesInCommit.main(null);
        DiffRenamedFile.main(null);
        FetchRemoteCommits.main(null);
        FetchRemoteCommitsWithPrune.main(null);
        InitRepository.main(null);
        ListBranches.main(null);
        ListNotes.main(null);
        ListRemoteRepository.main(null);
        ListRemotes.main(null);
        ListTags.main(null);
        ListUncommittedChanges.main(null);
        MergeChanges.main(null);
        RebaseToOriginMaster.main(null);
        RevertChanges.main(null);
        RevertCommit.main(null);
        ShowBlame.main(null);
        ShowBranchDiff.main(null);
        ShowChangedFilesBetweenCommits.main(null);
        ShowFileDiff.main(null);
        ShowLog.main(null);
        ShowStatus.main(null);
        WalkAllCommits.main(null);
    }
}
