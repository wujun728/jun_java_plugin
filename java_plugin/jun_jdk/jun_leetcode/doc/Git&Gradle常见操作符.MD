# 常用Git指令
|功能                       | 指令                                                | 
| ----------------------------------- | ------------------------------------------------------------ | 
|更新，合并到同版本 |git pull|
|更新，合并到新版本 |git fetch|
|强制回退版本 |git reset --hard version|
|添加多个远程仓库（单工程多仓库联合开发）|git remote add [origin-name] https://github.com/xxx.git|
|刷新远程分支| git remote update origin -p|
|强制推送更新（配合revert使用）| git push -f origin master|
|查看所有远程分支| git branch -a|
|提取/合并某分支的部分文件| git checkout [branch_name] [file_name]|
|把某个commit id的提交合并到当前分支| git cherry-pick commit id |
|删除某一个分支| git branch -d [branch_name] |
|修改Git提交用户名| git config --global user.name "BzCoder" |
|修改Git提交邮箱（GitHub提交统计是按照邮箱标记的）| git config --global user.email "BzCoder" |
|合并多个提交为一条|git merge --squash branchname|
|Git设置为大小写敏感|git config core.ignorecase false|
|查看项目的成员提交情况（剔除merge）|git shortlog -sn --no-merges|
|查看某一次提交属于哪个分支|git branch -r --contains COMMIT_ID|


# 常用Gradle指令
|功能                       | 指令                                                | 
| ----------------------------------- | ------------------------------------------------------------ | 
|修改Gradle缓存时间 |  configurations.all { resolutionStrategy.cacheDynamicVersionsFor 1, 'seconds' }|
