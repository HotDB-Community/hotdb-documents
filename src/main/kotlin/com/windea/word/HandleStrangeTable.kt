package com.windea.word

import java.io.*
import java.util.*

//用来处理从word转化成markdown生成的奇奇怪怪的表格

//----------------- ------------------ ---------------------
//
//**列名**          **说明**           **值类型/范围**
//  id                连接id             INTEGER/[number]

private val scanner = Scanner(System.`in`)

fun main(){
	while(true) {
		try {
			println("Input file path:")
			val filePath = scanner.nextLine().trim()
			val file = File(filePath)
			val text = file.readText()
			val handledText = handleText(text)
			File(filePath + ".bak").writeText(handledText)
			println("OK.")
			println()
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}
}

private val tableStartLineRegex = """^-{3,}( -{3,})+$""".toRegex()
private val blankRegex = """[ ]+""".toRegex()
private var tableStart = false
private var tableHeader = false

private fun handleText(text:String):String{
	//遍历每一行
	return text.lineSequence().mapNotNull { line->
		when {
			//开始
			!tableStart && line.matches(tableStartLineRegex) -> {
				tableStart = true
				tableHeader = true
				null
			}
			//结束
			tableStart && line.matches(tableStartLineRegex) -> {
				tableStart = false
				null
			}
			//去除其中的空行
			tableStart && line.isBlank() -> null
			//以空白分割，去除每一列作为前后缀的"*"
			tableStart && tableHeader -> {
				tableHeader = false
				val cols = line.trim().split(blankRegex)
				cols.joinToString(" | ","| "," |"){ it.trim('*') } + "\n" + cols.indices.joinToString(" | ","| "," |"){ "---" }
			}
			tableStart && !tableHeader -> {
				val cols = line.trim().split(blankRegex)
				cols.joinToString(" | ","| "," |")
			}
			else -> line
		}
	}.joinToString("\n")
}










