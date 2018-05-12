import java.io.DataOutputStream
import java.io.File
import java.net.Socket


fun main(args: Array<String>) {
    val server = Socket("127.0.0.1", 8080)
    val data = File(args[1]).readBytes()
    println("---layer3---\n" + ByteUtil.toHexString(data))
    val packet: ByteArray
    when (args[0].toInt()) {
        Type.UDP -> { //UDP

            val udpHeader = UdpHeader(
                    ByteUtil.intToBytes(UdpHeader.TYPE),
                    ByteUtil.intToBytes(data.size)
            ).apply { println("---layer2---\n" + toString()) }

            val ipHeader = IpHeader(
                    ByteUtil.intToBytes(Type.UDP),
                    ByteUtil.intToBytes(IpHeader.VERSION),
                    ByteUtil.intToBytes(IpHeader.TTL)
            ).apply { println("---layer1---\n" + toString()) }

            packet = ByteUtil.combineBytes(ipHeader.toBytes(), udpHeader.toBytes(), data)
        }
        Type.TCP -> {//TCP

            val tcpHeader = TcpHeader(
                    ByteUtil.intToBytes(TcpHeader.TYPE),
                    ByteUtil.intToBytes(data.size),
                    MD5.digest(data)
            ).apply { println("---layer2---\n" + toString()) }

            val ipHeader = IpHeader(
                    ByteUtil.intToBytes(Type.TCP),
                    ByteUtil.intToBytes(IpHeader.VERSION),
                    ByteUtil.intToBytes(IpHeader.TTL)
            ).apply { println("---layer1---\n" + toString()) }

            packet = ByteUtil.combineBytes(ipHeader.toBytes(), tcpHeader.toBytes(), data)
        }
        else -> {
            println("args[0](1:UDP, 2:TCP),args[1](filepath)")
            return
        }
    }
    val dos = DataOutputStream(server.getOutputStream())
    dos.write(packet)
    dos.close()
}
