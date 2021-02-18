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
				file.listFiles()?.filter { it.isFile }?.forEach { handleFile(it) }
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
private var isCodeFence = false
private val tableCache = mutableListOf<List<String>>()

private fun handleText(text: String): String {
	return text.lineSequence().mapNotNull { line ->
		//需要忽略代码块中的行
		if(line.startsWith("```")) isCodeFence = !isCodeFence
		if(isCodeFence) return@mapNotNull line
		//判断
		if(!isTable && line.isTableLine()) {
			isTable = true
			//将表头加入缓存
			tableCache.add(line.splitToColumns())
			null
		} else if(isTable) {
			if(line.isTableLine()) {
				//忽略表格分割线
				if(line.matches(tableSeparatorRegex)) return@mapNotNull null
				//将表格行加入缓存
				tableCache.add(line.splitToColumns())
				null
			} else {
				isTable = false
				//拼接处理后的表格+之后的一行
				val table = tableCache.joinToTable()
				tableCache.clear()
				table + line
			}
		} else {
			line
		}
	}.joinToString("\n")
}

private fun String.isTableLine(): Boolean {
	return startsWith('|') && endsWith('|') && count { it == '|' } >= 3
}

//1. |可能被转义 2. |可能是代码
private fun String.splitToColumns(): List<String> {
	//return split("|").map { it.trim() } //不能这样！
	val chars = this.trimStart('|').toCharArray()
	val columnChars = mutableListOf<MutableList<Char>>()
	var charList = mutableListOf<Char>()
	var isCode = false
	var isEscape = false
	for(char in chars) {
		if(char == '\\' && !isEscape) isEscape=true
		if(char == '`') isCode = !isCode
		if(char == '|' &&!isEscape && !isCode){
			columnChars.add(charList)
			charList = mutableListOf()
		}else{
			charList.add(char)
		}
		if(isEscape) isEscape = false
	}
	return columnChars.map { String(it.toCharArray()).trim() }
}

private fun List<List<String>>.joinToTable(): String {
	var isHeader = true
	val fixedLengths = this.getFixedLengths()
	val indices = fixedLengths.indices
	return buildString {
		for(row in this@joinToTable) {
			indices.joinTo(this," | ", "| ", " |\n") { i ->
				val column = row[i]
				val fixedLength = column.fixedLength
				val expectFixedLength = fixedLengths[i]
				when{
					fixedLength == expectFixedLength -> column
					fixedLength < expectFixedLength -> column + " ".repeat(expectFixedLength - fixedLength)
					else -> throw IllegalStateException()
				}
			}
			if(isHeader) {
				isHeader = false
				indices.joinTo(this,"-|-", "|-", "-|\n") { i ->
					val expectFixedLength = fixedLengths[i]
					"-".repeat(expectFixedLength)
				}
			}
		}
	}
}

private fun List<List<String>>.getFixedLengths(): List<Int> {
	val indices = this.first().indices
	return indices.map { i -> this.map {
		//可能会报IndexOutOfBoundsException
		try {
			it[i].fixedLength
		} catch(e: Exception) {
			println(it)
			throw e
		} 
	}.maxOrNull() ?: throw IllegalArgumentException() }
}

