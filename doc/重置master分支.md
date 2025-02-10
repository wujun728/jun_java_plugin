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

git checkout --orphan latest_branch
git push -f origin latest_branch
git push -f origin master --force

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
 
  
 
1、.git目录过大
要解决.git目录过大的问题，可以尝试以下方法：
使用git gc命令清理不再需要的缓存。这将帮助减小仓库的大小。
在命令行中输入以下命令：

git gc --prune=now --aggressive
1
使用git repack -ad命令来重新打包已经提交的文件。
这将有助于减小仓库的大小。在命令行中输入以下命令：

git repack -ad 
2、灵活使用 .gitignore 文件
及时排除仓库不需要的特殊目录或者文件
如下，忽略dist和build文件下内容，忽略格式为zip和exe的文件

/dist
/build
*.zip
*.exe 
3、查看仓库下文件大小
数字1代表查看当前1级子目录

du -d 1 -h 







  
方法一：强制回退法

1. 克隆仓库，但记住不可以加--depth=1这类选项；

2. 使用 git log 命令查询要回滚的 commit_id；

3. 查找最早一次提交到的commit_id；   


4. 备份原仓库目录下所有文件及文件夹(.git文件夹除外)；

5. 强制还原，执行命令：git reset --hard commit_id，之后 HEAD 就会指向此次的提交记录；
git reset --hard 7ee28e8437002722a9d5ff0c7e88ff8cc66dc9de

6. 删除仓库目录下所有文件和文件夹，去除你备份的文件中包含隐私信息的部分，并还原至原仓库目录下；

7. 执行提交新commit前的常规操作：git add . 和 git commit -m "<...>"；
git add .
git commit -m "xxx"

8. 强制推送到github远程仓库：git push origin HEAD --force；
git push origin HEAD --force

9. 此时只剩下第一次提交和本次提交记录，基本达到清空目的： 

 方法二：分支替换法

1. 同方法一克隆仓库；

2. 将当前分支指向一个空分支 latest_branch：git checkout --orphan latest_branch；

3. 备份原仓库目录下所有文件及文件夹(.git文件夹除外)，之后清空该目录(.git文件夹除外)；

4. 去除你备份的文件中包含隐私信息的部分，并还原至原仓库目录下，最后重新添加：git add -A；

5. 提交更改：git commit -am "<...>"；

6. 删除原分支：git branch -D master （新版的github默认分支是main了，请根据实际情况更改，下同）；

7. 将现有临时分支切换到主分支：git branch -m master；

8. 强制推送到远程：git push -f origin master --force
————————————————

                            本文是博主原创文章，未经许可不准转载！
                        
原文链接：https://blog.csdn.net/u012783994/article/details/126339284