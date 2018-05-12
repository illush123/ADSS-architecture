class TcpHeader(private val type: ByteArray, val len: ByteArray, val digest: ByteArray) {

    companion object {
        const val SIZE = 24
        const val TYPE = 5
    }

    fun toBytes(): ByteArray = ByteUtil.combineBytes(type, len, digest)

    override fun toString(): String {
        return "type = ${ByteUtil.bytesToInt(type)}\n" +
                "len = ${ByteUtil.bytesToInt(len)}\n" +
                "digest = ${ByteUtil.toHexString(digest)}"
    }
}
