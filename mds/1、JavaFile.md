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

###### 2、File#Method

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

还有一些常用的方法如下：

- public boolean exists()文件是否存在
- boolean isFile()是否是个普通的文件
- public boolean isDirectory()是否是目录文件
- public String getName()获取文件名（带后缀）
- public String getAbsolutePath()获取文件绝对路径
- public long length()获取文件大小（单位字节）
- public boolean createNewFile()创建文件（创建失败报错）
- public boolean delete()删除文件
- public boolean mkdir()创建文件夹（父文件夹不存在时创建失败返回false）
- public boolean mkdirs()创建文件夹（父文件夹不存在时会同时创建父文件夹）
- public File[] listFiles()返回一个文件数组
- public boolean isHidden()是否是隐藏文件

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
 * FileReader栗子
 * 1、方法简介
 * public int read() throws IOException：
 * 从流中读取1个字符，返回这个字符代表的int类型值。因此我们想要得到字符一般需要自己再强转一下。
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

read还有两个重载,首先看看三个参数的：

```kotlin
/**
方法：
public int read(char cbuf[], int offset, int length) throws IOException {}

功能：
Reads characters into a portion of an array.

参数：
cbuf – Destination buffer

offset – Offset at which to start storing characters

length – Maximum number of characters to read

返回值：
The number of characters read, or -1 if the end of the stream has been reached
-------------------------------------------------------------------------------------------------

点评：效率稍微比read() 好点，每次能够读取多个字符数据。

思考：BufferReader 每次读取一行数据， 使用read每次可以读取指定数据，当指定的数据大于行数的字符时 读取的效率是否比其高？

 * */
fun fileReader1() {
    val file = File("/Users/zb/JavaFilePractice/1.txt")
    val fr = FileReader(file)
    val buffer = CharArray(10)
    /**
     * 注意这里第三个参数length：理论上每次从流中读取length个数据，read每次的返回值就是length，当流中数据小于length时
     * 此时返回值就是剩余字节个数。
     *
     * 举个例子：如上我们申请的数组空间为10，每次从流中读取10个字符，read每次返回10。读取了n-1此后流中还剩3字符，则
     * 第n次read的结果是3
     *
     * */
    var count = fr.read(buffer,0,buffer.size)
    while (count != -1) {
        /***
         * 注意写法，这里第三个参数为count，fileReader2栗子中相当于传递了buffer.size因此出现读取多余的空格。
         * 这里若是传递buffer.size出现的结果也会很奇怪，多了几个字符。
         * 若传buffer.size得到的结果是：落霞与孤鹜齐飞，秋水与长天一色。飞，秋水
         * */
        print(String(buffer, 0, count))
        count = fr.read(buffer)
    }
    fr.close()
}
```

再看看两个参数的：

```kotlin
fun fileReader2() {
    val file = File("/Users/zb/JavaFilePractice/1.txt")
    val fr = FileReader(file)
    val buffer = CharArray(30)
    var count = fr.read(buffer)
    while (count != -1) {
        print(String(buffer, 0, count))
        count = fr.read(buffer)
    }

/**

    var count = fr.read(buffer)
    while (count != -1) {
    print(String(buffer, 0, count))
    count = fr.read(buffer)
    }

    错误写法，把上面这段注释了只使用下面的：
    fr.read(buffer)
    println(String(buffer))
    结果：落霞与孤鹜齐飞，秋水与长天一色。              

    这种方式读取的，受申请数组空间影响：
    1、数组空间较小，读取数据不全
    2、数组空间过大，浪费空间且多余的空间都是空数据，遍历char数组就会遍历出空字符。

    不建议写法，使用这种写法需要字符数组判空处理，当然我们也不知道这个是否
* */
    fr.close()
}
```


