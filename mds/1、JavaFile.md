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
    //JavaFilePractice目录下原有的1.txt会被重命名为2.txt,且被移动到JavaFilePractice/Test目录下
}
```
可以发现重命名操作也可以当做"移动"操作来使用。 File还有一些常用的方法如下：

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
 * 从流中读取1个字符，返回这个字符代表的int类型值。流中无数据时read方法返回-1.
 * 2、注意
 * 想要得到字符一般还需要自己强转一下，把int强转为char。
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
这里需要再次说明下流的概念，否则上面的循环还不好理解，看图：

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/stream.png)

这里应该就豁然开朗了，每次都"读走首个"数据，直到流中无数据此时read()返回-1

read还有两个重载,首先看看三个参数的：

```kotlin
/**
方法：
    public int read(char cbuf[], int offset, int length) throws IOException {}

功能：
    把字符从流中读到cbuf数组中

参数：
      cbuf    数组
    offset    偏移量，从字符流中读取的字符放入数组的偏移位置，一般从index 0 开始放。
    length    每次从流中读取字符的最大个数
返回值：
     真实读取到的字符个数，若流结束了就返回-1
注意：
    read()、read(char cbuf[])方法的底层都是使用到了这个方法来进行读取的。
    三者的差别：（1）一次性读取的字符个数不同
              （2）返回值不同
 
 * */
fun fileReader1() {
    val file = File("/Users/zb/JavaFilePractice/1.txt")
    val fr = FileReader(file)
    val buffer = CharArray(10)
    /**
     * 注意这里第三个参数length：理论上每次从流中读取length个数据放到cbuf中，read每次的返回值就是length，当流中数据小于length时
     * 此时返回值就是剩余字节个数。
     *
     * 举个例子：如上我们申请的数组空间为10，每次从流中读取10个字符，read每次返回10。读取了n-1此后流中还剩3字符，则第n次read的结果是3
     *
     * */
    var count = fr.read(buffer,0,buffer.size)
    while (count != -1) {
        /***
         * 注意写法，这里第三个参数传count，fileReader2栗子中错误写法注释相当于传递了buffer.size因此出现读取多余的空格。这里若是传递
         * buffer.size出现的结果也会很奇怪，多了几个字符：落霞与孤鹜齐飞，秋水与长天一色。飞，秋水
         * */
        print(String(buffer, 0, count))
        count = fr.read(buffer)
    }
    fr.close()
}
```

接下来继续看两个参数的read方法：

```kotlin
/**
public int read(char cbuf[]) throws IOException {
return read(cbuf, 0, cbuf.length);
}
可见底层直接调用了三个参数的方法，每次往buffer数组中读数组length个数据。
 * */
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
    假如把这段代码注释了：
    var count = fr.read(buffer)
    while (count != -1) {
    print(String(buffer, 0, count))
    count = fr.read(buffer)
    }
    使用下面的代码：
    fr.read(buffer)
    println(String(buffer))
    运行结果：落霞与孤鹜齐飞，秋水与长天一色。NULNULNUL（会有一些奇怪的nul特殊字符，由于粘在这里导致github readme解析失败，这我就手打出来了）
   
    这种方式读取的，受申请数组空间影响：
    1、数组空间较小，读取数据不全。
    2、数组空间过大，浪费空间，虽然我们需要的数据被读出来了但同时还会读出多余空间所代表的违法字符"NUL"
    
    除非我们指定的数组空间正好是字符个数，才能无误的读出字符。不建议这种写法。
    
    留意： 可以留意下字节流的这种读法，那里可以精确的指定字节总数，因此那里无误。
* */
    fr.close()
}
```

来看张图理解下read读取的奇怪现象背后原理：

![](https://gitee.com/sunnnydaydev/my-pictures/raw/master/github/file/read_flow.png)

可见：

当数组很小时需要多次读取流中的数据，流中的数据也会不断覆盖填充数组，当最后一次填充数组时，数组中就可能存在上次遗留的数据。要是取数组的全部
数据就会出现奇怪的现象，把上次遗留的数据也一并读了过来。

当数组很大时就一次把流中的数据读到了数组中，且数组还有空余，当取数据时取数组的全部字符，那么数组中无效的剩余空间也会被读取，就会出现NUL这样特殊字符。

###### 3、FileOutputStream

```kotlin
/**
 * 字节输出流：
 *     FileOutputStream
 * 
 * 构造的参数：
 *     1、file对象
 *     2、append：内容是追加还是覆盖，默认false覆盖，填true就是追加。
 *     
 * write方法：常见的也是三个方法
 *     public void write(int b) throws IOException：把int强转byte写进文件
 *     public void write(byte b[]) throws IOException：往文件写字节数组（数组size个字节写入文件）
 *     public void write(byte b[], int off, int len) throws IOException：往文件写字节数组。
 *     
 *     可见这三个方法与FileWriter的类似，只是FileWriter是字符数组，这里是字节数组。
 *     
 * 栗子如下：
 *    使用输出流往本地写个文本文件。
 * 
 * 特点：当文件不存在时FileOutputStream会创建文件，所以你会发现下文的 file.createNewFile()其实是多余的。
 * */
fun fileOutputStream() {
    val file = File("/Users/zb/JavaFilePractice/2.txt")
    if (!file.exists()) {
        file.createNewFile()
    }
    val fos = FileOutputStream(file, true)
    // write提供了几个重载，写字节数组的比较常用。String 直接提供了字符串转字节数组很方便。
    fos.write("滕王阁序1".toByteArray(Charsets.UTF_8))
    fos.close()
}
```


###### 4、FileInputStream

```kotlin
/**
 * 字节输入流：
 *     FileInputStream
 *     
 * 构造的参数：
 *    file对象
 *    
 * read方法: 
 *     public int read() throws IOException:从字节流中读取首个字节数据，返回这个字节代表的int值。
 *     public int read(byte b[]) throws IOException:从字节流中读取b.length个字节到b数组中，返回读取到的字节数目。
 *     public int read(byte b[], int off, int len) throws IOException：从字节流中读取len个字节到b数组中，返回读取到的字节数目。
 * 注意：
 *     1、三个read方法与FileReader的read方法很类似，只是这里为字节数组，FileReader的是字符数组。
 *     2、read读取的个数并不一定是指定的len，当流中数据小于len时，read读取的个数就是流中剩余的个数。
 * 
 * 收获：
 *     1、乱码问题原因，创建文件写字符内容使用的编码方式与读取文件解析字符内容使用的编码方式不一致。
 *     2、文件的length 就是文件内容所占的字节数单位byte。不过这并不是操作系统上一个文件的真实大小，如新建一个空的文本文件
 *        貌似在操作系统上占4k，这里我们也不care。
 * 栗子：
 *     如下
 * */
fun fileInputStream() {
    val file = File("/Users/zb/JavaFilePractice/2.txt")
    val fis = FileInputStream(file)
    val byteArray = ByteArray(1024)
    var byteCount = fis.read(byteArray,0,byteCount.size) //返回值为一次性读取的字节个数
    // 一般这样写即可，和上述写法一样，但看着精简点。
   // var byteCount = fis.read(byteArray)
    while (byteCount != -1) {
        val string =
            String(byteArray, 0, byteCount, Charsets.UTF_8)//若这为Charsets.ISO_8859_1 写时编码为utf-8 结果则乱码。
        println(string)
        byteCount = fis.read(byteArray)
    }
    fis.close()
}
```

```kotlin

/**
 * 1、由于字节总数可以获得，所以我们能够直接创建字节总数大小的数组。然后直接使用read(byteArray)读取不会出现奇怪的现象。
 *    对比FileReader理解下。
 *    
 * 2、这种写法假如文件大时如是个视频文件，一下申请的数组空间比较大可能会内存不足，最好申请 1024*n的空间进行循环读取好点。不建议这种写法
 *    建议使用fileInputStream中的经典写法。
 * */
fun fileInputStream1() {
    val file = File("/Users/zb/JavaFilePractice/2.txt")
    val fis = FileInputStream(file)
    val byteArray = ByteArray(file.length().toInt())//若file.length().toInt()+1 则结果有奇怪字符
    fis.read(byteArray)
    val string = String(byteArray, Charsets.UTF_8)
    println(string)
    fis.close()
}
```

###### 5、缓冲类

Java针对字符流字节流的读取，还提供了缓冲类：

BufferedWriter
- 基于Writer上几个write方法做了重写使方法更加高效读取。
- 使用步骤和Writer#write一致

BufferedReader

- 基于Reader上几个read方法做了重写使方法更加高效读取。
- 使用步骤和Reader#read一致
- 提供了readLine方法，一次可以读取一行字符。

BufferedOutputStream

- 基于OutputStream上几个write方法做了重写使方法更加高效读取。
- 使用步骤和FileOutputStream#write一致

BufferedInputStream

- 基于InputStream上几个read方法做了重写使方法更加高效读取。
- 使用步骤和FileInputStream#read一致

栗子：

```kotlin
/**
 * 使用BufferedInputStream来读取字节数据
 * */
fun bufferedInputStream() {
    val file = File("/Users/zb/JavaFilePractice/2.txt")
    val fis = FileInputStream(file)
    val bis = BufferedInputStream(fis)//接受一个InputStream对象
    val buf = ByteArray(1024)
    var dataCount = bis.read(buf)
    while (dataCount!=-1){
        print(String(buf,0,dataCount))
        dataCount = bis.read(buf)
    }
}
```

```kotlin
/**
 * BufferedReader
 * */
fun bufferedReader() {
    val file = File("/Users/zb/JavaFilePractice/1.txt")
    val fr = FileReader(file)
    val bf = BufferedReader(fr)//接受一个Read对象
    var str = bf.readLine() // 一次读取一行。这里使用其他的read方法也行下发可参考FileReader
    while (str != null) {
        print(str)
        str = bf.readLine()
    }
    fr.close()
}
```

###### 6、缓冲与非缓冲效率对比

```kotlin
/**
 * 目标：测试使用缓冲与不使用缓冲读的效率对比.
 * 栗子：使用普通的FileInputStream，FileOutputStream进行读写。
 * 测试结果：1.pptx 文件在磁盘占10.7M,copy一份耗时66ms
 * */
fun testCommonRead() {
    val src = File("/Users/zb/JavaFilePractice/1.pptx")
    val dest = File("/Users/zb/JavaFilePractice/common.pptx")
    if (!dest.exists()) {
        dest.createNewFile()
    }
    // copy 操作
    val ins = FileInputStream(src)
    val ops = FileOutputStream(dest)

    val buffer = ByteArray(1024)
    var count = ins.read(buffer)
    val startTime = System.currentTimeMillis()
    while (count != -1) {
        println("读取中...")
        ops.write(buffer, 0, count)
        count = ins.read(buffer)
    }
    ins.close()
    ops.close()
    val endTime = System.currentTimeMillis()
    println("读取完毕->耗时:${endTime-startTime}ms")
}
```

```kotlin
/**
 * 目标：测试使用缓冲与不使用缓冲读的效率对比.
 * 栗子：使用BufferedInputStream，BufferedOutputStream进行读写。
 * 测试结果：1.pptx 文件在磁盘占10.7M,copy一份耗时39ms
 * */
fun  testBufferReader(){
    val src = File("/Users/zb/JavaFilePractice/1.pptx")
    val dest = File("/Users/zb/JavaFilePractice/buffer.pptx")
    if (!dest.exists()) {
        dest.createNewFile()
    }
    // copy 操作
    val ins = FileInputStream(src)
    val ops = FileOutputStream(dest)

    val bins = BufferedInputStream(ins)
    val bops = BufferedOutputStream(ops)

    val buffer = ByteArray(1024)
    var count = bins.read(buffer)
    val startTime = System.currentTimeMillis()
    while (count != -1) {
        println("读取中...")
        bops.write(buffer, 0, count)
        count = bins.read(buffer)
    }
    //注意关闭顺序：依赖的先关闭，先打开的后关闭
    bops.close()
    bins.close()
    ops.close()
    ins.close()

    val endTime = System.currentTimeMillis()
    println("读取完毕->耗时:${endTime-startTime}ms")
}
```
可见缓冲类的效率还是比非缓冲类的效率高的，同时我们可以提升数组的大小，来一次多读取点数据，这样也能提升效率。如吧字节数组的大小改为1024*10
使用缓冲类耗时17ms，时间更快了点。

###### 7、转换流

主要涉及到两个类：

InputStreamReader
- public InputStreamReader(InputStream in)
- public InputStreamReader(InputStream in, String charsetName)

OutputStreamWriter
- public OutputStreamWriter(OutputStream out)
- public OutputStreamWriter(OutputStream out, Charset cs)

当我们需要的内容都是文本字符数据时可以吧对应的流直接转换为字符流，更加高效。FileReader，FileWriter就是基于二者实现的。

###### 8、文件压缩解压

- ZipInputStream
- ZipOutputStream

一般用处不多，用到了再了解。

###### 9、文件随机访问

- RandomAccess

一般应用在多线程下载，断点续传。用到了在了解。

[可参考1](https://blog.csdn.net/qq_40100414/article/details/120179117)

[可参考2](https://www.jianshu.com/p/1d2f957d6c95)

###### 10、kt对文件的扩展

- kotlin 针对流、文件等类提供了很多扩展方法，方便我们进行文件的读写。
- kotlin 有个扩展方法use，任意继承自Closeable的类都可以使用，这个扩展会在合适时机自动帮助我们调用close。
- kotlin还对网络（URL类）进行了扩展可以直接通过readText读取网页文本，通过readBytes拿到网络文件的字节数组。


常见的File#write相关的扩展方法如下：

```kotlin
    val file = File("/Users/zb/JavaFilePractice/ktx.txt")
    //写/追加文本
    file.writeText("writeText",Charsets.UTF_8)
    file.appendText("appendText",Charsets.UTF_8)
    //写/追加字符串
    file.writeBytes("writeBytes".toByteArray())
    file.appendBytes("appendBytes".toByteArray())
```

常见的File#read相关的扩展方法如下：

```kotlin
    
    val file = File("/Users/zb/JavaFilePractice/1.txt")
    // readText直接返回一个String类型值，这个string就是要读的全部内容
    val text1 = file.readText()
    val sb = StringBuilder()
    // readLines返回值为List<String>
    file.readLines().forEach {
        sb.append(it)
    }
    val text2 = sb.toString()
    
    // 直接返回一个字节数组
    val byteArray = file.readBytes()
```

网络资源的读取kt也做了拓展

```kotlin
    //直接读取百度网页text
    println(URL("https://www.baidu.com").readText())
    //直接把服务器上的文件写到本地 
    val byteArray =  URL("http://g.hiphotos.baidu.com/image/pic/item/2e2eb9389b504fc2bbdd8ce9ebdde71191ef6d5f.jpg").readBytes()
    File("/Users/zb/JavaFilePractice/1.png").write(byteArray)
```

这里就不一一列举了，想要了解更多可以去kotlin.io包下查看，以copy file的练习做个结尾吧~

```kotlin
fun fileCopy(src: File, des: File) {
    src.let {
        if (it.exists()) {
            if (src.absolutePath == des.absolutePath){
                println("under same dir，same file name. copy failed ！")
            }else{
                des.writeBytes(it.readBytes())
                println("copy success！")
            }
        } else {
            throw IllegalArgumentException("src file not found")
        }
    }
}

fun main(){
    val pathSrc = "/Users/zb/JavaFilePractice/1.txt"
    val pathDes = "/Users/zb/JavaFilePractice/2.txt"
    fileCopy(File(pathSrc), File(pathDes))
}
```








