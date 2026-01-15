Real name: wujun728
Email address: wujun728@163.com
You selected this USER-ID:
"wujun728 <wujun728@163.com>"

Change (N)ame, (E)mail, or (O)kay/(Q)uit? O
We need to generate a lot of random bytes. It is a good idea to perform
some other action (type on the keyboard, move the mouse, utilize the
disks) during the prime generation; this gives the random number
generator a better chance to gain enough entropy.
We need to generate a lot of random bytes. It is a good idea to perform
some other action (type on the keyboard, move the mouse, utilize the
disks) during the prime generation; this gives the random number
generator a better chance to gain enough entropy.
gpg: revocation certificate stored as 'C:\\Users\\Administrator\\AppData\\Roaming\\gnupg\\openpgp-revocs.d\\ECB23E2FDD65521C21EAD5360ECD3DDD5A391E46.rev'
public and secret key created and signed.

pub   ed25519 2024-07-08 [SC] [expires: 2026-07-08]
ECB23E2FDD65521C21EAD5360ECD3DDD5A391E46
uid                      wujun728 <wujun728@163.com>
sub   cv25519 2024-07-08 [E] [expires: 2026-07-08]



d:\workspace\github\jun-spring-boot-starter\jun-uidgenerator-spring-boot-starter>gpg --generate-key
gpg (GnuPG) 2.4.0; Copyright (C) 2021 g10 Code GmbH
This is free software: you are free to change and redistribute it.
There is NO WARRANTY, to the extent permitted by law.

Note: Use "gpg --full-generate-key" for a full featured key generation dialog.

GnuPG needs to construct a user ID to identify your key.

Real name: wujun728
Email address: wujun728@163.com
You selected this USER-ID:
"wujun728 <wujun728@163.com>"

Change (N)ame, (E)mail, or (O)kay/(Q)uit? O
We need to generate a lot of random bytes. It is a good idea to perform
some other action (type on the keyboard, move the mouse, utilize the
disks) during the prime generation; this gives the random number
generator a better chance to gain enough entropy.
We need to generate a lot of random bytes. It is a good idea to perform
some other action (type on the keyboard, move the mouse, utilize the
disks) during the prime generation; this gives the random number
generator a better chance to gain enough entropy.
gpg: revocation certificate stored as 'C:\\Users\\Administrator\\AppData\\Roaming\\gnupg\\openpgp-revocs.d\\EF79913B9AF7AF55BAEA1B56CB18FA1CB53CD02D.rev'
public and secret key created and signed.

pub   ed25519 2023-11-28 [SC] [expires: 2025-11-27]
EF79913B9AF7AF55BAEA1B56CB18FA1CB53CD02D
uid                      wujun728 <wujun728@163.com>
sub   cv25519 2023-11-28 [E] [expires: 2025-11-27]



GPG签名
注意：这一步也只要做一次，除非你的GPG签名过期了，或者被你删掉了，要么就是你换电脑了
兄弟们，废话不多说，直接上链接：www.gnupg.org/download/
咱们先下载一个gpg签名工具，大家根据自己的操作系统来；安装完毕后，直接在控制台输入命令：
gpg --generate-key
复制代码
后续就根据自己情况输入，最后回车会生成公钥和私钥，我们接下来需要把生成的公钥上传到公共服务器供 sonatype 验证。
可以通过以下命令把公钥发送给公共服务器：
gpg --keyserver pgp.mit.edu --send-keys ECB23E2FDD65521C21EAD5360ECD3DDD5A391E46
gpg --keyserver pgp.mit.edu --send-keys [公钥]
复制代码
或
gpg --keyserver keyserver.ubuntu.com --send-keys ECB23E2FDD65521C21EAD5360ECD3DDD5A391E46
gpg --keyserver keyserver.ubuntu.com --send-keys [公钥]

gpg --keyserver pgp.mit.edu --send-keys ECB23E2FDD65521C21EAD5360ECD3DDD5A391E46
gpg --keyserver keyserver.ubuntu.com --recv-keys ECB23E2FDD65521C21EAD5360ECD3DDD5A391E46
gpg --keyserver keys.openpgp.org --send-keys ECB23E2FDD65521C21EAD5360ECD3DDD5A391E46

复制代码
或
gpg --keyserver keys.openpgp.org --send-keys ECB23E2FDD65521C21EAD5360ECD3DDD5A391E46
gpg --keyserver keys.openpgp.org --send-keys [公钥]
复制代码
以上分别为向三个公共服务器发生公钥，只要其中一个成功即可。


mvn clean install deploy -P release
mvn clean   deploy 

https://s01.oss.sonatype.org/#stagingRepositories


https://developer.aliyun.com/article/1116049

