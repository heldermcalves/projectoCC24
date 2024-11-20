import java.io.*;
import java.net.*;
import java.util.*;

public class NetTaskClient implements Runnable{
    
    public ArrayList<NetTaskPacket> packetBuf;
    public DatagramSocket receiver;
    public Node server;

    NetTaskClient(ArrayList<NetTaskPacket> packetList,DatagramSocket clientSocket,Node serverNode) {
        this.packetBuf = packetList;
        this.receiver = clientSocket;
        this.server = serverNode;
        
    }

    public void run()  {

        int expectedSeq = 1;
        boolean activeConnection = true;

        while (activeConnection) {

            DatagramPacket bufferPacket = NetTaskPacket.buildPacket();
            
            try {
                receiver.receive(bufferPacket);
            }
            catch (IOException  e) {}

            NetTaskPacket dataPacket = new NetTaskPacket(new String(bufferPacket.getData()));

            
            if (dataPacket.getSeqN() == expectedSeq) {
                int dataLength = dataPacket.getDataLength(); // usando tamanho de packet.data, alternativamente usar tamanho do packet (real payload UDP)

                NetTaskPacket ackPacket = new NetTaskPacket("ACK: message received.",dataPacket.getAckN() + dataLength,dataPacket.getSeqN());
                byte[] ackData = ackPacket.toString().getBytes();
                DatagramPacket sendPacket = new DatagramPacket(ackData,ackData.length,server.getAddress(),server.getPort());
                
                try {
                    receiver.send(sendPacket);
                }
                catch (IOException e) {}

            }
            if (dataPacket.getAckN() == -1) {
                // send back ACK
                activeConnection = false;
            }
            
        }
    }
    
    public ArrayList<NetTaskPacket> getPacketBuf() {
        return this.packetBuf;
    }
    public DatagramSocket getReceiver() {
        return this.receiver;
    }
    public Node getServerNode() {
        return this.server;
    }
    
}
