import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.locks.*;;

public class NetTaskServer implements Runnable {
    ReentrantLock lock;
    
    boolean serverReceiver;
    boolean isTimer;
    
    HashMap<String,Node> clientSet;
    List<NetTaskPacket> datagramList;
    int window;

    public void run() {
        if (isTimer) {
            // timer
        }
        else if (serverReceiver) {
            // ACK receiver
        }
        else {
           // TASK sender
        }
    }

    
    
}