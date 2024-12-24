重置master分支：
git checkout --orphan latest_branch
git add -A
git commit -am "commit message"
git branch -D master
git branch -m master


git branch -m latest_branch master
git push -f origin master 
git push origin latest_branch:master -f
git reset --hard origin/master


有时候我们项目中会配置很多内容，在新起一个项目的时候，重新从头配置比较浪费时间，但是直接将原来项目拿来修改远程地址后，项目里还会保存之前的提交历史和tag；这个时候我们就需要下面的操作来清空这些内容。

1.创建新分支（这个命名是基于当前所在分支新建一个赤裸裸的分支，没有任何的提交历史，但是当前分支的内容一应俱全。新建的分支，严格意义上说，还不是一个分支，因为HEAD指向的引用中没有commit值，只有在进行一次提交后，它才算得上真正的分支。）

  git checkout --orphan latest_branch

2.添加所有文件

  git add .

3.commit代码

  git commit -m "xxx"

4.删除原来的master分支

  git branch -D master
    git branch -D main

5.把当前分支重命名为master

  git branch -m master 
  git branch -m main 

6.最后把代码推送到远程仓库（有些仓库有master分支保护，不允许强制push，需要在远程仓库项目里暂时把项目保护关掉才能推送）

  git push -f origin master
    git push -f origin main
  
  