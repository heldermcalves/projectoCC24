import java.net.*;
import java.util.*;

public class NetTaskClient implements Runnable {
    
    private Node server;

    public void run() {
        // Inicialização

        // loop receive
    }

    public void syncToServer(DatagramSocket clientSocket, Node server) {
        String clientName = systemUtils.captureCommandOutput("hostname");
        byte[] nameData = clientName.getBytes();
        byte[] ackData = new byte[1];
        
        DatagramPacket sendPacket = new DatagramPacket(nameData,nameData.length,this.getServerAddress(),this.getServerPort());
        DatagramPacket receiveACK = new DatagramPacket(ackData,ackData.length);
        boolean ack = false;
        while (!ack) {
            try {
                clientSocket.send(sendPacket);
                ack = true;
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
            }
        }
    }
    
    public Node getServerNode() {
        return this.server;
    }
    
    public InetAddress getServerAddress() {
        return this.getServerNode().getAddress();
    }

    public int getServerPort() {
        return this.getServerNode().getPort();
    }
}
