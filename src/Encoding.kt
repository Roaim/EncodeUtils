import EncodeUtils.base64ToHex
import EncodeUtils.base64ToText
import EncodeUtils.hexToBase64
import EncodeUtils.toBase64

const val APP_NAME = "ENCODE UTILS"
const val HEADER_TEXT = "" +
        " ----------------------------- \n" +
        "    Welcome to $APP_NAME   \n" +
        " ----------------------------- "
const val CHOICE_TEXT =
    "Please input a number from following choice:\n" +
            "1. Text to Base64\n" +
            "2. Base64 to Text\n" +
            "3. Hex to Base64\n" +
            "4. Base64 to Hex\n" +
            "5. Base64 to Hex (Pretty)\n" +
            "Enter 0 to exit"
const val FOOTER_TEXT = "Thank you for using $APP_NAME"

fun main(args: Array<String>) {
    println(HEADER_TEXT)
    loop@ while (true) {
        println(CHOICE_TEXT)
        when (readNumber()) {
            1 -> convertTextToBase64()
            2 -> convertBase64ToText()
            3 -> convertHexToBase64()
            4 -> convertBase64ToHex()
            5 -> convertBase64ToHex(pretty = true)
            0 -> break@loop
            else -> continue@loop
        }
    }
    println(FOOTER_TEXT)
}

fun convertBase64ToText() {
    println("Please enter Base64 to be converted to Text:")
    println("Text:\n${readText().base64ToText()}")
}

fun convertTextToBase64() {
    println("Please enter Text to be converted to Base64:")
    println("Base64:\n${readText().toBase64()}")
}

fun convertBase64ToHex(pretty: Boolean = false) {
    println("Please enter Base64 to be converted to Hex:")
    val base64 = readText().trim()
    if (pretty) EncodeUtils.setHexPrettyFormatting() else EncodeUtils.setHexDefaultFormatting()
    println("Hex:\n${base64.base64ToHex()}")
}

fun convertHexToBase64() {
    println("Please enter Hex to be converted to Base64")
    var hex = readText()
    if (hex.contains(':')) {
        EncodeUtils.setHexPrettyFormatting()
        hex = hex.toUpperCase().trim()
    } else {
        EncodeUtils.setHexDefaultFormatting()
        hex = hex.toLowerCase().trim()
    }
    println("Base64:\n${hex.hexToBase64()}")
}

fun readNumber(): Int = try {
    readLine()?.toInt()
} catch (e: NumberFormatException) {
    e.message?.run {
        substring(indexOf(':').inc())
    }.also { println("Your input, $it is not a number. Please enter a number.") }
    null
} ?: readNumber()

fun readText(): String = readLine().let {
    if (it.isNullOrEmpty()) {
        println("Please enter a valid text.")
        null
    } else it
} ?: readText()