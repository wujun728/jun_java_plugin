-- MySQL dump 10.10
--
-- Host: localhost    Database: day15
-- ------------------------------------------------------
-- Server version	5.0.18-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
CREATE TABLE `customers` (
  `ID` varchar(100) NOT NULL,
  `NAME` varchar(100) default NULL,
  `GENDER` varchar(10) default NULL,
  `BIRTHDAY` date default NULL,
  `PHONENUM` varchar(100) default NULL,
  `EMAIL` varchar(100) default NULL,
  `HOBBY` varchar(100) default NULL,
  `TYPE` varchar(100) default NULL,
  `DESCRIPTION` varchar(100) default NULL,
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `customers`
--


/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
LOCK TABLES `customers` WRITE;
INSERT INTO `customers` VALUES ('149DWGN2CP4URM3FDS1ZYY2WWPB0CGMH','大叔控','male','1990-09-08','13820328392',' dd@qq.com','吃饭,睡觉,学java','VVIP','lalalalalala!'),('1FA3YD3PAIRYMW96WW5L2DZRJF77FWYO','奥巴马','male','1990-09-08','1981918191819','aobama@198.com','吃饭,睡觉','VVIP','霸王。。。'),('1GP6PPMUYURE18BLZ9QNG60OT1RHH1KR','擎天柱','male','1220-11-12','88888888888','','学java','VVIP','超级领袖'),('1JLDX8ABMQ9CMB8X2913WZ57OXPLYW36','邪恶小女生','female','1990-09-08','11513114','dadad@qq。吃哦买、',NULL,'VIP',''),('1NMYK5D5PI87HUVH42QP4K6XQ55R86Y7','小怪兽','male','1990-09-12','51851851','xgs@qq.com','吃饭,睡觉,学java','VVIP','奥特曼打小怪兽'),('29CMHBGZ9A5YKPZTJU6C3E5CVI7ZBK3C','Nero','male','1990-09-08','123','n@itheim.com','吃饭,睡觉,学java','VVIP','xxxxxxxxxxxxxxxxx'),('2CEPLJ5S10SXWLLJT3XHU2A33G8HPGBS','夜尽天明','male','1990-09-08','10010','','吃饭,睡觉,学java','VVIP','还是一个帅哥'),('2S2ZFAJL2HKNTE5P2Z7318CODHVUNG6D','陶许','male','1990-09-08','250','genxinzou@gmail.com','学java','VIP','这是一个好人'),('2WEA9LJDBW52EUJ8UODOGERR7LQO3PI0','王二狗','male','1990-09-08','123','22@sina.com','吃饭','VIP','人'),('32HJ6XZV0K7AMCRMS3MAQEO7AUNOE4OJ','机器猫','male','1990-09-08','110','jqm@jqm.com','睡觉','VVIP','什么可以有'),('34SAKLWWL8FW6OBJK329C8NG62CBLHIY','HHHHHH','male','1990-09-08','1388888888','1388888888@qq.com','睡觉','VVIP','红红火火'),('35UXC3J684OQDL1X6Y9WI0II570ZY4CI','飞秋','male','1992-09-23','12435','qq@qq.com','学java','VVIP','wert'),('37Z8DIFC71UO18MUB501VKT4320V17BI','super曼','male','1988-09-08','12306','sm@qq.com','吃饭,睡觉','VIP','拯救世界'),('3CO960JC98GDFZFZVK39NBNTO9MGIKUA','隔壁老王头','male','1980-09-08','12306','145@xx.com','睡觉','VIP','你懂的'),('3KGIRQX31KQMY05KBLQ50MU98XKA3ES5','王大锤','male','1990-09-08','1232','34',NULL,'VIP',''),('3KJSDP4IB2OWVTU2A1W5ML0XMVO1NM0Z','奥巴马','female','1990-09-08','1383838438','保密','睡觉','VVIP','还是保密'),('3MG34XOSO0ES3CAKHBGNPWTAZNAXJSP','小王','male','1990-09-08','1511515111','qweqw@qq.com','睡觉','VVIP','我是老王的儿子'),('3QY2O21PQEVCHPWT9S329VCR8DBYSDLM','红豆','male','1990-09-08','186','hd@itheima.com','睡觉','VVIP','.........'),('3XMR0DNR1017V60EBLW3IQE5FN5NDDF6','账号','male','1111-11-11','111','111@163.com','睡觉','VIP','密码错误'),('40AO2ODMG9D34SMUXQJXFU8VDHDITWYA','奥巴牛','male','1990-09-08','18345641929','111@qq.com','睡觉','VIP','我就是中国人'),('4486R8PJF310N4ICPIB48199V2ZAJAKV','啊','male','1990-09-08','111','1119133524@qq.com','睡觉','VIP','一个能打的都没有'),('46M1JTX0G74DBXLBT9JTOG5LV71BEM1M','陈冠希','male','1990-09-08','13843838388','cgx@itcast.com','睡觉','VVIP','....'),('4AEZZY4H61WY37LY92PKR0WGAMR3DXME','杨洋','female','2014-08-22','119','yy@itheima.com','睡觉','VVIP','美女一个'),('4DM3SF9S3Y6DOBO1YC9S7TSMOC4ISUUY','变态狂','male','1990-09-08','198198198','jy@qq.com','吃饭,睡觉','VVIP','有点变态'),('4DT3E06N4M2O0LV33C2NX6PUZ6J7JTPW','珽哥','male','1962-09-08','10086','wzt@itcast.com','睡觉','VIP','牛人一枚'),('4HCX8X4HT93KA7WA0975JTHWFBYOMQG0','张小三','male','1990-09-08','123456','zxs@itheima.com','睡觉,学java','VIP','一个小三'),('4JKPRGO5U3KI0RGT0M89SWT6IS8SQV00','奥巴马','female','1990-09-08','911','genxinzouu@gmail.com','学java','VIP','呵呵'),('4YMN7FYI8PGN78F6Q5UBKNBCJDGYYBZT','OutMan','male','1991-02-20','13843838438','11@qq.com','吃饭,睡觉','VVIP','呵呵呵'),('561KQLV40IUJBZOCX4IKCLCWAFYAWFAO','蜘蛛侠','male','1990-09-08','123456654321','zjx@qq.com','睡觉,学java','VIP','咬咬更健康'),('5LU7616SXHUIBSG3YWMIGKCTT7SWLOFC','冰冰糖','male','1111-11-11','1111111111','11@11.com','睡觉','VVIP','你是猴子请来的逗比吗？'),('5RBXBD359KE9WQORNLTR2KWL2VYHUIHR','苏东坡','male','1030-01-03','11111111111','east@163.com','吃饭,睡觉','VVIP','有才'),('5RFOZWM57HHU8MRN25XA397ZHP59E1CA','哈哈','female','1990-09-08','21425','wn@it.com','睡觉','VVIP','arfgtrwegtr'),('6AGF6ZU4I6F280JAPJ49L05H83PDQIUM','奥巴牛','male','1990-09-08','18345641929','111@qq.com','睡觉','VIP','我就是中国人'),('6GLCRPX84X2UG2QQ3F6G9S73FFWPQ02V','啦啦啦啦啦','male','1990-09-08','1111111111111111','1111111111111','吃饭','VVIP','1111111111111'),('757967S5KWJONY2U8J5WEP4I9T2QAXZL','amor','female','1993-05-06','132000000000','132@qq.com','睡觉','VVIP','hello world~'),('7B78D2LR8UKDTRIBISQMTR5E9AM654F8','wasd','male','1990-09-08','111','wasd@163.com','吃饭,睡觉','VIP','...............'),('7FN9K82J84JK02V4YBSNZ646WNC7CC0O','Tad','male','1990-09-08','13583864237','tad@tad.com','吃饭,睡觉,学java','VVIP','啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊'),('7OMXS52CFDM50DULTPOT9A1MQYYYSSA7','张柏芝','male','1990-09-08','123456789','zbz','吃饭,睡觉','VIP','haha'),('7QC9BTURPCHUP2DTSK8DG57WP2R115M0','隔壁老王','male','1990-09-08','13812341234','wang@itheima.com','吃饭,睡觉,学java','VVIP','良民'),('7ZJJ2PPIDC3QORUCUAB7NYS2XCBX5C4I','fred','male','1990-09-08','119','zw@itcast.com','睡觉','VIP','haha'),('82G6ECPDQJI65K6DB9EPAH49817UQN21','青霞','female','1990-09-09','119','qx@itcast.cn','睡觉,学java','VIP','还是美女'),('87HUFYWO8I9U1Z0DBB5FQVFY06CE1W29','呵呵','male','1990-09-08','10000','hh@qq.com','吃饭,睡觉','VVIP','xiaxia'),('8DOKUSF0CYI64INUZWBC0WCQ3JD7OGB','斯蒂芬','male','1990-09-08','10086','10086@itheima.com','吃饭,睡觉,学java','VVIP','aa'),('8EVB6IAXXQOMB0KLA8COFK8XFOG5XGB5','龙东强','male','1990-09-08','13333333333','123@163.com','睡觉','VIP',''),('8FT5C4E5SZIT5JIRG7I5F62B4H2XO1CT','8k8k8k8k8k8k','male','1990-09-08','8k8k8k8k8k8k','8k8k8k@8k.8k','吃饭,睡觉','VVIP','8k8k'),('8NARTPP2SREZ6K6M2ETWB9BXIL4RAZ62','风起云升','male','1990-09-08','12334444445','123@qq.com','学java','VIP',''),('8OIIY75NJYYGF8U51XN96J3P2JC6FW2H','萌萌哒','female','1993-09-08','18345641929','111@qq.com','吃饭,睡觉','VVIP','我萌我快乐'),('8U1VIYUE1KWLT9786BDQ27Y4N4BEWAMK','奥巴马','male','1990-09-08','13023334444','123@qq.com','吃饭,睡觉','VVIP','谁不服就干谁'),('8XYRMT344RN4PCIUFHP8IBBDJ7LR4EQJ','罗密欧','male','1990-09-08','156','lmo@yeah.net','睡觉,学java','VVIP','爱情使者'),('91V79S5P2V87AHOF69X41MAJAULYJ12F','张三','male','1990-09-08','1420340182','zs@itheima.com','吃饭,睡觉,学java','VIP','努力的程序员'),('93FTA2D5JKUBR6EGNFJGA9C0TSN30Y34','隔壁老王','male','1990-09-08','18818818181','21313@qq.com','吃饭,睡觉,学java','VVIP','我是你隔壁的老王啊'),('93X67MQ5UZ8WJATM9D8HWZ589XANEMYY','葫芦娃','male','2015-09-08','3838383838','huluwa@163.com','吃饭','VIP','葫芦娃娃娃娃娃'),('95HO92SXJI1OUA8SLTDVRVPPSDK5RP42','罗永浩','male','1990-09-08','12345','lyh@Tt.com','睡觉','VVIP','aaaaaaaaaaa'),('97DMLC5F39H6PMNWVGXIYFTYHBSAK8W4','变态狂','male','1990-09-08','198198198','jy@qq.com','吃饭,睡觉','VVIP','有点变态'),('9LZ9SREY5V85564A7TWO9X4Z18LZCI8Q','猪哥亮','male','1990-09-08','13838238328','12309@qq.com','吃饭,睡觉','VIP','楼主好人,请留邮箱,有种子'),('9NMPVPIA19POAEI6Z5RR0AN9QPQO3DC2','毛主席','male','1921-09-08','13974567811','mzd@zhongnanhai.com','睡觉','VVIP','好好学习，天天向上'),('9R03DYBH3UJ2P5CVJ8ZV7MRXEPLDZQAV','奥巴马','male','1990-09-08','13023334444','123@qq.com','吃饭,睡觉','VVIP','谁不服就干谁'),('9WFL66BKFND7318SA0233EO9C2GGEOZS','孙悟空','male','1990-09-08','2975594359','swk@123.com','睡觉,学java','VIP','呆子。。。。'),('A1GQI0ZDFU11B9KH9E39VB6F47HQ5VNU','武大郎','male','1990-09-08','sdf','sdf',NULL,'VIP','金莲你在哪'),('AES7UMEKBMIM9FSJXWX745I4XPB81T6V','李明','male','1991-08-08','18611789668','lm@qq.com','吃饭,睡觉,学java','VVIP','好孩子'),('BT9UFD6XW6BW8IKSWT4Z0NB0M3DNNBCZ','奥巴牛','male','1990-09-08','18345641929','111@qq.com','睡觉','VIP','我就是中国人'),('C0ZYPXPWR5XX47K1546F90A5T1M0VDN6','奥巴马','male','1990-09-08','13023334444','123@qq.com','吃饭,睡觉','VVIP','谁不服就干谁'),('C5A3VA7VFMRTAQBUTYSWI0RA2R5WBNWP','到此一游','female','1133-09-08','138345409043','wolaile@qq.com','吃饭','VIP','hiahia，欧了'),('CH2R1J5J9Y6UKUS885ETWGXJR0A0JDFE','小怪兽','male','1110-09-08','1132328495','xiaoguaishou@qq.com','吃饭','VIP','来打我啊'),('CLZQESXG0M4ZFJKGDBHP8HIT4IFNMEVR','啊','male','1990-09-08','110','genxinzouu@gmail.com','学java','VIP','一个能打的都没有'),('CPTDY8PFT4BM1OH66KXBA2MWBEA8IWS','怪蜀黍','female','1990-09-08','18888888888','11@qq.com','睡觉,学java','VIP','怪蜀黍只爱小萝莉'),('CT0OF6YT346BQNFRUOQCD6NUYHAB3SKD','TTTTTTTTTTTTTTT','female','1990-09-08','120','120@163.COM','睡觉','VIP','123'),('CXWU8IXZDGNBDDEE8NFDJ7KALZGBOYQC','DM','male','1989-01-23','5539','daye@qq.com','吃饭,睡觉','VVIP','缺觉'),('CY5SRDVMUJBJH9VNCIK8DAXPKGLJKHIW','王五','male','1990-09-08','111','111@163.com','吃饭,睡觉,学java','VVIP','heihie'),('CYD9DJ0O35Z3XK21BTKPI12FSRTGC74B','柳下挥','male','8888-08-08','888888888','8888888@88.com','睡觉','VVIP','88888888888888888888888'),('CYL9F2Z1FFSAPDEB7WCLERW77HK9LZHS','张茜','male','1990-09-08','111111111111','qqqqq','吃饭','VIP','有本事你就来'),('CZT0VBR1JHCIK1NUWV6109URJO7GLDMQ','小逗比','male','1990-09-08','9527','9527@qq.com','睡觉,学java','VIP','小逗比是大逗比的弟弟'),('DK2F8IZGPW83VBKGCMQM8SKYYHYKQI71','毛巴马','male','1965-08-08','18611783668','mbm@163.com','吃饭,睡觉,学java','VIP','、、、'),('DUQZVFWHLIGQHR5H0INZKLV4XG8UG11H','www','male','1990-09-08','12334444445','123@qq.com','吃饭,睡觉,学java','VVIP','122334'),('DW651XUK2UFW3W953CYPZH4GFSGU1QTE','www','male','1990-09-08','13201096785','www@itheiam.com','吃饭,学java','VVIP','呵呵'),('EC9MNKP9QP1PNCVZMXMWXQPI2VEZU14A','奥巴牛','male','1990-09-08','18345641929','111@qq.com','睡觉','VIP','我就是中国人'),('ECCSRVEF70KJ3QW1EW9DN98C71ET7UN','王璐','female','1990-02-01','19999','123@123.qq','吃饭,睡觉','VIP','美女一个'),('EJVGHT7JGC9ZFSHFW6ZAL667D63OLNTU','到此一游','male','1990-09-09','110110','110@110.com','吃饭','VIP',''),('EO933SIFXPJKYNC7WNJP2T6TFHS839AQ','好妹妹','male','1990-09-08','1234','abc@qq.com','吃饭,睡觉,学java','VIP','喜欢软妹子'),('EU53PRN2ATZXVSK69ZWDX08Y6MFK56FL','毛巴马','male','1990-09-08','18611703688','mbm@qqcom','吃饭','VIP','？'),('EUE1FEKN6BK6ENOQBRXWRFV3JSE9640I','邓公','male','1919-09-08','121124343','gfd@cin.com','吃饭,睡觉,学java','VVIP','总工程师。。。。。。。。。。。。。'),('F3YJ0ELTIOYXE3ZKUK1473J05FTDFN1W','木头','female','1993-09-24','123321','123@123.com','吃饭,睡觉','VVIP','木头'),('F9L805O8N78U1MJ4A1PLD85SNSN3WMPS','小二B','male','1990-09-08','155','cdz@163.com','睡觉','VIP','我叫 金龙。'),('F9VXWZDPHCYQ55SKNE6DGH2ZS74Y2ZH0','1111111111111111','male','1990-09-08','1111111111','1111@163.com',NULL,'VIP','111111111111111111'),('FDXVKK1W1JFF8JLY0LLMVIQPA0Z5R3IQ','凹凸不满','male','1990-09-08','1434657','gae@sina.com','睡觉,学java','VIP','也是个有故事的人'),('FH87ZLIH6YXA89QCEBGJSZ24UKVTGT8Z','少羽','male','1990-09-08','10086','123@itheima.cn','睡觉,学java','VVIP','大帅哥'),('FJ18JJIRMQPGKTGXO2DKGBT3WB2V7QO7','TTTTTTTTTTTTTTT','female','1990-09-08','120','120@163.COM','睡觉','VIP','123'),('FOSALR23G2YPVQ0OB9KP83XQGDGDMX5T','秒杀','male','1990-09-08','12345','123@rfvgt.cn','吃饭,睡觉','VIP','只要锄头挥的好，没有墙角挖不倒'),('FTE2SRRWWFEIJYH7O6H107A6ENOSE6H8','奥巴牛','male','1990-09-08','18345641929','111@qq.com','睡觉','VIP','我就是中国人'),('FWJPM1FQ15AXNNO1Y422HIYOG97H3L6V','远方','female','1990-09-08','150','yf@itheima.com','学java','VIP','..........'),('FXLSY857YFUI4I8AS7PRVHFROMMV9QOM','奥巴牛','male','1990-09-08','18345641929','111@qq.com','睡觉','VIP','我就是中国人'),('G5E374B9T2HFO3MXBZ7GOGMRTKG504EC','Jon Wang','male','1990-09-08','15888888888','168@itash.cn','睡觉,学java','VVIP','sun of beach'),('G5LC637AEVX589KH6PHQQ6FB97EN805Y','aaaaa','male','1990-09-08','88888','88888@88.com',NULL,'VVIP','88888'),('GBVKGSOQXWEKMQ1U6EQ2OV97SVML8XNT','独角兽','male','2014-08-23','13245656565','dsajk@163.com','睡觉,学java','VIP','阿达SD卡手机打开'),('GGKUCANBWBI4LQTUF5JW3YHPJ5ODLEMM','奥巴牛','male','1990-09-08','18345641929','111@qq.com','睡觉','VIP','我就是中国人'),('GSI1DJL4QKGHLGUSS4KE9TS4Y81878ZT','阿杰','male','1990-10-10','556565656','958795@qq.com','睡觉,学java','VIP','伊娃一麻袋'),('GTNDX8ZSD3H9PC2A537OKIEP69QEQEQ','铁板葫芦娃','female','2000-09-08','你猜','111@qq.com','睡觉','VVIP','哈哈 呵呵 嘿嘿'),('H0ZFOK0Z6Q35YP0M28LW2VQ5LWNWJVUI','王淑丽','female','1990-09-08','188888888888','wsl@itheima.com','睡觉','VVIP','嘿嘿'),('HPQ9FR9J02FYQT9PRQ7SD7ZNEKE4KW83','奥巴牛','male','1990-09-08','18345641929','111@qq.com','睡觉','VIP','我就是中国人'),('IMWS8G3VN939YE8Z0TOXSIXYX7UKHQ45','奥巴马','male','1990-09-08','110110','111@qq.com','吃饭,睡觉','VIP','美国佬'),('IV9L5WW3VEC2HX1396BXLXQT2SKY8KGW','变态狂','male','1990-09-08','198198198','jy@qq.com','吃饭,睡觉','VVIP','有点变态'),('IYSU5ZGVBVEEC3MZ2CKEGY8MJCFOEWDU','紫霞','male','1990-09-08','1110','1232','吃饭','VIP','仙子'),('J4S9YRAIR1AY9R74EBTBN9RTS7CP9MUN','宇宙无敌','male','1990-09-08','10086','10000@qq.com','睡觉,学java','VVIP','好人一生平安！'),('J9XUV7UL43FFS4CZ6XZNJ2SL8FA03OAR','奥斯特洛夫斯基','female','1990-09-08','112','123@sina.com','吃饭,睡觉,学java','VIP','OOOOOOOO~~~~~~~~~~yes'),('JFRHQE2HY66YVHXIZBJHNXIB1T5T1KGJ','abc','male','1990-09-08','123','12345@qwert.com','吃饭,睡觉','VVIP','三步一停，五步一卡！'),('JKOU9U8A74E7FPBXJT6J701FFASEZ06J','王二麻子','female','1895-08-08','110119120','wzt@itcast.com','睡觉,学java','VIP','大神'),('JYEUJ53CKYCCR68BHR3VFIXVP2M5ALAS','苏轼','male','1111-09-09','888888888','99@sina.com',NULL,'VIP',''),('K7MBJK1QKN099Q3NL578IBBW2E38JPQ9','武松','male','1990-09-08','121212','1212@12.com','睡觉','VIP','嫂嫂，我在这~~'),('K9LNKGXGFFWUSDG1BTIIU2LB323NLC8H','葫芦娃','female','1990-09-08','000000000','ahdj@qq.com','学java','VVIP','七个人'),('KDZ34TIW8PKLPNK8GOH0A51LUTND5WVJ','帅哥来了','male','1990-09-08','1234567','321@hha.com',NULL,'VIP','......【省略一万字】'),('KG48IK2775NQADW2UHMY3OTTWWMDZ359','中国联通','male','1990-09-08','10010','10010@139.com','吃饭,睡觉','VIP','才'),('KPGN2T4L3AGHW0ETWJ7J1IK5MDBADWK','张尼玛','male','1990-09-08','110','znm@itcast.com','吃饭,睡觉,学java','VIP','bz'),('KPS1A8T52O1WCEPBFEK0CBHJIS043XY9','王大锤','male','1990-09-08','1232','34@aa.com','吃饭,睡觉','VIP','百度'),('KXSHXSPR399GSDC9HJHMGA2C5FGGORW9','隔壁老王 ','male','1990-09-08','18273684903','leexy@健健康康.com','吃饭,睡觉','VVIP','会  '),('L00E6S7OBTK8PJGREXT57F3FUTCR3DWP','梅川內酷','male','1980-10-01','911','mcnk@itheima.com','吃饭,睡觉','VIP','黑马大神'),('L3MH97LVMF4IG03BIEZ4ZT3EXEW2F4LX','梅川酷子','male','1990-09-24','111111','sf@ee.com','吃饭,睡觉','VIP','没穿裤子'),('L9RZOHHI6K4PRAMTHU0X31IR9W4V6CDJ','奥巴牛','male','1990-09-08','18345641929','111@qq.com','睡觉','VIP','我就是中国人'),('LA1H7TODKL391CRFQJZAAF19TV26447F','变态狂','male','1990-09-08','198198198','jy@qq.com','吃饭,睡觉','VVIP','有点变态'),('LDGVC9B9AI5Y4WP40DS1RYEK49EELCXL','金坷垃','male','2004-06-30','235346547457','zzz@qq.com','睡觉','VVIP','dgfsgdghs'),('LJ985646IS1W4GAFHIHLORSE64C79RK8','拉拉','female','1990-09-08','254667','fag@sina.com','睡觉','VIP','是个有故事的人'),('LS2BKZVTX12HIXTNYXPOI81EBU6C910Q','小胖子','male','1990-09-08','8888888888','xiao@cd。cm','学java','VVIP','java好难'),('MBS3I08X7DUE94DJVS6JE6VZHJCYAGSR','徐诚毅','male','1990-09-10','18650098894','xcy@qq.com','吃饭,睡觉,学java','VIP','逗比徐诚毅'),('MCADW1MQ5GUC6WQZGZBDELJW91BIXEQP','内裤外穿的大叔','male','1990-09-08','1222','114226132711@qq.com','睡觉,学java','VIP','霸气 .........'),('MFUS1FBI04O9GGLFCPW3032MRM0UQKKU','老罗粉','male','1990-09-08','123','21323',NULL,'VIP','锤子'),('MK5NQGF3465T20G642HLZDC6FRDYJ5GD','牛嘻嘻','male','1990-09-08','13782948368','114226132711@qq.com','睡觉,学java','VIP','喜欢逗逼,热爱 打 豆豆'),('MKKQECOWM849RRKW2NQA7H9GV0X41Y3G','寒冰','male','1990-09-08','1190','123@kjakls','吃饭,睡觉,学java','VIP','十发九中'),('MMOC8O1CLYLYJHA6O4BQP2JM3QWOXCBU','奥巴马','male','1990-09-08','13023334444','123@qq.com','吃饭,睡觉','VVIP','谁不服就干谁'),('MUZHTW3H2QUHHLTBP470L5X801H4LSPV','强强','male','1992-09-06','10086','qiangqiang@126.com','吃饭,睡觉,学java','VIP','坚持练习'),('MVOS8BZBTNHP1C4L7HA0IKG4N58U3Z4N','大逗比','male','1990-09-08','9527','9527@qq.com','吃饭,学java','VIP','大逗比是小逗比的哥哥'),('N4X9YBNQV13EU746W4EQA9LMVPJ3EL9R','薛振宇','male','1999-04-08','13808080909','xzy@ithiema.com','吃饭,睡觉,学java','VVIP','java路太坎坷，薛振宇我大哥。'),('NBH1SHP7NF1TCQE9QVEATAJFHRTHXSUK','变态狂','male','1990-09-08','198198198','jy@qq.com','吃饭,睡觉','VVIP','有点变态'),('NQ2URAFPF6NEJ67XBWREHSBJU2U3XK05','葫芦娃','male','2020-09-09','3838383838','huluwa@163.com','吃饭','VIP','葫芦娃'),('NQM4W2HLSL0MSOVNVFW18WV2NUE8AQXO','珽哥好帅','male','9999-09-09','911','tg@lailai.com','吃饭,睡觉,学java','VVIP','额。。。。不多描述'),('NS08LYUDCWFVL4INLNZ19JI2C0CJ5Q75','黄家驹','male','1960-06-10','8964','wongkkaui@tiantang.com','睡觉','VVIP','爱好唱歌'),('NUIOTA1VFYSFTRMRO1H1ROTZX6CLH607','王尼玛','male','1111-11-11','1111111','11@11.com','吃饭,睡觉','VIP','sun of bitch'),('OBP1HH2WVSVAN5F368FAIH8N6RYJQEFN','苍老师','female','1990-09-08','000','000@12.cn','睡觉','VVIP','ya  ma  dai'),('OCDCNSAZI7TN2N6CQQ7W8I0LLMW95JAM','王昭廷是帅哥吗？','female','1111-11-11','11111111','111@111.com','睡觉','VVIP','可能是帅哥可能是....客官自定。'),('OK8OR412OV3WYIHE5KJKUFLIO2JRXGB9','雷锋','male','1111-11-11','1111111111','leifeng@.live.cn','吃饭,睡觉','VVIP','很傻很天真'),('OKTBUEZI17A89BZOU382DXBLKNBN378K','老王隔壁隔壁的隔壁','male','1990-09-08','110','ww@www.com','吃饭,睡觉,学java','VVIP','小样儿吧'),('ORC62VKK3VDVKG225WQN4WUY4CI5HA07','潘金莲','male','1990-09-08','1234578','12@12.com','睡觉','VIP','官人在哪里~'),('OVDK18X77A4SV094HJEALMC97GZL6VOY','奥特曼','female','2018-08-08','13838383838','aoteman@itcast.cn','睡觉','VVIP','西天取经打怪兽'),('OVELNMWRBHY9JXL1ZD1MUFTMFTX6YI3Q','章子怡','female','1990-09-08','1323333','zzy@163.com','吃饭,睡觉','VIP','明星'),('OW8G2RLD5QG9S9C8HHLYQ7XX8MFH45V4','韩寒','male','1982-09-08','130','han@sina.com','吃饭,睡觉','VVIP','车手，作家'),('P4T33S0SGHMY3XL4B2Y5IJWJFH3P6UQA','凹凸曼','male','1990-09-08','18345641929','111@qq.com','吃饭,睡觉','VIP',''),('PBGP9AEAOVW431XHZZFOTQ6LD8NDDKO9','接盘侠','male','1990-09-08','13688889999','123@itheima.com','睡觉','VIP','盘？'),('PJE46MNC430NXBRSZ645VRHOMQ3DI9UN','小茜茜','female','1991-01-01','555','xx@ithieima.com','吃饭,睡觉','VVIP','美女'),('PO5ZTSKKK1DC0VQZR4PB9DBZFCKR5IUS','马六','male','1990-09-08','666','666@qq.com','学java','VVIP','he he'),('PPYEE3NB8R0VJZAL4WXA1UGWXSI9KVRN','隔壁老王','male','1990-09-08','123456','lw@itheima.com','学java','VIP','杀人红尘中，脱身白刃里'),('Q45R171IWK442IBRRANYDOXDNNI7NRQ1','交流交流','male','1990-09-08','129129129','jie@yu.com','吃饭,睡觉','VIP','。。。。'),('Q5FN4B31AFYZJF8QWZU9K4FQMKRYXTND','黑猫警长','male','1990-09-08','1032859','abc@qq.com','睡觉,学java','VVIP','嘿嘿');
UNLOCK TABLES;
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `USERNAME` varchar(100) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `EMAIL` varchar(100) NOT NULL,
  `BIRTHDAY` date NOT NULL,
  PRIMARY KEY  (`USERNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--


/*!40000 ALTER TABLE `user` DISABLE KEYS */;
LOCK TABLES `user` WRITE;
INSERT INTO `user` VALUES ('cui','312','lll@qq.com','1893-07-05'),('lql','345','dfjhskfgaihlaidhgsli@qq.org','2354-05-06'),('wzhting','123','wzt@itcast.cn','2014-08-22'),('yyy','123','yyy@itcast.cn','2014-08-22'),('zww','123','zw@itcast.cn','1990-09-08');
UNLOCK TABLES;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(40) default NULL,
  `password` varchar(40) default NULL,
  `email` varchar(60) default NULL,
  `birthday` date default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--


/*!40000 ALTER TABLE `users` DISABLE KEYS */;
LOCK TABLES `users` WRITE;
INSERT INTO `users` VALUES (2,'lisi','111','lisi@sina.com','1981-12-04'),(3,'wangwu','111','wangwu@sina.com','1979-12-04'),(4,'范青霞','111','fqx@itcast.cn','2000-10-01');
UNLOCK TABLES;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

