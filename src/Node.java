import java.net.*;

public class Node {
    InetAddress address; 
    int port;

    public Node(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    public InetAddress getAddress() {
        return this.address;
    }
    public int getPort() {
        return this.port;
    }
    
}
