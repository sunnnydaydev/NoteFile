package com.sunnyday.libfilebasic

import java.io.*
import java.nio.charset.Charset

/**
 * Create by SunnyDay /11/29 14:05:15
 */
fun main() {
    // fileWriter()
    //fileWriterTest()
   //  fileReader()
    // fileReader1()
     fileReader2()
    // bufferedReader()
    // fileOutputStream()
    // fileOutputStream()
    // fileInputStream()
   // fileInputStream1()
   // bufferedInputStream()
}

fun bufferedInputStream() {
    val start = System.currentTimeMillis()
    val file = File("/Users/zennioptical/JavaFilePractice/2.txt")
    val fis = FileInputStream(file)
    val buffer = ByteArray(1024)
    val bis = BufferedInputStream(fis)

    var index = bis.read(buffer)
    while (index != -1) {
        val string = String(buffer, 0, index)
        println(string)
        index = bis.read(buffer)
    }
    bis.close()
    fis.close()
    val end = System.currentTimeMillis()
    println("耗时：${end - start}")
}


fun fileInputStream1() {
    val file = File("/Users/zennioptical/JavaFilePractice/2.txt")
    val fis = FileInputStream(file)
    val byteArray = ByteArray(1024)
    //读取byteArray size个字节到字节数组中。流中小于size时则以真实个数为主。
    // 返回值为读取个数
    var data = fis.read(byteArray)
    while (data != -1) {
        println("data:$data")
        val string = String(byteArray, 0, data)
        println(string)
        data = fis.read(byteArray)
    }
    fis.close()
}

fun fileInputStream() {
    val file = File("/Users/zennioptical/JavaFilePractice/2.txt")
    val fis = FileInputStream(file)
    val byteArray = ByteArray(file.length().toInt())
    fis.read(byteArray)
    val string = String(byteArray, Charsets.UTF_8)
    println(string)
    fis.close()
}

fun fileOutputStream() {
    val file = File("/Users/zennioptical/JavaFilePractice/2.txt")
    if (!file.exists()) {
        file.createNewFile()
    }
    val fos = FileOutputStream(file)
    // write提供了几个重载，但一般都是把内容转为字节数组。
    fos.write("滕王阁序".toByteArray(Charsets.UTF_8))
    fos.close()
}


/**
 * 使用BufferedReader来提升读取效率，这玩意提供了readLine方法每次可读一行。
 * */
fun bufferedReader() {
    val file = File("/Users/zennioptical/JavaFilePractice/1.txt")
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
    fw.write("12345",1,3) //234写进文件
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