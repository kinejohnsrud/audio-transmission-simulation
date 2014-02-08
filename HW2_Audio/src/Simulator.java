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
	int packetsize = 100;				//change this to do your tests on different packet sizes
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
	
	//reading in music (.AU) file, entering it into a byte-array,
	//split the array into smaller byte-arrays of size packetSize,
	//run networkSimulation on the files
	public void readFile() throws IOException{
	    File soundFile = new File("your path here");
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
	    //The last packet that is not of full packet size
	    int bytesLeft = byteArray.length-counter;
	    for (int i = 0; i < bytesLeft; i++) {
			packet[i] = byteArray[counter];
		}
	    networkSimulation(packet);
	}
	
	public void printByte(byte b){
		System.out.print(String.format("%02X ", b));
	}
	
	//runs the simple network simulation
	public void networkSimulation(byte[] packet){
		//Calculates a random loss probability (between 0 and 100)
		int loss = calculateLossProb();
		int threshold = 40;		//set your wanted threshold here
		//Checks if loss prob is smaller than set threshold
		if (loss < threshold){
			//If it is, add packet to the output packetArray, and set packet as lastPacket
			lastPacket = new byte[packetsize];
			for (int i = 0; i < packet.length; i++) {
				packetArray.add(packet[i]);
				lastPacket[i] = packet[i];
			}
		}
		//if packet is lost, choose between the two loss handling probabilities
		else{
			for (int j = 0; j < lastPacket.length; j++) {
				//Uncomment the policy wanted:
				//Policy one: write last received packet in place of lost packet
				packetArray.add(lastPacket[j]);
				//Policy two: write a silent packet in place of lost packet
				//packetArray.add((byte)255);
			}
		}
	}

	//Calculate a random loss between 0 and 100
	public int calculateLossProb(){
		int prob = 0;
		prob = (int)(Math.random()*100);
		return prob;
	}
	
	//Writes the byte-array to an .AU audio file
	public void writeFile(ArrayList<Byte> output) throws IOException{
		FileOutputStream fos = new FileOutputStream("Your wanted output path and filename");
		byte[] outputData = new byte[output.size()];
		for (int i = 0; i < outputData.length; i++) {
		    outputData[i] = (byte) output.get(i);
		}
		fos.write(outputData);
		fos.close();
	}
}