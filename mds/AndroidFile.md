# Android File

###### 1、内部环境外部环境的区分

先把概念梳理明白，弄明白安卓的文件目录。参考文章：解析Android内部存储、外部存储的区别

https://blog.csdn.net/baidu_36385172/article/details/79695308

###### 2、安卓文件的适配

（1）针对外部环境文件的变化：

- 【4.4-6.0） 不支持动态权限，使用外部文件只需要在清单文件申请读写权限即可。
- 【6.0-10.0）  支持动态权限， 使用外部文件需要动态申请读写权限
- 10.0 加强外部文件的使用限制，如需要使用外部文件，则需额外的进行申请requestLegacyExternalStorage申明（清单文件中
  ，注意安卓10.0以下app默认具有requestLegacyExternalStorage,10.0上需要额外申请）
- 【11,13】

参考文章：https://blog.csdn.net/rzleilei/article/details/122700219

（2）分区

分区的概念

如何适配分区


五、做个练习-综合运用

1、安卓图片缓存到本地：好像与压缩的api 直接就能缓存到本地

2、视屏的本地播放、缓存。

3、这个当做一个解答：假如一个视频文件 1.MP4 我以输入流存入本地  video（不取后缀名）这个文件中，然后以输出流形式读取。 使用VideoView控件能正常播放吗？

4、app 自定义格式文件的支持

app支持自定义文件的打开，如 test.nb, 我们的app支持创建 *.nb格式的文件,也支持 *.nb 文件的打开。

最常见的就是微信app点击这个文件直接支持打开。

参考

[官方文档](https://developer.android.google.cn/guide/topics/data?hl=zh-cn)