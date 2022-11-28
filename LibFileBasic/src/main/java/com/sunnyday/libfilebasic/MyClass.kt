package com.sunnyday.libfilebasic

import java.io.File

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