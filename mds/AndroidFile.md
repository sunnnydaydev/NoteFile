# Android File

# 常见操作系统的文件系统

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/win.png)

Windows的文件系统有盘符的概念，在安装系统时可以划分几个盘如常见的C盘、D盘、E盘。每一个盘就是一个独立的根目录，这点和Linux文件系统差异比较明显。
因此在操作文件时我们通常会见到这样的absolute path
```kotlin
   val file1 = File("D://xxx1")
   val file2 = File("F://xxx2")
```

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/file_mac.png)

Mac的文件系统中只有一个根目录，根目录下有一系列文件夹，其中最常使用的就是Users目录了。一般我们需要在"根目录/Users/用户名/"路径下进行文件操作，因此
在操作文件时常会见到这样的absolute path

```kotlin
 val file1 = File("/Users/zb/JavaFilePractice/1.txt")
 val file1 = File("/Users/zb/JavaFilePractice/2.txt")
```

那么android操作系统的文件目录是怎样的呢？这点可以通过adb命令来查看，但是通过AS提供的Devices File Explorer看着更加清晰：

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/android.png)

Android是基于Linux操作系统的，安卓中也是只存在一个根目录。

# Android的内部环境与外部环境

Android的文件系统真的操蛋，内部环境外部环境牵涉到api把人搞得真乱。

起初大家可能和我有一样理解，手机自带的硬盘为内部存储，sd卡为外部存储。然后接触到了安卓常见的file api：

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        println("cacheDir：$cacheDir")
        println("externalCacheDir:$externalCacheDir")
    }
}
```
I/System.out: cacheDir：/data/user/0/com.sunnyday.notefile/cache

I/System.out: externalCacheDir:/storage/emulated/0/Android/data/com.sunnyday.notefile/cache

此时心里形成了一个概念data/data/pkg/目录下对应内部存储环境，/storage/emulated/0/对应的为外部存储环境。但是同时还存在着些疑惑：

- 为啥外部环境没有内存卡也能使用？
- sdCard目录又是啥玩意，这个不应该是内存卡目录吗？
- mnt啥玩意，里面为啥也有个sdCard目录
- 内部存储环境不应该像电脑那样我们随便存吗？为啥只能存自己app的数据？
- 安卓不是说操作外部环境目录要申请读写权限，为啥我操作externalCacheDir目录下文件不要读写权限也能成功？

一大堆疑问出来了，，，，，，

todo

安卓的文件挂在节点图

内部环境&外部环境物理上区分。

二、熟悉下场景的api

   fileDir 
   cacheDir
   等等，，，

三、了解下安卓文件的变更流程

    兼容写法~


四、官方文档过一遍







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

[Android系统目录介绍](https://blog.csdn.net/abc6368765/article/details/125403212)