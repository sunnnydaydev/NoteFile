# Java File

# File

###### 1、File和IO的概念

一谈起文件我们心里就会浮现出两个概念"文件(file)"和"文件夹(directory)"，在java中这二者统称文件，使用File类来描述。

举个栗子，在我电脑上有个"文件夹"和"文件" 我们就可以使用File类来描述二者。

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
可见当传入的文件路径是文件夹的路径时这个File就代表是一个文件夹文件，当传入的路径是一个普通的文件时这个File就代表是一个普通的文件。

那么啥叫IO呢？IO是Input和OutPut的简称，意思是输入输出。在计算机中，IO就是"程序"与"磁盘"进行交互的操作。程序是跑在内存中的代码，"磁盘"可以是计算机的硬盘、外部存储设备、其他电脑磁盘。

IO就像水流一样具有"流向"，输出就是流出，输入就是流入。输入和输出相对于内存，从内存往磁盘写数据就是输出，从磁盘往内存读数据就是输入。先看张图结合理解下：

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/file_io.png)

###### 2、File类

- public File(String pathname):给定文件路径生成一个file类。
- public File(String parent, String child):给定文件的parent路径、child路径，生成一个File类。
- public File(File parent, String child):给定一个parent File、child路径，生成一个File类。

第一个构造很好理解，参数是文件所在磁盘上的绝对路径，这样就可获得一个描述这个文件的file对象。

后两个构造乍一看不能直接明白方法的意思，但简单看下源码就会知道：File在创建对象时会把pathName这个参数赋值给自己的path字段，而后两个构造内部其实就是做了些"解析参数"、"拼接参数"的操作，最终拼成一个path赋值给自己的path字段。

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
        }
        /**
        log：
        name:1.txt
        name:1.txt
        name:1.txt
         * */
```

- public boolean renameTo(File dest)重命名文件
```kotlin
fun main() {
    val file = File( "/Users/zennioptical/JavaFilePractice/1.txt")
    val desc = File( "/Users/zennioptical/JavaFilePractice/2.txt")
    file.renameTo(desc)
    //JavaFilePractice目录下原有的1.txt会被重命名为2.txt
}
```

```kotlin
fun main() {
    val file = File( "/Users/zennioptical/JavaFilePractice/1.txt")
    val desc = File( "/Users/zennioptical/JavaFilePractice/Test/2.txt")
    file.renameTo(desc)
}
```
可以发现重命名操作也可以当做"移动"操作来使用。原本JavaFilePractice下的1.txt被移动到了JavaFilePractice/Test下且重命名了。

# IO流

IO流按照流向分类可以分为输入流和输出流

- 往内存中读的叫做输入流
- 从内存中往外写的叫做输出流
- 所有输入流都是InputStream或Reader的子类
- 所有输出流都是OutputStream或Writer的子类

IO流按照数据流的编码格式上可分为字符流和字节流

- InputStream、OutputStream及其子类为字节流
- 字节流主要处理二进制数据，如读写音频、图片等比较常用，处理单元为1字节
- 字符流主要处理字符数据。如配置文件、json等字符相关的读取

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/file_io_chart.png)

###### 1、FileWriter

```kotlin
/**
 * FileWriter栗子：往磁盘写文本。
 * */
fun fileWriter() {
    val file = File("/Users/zb/JavaFilePractice/1.txt")
    if (!file.exists()) {
        file.createNewFile()
    }
    val fw = FileWriter(file)
    fw.write("落霞与孤鹜齐飞，秋水与长天一色。")
    fw.flush()//把数据从内存缓冲区刷到磁盘
    fw.close()// 关闭输出流
}
```
- 在IO流中数据是先被读写到内存缓冲区的，然后再被刷到磁盘上。因此当程序执行完read/write并不一定是真正的完成了读写操作，只有当缓冲区的数据被刷出去才代表真正的读写完毕。
- 上述例子把flush、close注释了本地文件无数据。
- 上述例子把flush注释了本地文件有数据，因为close时会触发flush。
- 上述例子把close注释了本地有数据但流未关闭可能会引起内存泄漏。

FileWriter这个类中只有构造，直接父类OutputStreamWriter也仅是重写了一些方法，我们直接看其父类顶级父类Writer：

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/writer.png)

如图其他方法都很好理解，"偏移量"和"读取个数"可能不太明白，展示个例子：

```kotlin
fun fileWriterTest() {
    val file = File("/Users/zb/JavaFilePractice/fileWriterTest.txt")
    if (!file.exists()) {
        file.createNewFile()
    }
    val fw = FileWriter(file)
    fw.write("12345",1,3) //234写进文件
    fw.flush()
    fw.close()
}
```

###### 2、FileReader

```kotlin
/**
 * FileReader栗子：
 * */
fun fileReader() {
    val file = File("/Users/zb/JavaFilePractice/1.txt")
    val fr = FileReader(file)
    var index = fr.read() 
    while (index != -1) {
        print(index.toChar())
        index= fr.read()
    }
    fr.close()
}
```
read()方法:从字符输入流读取一个字符，返回当前读取的字符。当输入流无数据时read()方法返回-1。这里需要再次说明下流的概念，否则上面的循环还不好理解，看图：

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/stream.png)

这里应该就豁然开朗了，每次都"读走"首个数据，直到流中无数据此时read()返回-1
