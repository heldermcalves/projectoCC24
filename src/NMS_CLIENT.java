import java.io.*;
import java.net.*;
import java.lang.String;

public class NMS_CLIENT { 
    public static void main(String[] args) throws IOException {
        String serverIP = new String(args[0]);
        int serverPort = Integer.parseInt(args[1]);

        DatagramSocket clientSocket = new DatagramSocket();
        
        
        /* EXECUTAR EM THREADS */
        // listen - busca tasks
        // taskManager - manages, executes tasks

        
        
    }
} 
/*
System.out.println("Client: Hello and welcome!");

        DatagramSocket clientSocket = new DatagramSocket();
        byte[] data = new byte[128];
        InetAddress dest = InetAddress.getByName("10.0.4.10");
        DatagramPacket sendPacket = new DatagramPacket(data,data.length,dest,8888);

        clientSocket.send(sendPacket);                
        clientSocket.close();
        */