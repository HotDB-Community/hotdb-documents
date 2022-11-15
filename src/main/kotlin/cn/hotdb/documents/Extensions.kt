package cn.hotdb.documents

/** 判断字符是否是中文或中文标点（字符的显示长度是2）。 */
fun Char.isChinese(): Boolean {
    val unicodeScript = Character.UnicodeScript.of(this.toInt())
    if (unicodeScript == Character.UnicodeScript.HAN) return true
    val unicodeBlock = Character.UnicodeBlock.of(this)
    if (unicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION
        || unicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
        || unicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
        || unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
        || unicodeBlock == Character.UnicodeBlock.VERTICAL_FORMS
    ) return true;
    return false
}

/** 显示长度。 */
val String.displayLength get() = this.fold(0) { r, c -> if (c.isChinese()) r + 2 else r + 1 }
