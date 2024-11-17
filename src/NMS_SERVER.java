import java.net.*;
import java.util.HashMap;
import java.io.*;

public class NMS_SERVER {
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        int clientsN = Integer.parseInt(args[1]);

        HashMap<String,Node> clientMap = new HashMap<String,Node>();
        DatagramSocket serverSocket = new DatagramSocket(port);
        
        NMS_SERVER.syncToClients(serverSocket, clientMap,clientsN);

    }

    
    public static void syncToClients(DatagramSocket serverSocket,HashMap<String,Node> clientSet,int clientN) throws IOException {
        while (clientSet.size() < clientN) {
            byte[] nameData = new byte[16];
            DatagramPacket receivePacket = new DatagramPacket(nameData,nameData.length);
            
                
            serverSocket.receive(receivePacket);
            nameData = receivePacket.getData();
            String clientName = new String(nameData);
            Node newClient = new Node(receivePacket.getAddress(),receivePacket.getPort());
            clientSet.put(clientName, newClient);

            byte[] ackData = new byte[1];
            DatagramPacket ackPacket = new DatagramPacket(ackData,ackData.length,newClient.getAddress(),newClient.getPort());
            
            serverSocket.send(ackPacket);

        }

    }
}