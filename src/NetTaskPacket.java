import java.net.DatagramPacket;

public class NetTaskPacket {
    
    private int seqN;
    private int ackN;
    private String data;

    public NetTaskPacket(String data,int seqN,int ackN) {
        this.data = new String(data);
        this.seqN = seqN;
        this.ackN =  ackN;
    }
    public NetTaskPacket(String objectString) {
        String[] parameters = objectString.split("!");
        this.seqN = Integer.parseInt(parameters[0]);
        this.ackN = Integer.parseInt(parameters[1]);
        this.data = new String(parameters[2]); 
    }

    public int getSeqN() {
        return this.seqN;
    }

    public int getAckN() {
        return this.ackN;
    }
    
    public String getData() {
        return this.data;
    }

    public int getDataLength() {
        return this.data.length();
    }

    public String toString() {
        return (this.data + "!"+ this.seqN + "!" + this.ackN);
    }
    public byte[] toByte() {
        return this.toString().getBytes();
    }

    public static DatagramPacket buildPacket() {
        byte[] buffer = new byte[512];
        DatagramPacket receiverPacket = new DatagramPacket(buffer,buffer.length);
        return receiverPacket;
    }

    public DatagramPacket buildPacket(Node receiver) {
        byte[] data = this.toByte();
        DatagramPacket senderPacket = new DatagramPacket(data, data.length,receiver.getAddress(),receiver.getPort());
        return senderPacket;
    }
}