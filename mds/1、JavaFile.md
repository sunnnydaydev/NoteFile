# Java File

# File类

###### 1、File和IO

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