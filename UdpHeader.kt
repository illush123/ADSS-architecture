class UdpHeader(private val type: ByteArray, val len: ByteArray) {
    companion object {
        const val SIZE = 8
        const val TYPE = 5
    }

    fun toBytes(): ByteArray = ByteUtil.combineBytes(type, len)

    override fun toString(): String = "type = ${ByteUtil.bytesToInt(type)}\n" + "len = ${ByteUtil.bytesToInt(len)}"
}