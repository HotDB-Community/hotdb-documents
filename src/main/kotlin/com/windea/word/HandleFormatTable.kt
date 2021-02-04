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

private val tableSeparatorRegex = """\+(-+\+)+""".toRegex()
private var tableStart = false
private var tableSeparatorStart = false
private var tableHeader = ""

private fun handleText(text: String): String {
	return text.lineSequence().mapNotNull { line ->
		if(!tableStart && line.startsWith('|') && line.endsWith('|') && line.count { it == '|' } >= 3) {
			tableStart = true
			tableSeparatorStart = true
			tableHeader = line
			line
		}else if(tableSeparatorStart && line.startsWith("|-")){
			tableSeparatorStart = false
			buildString{
				tableHeader.forEach { c->
					if(c == '|') append('|') else if(c.isChinese()) append("--") else append('-')
				}
			}
		}else if(tableStart && !line.startsWith('|')){
			tableStart = false
			line
		}else{
			line
		}
	}.joinToString("\n")
}

private fun Char.isChinese():Boolean{
	return Character.UnicodeScript.of(this.toInt()) == Character.UnicodeScript.HAN
}