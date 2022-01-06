package cn.hotdb.documents

import java.io.File

/*
 * 将documents目录下的markdown文档进行必要的处理，以便后续进行差异对比。
 */

fun main() {
    val rootDir = "documents"
    File(rootDir).walk().filter { it.isFile && it.extension == "md" }.forEach { handleMarkdownFile(it) }
}

private fun handleMarkdownFile(file: File){
    val path = file.absolutePath
    val text = file.readText()
    val handledText = handleMarkdownText(text)
    file.writeText(handledText)

    println("Handle markdown file '$path'.")
    println()
}

private val handleRules =  listOf(
    """\[(.*?)]\{.ul}""".toRegex() to "$1"
)

fun handleMarkdownText(text: String): String {
    var t = text
    for ((regex, replacement) in handleRules) {
        t = regex.replace(t, replacement)
    }
    return t
}
