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