import java.io.DataInputStream
import java.net.ServerSocket
import java.nio.ByteBuffer

fun main(args: Array<String>) {
    val port = 8080
    val server = ServerSocket(port)
    while (true) {
        println("server waiting")
        val client = server.accept()
        val inputStream = client.getInputStream()
        val dis = DataInputStream(inputStream)

        val readBytes = dis.readBytes(1024 + IpHeader.HSIZE + TcpHeader.HSIZE)
        val ipHeader = IpHeader(readBytes.copyOfRange(0, 4), readBytes.copyOfRange(4, 8), readBytes.copyOfRange(8, 12))
        println("---layer1---\n"+ipHeader.toString())

        val type = ByteBuffer.wrap(ipHeader.type).int
        when (type) {
            Type.UDP -> {//udp
                val udpHeader = UdpHeader(readBytes.copyOfRange(12, 16), readBytes.copyOfRange(16, 20))
                println("---layer2---\n" +udpHeader.toString())
                val data = readBytes.copyOfRange(20, 20 + ByteBuffer.wrap(udpHeader.len).int)
                println("---layer3---\n"+ByteUtil.toHexString(data))
            }
            Type.TCP -> {//tcp
                val tcpHeader = TcpHeader(readBytes.copyOfRange(12, 16), readBytes.copyOfRange(16, 20), readBytes.copyOfRange(20, 36))
                println("---layer2---\n"+tcpHeader.toString())
                val data = readBytes.copyOfRange(36, 36 + ByteBuffer.wrap(tcpHeader.len).int)
                if (!tcpHeader.digest.contentEquals(MD5.digest(data))){
                    println("incorrect packet")
                    return
                }
                println("packet is protected")
                println("---layer3---\n"+ByteUtil.toHexString(data))
            }
            else -> {
                println("Type '$type' does not exist")
                return
            }
        }

        dis.close()
    }
}

