import java.nio.ByteBuffer

class UdpHeader(private val type: ByteArray, val len: ByteArray) {
    companion object {
        // const val HSIZE = 8
        val TESTTYPE = ByteBuffer.allocate(4).putInt(5).array() //test
    }

    fun toBytes(): ByteArray = ByteUtil.combineBytes(type, len)

    override fun toString(): String =
            "type = ${ByteBuffer.wrap(type).int}\n" + "len = ${ByteBuffer.wrap(len).int}"
}