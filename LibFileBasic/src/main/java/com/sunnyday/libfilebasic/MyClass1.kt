package com.sunnyday.libfilebasic

import java.io.File

/**
 * Create by SunnyDay /11/28 16:09:44
 */
fun main() {
    val file = File( "/Users/zennioptical/JavaFilePractice/1.txt")
    val desc = File( "/Users/zennioptical/JavaFilePractice/Test/2.txt")
    file.renameTo(desc)
}