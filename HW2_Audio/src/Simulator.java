import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class Simulator {
	
	byte[] byteArray;
	ArrayList<Byte> packetArray = new ArrayList<Byte>();
	int packetsize = 200;
	byte[] lastPacket = new byte[packetsize];
	
	public static void main(String[] args) {
		Simulator run = new Simulator();
		try {
			run.readFile();
			run.writeFile(run.packetArray);
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Something went wrong");
		}
	}
	
	public void readFile() throws IOException{
	    File soundFile = new File("/Users/Kine/Documents/GitHub/Network_Computing/HW2_Audio/src/canttakeit.au");
	    byteArray = new byte[(int) soundFile.length()];
	    DataInputStream dis = new DataInputStream(new FileInputStream(soundFile));
	    dis.readFully(byteArray);
	    dis.close();
	    int counter = 0;
	    byte[] packet = new byte[packetsize];
	    while(byteArray.length-counter>=packetsize){
	    	packet = Arrays.copyOfRange(byteArray, counter, (counter+packetsize));
	    	networkSimulation(packet);
	    	counter+=packetsize;
	    }
	    int bytesLeft = byteArray.length-counter;
	    for (int i = 0; i < bytesLeft; i++) {
			packet[i] = byteArray[counter];			//removed +1
		}
	    networkSimulation(packet);
	}
	
	public void printByte(byte byttte){
		System.out.print(String.format("%02X ", byttte));
	}
	
	public void networkSimulation(byte[] packet){
		int loss = calculateLossProb();
		int threshold = 80;
		if (loss < threshold){
			lastPacket = new byte[packetsize];
			for (int i = 0; i < packet.length; i++) {
				packetArray.add(packet[i]);
				lastPacket[i] = packet[i];
			}
		}
		else{
			for (int j = 0; j < lastPacket.length; j++) {
				packetArray.add(lastPacket[j]);
				//packetArray.add((byte)255);
			}
		}
	}

	public int calculateLossProb(){
		int prob = 0;
		prob = (int)(Math.random()*100);
		return prob;
	}
	
	public void writeFile(ArrayList<Byte> output) throws IOException{
		FileOutputStream fos = new FileOutputStream("/Users/Kine/Documents/GitHub/Network_Computing/HW2_Audio/src/output.au");
		byte[] outputData = new byte[output.size()];
		for (int i = 0; i < outputData.length; i++) {
		    outputData[i] = (byte) output.get(i);
		}
		fos.write(outputData);
		fos.close();
	}
}