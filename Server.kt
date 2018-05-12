import java.io.DataInputStream
import java.net.ServerSocket

fun main(args: Array<String>) {
    val port = 8080
    val server = ServerSocket(port)
    while (true) {
        println("server waiting")
        val client = server.accept()
        val dis = DataInputStream(client.getInputStream())
        val readBytes = dis.readBytes(1024 + IpHeader.SIZE + TcpHeader.SIZE)


        val ipHeader = IpHeader(
                readBytes.copyOfRange(0, 4),
                readBytes.copyOfRange(4, 8),
                readBytes.copyOfRange(8, 12)
        ).apply { println("---layer1---\n" + toString()) }

        val type = ByteUtil.bytesToInt(ipHeader.type)
        when (type) {

            Type.UDP -> {

                val udpHeader = UdpHeader(
                        readBytes.copyOfRange(12, 16),
                        readBytes.copyOfRange(16, 20)
                ).apply { println("---layer2---\n" + toString()) }
                val data = readBytes.copyOfRange(20, 20 + ByteUtil.bytesToInt(udpHeader.len))
                println("---layer3---\n" + ByteUtil.toHexString(data))
            }

            Type.TCP -> {

                val tcpHeader = TcpHeader(
                        readBytes.copyOfRange(12, 16),
                        readBytes.copyOfRange(16, 20),
                        readBytes.copyOfRange(20, 36)
                ).apply { println("---layer2---\n" + toString()) }
                val data = readBytes.copyOfRange(36, 36 + ByteUtil.bytesToInt(tcpHeader.len))

                val digest = MD5.digest(data)
                print("digest = ${ByteUtil.toHexString(digest)}")
                if (!tcpHeader.digest.contentEquals(digest)) {
                    println("incorrect packet")
                    return
                }

                println("packet is protected")
                println("---layer3---\n" + ByteUtil.toHexString(data))
            }

            else -> {
                println("Type '$type' does not exist")
                return
            }
        }
        dis.close()
    }
}

