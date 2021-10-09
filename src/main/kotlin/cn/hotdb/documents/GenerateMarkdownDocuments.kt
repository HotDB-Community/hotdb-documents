package cn.hotdb.documents

import java.io.File

fun main() {
    val rootDir = "documents"
    File(rootDir).walk()
        .filter { it.isFile && !it.name.startsWith("~$") }
        .forEach { generateMarkdown(it) }
}

private fun generateMarkdown(file: File) {
    when (file.extension) {
        "pdf" -> {
            //需要先转为docx
        }
        "doc" -> {
            //需要先转为docx
            //val newFile = convertDocToDocx(file)
            //convertToMarkdown(newFile)
        }
        "xlsx" -> {
            //需要先转为csv
        }
        "docx", "csv" -> {
            convertToMarkdown(file)
        }
    }
}

//private const val wordconv = "\"C:/Program Files (x86)/Microsoft Office/root/Office16/wordconv.exe\""
//
//private fun convertDocToDocx(file: File): File {
//    val inputPath = file.absolutePath
//    val outputPath = file.absolutePath + "x"
//
//    //使用wordconv将doc转化为docx
//    //好像没用
//    val command = "$wordconv -oice -nme $inputPath $outputPath"
//    val result = Runtime.getRuntime().exec(command)
//    result.waitFor()
//    println("Generate docx file '$outputPath' from doc file '$inputPath'.")
//    println(command)
//    printResult(result)
//    println()
//    return File(outputPath)
//}

private const val pandoc = "pandoc"

private fun convertToMarkdown(file: File) {
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

