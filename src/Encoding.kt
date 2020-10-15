import EncodeUtils.base64ToHex
import EncodeUtils.base64ToText
import EncodeUtils.hexToBase64
import EncodeUtils.hexToText
import EncodeUtils.toBase64
import EncodeUtils.toHexText

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
            "6. Hex to Text\n" +
            "7. Text to Hex\n" +
            "8. Text to Hex (Pretty)\n" +
            "Enter 0 to exit"
const val FOOTER_TEXT = "Thank you for using $APP_NAME"

fun main(args: Array<String>) {
    println(HEADER_TEXT)
    loop@ while (true) {
        println(CHOICE_TEXT)
        when (readNumber()) {
            1 -> convertOutput("Text", "Base64") { readText().toBase64() }
            2 -> convertOutput("Base64", "Text") { readText().base64ToText() }
            3 -> convertOutput("Hex", "Base64") { readHex().hexToBase64() }
            4 -> convertOutputToHex("Base64") { readText().base64ToHex() }
            5 -> convertOutputToHex("Base64", pretty = true) { readText().base64ToHex() }
            6 -> convertOutput("Hex", "Text") { readHex().hexToText() }
            7 -> convertOutputToHex("Text") { readText().toHexText() }
            8 -> convertOutputToHex("Text", pretty = true) { readText().toHexText() }
            0 -> break@loop
            else -> continue@loop
        }
    }
    println(FOOTER_TEXT)
}

inline fun convertOutputToHex(from: String, pretty: Boolean = false, convert: () -> String) {
    if (pretty) EncodeUtils.setHexPrettyFormatting() else EncodeUtils.setHexDefaultFormatting()
    convertOutput(from, "Hex", convert)
}

inline fun convertOutput(from: String, to: String, convert: () -> String) {
    println("Please enter $from to be converted to $to")
    println("$to:\n${convert()}\n")
}

fun readHex() = readText().run {
    if (contains(':')) {
        EncodeUtils.setHexPrettyFormatting()
        toUpperCase().trim()
    } else {
        EncodeUtils.setHexDefaultFormatting()
        toLowerCase().trim()
    }
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