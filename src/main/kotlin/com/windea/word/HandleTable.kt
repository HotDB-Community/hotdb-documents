package com.windea.word

import java.io.*
import java.util.*

private val scanner = Scanner(System.`in`)

fun main(){
	while(true) {
		try {
			println("Input file path:")
			val filePath = scanner.nextLine().trim()
			val file = File(filePath)
			val text = file.readText()
			val handledText = handleText(text)
			file.writeText(handledText)
			println("OK.")
			println()
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}
}

private fun handleText(text:String):String{
	return text
}