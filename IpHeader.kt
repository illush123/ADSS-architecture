class IpHeader(val type: ByteArray, private val version: ByteArray, private val ttl: ByteArray) {

    companion object {
        const val SIZE = 12
        const val VERSION = 4
        const val TTL = 255
    }

    fun toBytes(): ByteArray = ByteUtil.combineBytes(type, version, ttl)

    override fun toString(): String {
        return "type = ${ByteUtil.bytesToInt(type)}\n" + "version = ${ByteUtil.bytesToInt(version)}\n" + "ttl = ${ByteUtil.bytesToInt(ttl)}"
    }

}