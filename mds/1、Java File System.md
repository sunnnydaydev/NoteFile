# Java File System

###### 1、File类

（1）File & IO

一谈起文件我们心里就会浮现出两个概念"文件(file)"和"文件夹(directory)"，在java中这二者统称文件，使用File类来描述。

举个栗子~ 在我电脑上有个"文件夹"和"文件" 我们就可以使用File类来描述二者。

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/file_dir.png)
![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/file_file.png)

```kotlin
fun main(){
    val fileDir = File("/Users/zennioptical/JavaFilePractice")
    println("name:${fileDir.name}")
    val fileFile = File("/Users/zennioptical/JavaFilePractice/1.txt")
    println("name:${fileFile.name}")
    /**
     * log:
     * name:JavaFilePractice
     * name:1.txt
     * */
}
```
可见当传入的文件路径是文件夹的路径时这个File就代表是一个文件夹文件，当传入的路径是一个普通的文件时这个File就代表是一个
普通的文件。

那么啥叫IO呢？IO是Input和OutPut的简称，意思就是输入输出。 在计算机中，IO就是"程序"与"磁盘"进行交互的操作。程序很好理
解就是跑在内存中的，"磁盘"可以是本计算机的硬盘、外部存储设备、其他电脑磁盘。

IO就像水流一样具有"流向"，输出就是流出，输入就是流入。输入和输出相对于内存，从内存往磁盘写数据就是输出，从磁盘往内存读数
据就是输入。先看张图结合理解下，具体的IO操作就要设计到下章节"字符流与字节流"的概念了。

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/file_io.png)

(2)File api

java 使用File类来描述文件，那么我们首先看下File类常见的api。

- public File(String pathname):给定文件路径生成一个file类。
- public File(String parent, String child):给定文件的parent路径、child路径，生成一个File类。
- public File(File parent, String child):给定一个parent File、child路径，生成一个File类。

看下构造第一个很好理解，直接指明文件所在磁盘的绝对路径即可，这样直接通过构造就可以获取一个file对象，然后就可以使用file
对象来描述这个文件了。

后两个构造乍一看有点迷惑。其实简单看下源码就会知道："后两个构造和第一个构造类似，第一个构造直接把获取到的path赋值给自己
的path成员变量，后两个构造会把拿到的参数通过一些列操作转化为path赋值给自己的path成员变量"

接下来看下这三个构造如何使用的~

```kotlin

        const val path = "/Users/zennioptical/JavaFilePractice/1.txt"

        const val parent = "/Users/zennioptical"
        const val child = "JavaFilePractice/1.txt"

        fun main() {
        // 构造1，直接传参数完整path
        val file1 = File(path)
        println("name:${file1.name}")

        // 构造2，传递父、子 path。
        val file2 = File(parent, child)
        println("name:${file2.name}")

        // 构造3，构造一个父文件传递过来，然后传递一个子路径。
        val file3 = File(File(parent), child)
        println("name:${file3.name}")

        /**
         log：
         name:1.txt
         name:1.txt
         name:1.txt
         * */
        }
```

- public boolean exists()文件是否存在
- boolean isFile()是否是个普通的文件
- public boolean isDirectory()是否是目录文件
- public String getName()获取文件名（带后缀）
- public String getAbsolutePath()获取文件绝对路径名
- public long length()获取文件大小（单位字节）
- public boolean createNewFile()创建文件（创建失败报错）
- public boolean delete()删除文件
- public boolean mkdir()创建文件夹（父文件夹不存在时创建失败返回false）
- public boolean mkdirs()创建文件夹（父文件夹不存在时会同时创建父文件夹）
- public File[] listFiles()返回一个文件数组
- public boolean isHidden()是否是隐藏文件
- public boolean renameTo(File dest)重命名文件

看下文件的重命名操作,JavaFilePracticemu目录下原有的1.txt会被重命名为2.txt
```kotlin
fun main() {
    val file = File( "/Users/zennioptical/JavaFilePractice/1.txt")
    val desc = File( "/Users/zennioptical/JavaFilePractice/2.txt")
    file.renameTo(desc)
}
```
在看下这个，可以发现重命名操作也可以当做"移动"操作来使用。原本JavaFilePractice下的1.txt被移动到了
JavaFilePractice/Test下且重命名了。
```kotlin
fun main() {
    val file = File( "/Users/zennioptical/JavaFilePractice/1.txt")
    val desc = File( "/Users/zennioptical/JavaFilePractice/Test/2.txt")
    file.renameTo(desc)
}
```

###### 2、字符流与字节流

- 字符流
- 字节流
- 二者常见子类

###### 3、文件的压缩操作

- 压缩
- 解压缩

###### 4、文件工具类

可把常见的操作抽成工具，这里主要是基础工具类。可能有些安卓不适用，到时候需要看情况分开总结。

###### 5、kotlin对io的扩展

参考文章：https://juejin.cn/post/6844903704680726535