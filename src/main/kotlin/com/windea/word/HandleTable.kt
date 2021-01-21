package com.windea.word

import java.io.*
import java.util.*

//用来让格式化的表格有正确的长度

private val scanner = Scanner(System.`in`)

fun main(){
	while(true) {
		try {
			println("Input file path:")
			val filePath = scanner.nextLine().trim()
			val file = File(filePath)
			if(file.isDirectory){
				file.listFiles()?.forEach{ handleFile(it) }
			}else{
				handleFile(file)
			}
			println("OK.")
			println()
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}
}


private fun handleFile(file:File){
	val text = file.readText()
	val handledText = handleText(text)
	file.writeText(handledText)
}

private fun handleText(text:String):String{
	return text
}