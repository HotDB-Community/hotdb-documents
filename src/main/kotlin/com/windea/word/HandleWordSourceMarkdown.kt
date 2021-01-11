@file:Suppress("UnnecessaryVariable")

package com.windea.word

import java.io.*
import java.util.*
import java.util.concurrent.*
import kotlin.system.*

private val data = mapOf(
	"install-and-deploy.md" to mapOf("firstTitle" to "# 部署环境"),
	"glossary.md" to mapOf("firstTitle" to "# 常用名词")
)
private val executor = Executors.newCachedThreadPool()

//zh/latest install-and-deploy.md
//zh/latest glossary.md
//en/latest glossary.md

fun main(args: Array<String>) {
	println("Args: ${args.contentToString()}")
	if(args.isEmpty()) {
		val scanner = Scanner(System.`in`)
		while(true) {
			println("Command format1: parent")
			println("Command format2: parent fileName[ fileName2 ...]")
			val line = scanner.nextLine().trim()
			if(line == "exit") exitProcess(0)
			
			try {
				val arguments = line.split(' ')
				val parent = arguments[0]
				val fileNames = if(arguments.size == 1) File("docs/$parent").list()?.filter { it.endsWith(".md") } ?: emptyList() else arguments.drop(1)
				handleWordSourceMarkdowns(parent, fileNames)
			} catch(e: Exception) {
				e.printStackTrace()
			}
		}
	} else {
		try {
			val arguments = args
			val parent = arguments[0]
			val fileNames = arguments.drop(1)
			handleWordSourceMarkdowns(parent, fileNames)
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}
}


fun handleWordSourceMarkdowns(parent: String, fileNames: List<String>) {
	println("Handle word source markdowns...")
	val fileSize = fileNames.size
	val countDown = CountDownLatch(fileSize)
	fileNames.forEach { fileName ->
		executor.submit {
			try {
				handleWordSourceMarkdown("docs/${parent}", fileName)
				countDown.countDown()
			} catch(e: Exception) {
				e.printStackTrace()
			}
		}.get()
	}
	countDown.await()
	println("Handle word source markdowns finished.")
	println()
}

fun handleWordSourceMarkdown(parent: String, fileName: String) {
	println("Handle word source markdown: ${parent}/${fileName}...")
	val file = File(parent, fileName)
	val text = file.readText()
		.replaceToNormalWhiteSpace()
		.removePrefixContent(fileName)
		.changeImageRelUrl(fileName)
		.removeImageSizeAttributes()
		.removeHeadingAttributes()
		.replaceToHeadingLink()
		.removeDuplicates()
		.trimLineBreak()
	file.writeText(text)
	println("Handle word source markdown: ${parent}/${fileName} finished.")
	println()
}

private fun String.replaceToNormalWhiteSpace(): String {
	return this.replace(" ", " ")
}

private fun String.removePrefixContent(fileName: String): String {
	//到类似以下两行为止	
	//[[reset @\@dberrorcount]{.ul}](#reset-dberrorcount将所有逻辑库报错信息清空)将逻辑库的报错信息清空
	//#
	var keep = false
	val firstTitle = data[fileName]?.get("firstTitle") ?: return this
	val lines = mutableListOf<String>()
	for(line in lines()) {
		if(line == firstTitle) keep = true
		if(keep) lines.add(line)
	}
	if(!keep) return this
	return lines.joinToString("\n")
}

private val changeImageRelUrlRegex = """!\[[^]]*]\(media""".toRegex()

private fun String.changeImageRelUrl(fileName: String, relPath: String = "assets/${fileName.substringBeforeLast('.')}"): String {
	return this.replace(changeImageRelUrlRegex, "![](${relPath}")
}

private val removeImageSizeAttributesRegex = """\{width="[\w.]*?" height="[\w.]*?"}""".toRegex()

private fun String.removeImageSizeAttributes(): String {
	return this.replace(removeImageSizeAttributesRegex, "")
}

//{#基本信息 .list-paragraph}
private fun String.removeHeadingAttributes(): String {
	return this.lines().joinToString("\n") { line ->
		if(line.startsWith('#')) line.substringBefore('{') else line
	}
}

private val replaceToHeadingLinkRegex = """"(.*?)"""".toRegex()

//TODO 不准确
private fun String.replaceToHeadingLink(): String {
	//不替换未在统一文档中找到的标题
	val lines = this.lineSequence()
	val titles = lines.filter { it.startsWith('#') }.map { it.trim(' ', '#') }
	return this.replace(replaceToHeadingLinkRegex) { r ->
		val s = r.groupValues[1]
		if(s in titles) "[$s](#${s.urlEncode()})" else "\"$s\""
	}
}

private val removeDuplicatesRegex = """\[([^]\s]+?)]\{\.ul}""".toRegex()

private fun String.removeDuplicates(): String {
	return this.replace(removeDuplicatesRegex, "$1") //不知道啥东西
		.replace("```{=html}\n<!-- -->\n```", "") //不知道啥东西
		.lines().filter { it.trim(' ','.').toIntOrNull() == null } //单独的序号
		.joinToString("\n")
}

private val trimLineBreakRegex = """\s*\n\n\s*""".toRegex()

private fun String.trimLineBreak(): String {
	return this.replace(trimLineBreakRegex, "\n\n")
}

private fun String.fileNameWithoutExtension() = this.substringBeforeLast('.')

private fun String.urlEncode() = this.replace(" ", "%20")