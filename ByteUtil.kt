import java.nio.ByteBuffer

class ByteUtil {

    companion object {

        fun toHexString(bytes: ByteArray): String {
            val stringBuffer = StringBuffer()
            bytes.forEachIndexed() { index, value ->
                stringBuffer.append(String.format("%02x ", value))
                if ((index + 1) % 16 == 0) stringBuffer.append("\n")

            }
            return stringBuffer.toString()
        }

        fun combineBytes(vararg arrays: ByteArray): ByteArray {
            var totalSize = 0
            arrays.forEach {
                totalSize += it.size
            }
            val bytebuff = ByteBuffer.allocate(totalSize)
            arrays.forEach {
                bytebuff.put(it)
            }
            return bytebuff.array()
        }

        fun bytesToInt(bytes: ByteArray): Int = ByteBuffer.wrap(bytes).int

        fun intToBytes(int: Int): ByteArray = ByteBuffer.allocate(4).putInt(int).array()
    }
}