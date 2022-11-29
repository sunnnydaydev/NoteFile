package com.sunnyday.libfilebasic

import java.io.*
import java.nio.charset.Charset

/**
 * Create by SunnyDay /11/29 14:05:15
 */
fun main() {
    // fileWriter()
    fileWriterTest()

    // fileReader()
    // fileReader1()
    // fileReader2()
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

fun fileReader2() {
    val file = File("/Users/zennioptical/JavaFilePractice/1.txt")
    val fr = FileReader(file)
    val buffer = CharArray(30)
    fr.read(buffer, 0, 30)
    buffer.forEach {
        print("$it")
    }
    fr.close()
}


fun fileReader1() {
    val file = File("/Users/zennioptical/JavaFilePractice/1.txt")
    val fr = FileReader(file)
    val buffer = CharArray(30)
    fr.read(buffer)
    buffer.forEach {
        print("$it")
    }
    fr.close()
}

/**
 * FileReader栗子：
 * */
fun fileReader() {
    val file = File("/Users/zennioptical/JavaFilePractice/1.txt")
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