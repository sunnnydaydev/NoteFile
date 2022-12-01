# kt对文件的扩展

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








