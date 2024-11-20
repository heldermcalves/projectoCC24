import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.*;



public class NetTaskServer implements Runnable {
    
    DatagramSocket senderSocket;
    DatagramSocket receiverSocket;

    Node client;
    List<NetTaskPacket> datagramList;
    AtomicInteger base;
    
    ReentrantReadWriteLock lock;
    Boolean activeTransmission;

    AtomicInteger timer;

    public NetTaskServer(DatagramSocket senderSocket,DatagramSocket receiverSocket,Node client,List<NetTaskPacket> datagramList) {
        this.senderSocket = senderSocket;
        this.receiverSocket = receiverSocket;
        this.client = client;
        this.datagramList = datagramList;
        this.base = new AtomicInteger();
        this.timer = new AtomicInteger();
        this.lock = new ReentrantReadWriteLock();
        this.activeTransmission = Boolean.parseBoolean("true");
        
    }

    public void run() {

        Thread receiver = new Thread(new Receiver(this));
        
        int nextSeqN = 0,window = 10;
        
        while (activeTransmission) {
        
            try {
                this.lock.writeLock().lock();
            }
            finally {
                while (nextSeqN < this.base.get() + window) {
                    byte[] sendData = this.datagramList.get(nextSeqN).toByte();
                    DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length,this.client.getAddress(),this.client.getPort());
                    try {
                        senderSocket.send(sendPacket);
                    }
                    catch (IOException e) {}
                    if (this.base.get() == nextSeqN) {
                        this.timer.set(0);
                    }
                    nextSeqN++;
                }
                this.lock.writeLock().unlock();
            }
        }




    }

    private class Receiver implements Runnable {
        
        NetTaskServer netTaskSender;

        public Receiver(NetTaskServer sender) {
            this.netTaskSender = sender;
        }

        public void run() {
            int expectedAckN = 1;
            while (activeTransmission) {
                
                byte[] dataBuffer = new byte[256];
                DatagramPacket ackDatagram = new DatagramPacket(dataBuffer, dataBuffer.length);
                try {
                    receiverSocket.receive(ackDatagram);
                }
                catch (IOException e) {}

                NetTaskPacket ackPacket = new NetTaskPacket(new String(ackDatagram.getData()));
                if (ackPacket.getAckN() == expectedAckN) {
            
                    try {
                        netTaskSender.lock.writeLock().lock();
                    }
                    finally {
                        int newIndex= netTaskSender.base.incrementAndGet();
                        expectedAckN = netTaskSender.datagramList.get(newIndex).getSeqN() + netTaskSender.datagramList.get(newIndex).getData().length();
                        if (netTaskSender.base.get() == netTaskSender.nextSeqN.get()) { // ou equals()
                            // stop timer
                        }
                        else {
                            //restart timer
                        }
                        netTaskSender.lock.writeLock().unlock();
                    }
                }
            }
        }
    }
        public class timerAgent implements Runnable {

            Timer timer;
            public void run() {

            }
        }

    
    
}