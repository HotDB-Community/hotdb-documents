package cn.hotdb.documents

import java.io.File

/*
 * 将documents目录下的非markdown文档转换为markdown文档，并保存到对应的./markdown子目录下
 *
 * * 使用pandoc进行转换
 * * 可以直接转换的文件类型：docx csv
 * * 需要间接转换的文件类型：pdf doc xls xlsx
 */

fun main() {
    val rootDir = "documents"
    File(rootDir).walk().filter { it.isFile && !it.name.startsWith("~$") }.forEach { generateMarkdownFile(it) }
}

private fun generateMarkdownFile(file: File) {
    when (file.extension) {
        "pdf" -> {
            //需要先转为docx（暂时使用迅捷pdf转换器进行转换）
        }
        "doc" -> {
            //需要先转为docx（暂时使用word进行转换）
        }
        "xls", "xlsx" -> {
            //需要先转为csv（暂时使用excel进行转换）
        }
        "docx", "csv" -> {
            convertToMarkdownFile(file)
        }
    }
}

private const val pandoc = "pandoc"

private fun convertToMarkdownFile(file: File) {
    val extension = file.extension
    val inputPath = file.absolutePath
    val outputDir = file.parentFile.absolutePath + File.separator + "markdown"
    val outputPath = outputDir + File.separator + file.nameWithoutExtension + ".md"
    File(outputDir).mkdirs()
    File(outputPath).createNewFile()

    //使用pandoc将各种文档转化为markdown
    val command = "$pandoc -f $extension -t markdown -o \"$outputPath\" \"$inputPath\""
    val result = Runtime.getRuntime().exec(command)
    result.waitFor()

    println("Generate markdown file '$outputPath' from file '$inputPath'.")
    println(command)
    printResult(result)
    println()
}

private fun printResult(result: Process) {
    val output = result.inputStream.bufferedReader().use { it.readText() }.trim()
    val error = result.errorStream.bufferedReader().use { it.readText() }.trim()
    if (output.isNotEmpty()) println(output)
    if (error.isNotEmpty()) println(error)
}
