import java.util.*;
import java.io.*;
import java.net.*;
import java.lang.String;

public class NMS_CLIENT { 
    public static void main(String[] args) throws IOException {
        String serverIP = new String(args[0]);
        int serverPort = Integer.parseInt(args[1]);
        Node server = new Node(InetAddress.getByName(serverIP), serverPort);
        
        DatagramSocket clientSocket = new DatagramSocket(7777);
        NMS_CLIENT.syncToServer(clientSocket, server);

        ArrayList<NetTaskPacket> packetList = new ArrayList<NetTaskPacket>();

        Thread netTaskClient = new Thread(new NetTaskClient(packetList,clientSocket,server));
        Thread taskWorker;
        
        
    }
    
    public static void syncToServer(DatagramSocket clientSocket, Node server) throws IOException{
        String clientName = "PC3"; //systemUtils.captureCommandOutput("hostname");
        byte[] nameData = clientName.getBytes();
        byte[] ackData = new byte[1];
        
        DatagramPacket sendPacket = new DatagramPacket(nameData,nameData.length,server.getAddress(),server.getPort());
        DatagramPacket receiveACK = new DatagramPacket(ackData,ackData.length);
        boolean ack = false;
        while (!ack) {
            try {
                clientSocket.send(sendPacket);
                ack = true;
            }
            catch (Exception e) {
                System.err.println(e);
            }
            finally {
                try {
                    clientSocket.setSoTimeout(5000);
                    clientSocket.receive(receiveACK);
                }
                catch(Exception e) {
                    ack = false;
                }
            }
            clientSocket.setSoTimeout(0);
        }
    }
} 