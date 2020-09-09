@echo off
start cmd /k "gradle clean -Dprofile=releaseVersion && gradle releaseVersion -Dprofile=releaseVersion"
