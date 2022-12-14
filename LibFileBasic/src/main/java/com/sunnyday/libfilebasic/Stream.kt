package com.sunnyday.libfilebasic

import java.io.*
import java.lang.IllegalArgumentException
import java.net.URL
import java.nio.charset.Charset
import java.util.zip.ZipInputStream

/**
 * Create by SunnyDay /11/29 14:05:15
 */
fun main() {
    // fileWriter()
    //fileWriterTest()
    //  fileReader()
    // fileReader1()
    //  fileReader2()
    // bufferedReader()
    // fileOutputStream()

    // fileInputStream()
    // fileInputStream1()
    //bufferedInputStream()
    // ktx1()
    val pathSrc = "/Users/zb/JavaFilePractice/1.txt"
    val pathDes = "/Users/zb/JavaFilePractice/2.txt"
    fileCopy(File(pathSrc), File(pathDes))
}

fun fileCopy(src: File, des: File) {
    src.let {
        if (it.exists()) {
            if (src.absolutePath == des.absolutePath){
                println("under same dir，same file name。copy failed ！")
            }else{
                des.writeBytes(it.readBytes())
                println("copy success！")
            }

        } else {
            throw IllegalArgumentException("src file not found")
        }
    }
}

fun ktx1() {
    val file = File("/Users/zb/JavaFilePractice/ktx.txt")
    //写/追加文本
    file.writeText("writeText", Charsets.UTF_8)
    file.appendText("appendText", Charsets.UTF_8)
    //写/追加字符串
    file.writeBytes("writeBytes".toByteArray())
    file.appendBytes("appendBytes".toByteArray())
}

fun ktx2() {

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
}

/**
 * 提供缓冲功能：重写了几个read方法。
 * 具体的定位还真的有点不完全清楚。
 * */
fun bufferedInputStream() {
    val file = File("/Users/zb/JavaFilePractice/2.txt")
    val fis = FileInputStream(file)
    val bis = BufferedInputStream(fis)
    val byte = bis.readAllBytes() //最大字节数2147483647 1G的数据
    println(String(byte, Charset.defaultCharset()))
}

/**
 * 1、由于字节总数可以获得，所以我们能够直接创建字节总数大小的数组。然后直接使用read(byteArray)读取不会出现奇怪的现象。
 * 对比FileReader理解下。
 * 2、这种写法加入文件大时如是个视频文件，一下申请的数组空间比较大，最好申请 1024*n的空间进行循环读取好点。不建议这种写法
 * 建议使用经典写法。
 *
 * */
fun fileInputStream1() {
    val file = File("/Users/zb/JavaFilePractice/2.txt")
    val fis = FileInputStream(file)
    val byteArray = ByteArray(file.length().toInt())//file.length().toInt()+1
    fis.read(byteArray)
    val string = String(byteArray, Charsets.UTF_8)
    println(string)
    fis.close()
}

/**
 * 收获：
 * 1、乱码问题原因 创建文件写字符内容使用的编码方式，与读取文件解析字符内容使用的编码方式不一致。
 * 2、文件的length 就是文件内容所占的字节数单位byte。不过这并不是操作系统上一个文件的真实大小，如新建一个空的文本文件
 * 貌似在操作系统上占4k，这里我们也不care。
 * */
fun fileInputStream() {
    val file = File("/Users/zb/JavaFilePractice/2.txt")
    println("文件大小:${file.length()}") // length 就是文件内容所占字节数。
    val fis = FileInputStream(file)
    val byteArray = ByteArray(1024)
    var byteCount = fis.read(byteArray) //返回值为一次性读取的字节个数
    while (byteCount != -1) {
        println("data:$byteCount")
        val string =
            String(byteArray, 0, byteCount, Charsets.UTF_8)//Charsets.ISO_8859_1 写时utf-8 读时这个就是乱码。
        println(string)
        byteCount = fis.read(byteArray)
    }
    fis.close()
}

/**
 * 使用输出流往本地写个文本文件。
 * */
fun fileOutputStream() {
    val file = File("/Users/zb/JavaFilePractice/2.txt")
    if (!file.exists()) {
        file.createNewFile()
    }
    val fos = FileOutputStream(file, true) //默认覆盖，这里可控制追加内容。
    // write提供了几个重载，写字节数组的比较常用。string 直接提供了字符串转字节数组。
    fos.write("滕王阁序1".toByteArray(Charsets.UTF_8))
    fos.close()
}


/**
 * 使用BufferedReader来提升读取效率，这玩意提供了readLine方法每次可读一行。
 * 收获：
 * 1、BufferedReader 也是继承自Reader的在Reader的基础上提供了readLine方法
 * 2、kt的ReadWrite.kt 文件有很多扩展方法可以快速实现文件读写，如读文件直接readText读完
 * 3、kotlin 有个扩展方法use，任意继承自Closeable的类都可以使用，这个扩展会在合适时机自动帮助我们调用close。
 * */
fun bufferedReader() {
    val file = File("/Users/zb/JavaFilePractice/1.txt")
    val fr = FileReader(file)
    val bf = BufferedReader(fr)
    var str = bf.readLine()
    while (str != null) {
        print(str)
        str = bf.readLine()
    }
    fr.close()
}

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
    var count = fr.read(buffer, 0, buffer.size)
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

/**
源码：
public int read(char cbuf[]) throws IOException {
return read(cbuf, 0, cbuf.length);
}
可见底层直接调用了三个参数的方法，每次读取的个数为数组的大小。

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
     *
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

/**
 * FileReader栗子：
 * */
fun fileReader() {
    val file = File("/Users/zb/JavaFilePractice/1.txt")
    val fr = FileReader(file)
    var index = fr.read() //读取一个字符，流结束时这个api返回-1
    while (index != -1) {
        print(index.toChar())
        index = fr.read()
    }
    fr.close()
}


fun fileWriterTest() {
    val file = File("/Users/zb/JavaFilePractice/fileWriterTest.txt")
    if (!file.exists()) {
        file.createNewFile()
    }
    val fw = FileWriter(file)
    fw.write("12345", 1, 3) //234写进文件
    fw.flush()
    fw.close()
}

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