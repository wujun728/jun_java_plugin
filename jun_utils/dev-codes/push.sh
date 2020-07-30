#! /bin/bash

echo '当前工作目录：'
pwd

git pull

echo '------ commit files ------'
git add -A
git commit -m "update"

echo '------ push files ------'
git push
