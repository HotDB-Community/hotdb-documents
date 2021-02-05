package com.windea.word

import java.io.*
import java.util.*

//用来让格式化的表格有正确的长度

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

private val tableSeparatorRegex = """^\|(-+\|)+$""".toRegex()
private var isTable = false
private var tableHeader = ""
private val tableCache = mutableListOf<List<String>>()

private fun handleText(text: String): String {
	return text.lineSequence().mapNotNull { line ->
		if(!isTable && line.isTableLine()) {
			isTable = true
			//将表头加入缓存
			tableCache.add(line.splitToColumns())
			null
		}else if(isTable){
			if(line.isTableLine()){
				//忽略表格分割线
				if(line.matches(tableSeparatorRegex)) return@mapNotNull null
				//将表格行加入缓存
				tableCache.add(line.splitToColumns())
				null
			}else{
				isTable = false
				//拼接处理后的表格+之后的一行
				val table = tableCache.joinToTable()
				tableCache.clear()
				table + "\n" + line
			}
		}else{
			line
		}
	}.joinToString("\n")
}

private fun Char.isChinese():Boolean{
	return Character.UnicodeScript.of(this.toInt()) == Character.UnicodeScript.HAN
}

private fun String.isTableLine():Boolean{
	return startsWith('|') && endsWith('|') && count { it == '|' } >= 3
}

private fun String.splitToColumns():List<String>{
	return split("|") //不能这样！
}

private fun List<List<String>>.joinToTable():String{
	val isHeader = true
	return buildString {
		for(row in tableCache) {
			for(column in row) {
				
			}
		}
	}
}