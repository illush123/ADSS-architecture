import java.nio.ByteBuffer

class TcpHeader(private val type: ByteArray, val len: ByteArray, val digest: ByteArray) {

    companion object {
        const val HSIZE = 24
        val TESTTYPE = ByteBuffer.allocate(4).putInt(5).array() // test
    }

    fun toBytes(): ByteArray = ByteUtil.combineBytes(type, len, digest)

    override fun toString(): String {
        return "type = ${ByteBuffer.wrap(type).int}\n" +
                "len = ${ByteBuffer.wrap(len).int}\n" +
                "digest = ${ByteUtil.toHexString(digest)}"
    }
}
