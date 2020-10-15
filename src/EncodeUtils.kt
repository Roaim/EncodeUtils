import java.util.*

object EncodeUtils {

    private const val DEFAULT_HEX_SEPARATOR = ""
    private const val DEFAULT_IS_HEX_UPPER_CASE = false

    private var hexSeparator = DEFAULT_HEX_SEPARATOR
    private var isHexUpperCase = DEFAULT_IS_HEX_UPPER_CASE
    private val hexFormat: String get() = "%02${if (isHexUpperCase) "X" else "x"}"

    fun setHexDefaultFormatting() = setHexPrettyFormatting(DEFAULT_HEX_SEPARATOR, DEFAULT_IS_HEX_UPPER_CASE)

    fun setHexPrettyFormatting(separator: String = ":", isUpperCase: Boolean = true) {
        hexSeparator = separator
        isHexUpperCase = isUpperCase
    }

    fun ByteArray.toHexString() = joinToString(hexSeparator) { hexFormat.format(it) }

    fun String.hexToByteArray() = (hexSeparator.takeIf { it.isNotEmpty() }?.let { s ->
        replace(s, "")
    } ?: this).chunked(2).map { it.toInt(16).toByte() }.toByteArray()

    fun String.hexToBase64() = hexToByteArray().toBase64()

    fun String.base64ToHex() = base64ToByteArray().toHexString()

    fun ByteArray.toBase64() = Base64.getEncoder().encodeToString(this)

    fun String.toBase64() = toByteArray().toBase64()

    fun String.base64ToByteArray() = Base64.getDecoder().decode(this)

    fun String.base64ToText() = String(base64ToByteArray())

}