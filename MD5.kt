import java.security.MessageDigest

class MD5 {
    companion object {
        fun digest(data: ByteArray): ByteArray {
            val md = MessageDigest.getInstance("MD5")
            return md.digest(data)
        }
    }
}