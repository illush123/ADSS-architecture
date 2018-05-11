import java.nio.ByteBuffer

class IpHeader(val type: ByteArray, private val version: ByteArray, private val ttl: ByteArray) {

    companion object {
        const val HSIZE = 12
        val VERSION = ByteBuffer.allocate(4).putInt(10).array() // test
        val TTL = ByteBuffer.allocate(4).putInt(3).array() //test
    }

    fun toBytes(): ByteArray = ByteUtil.combineBytes(type, version, ttl)

    override fun toString(): String {
        return "type = ${ByteBuffer.wrap(type).int}\n" + "version = ${ByteBuffer.wrap(version).int}\n" + "ttl = ${ByteBuffer.wrap(ttl).int}"
    }

}