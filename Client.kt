import java.io.DataOutputStream
import java.io.File
import java.net.Socket
import java.nio.ByteBuffer


fun main(args: Array<String>) {
    val server = Socket("127.0.0.1", 8080)
    val data = File(args[1]).readBytes()
    println("---layer3---\n"+ByteUtil.toHexString(data))
    val len = ByteBuffer.allocate(4).putInt(data.size).array()

    val packet = when (args[0].toInt()) {
        Type.UDP -> { //UDP
            val udpHeader = UdpHeader(UdpHeader.TESTTYPE,len)
            println("---layer2---\n"+udpHeader.toString())
            val ipType = ByteBuffer.allocate(4).putInt(Type.UDP).array()
            val ipHeader = IpHeader(ipType,IpHeader.VERSION,IpHeader.TTL)
            println("---layer1---\n" + ipHeader.toString())
            ByteUtil.combineBytes(ipHeader.toBytes(),udpHeader.toBytes(),data)
        }
        Type.TCP -> {//TCP
            val digest = MD5.digest(data)
            val tcpHeader = TcpHeader(TcpHeader.TESTTYPE,len,digest)
            println("---layer2---\n"+tcpHeader.toString())
            val ipType = ByteBuffer.allocate(4).putInt(Type.TCP).array()
            val ipHeader = IpHeader(ipType,IpHeader.VERSION,IpHeader.TTL)
            println("---layer1---\n"+ipHeader.toString())
            ByteUtil.combineBytes(ipHeader.toBytes(),tcpHeader.toBytes(),data)
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
