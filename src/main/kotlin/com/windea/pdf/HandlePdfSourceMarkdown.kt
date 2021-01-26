@file:Suppress("UnnecessaryVariable")

package com.windea.pdf

import com.windea.word.*
import java.io.*
import java.util.*
import java.util.concurrent.*
import kotlin.system.*

private val data = mapOf(
	"installation-and-deployment.md" to mapOf("firstTitle" to "1\\. 部署环境"),
	"glossary.md" to mapOf("firstTitle" to "1\\. 常用名词")
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
				val fileNames = if(arguments.size==1) File("docs/$parent").list()?.filter{ it.endsWith(".md") }?:emptyList()  else arguments.drop(1)
				handlePdfSourceMarkdowns(parent, fileNames)
			} catch(e: Exception) {
				e.printStackTrace()
			}
		}
	} else {
		try {
			val arguments = args
			val parent = arguments[0]
			val fileNames = arguments.drop(1)
			handlePdfSourceMarkdowns(parent, fileNames)
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}
}

fun handlePdfSourceMarkdowns(parent: String, fileNames: List<String>) {
	println("Handle pdf source markdowns...")
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
	println("Handle pdf source markdowns finished.")
	println()
}

fun handlePdfSourceMarkdown(parent: String, fileName: String) {
	println("Handle pdf source markdown: ${parent}/${fileName}...")
	val file = File(parent, fileName)
	val text = file.readText()
		.removeAllBlockQuoteMarkers()
		.removePrefixContent(fileName)
		.changeImageRelUrl(fileName)
		.removeImageSizeAttributes()
		.removePageContent()
		.removePageNumber()
		.replaceToHeading()
		.replaceToHeadingLink()
		.replaceToTable()
		.replaceToBlock(fileName)
		.trimLineBreak()
	file.writeText(text)
	println("Handle pdf source markdown: ${parent}/${fileName} finished.")
	println()
}

private fun String.removePrefixContent(fileName: String): String {
	val lines = mutableListOf<String>()
	var keep = false
	val firstTitle = data[fileName]?.get("firstTitle") ?: return this
	for(line in lines()) {
		if(line == firstTitle) keep = true
		if(keep) lines.add(line)
	}
	if(!keep) return this
	return lines.joinToString("\n")
}

private fun String.removeAllBlockQuoteMarkers(): String {
	return this.replace("> ", "").replace("\n>\n", "\n\n")
}

private val changeImageRelUrlRegex = """!\[[^]]*]\(media""".toRegex()

private fun String.changeImageRelUrl(fileName: String, relPath: String = "assets/${fileName.substringBeforeLast('.')}"): String {
	return this.replace(changeImageRelUrlRegex, "![](${relPath}")
}

private val removeImageSizeAttributesRegex = """\{width="[\w.]*?" height="[\w.]*?"}""".toRegex()

private fun String.removeImageSizeAttributes(): String {
	return this.replace(removeImageSizeAttributesRegex, "")
}

val removePageContentRegex = """\s*\n((\d+)|(第\d+页 /共\d+页))\n\s*!\[[^]]*]\([^\s)]*\)(\n\n)?[^\r\n]+\n\s*""".toRegex()

private fun String.removePageContent():String{
	return this.replace(removePageContentRegex,"")
}

val removePageNumberRegex = """\s*\n((\d+)|(第\d+页 /共\d+页))\n\s*""".toRegex()

private fun String.removePageNumber(): String {
	return this.replace(removePageNumberRegex, "")
}

private val orderRegex = """^((?:\d+)(?:\\?\.\d+)*)\\?\.?\s*([^\r\n]+)""".toRegex()

private fun String.replaceToHeading(): String {
	return this.lines().joinToString("\n") { line ->
		orderRegex.replace(line) { r -> "${"#".repeat(r.groupValues[1].count { it == '.' } + 1)} ${r.groupValues[2]}" }
	}
}

private val replaceToHeadingLinkRegex = """"(.*?)"""".toRegex()

//TODO 不准确
private fun String.replaceToHeadingLink(): String {
	//不替换未在统一文档中找到的标题
	val lines = this.lineSequence()
	val titles = lines.filter { it.startsWith('#') }.map { it.trim(' ','#') }
	return this.replace(replaceToHeadingLinkRegex){ r->
		val s = r.groupValues[1]
		if(s in titles) "[$s](#${s.urlEncode()})" else "\"$s\""
	}
}

private val tableRowSeparatorRegex = """\+(-+\+)+""".toRegex() 
private val tableHeaderSeparatorRegex = """\+(=+\+)+""".toRegex() 
private val duplicateRowRegex = """\s*\|\s*\|\s*\|\s*\|\s*\|\s*\|""".toRegex()

private fun String.replaceToTable():String{
	return this.lineSequence().mapNotNull { line->
		when {
			line.matches(tableRowSeparatorRegex) -> null
			line.matches(tableHeaderSeparatorRegex) -> line.replace("+=","|:").replace("+","|").replace("=","-")
			else -> line
		} 
	}.joinToString("\n").replace(duplicateRowRegex,"")
}

private val singleColumnTableHeaderSeparatorRegex = """^\|:?-+:?\|$""".toRegex()

//TODO 需要继续手动调整
//TODO 也可能是代码块
private fun String.replaceToBlock(fileName:String):String{ //fromSingleColumnTable
	return when{
		fileName == "glossary.md" -> replaceToBlockQuote()
		else -> replaceToCodeFence()
	}
}

private fun String.replaceToBlockQuote(): String {
	return this.lineSequence().mapNotNull { line ->
		when {
			line.matches(singleColumnTableHeaderSeparatorRegex) -> null
			line.startsWith('|') && line.endsWith('|') && line.count { it == '|' } == 2 -> "> ${line.trim(' ', '|')}"
			else -> line
		}
	}.joinToString("\n")
}

private fun String.replaceToCodeFence(): String {
	return this.lineSequence().mapNotNull { line ->
		when {
			line.matches(singleColumnTableHeaderSeparatorRegex) -> null
			line.startsWith('|') && line.endsWith('|') && line.count { it == '|' } == 2 -> "> ${line.trim(' ', '|')}"
			else -> line
		}
	}.joinToString("\n")
}

private val trimLineBreakRegex = """\s*\n\n\s*""".toRegex()

private fun String.trimLineBreak():String{
	return this.replace(trimLineBreakRegex,"\n\n")
}

private fun String.fileNameWithoutExtension() = this.substringBeforeLast('.')

private fun String.urlEncode() = this.replace(" ","%20")