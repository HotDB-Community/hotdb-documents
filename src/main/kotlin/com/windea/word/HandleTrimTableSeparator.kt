package com.windea.word

import java.io.*
import java.util.*

//用来优化表格分隔符的长度

private val scanner = Scanner(System.`in`)

fun main() {
	while(true) {
		try {
			println("Input file path:")
			val filePath = scanner.nextLine().trim()
			val file = File(filePath)
			if(file.isDirectory) {
				file.listFiles()?.filter{ it.isFile }?.forEach { handleFile(it) }
			} else {
				handleFile(file)
			}
			println("OK.")
			println()
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}
}

private fun handleFile(file: File) {
	val text = file.readText()
	val handledText = handleText(text)
	file.writeText(handledText)
}

private var isTable = false
private var isTableSeparator = false
private var tableHeaderCache = ""

private fun handleText(text: String): String {
	return text.lineSequence().mapNotNull { line ->
		if(!isTable && line.startsWith('|') && line.endsWith('|') && line.count { it == '|' } >= 3) {
			isTable = true
			isTableSeparator = true
			tableHeaderCache = line
			line
		}else if(isTableSeparator && line.startsWith("|-")){
			isTableSeparator = false
			buildString{
				tableHeaderCache.forEach { c->
					if(c == '|') append('|') else if(c.isChinese()) append("--") else append('-')
				}
			}
		}else if(isTable && !line.startsWith('|')){
			isTable = false
			line
		}else{
			line
		}
	}.joinToString("\n")
}

private fun Char.isChinese():Boolean{
	return Character.UnicodeScript.of(this.toInt()) == Character.UnicodeScript.HAN
}