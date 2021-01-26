@file:Suppress("UnnecessaryVariable")

package com.windea.word

import java.io.*
import java.nio.file.*
import java.util.*
import java.util.concurrent.*
import kotlin.system.*

private val data = mapOf(
	"installation-and-deployment.md" to mapOf("firstTitle" to "# 部署环境"),
	"glossary.md" to mapOf("firstTitle" to "# 常用名词")
)
private val executor = Executors.newCachedThreadPool()

//zh/latest installation-and-deployment.md
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
			val fileNames = if(arguments.size == 1) File("docs/$parent").list()?.filter { it.endsWith(".md") } ?: emptyList() else arguments.drop(1)
			handleWordSourceMarkdowns(parent, fileNames)
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}
}


fun handleWordSourceMarkdowns(parent: String, fileNames: List<String>) {
	val parentPath = "docs/${parent}"
	println("Handle word source markdowns in path ${parentPath}...")
	val fileSize = fileNames.size
	val countDown = CountDownLatch(fileSize)
	fileNames.forEach { fileName ->
		executor.execute {
			try {
				handleWordSourceMarkdown(parentPath, fileName)
				countDown.countDown()
			} catch(e: Exception) {
				e.printStackTrace()
			}
		}
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
		.unescape()
		.removePrefixContent(fileName)
		.optimizeHeading()
		.optimizeOrderedList()
		.changeImageRelUrl(fileName)
		.removeImageSizeAttributes()
		.replaceToHeadingLink()
		.replaceToTable()
		.removeDuplicates()
		.trimLineBreak()
	file.writeText(text)
	println("Handle word source markdown: ${parent}/${fileName} finished.")
}

private val unescapeRegex = """\\([<>\-_"'*|@\[\]#`$])""".toRegex()

private fun String.replaceToNormalWhiteSpace(): String {
	return this.replace(" ", " ")
}

private fun String.unescape():String{
	return this.replace(unescapeRegex,"$1")
}


private fun String.removePrefixContent(fileName: String): String {
	//TODO 
	//到类似以下两行为止	
	//[[reset @\@dberrorcount]{.ul}](#reset-dberrorcount将所有逻辑库报错信息清空)将逻辑库的报错信息清空
	//#
	var keep = false
	val firstTitle = data[fileName]?.get("firstTitle") ?: return this
	val lines = mutableListOf<String>()
	for(line in lineSequence()) {
		if(line == firstTitle) keep = true
		if(keep) lines.add(line)
	}
	if(!keep) return this
	return lines.joinToString("\n")
}

private val optimizeHeadingRegex = """(?:\d+(?:\.\d+)*\.)?\s*(#+)(?:\s*\d+(?:\.\d+)*\.)?""".toRegex()

private fun String.optimizeHeading(): String {
	return this.lineSequence().joinToString("\n") { line ->
		var l =line.replace(optimizeHeadingRegex,"$1") //去除标题中的序号
		//不能这样做，有时就是需要指定
		//l= if(line.startsWith('#')) l1.substringBefore('{') else l1  //移除末尾的attributes
		l
	}
}

private	val orderRegex = """^(\d+)、""".toRegex()

private fun String.optimizeOrderedList():String{
	return this.lineSequence().joinToString("\n"){line->
		//不要转义序号中的点
		line.replace("\\.",".").replace(orderRegex,"$1. ")
	} 
}

private val changeImageRelUrlRegex = """!\[[^]]*]\(media""".toRegex()

private fun String.changeImageRelUrl(fileName: String, relPath: String = "assets/${fileName.substringBeforeLast('.')}"): String {
	return this.replace(changeImageRelUrlRegex, "![](${relPath}")
}

private val removeImageSizeAttributesRegex = """\{width="[\w.]*?" height="[\w.]*?"}""".toRegex()

private fun String.removeImageSizeAttributes(): String {
	return this.replace(removeImageSizeAttributesRegex, "")
}

private val replaceToHeadingLinkRegex = """"(.*?)"""".toRegex()

//TODO 不准确，可能会是其他文档的
private fun String.replaceToHeadingLink(): String {
	//不替换未在统一文档中找到的标题
	val lines = this.lineSequence()
	val titles = lines.filter { it.startsWith('#') }.map { it.trim(' ', '#') }
	return this.replace(replaceToHeadingLinkRegex) { r ->
		val s = r.groupValues[1]
		if(s in titles) "[$s](#${s.urlEncode()})" else "\"$s\""
	}
}

private val tableRowSeparatorRegex = """\+(-+\+)+""".toRegex()
private val tableHeaderSeparatorRegex = """\+(=+\+)+""".toRegex()
private val duplicateRowRegex = """\s*\|\s*\|\s*\|\s*\|\s*\|\s*\|""".toRegex()

//黑人问号啊
private fun String.replaceToTable():String{
	//不处理 - 可能是sql结果
	return this
	
	//return this.lineSequence().mapNotNull { line->
	//	when {
	//		line.matches(tableRowSeparatorRegex) -> null
	//		line.matches(tableHeaderSeparatorRegex) -> line.replace("+=","|:").replace("+","|").replace("=","-")
	//		else -> line
	//	}
	//}.joinToString("\n").replace(duplicateRowRegex,"")
}

private val removeDuplicatesRegex = """\[([^]\s]+?)]\{\.ul}""".toRegex()

private fun String.removeDuplicates(): String {
	return this.replace(removeDuplicatesRegex, "$1") //不知道啥东西
		.replace("```{=html}\n<!-- -->\n```", "") //不知道啥东西
		.lines().filter { it.trim(' ','.').toIntOrNull() == null } //单独的序号
		.joinToString("\n")
}

private val trimLineBreakRegex = """\s*\n\n""".toRegex()

private fun String.trimLineBreak(): String {
	return this.replace(trimLineBreakRegex, "\n\n")
}

private fun String.fileNameWithoutExtension() = this.substringBeforeLast('.')

private fun String.urlEncode() = this.replace(" ", "%20")