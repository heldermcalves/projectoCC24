public class NetTaskPacket {
    
    private String data;
    private int seqN;
    private int ackN;

    public NetTaskPacket(String data,int seqN,int ackN) {
        this.data = new String(data);
        this.seqN = seqN;
        this.ackN =  ackN;
    }

    public NetTaskPacket(String objectString) {
        String[] parameters = objectString.split("!");
        this.data = new String(parameters[0]);
        this.seqN = Integer.parseInt(parameters[1]);
        this.ackN = Integer.parseInt(parameters[2]);
    }

    public int getSeqN() {
        return this.seqN;
    }

    public int getAckN() {
        return this.ackN;
    }

    public String toString() {
        return (this.data + "!"+ this.seqN + "!" + this.ackN);
    }
    public byte[] toByte() {
        return this.toString().getBytes();
    }
}