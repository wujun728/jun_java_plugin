## 由来

Hutool针对Word（主要是docx格式）进行封装，实现简单的Word文件创建。

## 介绍

Hutool将POI中Word生成封装为`Word07Writer`, 通过分段写出，实现word生成。

## 使用例子

```java
Word07Writer writer = new Word07Writer();

// 添加段落（标题）
writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分", "我是第二部分");
// 添加段落（正文）
writer.addText(new Font("宋体", Font.PLAIN, 22), "我是正文第一部分", "我是正文第二部分");
// 写出到文件
writer.flush(FileUtil.file("e:/wordWrite.docx"));
// 关闭
writer.close();
```